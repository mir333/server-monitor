package im.ligas.settings;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Miroslav Ligas <miroslav.ligas@ibacz.eu>
 */
public class ProcessSettingsServlet extends HttpServlet {
    public static final Logger LOG = Logger.getLogger(ProcessSettingsServlet.class.getName());
    private static final Gson GSON = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("plain/text");
        response.setStatus(HttpServletResponse.SC_OK);
        WatchDog watchDog = WatchDog.getInstance();
        MemStorage memStore = MemStorage.getInstance();
        String jsonData = request.getParameter("data");
        String errorData = request.getParameter("error");
        try {
            if (jsonData!= null && !jsonData.trim().isEmpty()) {
                ServerData serverData = GSON.fromJson(jsonData, ServerData.class);
                serverData.setRemoteAddr(request.getRemoteAddr());
                serverData.setNow(new Date());
                memStore.storeMessage(serverData);
                watchDog.update();
            }else{
                memStore.storeError(errorData);
            }
        } catch (Exception e) {
            memStore.storeError("Problema: " + jsonData);
            LOG.log(Level.WARNING, "Could not process request", e);
        }
    }
}
