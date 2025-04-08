package teik.ers.bukkit.configs;

import org.bukkit.configuration.file.FileConfiguration;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;

public class ConfigManager {
    private final CustomConfig configFile;

    private boolean max_reports, reportOfflinePlayers = false, customizeCommand, activate_ers_menu_alias,
            notify_msg, notify_title, notify_sound, notify_processed_msg, notify_processed_title,
            notify_processed_sound, isMYSQLActive, skip_reports;

    private String host, database, user, password, customizedCommand, ers_menu_alias, report_sound, report_processed_sound, language;

    private int port, max_reports_size, minimum_size_to_skip;

    public ConfigManager(EpicReports plugin) {
        this.configFile = new CustomConfig("config.yml", null, plugin, false);
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
        this.customizeCommand = config.getBoolean("Reports_Settings.toogle_customize_command");
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

    public void setLanguage(String language) {
        this.language = language;
    }
}
