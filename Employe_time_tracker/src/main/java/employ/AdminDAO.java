package employ;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    // Add a new admin
    public void addAdmin(Admin admin) throws SQLException {
        String sql = "INSERT INTO Admin (username, password) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getPassword());
            stmt.executeUpdate();
        }
    }

    // Get an admin by username
    public Admin getAdminByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM Admin WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Admin(rs.getString("username"), rs.getString("password"));
                }
            }
        }
        return null;
    }

    // Get all admins
    public List<Admin> getAllAdmins() throws SQLException {
        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT * FROM Admin";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                admins.add(new Admin(rs.getString("username"), rs.getString("password")));
            }
        }
        return admins;
    }

    // Update admin details
    public void updateAdmin(Admin admin) throws SQLException {
        String sql = "UPDATE Admin SET password = ? WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, admin.getPassword());
            stmt.setString(2, admin.getUsername());
            stmt.executeUpdate();
        }
    }

    // Delete an admin
    public void deleteAdmin(String username) throws SQLException {
        String sql = "DELETE FROM Admin WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }
    public boolean validateAdmin(String username, String password) throws SQLException {
        String sql = "SELECT COUNT(*) FROM admin WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0; // Return true if at least one record is found
            }
        }
    }
}
