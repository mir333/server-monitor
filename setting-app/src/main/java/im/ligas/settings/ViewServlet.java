package im.ligas.settings;

import org.apache.commons.collections.Buffer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Miroslav Ligas <miroslav.ligas@ibacz.eu>
 */
public class ViewServlet extends HttpServlet {

    private static final String JSP_VIEW = "/WEB-INF/jsp/index.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MemStorage memStore = MemStorage.getInstance();

        Buffer storedData = memStore.getMessages();
        List<ServerData> data = bufferToList(storedData);
        request.setAttribute("data", data);

        boolean receiving = WatchDog.getInstance().isReceiving();
        request.setAttribute("error1", !receiving);

        Buffer errorData = memStore.getErrors();
        List<ErrorData> errors = bufferToList(errorData);
        request.setAttribute("errors", errors);

        RequestDispatcher view = request.getRequestDispatcher(JSP_VIEW);
        view.forward(request, response);
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
