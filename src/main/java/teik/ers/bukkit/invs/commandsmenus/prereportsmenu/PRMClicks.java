package teik.ers.bukkit.invs.commandsmenus.prereportsmenu;

import org.bukkit.entity.Player;
import teik.ers.bukkit.configs.LanguagesManager;
import teik.ers.bukkit.configs.inventories.commandsmenus.PredefinedReportsMenu;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.inventories.helpers.InvChannelsMG;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.PredefinedReport;
import teik.ers.bukkit.utilities.models.enums.InventorySection;

public class PRMClicks {
    private final boolean isMysql;

    private final InventoryMG inventoryMG;
    private final InvDataMG invDataMG;

    private final PredefinedReportsMenu predefinedReportsMenu;
    private final InvChannelsMG invChannelsMG;

    private final LanguagesManager messageManager;

    public PRMClicks(InventoryMG inventoryMG) {
        this.isMysql = inventoryMG.isMysql;

        this.inventoryMG = inventoryMG;
        this.invDataMG = inventoryMG.invDataMG;

        this.predefinedReportsMenu = inventoryMG.configInvsMG.getPredefinedReportsMenu();
        this.invChannelsMG = inventoryMG.invChannelsMG;

        this.messageManager = inventoryMG.messageManager;
    }

    public void PredefinedReportsMenu(InventoryPlayer inventoryPlayer, int slot){
        Player player = inventoryPlayer.getPlayer();
        int page = inventoryPlayer.getPage();
        String reportedName = invDataMG.getNickSelected(player);
        InventorySection lastInvSec = inventoryPlayer.getLastInventorySection();
        switch (slot){
            case 45:
                //Close
                player.closeInventory();
                if(lastInvSec.equals(InventorySection.OnlinePlayersMenu)){
                    inventoryMG.openOnlinePlayersM(player, 1, null);
                }
                break;
            case 48:
                //Pag Anterior
                if(page > 1){
                    player.closeInventory();
                    inventoryMG.openPredefinedReportsM(player, page-1, reportedName, lastInvSec);
                }
                break;
            case 50:
                //Pag Sig
                int predefinedSIze = predefinedReportsMenu.getPredefinedListSize();
                if(page < (int) Math.ceil((double) predefinedSIze / 45)){
                    player.closeInventory();
                    inventoryMG.openPredefinedReportsM(player, page-1, reportedName, lastInvSec);
                }
                break;
            //Predefined Click
            default:
                if(slot>44) return;
                int index = ((page - 1)*45) + slot;
                if(index>=predefinedReportsMenu.getPredefinedListSize()) return;

                PredefinedReport predefinedReport = predefinedReportsMenu.getPredefinedReportList().get(index);

                if(!player.hasPermission(predefinedReport.getPermission())) {
                    player.sendMessage(messageManager.getNot_permission());
                    return;
                }

                if(isMysql){
                    invChannelsMG.reportPlayerBungee(player, reportedName, predefinedReport.getReason());
                    return;
                }

                player.performCommand("report " + reportedName + " " +  predefinedReport.getReason());
                break;
        }
    }
}
