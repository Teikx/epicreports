package teik.ers.bukkit.configs;

import org.bukkit.configuration.file.FileConfiguration;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;

public class ConfigManager {
    private final CustomConfig configFile;

    private boolean max_reports, reportOfflinePlayers = false, customizeCommand, activate_ers_menu_alias,
            notify_msg, notify_title, notify_sound, notify_processed_msg, notify_processed_title,
            notify_processed_sound, skip_reports, isMYSQLActive;

    private String host, database, user, password, customizedCommand, ers_menu_alias, report_sound,
            report_processed_sound, language;

    private int port, max_reports_size, minimum_size_to_skip;

    public ConfigManager(EpicReports plugin) {
        this.configFile = new CustomConfig("config.yml", null, null, plugin, false);
        this.configFile.registerConfig();
        loadConfig();
    }

    private void loadConfig() {
        FileConfiguration config = this.configFile.getConfig();
        this.isMYSQLActive = config.getBoolean("Network_Settings.MySQL-active");
        this.max_reports = config.getBoolean("Reports_Settings.Max_reports_per_person");
        this.max_reports_size = config.getInt("Reports_Settings.max_reports");
        if(!isMYSQLActive){
            this.reportOfflinePlayers = config.getBoolean("Reports_Settings.report_offline_players");
        }
        this.customizeCommand = config.getBoolean("Reports_Settings.toggle_customize_command");
        this.customizedCommand = config.getString("Reports_Settings.customized_command");
        this.activate_ers_menu_alias = config.getBoolean("Reports_Settings.activate_ers_menu_alias");
        this.ers_menu_alias = config.getString("Reports_Settings.ers_menu_alias");
        this.notify_msg = config.getBoolean("Notify_Settings.notify_reports_msg");
        this.notify_title = config.getBoolean("Notify_Settings.notify_reports_title");
        this.notify_sound = config.getBoolean("Notify_Settings.notify_reports_sounds");
        this.report_sound = config.getString("Notify_Settings.report_sound");
        this.notify_processed_msg = config.getBoolean("Notify_Settings.notify_processed_reports_msg");
        this.notify_processed_title = config.getBoolean("Notify_Settings.notify_processed_reports_title");
        this.notify_processed_sound = config.getBoolean("Notify_Settings.notify_processed_reports_sounds");
        this.report_processed_sound = config.getString("Notify_Settings.report_processed_sound");
        this.skip_reports = config.getBoolean("Notify_Settings.skip_reports");
        this.minimum_size_to_skip = config.getInt("Notify_Settings.minimum_size_to_skip");
        this.host = config.getString("Network_Settings.MySQL.host");
        this.port = config.getInt("Network_Settings.MySQL.port");
        this.database = config.getString("Network_Settings.MySQL.database");
        this.user = config.getString("Network_Settings.MySQL.user");
        this.password = config.getString("Network_Settings.MySQL.password");
        this.language = config.getString("Language");
    }

    public void saveConfig(){
        FileConfiguration config = this.configFile.getConfig();
        config.set("Reports_Settings.Max_reports_per_person", max_reports);
        config.set("Reports_Settings.max_reports", max_reports_size);
        config.set("Reports_Settings.toggle_customize_command", customizeCommand);
        config.set("Reports_Settings.customized_command", customizedCommand);
        config.set("Reports_Settings.activate_ers_menu_alias", activate_ers_menu_alias);
        config.set("Reports_Settings.ers_menu_alias", ers_menu_alias);
        config.set("Notify_Settings.skip_reports", skip_reports);
        config.set("Notify_Settings.minimum_size_to_skip", minimum_size_to_skip);
        config.set("Reports_Settings.report_offline_players", reportOfflinePlayers);
        config.set("Notify_Settings.notify_reports_msg", notify_msg);
        config.set("Notify_Settings.notify_reports_title", notify_title);
        config.set("Notify_Settings.notify_reports_sounds", notify_sound);
        config.set("Notify_Settings.report_sound", report_sound);
        config.set("Notify_Settings.notify_processed_reports_msg", notify_processed_msg);
        config.set("Notify_Settings.notify_processed_reports_title", notify_processed_title);
        config.set("Notify_Settings.notify_processed_reports_sounds", notify_processed_sound);
        config.set("Notify_Settings.report_processed_sound", report_processed_sound);
        this.configFile.saveConfig();
    }

