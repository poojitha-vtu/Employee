package employ;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminDAO adminDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        adminDAO = new AdminDAO(); // Initialize AdminDAO
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Validate admin credentials using AdminDAO
            boolean isValidAdmin = adminDAO.validateAdmin(username, password);

            if (isValidAdmin) {
                // Admin credentials are correct, redirect to admin dashboard
                HttpSession session = request.getSession();
                session.setAttribute("adminUsername", username);
                response.sendRedirect("admin_dashboard.jsp");
            } else {
                // Admin credentials are incorrect, redirect back to login page with error message
                response.sendRedirect("admin_login.jsp?error=invalid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
            response.sendRedirect("admin_login.jsp?error=db");
        }
    }
}
