package im.ligas.monitoring.web;

import im.ligas.monitoring.common.ErrorData;
import im.ligas.monitoring.common.ServerData;
import org.apache.commons.collections.Buffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class OverviewController {

    @Autowired
    private MemStorage memStore;

    @Autowired
    private WatchDog watchDog;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails =
                    (UserDetails) authentication.getPrincipal();
            model.addAttribute("userDetails", userDetails);

            Buffer storedData = memStore.getMessages();
            List<ServerData> data = bufferToList(storedData);
            model.addAttribute("data", data);

            boolean receiving = watchDog.isReceiving();
            model.addAttribute("error1", !receiving);

            Buffer errorData = memStore.getErrors();
            List<ErrorData> errors = bufferToList(errorData);
            model.addAttribute("errors", errors);
        }

        return "index";
    }

    private <T> List<T> bufferToList(Buffer storedData) {
        List<T> data = new ArrayList<>(storedData.size());
        for (Object o : storedData) {
            T serverData = (T) o;
            data.add(serverData);
        }
        Collections.reverse(data);
        return data;
    }

}
