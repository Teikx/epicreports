package teik.ers.bukkit.invs.mainmenus.reportedsmenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import teik.ers.bukkit.configs.inventories.mainmenus.ReportedsMenu;
import teik.ers.bukkit.invs.InvsUtils;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InvDataUMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.inventories.helpers.InvHelperMG;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.enums.FilterType;
import teik.ers.bukkit.utilities.models.enums.SortType;
import teik.ers.global.models.objects.Report;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReportedsM {
    private final InvHelperMG invHelperMG;
    private final InvDataMG invDataMG;
    private final InvDataUMG invDataUMG;
    private final InvsUtils invsUtils;
    private final ReportedsMenu reportedsMenu;

    public ReportedsM(InventoryMG inventoryMG) {
        this.invHelperMG = inventoryMG.invHelperMG;
        this.invDataMG = inventoryMG.invDataMG;
        this.invDataUMG = inventoryMG.invDataUMG;
        this.invsUtils = inventoryMG.invsUtils;
        this.reportedsMenu = inventoryMG.configInvsMG.getReportedsMenu();
    }


    public void createReportedsMenu(InventoryPlayer inventoryPlayer){
        Player player = inventoryPlayer.getPlayer();
        SortType sortType = inventoryPlayer.getSortType();
        int page = inventoryPlayer.getPage();
        FilterType filterType = inventoryPlayer.getFilterType();
        String serverName = inventoryPlayer.getServerName();

        Inventory inventory = Bukkit.createInventory(null, 54, reportedsMenu.getMenuTitle());
        List<Report> reportedsReports = invHelperMG.getAllReportedsList(filterType, serverName);

        int totalPages = invsUtils.calculateTotalPages(reportedsReports.size());

        boolean[] pagination = invsUtils.setupPagination(page, totalPages);

        invsUtils.fillWithDarkGlassPanes(inventory, 45, 54);
        invsUtils.createPagePanels(inventory, true, pagination[0], pagination[1], sortType, 6);
        invsUtils.setChest(inventory, page, totalPages, filterType, 49,
                reportedsMenu.getChestTitle(),
                reportedsMenu.getChestLore()
        );

        SortReportedsList(reportedsReports, sortType);
        fillReportedsInventorySlots(inventory, reportedsReports, page);

        player.openInventory(inventory);

        invDataUMG.putPlayerSortType(player, sortType);
        invDataUMG.putPlayerFilterType(player, filterType);
        invDataMG.putPerPlayerReportedList(player, reportedsReports);

        invDataMG.putPlayerInventory(player, inventoryPlayer);
    }

    // SORT

    private void SortReportedsList(List<Report> sortedList, SortType sortType) {
        Comparator<Report> comparator;
        switch (sortType) {
            case dateZA: comparator = Comparator.comparing(Report::getDateTime);break;
            case nameAZ: comparator = Comparator.comparing(Report::getReportedName);break;
            case nameZA: comparator = Comparator.comparing(Report::getReportedName).reversed();break;
            case amountAZ: comparator = Comparator.comparingInt(Report::getReportsSize).reversed();break;
            case amountZA: comparator = Comparator.comparingInt(Report::getReportsSize);break;
            default: comparator = Comparator.comparing(Report::getDateTime).reversed();break;
        }
        sortedList.sort(comparator);
    }

    // UTILS

    private void fillReportedsInventorySlots(Inventory inv, List<Report> reportedsObj, int page) {
        if(reportedsObj.isEmpty()) return;
        int slotsPerPage = inv.getSize() - 9;
        int start = (page - 1) * slotsPerPage;
        int end = Math.min(start + slotsPerPage, reportedsObj.size());
        for (int i = start; i < end; i++) {
            Report report = reportedsObj.get(i);
            String title = reportedsMenu
                    .getReportedBookTitle().replaceAll("%ReportedName%", report.getReportedName());
            List<String> lore = new ArrayList<>(reportedsMenu.getReportedBookLore());

            ReplaceReportedLore(lore, report);
            ItemStack itemStack = invsUtils.setItemStack(Material.BOOK_AND_QUILL, title, lore, 0);

            inv.setItem(i - start, itemStack);
        }
    }

    private void ReplaceReportedLore(List<String> lore, Report report){
        String reportsAmount = String.valueOf(report.getReportsSize());
        String lastReportID = String.valueOf(report.getReportsSize());
        String lastReportReason = report.getReason();
        String lastReportDate = report.getDate();

        lore.replaceAll(s -> s
                .replaceAll("%ReportsAmount%", reportsAmount)
                .replaceAll("%LastReportID%", lastReportID)
                .replaceAll("%LastReportReason%", lastReportReason)
                .replaceAll("%LastReportDate%", lastReportDate));
    }
}
