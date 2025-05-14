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

    private String prefix, not_permission, report_successful, player_not_exist, max_reports_to_player, report_yourself,
            reason_invalid, notify_processing_reports_msg, notify_processing_reports_title_title,
            notify_processing_reports_title_subtitle, notify_processed_reports_msg, notify_processed_reports_title_title,
            notify_processed_reports_title_subtitle, console_report, console_sound_not_found, reload_successful,
            reports_save_successful, notify_reports_msg, notify_reports_title_title, notify_reports_title_subtitle,
            notify_skipped_reports_title_title, notify_skipped_reports_title_subtitle, notify_skipped_reports_msg,
            click_here, click_here_show_text_hover, player_banned, player_kick, player_mute, player_freeze,
            player_unfreeze, player_freeze_not_online, player_command_error, location_not_found, write_comment_title_title,
            write_comment_title_subtitle, write_comment_msg, comments_successful, comments_cancelled, write_text_title_title,
            write_text_title_subtitle, write_text_msg, text_successful, text_cancelled;
    private List<String> help_msg, console_help_msg;

    public LanguagesManager(EpicReports plugin, String folderName, String path) {
        super(plugin, folderName, null);
        this.path = plugin.getDataFolder() + File.separator + folderName + File.separator + path;
        messageFile = getConfigFile(this.path);
        loadMessages();
        checkearMessages();
    }

    @Override
    public void loadConfigs() {}

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
        this.player_not_exist = this.prefix + convertColor(config.getString("User_msgs.player_not_exist"));
        this.max_reports_to_player = this.prefix + convertColor(config.getString("User_msgs.max_reports_to_player"));
        this.report_yourself = this.prefix + convertColor(config.getString("User_msgs.report_yourself"));
        this.reason_invalid = this.prefix + convertColor(config.getString("User_msgs.reason_invalid"));
        this.notify_processing_reports_msg = this.prefix + convertColor(config.getString("User_msgs.notify_processing_reports_msg"));
        this.notify_processing_reports_title_title = convertColor(config.getString("User_msgs.notify_processing_reports_title.title"));
        this.notify_processing_reports_title_subtitle = convertColor(config.getString("User_msgs.notify_processing_reports_title.subtitle"));
        this.notify_processed_reports_msg = this.prefix + convertColor(config.getString("User_msgs.notify_processed_reports_msg"));
        this.notify_processed_reports_title_title = convertColor(config.getString("User_msgs.notify_processed_reports_title.title"));
        this.notify_processed_reports_title_subtitle = convertColor(config.getString("User_msgs.notify_processed_reports_title.subtitle"));
        this.console_report = this.prefix + convertColor(config.getString("Admin_msgs.console_report"));
        this.console_sound_not_found = this.prefix + convertColor(config.getString("Admin_msgs.console_sound_not_found"));
        this.reload_successful = this.prefix + convertColor(config.getString("Admin_msgs.reload_successful"));
        this.reports_save_successful = this.prefix + convertColor(config.getString("Admin_msgs.reports_save_successful"));
        this.notify_reports_msg = this.prefix + convertColor(config.getString("Admin_msgs.notify_reports_msg"));
        this.notify_reports_title_title = convertColor(config.getString("Admin_msgs.notify_reports_title.title"));
        this.notify_reports_title_subtitle = convertColor(config.getString("Admin_msgs.notify_reports_title.subtitle"));
        this.notify_skipped_reports_msg = this.prefix + convertColor(config.getString("Admin_msgs.notify_skipped_reports_msg"));
        this.notify_skipped_reports_title_title = convertColor(config.getString("Admin_msgs.notify_skipped_reports_title.title"));
        this.notify_skipped_reports_title_subtitle = convertColor(config.getString("Admin_msgs.notify_skipped_reports_title.subtitle"));
        this.help_msg = config.getStringList("Admin_msgs.help_msg");
        this.console_help_msg = config.getStringList("Admin_msgs.console_help_msg");
        this.click_here = this.prefix + convertColor(config.getString("Admin_msgs.click_here"));
        this.click_here_show_text_hover = convertColor(config.getString("Admin_msgs.click_here_show_text_hover"));
        this.player_banned = this.prefix + convertColor(config.getString("Admin_msgs.player_banned"));
        this.player_kick = this.prefix + convertColor(config.getString("Admin_msgs.player_kick"));
        this.player_mute = this.prefix + convertColor(config.getString("Admin_msgs.player_mute"));
        this.player_freeze = this.prefix + convertColor(config.getString("Admin_msgs.player_freeze"));
        this.player_unfreeze = this.prefix + convertColor(config.getString("Admin_msgs.player_unfreeze"));
        this.player_freeze_not_online = this.prefix + convertColor(config.getString("Admin_msgs.player_freeze_not_online"));
        this.player_command_error = this.prefix + convertColor(config.getString("Admin_msgs.player_command_error"));
        this.location_not_found = this.prefix + convertColor(config.getString("Admin_msgs.location_not_found"));
        this.write_comment_title_title = convertColor(config.getString("Admin_msgs.write_comment_title.title"));
        this.write_comment_title_subtitle = convertColor(config.getString("Admin_msgs.write_comment_title.subtitle"));
        this.write_comment_msg = this.prefix + convertColor(config.getString("Admin_msgs.write_comment_msg"));
        this.comments_successful = this.prefix + convertColor(config.getString("Admin_msgs.comments_successful"));
        this.comments_cancelled = this.prefix + convertColor(config.getString("Admin_msgs.comments_cancelled"));
        this.write_text_title_title = convertColor(config.getString("Admin_msgs.write_text_title.title"));
        this.write_text_title_subtitle = convertColor(config.getString("Admin_msgs.write_text_title.subtitle"));
        this.write_text_msg = this.prefix + convertColor(config.getString("Admin_msgs.write_text_msg"));
        this.text_successful = this.prefix + convertColor(config.getString("Admin_msgs.text_successful"));
        this.text_cancelled = this.prefix + convertColor(config.getString("Admin_msgs.text_cancelled"));
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CustomConfig getMessageFile() {
        return this.messageFile;
    }
    public String getNot_permission() {
        return this.not_permission;
    }
    public String getReport_successful() {
        return this.report_successful;
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
    public String getReason_invalid() {
        return reason_invalid;
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
    public String getReload_successful() {
        return this.reload_successful;
    }
    public String getReports_save_successful() {
        return this.reports_save_successful;
    }
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
    public String getClick_here() {
        return this.click_here;
    }
    public String getClick_here_show_text_hover() {
        return this.click_here_show_text_hover;
    }
    public String getPlayer_banned() {
        return this.player_banned;
    }
    public String getPlayer_kick() {
        return this.player_kick;
    }
    public String getPlayer_mute() {
        return player_mute;
    }
    public String getPlayer_freeze() {
        return player_freeze;
    }
    public String getPlayer_unfreeze() {
        return player_unfreeze;
    }
    public String getPlayer_freeze_not_online() {
        return this.player_freeze_not_online;
    }
    public String getPlayer_command_error() {
        return player_command_error;
    }
    public String getLocation_not_found() {
        return this.location_not_found;
    }
    public String getWrite_comment_title_title() {
        return write_comment_title_title;
    }
    public String getWrite_comment_title_subtitle() {
        return write_comment_title_subtitle;
    }
    public String getWrite_comment_msg() {
        return write_comment_msg;
    }
    public String getComments_successful() {
        return comments_successful;
    }
    public String getComments_cancelled() {
        return comments_cancelled;
    }
    public String getWrite_text_title_title() {
        return write_text_title_title;
    }
    public String getWrite_text_title_subtitle() {
        return write_text_title_subtitle;
    }
    public String getWrite_text_msg() {
        return write_text_msg;
    }
    public String getText_successful() {
        return text_successful;
    }
    public String getText_cancelled() {
        return text_cancelled;
    }
    public List<String> getHelp_msg() {
        return this.help_msg;
    }
    public List<String> getConsole_help_msg() {
        return this.console_help_msg;
    }

}
