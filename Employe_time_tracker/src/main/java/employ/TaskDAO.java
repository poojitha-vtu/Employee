package employ;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskDAO {

    // Add a new task
    public void addTask(Task task) throws SQLException {
        String sql = "INSERT INTO Tasks (employee_id, task_name, date, start_time, end_time, description, duration) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, task.getEmployeeId());
            stmt.setString(2, task.getTaskName());
            stmt.setString(3, task.getDate());
            stmt.setString(4, task.getStartTime());
            stmt.setString(5, task.getEndTime());
            stmt.setString(6, task.getDescription());
            stmt.setDouble(7, task.getDuration()); // Assuming duration is an int
            stmt.executeUpdate();
        }
    }

    // Update a task
    public void updateTask(Task task) throws SQLException {
        String sql = "UPDATE Tasks SET end_time=?, description=?, duration=? WHERE employee_id=? AND date=? AND start_time=? AND task_name=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getEndTime());
            stmt.setString(2, task.getDescription());
            stmt.setDouble(3, task.getDuration());  // Assuming duration is an int
            stmt.setInt(4, task.getEmployeeId());
            stmt.setString(5, task.getDate());
            stmt.setString(6, task.getStartTime());
            stmt.setString(7, task.getTaskName());
            stmt.executeUpdate();
        }
    }

    // Check for overlapping tasks
    public boolean hasOverlappingTasks(int employeeId, String date, String startTime, String endTime, String taskName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Tasks WHERE employee_id = ? AND date = ? AND ((start_time < ? AND end_time > ?) OR (start_time < ? AND end_time > ?) OR (start_time >= ? AND end_time <= ?)) AND task_name != ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            stmt.setString(2, date);
            stmt.setString(3, endTime);
            stmt.setString(4, startTime);
            stmt.setString(5, endTime);
            stmt.setString(6, startTime);
            stmt.setString(7, startTime);
            stmt.setString(8, endTime);
            stmt.setString(9, taskName);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                int overlapCount = rs.getInt(1);
                return overlapCount > 0;
            }
        }
    }

    // Delete a task
    public void deleteTask(int employeeId, String taskName, String date, String startTime, String endTime) throws SQLException {
        String sql = "DELETE FROM Tasks WHERE employee_id = ? AND task_name = ? AND date = ? AND start_time = ? AND end_time = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            stmt.setString(2, taskName);
            stmt.setString(3, date);
            stmt.setString(4, startTime);
            stmt.setString(5, endTime);
            stmt.executeUpdate();
        }
    }

    public ArrayList<Task> getTasksByEmployeeAndDate(int employeeId, java.sql.Date date) throws SQLException {
        ArrayList<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM Tasks WHERE employee_id = ? AND date = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            stmt.setDate(2, date);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Task task = new Task(
                        rs.getInt("employee_id"),
                        rs.getString("task_name"),
                        rs.getDate("date").toLocalDate().toString(), // Convert Date to LocalDate and then to String
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getString("description"),
                        rs.getInt("duration") // Assuming duration is stored as double in database
                    );
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

}
