package servlet;

import model.User;
import service.ServiceUser;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/roleFilter")
public class RoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        User user = ServiceUser.getInstance().getUserWithNameAndPasswordService(name, password);

        if (user == null) {
            RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
            rd.forward(request, response);
        } else {
            switch (user.getRole()) {
                case ("admin"):{
                    request.getRequestDispatcher("/WEB-INF/adminpage.jsp").forward(request, response);
                    break;
                }
                case ("user"):{
                    request.getRequestDispatcher("/user").forward(request, response);
                    break;
                }
            }
        }

    }
}