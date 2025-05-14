package teik.ers.bukkit.invs.submenus.datamenu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import teik.ers.bukkit.configs.LanguagesManager;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.inventories.helpers.InvChannelsMG;
import teik.ers.bukkit.managers.reportmg.ReportMG;
import teik.ers.bukkit.managers.reportmg.helpers.PlayersListMG;
import teik.ers.bukkit.managers.updateinvs.UpdateInvsMG;
import teik.ers.bukkit.utilities.UtilitiesMsgs;
import teik.ers.bukkit.utilities.UtilitiesPlayers;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.enums.ChatType;
import teik.ers.bukkit.utilities.models.enums.FilterType;
import teik.ers.bukkit.utilities.models.enums.InventorySection;
import teik.ers.global.models.objects.Locate;
import teik.ers.global.models.objects.PlayerOBJ;

public class DMClicks {
    private final InventoryMG inventoryMG;
    private final InvDataMG invDataMG;

    private final ReportMG reportMG;
    private final PlayersListMG playersListMG;

    private final InvChannelsMG invChannelsMG;
    private final UpdateInvsMG updateInvsMG;
    
    private final UtilitiesPlayers utilitiesPlayers;
    private final UtilitiesMsgs utilitiesMsgs;

    private final LanguagesManager messageManager;

    private final boolean isMysql;

    public DMClicks(InventoryMG inventoryMG) {
        this.inventoryMG = inventoryMG;
        this.invDataMG = inventoryMG.invDataMG;

        this.reportMG = inventoryMG.reportMG;
        this.playersListMG = inventoryMG.reportMG.getPlayersListMG();

        this.invChannelsMG = inventoryMG.invChannelsMG;
        this.updateInvsMG = inventoryMG.updateInvsMG;
        
        this.utilitiesPlayers = inventoryMG.utilitiesPlayers;
        this.utilitiesMsgs = inventoryMG.utilitiesMsgs;

        this.messageManager = inventoryMG.messageManager;

        this.isMysql = inventoryMG.isMysql;
    }

    public void DataMenu(InventoryPlayer inventoryPlayer, int slot, ClickType clickType){
        Player player = inventoryPlayer.getPlayer();
        String reportedUUID = invDataMG.getUuidSelected(player);
        PlayerOBJ reportedOBJ = playersListMG.getPlayerOBJ(reportedUUID);
        String reportedName = reportedOBJ.getName();
        switch(slot){
            case 10:
                //Name / UUID
                if(clickType.isLeftClick()){
                    utilitiesMsgs.suggestMsg(player, reportedName, "nick");
                }else{
                    utilitiesMsgs.suggestMsg(player, reportedUUID, "uuid");
                }
                break;
            case 12:
                //Location
                boolean isOnline = utilitiesPlayers.playerIsOnline(reportedUUID);
                if(isMysql){
                    if(isOnline){
                        if(clickType.isLeftClick()){
                            player.closeInventory();
                            invChannelsMG.teleportPlayerToReportPlayer(player, reportedName);
                        }
                        if(clickType.isRightClick()) {
                            invChannelsMG.teleportReportPlayerToPlayer(player, reportedName);
                        }
                        return;
                    }
                    if(reportedOBJ.getLastLocate() == null){
                        player.sendMessage(messageManager.getLocation_not_found());
                        return;
                    }
                    invChannelsMG.teleportPlayerToLocation(player, reportedOBJ.getLastLocation(), reportedOBJ.getLastserver());
                    return;
                }
                if(isOnline){
                    if(clickType.isLeftClick()){
                        player.closeInventory();
                        player.teleport(utilitiesPlayers.getPlayer(reportedName));
                    }
                    if(clickType.isRightClick()) {
                        utilitiesPlayers.getPlayer(reportedName).teleport(player);
                    }
                    return;
                }
                Locate locate = reportedOBJ.getLastLocate();

                if(locate == null){
                    player.sendMessage(messageManager.getLocation_not_found());
                    return;
                }

                locate.teleportToLocate(player);
                break;
            case 14:
                //ReportsMenu
                player.closeInventory();
                inventoryMG.openReportsM(player, 1, null, null, null);
                break;
            case 16:
                //ArchivedReports Menu
                player.closeInventory();
                inventoryMG.openArchivedM(player, 1, null, FilterType.ReportedName, reportedName,
                        InventorySection.DataMenu, null);
                break;
            case 20:
                //Online
                if(utilitiesPlayers.playerIsOnline(reportedUUID)){
                    if(clickType.isLeftClick()) {
                        player.closeInventory();
                        if (isMysql) {
                            invChannelsMG.teleportPlayerToReportPlayer(player, reportedName);
                            return;
                        }
                        player.teleport(utilitiesPlayers.getPlayer(reportedName));
                    }else{
                        if (isMysql) {
                            invChannelsMG.teleportReportPlayerToPlayer(player, reportedName);
                            return;
                        }
                        utilitiesPlayers.getPlayer(reportedName).teleport(player);
                    }
                }
                break;
            case 22:
                //Data Player
                switch (clickType){
                    case LEFT:
                        utilitiesMsgs.suggestMsg(player, reportedName, "nick");
                        break;
                    case RIGHT:
                        utilitiesMsgs.suggestMsg(player, reportedUUID, "uuid");
                        break;
                    case SHIFT_LEFT:
                        player.closeInventory();
                        inventoryMG.openActionsM(player, InventorySection.DataMenu, null);
                        break;
                    default:
                        utilitiesMsgs.suggestMsg(player, reportedOBJ.getIp(), "ip");
                        break;
                }
                break;
            case 24:
                //Delete Reports
                reportMG.removeAllReports(reportedUUID);
                player.closeInventory();
                inventoryMG.openReportedsM(player, 1, null, null, null);
                updateInvsMG.sendUpdateInvs(player);
                break;
            case 36:
                //Back
                player.closeInventory();
                inventoryMG.openReportedsM(player, 1, null, null, null);
                break;
            case 44:
                //Open Comments menu
                switch (clickType){
                    case LEFT:
                        //Open Comments Menu
                        player.closeInventory();
                        inventoryMG.openCommentsM(player, 1, null, FilterType.ReportedName,
                                reportedName, InventorySection.DataMenu, null);
                        break;
                    case RIGHT:
                        //Write Comment
                        player.closeInventory();
                        invDataMG.putPlayerChatType(player, ChatType.Commenting);
                        player.sendTitle(messageManager.getWrite_comment_title_title(), messageManager.getWrite_comment_title_subtitle());
                        player.sendMessage(messageManager.getWrite_comment_msg());
                        break;
                }
                break;
        }
    }
}
