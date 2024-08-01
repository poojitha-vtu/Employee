package employ;

public class Employee {
    private int employeeId;
    private String name;
    private String role;
    private String project;
    private String password; // Include password field

    // Constructor with all parameters
    public Employee(int employeeId, String name, String role, String project, String password) {
        this.employeeId = employeeId;
        this.name = name;
        this.role = role;
        this.project = project;
        this.password = password;
    }

    // Default constructor
    public Employee() {}

    // Getter and Setter for employeeId
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for role
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Getter and Setter for project
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
