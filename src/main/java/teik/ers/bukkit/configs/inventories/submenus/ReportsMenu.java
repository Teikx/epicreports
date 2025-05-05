package teik.ers.bukkit.configs.inventories.submenus;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;

import java.util.List;

public class ReportsMenu {
    private final CustomConfig inventoryFile;

    private String menuTitle, ReportPaperTitle, chestTitle;
    private List<String> ReportPaperLore, chestLore;

    public ReportsMenu(EpicReports plugin) {
        inventoryFile = new CustomConfig("reports-menu.yml", "inventories", "subMenus", plugin, false);
        this.inventoryFile.registerConfig();
        LoadInventory();
    }

    private void LoadInventory(){
        FileConfiguration inventory = inventoryFile.getConfig();
        this.menuTitle = convertColor(inventory.getString("MenuTitle"));
        this.ReportPaperTitle = convertColor(inventory.getString("ReportPaper.Title"));
        this.chestTitle = convertColor(inventory.getString("Chest.Title"));
        this.ReportPaperLore = inventory.getStringList("ReportPaper.Lore");
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

    public String getReportPaperTitle() {
        return ReportPaperTitle;
    }

    public String getChestTitle() {
        return chestTitle;
    }

    public List<String> getReportPaperLore() {
        return ReportPaperLore;
    }

    public List<String> getChestLore() {
        return chestLore;
    }
}
