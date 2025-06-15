package teik.ers.bukkit.utilities.models;

public class SettingsER {
    boolean maxReports, skipReports, menuAlias, reportOfflinePl;
    int maxReportsCount, skipReportsCount;
    String menuAliasName;

    boolean notifyMsg, notifyTitle, notifySound;
    String notifySoundName;

    boolean notifyProcessedMsg, notifyProcessedTitle, notifyProcessedSound;
    String notifyProcessedSoundName;

    public SettingsER(boolean maxReports, boolean skipReports, boolean menuAlias, boolean reportOfflinePl,
                      int maxReportsCount, int skipReportsCount,
                      String menuAliasName,
                      boolean notifyMsg, boolean notifyTitle, boolean notifySound,
                      String notifySoundName,
                      boolean notifyProcessedMsg, boolean notifyProcessedTitle, boolean notifyProcessedSound,
                      String notifyProcessedSoundName) {
        this.maxReports = maxReports;
        this.skipReports = skipReports;
        this.menuAlias = menuAlias;
        this.reportOfflinePl = reportOfflinePl;
        this.maxReportsCount = maxReportsCount;
        this.skipReportsCount = skipReportsCount;
        this.menuAliasName = menuAliasName;
        this.notifyMsg = notifyMsg;
        this.notifyTitle = notifyTitle;
        this.notifySound = notifySound;
        this.notifySoundName = notifySoundName;
        this.notifyProcessedMsg = notifyProcessedMsg;
        this.notifyProcessedTitle = notifyProcessedTitle;
        this.notifyProcessedSound = notifyProcessedSound;
        this.notifyProcessedSoundName = notifyProcessedSoundName;
    }

    public boolean isMaxReports() {
        return maxReports;
    }

    public void setMaxReports(boolean maxReports) {
        this.maxReports = maxReports;
    }

    public boolean isSkipReports() {
        return skipReports;
    }

    public void setSkipReports(boolean skipReports) {
        this.skipReports = skipReports;
    }

    public boolean isMenuAlias() {
        return menuAlias;
    }

    public void setMenuAlias(boolean menuAlias) {
        this.menuAlias = menuAlias;
    }

    public boolean isReportOfflinePl() {
        return reportOfflinePl;
    }

    public void setReportOfflinePl(boolean reportOfflinePl) {
        this.reportOfflinePl = reportOfflinePl;
    }

    public int getMaxReportsCount() {
        return maxReportsCount;
    }

    public void setMaxReportsCount(int maxReportsCount) {
        this.maxReportsCount = maxReportsCount;
    }

    public int getSkipReportsCount() {
        return skipReportsCount;
    }

    public void setSkipReportsCount(int skipReportsCount) {
        this.skipReportsCount = skipReportsCount;
    }

    public String getMenuAliasName() {
        return menuAliasName;
    }

    public void setMenuAliasName(String menuAliasName) {
        this.menuAliasName = menuAliasName;
    }

    public boolean isNotifyMsg() {
        return notifyMsg;
    }

    public void setNotifyMsg(boolean notifyMsg) {
        this.notifyMsg = notifyMsg;
    }

    public boolean isNotifyTitle() {
        return notifyTitle;
    }

    public void setNotifyTitle(boolean notifyTitle) {
        this.notifyTitle = notifyTitle;
    }

    public boolean isNotifySound() {
        return notifySound;
    }

    public void setNotifySound(boolean notifySound) {
        this.notifySound = notifySound;
    }

    public String getNotifySoundName() {
        return notifySoundName;
    }

    public void setNotifySoundName(String notifySoundName) {
        this.notifySoundName = notifySoundName;
    }

    public boolean isNotifyProcessedMsg() {
        return notifyProcessedMsg;
    }

    public void setNotifyProcessedMsg(boolean notifyProcessedMsg) {
        this.notifyProcessedMsg = notifyProcessedMsg;
    }

    public boolean isNotifyProcessedTitle() {
        return notifyProcessedTitle;
    }

    public void setNotifyProcessedTitle(boolean notifyProcessedTitle) {
        this.notifyProcessedTitle = notifyProcessedTitle;
    }

    public boolean isNotifyProcessedSound() {
        return notifyProcessedSound;
    }

    public void setNotifyProcessedSound(boolean notifyProcessedSound) {
        this.notifyProcessedSound = notifyProcessedSound;
    }

    public String getNotifyProcessedSoundName() {
        return notifyProcessedSoundName;
    }

    public void setNotifyProcessedSoundName(String notifyProcessedSoundName) {
        this.notifyProcessedSoundName = notifyProcessedSoundName;
    }
}
