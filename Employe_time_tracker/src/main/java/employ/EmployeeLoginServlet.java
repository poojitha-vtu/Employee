package employ;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/EmployeeLoginServlet")
public class EmployeeLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeId = request.getParameter("employee_id");
        String password = request.getParameter("password");

        EmployeeDAO employeeDAO = new EmployeeDAO();
        
        try {
            // Validate employee credentials
            boolean isValid = employeeDAO.validateEmployee(employeeId, password);
            
            if (isValid) {
                // Employee exists with provided credentials
                HttpSession session = request.getSession();
                session.setAttribute("employeeId", employeeId);
                response.sendRedirect("dashboard.jsp");
            } else {
                // Invalid credentials, redirect back to login page with error message
                response.sendRedirect("EmployeeLogin.jsp?error=1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("EmployeeLogin.jsp?error=db");
        }
    }
}
