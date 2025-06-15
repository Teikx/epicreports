package teik.ers.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import teik.ers.bukkit.commands.AliasCommand;
import teik.ers.bukkit.commands.ErsCommand;
import teik.ers.bukkit.commands.report.RCommand;
import teik.ers.bukkit.completercmd.ERSCommandC;
import teik.ers.bukkit.completercmd.ReportCommandC;
import teik.ers.bukkit.configs.*;
import teik.ers.bukkit.listeners.ChatL;
import teik.ers.bukkit.listeners.FreezeL;
import teik.ers.bukkit.listeners.InvL;
import teik.ers.bukkit.listeners.QuitJoinL;
import teik.ers.bukkit.managers.bstats.BStatsBukkitMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.msgchannel.ChannelMG;
import teik.ers.bukkit.managers.notifys.NotifysMG;
import teik.ers.bukkit.managers.reportmg.ReportMG;
import teik.ers.bukkit.managers.sql.MysqlMG;
import teik.ers.bukkit.managers.sql.SqLiteMG;
import teik.ers.bukkit.utilities.UtilitiesMsgs;
import teik.ers.bukkit.utilities.UtilitiesPlayers;
import teik.ers.global.mgs.UpdateCheckerMG;
import teik.ers.global.utils.querys.QuerysUT;

import java.lang.reflect.Field;
import java.sql.Connection;

public class EpicReports extends JavaPlugin {
    //Configs
    public ConfigManager configManager;
    public LanguagesManager messageManager;
    public ConfigInvsMG configInvsMG;
    public ReasonsCtrlManager reasonsCtrlManager;
    public ErsDiscord ersDiscord;

    //Managers

    public ReportMG reportMG;
    public NotifysMG notifysMG;
    public InventoryMG inventoryMG;
    private BStatsBukkitMG bStatsMG;

    private MysqlMG mysqlMG;
    private SqLiteMG sqLiteMG;

    //Utilities
    public UtilitiesMsgs utilitiesMsgs;
    public UtilitiesPlayers utilitiesPlayers;
    public QuerysUT querysUT;

    @Override
    public void onEnable() {
        loadAllConfigs();
        this.utilitiesMsgs = new UtilitiesMsgs(messageManager);
        loadSql();
        loadDiscord();
        loadUtilities();
        loadManagers();
        loadCommands();
        loadTabCompleter();
        loadErsMenuAlias(configManager.getErs_menu_alias());
        loadListeners();
        utilitiesMsgs.pluginMessage(true);
        loadUpdater();
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
        this.reasonsCtrlManager = new ReasonsCtrlManager(this);
    }

    public void reloadAllConfigs(){
        this.configManager.reloadConfig();
        this.messageManager.reloadConfig();
        this.configInvsMG.reloadConfigs();
        this.reasonsCtrlManager.reloadConfig();

    }

    //LOAD

    private void loadSql(){
        if(configManager.isMYSQLActive()) {ConnectionMysql();}
        if(!configManager.isMYSQLActive()) {
            sqLiteMG = new SqLiteMG(getDataFolder().getAbsolutePath() + "/reports.db");}
    }

    private void loadDiscord(){
        if(!configManager.isDiscord_Notifications()) return;
        try{
            ersDiscord = (ErsDiscord) Bukkit.getPluginManager().getPlugin("ErsDiscord");
            if(ersDiscord == null) {
                Bukkit.getConsoleSender().sendMessage(
                        utilitiesMsgs.convertColor("&b[&fEpicReports&b]&c Discord notifications is disabled! " +
                                "We can't find the ErsDiscord plugin! " +
                                "Download it from: &6 https://www.spigotmc.org/resources/112351/")
                );
            }
        }catch (Exception e){
            ersDiscord = null;
            Bukkit.getConsoleSender().sendMessage(
                    utilitiesMsgs.convertColor("&b[&fEpicReports&b]&c Discord notifications is disabled! " +
                            "We can't find the ErsDiscord plugin! " +
                            "Download it from: &6 https://www.spigotmc.org/resources/112351/")
            );
        }
    }

    private void loadUtilities(){
        this.querysUT = new QuerysUT(getConnection());
        this.utilitiesPlayers = new UtilitiesPlayers(configManager, querysUT);
    }

    private void loadManagers(){
        reportMG = new ReportMG(this);
        notifysMG = new NotifysMG(this);
        inventoryMG = new InventoryMG(this);
        bStatsMG = new BStatsBukkitMG(this, 0000);

        ChannelMG channelMG = new ChannelMG(this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "epicreports:main");
        getServer().getMessenger().registerIncomingPluginChannel(this, "epicreports:main", channelMG);
    }

    private void loadCommands(){
        getCommand("report").setExecutor(new RCommand(this));
        getCommand("epicreports").setExecutor(new ErsCommand(this));
        getCommand("ers").setExecutor(new ErsCommand(this));
    }

    private void loadTabCompleter(){
        getCommand("report").setTabCompleter(new ReportCommandC(this));
        getCommand("epicreports").setTabCompleter(new ERSCommandC());
        getCommand("ers").setTabCompleter(new ERSCommandC());
    }

    public void loadErsMenuAlias(String name) {
        CommandMap commandMap = getCommandMap();
        AliasCommand aliasCommand = new AliasCommand(name);
        commandMap.register(name, aliasCommand);
    }


    private void loadListeners(){
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new QuitJoinL(this), this);
        pluginManager.registerEvents(new InvL(inventoryMG), this);
        pluginManager.registerEvents(new FreezeL(inventoryMG), this);
        pluginManager.registerEvents(new ChatL(inventoryMG), this);
    }

    private void loadUpdater(){
        new UpdateCheckerMG(112351).getVersion( version -> {
            if(this.getDescription().getVersion().equals(version)){
                Bukkit.getConsoleSender().sendMessage(
                        utilitiesMsgs.convertColor("&b[&fEpicReports&b]&a You are using the latest version!")
                );
                return;
            }
            Bukkit.getConsoleSender().sendMessage(
                    utilitiesMsgs.convertColor("&b[&fEpicReports&b]&e There is a new version available! " +
                            "Download it from: &6 https://www.spigotmc.org/resources/112351/")
            );
        });
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

    //Utilities Dynamic Command
    private CommandMap getCommandMap() {
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            return (CommandMap) field.get(Bukkit.getServer());
        } catch (Exception e) {
            System.out.print("[EpicReports] Error 502 (Use Dynamic Command): " + e.getMessage());
            return null;
        }
    }

}
