package filter;

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
        User userInSession = (User) session.getAttribute("user");

       /* Проверка, есть ли в сессии User.
       Если в сессии нет User, узнаем, есть ли он в БД. Нашли - сохранили в сессию этого User'а.
       Если в сессии есть User, он может не совпадать с тем User, под которым хочет зайти пользователь.
       Узнаем, есть ли он в БД. Нашли - сохранили в сессию этого User'а.
         */
        if (userInSession == null) {
            User userDB = userInDB(session, name, password);
            sendRedirect(request, response, userDB);
        } else {
            String nameUserInSession = userInSession.getName();
            String passwordUserInSession = userInSession.getPassword();
            if (nameUserInSession.equals(name) && passwordUserInSession.equals(password)) {
                sendRedirect(request, response, userInSession);
            } else {
                User userDB = userInDB(session, name, password);
                sendRedirect(request, response, userDB);
            }
        }
    }

    private void sendRedirect(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
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

    private User userInDB(HttpSession session, String name, String password) {
        User userDB = ServiceUser.getInstance().getUserWithNameAndPasswordService(name, password);
        if (userDB != null) {
            session.setAttribute("user", userDB);
        }
        return userDB;
    }
}
