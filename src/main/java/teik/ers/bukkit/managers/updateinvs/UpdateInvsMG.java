package teik.ers.bukkit.managers.updateinvs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.inventories.helpers.InvChannelsMG;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.enums.FilterType;
import teik.ers.bukkit.utilities.models.enums.InventorySection;
import teik.ers.bukkit.utilities.models.enums.SortType;

public class UpdateInvsMG {

    private final InventoryMG inventoryMG;
    private final InvDataMG invDataMG;

    private final InvChannelsMG invChannelsMG;

    private final boolean isMysql;

    public UpdateInvsMG(InventoryMG inventoryMG) {
        this.inventoryMG = inventoryMG;
        this.invDataMG = inventoryMG.invDataMG;

        this.invChannelsMG = inventoryMG.invChannelsMG;

        this.isMysql = inventoryMG.isMysql;
    }

    public void sendUpdateInvs(Player player){
        if(isMysql){
            invChannelsMG.sendUpdateCall(player);
            return;
        }
        checkOpenInventories();
    }

    public void checkOpenInventories(){
        for(Player player : Bukkit.getOnlinePlayers()){
            InventoryPlayer inventoryPlayer = invDataMG.getPlayerInventory(player);

            if(inventoryPlayer==null) return;

            player.closeInventory();
            int page = inventoryPlayer.getPage();
            SortType sortType = inventoryPlayer.getSortType();
            FilterType filterType = inventoryPlayer.getFilterType();
            String name = inventoryPlayer.getServerName();
            InventorySection lastInvSec = inventoryPlayer.getLastInventorySection();

            switch (inventoryPlayer.getInventorySection()){
                case ReportedsMenu:
                    inventoryMG.openReportedsM(player, page, sortType, filterType, name);
                    break;

                case ServersMenu:
                    inventoryMG.openServersM(player, page,
                            lastInvSec, inventoryPlayer.getLastLastInvSec(), inventoryPlayer.getLastLastLastInvSec());
                    break;

                case DataMenu:
                    inventoryMG.openDataM(player);
                    break;

                case ReportsMenu:
                    inventoryMG.openReportsM(player, page, sortType, filterType, name);
                    break;

                case InfoReportMenu:
                    inventoryMG.openIfReportsM(player, lastInvSec);
                    break;

                case ArchivedMenu:
                    inventoryMG.openArchivedM(player, page, sortType, filterType, name,
                            lastInvSec, inventoryPlayer.getLastLastInvSec());
                    break;

                case CommentsMenu:
                    inventoryMG.openCommentsM(player, page, sortType, filterType, name,
                            lastInvSec, inventoryPlayer.getLastLastInvSec());
                    break;

                case AllReportsMenu:
                    inventoryMG.openAllReportsM(player, page, sortType, filterType, name);
                    break;

                case ReportersMenu:
                    inventoryMG.openReportersM(player, page, sortType, filterType, name);
                    break;

                case ReporterReportsMenu:
                    inventoryMG.openReporterReportsM(player, page, sortType, filterType, name);
                    break;

                case ActionsMenu:
                    inventoryMG.openActionsM(player, inventoryPlayer.getLastInventorySection(), inventoryPlayer.getLastLastInvSec());
                    break;

                case SettingsMenu:
                    inventoryMG.openSettingsM(player);
                    break;

                case NotifysMenu:
                    inventoryMG.openNotifysM(player);
                    break;
            }
        }
    }
}
