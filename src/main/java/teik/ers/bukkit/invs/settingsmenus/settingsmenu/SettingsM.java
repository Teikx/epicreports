package teik.ers.bukkit.invs.settingsmenus.settingsmenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import teik.ers.bukkit.configs.ConfigManager;
import teik.ers.bukkit.configs.inventories.settingmenus.SettingsMenu;
import teik.ers.bukkit.invs.InvsUtils;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.SettingsER;

import java.util.ArrayList;
import java.util.List;

public class SettingsM {
    private final InvDataMG invDataMG;
    private final InvsUtils invsUtils;
    private final ConfigManager configManager;
    private final SettingsMenu settingsMenu;

    public SettingsM(InventoryMG inventoryMG) {
        this.invDataMG = inventoryMG.invDataMG;
        this.invsUtils = inventoryMG.invsUtils;
        this.configManager = inventoryMG.configManager;
        this.settingsMenu = inventoryMG.configInvsMG.getSettingsMenu();
    }

    public void createSettingsMenu(InventoryPlayer inventoryPlayer){
        Player player = inventoryPlayer.getPlayer();

        Inventory inventory = Bukkit.createInventory(null, 54, settingsMenu.getMenuTitle());

        invsUtils.fillWithGrayGlassPanes(inventory, 0, 45);
        invsUtils.fillWithDarkGlassPanes(inventory, 45, 54);
        invsUtils.configureCloseButton(inventory, 45, false);

        SettingsER settingsER = getSettingsER(player);

        setItems(inventory);
        setEnableds(inventory, settingsER);
        setConfigs(inventory, settingsER);

        player.openInventory(inventory);
        invDataMG.putPlayerInventory(player, inventoryPlayer);
        invDataMG.putSettingsER(player, settingsER);
    }

    private SettingsER getSettingsER(Player player) {
        SettingsER settingsER = invDataMG.getSettingsER(player);
        if(settingsER == null)
            return new SettingsER(
                    configManager.isMax_reports(),
                    configManager.isSkip_reports(),
                    configManager.isActivate_ers_menu_alias(),
                    configManager.isReportOfflinePlayers(),
                    configManager.getMax_reports_size(),
                    configManager.getMinimum_size_to_skip(),
                    configManager.getErs_menu_alias(),
                    configManager.isNotify_msg(),
                    configManager.isNotify_title(),
                    configManager.isNotify_sound(),
                    configManager.getReport_sound(),
                    configManager.isNotify_processed_msg(),
                    configManager.isNotify_processed_title(),
                    configManager.isNotify_processed_sound(),
                    configManager.getReport_processed_sound()
            );
        return settingsER;
    }

    private void setItems(Inventory inventory){
        String title;
        List<String> lore;
        ItemStack itemStack;

        //Max Reports
        title = settingsMenu.getMaxReportsTitle();
        lore = settingsMenu.getMaxReportsLore();

        itemStack = invsUtils.setItemStack(Material.EMPTY_MAP, title, lore, 0);
        inventory.setItem(11, itemStack);

        //Skip Notifys
        title = settingsMenu.getSkipNotifyReportsTitle();
        lore = settingsMenu.getSkipNotifyReportsLore();

        itemStack = invsUtils.setItemStack(Material.FLINT, title, lore, 0);
        inventory.setItem(12, itemStack);

        //Menu Alias
        title = settingsMenu.getMenuAliasTitle();
        lore = settingsMenu.getMenuAliasLore();

        itemStack = invsUtils.setItemStack(Material.CHEST, title, lore, 0);
        inventory.setItem(13, itemStack);

        //Report Offline Players
        title = settingsMenu.getReportOfflinePlayersTitle();
        lore = settingsMenu.getReportOfflinePlayersLore();

        itemStack = invsUtils.setItemStack(Material.getMaterial(289), title, lore, 0);
        inventory.setItem(14, itemStack);

        //Notifys Menu
        title = settingsMenu.getNotifysMenuTitle();
        lore = settingsMenu.getNotifysMenuLore();

        itemStack = invsUtils.setItemStack(Material.NETHER_STAR, title, lore, 0);
        inventory.setItem(19, itemStack);

        //Discord Menu
        title = settingsMenu.getDiscordMenuTitle();
        lore = settingsMenu.getDiscordMenuLore();

        itemStack = invsUtils.setItemStack(Material.getMaterial(397), title, lore, 3);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner("Berkcord");
        itemStack.setItemMeta(skullMeta);
        inventory.setItem(25, itemStack);

        //ResetPanel
        title = settingsMenu.getResetConfigTitle();
        lore = settingsMenu.getResetConfigLore();

        itemStack = invsUtils.setItemStack(Material.STAINED_GLASS_PANE, title, lore, 11);
        inventory.setItem(52, itemStack);

        //SavePanel
        title = settingsMenu.getSaveConfigTitle();
        lore = settingsMenu.getSaveConfigLore();

        itemStack = invsUtils.setItemStack(Material.STAINED_GLASS_PANE, title, lore, 5);
        inventory.setItem(53, itemStack);
    }

