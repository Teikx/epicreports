package teik.ers.bukkit.invs.others.archivedmenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import teik.ers.bukkit.configs.inventories.others.ArchivedMenu;
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

public class ArchivedM {
    private final InvHelperMG invHelperMG;
    private final InvDataMG invDataMG;
    private final InvDataUMG invDataUMG;
    private final InvsUtils invsUtils;
    private final ArchivedMenu archivedMenu;

    public ArchivedM(InventoryMG inventoryMG) {
        this.invHelperMG = inventoryMG.invHelperMG;
        this.invDataMG = inventoryMG.invDataMG;
        this.invDataUMG = inventoryMG.invDataUMG;
        this.invsUtils = inventoryMG.invsUtils;
        this.archivedMenu = inventoryMG.configInvsMG.getArchivedMenu();
    }


    public void createArchivedReportsMenu(InventoryPlayer inventoryPlayer){
        Player player = inventoryPlayer.getPlayer();
        SortType sortType = inventoryPlayer.getSortType();
        int page = inventoryPlayer.getPage();
        FilterType filterType = inventoryPlayer.getFilterType();
        String serverName = inventoryPlayer.getServerName();

        Inventory inventory = Bukkit.createInventory(null, 54, archivedMenu.getMenuTitle());
        List<Report> archivedReports = invHelperMG.getArchivedsReportsList(filterType, serverName);

        int totalPages = invsUtils.calculateTotalPages(archivedReports.size());

        boolean[] pagination = invsUtils.setupPagination(page, totalPages);

        invsUtils.fillWithDarkGlassPanes(inventory, 45, 54);
        invsUtils.createPagePanels(inventory, false, pagination[0], pagination[1], sortType, 6);
        invsUtils.setChest(inventory, page, totalPages, filterType, 49,
                archivedMenu.getChestTitle(),
                archivedMenu.getChestLore()
        );

        SortArchivedList(archivedReports, sortType);
        fillArchivedsInventorySlots(inventory, archivedReports, page);

        player.openInventory(inventory);

        invDataUMG.putPlayerSortTypeArchiveds(player, sortType);
        invDataUMG.putPlayerFilterTypeArchiveds(player, filterType);
        invDataMG.putPerPlayerArchivedList(player, archivedReports);

        invDataMG.putPlayerInventory(player, inventoryPlayer);
    }

    // SORT

    private void SortArchivedList(List<Report> sortedList, SortType sortType) {
        Comparator<Report> comparator;
        switch (sortType) {
            case dateZA: comparator = Comparator.comparing(Report::getDateTime);break;
            case nameAZ: comparator = Comparator.comparing(Report::getReportedName);break;
            case nameZA: comparator = Comparator.comparing(Report::getReportedName).reversed();break;
            case uuidAZ: comparator = Comparator.comparing(Report::getReportedUUID);break;
            case uuidZA: comparator = Comparator.comparing(Report::getReportedUUID).reversed();break;
            default: comparator = Comparator.comparing(Report::getDateTime).reversed();break;
        }
        sortedList.sort(comparator);
    }

    // UTILS

    private void fillArchivedsInventorySlots(Inventory inv, List<Report> reportedsObj, int page) {
        if(reportedsObj.isEmpty()) return;
        int slotsPerPage = inv.getSize() - 9;
        int start = (page - 1) * slotsPerPage;
        int end = Math.min(start + slotsPerPage, reportedsObj.size());
        for (int i = start; i < end; i++) {
            Report report = reportedsObj.get(i);
            String title = archivedMenu
                    .getEnchantedBookTitle()
                    .replaceAll("%reason%", report.getReason()
                    );
            List<String> lore = new ArrayList<>(archivedMenu.getEnchantedBookLore());

            ReplaceArchivedLore(lore, report);
            ItemStack itemStack = invsUtils.setItemStack(Material.ENCHANTED_BOOK, title, lore, 0);

            inv.setItem(i - start, itemStack);
        }
    }

    private void ReplaceArchivedLore(List<String> lore, Report report){
        lore.replaceAll(s -> s
                .replaceAll("%reportedName%", report.getReportedName())
                .replaceAll("%reportedUUID%", report.getReportedUUID())
                .replaceAll("%reportedLocation%", report.getReportedLocation())
                .replaceAll("%reportedServer%", report.getReportedServer())
                .replaceAll("%reportedGamemode%", report.getReportedGamemode())
                .replaceAll("%reportedHealth%", report.getReportedHealth() + "")
                .replaceAll("%reporterName%", report.getReporterName())
                .replaceAll("%reporterUUID%", report.getReporterUUID())
                .replaceAll("%reporterLocation%", report.getReporterLocation())
                .replaceAll("%reporterServer%", report.getReporterServer())
                .replaceAll("%reporterGamemode%", report.getReporterGamemode())
                .replaceAll("%reporterHealth%", report.getReporterHealth() + "")
                .replaceAll("%reportDate%", report.getDate())
                .replaceAll("%reportStatus%", report.getProcess().toString())
        );
    }
}
