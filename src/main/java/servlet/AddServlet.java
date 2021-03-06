package servlet;

import model.User;
import service.ServiceUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add")
public class AddServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/adminpage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String password = req.getParameter("password");
        String city = req.getParameter("city");
        String role = req.getParameter("role");
        if (role == null) {
            role = "user";
        }

        try {
            int ageInt = Integer.parseInt(age);
            User user = new User(name, ageInt, password, city, role);
            ServiceUser.getInstance().addUserService(user);
            req.setAttribute("user", user);
        } catch (NumberFormatException e) {

        }

    }
}
