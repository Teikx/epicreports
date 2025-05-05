package teik.ers.bukkit.configs.inventories.commandsmenus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;
import teik.ers.bukkit.utilities.models.PredefinedReport;

import java.util.ArrayList;
import java.util.List;

public class PredefinedReportsMenu {
    private final CustomConfig inventoryFile;
    private String menuTitle, chestTitle;
    private List<String> chestLore;
    private final List<PredefinedReport> predefinedReportList = new ArrayList<>();
    private int predefinedListSize;

    public PredefinedReportsMenu(EpicReports plugin) {
        inventoryFile = new CustomConfig("predefined-reports-menu.yml", "inventories", "commandsMenus", plugin, false);
        this.inventoryFile.registerConfig();
        LoadInventory();
    }

    private void LoadInventory() {
        FileConfiguration inventory = inventoryFile.getConfig();

        this.menuTitle = convertColor(inventory.getString("MenuTitle"));
        this.chestTitle = convertColor(inventory.getString("Chest.Title"));
        this.chestLore = inventory.getStringList("Chest.Lore");

        if (!inventory.contains("PredefinedList"))
            return;
        ConfigurationSection section = inventory.getConfigurationSection("PredefinedList");
        for (String key : section.getKeys(false)) {
            String path = "PredefinedList." + key + ".";
            try {
                String id = inventory.getString(path + "item_id");
                Material material = Material.matchMaterial(id);
                String name = convertColor(inventory.getString(path + "item_name"));
                List<String> lore = inventory.getStringList(path + "item_lore");
                lore.replaceAll(this::convertColor);
                String reason = inventory.getString(path + "reason");
                String permissionStr = inventory.getString(path + "permission");
                ItemStack item = new ItemStack(material);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(name);
                itemMeta.setLore(lore);
                item.setItemMeta(itemMeta);
                Permission permission = new Permission(permissionStr);
                PredefinedReport predefinedReport = new PredefinedReport(name, lore, reason, permission, item);
                this.predefinedReportList.add(predefinedReport);
            } catch (Exception e) {
                System.out.print("[EpicReports] Error with: " + key + " in predefined-reports-menu.yml!");
            }
        }

        predefinedListSize = predefinedReportList.size();
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

    public String getChestTitle() {
        return chestTitle;
    }

    public List<String> getChestLore() {
        return chestLore;
    }

    public List<PredefinedReport> getPredefinedReportList() {
        return predefinedReportList;
    }

    public int getPredefinedListSize() {
        return predefinedListSize;
    }
}
