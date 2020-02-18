package servlet;

import model.User;
import service.ServiceUser;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/role")
public class RoleFilter extends AbstractHttpFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        User userInSession = (User)session.getAttribute("user");

       /* User'a может не быть в сессии. Надо узнать, есть ли он в БД.
        User может быть в сессии, но тот, что в сессии, может не совпадать с User, под которым
        хочет войти пользователь. Если не совпадает, то надо узнать, есть ли в БД.
         */

        if (userInSession == null) {
            User userBD = ServiceUser.getInstance().getUserWithNameAndPasswordService(name, password);
            if (userBD != null) {
                session.setAttribute("user", userBD);
            }
            sendRedirect(request, response, userBD);
        } else {
            String nameUserInSession = userInSession.getName();
            String passwordUserInSession = userInSession.getPassword();
            if (nameUserInSession.equals(name) && passwordUserInSession.equals(password)) {
                sendRedirect(request, response, userInSession);
            } else {
                User userBD = ServiceUser.getInstance().getUserWithNameAndPasswordService(name, password);
                if (userBD != null) {
                    session.setAttribute("user", userBD);
                }
                sendRedirect(request, response, userBD);
                }
            }
    }

    private void sendRedirect (HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        if (user == null) {
            RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
            rd.forward(request, response);
        } else {
            switch (user.getRole()) {
                case ("admin"): {
                    request.getRequestDispatcher("/admin").forward(request, response);
                    break;
                }
                case ("user"): {
                    request.getRequestDispatcher("/user").forward(request, response);
                    break;
                }
            }
        }
    }
}
