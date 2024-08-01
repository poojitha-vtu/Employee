<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="employ.TaskDAO, employ.Task" %>
<%@ page import="java.util.ArrayList, java.sql.Date, java.time.LocalDate, java.time.format.DateTimeFormatter" %>
<%
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0"); // Prevents caching at the proxy server

    TaskDAO taskDAO = new TaskDAO();
    ArrayList<String> taskNames = new ArrayList<>();
    ArrayList<Double> taskDurations = new ArrayList<>();
    ArrayList<Task> tasks = null; // Ensure tasks is declared here

    try {
        String employeeIdStr = session.getAttribute("employeeId").toString(); // Get employee_id from session
        int employeeId = Integer.parseInt(employeeIdStr);

        // Get today's date
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        java.sql.Date todaySqlDate = java.sql.Date.valueOf(today);

        // Fetch tasks for today
        tasks = taskDAO.getTasksByEmployeeAndDate(employeeId, todaySqlDate);

        for (Task task : tasks) {
            taskNames.add(task.getTaskName());
            taskDurations.add(task.getDuration());
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("<script>alert('Error occurred: " + e.getMessage() + "'); window.history.back();</script>");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Employee Tasks</title>
    <!-- Add your CSS styles here -->
    <link rel="stylesheet" type="text/css" href="./css/viewTasks.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <div class="content">
        <div class="container">
            <h3>Employee Tasks for Today</h3>
            <table border="1">
                <tr>
                    <th>Task Name</th>
                    <th>Date</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Description</th>
                    <th>Duration</th>
                    <th>Actions</th>
                </tr>
                <% 
                    // Ensure tasks variable is in scope and used correctly
                    if (tasks != null && !tasks.isEmpty()) {
                        for (Task task : tasks) {
                %>
                <tr>
                    <td><%= task.getTaskName() %></td>
                    <td><%= task.getDate() %></td>
                    <td><%= task.getStartTime() %></td>
                    <td><%= task.getEndTime() %></td>
                    <td><%= task.getDescription() %></td>
                    <td><%= task.getDuration() %></td>
                    <td style="display: flex;">
                        <a href="update_task.jsp?employee_id=<%= task.getEmployeeId() %>&date=<%= task.getDate() %>&start_time=<%= task.getStartTime() %>&task_name=<%= task.getTaskName() %>&end_time=<%= task.getEndTime() %>&description=<%= task.getDescription() %>&duration=<%= task.getDuration() %>">
                            <button class="update-button">Update</button>
                        </a>
                        <a href="delete_task.jsp?employee_id=<%= task.getEmployeeId() %>&date=<%= task.getDate() %>&start_time=<%= task.getStartTime() %>&end_time=<%= task.getEndTime() %>&task_name=<%= task.getTaskName() %>" onclick="return confirm('Are you sure?')">
                            <button class="delete-button">Delete</button>
                        </a>
                    </td>
                </tr>
                <% 
                        }
                    } else {
                        out.println("<tr><td colspan='7'>No tasks found for today.</td></tr>");
                    }
                %>
            </table>
        </div>

        <div class="chart-container">
            <h3>Task Distribution</h3>
            <canvas id="taskChart"></canvas>
        </div>
    </div>

    <script>
        const taskNames = <%= new com.google.gson.Gson().toJson(taskNames) %>;
        const taskDurations = <%= new com.google.gson.Gson().toJson(taskDurations) %>;

        const ctx = document.getElementById('taskChart').getContext('2d');
        const taskChart = new Chart(ctx, {
            type: 'pie',
            data: {
                labels: taskNames,
                datasets: [{
                    label: 'Task Distribution',
                    data: taskDurations,
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top',
                    },
                    title: {
                        display: true,
                        text: 'Task Distribution'
                    }
                }
            }
        });
    </script>
</body>
</html>
