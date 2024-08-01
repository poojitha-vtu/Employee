<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="employ.TaskDAO" %>
<%@ page import="employ.Task" %>
<%@ page import="java.sql.SQLException" %>
<%
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0"); // prevents caching at the proxy server

    String employeeIdStr = request.getParameter("employee_id");
    String date = request.getParameter("date");
    String startTime = request.getParameter("start_time");
    String taskName = request.getParameter("task_name");
    String endTime = request.getParameter("end_time");
    String description = request.getParameter("description");
    String durationStr = request.getParameter("duration");

    int employeeId = Integer.parseInt(employeeIdStr);
    int duration = Integer.parseInt(durationStr);

    TaskDAO taskDAO = new TaskDAO();
    
    try {
        // Check for overlapping tasks
        boolean hasOverlap = taskDAO.hasOverlappingTasks(employeeId, date, startTime, endTime, taskName);
        
        if (hasOverlap) {
            out.println("<h2>Error: There are overlapping tasks. Please adjust the task time.</h2>");
            return;
        }
        
        // Prepare the task object
        Task task = new Task(employeeId, taskName, date, startTime, endTime, description, duration);
        
        // Update the task
        taskDAO.updateTask(task);
        
        out.println("<h2>Task updated successfully!</h2>");
    } catch (SQLException e) {
        e.printStackTrace();
        out.println("<h2>Error occurred while updating task: " + e.getMessage() + "</h2>");
    }
%>
