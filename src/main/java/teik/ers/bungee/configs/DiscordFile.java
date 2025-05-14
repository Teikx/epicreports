package teik.ers.bungee.configs;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import teik.ers.bungee.EpicReports;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class DiscordFile {
    File discordFile;
    Configuration discord;

    EpicReports plugin;

    private boolean isDiscordActive;
    private String serverID, channelID, image;
    private String title, reportedDataTitle, reportedDataDescription, reporterDataTitle,
            reporterDataDescription, reason, date, status;

    public DiscordFile(EpicReports plugin) throws IOException {
        this.plugin = plugin;
        reloadDiscord();
    }

    public File getFile(){
        return discordFile;
    }
    public Configuration getDiscord() {
        return discord;
    }

    public void reloadDiscord() throws IOException {
        registerConfig();
        loadConfig();
    }

    private void registerConfig() throws IOException {
        try {
            makeConfig();
        }catch (Exception e) {
            makeConfigAlternative();
        }
    }

    private void makeConfig() throws IOException {
        discordFile = new File(plugin.getDataFolder(), "discord-config.yml");

        if (!discordFile.exists()) {
            try (InputStream in = plugin.getResourceAsStream("discord-config.yml")) {
                Files.copy(in, discordFile.toPath());
            }
        }
        discord = ConfigurationProvider.getProvider(YamlConfiguration.class).load(discordFile);
    }
    private void makeConfigAlternative() throws IOException {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        discordFile = new File(plugin.getDataFolder(), "discord-config.yml");

        if (!discordFile.exists()) {
            try (InputStream in = plugin.getResourceAsStream("discord-config.yml")) {
                Files.copy(in, discordFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        discord = ConfigurationProvider.getProvider(YamlConfiguration.class).load(discordFile);
    }

    public void loadConfig() {
        this.isDiscordActive = discord.getBoolean("Discord_Settings.Discord_active");
        this.serverID = discord.getString("Discord_Settings.Discord.serverID");
        this.channelID = discord.getString("Discord_Settings.Discord.channelID");
        this.image = discord.getString("Discord_Settings.Discord.discord_image");
        this.title = discord.getString("Discord_Msg.Title");
        this.reportedDataTitle = discord.getString("Discord_Msg.reported_data.Title");
        this.reportedDataDescription = discord.getString("Discord_Msg.reported_data.Description");
        this.reporterDataTitle = discord.getString("Discord_Msg.reporter_data.Title");
        this.reporterDataDescription = discord.getString("Discord_Msg.reporter_data.Description");
        this.reason = discord.getString("Discord_Msg.reason");
        this.date = discord.getString("Discord_Msg.date");
        this.status = discord.getString("Discord_Msg.status");
    }

    public boolean isDiscordActive() {
        return isDiscordActive;
    }

    public String get(String key) {
        switch (key.toLowerCase()) {
            case "serverid": return getServerID();
            case "channelid": return getChannelID();
            case "image": return getImage();
            case "title": return getTitle();
            case "reporteddatatitle": return getReportedDataTitle();
            case "reporteddatadescription": return getReportedDataDescription();
            case "reporterdatatitle": return getReporterDataTitle();
            case "reporterdatadescription": return getReporterDataDescription();
            case "reason": return getReason();
            case "date": return getDate();
            case "status": return getStatus();
            default: throw new IllegalArgumentException("Unknown key in DiscordFile: " + key);
        }
    }

    public String getServerID() {
        return serverID;
    }

    public String getChannelID() {
        return channelID;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getReportedDataTitle() {
        return reportedDataTitle;
    }

    public String getReportedDataDescription() {
        return reportedDataDescription;
    }

    public String getReporterDataTitle() {
        return reporterDataTitle;
    }

    public String getReporterDataDescription() {
        return reporterDataDescription;
    }

    public String getReason() {
        return reason;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}
