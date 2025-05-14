package teik.ers.bukkit.utilities.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comment {
    private int id;
    private final String reportedUUID, reportedName, staffUUID, staffName, comment, date;
    private final LocalDateTime dateTime;

    public Comment(int id, String reportedUUID, String reportedName, String staffUUID,
                   String staffName, String comment, String date) {
        this.id = id;
        this.reportedUUID = reportedUUID;
        this.reportedName = reportedName;
        this.staffUUID = staffUUID;
        this.staffName = staffName;
        this.comment = comment;
        this.date = date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        this.dateTime = LocalDateTime.parse(date, formatter);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getReportedUUID() {
        return reportedUUID;
    }

    public String getReportedName() {
        return reportedName;
    }

    public String getStaffUUID() {
        return staffUUID;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
