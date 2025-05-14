package teik.ers.bukkit.invs.settingsmenus.notifysstmenu;

import org.bukkit.entity.Player;
import teik.ers.bukkit.configs.ConfigManager;
import teik.ers.bukkit.configs.LanguagesManager;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.updateinvs.UpdateInvsMG;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.SettingsER;
import teik.ers.bukkit.utilities.models.enums.ChatType;

public class NMClicks {
    private final InventoryMG inventoryMG;
    private final InvDataMG invDataMG;
    private final UpdateInvsMG updateInvsMG;
    private final ConfigManager configManager;
    private final LanguagesManager messageManager;

    public NMClicks(InventoryMG inventoryMG) {
        this.inventoryMG = inventoryMG;
        this.invDataMG = inventoryMG.invDataMG;
        this.updateInvsMG = inventoryMG.updateInvsMG;
        this.configManager = inventoryMG.configManager;
        this.messageManager = inventoryMG.messageManager;
    }

    public void notifysMenu(InventoryPlayer inventoryPlayer, int slot){
        Player player = inventoryPlayer.getPlayer();
        SettingsER settingsER = invDataMG.getSettingsER(player);

        switch (slot){
            case 36:
                //Back
                player.closeInventory();
                inventoryMG.openSettingsM(player);
                break;
            //NotifyReports
            case 12:
                //MsgNotify
                boolean isMsg = settingsER.isNotifyMsg();
                settingsER.setNotifyMsg(!isMsg);
                invDataMG.putSettingsER(player, settingsER);
                inventoryMG.openNotifysM(player);
                break;
            case 21:
                //TitleNotify
                boolean isTitle = settingsER.isNotifyTitle();
                settingsER.setNotifyTitle(!isTitle);
                invDataMG.putSettingsER(player, settingsER);
                inventoryMG.openNotifysM(player);
                break;
            case 29:
                //SoundNotify
                if(!settingsER.isNotifySound()) return;
                invDataMG.putPlayerChatType(player, ChatType.NotifySound);
                player.closeInventory();
                player.sendTitle(messageManager.getWrite_text_title_title(), messageManager.getWrite_text_title_subtitle());
                player.sendMessage(messageManager.getWrite_text_msg());
                break;
            case 30:
                //SoundNotifyEna
                boolean isNotifySound = settingsER.isNotifySound();
                settingsER.setNotifySound(!isNotifySound);
                invDataMG.putSettingsER(player, settingsER);
                inventoryMG.openNotifysM(player);
                break;
            //Notify Processed Reports
            case 14:
                //MsgNotify
                boolean isProcessedMsg = settingsER.isNotifyProcessedMsg();
                settingsER.setNotifyProcessedMsg(!isProcessedMsg);
                invDataMG.putSettingsER(player, settingsER);
                inventoryMG.openNotifysM(player);
                break;
            case 23:
                //TitleNotify
                boolean isProcessedTitle = settingsER.isNotifyProcessedTitle();
                settingsER.setNotifyProcessedTitle(!isProcessedTitle);
                invDataMG.putSettingsER(player, settingsER);
                inventoryMG.openNotifysM(player);
                break;
            case 33:
                //SoundNotify
                if(!settingsER.isNotifyProcessedSound()) return;
                invDataMG.putPlayerChatType(player, ChatType.ProcessedNotifySound);
                player.closeInventory();
                player.sendTitle(messageManager.getWrite_text_title_title(), messageManager.getWrite_text_title_subtitle());
                player.sendMessage(messageManager.getWrite_text_msg());
                break;
            case 32:
                //SoundNotifyEna
                boolean isProcessedNotifySound = settingsER.isNotifyProcessedSound();
                settingsER.setNotifyProcessedSound(!isProcessedNotifySound);
                invDataMG.putSettingsER(player, settingsER);
                inventoryMG.openNotifysM(player);
                break;

            case 43:
                //Reset Config
                invDataMG.removeSettingsER(player);
                player.closeInventory();
                inventoryMG.openNotifysM(player);
                break;
            case 44:
                //Save Config
                configManager.setNotify_msg(settingsER.isNotifyMsg());
                configManager.setNotify_title(settingsER.isNotifyTitle());
                configManager.setNotify_sound(settingsER.isNotifySound());
                configManager.setReport_sound(settingsER.getNotifySoundName());
                configManager.setNotify_processed_msg(settingsER.isNotifyProcessedMsg());
                configManager.setNotify_processed_title(settingsER.isNotifyProcessedTitle());
                configManager.setNotify_processed_sound(settingsER.isNotifyProcessedSound());
                configManager.setReport_processed_sound(settingsER.getNotifyProcessedSoundName());
                configManager.saveConfig();
                invDataMG.removeSettingsER(player);
                updateInvsMG.checkOpenInventories();
                break;
        }
    }
}
