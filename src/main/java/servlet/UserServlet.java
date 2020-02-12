package servlet;

import com.google.gson.Gson;
import model.User;
import service.ServiceUser;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user== null){
            RequestDispatcher authorization = req.getRequestDispatcher("/index.jsp");
            authorization.forward(req, resp);
        }
        Gson gson = new Gson();
        String json = gson.toJson(user);
        resp.getWriter().write(json);
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher addUser = req.getRequestDispatcher("/add");
        addUser.include(req, resp);

        String name = req.getParameter("name");
        String password = req.getParameter("password");
        User user = ServiceUser.getInstance().getUserWithNameAndPasswordService(name, password);
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        Gson gson = new Gson();
        String json = gson.toJson(user);
        resp.getWriter().write(json);
        resp.setStatus(200);
    }
}
