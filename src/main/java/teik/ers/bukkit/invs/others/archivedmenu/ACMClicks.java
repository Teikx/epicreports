package teik.ers.bukkit.invs.others.archivedmenu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.reportmg.ReportMG;
import teik.ers.bukkit.managers.updateinvs.UpdateInvsMG;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.enums.FilterType;
import teik.ers.bukkit.utilities.models.enums.InventorySection;
import teik.ers.bukkit.utilities.models.enums.SortType;
import teik.ers.global.models.objects.Report;

import java.util.List;

public class ACMClicks {
    private final InventoryMG inventoryMG;
    private final ReportMG reportMG;
    private final InvDataMG invDataMG;


    private final UpdateInvsMG updateInvsMG;

    public ACMClicks(InventoryMG inventoryMG) {
        this.inventoryMG = inventoryMG;
        this.reportMG = inventoryMG.reportMG;
        this.invDataMG = inventoryMG.invDataMG;

        this.updateInvsMG = inventoryMG.updateInvsMG;
    }

    public void ArchivedMenu(InventoryPlayer inventoryPlayer, int slot, ClickType clickType, ItemStack currentItem){
        Player player = inventoryPlayer.getPlayer();
        int page = inventoryPlayer.getPage();
        SortType sortType = inventoryPlayer.getSortType();
        FilterType filterType = inventoryPlayer.getFilterType();
        String name = inventoryPlayer.getServerName();
        InventorySection lastSection = inventoryPlayer.getLastInventorySection();
        InventorySection lastLastSection = inventoryPlayer.getLastLastInvSec();

        switch (slot){
            case 45:
                //Back
                player.closeInventory();
                switch (lastSection){
                    case InfoReportMenu:
                        inventoryMG.openIfReportsM(player, lastLastSection);
                        break;
                    case DataMenu:
                        inventoryMG.openDataM(player);
                        break;
                    default:
                        break;
                }
                break;
            case 53:
                //Sort
                SortType newSortType = SortActionsArchivedsMenu(sortType, clickType);
                player.closeInventory();
                inventoryMG.openArchivedM(player, page, newSortType, filterType, name, lastSection, lastLastSection);
                break;
            case 48:
                //Back Page
                if(page > 1){
                    player.closeInventory();
                    inventoryMG.openArchivedM(player, page-1, sortType, filterType, name, lastSection, lastLastSection);
                }
                break;
            case 50:
                //Next Page
                if(page < (int) Math.ceil((double) invDataMG.getPerPlayerArchivedListSize(player) / 45)){
                    player.closeInventory();
                    inventoryMG.openArchivedM(player, page+1, sortType, filterType, name, lastSection, lastLastSection);
                }
                break;
            case 49:
                //Chest
                switch (clickType){
                    //Change Filter
                    case LEFT:
                        FilterType newFilterType = getfilterTypeArchivedsMenu(filterType);
                        player.closeInventory();
                        inventoryMG.openArchivedM(player, page, sortType, newFilterType, null, lastSection, lastLastSection);
                        break;
                    //Open Filter Config (Only server filter)
                    case SHIFT_LEFT:
                        if(!inventoryMG.isMysql) return;
                        if(!filterType.equals(FilterType.Server)) return;
                        player.closeInventory();
                        inventoryMG.openServersM(player, 1, InventorySection.ArchivedMenu, lastSection, lastLastSection);
                        break;
                    //Change Menu
                    case RIGHT:
                        player.closeInventory();
                        inventoryMG.openCommentsM(player, 1, null, null, null, InventorySection.Close, null);
                        break;
                }
                break;
            default:
                //Enchanted Book
                if(currentItem.getType() != (Material.ENCHANTED_BOOK)) return;
                int reportNumber = ((page -1) * 45) + slot;

                List<Report> archivedList = invDataMG.getPerPlayerArchivedList(player);
                Report archivedReport = archivedList.get(reportNumber);

                reportMG.removeArchivedReport(archivedReport);

                updateInvsMG.sendUpdateInvs(player);
                break;
        }
    }

    private SortType SortActionsArchivedsMenu(SortType sortType, ClickType clickType){
        if(clickType.isLeftClick()){
            switch (sortType) {
                case dateAZ: return SortType.dateZA;
                case dateZA: return SortType.nameAZ;
                case nameAZ: return SortType.nameZA;
                case nameZA: return SortType.uuidAZ;
                case uuidAZ: return SortType.uuidZA;
                case uuidZA: return SortType.dateAZ;
            }
        }
        switch (sortType) {
            case dateAZ: return SortType.uuidZA;
            case dateZA: return SortType.dateAZ;
            case nameAZ: return SortType.dateZA;
            case nameZA: return SortType.nameAZ;
            case uuidAZ: return SortType.nameZA;
            case uuidZA: return SortType.uuidAZ;
        }
        return null;
    }

    private FilterType getfilterTypeArchivedsMenu(FilterType filterType){
        switch (filterType){
            case None:
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
            case ReportedName:
                return FilterType.None;
        }
        return null;
    }
}
