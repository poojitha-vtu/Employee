package employ;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // Add a new employee
    public void addEmployee(Employee employee) throws SQLException {
        String sql = "INSERT INTO Employee (employee_id, name, password, role, project) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, employee.getEmployeeId());
            stmt.setString(2, employee.getName());
            stmt.setString(3, employee.getPassword());
            stmt.setString(4, employee.getRole());
            stmt.setString(5, employee.getProject());
            stmt.executeUpdate();
        }
    }

    // Get an employee by ID
    public Employee getEmployeeById(int employeeId) throws SQLException {
        String sql = "SELECT * FROM Employee WHERE employee_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Employee(rs.getInt("employee_id"), rs.getString("name"), rs.getString("password"), rs.getString("role"), rs.getString("project"));
                }
            }
        }
        return null;
    }

    // Get all employees
    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employee";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                employees.add(new Employee(rs.getInt("employee_id"), rs.getString("name"), rs.getString("password"), rs.getString("role"), rs.getString("project")));
            }
        }
        return employees;
    }

    // Update employee details
    public void updateEmployee(Employee employee) throws SQLException {
        String sql = "UPDATE Employee SET name = ?, password = ?, role = ?, project = ? WHERE employee_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getPassword());
            stmt.setString(3, employee.getRole());
            stmt.setString(4, employee.getProject());
            stmt.setInt(5, employee.getEmployeeId());
            stmt.executeUpdate();
        }
    }

    // Delete an employee
    public void deleteEmployee(int employeeId) throws SQLException {
        String sql = "DELETE FROM Employee WHERE employee_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            stmt.executeUpdate();
        }
    }
    public boolean validateEmployee(String employeeId, String password) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Employee WHERE employee_id = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employeeId);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                rs.next();
                int count = rs.getInt(1);
                return count > 0; // Return true if credentials are valid
            }
        }
    }
}
