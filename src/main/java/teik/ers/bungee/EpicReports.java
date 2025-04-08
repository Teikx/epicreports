package teik.ers.bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import teik.ers.bukkit.listeners.QuitJoinL;
import teik.ers.bungee.commands.ReportCommandBungee;
import teik.ers.bungee.configs.ConfigFile;
import teik.ers.bungee.configs.MessagesFile;
import teik.ers.bungee.managers.ListenersManager;
import teik.ers.bungee.mysql.ConnectionBungee;
import teik.ers.bungee.notifys.NotifysSql;
import teik.ers.bungee.utilities.MsgsUtilsBungee;
import teik.ers.bungee.utilities.PlayersUtilsBungee;
import teik.ers.global.utils.querys.AddQuerysUT;
import teik.ers.global.utils.querys.QuerysUT;

import java.io.IOException;
import java.sql.Connection;


public class EpicReports extends Plugin {
    public ConfigFile configFile;
    public MessagesFile messagesFile;

    public MsgsUtilsBungee msgsUtilsBungee;
    public PlayersUtilsBungee playersUtilsBungee;
    public NotifysSql notifysSql;
    public QuerysUT querysUT;
    public AddQuerysUT addQuerysUT;

    private ConnectionBungee connection;

    public boolean isMysql = false;

    @Override
    public void onEnable() {
        registerConfigs();
        ConnectionSQL();
        registerUtils();
        registerCommands();
        registerChannel();
        registerManagers();
        msgsUtilsBungee.pluginMessage(true, this);
    }

    @Override
    public void onDisable() {
        notifysSql.clearNotifysTable();
        msgsUtilsBungee.pluginMessage(false, this);
    }

    //Registers

    private void registerConfigs(){
        try {
            configFile = new ConfigFile(this);
            messagesFile = new MessagesFile(this);
        } catch (IOException ignored) {}
    }

    private void registerUtils(){
        msgsUtilsBungee = new MsgsUtilsBungee();
        playersUtilsBungee = new PlayersUtilsBungee();
        notifysSql = new NotifysSql(getMySQL());
        querysUT = new QuerysUT(getMySQL());
        addQuerysUT = new AddQuerysUT(getMySQL());
    }

    private void registerCommands(){
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ReportCommandBungee(this));
    }

    private void registerChannel(){
        ProxyServer.getInstance().registerChannel("epicreports:main");
    }

    private void registerManagers(){
        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        if(isMysql) {
            pluginManager.registerListener(this, new ListenersManager(this));
        }
    }

    //Sql

    private void ConnectionSQL() {
        String host = configFile.getHost();
        int port = configFile.getPort();
        String database = configFile.getDatabase();
        String user = configFile.getUser();
        String password = configFile.getPassword();
        this.connection = new ConnectionBungee(host, port, database, user, password, this);
    }

    public Connection getMySQL() {
        return this.connection.getConnection();
    }
}
