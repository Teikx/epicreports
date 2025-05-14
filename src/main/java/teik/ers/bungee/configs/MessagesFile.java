package teik.ers.bungee.configs;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import teik.ers.bungee.EpicReports;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@SuppressWarnings("ALL")
public class MessagesFile {
    File messagesFile;
    Configuration messages;

    EpicReports plugin;

    private String prefix, report_successful, player_not_exist, max_reports_to_player, report_yourself,
            reason_invalid, console_report, disabled_plugin, player_not_found, server_not_found, teleported;

    public MessagesFile(EpicReports plugin) throws IOException {
        this.plugin = plugin;
        ReloadMessages();
    }

    public File getFile(){
        return messagesFile;
    }
    public Configuration getMessages(){
        return messages;
    }

    public void ReloadMessages() throws IOException {
        RegisterMessages();
        loadMessages();
    }

    private void RegisterMessages() throws IOException {
        try {
            makeConfig();
        } catch (Exception e) {
            makeConfigAlternative();
        }
    }
    private void makeConfig() throws IOException {
        messagesFile = new File(plugin.getDataFolder(), "bungee-messages.yml");

        if (!messagesFile.exists()) {
            try (InputStream in = plugin.getResourceAsStream("bungee-messages.yml")) {
                Files.copy(in, messagesFile.toPath());
            }
        }
        messages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(messagesFile);
    }
    private void makeConfigAlternative() throws IOException {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        messagesFile = new File(plugin.getDataFolder(), "bungee-messages.yml");

        if (!messagesFile.exists()) {
            try (InputStream in = plugin.getResourceAsStream("bungee-messages.yml")) {
                Files.copy(in, messagesFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        messages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(messagesFile);
    }

    private void loadMessages() {
        this.prefix = messages.getString("Prefix");
        this.report_successful = prefix + messages.getString("User_msgs.report_successful");
        this.player_not_exist = prefix + messages.getString("User_msgs.player_not_exist");
        this.max_reports_to_player = prefix + messages.getString("User_msgs.max_reports_to_player");
        this.report_yourself = prefix + messages.getString("User_msgs.report_yourself");
        this.reason_invalid = prefix + messages.getString("User_msgs.reason_invalid");
        this.disabled_plugin = prefix + messages.getString("User_msgs.disabled_plugin");
        this.console_report = prefix + messages.getString("Admin_msgs.console_report");
        this.player_not_found = prefix + messages.getString("Admin_msgs.player_not_found");
        this.server_not_found = prefix + messages.getString("Admin_msgs.server_not_found");
        this.teleported = prefix + messages.getString("Admin_msgs.teleported");
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getReport_successful() {
        return report_successful;
    }

    public String getPlayer_not_exist() {
        return player_not_exist;
    }

    public String getMax_reports_to_player() {
        return max_reports_to_player;
    }

    public String getReport_yourself() {
        return report_yourself;
    }

    public String getReason_invalid() {
        return reason_invalid;
    }

    public String getConsole_report() {
        return console_report;
    }

    public String getDisabled_plugin() {
        return disabled_plugin;
    }

    public String getPlayer_not_found() {
        return player_not_found;
    }

    public String getServer_not_found() {
        return server_not_found;
    }

    public String getTeleported() {
        return teleported;
    }
}
