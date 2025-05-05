package teik.ers.bukkit.invs.helpermenus.ifreportsmenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import teik.ers.bukkit.configs.inventories.helpermenus.InfoReportMenu;
import teik.ers.bukkit.invs.InvsUtils;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.inventories.helpers.InvHelperMG;
import teik.ers.bukkit.utilities.UtilitiesPlayers;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.global.models.objects.Locate;
import teik.ers.global.models.objects.Report;
import teik.ers.global.models.Process;

import java.util.ArrayList;
import java.util.List;

public class IfReportsM {
    private final InvHelperMG invHelperMG;
    private final InvDataMG invDataMG;
    private final InvsUtils invsUtils;
    private final UtilitiesPlayers utilitiesPlayers;
    private final InfoReportMenu infoReportMenu;

    public IfReportsM(InventoryMG inventoryMG) {
        this.invHelperMG = inventoryMG.invHelperMG;
        this.invDataMG = inventoryMG.invDataMG;
        this.invsUtils = inventoryMG.invsUtils;
        this.utilitiesPlayers = inventoryMG.utilitiesPlayers;
        this.infoReportMenu = inventoryMG.configInvsMG.getInfoReportMenu();
    }

    public void createInfoReportMenu(InventoryPlayer inventoryPlayer){
        Player player = inventoryPlayer.getPlayer();

        int idReport = invDataMG.getReportSelected(player);
        Report report = invHelperMG.getReportPerId(idReport);

        Inventory inventory = Bukkit.createInventory(null, 54, infoReportMenu.getMenuTitle());

        invsUtils.fillWithGrayGlassPanes(inventory, 0, 45);
        invsUtils.fillWithDarkGlassPanes(inventory, 45, 54);
        invsUtils.configureCloseButton(inventory, 45, false);

        setPlayerInfoItems(report, inventory);
        setReportInfoItems(report, inventory);

        player.openInventory(inventory);
        invDataMG.putPlayerInventory(player, inventoryPlayer);
    }

    private void setReportInfoItems(Report report, Inventory inventory){
        setReportReason(report.getReason(), inventory);
        setReportDate(report.getDate(), inventory);
        setReportStatus(report.getProcess(), inventory);
        setReportArchive(inventory);
        setReportDelete(inventory);

        String reportedName = report.getReportedName();

        setReportedComments(reportedName, inventory);
        setActionsMenu(reportedName, inventory);

    }

    private void setActionsMenu(String reportedName, Inventory inventory){
        String title = infoReportMenu.getActionsMenuTitle();
        List<String> lore = infoReportMenu.getActionsMenuLore();
        title = title.replaceAll("%ReportedName%", reportedName);
        ItemStack itemStack = invsUtils.setItemStack(Material.COMPASS, title, lore, 0);
        inventory.setItem(43, itemStack);
    }

    private void setReportedComments(String reportedName, Inventory inventory){
        String title = infoReportMenu.getReportedCommentsTitle();
        List<String> lore = infoReportMenu.getReportedCommentsLore();
        title = title.replaceAll("%ReportedName%", reportedName);
        ItemStack itemStack = invsUtils.setItemStack(Material.BOOK, title, lore, 0);
        inventory.setItem(42, itemStack);
    }

    private void setReportDelete(Inventory inventory){
        String title = infoReportMenu.getDeleteReportTitle();
        List<String> lore = infoReportMenu.getDeleteReportLore();
        ItemStack itemStack = invsUtils.setItemStack(Material.REDSTONE, title, lore, 0);
        inventory.setItem(41, itemStack);
    }

    private void setReportArchive(Inventory inventory){
        String title = infoReportMenu.getArchiveReportTitle();
        List<String> lore = infoReportMenu.getArchiveReportLore();
        ItemStack itemStack = invsUtils.setItemStack(Material.ANVIL, title, lore, 0);
        inventory.setItem(40, itemStack);
    }

    private void setReportStatus(Process status, Inventory inventory){
        String title = infoReportMenu.getStatusReportTitle();
        List<String> lore = infoReportMenu.getStatusReportLore();
        title = title.replaceAll("%StatusReport%", status.toString());

        ItemStack itemStack = invsUtils.setItemStack(Material.getMaterial(351), title, lore, 11);
        if(status == Process.Processing) itemStack = invsUtils.setItemStack(Material.getMaterial(351), title, lore, 12);
        if(status == Process.Archived) itemStack = invsUtils.setItemStack(Material.getMaterial(351), title, lore, 8);
        inventory.setItem(39, itemStack);
    }

    private void setReportDate(String date, Inventory inventory){
        String title = infoReportMenu.getDateReportTitle();
        List<String> lore = infoReportMenu.getDateReportLore();
        title = title.replaceAll("%DateReport%", date);
        ItemStack itemStack = invsUtils.setItemStack(Material.getMaterial(347), title, lore, 0);
        inventory.setItem(38, itemStack);
    }

    private void setReportReason(String reason, Inventory inventory){
        String title = infoReportMenu.getReasonReportTitle();
        List<String> lore = infoReportMenu.getReasonReportLore();
        title = title.replaceAll("%ReasonReport%", reason);
        ItemStack itemStack = invsUtils.setItemStack(Material.PAPER, title, lore, 0);
        inventory.setItem(37, itemStack);
    }

