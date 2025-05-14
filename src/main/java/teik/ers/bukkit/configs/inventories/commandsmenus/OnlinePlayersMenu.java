package teik.ers.bukkit.configs.inventories.commandsmenus;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;

import java.util.List;

public class OnlinePlayersMenu {
    private final CustomConfig inventoryFile;

    private String menuTitle, onlinePlayerTitle,chestTitle;
    private List<String> onlinePlayerLore, chestLore;

    public OnlinePlayersMenu(EpicReports plugin) {
        inventoryFile = new CustomConfig("online-players-menu.yml", "inventories", "commandsMenus", plugin, false);
        inventoryFile.registerConfig();
        loadInventory();
    }

    private void loadInventory() {
        FileConfiguration inventory = inventoryFile.getConfig();
        this.menuTitle = convertColor(inventory.getString("MenuTitle"));
        this.onlinePlayerTitle = convertColor(inventory.getString("OnlinePlayer.Title"));
        this.chestTitle = convertColor(inventory.getString("Chest.Title"));
        this.onlinePlayerLore = inventory.getStringList("OnlinePlayer.Lore");
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

    public String getChestTitle() {
        return chestTitle;
    }

    public String getOnlinePlayerTitle() {
        return onlinePlayerTitle;
    }

    public List<String> getChestLore() {
        return chestLore;
    }

    public List<String> getOnlinePlayerLore() {
        return onlinePlayerLore;
    }
}
