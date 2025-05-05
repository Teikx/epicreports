package teik.ers.bukkit.invs.helpermenus.serversmenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import teik.ers.bukkit.configs.inventories.helpermenus.ServersMenu;
import teik.ers.bukkit.invs.InvsUtils;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.enums.FilterType;
import teik.ers.global.utils.querys.QuerysUT;

import java.util.ArrayList;
import java.util.List;

public class ServersM {

    private final InvsUtils invsUtils;
    private final InvDataMG invDataMG;
    private final QuerysUT querysUT;
    private final ServersMenu serversMenu;

    public ServersM(InventoryMG inventoryMG, QuerysUT querysUT){
        this.invsUtils = inventoryMG.invsUtils;
        this.invDataMG = inventoryMG.invDataMG;
        this.querysUT = querysUT;
        this.serversMenu = inventoryMG.configInvsMG.getServersMenu();
    }
    
    public void createServersMenu(InventoryPlayer inventoryPlayer){
        List<String> serversList = querysUT.getAllServers();

        int totalItems = serversList.size();
        int totalPages = (int) Math.ceil((double) totalItems / 14);

        Player player = inventoryPlayer.getPlayer();
        int page = inventoryPlayer.getPage();

        Inventory inventory = Bukkit.createInventory(null, 45, serversMenu.getMenuTitle());
        fillBordersWithGlass(inventory);

        invsUtils.fillWithDarkGlassPanes(inventory, 36, 45);
        boolean[] pagination = invsUtils.setupPagination(page, totalPages);
        invsUtils.createPagePanels(inventory, false, pagination[0], pagination[1], null, 5);
        invsUtils.setChest(inventory, page, totalPages, FilterType.Server, 40,
                serversMenu.getChestTitle(),
                serversMenu.getChestLore()
        );

        fillInventory(page, totalItems, serversList, inventory);

        player.openInventory(inventory);
        invDataMG.putPlayerInventory(player, inventoryPlayer);
        invDataMG.updateServersList(serversList);
    }

    private void fillInventory(int page, int totalItems, List<String> Servers, Inventory inventory) {
        if(Servers.isEmpty()) return;
        int[] availableSlots = {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25};
        int start = (page - 1) * 14;
        int end = Math.min(start + 14, totalItems);
        for (int i = start, slotIndex = 0; i < end; i++, slotIndex++) {
            String serverName = Servers.get(i);
            String title = serversMenu.getServerTitle()
                    .replaceAll("%ServerName%", serverName);
            List<String> lore = new ArrayList<>(serversMenu.getServerLore());
            ReplaceServerLore(lore, serverName);

            ItemStack itemStack = invsUtils.setItemStack(Material.WORKBENCH, title, lore, 0);
            inventory.setItem(availableSlots[slotIndex], itemStack);
        }
    }


    private void fillBordersWithGlass(Inventory inv) {
        ItemStack itemStack = invsUtils.setItemStack(Material.STAINED_GLASS_PANE, "", null, 7);
        for (int i = 0; i < 45; i++) {
            if (i < 9 || i >= 45 - 9 || i % 9 == 0 || i % 9 == 8) {
                inv.setItem(i, itemStack);
            }
        }
    }
    private void ReplaceServerLore(List<String> Lore, String server){
        Lore.replaceAll(s -> s.replaceAll("%ServerName%", server));
    }
}
