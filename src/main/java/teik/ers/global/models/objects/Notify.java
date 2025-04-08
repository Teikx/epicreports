package teik.ers.global.models.objects;

public class Notify {
    private final String ReporterNick;
    private final String ReportedNick;
    private final int notifyID;
    private String StaffNick = null;
    private boolean isProcessed = true;

    public Notify(String reporterNick, String reportedNick, int ID) {
        ReporterNick = reporterNick;
        ReportedNick = reportedNick;
        notifyID = ID;
    }

    public String getReporterNick() {
        return ReporterNick;
    }
    public String getReportedNick() {
        return ReportedNick;
    }
    public String getStaffNick() {
        return StaffNick;
    }
    public boolean isProcessed() {
        return isProcessed;
    }
    public void setStaffNick(String staffNick) {
        StaffNick = staffNick;
    }
    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }
    public int getNotifyID() {
        return notifyID;
    }

    public boolean isUserType(){return getStaffNick() != null;
    }
}
