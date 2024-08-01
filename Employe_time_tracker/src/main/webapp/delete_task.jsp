<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="employ.TaskDAO" %>
<%@ page import="employ.Task" %>
<%@ page import="java.sql.SQLException" %>

<%
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0"); // Prevents caching at the proxy server

    // Get parameters from the request
    int employeeId = Integer.parseInt(request.getParameter("employee_id"));
    String dateStr = request.getParameter("date"); // Ensure the date is in a format suitable for your DB
    String startTime = request.getParameter("start_time");
    String endTime = request.getParameter("end_time");
    String taskName = request.getParameter("task_name");

    // Parse the date
    java.sql.Date date = null;
    try {
        date = java.sql.Date.valueOf(dateStr); // Convert dateStr to java.sql.Date
    } catch (IllegalArgumentException e) {
        out.println("<script>alert('Invalid date format: " + e.getMessage() + "'); window.history.back();</script>");
        return;
    }

    TaskDAO taskDAO = new TaskDAO();
    
    try {
        // Delete the task
        taskDAO.deleteTask(employeeId, taskName, dateStr, startTime, endTime); // Adjust as needed based on your DAO
        
        out.println("<script>alert('Task deleted successfully!'); window.history.back();</script>");
    } 
    catch (SQLException e) {
        e.printStackTrace();
        out.println("<script>alert('Error occurred while deleting task: " + e.getMessage() + "'); window.history.back();</script>");
    }
%>
