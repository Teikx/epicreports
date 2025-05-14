package teik.ers.bukkit.managers.inventories;

import org.bukkit.entity.Player;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.ConfigInvsMG;
import teik.ers.bukkit.configs.ConfigManager;
import teik.ers.bukkit.configs.LanguagesManager;
import teik.ers.bukkit.invs.InvsUtils;
import teik.ers.bukkit.invs.commandsmenus.onlineplmenu.OnlinePLM;
import teik.ers.bukkit.invs.commandsmenus.prereportsmenu.PreReportsM;
import teik.ers.bukkit.invs.mainmenus.allreportsmenu.AllReportsM;
import teik.ers.bukkit.invs.others.archivedmenu.ArchivedM;
import teik.ers.bukkit.invs.others.commentsmenu.CommentsM;
import teik.ers.bukkit.invs.settingsmenus.notifysstmenu.NotifysM;
import teik.ers.bukkit.invs.settingsmenus.settingsmenu.SettingsM;
import teik.ers.bukkit.invs.submenus.datamenu.DataM;
import teik.ers.bukkit.invs.helpermenus.ifreportsmenu.IfReportsM;
import teik.ers.bukkit.invs.helpermenus.actionsmenu.ActionsM;
import teik.ers.bukkit.invs.mainmenus.reportedsmenu.ReportedsM;
import teik.ers.bukkit.invs.mainmenus.reportersmenu.ReportersM;
import teik.ers.bukkit.invs.submenus.reportsmenu.ReportsM;
import teik.ers.bukkit.invs.submenus.rrreportsmenu.RrReportsM;
import teik.ers.bukkit.invs.helpermenus.serversmenu.ServersM;
import teik.ers.bukkit.managers.freeze.FreezeMG;
import teik.ers.bukkit.managers.inventories.helpers.InvChannelsMG;
import teik.ers.bukkit.managers.inventories.helpers.InvHelperMG;
import teik.ers.bukkit.managers.onlinepl.OnlinePLMG;
import teik.ers.bukkit.managers.reportmg.ReportMG;
import teik.ers.bukkit.managers.updateinvs.UpdateInvsMG;
import teik.ers.bukkit.utilities.UtilitiesMsgs;
import teik.ers.bukkit.utilities.UtilitiesPlayers;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.enums.FilterType;
import teik.ers.bukkit.utilities.models.enums.InventorySection;
import teik.ers.bukkit.utilities.models.enums.SortType;

public class InventoryMG {
    public final ConfigManager configManager;
    public final LanguagesManager messageManager;
    public final ConfigInvsMG configInvsMG;

    public final boolean isMysql;

    public final ReportMG reportMG;

    public final InvDataMG invDataMG;
    public final InvDataUMG invDataUMG;
    public final InvHelperMG invHelperMG;

    public final InvChannelsMG invChannelsMG;
    public final UpdateInvsMG updateInvsMG;
    public final FreezeMG freezeMG;

    public final InvsUtils invsUtils;
    public final UtilitiesPlayers utilitiesPlayers;
    public final UtilitiesMsgs utilitiesMsgs;

    public final OnlinePLMG onlinePLMG;

    private final ReportedsM reportedsM;
    private final ServersM serversM;
    private final DataM dataM;
    private final ReportsM reportsM;
    private final IfReportsM ifReportsM;
    private final ActionsM actionsM;
    private final ArchivedM archivedM;
    private final CommentsM commentsM;
    private final AllReportsM allReportsM;
    private final ReportersM reportersM;
    private final RrReportsM rrReportsM;
    private final SettingsM settingsM;
    private final NotifysM notifysM;
    private final PreReportsM preReportsM;
    private final OnlinePLM onlinePLM;

