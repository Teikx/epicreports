package teik.ers.global.models.objects;

import teik.ers.global.models.Process;

import java.time.LocalDateTime;

public class Report {
    private final String Reason, Date;
    private String
            ReporterUUID,
            ReportedUUID,
            ReporterLocation = " ",
            ReportedLocation = " ",
            ReporterServer = " ",
            ReportedServer = " ",
            ReportedGamemode = " ",
            ReporterGamemode = " ";
    private PlayerOBJ reportedPlayer, reporterPlayer;
    private int ReportedHealth = 20, ReporterHealth = 20, reportID, reportsSize;
    private LocalDateTime dateTime;
    private Process process = Process.Waiting;

    public Report(String reason, String date) {
        Reason = reason;
        Date = date;
    }

    public String getReason() {
        return Reason;
    }

    public String getDate() {
        return Date;
    }

    public void setReportedPlayer(PlayerOBJ reportedPlayer) {
        this.reportedPlayer = reportedPlayer;
    }

    public PlayerOBJ getReportedPlayer() {
        return reportedPlayer;
    }

    public void setReporterPlayer(PlayerOBJ reporterPlayer) {
        this.reporterPlayer = reporterPlayer;
    }

    public PlayerOBJ getReporterPlayer() {
        return reporterPlayer;
    }

    public String getReporterName() {
        return reporterPlayer.getName();
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public String getReporterLocation() {
        return ReporterLocation;
    }

    public void setReporterLocation(String reporterLocation) {
        ReporterLocation = reporterLocation;
    }

    public String getReporterServer() {
        return ReporterServer;
    }

    public void setReporterServer(String reporterServer) {
        ReporterServer = reporterServer;
    }

    public String getReportedLocation() {
        return ReportedLocation;
    }

    public void setReportedLocation(String reportedLocation) {
        ReportedLocation = reportedLocation;
    }

    public String getReportedServer() {
        return ReportedServer;
    }

    public void setReportedServer(String reportedServer) {
        ReportedServer = reportedServer;
    }

    public String getReportedName() {
        return reportedPlayer.getName();
    }

    public String getReportedGamemode() {
        return ReportedGamemode;
    }

    public void setReportedGamemode(String reportedGamemode) {
        ReportedGamemode = reportedGamemode;
    }

    public String getReporterGamemode() {
        return ReporterGamemode;
    }

    public void setReporterGamemode(String reporterGamemode) {
        ReporterGamemode = reporterGamemode;
    }

    public int getReportedHealth() {
        return ReportedHealth;
    }

    public void setReportedHealth(int reportedHealth) {
        ReportedHealth = reportedHealth;
    }

    public int getReporterHealth() {
        return ReporterHealth;
    }

    public void setReporterHealth(int reporterHealth) {
        ReporterHealth = reporterHealth;
    }

    public String getReporterIP() {
        return reporterPlayer.getIp();
    }

    public String getReportedIP() {
        return reportedPlayer.getIp();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public int getReportsSize() {
        return reportsSize;
    }

    public void setReportsSize(int reportsSize) {
        this.reportsSize = reportsSize;
    }

    public boolean isArchived(){
        return process.equals(Process.Archived);
    }

    public String getReporterUUID() {
        return ReporterUUID;
    }

    public void setReporterUUID(String reporterUUID) {
        ReporterUUID = reporterUUID;
    }

    public String getReportedUUID() {
        return ReportedUUID;
    }

    public void setReportedUUID(String reportedUUID) {
        ReportedUUID = reportedUUID;
    }
}
