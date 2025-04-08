package teik.ers.bukkit.configs;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;
import teik.ers.bukkit.configs.utils.DataFolderConfigManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LanguagesManager extends DataFolderConfigManager {
    String path;
    CustomConfig messageFile;

    private String prefix, not_permission, report_successful, command_use, player_not_exist, max_reports_to_player, report_yourself,
            notify_processing_reports_msg, notify_processing_reports_title_title, notify_processing_reports_title_subtitle,
            notify_processed_reports_msg, notify_processed_reports_title_title, notify_processed_reports_title_subtitle, console_report,
            console_sound_not_found, reports_save_and_load_error, reload_successful, reports_save_successful, reports_load_successful,
            notify_reports_msg, notify_reports_title_title, notify_reports_title_subtitle, notify_skipped_reports_title_title,
            notify_skipped_reports_title_subtitle, notify_skipped_reports_msg, reported_name, click_here, click_here_show_text_hover,
            player_freeze_not_online, player_kick_not_online, player_kick_successful, player_not_found, player_banned, player_unbanned,
            player_customize_action, player_customize_action_error, location_not_found;
    private List<String> help_msg, console_help_msg;

    public LanguagesManager(EpicReports plugin, String folderName, String path) {
        super(plugin, folderName);
        this.path = plugin.getDataFolder() + File.separator + folderName + File.separator + path;
        messageFile = getConfigFile(this.path);
        loadMessages();
        checkearMessages();
    }

    @Override
    public void loadConfigs() {

    }

    @Override
    public void saveConfigs() {}

    private String convertColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public void loadMessages() {
        FileConfiguration config = this.messageFile.getConfig();
        this.prefix = convertColor(config.getString("Prefix")) + " ";
        this.not_permission = this.prefix + convertColor(config.getString("User_msgs.not_permission"));
        this.report_successful = this.prefix + convertColor(config.getString("User_msgs.report_successful"));
        this.command_use = this.prefix + convertColor(config.getString("User_msgs.command_use"));
        this.player_not_exist = this.prefix + convertColor(config.getString("User_msgs.player_not_exist"));
        this.max_reports_to_player = this.prefix + convertColor(config.getString("User_msgs.max_reports_to_player"));
        this.report_yourself = this.prefix + convertColor(config.getString("User_msgs.report_yourself"));
        this.notify_processing_reports_msg = this.prefix + convertColor(config.getString("User_msgs.notify_processing_reports_msg"));
        this.notify_processing_reports_title_title = convertColor(config.getString("User_msgs.notify_processing_reports_title.title"));
        this.notify_processing_reports_title_subtitle = convertColor(config.getString("User_msgs.notify_processing_reports_title.subtitle"));
        this.notify_processed_reports_msg = this.prefix + convertColor(config.getString("User_msgs.notify_processed_reports_msg"));
        this.notify_processed_reports_title_title = convertColor(config.getString("User_msgs.notify_processed_reports_title.title"));
        this.notify_processed_reports_title_subtitle = convertColor(config.getString("User_msgs.notify_processed_reports_title.subtitle"));
        this.console_report = this.prefix + convertColor(config.getString("Admin_msgs.console_report"));
        this.console_sound_not_found = this.prefix + convertColor(config.getString("Admin_msgs.console_sound_not_found"));
        this.reports_save_and_load_error = this.prefix + convertColor(config.getString("Admin_msgs.reports_save_and_load_error"));
        this.reload_successful = this.prefix + convertColor(config.getString("Admin_msgs.reload_successful"));
        this.reports_save_successful = this.prefix + convertColor(config.getString("Admin_msgs.reports_save_successful"));
        this.reports_load_successful = this.prefix + convertColor(config.getString("Admin_msgs.reports_load_successful"));
        this.notify_reports_msg = this.prefix + convertColor(config.getString("Admin_msgs.notify_reports_msg"));
        this.notify_reports_title_title = convertColor(config.getString("Admin_msgs.notify_reports_title.title"));
        this.notify_reports_title_subtitle = convertColor(config.getString("Admin_msgs.notify_reports_title.subtitle"));
        this.notify_skipped_reports_msg = convertColor(config.getString("Admin_msgs.notify_skipped_reports_msg"));
        this.notify_skipped_reports_title_title = convertColor(config.getString("Admin_msgs.notify_skipped_reports_title.title"));
        this.notify_skipped_reports_title_subtitle = convertColor(config.getString("Admin_msgs.notify_skipped_reports_title.subtitle"));
        this.help_msg = config.getStringList("Admin_msgs.help_msg");
        this.console_help_msg = config.getStringList("Admin_msgs.console_help_msg");
        this.reported_name = this.prefix + convertColor(config.getString("Admin_msgs.reported_name"));
        this.click_here = this.prefix + convertColor(config.getString("Admin_msgs.click_here"));
        this.click_here_show_text_hover = convertColor(config.getString("Admin_msgs.click_here_show_text_hover"));
        this.player_freeze_not_online = this.prefix + convertColor(config.getString("Admin_msgs.player_freeze_not_online"));
        this.player_kick_not_online = this.prefix + convertColor(config.getString("Admin_msgs.player_kick_not_online"));
        this.player_kick_successful = this.prefix + convertColor(config.getString("Admin_msgs.player_kick_successful"));
        this.player_not_found = this.prefix + convertColor(config.getString("Admin_msgs.player_not_found"));
        this.player_banned = this.prefix + convertColor(config.getString("Admin_msgs.player_banned"));
        this.player_unbanned = this.prefix + convertColor(config.getString("Admin_msgs.player_unbanned"));
        this.player_customize_action = this.prefix + convertColor(config.getString("Admin_msgs.player_customize_action"));
        this.player_customize_action_error = this.prefix + convertColor(config.getString("Admin_msgs.player_customize_action_error"));
        this.location_not_found = this.prefix + convertColor(config.getString("Admin_msgs.location_not_found"));
    }

    public void reloadConfig() {
        this.messageFile.reloadConfig();
        loadMessages();
    }

    private void checkearMessages() {
        Path archivo = Paths.get(this.messageFile.getPath(), new String[0]);
        try {
            String text = new String(Files.readAllBytes(archivo));
            if (!text.contains("report_yourself:")) {
                this.messageFile.getConfig().set("User_msgs.report_yourself", "&cYou cant report yourself!");
                this.messageFile.saveConfig();
                reloadConfig();
            }
            if (!text.contains("notify_processing_reports_msg:")) {
                this.messageFile.getConfig().set("User_msgs.notify_processing_reports_msg", "&6Your report to &c%reported% &6is now processing by: &f%staff%");
                this.messageFile.getConfig().set("User_msgs.notify_processing_reports_title.title", "&6Your Report is Processing!");
                this.messageFile.getConfig().set("User_msgs.notify_processing_reports_title.subtitle", "&eReported: &c%reported% &f |&e By &f%staff%");
                this.messageFile.getConfig().set("User_msgs.notify_processed_reports_msg", "&eYour report to &c%reported% &ehas been processed by: &f%staff%");
                this.messageFile.getConfig().set("User_msgs.notify_processed_reports_title.title", "&eYour Report is Processed!");
                this.messageFile.getConfig().set("User_msgs.notify_processed_reports_title.subtitle", "&6Reported: &c%reported% &6| By &f%staff%");
                this.messageFile.saveConfig();
                reloadConfig();
            }
            if (!text.contains("location_not_found:")) {
                this.messageFile.getConfig().set("Admin_msgs.location_not_found", "&cLocation not found!");
                this.messageFile.getConfig().set("Admin_msgs.server_not_found", "&cServer not found!");
                this.messageFile.getConfig().set("Admin_msgs.notify_skipped_reports_msg", "&eYou have more than &c%size% &enew reports!");
                this.messageFile.getConfig().set("Admin_msgs.notify_skipped_reports_title.title", "&c%size% &6New Reports!");
                this.messageFile.getConfig().set("Admin_msgs.notify_skipped_reports_title.subtitle", "&eYou have &c%size% &enew reports!");
                reloadConfig();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CustomConfig getMessageFile() {
        return this.messageFile;
    }
    public String getPrefix() {
        return this.prefix;
    }
    public String getNot_permission() {
        return this.not_permission;
    }
    public String getReport_successful() {
        return this.report_successful;
    }
    public String getCommand_use() {
        return this.command_use;
    }
    public String getPlayer_not_exist() {
        return this.player_not_exist;
    }
    public String getMax_reports_to_player() {
        return this.max_reports_to_player;
    }
    public String getReport_yourself() {
        return this.report_yourself;
    }
    public String getNotify_processing_reports_msg() {
        return this.notify_processing_reports_msg;
    }
    public String getNotify_processing_reports_title_title() {
        return this.notify_processing_reports_title_title;
    }
    public String getNotify_processing_reports_title_subtitle() {
        return this.notify_processing_reports_title_subtitle;
    }
    public String getNotify_processed_reports_msg() {
        return this.notify_processed_reports_msg;
    }
    public String getNotify_processed_reports_title_title() {
        return this.notify_processed_reports_title_title;
    }
    public String getNotify_processed_reports_title_subtitle() {
        return this.notify_processed_reports_title_subtitle;
    }
    public String getConsole_report() {
        return this.console_report;
    }
    public String getConsole_sound_not_found() {
        return this.console_sound_not_found;
    }
    public String getReports_save_and_load_error() {
        return this.reports_save_and_load_error;
    }
    public String getReload_successful() {
        return this.reload_successful;
    }
    public String getReports_save_successful() {
        return this.reports_save_successful;
    }
    public String getReports_load_successful() {return this.reports_load_successful;}
    public String getNotify_reports_msg() {
        return this.notify_reports_msg;
    }
    public String getNotify_reports_title_title() {
        return this.notify_reports_title_title;
    }
    public String getNotify_reports_title_subtitle() {
        return this.notify_reports_title_subtitle;
    }
    public String getNotify_skipped_reports_title_title() {
        return notify_skipped_reports_title_title;
    }
    public String getNotify_skipped_reports_title_subtitle() {
        return notify_skipped_reports_title_subtitle;
    }
    public String getNotify_skipped_reports_msg() {
        return notify_skipped_reports_msg;
    }
    public String getReported_name() {
        return this.reported_name;
    }
    public String getClick_here() {
        return this.click_here;
    }
    public String getClick_here_show_text_hover() {
        return this.click_here_show_text_hover;
    }
    public String getPlayer_freeze_not_online() {
        return this.player_freeze_not_online;
    }
    public String getPlayer_kick_not_online() {
        return this.player_kick_not_online;
    }
    public String getPlayer_kick_successful() {
        return this.player_kick_successful;
    }
    public String getPlayer_not_found() {
        return this.player_not_found;
    }
    public String getPlayer_banned() {
        return this.player_banned;
    }
    public String getPlayer_unbanned() {
        return this.player_unbanned;
    }
    public String getPlayer_customize_action() {
        return this.player_customize_action;
    }
    public String getPlayer_customize_action_error() {
        return this.player_customize_action_error;
    }
    public String getLocation_not_found() {
        return this.location_not_found;
    }
    public List<String> getHelp_msg() {
        return this.help_msg;
    }
    public List<String> getConsole_help_msg() {
        return this.console_help_msg;
    }
}
