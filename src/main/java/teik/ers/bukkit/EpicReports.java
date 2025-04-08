package teik.ers.bukkit;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import teik.ers.bukkit.commands.ErsCommand;
import teik.ers.bukkit.commands.report.RCommand;
import teik.ers.bukkit.configs.ConfigInvsMG;
import teik.ers.bukkit.configs.ConfigManager;
import teik.ers.bukkit.configs.LanguagesManager;
import teik.ers.bukkit.listeners.InvL;
import teik.ers.bukkit.listeners.QuitJoinL;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.msgchannel.ChannelMG;
import teik.ers.bukkit.managers.notifys.NotifysMG;
import teik.ers.bukkit.managers.reportmg.ReportMG;
import teik.ers.bukkit.managers.sql.MysqlMG;
import teik.ers.bukkit.managers.sql.SqLiteMG;
import teik.ers.bukkit.utilities.UtilitiesMsgs;
import teik.ers.bukkit.utilities.UtilitiesPlayers;
import teik.ers.global.utils.querys.QuerysUT;

import java.sql.Connection;

public class EpicReports extends JavaPlugin {
    //Configs
    public ConfigManager configManager;
    public LanguagesManager messageManager;
    public ConfigInvsMG configInvsMG;

    //Managers
    public ReportMG reportMG;
    public NotifysMG notifysMG;
    public InventoryMG inventoryMG;

    private MysqlMG mysqlMG;
    private SqLiteMG sqLiteMG;

    //Utilities
    public UtilitiesMsgs utilitiesMsgs;
    public UtilitiesPlayers utilitiesPlayers;
    public QuerysUT querysUT;

    @Override
    public void onEnable() {
        loadAllConfigs();
        loadSql();
        loadUtilities();
        loadManagers();
        loadCommands();
        loadListeners();
        utilitiesMsgs.pluginMessage(true);
    }

    @Override
    public void onDisable() {
        reportMG.onDisable();
        utilitiesMsgs.pluginMessage(false);
    }

    //CONFIGS

    private void loadAllConfigs(){
        this.configManager = new ConfigManager(this);
        this.messageManager = new LanguagesManager(this, "languages", configManager.getLanguage());
        this.configInvsMG = new ConfigInvsMG(this);
    }

    public void reloadAllConfigs(){
        this.configManager.reloadConfig();
    }

    //LOAD

    private void loadSql(){
        if(configManager.isMYSQLActive()) {ConnectionMysql();}
        if(!configManager.isMYSQLActive()) {
            sqLiteMG = new SqLiteMG(getDataFolder().getAbsolutePath() + "/reports.db");}
    }

    private void loadUtilities(){
        this.querysUT = new QuerysUT(getConnection());
        this.utilitiesMsgs = new UtilitiesMsgs(messageManager);
        this.utilitiesPlayers = new UtilitiesPlayers(configManager, querysUT);
    }

    private void loadManagers(){
        reportMG = new ReportMG(this);
        notifysMG = new NotifysMG(this);
        inventoryMG = new InventoryMG(this);

        ChannelMG channelMG = new ChannelMG(this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "epicreports:main");
        getServer().getMessenger().registerIncomingPluginChannel(this, "epicreports:main", channelMG);
    }

    private void loadCommands(){
        getCommand("report").setExecutor(new RCommand(this));
        getCommand("epicreports").setExecutor(new ErsCommand(this));
    }

    private void loadListeners(){
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new QuitJoinL(this), this);
        pluginManager.registerEvents(new InvL(inventoryMG), this);
    }

    //SQL

    public Connection getConnection() {
        if(configManager.isMYSQLActive()) return this.mysqlMG.getConnection();
        return this.sqLiteMG.getConnection();
    }

    private void ConnectionMysql() {
        String host = configManager.getHost();
        int port = configManager.getPort();
        String database = configManager.getDatabase();
        String user = configManager.getUser();
        String password = configManager.getPassword();
        this.mysqlMG = new MysqlMG(host, port, database, user, password, configManager);

    }
}