    private void setEnableds(Inventory inventory, SettingsER settingsER){
        String enabledTitle = settingsMenu.getEnabledTitle();
        List<String> enabledLore = settingsMenu.getEnabledLore();

        String disabledTitle = settingsMenu.getDisabledTitle();
        List<String> disabledLore = settingsMenu.getDisabledLore();

        ItemStack itemStack;
        boolean isEnabled;

        //Max Reports
        isEnabled = settingsER.isMaxReports();
        if(isEnabled){
            itemStack = invsUtils.setDyeItem(10, enabledTitle, enabledLore);
        }else{
            itemStack = invsUtils.setDyeItem(1, disabledTitle, disabledLore);
        }
        inventory.setItem(20, itemStack);

        //Skip Reports
        isEnabled = settingsER.isSkipReports();
        if(isEnabled){
            itemStack = invsUtils.setDyeItem(10, enabledTitle, enabledLore);
        }else{
            itemStack = invsUtils.setDyeItem(1, disabledTitle, disabledLore);
        }
        inventory.setItem(21, itemStack);

        //Menu Alias
        isEnabled = settingsER.isMenuAlias();
        if(isEnabled){
            itemStack = invsUtils.setDyeItem(10, enabledTitle, enabledLore);
        }else{
            itemStack = invsUtils.setDyeItem(1, disabledTitle, disabledLore);
        }
        inventory.setItem(22, itemStack);

        //Report Offline Players
        isEnabled = settingsER.isReportOfflinePl();
        if(isEnabled){
            itemStack = invsUtils.setDyeItem(10, enabledTitle, enabledLore);
        }else{
            itemStack = invsUtils.setDyeItem(1, disabledTitle, disabledLore);
        }
        inventory.setItem(23, itemStack);
    }

    private void setConfigs(Inventory inventory, SettingsER settingsER){
        String title;
        List<String> lore;
        ItemStack itemStack;

        //Size
        title = settingsMenu.getSizeTitle();
        lore = settingsMenu.getSizeLore();

            //Max Reports
            int maxReports = settingsER.getMaxReportsCount();
            itemStack = invsUtils.setItemStack(Material.STONE_BUTTON, title, lore, 0, maxReports);
            inventory.setItem(29, itemStack);

            //Skip Reports
            int skipSize = settingsER.getSkipReportsCount();
            itemStack = invsUtils.setItemStack(Material.STONE_BUTTON, title, lore, 0, skipSize);
            inventory.setItem(30, itemStack);

        //Text
        title = settingsMenu.getTextTitle();
        lore = new ArrayList<>(settingsMenu.getTextLore());
        List<String> newLore = new ArrayList<>(settingsMenu.getTextLore());

            //Menu Alias
            String MenuAlias = settingsER.getMenuAliasName();

            newLore.replaceAll(s -> s.replaceAll("%textArgument%", MenuAlias));

            itemStack = invsUtils.setItemStack(Material.NAME_TAG, title, newLore, 0);
            inventory.setItem(31, itemStack);

        //Barrier
        newLore = new ArrayList<>();
        newLore.add(" ");
        itemStack = invsUtils.setItemStack(Material.BARRIER, " ", newLore, 0);
        inventory.setItem(32, itemStack);

        //createGlassPaneItem
        itemStack = invsUtils.createGlassPaneItem(0, " ", newLore);
        inventory.setItem(15, itemStack);
        inventory.setItem(24, itemStack);
        inventory.setItem(33, itemStack);
    }
}
