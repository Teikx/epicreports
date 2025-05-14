package teik.ers.bukkit.invs.submenus.rrreportsmenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import teik.ers.bukkit.configs.inventories.submenus.RrReportsMenu;
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

public class RrReportsM {
    private final InvHelperMG invHelperMG;
    private final InvDataMG invDataMG;
    private final InvDataUMG invDataUMG;
    private final InvsUtils invsUtils;
    private final RrReportsMenu rrReportsMenu;

    public RrReportsM(InventoryMG inventoryMG) {
        this.invHelperMG = inventoryMG.invHelperMG;
        this.invDataMG = inventoryMG.invDataMG;
        this.invDataUMG = inventoryMG.invDataUMG;
        this.invsUtils = inventoryMG.invsUtils;
        this.rrReportsMenu = inventoryMG.configInvsMG.getRrReportsMenu();
    }

    public void createReporterReportsMenu(InventoryPlayer inventoryPlayer){
        Player player = inventoryPlayer.getPlayer();
        String reportedUUID = invDataMG.getReporterUuidSelected(player);
        SortType sortType = inventoryPlayer.getSortType();
        int page = inventoryPlayer.getPage();
        FilterType filterType = inventoryPlayer.getFilterType();
        String serverName = inventoryPlayer.getServerName();

        Inventory inventory = Bukkit.createInventory(null, 54, rrReportsMenu.getMenuTitle());

        List<Report> reportList = invHelperMG.getReporterReportsList(reportedUUID, filterType, serverName);

        int totalPages = invsUtils.calculateTotalPages(reportList.size());

        boolean[] pagination = invsUtils.setupPagination(page, totalPages);

        invsUtils.fillWithDarkGlassPanes(inventory, 45, 54);
        invsUtils.createPagePanels(inventory, false, pagination[0], pagination[1], sortType, 6);
        invsUtils.setChest(inventory, page, totalPages, filterType, 49,
                rrReportsMenu.getChestTitle(),
                rrReportsMenu.getChestLore()
        );

        SortReportersReportList(reportList, sortType);
        fillReportersReportInventorySlots(inventory, reportList, page);

        player.openInventory(inventory);

        invDataUMG.putPlayerSortTypeReporterReports(player, sortType);
        invDataUMG.putPlayerFilterTypeReporterReports(player, filterType);
        invDataMG.putPerPlayerReporterReportsList(player, reportList);

        invDataMG.putPlayerInventory(player, inventoryPlayer);
    }

    private void fillReportersReportInventorySlots(Inventory inv, List<Report> reportList, int page) {
        if(reportList.isEmpty()) return;
        int slotsPerPage = inv.getSize() - 9;
        int start = (page - 1) * slotsPerPage;
        int end = Math.min(start + slotsPerPage, reportList.size());
        for (int i = start; i < end; i++) {

            Report report = reportList.get(i);

            String title = rrReportsMenu
                    .getReportPaperTitle().replaceAll("%reason%", report.getReason());
            List<String> lore = new ArrayList<>(rrReportsMenu.getReportPaperLore());
            ReplaceReporterReportLore(lore, report);

            ItemStack itemStack = invsUtils.setItemStack(Material.PAPER, title, lore, 0);
            inv.setItem(i - start, itemStack);
        }
    }

    private void ReplaceReporterReportLore(List<String> lore, Report report){
        String reportedName = report.getReportedName();
        String reporterName = report.getReporterName();
        String reportStatus = report.getProcess().toString();
        String reportDate = report.getDate();

        lore.replaceAll(s -> s
                .replaceAll("%reportedName%", reportedName)
                .replaceAll("%reporterName%", reporterName)
                .replaceAll("%reportStatus%", reportStatus)
                .replaceAll("%reportDate%", reportDate));
    }

    private void SortReportersReportList(List<Report> sortedList, SortType sortType) {
        Comparator<Report> comparator;
        switch (sortType){
            case dateZA:
                comparator = Comparator.comparing(Report::getDateTime);
                break;
            case uuidAZ:
                comparator = Comparator.comparing(Report::getReportedUUID);
                break;
            case uuidZA:
                comparator = Comparator.comparing(Report::getReportedUUID).reversed();
                break;
            case nameAZ:
                comparator = Comparator.comparing(Report::getReportedName);
                break;
            case nameZA:
                comparator = Comparator.comparing(Report::getReportedName).reversed();
                break;
            default:
                comparator = Comparator.comparing(Report::getDateTime).reversed();
                break;
        }
        sortedList.sort(comparator);
    }
}
