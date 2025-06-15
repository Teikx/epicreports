package teik.ers.bukkit.invs.submenus.datamenu;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import teik.ers.bukkit.configs.inventories.submenus.DataMenu;
import teik.ers.bukkit.invs.InvsUtils;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.reportmg.helpers.PlayersListMG;
import teik.ers.bukkit.utilities.UtilitiesPlayers;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.global.models.objects.Locate;
import teik.ers.global.models.objects.PlayerOBJ;
import java.util.List;

public class DataM {
    private final PlayersListMG playersListMG;
    private final InvDataMG invDataMG;
    private final InvsUtils invsUtils;
    private final DataMenu dataMenu;
    private final UtilitiesPlayers utilitiesPlayers;

    public DataM(InventoryMG inventoryMG) {
        this.playersListMG = inventoryMG.reportMG.getPlayersListMG();
        this.invDataMG = inventoryMG.invDataMG;
        this.invsUtils = inventoryMG.invsUtils;
        this.dataMenu = inventoryMG.configInvsMG.getDataMenu();
        this.utilitiesPlayers = inventoryMG.utilitiesPlayers;
    }

    public void createDataMenu(InventoryPlayer inventoryPlayer){
        Player player = inventoryPlayer.getPlayer();
        String reportedUUID = invDataMG.getUuidSelected(player);

        boolean isOnline = utilitiesPlayers.playerIsOnline(reportedUUID);

        PlayerOBJ reportedObj = playersListMG.getPlayerOBJ(reportedUUID);

        Inventory inventory = Bukkit.createInventory(null, 45, dataMenu.getMenuTitle());

        invsUtils.fillWithGrayGlassPanes(inventory, 0, 36);
        invsUtils.fillWithDarkGlassPanes(inventory, 36, 45);
        invsUtils.configureCloseButton(inventory, 36, false);


        setNickItem(inventory, reportedObj);
        setLocationItem(inventory, isOnline, reportedObj);
        setReportsItem(inventory);
        setArchivedReportsItem(inventory);
        setStatusItem(inventory, isOnline, reportedObj);
        setDataItem(inventory, isOnline, reportedObj);
        setDeleteItem(inventory);
        setCommentsItems(inventory, reportedObj);

        player.openInventory(inventory);
        invDataMG.putPlayerInventory(player, inventoryPlayer);
    }

    private void setNickItem(Inventory inventory, PlayerOBJ playerOBJ){
        String title = dataMenu.getNickUuidTitle();
        List<String> lore = dataMenu.getNickUuidLore();

        lore.replaceAll(s -> s
                .replaceAll("%ReportedUuid%", playerOBJ.getUuid())
                .replaceAll("%ReportedName%", playerOBJ.getName()));

        ItemStack itemStack = invsUtils.setItemStack(Material.NAME_TAG, title, lore, 0);
        inventory.setItem(10, itemStack);
    }

    private void setLocationItem(Inventory inventory, Boolean isOnline, PlayerOBJ playerOBJ){
        String title;
        List<String> lore;

        if(isOnline){
            title = dataMenu.getLocationOnlineTitle();
            lore  = dataMenu.getLocationOnlineLore();

            lore.replaceAll(s -> s
                    .replaceAll("%ReportedServer%", " ")
                    .replaceAll("%ReportedWorld%", " ")
                    .replaceAll("%ReportedCords%", " "));
        }else{
            title = dataMenu.getLocationOfflineTitle();
            lore  = dataMenu.getLocationOfflineLore();
            Locate locate = playerOBJ.getLastLocate();

            String world, xyz;

            if(locate != null){
                world = locate.getWorld(); xyz = locate.getXYZ();
            }else{
                world = " "; xyz = " ";
            }

            lore.replaceAll(s -> s
                    .replaceAll("%ReportedServer%", playerOBJ.getLastserver())
                    .replaceAll("%ReportedWorld%", world)
                    .replaceAll("%ReportedCords%", xyz));
        }


        ItemStack itemStack = invsUtils.setItemStack(Material.getMaterial(2), title, lore, 0);
        inventory.setItem(12, itemStack);
    }

    private void setReportsItem(Inventory inventory){
        String title = dataMenu.getReportsTitle();
        List<String> lore = dataMenu.getReportsLore();

        ItemStack itemStack = invsUtils.setItemStack(Material.PAPER, title, lore, 0);

        inventory.setItem(14, itemStack);
    }

    private void setArchivedReportsItem(Inventory inventory){
        String title = dataMenu.getArchivedReportsTitle();
        List<String> lore = dataMenu.getArchivedReportsLore();

        ItemStack itemStack = invsUtils.setItemStack(Material.ANVIL, title, lore, 0);
        inventory.setItem(16, itemStack);
    }

    private void setStatusItem(Inventory inventory, Boolean isOnline, PlayerOBJ playerOBJ){
        String title;
        List<String> lore;

        if(isOnline){
            title = dataMenu.getStatusOnlineTitle();
            lore = dataMenu.getStatusOnlineLore();
        }else{
            title = dataMenu.getStatusOfflineTitle();
            lore = dataMenu.getStatusOfflineLore();
        }

        String nick = playerOBJ.getName();
        title = title.replaceAll("%ReportedName%", nick);
        lore.replaceAll(s -> s
                .replaceAll("%ReportedName%", nick));

        ItemStack itemStack;
        if(isOnline){
            itemStack = invsUtils.setDyeItem(10, title, lore);
        }else{
            itemStack = invsUtils.setDyeItem(1, title, lore);
        }

        inventory.setItem(20, itemStack);
    }

    private void setDataItem(Inventory inventory, Boolean isOnline, PlayerOBJ playerOBJ){
        String title = dataMenu.getReportedDataTitle();
        List<String> lore = dataMenu.getReportedDataLore();

        String status;
        if(isOnline) status = "online";
        else {status = "offline";}

        String nick = playerOBJ.getName();
        title = title.replaceAll("%ReportedName%", nick);
        lore.replaceAll(s -> s
                .replaceAll("%ReportedName%", nick)
                .replaceAll("%ReportedStatus%", status)
                .replaceAll("%ReportedIP%", playerOBJ.getIp())
                .replaceAll("%ReportedUuid%", playerOBJ.getUuid())
        );

        ItemStack itemStack = invsUtils.setItemStack(Material.getMaterial(397), title, lore, 3);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(nick);
        itemStack.setItemMeta(skullMeta);
        inventory.setItem(22, itemStack);
    }

    private void setDeleteItem(Inventory inventory){
        String title = dataMenu.getDeleteTitle();
        List<String> lore = dataMenu.getDeleteLore();

        ItemStack itemStack = invsUtils.setItemStack(Material.REDSTONE, title, lore, 0);
        inventory.setItem(24, itemStack);
    }

    private void setCommentsItems(Inventory inventory, PlayerOBJ playerOBJ){
        String title = dataMenu.getReportedCommentsTitle();
        List<String> lore = dataMenu.getReportedCommentsLore();

        title = title.replaceAll("%ReportedName%", playerOBJ.getName());

        ItemStack itemStack = invsUtils.setItemStack(Material.BOOK, title, lore, 0);
        inventory.setItem(44, itemStack);
    }
}
