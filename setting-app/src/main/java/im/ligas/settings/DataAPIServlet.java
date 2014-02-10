package im.ligas.settings;

import com.google.gson.Gson;
import org.apache.commons.collections.Buffer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Miroslav Ligas <miroslav.ligas@ibacz.eu>
 */
public class DataAPIServlet extends HttpServlet {
    private static final Gson GSON = new Gson();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Buffer messages = MemStorage.getInstance().getMessages();
        List<ServerData> data = DataUtil.bufferToList(messages);
        String result = GSON.toJson(data);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(result);
        response.flushBuffer();
    }
}
