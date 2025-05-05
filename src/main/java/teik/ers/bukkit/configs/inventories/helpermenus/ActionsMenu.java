package teik.ers.bukkit.configs.inventories.helpermenus;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;

import java.util.List;

public class ActionsMenu {
    private final CustomConfig inventoryFile;

    private String menuTitle, banPlayerTitle, kickPlayerTitle, freezePlayerTitle, MutePlayerTitle;
    private List<String> banPlayerLore, kickPlayerLore, freezePlayerLore, MutePlayerLore;
    private String customCommand1, customCommand2, customCommand3;

    public ActionsMenu(EpicReports plugin) {
        inventoryFile = new CustomConfig("actions-menu.yml", "inventories", "helperMenus", plugin, false);
        this.inventoryFile.registerConfig();
        LoadInventory();
    }

    private void LoadInventory(){
        FileConfiguration inventory = inventoryFile.getConfig();
        this.menuTitle = convertColor(inventory.getString("MenuTitle"));
        this.banPlayerTitle = convertColor(inventory.getString("BanPlayer.Title"));
        this.kickPlayerTitle = convertColor(inventory.getString("KickPlayer.Title"));
        this.freezePlayerTitle = convertColor(inventory.getString("FreezePlayer.Title"));
        this.MutePlayerTitle = convertColor(inventory.getString("MutePlayer.Title"));
        this.banPlayerLore = inventory.getStringList("BanPlayer.Lore");
        this.kickPlayerLore = inventory.getStringList("KickPlayer.Lore");
        this.freezePlayerLore = inventory.getStringList("FreezePlayer.Lore");
        this.MutePlayerLore = inventory.getStringList("MutePlayer.Lore");
        this.customCommand1 = inventory.getString("BanPlayer.Command");
        this.customCommand2 = inventory.getString("KickPlayer.Command");
        this.customCommand3 = inventory.getString("MutePlayer.Command");
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

    public String getBanPlayerTitle() {
        return banPlayerTitle;
    }

    public String getKickPlayerTitle() {
        return kickPlayerTitle;
    }

    public String getFreezePlayerTitle() {
        return freezePlayerTitle;
    }

    public String getMutePlayerTitle() {
        return MutePlayerTitle;
    }

    public List<String> getBanPlayerLore() {
        return banPlayerLore;
    }

    public List<String> getKickPlayerLore() {
        return kickPlayerLore;
    }

    public List<String> getFreezePlayerLore() {
        return freezePlayerLore;
    }

    public List<String> getMutePlayerLore() {
        return MutePlayerLore;
    }

    public String getCustomCommand1() {
        return customCommand1;
    }

    public String getCustomCommand2() {
        return customCommand2;
    }

    public String getCustomCommand3() {
        return customCommand3;
    }
}
