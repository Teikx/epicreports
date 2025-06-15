package teik.ers.bukkit.invs.helpermenus.actionsmenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import teik.ers.bukkit.configs.inventories.helpermenus.ActionsMenu;
import teik.ers.bukkit.invs.InvsUtils;
import teik.ers.bukkit.managers.freeze.FreezeMG;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.reportmg.helpers.PlayersListMG;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.global.models.objects.PlayerOBJ;

import java.util.List;

public class ActionsM {

    private final InvDataMG invDataMG;
    private final PlayersListMG playersListMG;

    private final InvsUtils invsUtils;
    private final FreezeMG freezeMG;

    private final ActionsMenu ActionsMenu;

    public ActionsM(InventoryMG inventoryMG) {
        this.invDataMG = inventoryMG.invDataMG;
        this.playersListMG = inventoryMG.reportMG.getPlayersListMG();

        this.invsUtils = inventoryMG.invsUtils;
        this.freezeMG = inventoryMG.freezeMG;

        this.ActionsMenu = inventoryMG.configInvsMG.getActionsMenu();
    }

    public void createActionsMenu(InventoryPlayer inventoryPlayer){
        Player player = inventoryPlayer.getPlayer();

        String reportedUUID = invDataMG.getUuidSelected(player);
        PlayerOBJ playerOBJ = playersListMG.getPlayerOBJ(reportedUUID);
        String reportedNick = playerOBJ.getName();

        Inventory inventory = Bukkit.createInventory(null, 36, ActionsMenu.getMenuTitle());

        invsUtils.fillWithGrayGlassPanes(inventory, 0, 27);
        invsUtils.fillWithDarkGlassPanes(inventory, 27, 36);
        invsUtils.configureCloseButton(inventory, 27, false);

        setBanItem(inventory, reportedNick);
        setKickItem(inventory, reportedNick);
        setMuteItem(inventory, reportedNick);

        if(!freezeMG.playerIsFreezed(reportedNick)) setFreezeItem(inventory, reportedNick);
        else{
            setUnfreezeItem(inventory, reportedNick);
        }

        player.openInventory(inventory);
        invDataMG.putPlayerInventory(player, inventoryPlayer);
    }

    private void setBanItem(Inventory inventory, String reportedNick){
        String title = ActionsMenu.getBanPlayerTitle();
        List<String> lore = ActionsMenu.getBanPlayerLore();
        title = title.replaceAll("%reportedNick%", reportedNick);

        lore.replaceAll(s -> s
                .replaceAll("%reportedNick%", reportedNick));

        ItemStack itemStack = invsUtils.setItemStack(Material.BARRIER, title, lore, 0);
        inventory.setItem(10, itemStack);
    }

    private void setKickItem(Inventory inventory, String reportedNick){
        String title = ActionsMenu.getKickPlayerTitle();
        List<String> lore = ActionsMenu.getKickPlayerLore();
        title = title.replaceAll("%reportedNick%", reportedNick);

        lore.replaceAll(s -> s
                .replaceAll("%reportedNick%", reportedNick));

        ItemStack itemStack = invsUtils.setItemStack(Material.getMaterial(414), title, lore, 0);
        inventory.setItem(12, itemStack);
    }

    private void setMuteItem(Inventory inventory, String reportedNick){
        String title = ActionsMenu.getMutePlayerTitle();
        List<String> lore = ActionsMenu.getMutePlayerLore();
        title = title.replaceAll("%reportedNick%", reportedNick);

        lore.replaceAll(s -> s
                .replaceAll("%reportedNick%", reportedNick));

        ItemStack itemStack = invsUtils.setItemStack(Material.STICK, title, lore, 0);
        inventory.setItem(14, itemStack);
    }

    private void setFreezeItem(Inventory inventory, String reportedNick){
        String title = ActionsMenu.getFreezePlayerTitle();
        List<String> lore = ActionsMenu.getFreezePlayerLore();
        title = title.replaceAll("%reportedNick%", reportedNick);

        lore.replaceAll(s -> s
                .replaceAll("%reportedNick%", reportedNick));

        ItemStack itemStack = invsUtils.setItemStack(Material.ICE, title, lore, 0);
        inventory.setItem(16, itemStack);
    }

    private void setUnfreezeItem(Inventory inventory, String reportedNick){
        String title = ActionsMenu.getUnfreezePlayerTitle();
        List<String> lore = ActionsMenu.getUnfreezePlayerLore();
        title = title.replaceAll("%reportedNick%", reportedNick);

        lore.replaceAll(s -> s
                .replaceAll("%reportedNick%", reportedNick));

        ItemStack itemStack = invsUtils.setItemStack(Material.BLAZE_POWDER, title, lore, 0);
        inventory.setItem(16, itemStack);
    }
}
