package teik.ers.bukkit.invs.settingsmenus.settingsmenu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import teik.ers.bukkit.configs.ConfigManager;
import teik.ers.bukkit.configs.LanguagesManager;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.updateinvs.UpdateInvsMG;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.SettingsER;
import teik.ers.bukkit.utilities.models.enums.ChatType;

public class STMClicks {

    private final InventoryMG inventoryMG;
    private final InvDataMG invDataMG;
    private final UpdateInvsMG updateInvsMG;
    private final ConfigManager configManager;
    private final LanguagesManager messageManager;

    private final boolean isMysql;

    public STMClicks(InventoryMG inventoryMG) {
        this.inventoryMG = inventoryMG;
        this.invDataMG = inventoryMG.invDataMG;
        this.updateInvsMG = inventoryMG.updateInvsMG;
        this.configManager = inventoryMG.configManager;
        this.messageManager = inventoryMG.messageManager;

        isMysql = inventoryMG.isMysql;
    }

    public void SettingsMenu(InventoryPlayer inventoryPlayer, int slot, ClickType clickType){
        Player player = inventoryPlayer.getPlayer();
        SettingsER settingsER = invDataMG.getSettingsER(player);

        switch (slot){
            case 45:
                //Back
                player.closeInventory();
                inventoryMG.openReportedsM(player, 1, null, null, null);
                break;
            case 19:
                //Open Notifys Menu
                player.closeInventory();
                inventoryMG.openNotifysM(player);
                break;
            case 25:
                //Open Discord Menu
                player.closeInventory();
                break;
            case 20:
                //MaxReports
                boolean isMaxReports = settingsER.isMaxReports();
                settingsER.setMaxReports(!isMaxReports);
                invDataMG.putSettingsER(player, settingsER);
                inventoryMG.openSettingsM(player);
                break;
            case 21:
                //SkipNotifys
                boolean isSkipReports = settingsER.isSkipReports();
                settingsER.setSkipReports(!isSkipReports);
                invDataMG.putSettingsER(player, settingsER);
                inventoryMG.openSettingsM(player);
                break;
            case 22:
                //CustomizeCommand
                boolean isCustomizeCommand = settingsER.isCustomizeCommand();
                settingsER.setCustomizeCommand(!isCustomizeCommand);
                configManager.setCustomizeCommand(!isCustomizeCommand);
                invDataMG.putSettingsER(player, settingsER);
                inventoryMG.openSettingsM(player);
                break;
            case 23:
                //MenuAlias
                boolean isMenuAlias = settingsER.isMenuAlias();
                settingsER.setMenuAlias(!isMenuAlias);
                invDataMG.putSettingsER(player, settingsER);
                inventoryMG.openSettingsM(player);
                break;
            case 24:
                //ReportOfflinePlayers
                if(isMysql) return;
                boolean isReportOfflinePlayers = settingsER.isReportOfflinePl();
                settingsER.setReportOfflinePl(!isReportOfflinePlayers);
                invDataMG.putSettingsER(player, settingsER);
                inventoryMG.openSettingsM(player);
                break;
            case 29:
                //MaxReports
                if(!settingsER.isMaxReports()) return;
                int maxReports = settingsER.getMaxReportsCount();
                switch (clickType){
                    case LEFT:
                        maxReports++;
                        settingsER.setMaxReportsCount(maxReports);
                        break;
                    case RIGHT:
                        if(maxReports <= 1) return;
                        maxReports--;
                        settingsER.setMaxReportsCount(maxReports);
                        break;
                }
                invDataMG.putSettingsER(player, settingsER);
                inventoryMG.openSettingsM(player);
                break;
            case 30:
                //SkipNotifys
                if(!settingsER.isSkipReports()) return;
                int skipReports = settingsER.getSkipReportsCount();
                switch (clickType){
                    case LEFT:
                        skipReports++;
                        settingsER.setSkipReportsCount(skipReports);
                        break;
                    case RIGHT:
                        if(skipReports <= 1) return;
                        skipReports--;
                        settingsER.setSkipReportsCount(skipReports);
                        break;
                }
                invDataMG.putSettingsER(player, settingsER);
                inventoryMG.openSettingsM(player);
                break;
            case 31:
                //CustomizeCommand
                if(!settingsER.isCustomizeCommand()) return;
                invDataMG.putPlayerChatType(player, ChatType.CustomizeCommand);
                player.closeInventory();
                player.sendTitle(messageManager.getWrite_text_title_title(), messageManager.getWrite_text_title_subtitle());
                player.sendMessage(messageManager.getWrite_text_msg());
                break;
            case 32:
                //MenuAlias
                if(!settingsER.isMenuAlias()) return;
                invDataMG.putPlayerChatType(player, ChatType.MenuAlias);
                player.closeInventory();
                player.sendTitle(messageManager.getWrite_text_title_title(), messageManager.getWrite_text_title_subtitle());
                player.sendMessage(messageManager.getWrite_text_msg());
                break;
            case 52:
                //Reset Config
                invDataMG.removeSettingsER(player);
                player.closeInventory();
                inventoryMG.openSettingsM(player);
                break;
            case 53:
                //Save Config
                configManager.setMax_reports(settingsER.isMaxReports());
                configManager.setSkip_reports(settingsER.isSkipReports());
                configManager.setCustomizeCommand(settingsER.isCustomizeCommand());
                configManager.setActivate_ers_menu_alias(settingsER.isMenuAlias());
                configManager.setReportOfflinePlayers(settingsER.isReportOfflinePl());
                configManager.setMax_reports_size(settingsER.getMaxReportsCount());
                configManager.setMinimum_size_to_skip(settingsER.getSkipReportsCount());
                configManager.setCustomizedCommand(settingsER.getCustomCommand());
                configManager.setErs_menu_alias(settingsER.getMenuAliasName());
                configManager.setNotify_msg(settingsER.isNotifyMsg());
                configManager.saveConfig();
                invDataMG.removeSettingsER(player);
                updateInvsMG.checkOpenInventories();
                break;
        }
    }

}
