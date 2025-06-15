package teik.ers.bukkit.configs.inventories.commandsmenus;


import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import java.util.Optional;

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

                Optional<XMaterial> xMat = XMaterial.matchXMaterial(id);

                if (!xMat.isPresent() || xMat.get().parseItem() == null) {
                    Bukkit.getConsoleSender().sendMessage(convertColor(
                            "&b[&fEpicReports&b]&c Invalid material: &f" + id + "&c in &e" + key + "on predefined-reports-menu.yml!"
                    ));
                    continue;
                }

                String name = convertColor(inventory.getString(path + "item_name"));
                List<String> lore = inventory.getStringList(path + "item_lore");
                lore.replaceAll(this::convertColor);
                String reason = inventory.getString(path + "reason");
                String permissionStr = inventory.getString(path + "permission");
                ItemStack item = xMat.get().parseItem();
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(name);
                itemMeta.setLore(lore);
                item.setItemMeta(itemMeta);
                Permission permission = new Permission(permissionStr);
                PredefinedReport predefinedReport = new PredefinedReport(name, lore, reason, permission, item);
                this.predefinedReportList.add(predefinedReport);
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(convertColor(
                        "&b[&fEpicReports&b]&c Invalid material in: &e" + key + "&c on predefined-reports-menu.yml!"
                ));
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
