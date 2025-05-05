package teik.ers.bukkit.configs.inventories.mainmenus;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;

import java.util.List;

public class ReportersMenu {
    private final CustomConfig inventoryFile;

    private String menuTitle, reporterNameTagTitle, chestTitle;
    private List<String> reporterNameTagLore, chestLore;

    public ReportersMenu(EpicReports plugin) {
        inventoryFile = new CustomConfig("reporters-menu.yml", "inventories", "mainMenus", plugin, false);
        this.inventoryFile.registerConfig();
        LoadInventory();
    }

    private void LoadInventory(){
        FileConfiguration inventory = inventoryFile.getConfig();
        this.menuTitle = convertColor(inventory.getString("MenuTitle"));
        this.reporterNameTagTitle = convertColor(inventory.getString("ReporterNameTag.Title"));
        this.chestTitle = convertColor(inventory.getString("Chest.Title"));
        this.reporterNameTagLore = inventory.getStringList("ReporterNameTag.Lore");
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

    public String getReporterNameTagTitle() {
        return reporterNameTagTitle;
    }

    public String getChestTitle() {
        return chestTitle;
    }

    public List<String> getReporterNameTagLore() {
        return reporterNameTagLore;
    }

    public List<String> getChestLore() {
        return chestLore;
    }
}
