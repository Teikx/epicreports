package teik.ers.bukkit.configs;

import org.bukkit.configuration.file.FileConfiguration;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;

public class DiscordManager {
    private final CustomConfig configFile;

    private boolean isDiscordActive, img_active;
    private String serverID, channelID;
    private String title, reportedDataTitle, reportedDataDescription, reporterDataTitle,
            reporterDataDescription, reason, date, status, image;

    public DiscordManager(EpicReports plugin) {
        this.configFile = new CustomConfig("discord-config.yml", null, null, plugin, false);
        this.configFile.registerConfig();
        loadConfig();
    }

    private void loadConfig() {
        FileConfiguration config = this.configFile.getConfig();
        this.isDiscordActive = config.getBoolean("Discord_Settings.Discord_active");
        this.img_active = config.getBoolean("Discord_img.active");
        this.serverID = config.getString("Discord_Settings.Discord.serverID");
        this.channelID = config.getString("Discord_Settings.Discord.channelID");
        this.title = config.getString("Discord_Msg.Title");
        this.reportedDataTitle = config.getString("Discord_Msg.reported_data.Title");
        this.reportedDataDescription = config.getString("Discord_Msg.reported_data.Description");
        this.reporterDataTitle = config.getString("Discord_Msg.reporter_data.Title");
        this.reporterDataDescription = config.getString("Discord_Msg.reporter_data.Description");
        this.reason = config.getString("Discord_Msg.reason");
        this.date = config.getString("Discord_Msg.date");
        this.status = config.getString("Discord_Msg.status");
        this.image = config.getString("Discord_img.image");
    }

    public void reloadConfig() {
        this.configFile.reloadConfig();
        loadConfig();
    }

    public CustomConfig getConfigFile() {
        return this.configFile;
    }

    public String get(String key) {
        switch (key.toLowerCase()) {
            case "serverid": return getServerID();
            case "channelid": return getChannelID();
            case "img_active": return String.valueOf(isImg_active());
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

    public boolean isDiscordActive() {
        return isDiscordActive;
    }

    public boolean isImg_active() {
        return img_active;
    }

    public String getServerID() {
        return serverID;
    }

    public String getChannelID() {
        return channelID;
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

    public void setDiscordActive(boolean discordActive) {
        isDiscordActive = discordActive;
    }

    public String getImage() {
        return image;
    }
}
