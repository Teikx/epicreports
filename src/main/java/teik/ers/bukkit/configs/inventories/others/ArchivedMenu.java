package teik.ers.bukkit.configs.inventories.others;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;

import java.util.List;

public class ArchivedMenu {
    private final CustomConfig inventoryFile;

    private String menuTitle, enchantedBookTitle, chestTitle;
    private List<String> enchantedBookLore, chestLore;

    public ArchivedMenu(EpicReports plugin) {
        inventoryFile = new CustomConfig("archived-menu.yml", "inventories", "others", plugin, false);
        this.inventoryFile.registerConfig();
        LoadInventory();
    }

    private void LoadInventory(){
        FileConfiguration inventory = inventoryFile.getConfig();
        this.menuTitle = convertColor(inventory.getString("MenuTitle"));
        this.enchantedBookTitle = convertColor(inventory.getString("EnchantedBook.Title"));
        this.chestTitle = convertColor(inventory.getString("Chest.Title"));
        this.enchantedBookLore = inventory.getStringList("EnchantedBook.Lore");
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

    public String getEnchantedBookTitle() {
        return enchantedBookTitle;
    }

    public String getChestTitle() {
        return chestTitle;
    }

    public List<String> getEnchantedBookLore() {
        return enchantedBookLore;
    }

    public List<String> getChestLore() {
        return chestLore;
    }
}
