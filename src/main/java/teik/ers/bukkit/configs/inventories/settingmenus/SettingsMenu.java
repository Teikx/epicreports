package teik.ers.bukkit.configs.inventories.settingmenus;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;

import java.util.List;

public class SettingsMenu {
    private final CustomConfig inventoryFile;

    private String menuTitle, enabledTitle, disabledTitle, sizeTitle, textTitle, maxReportsTitle, skipNotifyReportsTitle,
            menuAliasTitle, reportOfflinePlayersTitle, discordMenuTitle, notifysMenuTitle,
            saveConfigTitle, resetConfigTitle;
    private List<String> enabledLore, disabledLore, sizeLore, textLore, maxReportsLore, skipNotifyReportsLore,
            menuAliasLore, reportOfflinePlayersLore, discordMenuLore, notifysMenuLore,
            saveConfigLore, resetConfigLore;

    public SettingsMenu(EpicReports plugin) {
        inventoryFile = new CustomConfig("settings-menu.yml", "inventories", "settingsMenu", plugin, false);
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
        this.sizeTitle = convertColor(inventory.getString("Size.Title"));
        this.sizeLore = inventory.getStringList("Size.Lore");
        this.textTitle = convertColor(inventory.getString("Text.Title"));
        this.textLore = inventory.getStringList("Text.Lore");
        this.maxReportsTitle = convertColor(inventory.getString("MaxReports.Title"));
        this.maxReportsLore = inventory.getStringList("MaxReports.Lore");
        this.skipNotifyReportsTitle = convertColor(inventory.getString("SkipNotifyReports.Title"));
        this.skipNotifyReportsLore = inventory.getStringList("SkipNotifyReports.Lore");
        this.menuAliasTitle = convertColor(inventory.getString("MenuAlias.Title"));
        this.menuAliasLore = inventory.getStringList("MenuAlias.Lore");
        this.reportOfflinePlayersTitle = convertColor(inventory.getString("ReportOfflinePlayers.Title"));
        this.reportOfflinePlayersLore = inventory.getStringList("ReportOfflinePlayers.Lore");
        this.discordMenuTitle = convertColor(inventory.getString("DiscordMenu.Title"));
        this.discordMenuLore = inventory.getStringList("DiscordMenu.Lore");
        this.notifysMenuTitle = convertColor(inventory.getString("NotifysMenu.Title"));
        this.notifysMenuLore = inventory.getStringList("NotifysMenu.Lore");
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

    public String getSizeTitle() {
        return sizeTitle;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public String getMaxReportsTitle() {
        return maxReportsTitle;
    }

    public String getSkipNotifyReportsTitle() {
        return skipNotifyReportsTitle;
    }

    public String getMenuAliasTitle() {
        return menuAliasTitle;
    }

    public String getReportOfflinePlayersTitle() {
        return reportOfflinePlayersTitle;
    }

    public String getDiscordMenuTitle() {
        return discordMenuTitle;
    }

    public String getNotifysMenuTitle() {
        return notifysMenuTitle;
    }

    public List<String> getEnabledLore() {
        return enabledLore;
    }

    public List<String> getDisabledLore() {
        return disabledLore;
    }

    public List<String> getSizeLore() {
        return sizeLore;
    }

    public List<String> getTextLore() {
        return textLore;
    }

    public List<String> getMaxReportsLore() {
        return maxReportsLore;
    }

    public List<String> getSkipNotifyReportsLore() {
        return skipNotifyReportsLore;
    }

    public List<String> getMenuAliasLore() {
        return menuAliasLore;
    }

    public List<String> getReportOfflinePlayersLore() {
        return reportOfflinePlayersLore;
    }

    public List<String> getDiscordMenuLore() {
        return discordMenuLore;
    }

    public List<String> getNotifysMenuLore() {
        return notifysMenuLore;
    }

    public String getSaveConfigTitle() {
        return saveConfigTitle;
    }

    public List<String> getSaveConfigLore() {
        return saveConfigLore;
    }

    public String getResetConfigTitle() {
        return resetConfigTitle;
    }

    public List<String> getResetConfigLore() {
        return resetConfigLore;
    }
}