    public void reloadConfig() {
        this.configFile.reloadConfig();
        loadConfig();
    }

    public CustomConfig getConfigFile() {
        return this.configFile;
    }

    public boolean isMax_reports() {
        return this.max_reports;
    }

    public boolean isReportOfflinePlayers() {
        return this.reportOfflinePlayers;
    }

    public boolean isCustomizeCommand() {
        return this.customizeCommand;
    }

    public boolean isActivate_ers_menu_alias() {
        return this.activate_ers_menu_alias;
    }

    public boolean isNotify_msg() {
        return this.notify_msg;
    }

    public boolean isNotify_title() {
        return this.notify_title;
    }

    public boolean isNotify_sound() {
        return this.notify_sound;
    }

    public boolean isNotify_processed_msg() {
        return this.notify_processed_msg;
    }

    public boolean isNotify_processed_title() {
        return this.notify_processed_title;
    }

    public boolean isNotify_processed_sound() {
        return this.notify_processed_sound;
    }

    public boolean isSkip_reports() {
        return this.skip_reports;
    }

    public boolean isMYSQLActive() {
        return this.isMYSQLActive;
    }

    public void setMYSQLActive(boolean MYSQLActive) {
        this.isMYSQLActive = MYSQLActive;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getDatabase() {
        return this.database;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    public int getMax_reports_size() {
        return this.max_reports_size;
    }

    public String getCustomizedCommand() {
        return this.customizedCommand;
    }

    public String getErs_menu_alias() {
        return this.ers_menu_alias;
    }

    public String getReport_sound() {
        return this.report_sound;
    }

    public String getReport_processed_sound() {
        return this.report_processed_sound;
    }

    public int getMinimum_size_to_skip() {
        return this.minimum_size_to_skip;
    }

    public String getLanguage() {
        return language;
    }

    public void setMax_reports(boolean max_reports) {
        this.max_reports = max_reports;
    }

    public void setReportOfflinePlayers(boolean reportOfflinePlayers) {
        this.reportOfflinePlayers = reportOfflinePlayers;
    }

    public void setCustomizeCommand(boolean customizeCommand) {
        this.customizeCommand = customizeCommand;
    }

    public void setActivate_ers_menu_alias(boolean activate_ers_menu_alias) {
        this.activate_ers_menu_alias = activate_ers_menu_alias;
    }

    public void setNotify_msg(boolean notify_msg) {
        this.notify_msg = notify_msg;
    }

    public void setNotify_title(boolean notify_title) {
        this.notify_title = notify_title;
    }

    public void setNotify_sound(boolean notify_sound) {
        this.notify_sound = notify_sound;
    }

    public void setReport_sound(String report_sound) {
        this.report_sound = report_sound;
    }

    public void setNotify_processed_msg(boolean notify_processed_msg) {
        this.notify_processed_msg = notify_processed_msg;
    }

    public void setNotify_processed_title(boolean notify_processed_title) {
        this.notify_processed_title = notify_processed_title;
    }

    public void setNotify_processed_sound(boolean notify_processed_sound) {
        this.notify_processed_sound = notify_processed_sound;
    }

    public void setReport_processed_sound(String report_processed_sound) {
        this.report_processed_sound = report_processed_sound;
    }

    public void setSkip_reports(boolean skip_reports) {
        this.skip_reports = skip_reports;

    }

    public void setCustomizedCommand(String customizedCommand) {
        this.customizedCommand = customizedCommand;
    }

    public void setErs_menu_alias(String ers_menu_alias) {
        this.ers_menu_alias = ers_menu_alias;
    }

    public void setMax_reports_size(int max_reports_size) {
        this.max_reports_size = max_reports_size;
    }

    public void setMinimum_size_to_skip(int minimum_size_to_skip) {
        this.minimum_size_to_skip = minimum_size_to_skip;
    }
}
