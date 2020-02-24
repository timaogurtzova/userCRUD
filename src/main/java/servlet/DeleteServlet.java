package servlet;

import service.ServiceUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            long idLong = Long.parseLong(id);
            ServiceUser.getInstance().deleteUserById(idLong);
        } catch (NumberFormatException e) {

        }
        getServletContext().getRequestDispatcher("/WEB-INF/adminpage.jsp").forward(req, resp);
    }
}
