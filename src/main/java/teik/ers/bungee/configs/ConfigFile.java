package teik.ers.bungee.configs;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import teik.ers.bungee.EpicReports;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

@SuppressWarnings("ALL")
public class ConfigFile {
    File configFile;
    Configuration config;

    EpicReports plugin;

    private boolean max_reports, notifyReports, discord_Notifications;

    private String host, database, user, password;

    private int port, max_reports_size;

    private List<String> whitelistTab;

    public ConfigFile(EpicReports plugin) throws IOException {
        this.plugin = plugin;
        ReloadConfig();
    }

    public File getFile(){
        return configFile;
    }
    public Configuration getConfig() {
        return config;
    }

    public void ReloadConfig() throws IOException {
        RegisterConfig();
        loadConfig();
    }

    private void RegisterConfig() throws IOException {
        try {
            makeConfig();
        }catch (Exception e) {
            makeConfigAlternative();
        }
    }

    private void makeConfig() throws IOException {
        configFile = new File(plugin.getDataFolder(), "bungee-config.yml");

        if (!configFile.exists()) {
            try (InputStream in = plugin.getResourceAsStream("bungee-config.yml")) {
                Files.copy(in, configFile.toPath());
            }
        }
        config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
    }
    private void makeConfigAlternative() throws IOException {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        configFile = new File(plugin.getDataFolder(), "bungee-config.yml");

        if (!configFile.exists()) {
            try (InputStream in = plugin.getResourceAsStream("bungee-config.yml")) {
                Files.copy(in, configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
    }

    public void loadConfig() {
        this.max_reports = config.getBoolean("Reports_Settings.Max_reports_per_person");
        this.max_reports_size = config.getInt("Reports_Settings.max_reports");
        this.notifyReports = config.getBoolean("Notify_Settings.notify_reports");
        this.discord_Notifications = config.getBoolean("Discord_Notifications");
        this.host = config.getString("Network_Settings.MySQL.host");
        this.port = config.getInt("Network_Settings.MySQL.port");
        this.database = config.getString("Network_Settings.MySQL.database");
        this.user = config.getString("Network_Settings.MySQL.user");
        this.password = config.getString("Network_Settings.MySQL.password");
        this.whitelistTab = config.getStringList("Players_Whitelist_Tab");
    }

    public boolean isMax_reports() {
        return this.max_reports;
    }

    public boolean isDiscord_Notifications() {
        return discord_Notifications;
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

    public List<String> getWhitelistTab() {
        return whitelistTab;
    }
}
