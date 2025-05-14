package teik.ers.bukkit.configs.inventories.settingmenus;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;

import java.util.List;

public class NotifysMenu {
    private final CustomConfig inventoryFile;

    private String menuTitle, enabledTitle, disabledTitle, notifyReportsMainTitle, notifyReportsMsgNotifyTitle,
            notifyReportsTitleNotifyTitle, notifyReportsSoundNotifyTitle, NotifyProcessedReportsMainTitle,
            NotifyProcessedReportsMsgNotifyTitle, NotifyProcessedReportsTitleNotifyTitle,
            NotifyProcessedReportsSoundNotifyTitle, saveConfigTitle, resetConfigTitle;;
    private List<String> enabledLore, disabledLore, notifyReportsMainLore, notifyReportsMsgNotifyLore,
            notifyReportsTitleNotifyLore, notifyReportsSoundNotifyLore, NotifyProcessedReportsMainLore,
            NotifyProcessedReportsMsgNotifyLore, NotifyProcessedReportsTitleNotifyLore,
            NotifyProcessedReportsSoundNotifyLore, saveConfigLore, resetConfigLore;;

    public NotifysMenu(EpicReports plugin) {
        inventoryFile = new CustomConfig("notifys-menu.yml", "inventories", "settingsMenu", plugin, false);
        inventoryFile.registerConfig();
        loadInventory();
    }

    private void loadInventory() {
        FileConfiguration inventory = inventoryFile.getConfig();
        this.menuTitle = convertColor(inventory.getString("MenuTitle"));
        this.enabledTitle = convertColor(inventory.getString("Enabled.Title"));
        this.enabledLore = inventory.getStringList("Enabled.Lore");
        this.disabledTitle = convertColor(inventory.getString("Disabled.Title"));
        this.disabledLore = inventory.getStringList("Disabled.Lore");
        this.notifyReportsMainTitle = convertColor(inventory.getString("NotifyReports.Main.Title"));
        this.notifyReportsMainLore = inventory.getStringList("NotifyReports.Main.Lore");
        this.notifyReportsMsgNotifyTitle = convertColor(inventory.getString("NotifyReports.MsgNotify.Title"));
        this.notifyReportsMsgNotifyLore = inventory.getStringList("NotifyReports.MsgNotify.Lore");
        this.notifyReportsTitleNotifyTitle = convertColor(inventory.getString("NotifyReports.TitleNotify.Title"));
        this.notifyReportsTitleNotifyLore = inventory.getStringList("NotifyReports.TitleNotify.Lore");
        this.notifyReportsSoundNotifyTitle = convertColor(inventory.getString("NotifyReports.SoundNotify.Title"));
        this.notifyReportsSoundNotifyLore = inventory.getStringList("NotifyReports.SoundNotify.Lore");
        this.NotifyProcessedReportsMainTitle = convertColor(inventory.getString("NotifyProcessedReports.Main.Title"));
        this.NotifyProcessedReportsMainLore = inventory.getStringList("NotifyProcessedReports.Main.Lore");
        this.NotifyProcessedReportsMsgNotifyTitle = convertColor(inventory.getString("NotifyProcessedReports.MsgNotify.Title"));
        this.NotifyProcessedReportsMsgNotifyLore = inventory.getStringList("NotifyProcessedReports.MsgNotify.Lore");
        this.NotifyProcessedReportsTitleNotifyTitle = convertColor(inventory.getString("NotifyProcessedReports.TitleNotify.Title"));
        this.NotifyProcessedReportsTitleNotifyLore = inventory.getStringList("NotifyProcessedReports.TitleNotify.Lore");
        this.NotifyProcessedReportsSoundNotifyTitle = convertColor(inventory.getString("NotifyProcessedReports.SoundNotify.Title"));
        this.NotifyProcessedReportsSoundNotifyLore = inventory.getStringList("NotifyProcessedReports.SoundNotify.Lore");
        this.saveConfigTitle = convertColor(inventory.getString("SaveConfig.Title"));
        this.saveConfigLore = inventory.getStringList("SaveConfig.Lore");
        this.resetConfigTitle = convertColor(inventory.getString("ResetConfig.Title"));
        this.resetConfigLore = inventory.getStringList("ResetConfig.Lore");
    }

    private String convertColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public void reloadConfig() {
        inventoryFile.reloadConfig();
        loadInventory();
    }

    public CustomConfig getInventoryFile() {
        return inventoryFile;
    }

    public String getMenuTitle() {
        return menuTitle;
    }
    public String getEnabledTitle() {
        return enabledTitle;
    }
    public String getDisabledTitle() {
        return disabledTitle;
    }
    public String getNotifyReportsMainTitle() {
        return notifyReportsMainTitle;
    }
    public String getNotifyReportsMsgNotifyTitle() {
        return notifyReportsMsgNotifyTitle;
    }
    public String getNotifyReportsTitleNotifyTitle() {
        return notifyReportsTitleNotifyTitle;
    }
    public String getNotifyReportsSoundNotifyTitle() {
        return notifyReportsSoundNotifyTitle;
    }
    public String getNotifyProcessedReportsMainTitle() {
        return NotifyProcessedReportsMainTitle;
    }
    public String getNotifyProcessedReportsMsgNotifyTitle() {
        return NotifyProcessedReportsMsgNotifyTitle;
    }
    public String getNotifyProcessedReportsTitleNotifyTitle() {
        return NotifyProcessedReportsTitleNotifyTitle;
    }
    public String getNotifyProcessedReportsSoundNotifyTitle() {
        return NotifyProcessedReportsSoundNotifyTitle;
    }
    public List<String> getEnabledLore() {
        return enabledLore;
    }
    public List<String> getDisabledLore() {
        return disabledLore;
    }
    public List<String> getNotifyReportsMainLore() {
        return notifyReportsMainLore;
    }
    public List<String> getNotifyReportsMsgNotifyLore() {
        return notifyReportsMsgNotifyLore;
    }
    public List<String> getNotifyReportsTitleNotifyLore() {
        return notifyReportsTitleNotifyLore;
    }
    public List<String> getNotifyReportsSoundNotifyLore() {
        return notifyReportsSoundNotifyLore;
    }
    public List<String> getNotifyProcessedReportsMainLore() {
        return NotifyProcessedReportsMainLore;
    }
    public List<String> getNotifyProcessedReportsMsgNotifyLore() {
        return NotifyProcessedReportsMsgNotifyLore;
    }
    public List<String> getNotifyProcessedReportsTitleNotifyLore() {
        return NotifyProcessedReportsTitleNotifyLore;
    }
    public List<String> getNotifyProcessedReportsSoundNotifyLore() {
        return NotifyProcessedReportsSoundNotifyLore;
    }
    public String getSaveConfigTitle() {
        return saveConfigTitle;
    }
    public String getResetConfigTitle() {
        return resetConfigTitle;
    }
    public List<String> getSaveConfigLore() {
        return saveConfigLore;
    }
    public List<String> getResetConfigLore() {
        return resetConfigLore;
    }
}
