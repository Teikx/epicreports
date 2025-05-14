package teik.ers.bukkit.configs.inventories.others;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;

import java.util.List;

public class CommentsMenu {
    private final CustomConfig inventoryFile;

    private String menuTitle, bookTitle, chestTitle;
    private List<String> bookLore, chestLore;

    public CommentsMenu(EpicReports plugin) {
        inventoryFile = new CustomConfig("comments-menu.yml", "inventories", "others", plugin, false);
        this.inventoryFile.registerConfig();
        LoadInventory();
    }

    private void LoadInventory(){
        FileConfiguration inventory = inventoryFile.getConfig();
        this.menuTitle = convertColor(inventory.getString("MenuTitle"));
        this.bookTitle = convertColor(inventory.getString("Book.Title"));
        this.chestTitle = convertColor(inventory.getString("Chest.Title"));
        this.bookLore = inventory.getStringList("Book.Lore");
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

    public String getBookTitle() {
        return bookTitle;
    }

    public String getChestTitle() {
        return chestTitle;
    }

    public List<String> getBookLore() {
        return bookLore;
    }

    public List<String> getChestLore() {
        return chestLore;
    }
}