    public InventoryMG(EpicReports plugin) {
        this.configManager = plugin.configManager;
        this.messageManager = plugin.messageManager;
        this.configInvsMG = plugin.configInvsMG;

        this.isMysql = plugin.configManager.isMYSQLActive();

        this.reportMG = plugin.reportMG;

        this.invDataMG = new InvDataMG();
        this.invDataUMG = new InvDataUMG();
        this.invHelperMG = new InvHelperMG(plugin.reportMG.getPlayersListMG(), plugin.reportMG.getReportsListMG(),
                plugin.reportMG.getCommentsListMG(), plugin.utilitiesPlayers, plugin.configManager.isMYSQLActive());

        this.invChannelsMG = new InvChannelsMG(plugin);
        this.updateInvsMG = new UpdateInvsMG(this);
        this.freezeMG = new FreezeMG();

        this.invsUtils = new InvsUtils(plugin.configInvsMG.getPagePanels());
        this.utilitiesPlayers = plugin.utilitiesPlayers;
        this.utilitiesMsgs = plugin.utilitiesMsgs;

        this.onlinePLMG = new OnlinePLMG(plugin);

        this.reportedsM = new ReportedsM(this);
        this.serversM = new ServersM(this, plugin.querysUT);
        this.dataM = new DataM(this);
        this.reportsM = new ReportsM(this);
        this.ifReportsM = new IfReportsM(this);
        this.actionsM = new ActionsM(this);
        this.archivedM = new ArchivedM(this);
        this.commentsM = new CommentsM(this);
        this.allReportsM = new AllReportsM(this);
        this.reportersM = new ReportersM(this);
        this.rrReportsM = new RrReportsM(this);
        this.settingsM = new SettingsM(this);
        this.notifysM = new NotifysM(this);
        this.preReportsM = new PreReportsM(this);
        this.onlinePLM = new OnlinePLM(this);
    }

    //OPEN INVENTORIES

    public void openReportedsM(Player player, int page, SortType sortType, FilterType filterType, String server){
        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.ReportedsMenu);

        inventoryPlayer.setPage(page);
        invDataUMG.setSortType(player, sortType, inventoryPlayer);
        invDataUMG.setFilterType(player, filterType, inventoryPlayer);
        if(server != null) inventoryPlayer.setServerName(server);

