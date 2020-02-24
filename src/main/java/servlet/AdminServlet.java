package servlet;

import model.User;
import service.ServiceUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String update = req.getPathInfo();
        if (user != null && user.getRole().equals("admin")) {
            if (update != null) {
                try {
                    String[] path = req.getPathInfo().split("/");
                    long id = Long.parseLong(path[path.length - 1]);
                    if (update.contains("update")) {
                        User userUpdate = ServiceUser.getInstance().getUserByIdService(id);
                        req.setAttribute("user", userUpdate);
                        getServletContext().getRequestDispatcher("/WEB-INF/update.jsp").forward(req, resp);
                    } else if (update.contains("delete")) {
                        ServiceUser.getInstance().deleteUserById(id);
                        getServletContext().getRequestDispatcher("/WEB-INF/adminpage.jsp").forward(req, resp);
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    //ignore
                }
            }
            getServletContext().getRequestDispatcher("/WEB-INF/adminpage.jsp").forward(req, resp);
        } else {
            resp.getWriter().write("This page for admin, nya");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getPathInfo();

        if (operation != null) {
            if (operation.contains("add")) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/add");
                requestDispatcher.include(req, resp);
            } else if (operation.contains("update")) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/update");
                requestDispatcher.include(req, resp);
            } else if (operation.contains("delete")) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/delete");
                requestDispatcher.include(req, resp);
            }
        }
        doGet(req, resp);
    }
}