    private void setPlayerInfoItems(Report report, Inventory inventory){
        String reportedName = report.getReportedName();
        String reporterName = report.getReporterName();
        String reportedUUID = report.getReportedUUID();
        String reporterUUID = report.getReporterUUID();
        setReportPlayerData(reportedName, 10, inventory);
        setReportPlayerData(reporterName, 19, inventory);
        setReportPlayerOnline(reportedName, utilitiesPlayers.playerIsOnline(reportedUUID), 11, inventory);
        setReportPlayerOnline(reporterName, utilitiesPlayers.playerIsOnline(reporterUUID), 20, inventory);
        setReportPlayerUUIDNick(reportedName, reportedUUID, 12, inventory);
        setReportPlayerUUIDNick(reporterName, reporterUUID, 21, inventory);
        setReportPlayerIp(reportedName, report.getReportedIP(), 13, inventory);
        setReportPlayerIp(reporterName, report.getReporterIP(), 22, inventory);
        setReportPlayerLocation(reportedName, report.getReportedLocation(), report.getReportedServer(), 14, inventory);
        setReportPlayerLocation(reporterName, report.getReporterLocation(), report.getReporterServer(), 23, inventory);
        setReportPlayerGamemode(reportedName, report.getReportedGamemode(), 15, inventory);
        setReportPlayerGamemode(reporterName, report.getReporterGamemode(), 24, inventory);
        setReportPlayerHealth(reportedName, report.getReportedHealth(), 16, inventory);
        setReportPlayerHealth(reporterName, report.getReporterHealth(), 25, inventory);
    }

    private void setReportPlayerData(String playerName, int slot, Inventory inventory){
        String title = infoReportMenu.getReportedInfoTitle();
        title = title.replaceAll("%ReportedName%", playerName);
        List<String> lore = infoReportMenu.getReportedInfoLore();
        if(slot != 10) {
            title = infoReportMenu.getReporterInfoTitle();
            title = title.replaceAll("%ReporterName%", playerName);
            lore = infoReportMenu.getReporterInfoLore();
        }

        ItemStack itemStack = invsUtils.setItemStack(Material.getMaterial(397), title, lore, 3);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(playerName);
        itemStack.setItemMeta(skullMeta);

        inventory.setItem(slot, itemStack);
    }

    private void setReportPlayerOnline(String playerName, boolean isOnline, int slot, Inventory inventory){
        String title = infoReportMenu.getOnlinePlayerTitle();
        List<String> lore = infoReportMenu.getOnlinePlayerLore();
        if(!isOnline) {
            title = infoReportMenu.getOfflinePlayerTitle();
            lore = infoReportMenu.getOfflinePlayerLore();
        }
        title = title.replaceAll("%PlayerReport%", playerName);

        ItemStack itemStack;
        if(isOnline){
            itemStack = invsUtils.setItemStack(Material.getMaterial(351), title, lore, 10);
        }else{
            itemStack = invsUtils.setItemStack(Material.getMaterial(351), title, lore, 1);
        }
        inventory.setItem(slot, itemStack);
    }

    private void setReportPlayerUUIDNick(String playerName, String playerUUID, int slot, Inventory inventory){
        String title = infoReportMenu.getNickUuidTitle();
        List<String> lore = new ArrayList<>(infoReportMenu.getNickUuidLore());
        title = title
                .replaceAll("%playerNick%", playerName)
                .replaceAll("%playerUUID%", playerUUID)
        ;

        lore.replaceAll(s -> s
                .replaceAll("%playerNick%", playerName)
                .replaceAll("%playerUUID%", playerUUID));
        ItemStack itemStack = invsUtils.setItemStack(Material.NAME_TAG, title, lore, 0);
        inventory.setItem(slot, itemStack);
    }

    private void setReportPlayerIp(String playerName, String ip, int slot, Inventory inventory){
        String title = infoReportMenu.getIpTitle();
        List<String> lore = new ArrayList<>(infoReportMenu.getIpLore());
        title = title.replaceAll("%PlayerReport%", playerName);
        lore.replaceAll(s -> s
                .replaceAll("%ReportIp%", ip));
        ItemStack itemStack = invsUtils.setItemStack(Material.BRICK, title, lore, 0);
        inventory.setItem(slot, itemStack);
    }

    private void setReportPlayerLocation(String playerName, String playerLocation, String server, int slot, Inventory inventory){
        String title = infoReportMenu.getLocationTitle();
        List<String> lore = new ArrayList<>(infoReportMenu.getLocationLore());
        title = title.replaceAll("%PlayerReport%", playerName);

        Locate locate = new Locate();
        locate.setFromString(playerLocation);
        lore.replaceAll(s -> s
                .replaceAll("%ReportWorld%", locate.getWorld())
                .replaceAll("%ReportCords%", locate.getXYZ())
                .replaceAll("%ReportServer%", server));

        ItemStack itemStack = invsUtils.setItemStack(Material.getMaterial(2), title, lore, 0);
        inventory.setItem(slot, itemStack);
    }

    private  void setReportPlayerGamemode(String playerName, String playerGamemode, int slot, Inventory inventory){
        String title = infoReportMenu.getGamemodeTitle();
        List<String> lore = new ArrayList<>(infoReportMenu.getGamemodeLore());
        title = title.replaceAll("%PlayerReport%", playerName);
        lore.replaceAll(s -> s
                .replaceAll("%ReportGamemode%", playerGamemode));
        ItemStack itemStack = invsUtils.setItemStack(Material.GOLD_AXE, title, lore, 0);
        inventory.setItem(slot, itemStack);
    }

    private void setReportPlayerHealth(String playerName, int playerHealth, int slot, Inventory inventory){
        String title = infoReportMenu.getHealthTitle();
        List<String> lore = new ArrayList<>(infoReportMenu.getHealthLore());
        title = title.replaceAll("%PlayerReport%", playerName);
        lore.replaceAll(s -> s
                .replaceAll("%ReportHealth%", playerHealth + ""));
        ItemStack itemStack = invsUtils.setItemStack(Material.APPLE, title, lore, 0);
        inventory.setItem(slot, itemStack);
    }
}
