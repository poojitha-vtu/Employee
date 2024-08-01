package employ;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/AdminDashboardServlet")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDAO taskDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        taskDAO = new TaskDAO(); // Initialize TaskDAO
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeIdStr = request.getParameter("employeeId");

        try {
            int employeeId = Integer.parseInt(employeeIdStr); // Parse employeeId from request parameter
            
            // Get tasks for the employee from TaskDAO
            ArrayList<Task> tasks = taskDAO.getTasksByEmployeeAndDate(employeeId, null); // Pass null for date if needed
            
            // Set the tasks list as a request attribute to be accessed in the JSP
            request.setAttribute("tasks", tasks);

            // Forward the request to the admin_dashboard.jsp for displaying user activity
            RequestDispatcher dispatcher = request.getRequestDispatcher("admin_dashboard.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exceptions
            response.sendRedirect("error.jsp");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Handle number format exceptions
            response.sendRedirect("error.jsp");
        }
    }
}
