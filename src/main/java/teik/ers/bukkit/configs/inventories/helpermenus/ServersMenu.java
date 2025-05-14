package teik.ers.bukkit.configs.inventories.helpermenus;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;

import java.util.List;

public class ServersMenu {
    private final CustomConfig inventoryFile;

    private String menuTitle, serverTitle, chestTitle;
    private List<String> serverLore, chestLore;

    public ServersMenu(EpicReports plugin) {
        inventoryFile = new CustomConfig("servers-menu.yml", "inventories", "helperMenus", plugin, false);
        inventoryFile.registerConfig();
        loadInventory();
    }

    private void loadInventory() {
        FileConfiguration inventory = inventoryFile.getConfig();
        this.menuTitle = convertColor(inventory.getString("MenuTitle"));
        this.serverTitle = convertColor(inventory.getString("Server.Title"));
        this.serverLore = inventory.getStringList("Server.Lore");
        this.chestTitle = convertColor(inventory.getString("Chest.Title"));
        this.chestLore = inventory.getStringList("Chest.Lore");
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

    public String getServerTitle() {
        return serverTitle;
    }

    public List<String> getServerLore() {
        return serverLore;
    }

    public String getChestTitle() {
        return chestTitle;
    }

    public List<String> getChestLore() {
        return chestLore;
    }
}