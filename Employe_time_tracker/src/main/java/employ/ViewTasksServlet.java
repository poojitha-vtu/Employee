package employ;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ViewTasksServlet")
public class ViewTasksServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String timePeriod = request.getParameter("timePeriod");
        int employeeId = Integer.parseInt(request.getParameter("employeeId")); // Assuming you have the employeeId in session

        TaskDAO taskDAO = new TaskDAO();
        List<Task> tasks = new ArrayList<>();
        
        try {
            // Determine start date based on the selected time period
            LocalDate startDate = null;
            if ("daily".equals(timePeriod)) {
                startDate = LocalDate.now();
            } else if ("weekly".equals(timePeriod)) {
                startDate = LocalDate.now().minusDays(7);
            } else if ("monthly".equals(timePeriod)) {
                startDate = LocalDate.now().minusDays(30);
            }

            // Retrieve tasks from DAO
            tasks = taskDAO.getTasksByEmployeeAndDate(employeeId, java.sql.Date.valueOf(startDate));
            
            // Set tasks data as a request attribute
            request.setAttribute("tasksList", tasks);
            
            // Forward the request to the JSP file
            RequestDispatcher dispatcher = request.getRequestDispatcher("/view.jsp");
            dispatcher.forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SQL error occurred.");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Invalid employee ID format.");
        }
    }
}
