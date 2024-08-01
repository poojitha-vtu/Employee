package employ;

public class Task {
    private int employeeId;
    private String taskName;
    private String date;
    private String startTime;
    private String endTime;
    private String description;
    private double duration; // Assuming duration is a double

    public Task(int employeeId, String taskName, String date, String startTime, String endTime, String description, double duration) {
        this.employeeId = employeeId;
        this.taskName = taskName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.duration = duration;
    }

    // Getters and Setters
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDuration() { // Ensure this returns double
        return duration;
    }

    public void setDuration(double duration) { // Ensure this accepts double
        this.duration = duration;
    }
}
