package teik.ers.bukkit.configs;

import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.inventories.commandsmenus.OnlinePlayersMenu;
import teik.ers.bukkit.configs.inventories.commandsmenus.PredefinedReportsMenu;
import teik.ers.bukkit.configs.inventories.helpermenus.ActionsMenu;
import teik.ers.bukkit.configs.inventories.helpermenus.InfoReportMenu;
import teik.ers.bukkit.configs.inventories.helpermenus.ServersMenu;
import teik.ers.bukkit.configs.inventories.mainmenus.AllReportsMenu;
import teik.ers.bukkit.configs.inventories.mainmenus.ReportedsMenu;
import teik.ers.bukkit.configs.inventories.mainmenus.ReportersMenu;
import teik.ers.bukkit.configs.inventories.others.ArchivedMenu;
import teik.ers.bukkit.configs.inventories.others.CommentsMenu;
import teik.ers.bukkit.configs.inventories.others.PagePanels;
import teik.ers.bukkit.configs.inventories.settingmenus.NotifysMenu;
import teik.ers.bukkit.configs.inventories.settingmenus.SettingsMenu;
import teik.ers.bukkit.configs.inventories.submenus.DataMenu;
import teik.ers.bukkit.configs.inventories.submenus.ReportsMenu;
import teik.ers.bukkit.configs.inventories.submenus.RrReportsMenu;

public class ConfigInvsMG {
    private final PagePanels pagePanels;
    private final ReportedsMenu reportedsMenu;
    private final ServersMenu serversMenu;
    private final DataMenu dataMenu;
    private final ReportsMenu reportsMenu;
    private final InfoReportMenu infoReportMenu;
    private final ActionsMenu actionsMenu;
    private final ArchivedMenu archivedMenu;
    private final CommentsMenu commentsMenu;
    private final AllReportsMenu allReportsMenu;
    private final ReportersMenu reportersMenu;
    private final RrReportsMenu rrReportsMenu;
    private final SettingsMenu settingsMenu;
    private final NotifysMenu notifysMenu;
    private final PredefinedReportsMenu predefinedReportsMenu;
    private final OnlinePlayersMenu onlinePlayersMenu;

    public ConfigInvsMG(EpicReports plugin) {
        pagePanels = new PagePanels(plugin);
        reportedsMenu = new ReportedsMenu(plugin);
        serversMenu = new ServersMenu(plugin);
        dataMenu = new DataMenu(plugin);
        reportsMenu = new ReportsMenu(plugin);
        infoReportMenu = new InfoReportMenu(plugin);
        actionsMenu = new ActionsMenu(plugin);
        archivedMenu = new ArchivedMenu(plugin);
        commentsMenu = new CommentsMenu(plugin);
        allReportsMenu = new AllReportsMenu(plugin);
        reportersMenu = new ReportersMenu(plugin);
        rrReportsMenu = new RrReportsMenu(plugin);
        settingsMenu = new SettingsMenu(plugin);
        notifysMenu = new NotifysMenu(plugin);
        predefinedReportsMenu = new PredefinedReportsMenu(plugin);
        onlinePlayersMenu = new OnlinePlayersMenu(plugin);
    }

    public void reloadConfigs(){
        pagePanels.reloadConfig();
        reportedsMenu.reloadConfig();
        serversMenu.reloadConfig();
        dataMenu.reloadConfig();
        reportsMenu.reloadConfig();
        infoReportMenu.reloadConfig();
        actionsMenu.reloadConfig();
        archivedMenu.reloadConfig();
        commentsMenu.reloadConfig();
        allReportsMenu.reloadConfig();
        reportersMenu.reloadConfig();
        rrReportsMenu.reloadConfig();
        settingsMenu.reloadConfig();
        notifysMenu.reloadConfig();
        predefinedReportsMenu.reloadConfig();
        onlinePlayersMenu.reloadConfig();
    }

    public PagePanels getPagePanels() {return pagePanels;}
    public ReportedsMenu getReportedsMenu() {return reportedsMenu;}
    public ServersMenu getServersMenu() {return serversMenu;}
    public DataMenu getDataMenu() {return dataMenu;}
    public ReportsMenu getReportsMenu() {return reportsMenu;}
    public InfoReportMenu getInfoReportMenu() {return infoReportMenu;}
    public ActionsMenu getActionsMenu() {return actionsMenu;}
    public ArchivedMenu getArchivedMenu() {return archivedMenu;}
    public CommentsMenu getCommentsMenu() {return commentsMenu;}
    public AllReportsMenu getAllReportsMenu() {return allReportsMenu;}
    public ReportersMenu getReportersMenu() {return reportersMenu;}
    public RrReportsMenu getRrReportsMenu() {return rrReportsMenu;}
    public SettingsMenu getSettingsMenu() {return settingsMenu;}
    public NotifysMenu getNotifysMenu() {return notifysMenu;}
    public PredefinedReportsMenu getPredefinedReportsMenu() {
        return predefinedReportsMenu;
    }
    public OnlinePlayersMenu getOnlinePlayersMenu() {return onlinePlayersMenu;}
}
