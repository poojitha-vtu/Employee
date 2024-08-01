<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="employ.Task" %>
<%@ page import="java.util.Arrays" %>
<%
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0"); // prevents caching at the proxy server

    // Retrieve tasks list from request attribute
    ArrayList<Task> tasks = (ArrayList<Task>) request.getAttribute("tasksList");
    ArrayList<String> taskNames = new ArrayList<>();
    ArrayList<Double> durations = new ArrayList<>();

    // Populate taskNames and durations lists
    if (tasks != null) {
        for (Task task : tasks) {
            taskNames.add(task.getTaskName());
            durations.add(task.getDuration()); // Ensure getDuration() returns a double
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>View Tasks</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link rel="stylesheet" type="text/css" href="./css/view.css">
</head>
<body>
    <div class="container">
        <h2>View Tasks</h2>

        <form action="ViewTasksServlet" method="get">
            <label for="timePeriod">Select Time Period:</label>
            <select id="timePeriod" name="timePeriod">
                <option value="daily">Daily</option>
                <option value="weekly">Weekly</option>
                <option value="monthly">Monthly</option>
            </select>
            <input type="hidden" name="employeeId" value="<%= session.getAttribute("employeeId") %>">
            <input type="submit" value="Submit">
        </form>

        <div id="tasksContainer">
            <% if (tasks != null && !tasks.isEmpty()) { %>
                <table>
                    <tr>
                        <th>Task Name</th>
                        <th>Duration (hours)</th>
                    </tr>
                    <% for (int i = 0; i < taskNames.size(); i++) { %>
                        <tr>
                            <td><%= taskNames.get(i) %></td>
                            <td><%= durations.get(i) %></td>
                        </tr>
                    <% } %>
                </table>
                
                <% if ("daily".equals(request.getParameter("timePeriod"))) { %>
                    <div class="content">
                        <div class="charts">
                            <div class="chart-container">
                                <canvas id="taskPieChart"></canvas>
                            </div>
                            <div class="chart-container">
                                <canvas id="taskDoughnutChart"></canvas>
                            </div>
                        </div>

                        <script>
                            var taskNamesArray = <%= Arrays.toString(taskNames.toArray(new String[0])) %>;
                            var durationsArray = <%= Arrays.toString(durations.toArray(new Double[0])) %>;

                            var pieCtx = document.getElementById('taskPieChart').getContext('2d');
                            new Chart(pieCtx, {
                                type: 'pie',
                                data: {
                                    labels: taskNamesArray,
                                    datasets: [{
                                        data: durationsArray,
                                        backgroundColor: [
                                            '#FF6384',
                                            '#36A2EB',
                                            '#FFCE56',
                                            '#4BC0C0',
                                            '#9966FF'
                                        ],
                                        borderColor: '#fff',
                                        borderWidth: 2
                                    }]
                                },
                                options: {
                                    responsive: true,
                                    maintainAspectRatio: false,
                                    animation: {
                                        animateScale: true,
                                        animateRotate: true
                                    }
                                }
                            });

                            var doughnutCtx = document.getElementById('taskDoughnutChart').getContext('2d');
                            new Chart(doughnutCtx, {
                                type: 'doughnut',
                                data: {
                                    labels: taskNamesArray,
                                    datasets: [{
                                        data: durationsArray,
                                        backgroundColor: [
                                            '#FF6384',
                                            '#36A2EB',
                                            '#FFCE56',
                                            '#4BC0C0',
                                            '#9966FF'
                                        ],
                                        borderColor: '#fff',
                                        borderWidth: 2
                                    }]
                                },
                                options: {
                                    responsive: true,
                                    maintainAspectRatio: false,
                                    animation: {
                                        animateScale: true,
                                        animateRotate: true
                                    }
                                }
                            });
                        </script>

                <% } else if ("weekly".equals(request.getParameter("timePeriod")) || "monthly".equals(request.getParameter("timePeriod"))) { %>
                    <div class="content">
                        <div class="charts">
                            <div class="chart-container">
                                <canvas id="taskDoughnutChart"></canvas>
                            </div>
                            <div class="chart-container">
                                <canvas id="taskBarChart"></canvas>
                            </div>
                        </div>

                        <script>
                            var taskNamesArray = <%= Arrays.toString(taskNames.toArray(new String[0])) %>;
                            var durationsArray = <%= Arrays.toString(durations.toArray(new Double[0])) %>;

                            var doughnutCtx = document.getElementById('taskDoughnutChart').getContext('2d');
                            new Chart(doughnutCtx, {
                                type: 'doughnut',
                                data: {
                                    labels: taskNamesArray,
                                    datasets: [{
                                        data: durationsArray,
                                        backgroundColor: [
                                            '#FF6384',
                                            '#36A2EB',
                                            '#FFCE56',
                                            '#4BC0C0',
                                            '#9966FF'
                                        ],
                                        borderColor: '#fff',
                                        borderWidth: 2
                                    }]
                                },
                                options: {
                                    responsive: true,
                                    maintainAspectRatio: false,
                                    animation: {
                                        animateScale: true,
                                        animateRotate: true
                                    }
                                }
                            });

                            var barCtx = document.getElementById('taskBarChart').getContext('2d');
                            new Chart(barCtx, {
                                type: 'bar',
                                data: {
                                    labels: taskNamesArray,
                                    datasets: [{
                                        label: 'Duration (hours)',
                                        data: durationsArray,
                                        backgroundColor: '#36A2EB',
                                        borderColor: '#fff',
                                        borderWidth: 2
                                    }]
                                },
                                options: {
                                    responsive: true,
                                    maintainAspectRatio: false,
                                    scales: {
                                        y: {
                                            beginAtZero: true
                                        }
                                    }
                                }
                            });
                        </script>
                <% } %>
            <% } else { %>
                <p>No tasks found for the selected time period.</p>
            <% } %>
        </div>
    </div>
</body>
</html>
