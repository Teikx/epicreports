package teik.ers.bukkit.configs.inventories.helpermenus;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;

import java.util.List;

public class InfoReportMenu {
    private final CustomConfig infoReportMenuFile;

    private String menuTitle;
    private String reportedInfoTitle, reporterInfoTitle, onlinePlayerTitle, offlinePlayerTitle, nickUuidTitle, ipTitle, locationTitle,
            gamemodeTitle, healthTitle, reasonReportTitle, dateReportTitle, statusReportTitle, archiveReportTitle, deleteReportTitle,
            reportedCommentsTitle, actionsMenuTitle;
    private List<String> reportedInfoLore, reporterInfoLore, onlinePlayerLore, offlinePlayerLore, nickUuidLore, ipLore, locationLore,
            gamemodeLore, healthLore, reasonReportLore, dateReportLore, statusReportLore, archiveReportLore, deleteReportLore,
            reportedCommentsLore, actionsMenuLore;

    public InfoReportMenu(EpicReports plugin) {
        infoReportMenuFile = new CustomConfig("info-report-menu.yml", "inventories", "helperMenus", plugin, false);
        this.infoReportMenuFile.registerConfig();
        loadInfoReportMenu();
    }

    private void loadInfoReportMenu() {
        FileConfiguration config = infoReportMenuFile.getConfig();

        this.menuTitle = convertColor(config.getString("MenuTitle"));
        this.reportedInfoTitle = convertColor(config.getString("ReportedInfo.Title"));
        this.reportedInfoLore = config.getStringList("ReportedInfo.Lore");
        this.reporterInfoTitle = convertColor(config.getString("ReporterInfo.Title"));
        this.reporterInfoLore = config.getStringList("ReporterInfo.Lore");
        this.onlinePlayerTitle = convertColor(config.getString("OnlinePlayer.Title"));
        this.onlinePlayerLore = config.getStringList("OnlinePlayer.Lore");
        this.offlinePlayerTitle = convertColor(config.getString("OfflinePlayer.Title"));
        this.offlinePlayerLore = config.getStringList("OfflinePlayer.Lore");
        this.nickUuidTitle = convertColor(config.getString("NickUuid.Title"));
        this.nickUuidLore = config.getStringList("NickUuid.Lore");
        this.ipTitle = convertColor(config.getString("IP.Title"));
        this.ipLore = config.getStringList("IP.Lore");
        this.locationTitle = convertColor(config.getString("Location.Title"));
        this.locationLore = config.getStringList("Location.Lore");
        this.gamemodeTitle = convertColor(config.getString("Gamemode.Title"));
        this.gamemodeLore = config.getStringList("Gamemode.Lore");
        this.healthTitle = convertColor(config.getString("Health.Title"));
        this.healthLore = config.getStringList("Health.Lore");
        this.reasonReportTitle = convertColor(config.getString("ReasonReport.Title"));
        this.reasonReportLore =config.getStringList("ReasonReport.Lore");
        this.dateReportTitle = convertColor(config.getString("DateReport.Title"));
        this.dateReportLore =config.getStringList("DateReport.Lore");
        this.statusReportTitle = convertColor(config.getString("StatusReport.Title"));
        this.statusReportLore =config.getStringList("StatusReport.Lore");
        this.archiveReportTitle = convertColor(config.getString("ArchiveReport.Title"));
        this.archiveReportLore =config.getStringList("ArchiveReport.Lore");
        this.deleteReportTitle = convertColor(config.getString("DeleteReport.Title"));
        this.deleteReportLore = config.getStringList("DeleteReport.Lore");
        this.reportedCommentsTitle = convertColor(config.getString("ReportedComments.Title"));
        this.reportedCommentsLore = config.getStringList("ReportedComments.Lore");
        this.actionsMenuTitle = convertColor(config.getString("ActionsMenu.Title"));
        this.actionsMenuLore = config.getStringList("ActionsMenu.Lore");
    }

    private String convertColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public void reloadConfig() {
        infoReportMenuFile.reloadConfig();
        loadInfoReportMenu();
    }

    public CustomConfig getInfoReportMenuFile() {
        return infoReportMenuFile;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public String getReportedInfoTitle() {
        return reportedInfoTitle;
    }

    public String getReporterInfoTitle() {
        return reporterInfoTitle;
    }

    public String getOnlinePlayerTitle() {
        return onlinePlayerTitle;
    }

    public String getOfflinePlayerTitle() {
        return offlinePlayerTitle;
    }

    public String getNickUuidTitle() {
        return nickUuidTitle;
    }

    public String getIpTitle() {
        return ipTitle;
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    public String getGamemodeTitle() {
        return gamemodeTitle;
    }

    public String getHealthTitle() {
        return healthTitle;
    }

    public String getReasonReportTitle() {
        return reasonReportTitle;
    }

    public String getDateReportTitle() {
        return dateReportTitle;
    }

    public String getStatusReportTitle() {
        return statusReportTitle;
    }

    public String getArchiveReportTitle() {
        return archiveReportTitle;
    }

    public String getDeleteReportTitle() {
        return deleteReportTitle;
    }

    public String getReportedCommentsTitle() {
        return reportedCommentsTitle;
    }

    public String getActionsMenuTitle() {
        return actionsMenuTitle;
    }

    public List<String> getReportedInfoLore() {
        return reportedInfoLore;
    }

    public List<String> getReporterInfoLore() {
        return reporterInfoLore;
    }

    public List<String> getOnlinePlayerLore() {
        return onlinePlayerLore;
    }

    public List<String> getOfflinePlayerLore() {
        return offlinePlayerLore;
    }

    public List<String> getNickUuidLore() {
        return nickUuidLore;
    }

    public List<String> getIpLore() {
        return ipLore;
    }

    public List<String> getLocationLore() {
        return locationLore;
    }

    public List<String> getGamemodeLore() {
        return gamemodeLore;
    }

    public List<String> getHealthLore() {
        return healthLore;
    }

    public List<String> getReasonReportLore() {
        return reasonReportLore;
    }

    public List<String> getDateReportLore() {
        return dateReportLore;
    }

    public List<String> getStatusReportLore() {
        return statusReportLore;
    }

    public List<String> getArchiveReportLore() {
        return archiveReportLore;
    }

    public List<String> getDeleteReportLore() {
        return deleteReportLore;
    }

    public List<String> getReportedCommentsLore() {
        return reportedCommentsLore;
    }

    public List<String> getActionsMenuLore() {
        return actionsMenuLore;
    }
}
