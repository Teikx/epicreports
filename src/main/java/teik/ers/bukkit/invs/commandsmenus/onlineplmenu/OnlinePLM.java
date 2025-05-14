package teik.ers.bukkit.invs.commandsmenus.onlineplmenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import teik.ers.bukkit.configs.inventories.commandsmenus.OnlinePlayersMenu;
import teik.ers.bukkit.invs.InvsUtils;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InvDataUMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.onlinepl.OnlinePLMG;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.enums.FilterType;
import teik.ers.bukkit.utilities.models.enums.SortType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OnlinePLM {
    private final OnlinePLMG onlinePLMG;
    private final InvDataMG invDataMG;
    private final InvDataUMG invDataUMG;
    private final InvsUtils invsUtils;
    private final OnlinePlayersMenu onlinePlayersMenu;

    public OnlinePLM(InventoryMG inventoryMG) {
        this.onlinePLMG = inventoryMG.onlinePLMG;
        this.invDataMG = inventoryMG.invDataMG;
        this.invDataUMG = inventoryMG.invDataUMG;
        this.invsUtils = inventoryMG.invsUtils;
        this.onlinePlayersMenu = inventoryMG.configInvsMG.getOnlinePlayersMenu();
    }

    public void createOnlinePlayersMenu(InventoryPlayer inventoryPlayer){
        Player player = inventoryPlayer.getPlayer();
        SortType sortType = inventoryPlayer.getSortType();
        int page = inventoryPlayer.getPage();

        Inventory inventory = Bukkit.createInventory(null, 54, onlinePlayersMenu.getMenuTitle());

        List<String> playersList = onlinePLMG.getOnlinePlayers();
        playersList.remove(player.getName());

        SortPlayersList(playersList, sortType);

        int totalPages = invsUtils.calculateTotalPages(playersList.size());

        boolean[] pagination = invsUtils.setupPagination(page, totalPages);

        invsUtils.fillWithDarkGlassPanes(inventory, 45, 54);
        invsUtils.createPagePanels(inventory, true, pagination[0], pagination[1], sortType, 6);
        invsUtils.setChest(inventory, page, totalPages, FilterType.None, 49,
                onlinePlayersMenu.getChestTitle(),
                onlinePlayersMenu.getChestLore()
        );

        fillOnlinePlayersInvSlots(inventory, playersList, page);

        player.openInventory(inventory);

        invDataUMG.putPlayerSortTypeOnlinePlayers(player, sortType);
        invDataMG.putPerPlayerOnlinePlayersList(player, playersList);

        invDataMG.putPlayerInventory(player, inventoryPlayer);
    }

    private void fillOnlinePlayersInvSlots(Inventory inv, List<String> playersList, int page){
        if(playersList.isEmpty()) return;
        int slotsPerPage = inv.getSize() - 9;
        int start = (page - 1) * slotsPerPage;
        int end = Math.min(start + slotsPerPage, playersList.size());
        for (int i = start; i < end; i++) {
            String nick = playersList.get(i);

            String title = onlinePlayersMenu
                    .getOnlinePlayerTitle().replaceAll("%PlayerName%", nick);
            List<String> lore = onlinePlayersMenu.getOnlinePlayerLore();

            ItemStack itemStack = invsUtils.setItemStack(Material.getMaterial(397), title, lore, 3);
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            skullMeta.setOwner(nick);
            itemStack.setItemMeta(skullMeta);

            inv.setItem(i - start, itemStack);
        }
    }

    private void SortPlayersList(List<String> playersList, SortType sortType) {
        Comparator<String> comparator;
        if (sortType == SortType.nameAZ) {
            comparator = Comparator.naturalOrder();
        } else {
            comparator = Comparator.reverseOrder();
        }
        playersList.sort(comparator);
    }
}
