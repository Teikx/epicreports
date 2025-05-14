package teik.ers.bukkit.invs.others.commentsmenu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.reportmg.ReportMG;
import teik.ers.bukkit.managers.updateinvs.UpdateInvsMG;
import teik.ers.bukkit.utilities.models.Comment;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.enums.FilterType;
import teik.ers.bukkit.utilities.models.enums.InventorySection;
import teik.ers.bukkit.utilities.models.enums.SortType;

import java.util.List;

public class CMClicks {
    private final InventoryMG inventoryMG;
    private final ReportMG reportMG;
    private final InvDataMG invDataMG;


    private final UpdateInvsMG updateInvsMG;

    public CMClicks(InventoryMG inventoryMG) {
        this.inventoryMG = inventoryMG;
        this.reportMG = inventoryMG.reportMG;
        this.invDataMG = inventoryMG.invDataMG;

        this.updateInvsMG = inventoryMG.updateInvsMG;
    }

    public void CommentsMenu(InventoryPlayer inventoryPlayer, int slot, ClickType clickType, ItemStack currentItem){
        Player player = inventoryPlayer.getPlayer();
        int page = inventoryPlayer.getPage();
        SortType sortType = inventoryPlayer.getSortType();
        FilterType filterType = inventoryPlayer.getFilterType();
        String name = inventoryPlayer.getServerName();
        InventorySection lastSection = inventoryPlayer.getLastInventorySection();
        InventorySection lastLastInvSec = inventoryPlayer.getLastLastInvSec();

        switch (slot){
            case 45:
                //Back
                player.closeInventory();
                switch (lastSection){
                    case DataMenu:
                        inventoryMG.openDataM(player);
                        break;
                    case InfoReportMenu:
                        inventoryMG.openIfReportsM(player, lastLastInvSec);
                        break;
                    default:
                        break;
                }
                break;
            case 53:
                //Sort
                SortType newSortType = SortActionsCommentsMenu(sortType, clickType);
                player.closeInventory();
                inventoryMG.openCommentsM(player, page, newSortType, filterType, name, lastSection, lastLastInvSec);
                break;
            case 48:
                //Back Page
                if(page > 1){
                    player.closeInventory();
                    inventoryMG.openCommentsM(player, page-1, sortType, filterType, name, lastSection, lastLastInvSec);
                }
                break;
            case 50:
                //Next Page
                if(page < (int) Math.ceil((double) invDataMG.getPerPlayerCommentListSize(player) / 45)){
                    player.closeInventory();
                    inventoryMG.openCommentsM(player, page+1, sortType, filterType, name, lastSection, lastLastInvSec);
                }
                break;
            case 49:
                switch (clickType){
                    case LEFT:
                        //Chest - Change Filter
                        FilterType newFilterType = getfilterTypeCommentsMenu(filterType);
                        player.closeInventory();
                        inventoryMG.openCommentsM(player, page, sortType, newFilterType, null, lastSection, lastLastInvSec);
                        break;
                    case RIGHT:
                        //Change menu
                        player.closeInventory();
                        inventoryMG.openReportedsM(player, 1, null, null, null);
                        break;
                }
                break;
            default:
                //Book
                if(currentItem.getType() != (Material.BOOK)) return;
                int reportNumber = ((page -1) * 45) + slot;

                List<Comment> commentList = invDataMG.getPerPlayerCommentList(player);
                Comment comment = commentList.get(reportNumber);

                reportMG.removeComment(comment.getId());

                updateInvsMG.sendUpdateInvs(player);
                break;
        }
    }

    private SortType SortActionsCommentsMenu(SortType sortType, ClickType clickType){
        if(clickType.isLeftClick()){
            switch (sortType) {
                case dateAZ: return SortType.dateZA;
                case dateZA: return SortType.nameAZ;
                case nameAZ: return SortType.nameZA;
                case nameZA: return SortType.uuidAZ;
                case uuidAZ: return SortType.uuidZA;
                case uuidZA: return SortType.dateAZ;
            }
        }
        switch (sortType) {
            case dateAZ: return SortType.uuidZA;
            case dateZA: return SortType.dateAZ;
            case nameAZ: return SortType.dateZA;
            case nameZA: return SortType.nameAZ;
            case uuidAZ: return SortType.nameZA;
            case uuidZA: return SortType.uuidAZ;
        }
        return null;
    }

    private FilterType getfilterTypeCommentsMenu(FilterType filterType){
        switch (filterType){
            case None:
                return FilterType.ReportedOnline;
            case ReportedOnline:
                return FilterType.ReportedOffline;
            case ReportedOffline:
            case ReportedName:
                return FilterType.None;
        }
        return null;
    }
}
