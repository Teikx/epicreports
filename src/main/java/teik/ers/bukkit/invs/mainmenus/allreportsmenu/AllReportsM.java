package teik.ers.bukkit.invs.mainmenus.allreportsmenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import teik.ers.bukkit.configs.inventories.mainmenus.AllReportsMenu;
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

public class AllReportsM {
    private final InvHelperMG invHelperMG;
    private final InvDataMG invDataMG;
    private final InvDataUMG invDataUMG;
    private final InvsUtils invsUtils;
    private final AllReportsMenu allReportsMenu;

    public AllReportsM(InventoryMG inventoryMG) {
        this.invHelperMG = inventoryMG.invHelperMG;
        this.invDataMG = inventoryMG.invDataMG;
        this.invDataUMG = inventoryMG.invDataUMG;
        this.invsUtils = inventoryMG.invsUtils;
        this.allReportsMenu = inventoryMG.configInvsMG.getAllReportsMenu();
    }

    public void createAllReportsMenu(InventoryPlayer inventoryPlayer){
        Player player = inventoryPlayer.getPlayer();
        SortType sortType = inventoryPlayer.getSortType();
        int page = inventoryPlayer.getPage();
        FilterType filterType = inventoryPlayer.getFilterType();
        String serverName = inventoryPlayer.getServerName();

        Inventory inventory = Bukkit.createInventory(null, 54, allReportsMenu.getMenuTitle());

        List<Report> allReportList = invHelperMG.getAllReportList(filterType, serverName);

        int totalPages = invsUtils.calculateTotalPages(allReportList.size());

        boolean[] pagination = invsUtils.setupPagination(page, totalPages);

        invsUtils.fillWithDarkGlassPanes(inventory, 45, 54);
        invsUtils.createPagePanels(inventory, false, pagination[0], pagination[1], sortType, 6);
        invsUtils.setChest(inventory, page, totalPages, filterType, 49,
                allReportsMenu.getChestTitle(),
                new ArrayList<>(allReportsMenu.getChestLore())
        );

        SortAllReportsList(allReportList, sortType);
        fillAllReportsInventorySlots(inventory, allReportList, page);

        player.openInventory(inventory);

        invDataUMG.putPlayerSortTypeAllReports(player, sortType);
        invDataUMG.putPlayerFilterTypeAllReports(player, filterType);
        invDataMG.putPerPlayerAllReportList(player, allReportList);

        invDataMG.putPlayerInventory(player, inventoryPlayer);
    }

    private void fillAllReportsInventorySlots(Inventory inv, List<Report> reportList, int page) {
        if(reportList.isEmpty()) return;
        int slotsPerPage = inv.getSize() - 9;
        int start = (page - 1) * slotsPerPage;
        int end = Math.min(start + slotsPerPage, reportList.size());
        for (int i = start; i < end; i++) {

            Report report = reportList.get(i);

            String title = allReportsMenu
                    .getReportMapTitle().replaceAll("%reason%", report.getReason());
            List<String> lore = new ArrayList<>(allReportsMenu.getReportMapLore());
            ReplaceAllReportsLore(lore, report);

            ItemStack itemStack = invsUtils.setItemStack(Material.EMPTY_MAP, title, lore, 0);
            inv.setItem(i - start, itemStack);
        }
    }

    private void ReplaceAllReportsLore(List<String> lore, Report report){
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

    private void SortAllReportsList(List<Report> sortedList, SortType sortType) {
        Comparator<Report> comparator;
        switch (sortType){
            case dateZA:
                comparator = Comparator.comparing(Report::getDateTime);
                break;
            case nameAZ:
                comparator = Comparator.comparing(Report::getReportedName);
                break;
            case nameZA:
                comparator = Comparator.comparing(Report::getReportedName).reversed();
                break;
            case uuidAZ:
                comparator = Comparator.comparing(Report::getReportedUUID);
                break;
            case uuidZA:
                comparator = Comparator.comparing(Report::getReportedUUID).reversed();
                break;
            default:
                comparator = Comparator.comparing(Report::getDateTime).reversed();
                break;
        }
        sortedList.sort(comparator);
    }
}
