/* ===========================================================================
 * IBA CZ Confidential
 *
 * (c) Copyright IBA CZ 2011 ALL RIGHTS RESERVED
 * The source code for this program is not published or otherwise
 * divested of its trade secrets.
 *
 * =========================================================================== */
package im.ligas.monitoring.web;

import com.google.gson.Gson;
import im.ligas.monitoring.common.ServerData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Miroslav Ligas <miroslav.ligas@ibacz.eu>
 */
@Controller
@RequestMapping(value = "/data")
public class ProcessDataController {

    private static final Logger LOG = Logger.getLogger(ProcessDataController.class.getName());

    @Autowired
    private Gson gson;

    @Autowired
    private MemStorage memStore;

    @Autowired
    private WatchDog watchDog;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
     public String index(Model model) {
         return "index";
     }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void processData(
            @RequestParam(value = "data", required = false) String jsonData,
            @RequestParam(value = "error", required = false) String errorData,
            HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("plain/text");
        try {
            if (StringUtils.isNotBlank(jsonData)) {
                ServerData serverData = gson.fromJson(jsonData, ServerData.class);
                serverData.setRemoteAddr(request.getRemoteAddr());
                serverData.setNow(new Date());
                memStore.storeMessage(serverData);
                watchDog.update();
            } else {
                memStore.storeError(errorData);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            memStore.storeError("Problema: " + jsonData);
            LOG.log(Level.WARNING, "Could not process request", e);
        }
    }
}
