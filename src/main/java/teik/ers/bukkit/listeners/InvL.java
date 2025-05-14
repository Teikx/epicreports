package teik.ers.bukkit.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import teik.ers.bukkit.invs.commandsmenus.onlineplmenu.OPLClicks;
import teik.ers.bukkit.invs.commandsmenus.prereportsmenu.PRMClicks;
import teik.ers.bukkit.invs.helpermenus.actionsmenu.AMClicks;
import teik.ers.bukkit.invs.mainmenus.allreportsmenu.ARMClicks;
import teik.ers.bukkit.invs.others.archivedmenu.ACMClicks;
import teik.ers.bukkit.invs.others.commentsmenu.CMClicks;
import teik.ers.bukkit.invs.settingsmenus.notifysstmenu.NMClicks;
import teik.ers.bukkit.invs.settingsmenus.settingsmenu.STMClicks;
import teik.ers.bukkit.invs.submenus.datamenu.DMClicks;
import teik.ers.bukkit.invs.helpermenus.ifreportsmenu.IRMClicks;
import teik.ers.bukkit.invs.mainmenus.reportedsmenu.RDMClicks;
import teik.ers.bukkit.invs.mainmenus.reportersmenu.RRMClicks;
import teik.ers.bukkit.invs.submenus.reportsmenu.RMClicks;
import teik.ers.bukkit.invs.submenus.rrreportsmenu.RRRMClicks;
import teik.ers.bukkit.invs.helpermenus.serversmenu.SMClicks;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.enums.InventorySection;

public class InvL implements Listener {
    private final InvDataMG invDataMG;

    private final RDMClicks rdmClicks;
    private final SMClicks smClicks;
    private final DMClicks dmClicks;
    private final RMClicks rmClicks;
    private final IRMClicks irmClicks;
    private final AMClicks amClicks;
    private final ACMClicks acmClicks;
    private final CMClicks cmClicks;
    private final ARMClicks armClicks;
    private final RRMClicks rrmClicks;
    private final RRRMClicks rrrmClicks;
    private final STMClicks stmClicks;
    private final NMClicks nmClicks;
    private final PRMClicks prmClicks;
    private final OPLClicks oplClicks;

    public InvL(InventoryMG inventoryMG) {
        this.invDataMG = inventoryMG.invDataMG;

        rdmClicks = new RDMClicks(inventoryMG);
        smClicks = new SMClicks(inventoryMG);
        dmClicks = new DMClicks(inventoryMG);
        rmClicks = new RMClicks(inventoryMG);
        irmClicks = new IRMClicks(inventoryMG);
        amClicks = new AMClicks(inventoryMG);
        acmClicks = new ACMClicks(inventoryMG);
        cmClicks = new CMClicks(inventoryMG);
        armClicks = new ARMClicks(inventoryMG);
        rrmClicks = new RRMClicks(inventoryMG);
        rrrmClicks = new RRRMClicks(inventoryMG);
        stmClicks = new STMClicks(inventoryMG);
        nmClicks = new NMClicks(inventoryMG);
        prmClicks = new PRMClicks(inventoryMG);
        oplClicks = new OPLClicks(inventoryMG);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        InventoryPlayer inventoryPlayer = invDataMG.getPlayerInventory(player);

        if(inventoryPlayer == null) return;

        event.setCancelled(true);
        if(event.getCurrentItem() == null || event.getClickedInventory() != player.getOpenInventory().getTopInventory()) return;

        clickedMenu(inventoryPlayer, event.getSlot(), event.getClick(), event.getCurrentItem());
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        invDataMG.removePlayerInventory(player);
    }

    private void clickedMenu(InventoryPlayer inventoryPlayer, int slot, ClickType clickType, ItemStack currentItem){
        InventorySection inventorySection = inventoryPlayer.getInventorySection();
        switch (inventorySection){
            case ReportedsMenu:
                rdmClicks.ReportedsMenu(inventoryPlayer, slot, clickType, currentItem);
                break;
            case ServersMenu:
                smClicks.ServersMenu(inventoryPlayer, slot, currentItem);
                break;
            case DataMenu:
                dmClicks.DataMenu(inventoryPlayer, slot, clickType);
                break;
            case ReportsMenu:
                rmClicks.ReportsMenu(inventoryPlayer, slot, clickType, currentItem);
                break;
            case InfoReportMenu:
                irmClicks.InfoReportsMenu(inventoryPlayer, slot, clickType);
                break;
            case ActionsMenu:
                amClicks.ActionsMenu(inventoryPlayer, slot);
                break;
            case ArchivedMenu:
                acmClicks.ArchivedMenu(inventoryPlayer, slot, clickType, currentItem);
                break;
            case CommentsMenu:
                cmClicks.CommentsMenu(inventoryPlayer, slot, clickType, currentItem);
                break;
            case AllReportsMenu:
                armClicks.AllReportsMenu(inventoryPlayer, slot, clickType, currentItem);
                break;
            case ReportersMenu:
                rrmClicks.ReportersMenu(inventoryPlayer, slot, clickType, currentItem);
                break;
            case ReporterReportsMenu:
                rrrmClicks.ReporterReportsMenu(inventoryPlayer, slot, clickType, currentItem);
                break;
            case SettingsMenu:
                stmClicks.SettingsMenu(inventoryPlayer, slot, clickType);
                break;
            case NotifysMenu:
                nmClicks.notifysMenu(inventoryPlayer, slot);
                break;
            case PredefinedReportsMenu:
                prmClicks.PredefinedReportsMenu(inventoryPlayer, slot);
                break;
            case OnlinePlayersMenu:
                oplClicks.OnlinePlayersMenu(inventoryPlayer, slot, clickType, currentItem);
                break;
        }
    }
}
