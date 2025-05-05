package teik.ers.bukkit.configs.inventories.mainmenus;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;

import java.util.List;

public class ReportedsMenu {
    private final CustomConfig inventoryFile;

    private String menuTitle, reportedBookTitle, chestTitle;
    private List<String> reportedBookLore, chestLore;

    public ReportedsMenu(EpicReports plugin) {
        inventoryFile = new CustomConfig("reporteds-menu.yml", "inventories", "mainMenus", plugin, false);
        this.inventoryFile.registerConfig();
        LoadInventory();
    }

    private void LoadInventory(){
        FileConfiguration inventory = inventoryFile.getConfig();
        this.menuTitle = convertColor(inventory.getString("MenuTitle"));
        this.reportedBookTitle = convertColor(inventory.getString("ReportedBook.Title"));
        this.chestTitle = convertColor(inventory.getString("Chest.Title"));
        this.reportedBookLore = inventory.getStringList("ReportedBook.Lore");
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

    public String getReportedBookTitle() {
        return reportedBookTitle;
    }

    public String getChestTitle() {
        return chestTitle;
    }

    public List<String> getReportedBookLore() {
        return reportedBookLore;
    }

    public List<String> getChestLore() {
        return chestLore;
    }
}
