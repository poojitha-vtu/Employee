package employ;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/AddTaskServlet")
public class AddTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDAO taskDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        taskDAO = new TaskDAO(); // Initialize TaskDAO
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskName = request.getParameter("taskType");
        String date = request.getParameter("date");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String description = request.getParameter("description");
        int employeeId = Integer.parseInt(request.getParameter("employeeId")); // Parse employeeId from request parameter
        double duration = Double.parseDouble(request.getParameter("duration")); // Parse duration from request parameter

        try {
            // Check for overlapping tasks
            boolean hasOverlap = taskDAO.hasOverlappingTasks(employeeId, date, startTime, endTime, taskName);
            
            if (hasOverlap) {
                // Overlapping task found, redirect back to dashboard with error message
                response.sendRedirect("dashboard.jsp?error=overlap");
                return; // Exit from the method
            }
            
            // No overlapping task found, proceed with adding the task
            Task task = new Task(employeeId, taskName, date, startTime, endTime, description, duration);
            taskDAO.addTask(task);

            // Task added successfully, redirect back to dashboard
            response.sendRedirect("dashboard.jsp?success=true");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exceptions
            response.sendRedirect("dashboard.jsp?error=sql");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Handle number format exceptions
            response.sendRedirect("dashboard.jsp?error=numberformat");
        }
    }
}
