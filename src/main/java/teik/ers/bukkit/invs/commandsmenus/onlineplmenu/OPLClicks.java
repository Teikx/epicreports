package teik.ers.bukkit.invs.commandsmenus.onlineplmenu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import teik.ers.bukkit.configs.LanguagesManager;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.enums.ChatType;
import teik.ers.bukkit.utilities.models.enums.InventorySection;
import teik.ers.bukkit.utilities.models.enums.SortType;

import java.util.List;

public class OPLClicks {
    private final InventoryMG inventoryMG;
    private final InvDataMG invDataMG;
    private final LanguagesManager messageManager;

    public OPLClicks(InventoryMG inventoryMG) {
        this.inventoryMG = inventoryMG;
        this.invDataMG = inventoryMG.invDataMG;
        this.messageManager = inventoryMG.messageManager;
    }

    public void OnlinePlayersMenu(InventoryPlayer inventoryPlayer, int slot, ClickType clickType, ItemStack currentItem){
        Player player = inventoryPlayer.getPlayer();
        int page = inventoryPlayer.getPage();
        SortType sortType = inventoryPlayer.getSortType();

        switch (slot){
            case 45:
                //Close
                player.closeInventory();
                break;
            case 48:
                //Pag Anterior
                if(page > 1){
                    player.closeInventory();
                    inventoryMG.openOnlinePlayersM(player, page-1, sortType);
                }
                break;
            case 50:
                //Pag Sig
                int reportListSize = invDataMG.getPerPlayerOnlinePlayersListSize(player);
                if(page < (int) Math.ceil((double) reportListSize / 45)){
                    player.closeInventory();
                    inventoryMG.openOnlinePlayersM(player, page+1, sortType);
                }
                break;
            case 53:
                //Change Sort
                SortType newSort = getSortTypeReportsMenu(sortType, clickType);
                player.closeInventory();
                inventoryMG.openOnlinePlayersM(player, page, newSort);
                break;

            //Head click
            default:
                if(currentItem.getType() != Material.getMaterial(397)) return;
                int reportIndex = ((page -1) * 45) + slot;
                List<String> playersList = invDataMG.getPerPlayerOnlinePlayersList(player);
                String nick = playersList.get(reportIndex);
                switch (clickType){
                    case LEFT:
                        //Write reason in chat
                        invDataMG.putNickSelected(player, nick);
                        invDataMG.putPlayerChatType(player, ChatType.Reporting);
                        player.closeInventory();
                        player.sendTitle(messageManager.getWrite_text_title_title(), messageManager.getWrite_text_title_subtitle());
                        player.sendMessage(messageManager.getWrite_text_msg());
                        break;
                    case RIGHT:
                        //Open predefined reports menu
                        player.closeInventory();
                        inventoryMG.openPredefinedReportsM(player, 1, nick, InventorySection.OnlinePlayersMenu);
                        break;
                }
                break;
        }
    }

    private SortType getSortTypeReportsMenu(SortType sortType, ClickType clickType){
        if(clickType.isLeftClick()){
            switch (sortType) {
                case nameAZ: return SortType.nameZA;
                case nameZA: return SortType.nameAZ;
            }
        }
        switch (sortType) {
            case nameAZ: return SortType.nameZA;
            case nameZA: return SortType.nameAZ;
        }
        return null;
    }
}
