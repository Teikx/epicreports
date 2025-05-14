package teik.ers.bukkit.invs.helpermenus.serversmenu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.enums.FilterType;
import teik.ers.bukkit.utilities.models.enums.InventorySection;

import java.util.List;

public class SMClicks {
    private final InventoryMG inventoryMG;
    private final InvDataMG invDataMG;

    public SMClicks(InventoryMG inventoryMG) {
        this.inventoryMG = inventoryMG;
        this.invDataMG = inventoryMG.invDataMG;
    }

    public void ServersMenu(InventoryPlayer inventoryPlayer, int slot, ItemStack currentItem){
        Player player = inventoryPlayer.getPlayer();
        int page = inventoryPlayer.getPage();
        InventorySection lastInventorySection = inventoryPlayer.getLastInventorySection();
        InventorySection lastLastInvSec = inventoryPlayer.getLastLastInvSec();
        InventorySection lastLastLastInvSec = inventoryPlayer.getLastLastLastInvSec();

        switch (slot){
            case 36:
                //Back
                player.closeInventory();
                switch (lastInventorySection){
                    case ReportsMenu:
                        inventoryMG.openReportsM(player, 1, null, null, null);
                        break;
                    case ReportedsMenu:
                        inventoryMG.openReportedsM(player, 1, null, null, null);
                        break;
                    case AllReportsMenu:
                        inventoryMG.openAllReportsM(player, 1, null, null, null);
                        break;
                    case ReportersMenu:
                        inventoryMG.openReportersM(player, 1, null, null, null);
                        break;
                    case ReporterReportsMenu:
                        inventoryMG.openReporterReportsM(player, 1, null, null, null);
                        break;
                    case ArchivedMenu:
                        inventoryMG.openArchivedM(player, 1, null, null, null, lastLastInvSec, lastLastLastInvSec);
                        break;
                }
                break;
            case 39:
                //Back page
                if(page > 1){
                    player.closeInventory();
                    inventoryMG.openServersM(player, page-1, lastInventorySection, lastLastInvSec, lastLastLastInvSec);
                }
                break;
            case 41:
                //Next page
                if(page > (int) Math.ceil((double) invDataMG.getServersListSize() / 14)){
                    player.closeInventory();
                    inventoryMG.openServersM(player, page+1, lastInventorySection, lastLastInvSec, lastLastLastInvSec);
                }
                break;
            default:
                //Workbench
                if(currentItem.getType() != (Material.WORKBENCH)) return;
                List<String> servers = invDataMG.getServersList();
                int serverID = setSlot(slot, page);
                player.closeInventory();
                switch (lastInventorySection){
                    case ReportsMenu:
                        inventoryMG.openReportsM(player, 1, null, FilterType.Server, servers.get(serverID));
                        break;
                    case ReportedsMenu:
                        inventoryMG.openReportedsM(player, 1, null, FilterType.Server, servers.get(serverID));
                        break;
                    case AllReportsMenu:
                        inventoryMG.openAllReportsM(player, 1, null, FilterType.Server, servers.get(serverID));
                        break;
                    case ReportersMenu:
                        inventoryMG.openReportersM(player, 1, null, FilterType.Server, servers.get(serverID));
                        break;
                    case ReporterReportsMenu:
                        inventoryMG.openReporterReportsM(player, 1, null, FilterType.Server, servers.get(serverID));
                        break;
                    case ArchivedMenu:
                        inventoryMG.openArchivedM(player, 1, null, FilterType.Server, servers.get(serverID),
                                lastLastInvSec, lastLastLastInvSec);
                        break;
                }
                break;
        }
    }

    private int setSlot(int slot, int page) {
        int itemsPerPage = 14;
        int offset = (page - 1) * itemsPerPage;

        if (slot >= 10 && slot <= 16) {
            return offset + (slot - 10);
        }
        if (slot >= 19 && slot <= 25) {
            return offset + (slot - 12);
        }
        return -1;
    }
}
