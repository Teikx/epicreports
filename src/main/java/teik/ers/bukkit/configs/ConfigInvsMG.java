package teik.ers.bukkit.configs;

import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.inventories.*;

public class ConfigInvsMG {
    private final PagePanels pagePanels;
    private final ReportedsMenu reportedsMenu;
    private final ServersMenu serversMenu;
    private final DataMenu dataMenu;
    private final SettingsMenu settingsMenu;
    private final ReportsMenu reportsMenu;
    private final InfoReportMenu infoReportMenu;

    public ConfigInvsMG(EpicReports plugin) {
        pagePanels = new PagePanels(plugin);
        reportedsMenu = new ReportedsMenu(plugin);
        serversMenu = new ServersMenu(plugin);
        dataMenu = new DataMenu(plugin);
        reportsMenu = new ReportsMenu(plugin);
        infoReportMenu = new InfoReportMenu(plugin);
        settingsMenu = new SettingsMenu(plugin);
    }

    public void reloadConfigs(){
        pagePanels.reloadConfig();
        reportedsMenu.reloadConfig();
        serversMenu.reloadConfig();
        dataMenu.reloadConfig();
        reportsMenu.reloadConfig();
        infoReportMenu.reloadConfig();
        settingsMenu.reloadConfig();
    }

    public PagePanels getPagePanels() {return pagePanels;}
    public ReportedsMenu getReportedsMenu() {return reportedsMenu;}
    public ServersMenu getServersMenu() {return serversMenu;}
    public DataMenu getDataMenu() {return dataMenu;}
    public ReportsMenu getReportsMenu() {return reportsMenu;}
    public InfoReportMenu getInfoReportMenu() {return infoReportMenu;}
    public SettingsMenu getSettingsMenu() {return settingsMenu;}
}
