package teik.ers.bukkit.invs.mainmenus.allreportsmenu;

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
import teik.ers.global.models.Process;
import teik.ers.global.models.objects.Report;

import java.util.List;

public class ARMClicks {
    private final InventoryMG inventoryMG;

    private final InvDataMG invDataMG;
    private final ReportMG reportMG;

    private final UpdateInvsMG updateInvsMG;

    public ARMClicks(InventoryMG inventoryMG) {
        this.inventoryMG = inventoryMG;

        this.invDataMG = inventoryMG.invDataMG;
        this.reportMG = inventoryMG.reportMG;

        this.updateInvsMG = inventoryMG.updateInvsMG;
    }


    public void AllReportsMenu(InventoryPlayer inventoryPlayer, int slot, ClickType clickType, ItemStack currentItem){
        Player player = inventoryPlayer.getPlayer();
        int page = inventoryPlayer.getPage();
        SortType sortType = inventoryPlayer.getSortType();
        FilterType filterType = inventoryPlayer.getFilterType();
        String server = inventoryPlayer.getServerName();

        switch (slot){
            case 45:
                //Back
                player.closeInventory();
                break;
            case 48:
                //Pag Anterior
                if(page > 1){
                    player.closeInventory();
                    inventoryMG.openAllReportsM(player, page-1, sortType, filterType, server);
                }
                break;
            case 50:
                //Pag Sig
                int size = invDataMG.getPerPlayerAllReportSize(player);
                if(page < (int) Math.ceil((double) size / 45)){
                    player.closeInventory();
                    inventoryMG.openAllReportsM(player, page+1, sortType, filterType, server);
                }
                break;
            case 49:
                //Chest
                switch (clickType){
                    case LEFT:
                        //Change filter
                        FilterType newFilterType = getFilterTypeAllReportsMenu(filterType);
                        player.closeInventory();
                        inventoryMG.openAllReportsM(player, page, sortType, newFilterType, null);
                        break;
                    case SHIFT_LEFT:
                        //Open Servers Filter
                        if(!inventoryMG.isMysql) return;
                        if(!filterType.equals(FilterType.Server)) return;

                        player.closeInventory();
                        inventoryMG.openServersM(player, 1, InventorySection.AllReportsMenu, null, null);
                        break;
                    case RIGHT:
                        //Change Menu
                        player.closeInventory();
                        inventoryMG.openReportersM(player, 1, null, null, null);
                        break;
                }
                break;
            case 53:
                //Change Sort
                SortType newSort = getSortTypeAllReportsMenu(sortType, clickType);
                player.closeInventory();
                inventoryMG.openAllReportsM(player, page, newSort, filterType, server);
                break;

            //EmptyMap click
            default:
                if(currentItem.getType() != Material.EMPTY_MAP) return;
                int reportIndex = ((page -1) * 45) + slot;
                List<Report> allReportList = invDataMG.getPerPlayerAllReportList(player);
                Report report = allReportList.get(reportIndex);
                int reportID = report.getReportID();

                switch (clickType){
                    case LEFT:
                        //Open InfoReportMenu
                        invDataMG.putUuidSelected(player, report.getReportedUUID());
                        invDataMG.putReportSelected(player, reportID);
                        player.closeInventory();
                        inventoryMG.openIfReportsM(player, InventorySection.AllReportsMenu);
                        break;
                    case SHIFT_LEFT:
                        //Update Report Process
                        Process newProcess = getNewProcess(report.getProcess());
                        reportMG.updateReportProcess(reportID, newProcess);
                        updateInvsMG.sendUpdateInvs(player);
                        break;
                    case RIGHT:
                        //Delete Report
                        reportMG.removeReport(reportID);
                        updateInvsMG.sendUpdateInvs(player);
                        break;
                    default:
                        //Archive Report
                        reportMG.archiveReport(reportID);
                        updateInvsMG.sendUpdateInvs(player);
                        break;
                }
                break;
        }
    }

    private Process getNewProcess(Process process){
        switch (process){
            case Waiting:
                return Process.Processing;
            case Processing:
                return Process.Waiting;
        }
        return Process.Waiting;
    }

    private FilterType getFilterTypeAllReportsMenu(FilterType filterType){
        switch (filterType){
            case None:
                return FilterType.Waiting;
            case Waiting:
                return FilterType.Processing;
            case Processing:
                return FilterType.ReportedOnline;
            case ReportedOnline:
                return FilterType.ReportedOffline;
            case ReportedOffline:
                return FilterType.ReporterOnline;
            case ReporterOnline:
                return FilterType.ReporterOffline;
            case ReporterOffline:
                if(inventoryMG.isMysql) return FilterType.Server;
                return FilterType.None;
            case Server:
                return FilterType.None;
        }
        return null;
    }


    private SortType getSortTypeAllReportsMenu(SortType sortType, ClickType clickType){
        if(clickType.isLeftClick()){
            switch (sortType) {
                case dateAZ: return SortType.nameAZ;
                case nameAZ: return SortType.nameZA;
                case nameZA: return SortType.uuidAZ;
                case uuidAZ: return SortType.uuidZA;
                case uuidZA: return SortType.dateZA;
                default: return SortType.dateAZ;
            }
        }
        switch (sortType) {
            case dateAZ: return SortType.dateZA;
            case nameAZ: return SortType.dateAZ;
            case nameZA: return SortType.nameAZ;
            case uuidAZ: return SortType.nameZA;
            case uuidZA: return SortType.uuidAZ;
            default: return SortType.uuidZA;
        }
    }
}
