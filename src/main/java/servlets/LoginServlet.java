package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.IUserService;
import service.UserServiceImpl;
import java.io.IOException;


@WebServlet("/login") 
public class LoginServlet extends HttpServlet {
	
    private static final long serialVersionUID = 1L;
    private final IUserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Empty fields");
            return;
        }

        if (userService.authenticate(username, password)) {
            HttpSession session = request.getSession(true); // Creates a new session if one doesn't exist
            session.setAttribute("username", username);
            
            // Redirect to dashboard servlet to avoid request forwarding issues
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Invalid credentials");
        }
    }
}