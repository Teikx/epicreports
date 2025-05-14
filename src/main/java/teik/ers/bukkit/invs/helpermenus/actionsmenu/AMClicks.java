package teik.ers.bukkit.invs.helpermenus.actionsmenu;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import teik.ers.bukkit.configs.LanguagesManager;
import teik.ers.bukkit.configs.inventories.helpermenus.ActionsMenu;
import teik.ers.bukkit.managers.freeze.FreezeMG;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.inventories.helpers.InvChannelsMG;
import teik.ers.bukkit.managers.reportmg.helpers.PlayersListMG;
import teik.ers.bukkit.managers.updateinvs.UpdateInvsMG;
import teik.ers.bukkit.utilities.UtilitiesPlayers;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.enums.InventorySection;
import teik.ers.global.models.objects.PlayerOBJ;

@SuppressWarnings("CallToPrintStackTrace")
public class AMClicks {
    private final boolean isMysql;

    private final InventoryMG inventoryMG;

    private final InvDataMG invDataMG;
    private final PlayersListMG playersListMG;

    private final FreezeMG freezeMG;
    private final InvChannelsMG invChannelsMG;
    private final UpdateInvsMG updateInvsMG;

    private final UtilitiesPlayers utilitiesPlayers;

    private final ActionsMenu actionsMenu;
    private final LanguagesManager messageManager;

    public AMClicks(InventoryMG inventoryMG) {
        this.isMysql = inventoryMG.isMysql;

        this.inventoryMG = inventoryMG;

        this.invDataMG = inventoryMG.invDataMG;
        this.playersListMG = inventoryMG.reportMG.getPlayersListMG();

        this.freezeMG = inventoryMG.freezeMG;
        this.invChannelsMG = inventoryMG.invChannelsMG;
        this.updateInvsMG = inventoryMG.updateInvsMG;

        this.utilitiesPlayers = inventoryMG.utilitiesPlayers;

        this.actionsMenu = inventoryMG.configInvsMG.getActionsMenu();
        this.messageManager = inventoryMG.messageManager;
    }

    public void ActionsMenu(InventoryPlayer inventoryPlayer, int slot){
        Player player = inventoryPlayer.getPlayer();
        String reportedUUID = invDataMG.getUuidSelected(player);
        PlayerOBJ playerOBJ = playersListMG.getPlayerOBJ(reportedUUID);
        String reportedNick = playerOBJ.getName();

        InventorySection lastInvSec = inventoryPlayer.getLastInventorySection();
        InventorySection lastLastInvSec = inventoryPlayer.getLastLastInvSec();

        CommandSender console = Bukkit.getConsoleSender();

        switch (slot){
            case 27:
                //Back
                player.closeInventory();
                if(lastInvSec == InventorySection.InfoReportMenu) {
                    inventoryMG.openIfReportsM(player, lastLastInvSec);
                    return;
                }
                inventoryMG.openDataM(player);
                break;
            case 10:
                //Ban item
                String command1 = actionsMenu.getCustomCommand1().replaceAll("%reportedNick%", reportedNick);

                try{
                    Bukkit.dispatchCommand(console, command1);
                }catch (Exception e){
                    sendError(player, e);
                    return;
                }

                player.sendMessage(messageManager.getPlayer_banned());
                break;
            case 12:
                //Kick item
                String command2 = actionsMenu.getCustomCommand2().replaceAll("%reportedNick%", reportedNick);

                try{
                    Bukkit.dispatchCommand(console, command2);
                }catch (Exception e){
                    sendError(player, e);
                    return;
                }

                player.sendMessage(messageManager.getPlayer_kick());
                break;
            case 14:
                //Mute item
                String command3 = actionsMenu.getCustomCommand3().replaceAll("%reportedNick%", reportedNick);

                try{
                    Bukkit.dispatchCommand(console, command3);
                }catch (Exception e){
                    sendError(player, e);
                    return;
                }

                player.sendMessage(messageManager.getPlayer_mute());
                break;
            case 16:
                //Freeze item
                if (unfreezePlayer(reportedNick, player)) return;

                if(!utilitiesPlayers.playerIsOnline(reportedUUID)){
                    player.sendMessage(messageManager.getPlayer_freeze_not_online());
                    return;
                }

                freezePlayer(player, reportedNick);
                break;
        }
    }

    private void sendError(Player player, Exception e){
        player.sendMessage(messageManager.getPlayer_command_error());
        System.out.print("[EpicReports] Error with command: " + e.getMessage());
        e.printStackTrace();
    }

    private boolean unfreezePlayer(String reportedNick, Player player) {
        if(freezeMG.playerIsFreezed(reportedNick)){
            player.sendMessage(messageManager.getPlayer_unfreeze())
            ;
            if(isMysql){
                invChannelsMG.unfreezePlayer(player, reportedNick);
                updateInvsMG.sendUpdateInvs(player);
                return true;
            }
            freezeMG.unfreezePlayer(reportedNick);
            updateInvsMG.checkOpenInventories();
            return true;
        }
        return false;
    }

    private void freezePlayer(Player player, String reportedNick) {
        player.sendMessage(messageManager.getPlayer_freeze());

        if(isMysql){
            invChannelsMG.freezePlayer(player, reportedNick);
            updateInvsMG.sendUpdateInvs(player);
            return;
        }

        freezeMG.freezePlayer(reportedNick);
        updateInvsMG.checkOpenInventories();
    }
}
