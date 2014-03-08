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
import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author Miroslav Ligas <miroslav.ligas@ibacz.eu>
 */
@Controller
@RequestMapping(value = "/data")
public class ProcessDataController {

    private static final Logger LOG = Logger.getLogger(ProcessDataController.class);

    @Autowired
    private Gson gson;

    @Autowired
    private MemStorage memStore;

    @Autowired
    private WatchDog watchDog;

    @RequestMapping(value = "/save", method = RequestMethod.POST, params = "data")
    public void processData(
            @RequestParam("data") String data,
            HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("plain/text");
        try {
            if (StringUtils.isNotBlank(data)) {
                ServerData serverData = gson.fromJson(data, ServerData.class);
                serverData.setRemoteAddr(request.getRemoteAddr());
                serverData.setNow(new Date());
                memStore.storeMessage(serverData);
                watchDog.update();
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            memStore.storeError("PROBLEMA: " + data);
            LogMF.warn(LOG, e, "Could not process request {0}", new Object[]{data});
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, params = "error")
    public void processError(
            @RequestParam("error") String data,
                HttpServletResponse response) {
        response.setContentType("plain/text");
        try {
            memStore.storeError(data);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            memStore.storeError("ERROR: " + data);
            LogMF.warn(LOG, e, "Could not process request {0}", new Object[]{data});
        }
    }
}
