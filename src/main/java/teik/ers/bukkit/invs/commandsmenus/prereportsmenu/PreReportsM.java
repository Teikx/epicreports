package teik.ers.bukkit.invs.commandsmenus.prereportsmenu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import teik.ers.bukkit.configs.inventories.commandsmenus.PredefinedReportsMenu;
import teik.ers.bukkit.invs.InvsUtils;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.PredefinedReport;
import teik.ers.bukkit.utilities.models.enums.FilterType;

import java.util.List;

public class PreReportsM {
    private final InvDataMG invDataMG;
    private final InvsUtils invsUtils;
    private final PredefinedReportsMenu predefinedReportsMenu;

    public PreReportsM(InventoryMG inventoryMG) {
        this.invDataMG = inventoryMG.invDataMG;
        this.invsUtils = inventoryMG.invsUtils;
        this.predefinedReportsMenu = inventoryMG.configInvsMG.getPredefinedReportsMenu();
    }

    public void createPredefinedReportsMenu(InventoryPlayer inventoryPlayer){
        Player player = inventoryPlayer.getPlayer();
        int page = inventoryPlayer.getPage();

        Inventory inventory = Bukkit.createInventory(null, 54, predefinedReportsMenu.getMenuTitle());

        List<PredefinedReport> predefinedReportList = predefinedReportsMenu.getPredefinedReportList();

        int totalPages = invsUtils.calculateTotalPages(predefinedReportList.size());

        boolean[] pagination = invsUtils.setupPagination(page, totalPages);

        invsUtils.fillWithDarkGlassPanes(inventory, 45, 54);
        invsUtils.createPagePanels(inventory, true, pagination[0], pagination[1], null, 6);
        invsUtils.setChest(inventory, page, totalPages, FilterType.None, 49,
                predefinedReportsMenu.getChestTitle(),
                predefinedReportsMenu.getChestLore()
        );

        setPredefinedItems(inventory, predefinedReportList, page);

        player.openInventory(inventory);
        invDataMG.putPlayerInventory(player, inventoryPlayer);
    }

    private void setPredefinedItems(Inventory inv, List<PredefinedReport> predefinedReportList, int page){
        if(predefinedReportList.isEmpty()) return;
        int slotsPerPage = inv.getSize() - 9;
        int start = (page - 1) * slotsPerPage;
        int end = Math.min(start + slotsPerPage, predefinedReportList.size());
        for (int i = start; i < end; i++) {
            ItemStack itemStack = predefinedReportList.get(i).getItem();

            inv.setItem(i - start, itemStack);
        }
    }
}
