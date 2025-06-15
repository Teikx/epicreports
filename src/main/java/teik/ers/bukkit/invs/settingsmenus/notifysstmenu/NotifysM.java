package teik.ers.bukkit.invs.settingsmenus.notifysstmenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import teik.ers.bukkit.configs.ConfigManager;
import teik.ers.bukkit.configs.inventories.settingmenus.NotifysMenu;
import teik.ers.bukkit.invs.InvsUtils;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.SettingsER;

import java.util.List;

public class NotifysM {
    private final InvDataMG invDataMG;
    private final InvsUtils invsUtils;
    private final NotifysMenu notifysMenu;

    public NotifysM(InventoryMG inventoryMG) {
        this.invDataMG = inventoryMG.invDataMG;
        this.invsUtils = inventoryMG.invsUtils;
        this.notifysMenu = inventoryMG.configInvsMG.getNotifysMenu();
    }

    public void createNotifysMenu(InventoryPlayer inventoryPlayer){
        Player player = inventoryPlayer.getPlayer();

        Inventory inventory = Bukkit.createInventory(null, 45, notifysMenu.getMenuTitle());

        invsUtils.fillWithGrayGlassPanes(inventory, 0, 36);
        invsUtils.fillWithDarkGlassPanes(inventory, 36, 45);
        invsUtils.configureCloseButton(inventory, 36, false);

        SettingsER settingsER = invDataMG.getSettingsER(player);

        setItems(inventory);
        setEnableds(inventory, settingsER);

        player.openInventory(inventory);
        invDataMG.putPlayerInventory(player, inventoryPlayer);
        invDataMG.putSettingsER(player, settingsER);
    }

    private void setItems(Inventory inventory){
        String title;
        List<String> lore;
        ItemStack itemStack;

        //NotifyReports
        //Main
        title = notifysMenu.getNotifyReportsMainTitle();
        lore = notifysMenu.getNotifyReportsMainLore();

        itemStack = invsUtils.setItemStack(Material.STORAGE_MINECART, title, lore, 0);
        inventory.setItem(3, itemStack);

        //MsgNotify
        title = notifysMenu.getNotifyReportsMsgNotifyTitle();
        lore = notifysMenu.getNotifyReportsMsgNotifyLore();

        itemStack = invsUtils.setItemStack(Material.PAPER, title, lore, 0);
        inventory.setItem(11, itemStack);

        //TitleNotify
        title = notifysMenu.getNotifyReportsTitleNotifyTitle();
        lore = notifysMenu.getNotifyReportsTitleNotifyLore();

        itemStack = invsUtils.setItemStack(Material.ITEM_FRAME, title, lore, 0);
        inventory.setItem(20, itemStack);

        //SoundNotify
        title = notifysMenu.getNotifyReportsSoundNotifyTitle();
        lore = notifysMenu.getNotifyReportsSoundNotifyLore();

        itemStack = invsUtils.setItemStack(Material.FIREWORK_CHARGE, title, lore, 0);
        inventory.setItem(29, itemStack);

        //-----------------------------------------------------------------------------\\

        //Notify Processed Reports
        //Main
        title = notifysMenu.getNotifyProcessedReportsMainTitle();
        lore = notifysMenu.getNotifyProcessedReportsMainLore();

        itemStack = invsUtils.setItemStack(Material.POWERED_MINECART, title, lore, 0);
        inventory.setItem(5, itemStack);

        //MsgNotify
        title = notifysMenu.getNotifyProcessedReportsMsgNotifyTitle();
        lore = notifysMenu.getNotifyProcessedReportsMsgNotifyLore();

        itemStack = invsUtils.setItemStack(Material.PAPER, title, lore, 0);
        inventory.setItem(15, itemStack);

        //TitleNotify
        title = notifysMenu.getNotifyProcessedReportsTitleNotifyTitle();
        lore = notifysMenu.getNotifyProcessedReportsTitleNotifyLore();

        itemStack = invsUtils.setItemStack(Material.ITEM_FRAME, title, lore, 0);
        inventory.setItem(24, itemStack);

        //SoundNotify
        title = notifysMenu.getNotifyProcessedReportsSoundNotifyTitle();
        lore = notifysMenu.getNotifyProcessedReportsSoundNotifyLore();

        itemStack = invsUtils.setItemStack(Material.FIREWORK_CHARGE, title, lore, 0);
        inventory.setItem(33, itemStack);

        //ResetPanel
        title = notifysMenu.getResetConfigTitle();
        lore = notifysMenu.getResetConfigLore();

        itemStack = invsUtils.setItemStack(Material.STAINED_GLASS_PANE, title, lore, 11);
        inventory.setItem(43, itemStack);

        //SavePanel
        title = notifysMenu.getSaveConfigTitle();
        lore = notifysMenu.getSaveConfigLore();

        itemStack = invsUtils.setItemStack(Material.STAINED_GLASS_PANE, title, lore, 5);
        inventory.setItem(44, itemStack);
    }

    private void setEnableds(Inventory inventory, SettingsER settingsER){
        String enabledTitle = notifysMenu.getEnabledTitle();
        List<String> enabledLore = notifysMenu.getEnabledLore();

        String disabledTitle = notifysMenu.getDisabledTitle();
        List<String> disabledLore = notifysMenu.getDisabledLore();

        ItemStack itemStack;
        boolean isEnabled;

        //NotifyReports
        //MsgNotify
        isEnabled = settingsER.isNotifyMsg();
        if(isEnabled){
            itemStack = invsUtils.setDyeItem(10, enabledTitle, enabledLore);
        }else{
            itemStack = invsUtils.setDyeItem(1, disabledTitle, disabledLore);
        }
        inventory.setItem(12, itemStack);

        //TitleNotify
        isEnabled = settingsER.isNotifyTitle();
        if(isEnabled){
            itemStack = invsUtils.setDyeItem(10, enabledTitle, enabledLore);
        }else{
            itemStack = invsUtils.setDyeItem(1, disabledTitle, disabledLore);
        }
        inventory.setItem(21, itemStack);

        //SoundNotify
        isEnabled = settingsER.isNotifySound();
        if(isEnabled){
            itemStack = invsUtils.setDyeItem(10, enabledTitle, enabledLore);
        }else{
            itemStack = invsUtils.setDyeItem(1, disabledTitle, disabledLore);
        }
        inventory.setItem(30, itemStack);

        //-----------------------------------------------------------------------------\\

        //Notify Processed Reports
        //MsgNotify
        isEnabled = settingsER.isNotifyProcessedMsg();
        if(isEnabled){
            itemStack = invsUtils.setDyeItem(10, enabledTitle, enabledLore);
        }else{
            itemStack = invsUtils.setDyeItem(1, disabledTitle, disabledLore);
        }
        inventory.setItem(14, itemStack);

        //TitleNotify
        isEnabled = settingsER.isNotifyProcessedTitle();
        if(isEnabled){
            itemStack = invsUtils.setDyeItem(10, enabledTitle, enabledLore);
        }else{
            itemStack = invsUtils.setDyeItem(1, disabledTitle, disabledLore);
        }
        inventory.setItem(23, itemStack);

        //SoundNotify
        isEnabled = settingsER.isNotifyProcessedSound();
        if(isEnabled){
            itemStack = invsUtils.setDyeItem(10, enabledTitle, enabledLore);
        }else{
            itemStack = invsUtils.setDyeItem(1, disabledTitle, disabledLore);
        }
        inventory.setItem(32, itemStack);
    }
}
