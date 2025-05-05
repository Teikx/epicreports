package teik.ers.bukkit.configs.inventories.submenus;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;

import java.util.List;

public class DataMenu {
    private final CustomConfig dataMenuFile;

    private String menuTitle, nickUuidTitle, locationOnlineTitle, locationOfflineTitle, reportsTitle, archivedReportsTitle, statusOnlineTitle,
            statusOfflineTitle, reportedDataTitle, deleteTitle, reportedCommentsTitle;
    private List<String> nickUuidLore, locationOnlineLore, locationOfflineLore, reportsLore, archivedReportsLore, statusOnlineLore,
            statusOfflineLore, reportedDataLore, deleteLore, reportedCommentsLore;

    public DataMenu(EpicReports plugin) {
        dataMenuFile = new CustomConfig("data-menu.yml", "inventories", "subMenus", plugin, false);
        this.dataMenuFile.registerConfig();
        loadDataMenu();
    }

    private void loadDataMenu(){
        FileConfiguration dataMenu = dataMenuFile.getConfig();
        this.menuTitle = convertColor(dataMenu.getString("MenuTitle"));
        this.nickUuidTitle = convertColor(dataMenu.getString("NickUUID.Title"));
        this.locationOnlineTitle = convertColor(dataMenu.getString("LocationOnline.Title"));
        this.locationOfflineTitle = convertColor(dataMenu.getString("LocationOffline.Title"));
        this.reportsTitle = convertColor(dataMenu.getString("Reports.Title"));
        this.archivedReportsTitle = convertColor(dataMenu.getString("ArchivedReports.Title"));
        this.statusOnlineTitle = convertColor(dataMenu.getString("StatusOnline.Title"));
        this.statusOfflineTitle = convertColor(dataMenu.getString("StatusOffline.Title"));
        this.reportedDataTitle = convertColor(dataMenu.getString("ReportedData.Title"));
        this.deleteTitle = convertColor(dataMenu.getString("Delete.Title"));
        this.reportedCommentsTitle = convertColor(dataMenu.getString("ReportedComments.Title"));

        this.nickUuidLore = dataMenu.getStringList("NickUUID.Lore");
        this.locationOnlineLore = dataMenu.getStringList("LocationOnline.Lore");
        this.locationOfflineLore = dataMenu.getStringList("LocationOffline.Lore");
        this.reportsLore = dataMenu.getStringList("Reports.Lore");
        this.archivedReportsLore = dataMenu.getStringList("ArchivedReports.Lore");
        this.statusOnlineLore = dataMenu.getStringList("StatusOnline.Lore");
        this.statusOfflineLore = dataMenu.getStringList("StatusOffline.Lore");
        this.reportedDataLore = dataMenu.getStringList("ReportedData.Lore");
        this.deleteLore = dataMenu.getStringList("Delete.Lore");
        this.reportedCommentsLore = dataMenu.getStringList("ReportedComments.Lore");
    }

    private String convertColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public void reloadConfig(){
        dataMenuFile.reloadConfig();
        loadDataMenu();
    }

    public CustomConfig getDataMenuFile() {
        return dataMenuFile;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public String getNickUuidTitle() {
        return nickUuidTitle;
    }

    public List<String> getNickUuidLore() {
        return nickUuidLore;
    }

    public String getLocationOnlineTitle() {return locationOnlineTitle;}

    public List<String> getLocationOnlineLore() {return locationOnlineLore;}

    public String getLocationOfflineTitle() {return locationOfflineTitle;}

    public List<String> getLocationOfflineLore() {return locationOfflineLore;}

    public String getReportsTitle() {
        return reportsTitle;
    }

    public List<String> getReportsLore() {
        return reportsLore;
    }

    public String getArchivedReportsTitle() {
        return archivedReportsTitle;
    }

    public List<String> getArchivedReportsLore() {
        return archivedReportsLore;
    }

    public String getStatusOnlineTitle() {
        return statusOnlineTitle;
    }

    public List<String> getStatusOnlineLore() {
        return statusOnlineLore;
    }

    public String getStatusOfflineTitle() {
        return statusOfflineTitle;
    }

    public List<String> getStatusOfflineLore() {
        return statusOfflineLore;
    }

    public String getReportedDataTitle() {
        return reportedDataTitle;
    }

    public List<String> getReportedDataLore() {
        return reportedDataLore;
    }

    public String getDeleteTitle() {
        return deleteTitle;
    }

    public List<String> getDeleteLore() {
        return deleteLore;
    }

    public String getReportedCommentsTitle() {
        return reportedCommentsTitle;
    }

    public List<String> getReportedCommentsLore() {
        return reportedCommentsLore;
    }
}
