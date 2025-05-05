package teik.ers.bukkit.configs.inventories.mainmenus;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;

import java.util.List;

public class AllReportsMenu {
    private final CustomConfig inventoryFile;

    private String menuTitle, ReportMapTitle, chestTitle;
    private List<String> ReportMapLore, chestLore;

    public AllReportsMenu(EpicReports plugin) {
        inventoryFile = new CustomConfig("all-reports-menu.yml", "inventories", "mainMenus", plugin, false);
        this.inventoryFile.registerConfig();
        LoadInventory();
    }

    private void LoadInventory(){
        FileConfiguration inventory = inventoryFile.getConfig();
        this.menuTitle = convertColor(inventory.getString("MenuTitle"));
        this.ReportMapTitle = convertColor(inventory.getString("ReportMap.Title"));
        this.chestTitle = convertColor(inventory.getString("Chest.Title"));
        this.ReportMapLore = inventory.getStringList("ReportMap.Lore");
        this.chestLore = inventory.getStringList("Chest.Lore");
    }

    private String convertColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public void reloadConfig(){
        inventoryFile.reloadConfig();
        LoadInventory();
    }

    public CustomConfig getInventoryFile() {
        return inventoryFile;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public String getReportMapTitle() {
        return ReportMapTitle;
    }

    public String getChestTitle() {
        return chestTitle;
    }

    public List<String> getReportMapLore() {
        return ReportMapLore;
    }

    public List<String> getChestLore() {
        return chestLore;
    }
}
