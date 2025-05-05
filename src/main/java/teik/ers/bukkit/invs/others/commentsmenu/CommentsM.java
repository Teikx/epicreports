package teik.ers.bukkit.invs.others.commentsmenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import teik.ers.bukkit.configs.inventories.others.CommentsMenu;
import teik.ers.bukkit.invs.InvsUtils;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InvDataUMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.inventories.helpers.InvHelperMG;
import teik.ers.bukkit.utilities.models.Comment;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.enums.FilterType;
import teik.ers.bukkit.utilities.models.enums.SortType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CommentsM {
    private final InvHelperMG invHelperMG;
    private final InvDataMG invDataMG;
    private final InvDataUMG invDataUMG;
    private final InvsUtils invsUtils;
    private final CommentsMenu commentsMenu;

    public CommentsM(InventoryMG inventoryMG) {
        this.invHelperMG = inventoryMG.invHelperMG;
        this.invDataMG = inventoryMG.invDataMG;
        this.invDataUMG = inventoryMG.invDataUMG;
        this.invsUtils = inventoryMG.invsUtils;
        this.commentsMenu = inventoryMG.configInvsMG.getCommentsMenu();
    }

    public void createCommentsMenu(InventoryPlayer inventoryPlayer){
        Player player = inventoryPlayer.getPlayer();
        SortType sortType = inventoryPlayer.getSortType();
        int page = inventoryPlayer.getPage();
        FilterType filterType = inventoryPlayer.getFilterType();
        String serverName = inventoryPlayer.getServerName();

        Inventory inventory = Bukkit.createInventory(null, 54, commentsMenu.getMenuTitle());
        List<Comment> commentsList = invHelperMG.getCommentsList(filterType, serverName);

        int totalPages = invsUtils.calculateTotalPages(commentsList.size());

        boolean[] pagination = invsUtils.setupPagination(page, totalPages);

        invsUtils.fillWithDarkGlassPanes(inventory, 45, 54);
        invsUtils.createPagePanels(inventory, false, pagination[0], pagination[1], sortType, 6);
        invsUtils.setChest(inventory, page, totalPages, filterType, 49,
                commentsMenu.getChestTitle(),
                commentsMenu.getChestLore()
        );

        SortCommentsList(commentsList, sortType);
        fillCommentsInventorySlots(inventory, commentsList, page);

        player.openInventory(inventory);

        invDataUMG.putPlayerSortTypeComments(player, sortType);
        invDataUMG.putPlayerFilterTypeComments(player, filterType);
        invDataMG.putPerPlayerCommentList(player, commentsList);

        invDataMG.putPlayerInventory(player, inventoryPlayer);
    }

    // SORT

    private void SortCommentsList(List<Comment> sortedList, SortType sortType) {
        Comparator<Comment> comparator;
        switch (sortType) {
            case dateZA: comparator = Comparator.comparing(Comment::getDateTime);break;
            case nameAZ: comparator = Comparator.comparing(Comment::getReportedName);break;
            case nameZA: comparator = Comparator.comparing(Comment::getReportedName).reversed();break;
            case uuidAZ: comparator = Comparator.comparing(Comment::getReportedUUID);break;
            case uuidZA: comparator = Comparator.comparing(Comment::getReportedUUID).reversed();break;
            default: comparator = Comparator.comparing(Comment::getDateTime).reversed();break;
        }
        sortedList.sort(comparator);
    }

    // UTILS

    private void fillCommentsInventorySlots(Inventory inv, List<Comment> commentList, int page) {
        if(commentList.isEmpty()) return;
        int slotsPerPage = inv.getSize() - 9;
        int start = (page - 1) * slotsPerPage;
        int end = Math.min(start + slotsPerPage, commentList.size());
        for (int i = start; i < end; i++) {
            Comment comment = commentList.get(i);
            String title = commentsMenu
                    .getBookTitle()
                    .replaceAll("%comment%", comment.getComment()
                    );
            List<String> lore = new ArrayList<>(commentsMenu.getBookLore());

            ReplaceCommentLore(lore, comment);
            ItemStack itemStack = invsUtils.setItemStack(Material.BOOK, title, lore, 0);

            inv.setItem(i - start, itemStack);
        }
    }

    private void ReplaceCommentLore(List<String> lore, Comment comment){
        lore.replaceAll(s -> s
                .replaceAll("%reportedName%", comment.getReportedName())
                .replaceAll("%reportedUUID%", comment.getReportedUUID())
                .replaceAll("%staffName%", comment.getStaffName())
                .replaceAll("%staffUUID%", comment.getStaffUUID())
                .replaceAll("%commentDate%", comment.getDate())
        );
    }
}
