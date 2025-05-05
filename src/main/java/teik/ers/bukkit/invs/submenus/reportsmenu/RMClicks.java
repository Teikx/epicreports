package teik.ers.bukkit.invs.submenus.reportsmenu;

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



public class RMClicks {
    private final InventoryMG inventoryMG;

    private final InvDataMG invDataMG;
    private final ReportMG reportMG;

    private final UpdateInvsMG updateInvsMG;

    public RMClicks(InventoryMG inventoryMG) {
        this.inventoryMG = inventoryMG;

        this.invDataMG = inventoryMG.invDataMG;
        this.reportMG = inventoryMG.reportMG;

        this.updateInvsMG = inventoryMG.updateInvsMG;
    }


    public void ReportsMenu(InventoryPlayer inventoryPlayer, int slot, ClickType clickType, ItemStack currentItem){
        Player player = inventoryPlayer.getPlayer();
        int page = inventoryPlayer.getPage();
        SortType sortType = inventoryPlayer.getSortType();
        FilterType filterType = inventoryPlayer.getFilterType();
        String server = inventoryPlayer.getServerName();

        switch (slot){
            case 45:
                //Back
                player.closeInventory();
                inventoryMG.openDataM(player);
                break;
            case 48:
                //Pag Anterior
                if(page > 1){
                    player.closeInventory();
                    inventoryMG.openReportsM(player, page-1, sortType, filterType, server);
                }
                break;
            case 50:
                //Pag Sig
                int reportListSize = invDataMG.getPerPlayerReportListSize(player);
                if(page < (int) Math.ceil((double) reportListSize / 45)){
                    player.closeInventory();
                    inventoryMG.openReportsM(player, page+1, sortType, filterType, server);
                }
                break;
            case 49:
                //Chest
                switch (clickType){
                    case LEFT:
                        //Change filter
                        FilterType newFilterType = getFilterTypeReportsMenu(filterType);
                        player.closeInventory();
                        inventoryMG.openReportsM(player, page, sortType, newFilterType, null);
                        break;
                    case SHIFT_LEFT:
                        //Open Servers Filter
                        if(!inventoryMG.isMysql) return;
                        if(!filterType.equals(FilterType.Server)) return;

                        player.closeInventory();
                        inventoryMG.openServersM(player, 1, InventorySection.ReportsMenu, null, null);
                        break;
                }
                break;
            case 53:
                //Change Sort
                SortType newSort = getSortTypeReportsMenu(sortType, clickType);
                player.closeInventory();
                inventoryMG.openReportsM(player, page, newSort, filterType, server);
                break;

            //Paper click
            default:
                if(currentItem.getType() != Material.PAPER) return;
                int reportIndex = ((page -1) * 45) + slot;
                List<Report> plReportsL = invDataMG.getPerPlayerReportList(player);
                Report report = plReportsL.get(reportIndex);
                int reportID = report.getReportID();

                switch (clickType){
                    case LEFT:
                        //Open InfoReportMenu
                        invDataMG.putReportSelected(player, reportID);
                        player.closeInventory();
                        inventoryMG.openIfReportsM(player, InventorySection.ReportsMenu);
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
                        if(plReportsL.size()>1) {
                            updateInvsMG.sendUpdateInvs(player);
                            return;
                        }
                        player.closeInventory();
                        inventoryMG.openReportedsM(player, 1, null, null, null);
                        updateInvsMG.sendUpdateInvs(player);
                        break;
                    default:
                        //Archive Report
                        reportMG.archiveReport(reportID);
                        if(plReportsL.size()>1){
                            updateInvsMG.sendUpdateInvs(player);
                            return;
                        }
                        player.closeInventory();
                        inventoryMG.openReportedsM(player, 1, null, null, null);
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

    private FilterType getFilterTypeReportsMenu(FilterType filterType){
        switch (filterType){
            case None:
                return FilterType.Waiting;
            case Waiting:
                return FilterType.Processing;
            case Processing:
                if(inventoryMG.isMysql) return FilterType.Server;
                return FilterType.None;
            case Server:
                return FilterType.None;
        }
        return null;
    }


    private SortType getSortTypeReportsMenu(SortType sortType, ClickType clickType){
        if(clickType.isLeftClick()){
            switch (sortType) {
                case dateAZ: return SortType.dateZA;
                case dateZA: return SortType.dateAZ;
            }
        }
        switch (sortType) {
            case dateAZ: return SortType.dateZA;
            case dateZA: return SortType.dateAZ;
        }
        return null;
    }
}