        reportedsM.createReportedsMenu(inventoryPlayer);
    }

    public void openServersM(Player player, int page, InventorySection lastInventory, InventorySection lastLastInvSec,
                             InventorySection lastLastLastInvSec){
        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.ServersMenu);
        inventoryPlayer.setPage(page);
        inventoryPlayer.setLastInventorySection(lastInventory);
        inventoryPlayer.setLastLastInvSec(lastLastInvSec);
        inventoryPlayer.setLastLastLastInvSec(lastLastLastInvSec);

        serversM.createServersMenu(inventoryPlayer);
    }

    public void openDataM(Player player){
        if (checkReportedExist(player)) return;

        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.DataMenu);
        dataM.createDataMenu(inventoryPlayer);
    }

    public void openReportsM(Player player, int page, SortType sortType, FilterType filterType, String server){
        if (checkReportedExist(player)) return;

        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.ReportsMenu);

        inventoryPlayer.setPage(page);
        invDataUMG.setSortTypeReports(player, sortType, inventoryPlayer);
        invDataUMG.setFilterTypeReports(player, filterType, inventoryPlayer);
        if(server != null) inventoryPlayer.setServerName(server);

        reportsM.createReportsMenu(inventoryPlayer);
    }

    public void openIfReportsM(Player player, InventorySection lastInvSection){
        if (checkReportedExist(player)) return;
        if (checkReportExist(player)) return;

        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.InfoReportMenu);
        inventoryPlayer.setLastInventorySection(lastInvSection);

        ifReportsM.createInfoReportMenu(inventoryPlayer);
    }

    public void openActionsM(Player player, InventorySection lastInvSection, InventorySection lastLastInvSec){
        if (checkReportedExist(player)) return;

        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.ActionsMenu);
        inventoryPlayer.setLastInventorySection(lastInvSection);
        inventoryPlayer.setLastLastInvSec(lastLastInvSec);

        actionsM.createActionsMenu(inventoryPlayer);
    }

    public void openArchivedM(Player player, int page, SortType sortType, FilterType filterType, String name,
                              InventorySection lastInvSec, InventorySection lastLastInvSec){
        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.ArchivedMenu);
        inventoryPlayer.setLastInventorySection(lastInvSec);
        inventoryPlayer.setLastLastInvSec(lastLastInvSec);

        inventoryPlayer.setPage(page);
        invDataUMG.setSortTypeArchiveds(player, sortType, inventoryPlayer);
        invDataUMG.setFilterTypeArchiveds(player, filterType, inventoryPlayer);
        if(name != null) inventoryPlayer.setServerName(name);

        archivedM.createArchivedReportsMenu(inventoryPlayer);
    }

    public void openCommentsM(Player player, int page, SortType sortType, FilterType filterType, String name,
                              InventorySection inventorySection, InventorySection lastLastInvSec){
        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.CommentsMenu);
        inventoryPlayer.setLastInventorySection(inventorySection);
        inventoryPlayer.setLastLastInvSec(lastLastInvSec);

        inventoryPlayer.setPage(page);
        invDataUMG.setSortTypeComments(player, sortType, inventoryPlayer);
        invDataUMG.setFilterTypeComments(player, filterType, inventoryPlayer);
        if(name != null) inventoryPlayer.setServerName(name);

        commentsM.createCommentsMenu(inventoryPlayer);
    }

    public void openAllReportsM(Player player, int page, SortType sortType, FilterType filterType, String serverName){
        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.AllReportsMenu);

        inventoryPlayer.setPage(page);
        invDataUMG.setSortTypeAllReports(player, sortType, inventoryPlayer);
        invDataUMG.setFilterTypeAllReports(player, filterType, inventoryPlayer);
        if(serverName != null) inventoryPlayer.setServerName(serverName);

        allReportsM.createAllReportsMenu(inventoryPlayer);
    }

    public void openReportersM(Player player, int page, SortType sortType, FilterType filterType, String serverName){
        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.ReportersMenu);

        inventoryPlayer.setPage(page);
        invDataUMG.setSortTypeReporters(player, sortType, inventoryPlayer);
        invDataUMG.setFilterTypeReporters(player, filterType, inventoryPlayer);
        if(serverName != null) inventoryPlayer.setServerName(serverName);

        reportersM.createReportersMenu(inventoryPlayer);
    }

    public void openReporterReportsM(Player player, int page, SortType sortType, FilterType filterType, String serverName){
        if(checkReporterExist(player)) return;

        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.ReporterReportsMenu);

        inventoryPlayer.setPage(page);
        invDataUMG.setSortTypeReporterReports(player, sortType, inventoryPlayer);
        invDataUMG.setFilterTypeReporterReports(player, filterType, inventoryPlayer);
        if(serverName != null) inventoryPlayer.setServerName(serverName);

        rrReportsM.createReporterReportsMenu(inventoryPlayer);
    }

    public void openSettingsM(Player player){
        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.SettingsMenu);

        settingsM.createSettingsMenu(inventoryPlayer);
    }

    public void openNotifysM(Player player){
        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.NotifysMenu);

        notifysM.createNotifysMenu(inventoryPlayer);
    }

    public void openPredefinedReportsM(Player player, int page, String nick, InventorySection lastInvSec){
        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.PredefinedReportsMenu);
        inventoryPlayer.setPage(page);
        inventoryPlayer.setLastInventorySection(lastInvSec);
        invDataMG.putNickSelected(player, nick);

        preReportsM.createPredefinedReportsMenu(inventoryPlayer);
    }

    public void openOnlinePlayersM(Player player, int page, SortType sortType){
        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.OnlinePlayersMenu);

        inventoryPlayer.setPage(page);
        invDataUMG.setSortTypeOnlinePlayers(player, sortType, inventoryPlayer);

        onlinePLM.createOnlinePlayersMenu(inventoryPlayer);
    }

    //UTILS

    private boolean checkReportedExist(Player player) {
        String selectedUUID = invDataMG.getUuidSelected(player);
        boolean reportedExist = reportMG.reportedExist(selectedUUID);
        if(!reportedExist){
            openReportedsM(player, 1, null, null, null);
            return true;
        }
        return false;
    }

    private boolean checkReporterExist(Player player) {
        String selectedUUID = invDataMG.getReporterUuidSelected(player);
        boolean reporterExist = reportMG.reporterExist(selectedUUID);
        if(!reporterExist){
            openReportersM(player, 1, null, null, null);
            return true;
        }
        return false;
    }

    private boolean checkReportExist(Player player) {
        int idReport = invDataMG.getReportSelected(player);
        boolean reportExist = reportMG.idReportExist(idReport);
        if(!reportExist){
            openReportsM(player, 1, null, null, null);
            return true;
        }
        return false;
    }
}
