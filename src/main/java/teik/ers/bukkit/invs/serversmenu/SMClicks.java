package teik.ers.bukkit.invs.serversmenu;

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
        List<String> servers = invDataMG.getServersList();
        switch (slot){
            case 36:
                player.closeInventory();
                switch (lastInventorySection){
                    case ReportedsMenu:
                        inventoryMG.openReportedsM(player, 1, null, null, null);
                        break;
                    case ReportsMenu:
                        inventoryMG.openReportsM(player, 1, null, null, null);
                        break;
                }
                break;
            case 39:
                if(page > 1){
                    player.closeInventory();
                    inventoryMG.openServersM(player, page-1, lastInventorySection);
                }
                break;
            case 41:
                if(page > (int) Math.ceil((double) servers.size() / 14)){
                    player.closeInventory();
                    inventoryMG.openServersM(player, page+1, lastInventorySection);
                }
                break;
            default:
                if(currentItem.getType() != (Material.WORKBENCH)) return;
                int serverID = setSlot(slot, page);
                player.closeInventory();
                switch (lastInventorySection){
                    case ReportedsMenu:
                        inventoryMG.openReportedsM(player, 1, null, FilterType.Server, servers.get(serverID));
                        break;
                    case ReportsMenu:
                        inventoryMG.openReportsM(player, 1, null, FilterType.Server, servers.get(serverID));
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
