package teik.ers.bukkit.invs.mainmenus.reportersmenu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.enums.FilterType;
import teik.ers.bukkit.utilities.models.enums.InventorySection;
import teik.ers.bukkit.utilities.models.enums.SortType;

public class RRMClicks {
    private final InventoryMG inventoryMG;
    private final InvDataMG invDataMG;

    public RRMClicks(InventoryMG inventoryMG) {
        this.inventoryMG = inventoryMG;
        this.invDataMG = inventoryMG.invDataMG;
    }

    public void ReportersMenu(InventoryPlayer inventoryPlayer, int slot, ClickType clickType, ItemStack currentItem){
        Player player = inventoryPlayer.getPlayer();
        int page = inventoryPlayer.getPage();
        SortType sortType = inventoryPlayer.getSortType();
        FilterType filterType = inventoryPlayer.getFilterType();
        String server = inventoryPlayer.getServerName();

        switch (slot){
            case 45:
                //Close
                player.closeInventory();
                break;
            case 53:
                //Sort
                SortType newSortType = SortActionsReportersMenu(sortType, clickType);
                player.closeInventory();
                inventoryMG.openReportersM(player, page, newSortType, filterType, server);
                break;
            case 48:
                //Back Page
                if(page > 1){
                    player.closeInventory();
                    inventoryMG.openReportersM(player, page-1, sortType, filterType, server);
                }
                break;
            case 50:
                //Next Page
                if(page < (int) Math.ceil((double) invDataMG.getPerPlayerReporterListSize(player) / 45)){
                    player.closeInventory();
                    inventoryMG.openReportersM(player, page+1, sortType, filterType, server);
                }
                break;
            case 49:
                //Chest
                switch (clickType){
                    //Change Filter
                    case LEFT:
                        FilterType newFilterType = getfilterTypeReportersMenu(filterType);
                        player.closeInventory();
                        inventoryMG.openReportersM(player, page, sortType, newFilterType, null);
                        break;
                    //Open Filter Config (Only server filter)
                    case SHIFT_LEFT:
                        if(!inventoryMG.isMysql) return;
                        if(!filterType.equals(FilterType.Server)) return;
                        player.closeInventory();
                        inventoryMG.openServersM(player, 1, InventorySection.ReportersMenu, null, null);
                        break;
                    //Change Inventory Type
                    case RIGHT:
                        player.closeInventory();
                        inventoryMG.openArchivedM(player, 1, null, null, null, InventorySection.Close, null);
                        break;
                }
                break;
            default:
                //Name Tag
                if(currentItem.getType() != (Material.NAME_TAG)) return;

                int reportNumber = ((page -1) * 45) + slot;
                String reporterUUID = invDataMG.getPerPlayerReporterList(player).get(reportNumber).getReporterUUID();
                invDataMG.putReporterUuidSelected(player, reporterUUID);

                player.closeInventory();
                inventoryMG.openReporterReportsM(player, 1, null, null, null);
                break;
        }
    }

    private SortType SortActionsReportersMenu(SortType sortType, ClickType clickType){
        if(clickType.isLeftClick()){
            switch (sortType) {
                case dateAZ: return SortType.dateZA;
                case dateZA: return SortType.nameAZ;
                case nameAZ: return SortType.nameZA;
                case nameZA: return SortType.amountAZ;
                case amountAZ: return SortType.amountZA;
                case amountZA: return SortType.dateAZ;
            }
        }
        switch (sortType) {
            case dateAZ: return SortType.amountZA;
            case dateZA: return SortType.dateAZ;
            case nameAZ: return SortType.dateZA;
            case nameZA: return SortType.nameAZ;
            case amountAZ: return SortType.nameZA;
            case amountZA: return SortType.amountAZ;
        }
        return null;
    }

    private FilterType getfilterTypeReportersMenu(FilterType filterType){
        switch (filterType){
            case None:
                return FilterType.Waiting;
            case Waiting:
                return FilterType.Processing;
            case Processing:
                if(inventoryMG.isMysql) return FilterType.Server;
                return FilterType.ReporterOnline;
            case Server:
                return FilterType.ReporterOnline;
            case ReporterOnline:
                return FilterType.ReporterOffline;
            case ReporterOffline:
                return FilterType.ReportedOnline;
            case ReportedOnline:
                return FilterType.ReportedOffline;
            case ReportedOffline:
                return FilterType.None;
        }
        return null;
    }
}
