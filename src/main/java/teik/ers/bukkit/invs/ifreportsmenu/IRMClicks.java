package teik.ers.bukkit.invs.ifreportsmenu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.inventories.helpers.InvChannelsMG;
import teik.ers.bukkit.managers.inventories.helpers.InvHelperMG;
import teik.ers.bukkit.managers.reportmg.ReportMG;
import teik.ers.bukkit.managers.updateinvs.UpdateInvsMG;
import teik.ers.bukkit.utilities.UtilitiesMsgs;
import teik.ers.bukkit.utilities.UtilitiesPlayers;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.global.models.Process;
import teik.ers.global.models.objects.Locate;
import teik.ers.global.models.objects.Report;

import java.util.List;

public class IRMClicks {

    private final Boolean isMysql;
    
    private final InventoryMG inventoryMG;
    private final ReportMG reportMG;

    private final InvDataMG invDataMG;
    private final InvHelperMG invHelperMG;

    private final InvChannelsMG invChannelsMG;
    private final UpdateInvsMG updateInvsMG;

    private final UtilitiesPlayers utilitiesPlayers;
    private final UtilitiesMsgs utilitiesMsgs;

    public IRMClicks(InventoryMG inventoryMG) {
        this.isMysql = inventoryMG.isMysql;
        
        this.inventoryMG = inventoryMG;
        this.reportMG = inventoryMG.reportMG;

        this.invDataMG = inventoryMG.invDataMG;
        this.invHelperMG = inventoryMG.invHelperMG;

        this.invChannelsMG = inventoryMG.invChannelsMG;
        this.updateInvsMG = inventoryMG.updateInvsMG;

        this.utilitiesPlayers = inventoryMG.utilitiesPlayers;
        this.utilitiesMsgs = inventoryMG.utilitiesMsgs;
        
    }

    public void InfoReportsMenu(InventoryPlayer inventoryPlayer, int slot, ClickType clickType){
        Player player = inventoryPlayer.getPlayer();
        int reportID = invDataMG.getReportSelected(player);
        Report report = invHelperMG.getReportPerId(reportID);

        switch (slot){
            case 45:
                //Back
                player.closeInventory();
                inventoryMG.openReportsM(player, 1, null, null, null);
                break;
            case 10:
                //Reported Info
                //Open archived and comments menu
                break;
            case 11:
                //Reported Online
                if(utilitiesPlayers.playerIsOnline(report.getReportedUUID())){
                    if(clickType.isLeftClick()) {
                        player.closeInventory();
                        if (isMysql) {
                            invChannelsMG.teleportPlayerToReportPlayer(player, report.getReportedName());
                            return;
                        }
                        player.teleport(utilitiesPlayers.getPlayer(report.getReportedName()));
                        return;
                    }
                    if (isMysql) {
                        invChannelsMG.teleportReportPlayerToPlayer(player, report.getReportedName());
                        return;
                    }
                    utilitiesPlayers.getPlayer(report.getReportedName()).teleport(player);
                }
                break;
            case 20:
                //Reporter Online
                if(utilitiesPlayers.playerIsOnline(report.getUuidReported())){
                    if(clickType.isLeftClick()) {
                        player.closeInventory();
                        if (isMysql) {
                            invChannelsMG.teleportPlayerToReportPlayer(player, report.getReporterName());
                            return;
                        }
                        player.teleport(utilitiesPlayers.getPlayer(report.getReporterName()));
                        return;
                    }
                    if (isMysql) {
                        invChannelsMG.teleportReportPlayerToPlayer(player, report.getReporterName());
                        return;
                    }
                    utilitiesPlayers.getPlayer(report.getReporterName()).teleport(player);
                }
                break;
            case 12:
                //Reported Nick / UUID
                if(clickType.isLeftClick()){
                    utilitiesMsgs.suggestMsg(player, report.getReportedName(), "nick");
                }
                if(clickType.isRightClick()){
                    utilitiesMsgs.suggestMsg(player, report.getUuidReported(), "uuid");
                }
                break;
            case 21:
                //Reporter Nick / UUID
                if(clickType.isLeftClick()){
                    utilitiesMsgs.suggestMsg(player, report.getReporterName(), "nick");
                }
                if(clickType.isRightClick()){
                    utilitiesMsgs.suggestMsg(player, report.getUuidReporter(), "uuid");
                }
                break;
            case 13:
                //Reported IP
                utilitiesMsgs.suggestMsg(player, report.getReportedIP(), "ip");
                break;
            case 22:
                //Reporter IP
                utilitiesMsgs.suggestMsg(player, report.getReporterIP(), "ip");
                break;
            case 14:
                //Reported Location
                String reportedLocation = report.getReportedLocation();

                if(isMysql) {
                    invChannelsMG.teleportPlayerToLocation(player, reportedLocation, report.getReportedServer());
                    return;
                }

                Locate locate = new Locate();
                locate.setFromString(reportedLocation);
                locate.teleportToLocate(player);
                break;
            case 23:
                //Reporter Location
                String reporterLocation = report.getReporterLocation();

                if(isMysql) {
                    invChannelsMG.teleportPlayerToLocation(player, reporterLocation, report.getReporterServer());
                    return;
                }
                Locate locate2 = new Locate();
                locate2.setFromString(reporterLocation);
                locate2.teleportToLocate(player);
                break;
            case 37:
                //Report Reason
                utilitiesMsgs.suggestMsg(player, report.getReason(), "report reason");
                break;
            case 38:
                //Report Date
                utilitiesMsgs.suggestMsg(player, report.getDate(), "report date");
                break;
            case 39:
                //Report status
                reportMG.updateReportProcess(report.getReportID(), getNewProcess(report.getProcess()));
                updateInvsMG.sendUpdateInvs(player);
                break;
            case 40:
                //Archive Report
                List<Report> plReportsL = invDataMG.getPerPlayerReportList(player);
                reportMG.archiveReport(reportID);
                if(plReportsL.size()>1) {
                    updateInvsMG.sendUpdateInvs(player);
                    return;
                }
                player.closeInventory();
                inventoryMG.openReportedsM(player, 1, null, null, null);
                updateInvsMG.sendUpdateInvs(player);
                break;
            case 41:
                //Delete Report
                List<Report> plReportsL2 = invDataMG.getPerPlayerReportList(player);
                reportMG.removeReport(reportID);
                if(plReportsL2.size()>1) {
                    updateInvsMG.sendUpdateInvs(player);
                    return;
                }
                player.closeInventory();
                inventoryMG.openReportedsM(player, 1, null, null, null);
                updateInvsMG.sendUpdateInvs(player);
                break;
            case 42:
                //Open Comments Menu
                break;
        }
    }

    private Process getNewProcess(Process process){
        if(process == Process.Waiting){
            return Process.Processing;
        }
        return Process.Waiting;
    }
}
