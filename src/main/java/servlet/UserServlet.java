package servlet;

import com.google.gson.Gson;
import model.User;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user != null) {
            Gson gson = new Gson();
            String json = gson.toJson(user);
            resp.getWriter().write(json);
            resp.setStatus(200);
        }else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() != null && req.getPathInfo().contains("registration")) {
            RequestDispatcher addUser = req.getRequestDispatcher("/add");
            addUser.include(req, resp);
            req.getSession().setAttribute("user", req.getAttribute("user"));
        }
        doGet(req, resp);
    }

}
