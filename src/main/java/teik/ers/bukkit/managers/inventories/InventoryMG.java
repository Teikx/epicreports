package teik.ers.bukkit.managers.inventories;

import org.bukkit.entity.Player;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.ConfigInvsMG;
import teik.ers.bukkit.configs.LanguagesManager;
import teik.ers.bukkit.invs.InvsUtils;
import teik.ers.bukkit.invs.datamenu.DataM;
import teik.ers.bukkit.invs.ifreportsmenu.IfReportsM;
import teik.ers.bukkit.invs.reportedsmenu.ReportedsM;
import teik.ers.bukkit.invs.reportsmenu.ReportsM;
import teik.ers.bukkit.invs.serversmenu.ServersM;
import teik.ers.bukkit.managers.inventories.helpers.InvChannelsMG;
import teik.ers.bukkit.managers.inventories.helpers.InvHelperMG;
import teik.ers.bukkit.managers.reportmg.ReportMG;
import teik.ers.bukkit.managers.updateinvs.UpdateInvsMG;
import teik.ers.bukkit.utilities.UtilitiesMsgs;
import teik.ers.bukkit.utilities.UtilitiesPlayers;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.enums.FilterType;
import teik.ers.bukkit.utilities.models.enums.InventorySection;
import teik.ers.bukkit.utilities.models.enums.SortType;

public class InventoryMG {
    public final ReportMG reportMG;

    public final ConfigInvsMG configInvsMG;
    public final InvDataMG invDataMG;
    public final InvHelperMG invHelperMG;

    public final InvChannelsMG invChannelsMG;
    public final UpdateInvsMG updateInvsMG;

    public final InvsUtils invsUtils;
    public final UtilitiesPlayers utilitiesPlayers;
    public final UtilitiesMsgs utilitiesMsgs;

    public final LanguagesManager messageManager;

    public final boolean isMysql;

    private final ReportedsM reportedsM;
    private final ServersM serversM;
    private final DataM dataM;
    private final ReportsM reportsM;
    private final IfReportsM ifReportsM;

    public InventoryMG(EpicReports plugin) {
        this.reportMG = plugin.reportMG;

        this.configInvsMG = plugin.configInvsMG;
        this.invDataMG = new InvDataMG();
        this.invHelperMG = new InvHelperMG(plugin.reportMG.getPlayersListMG(), plugin.reportMG.getReportsListMG(),
                plugin.utilitiesPlayers, plugin.configManager.isMYSQLActive());

        this.invChannelsMG = new InvChannelsMG(plugin);
        this.updateInvsMG = new UpdateInvsMG(this);

        this.invsUtils = new InvsUtils(plugin.configInvsMG.getPagePanels());
        this.utilitiesPlayers = plugin.utilitiesPlayers;
        this.utilitiesMsgs = plugin.utilitiesMsgs;

        this.messageManager = plugin.messageManager;

        this.isMysql = plugin.configManager.isMYSQLActive();

        this.reportedsM = new ReportedsM(this);
        this.serversM = new ServersM(this, plugin.querysUT);
        this.dataM = new DataM(this);
        this.reportsM = new ReportsM(this);
        this.ifReportsM = new IfReportsM(this);
    }

    //OPEN INVENTORIES

    public void openReportedsM(Player player, int page, SortType sortType, FilterType filterType, String server){
        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.ReportedsMenu);

        inventoryPlayer.setPage(page);
        setSortType(player, sortType, inventoryPlayer);
        setFilterType(player, filterType, inventoryPlayer);
        if(server != null) inventoryPlayer.setServerName(server);

        reportedsM.createReportedsMenu(inventoryPlayer);
    }

    public void openServersM(Player player, int page, InventorySection lastInventory){
        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.ServersMenu);
        inventoryPlayer.setPage(page);
        inventoryPlayer.setLastInventorySection(lastInventory);

        serversM.createServersMenu(inventoryPlayer);
    }

    public void openDataM(Player player){
        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.DataMenu);
        dataM.createDataMenu(inventoryPlayer);
    }

    public void openReportsM(Player player, int page, SortType sortType, FilterType filterType, String server){
        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.ReportsMenu);

        inventoryPlayer.setPage(page);
        setSortTypeReports(player, sortType, inventoryPlayer);
        setFilterTypeReports(player, filterType, inventoryPlayer);
        if(server != null) inventoryPlayer.setServerName(server);

        reportsM.createReportsMenu(inventoryPlayer);
    }

    public void openIfReportsM(Player player){
        InventoryPlayer inventoryPlayer = new InventoryPlayer(player, InventorySection.InfoReportMenu);

        ifReportsM.createInfoReportMenu(inventoryPlayer);
    }

    //UTILS

    private void setSortType(Player player, SortType sortType, InventoryPlayer inventoryPlayer) {
        if(sortType !=null) inventoryPlayer.setSortType(sortType);
        else{
            inventoryPlayer.setSortType(invDataMG.getPlayerSortType(player));}
    }

    private void setFilterType(Player player, FilterType filterType, InventoryPlayer inventoryPlayer) {
        if(filterType != null) inventoryPlayer.setFilterType(filterType);
        else{
            inventoryPlayer.setFilterType(invDataMG.getPlayerFilterType(player));}
    }

    private void setSortTypeReports(Player player, SortType sortType, InventoryPlayer inventoryPlayer) {
        if(sortType !=null) inventoryPlayer.setSortType(sortType);
        else{
            inventoryPlayer.setSortType(invDataMG.getPlayerSortTypeReports(player));}
    }

    private void setFilterTypeReports(Player player, FilterType filterType, InventoryPlayer inventoryPlayer) {
        if(filterType != null) inventoryPlayer.setFilterType(filterType);
        else{
            inventoryPlayer.setFilterType(invDataMG.getPlayerFilterTypeReports(player));}
    }
}
