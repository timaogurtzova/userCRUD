package servlet;

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
        getServletContext().getRequestDispatcher("/WEB-INF/adminpage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getPathInfo();

        if (operation.contains("add")) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/add");
            requestDispatcher.include(req, resp);
        } else if (operation.contains("update")) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/update");
            requestDispatcher.include(req, resp);
        }else if (operation.contains("delete")) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/delete");
            requestDispatcher.include(req, resp);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/adminpage.jsp").forward(req, resp);
    }
}
