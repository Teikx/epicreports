package teik.ers.bukkit.managers.notifys;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import teik.ers.bukkit.configs.ConfigManager;
import teik.ers.bukkit.configs.LanguagesManager;
import teik.ers.bukkit.utilities.UtilitiesPlayers;
import teik.ers.global.models.objects.Notify;

public class NotifysSender {

    private final NotifysMG notifysManager;

    private final ConfigManager configManager;
    private final LanguagesManager messageManager;

    private final UtilitiesPlayers utilitiesPlayers;

    public NotifysSender(NotifysMG notifysManager, ConfigManager configManager, LanguagesManager messageManager, UtilitiesPlayers utilitiesPlayers) {
        this.notifysManager = notifysManager;

        this.configManager = configManager;
        this.messageManager = messageManager;

        this.utilitiesPlayers = utilitiesPlayers;
    }

    public void notifyAllTypes(Notify notify){
        notifysManager.setLastProcessedID(notify.getNotifyID());
        if(notify.isUserType()){
            if(configManager.isNotify_processed_sound()) notifySound(notify);
            if(configManager.isNotify_processed_title()) notifyTitle(notify);
            if(configManager.isNotify_processed_msg()) notifyMessage(notify);
            return;
        }
        if(configManager.isNotify_sound()) notifySound(notify);
        if(configManager.isNotify_title()) notifyTitle(notify);
        if(configManager.isNotify_msg()) notifyMessage(notify);
    }

    public void notifySkip(){
        if(configManager.isNotify_sound()) {
            String path = configManager.getReport_sound();
            sendSoundToAdmins(path);
        }
        if(configManager.isNotify_title()) {
            sendTitleToAdmins(
                    setReplace(messageManager.getNotify_skipped_reports_title_title()),
                    setReplace(messageManager.getNotify_skipped_reports_title_subtitle())
            );
        }
        if(configManager.isNotify_msg()) {
            sendMessageToAdmins(setReplace(messageManager.getNotify_skipped_reports_msg()));
        }
    }

    private void notifySound(Notify notify) {
        String path = notify.isUserType() ? configManager.getReport_processed_sound() : configManager.getReport_sound();
        if (notify.isUserType()) {
            Player playerToSend = utilitiesPlayers.getPlayer(notify.getReporterNick());
            if (playerToSend != null) {
                generateSound(playerToSend, path);
            }
        } else {
            sendSoundToAdmins(path);
        }
    }

    private void generateSound(Player playerToSend, String path) {
        String[] soundData = path.split(";");
        try {
            int volume = parseInt(soundData[1], 1);
            float pitch = parseFloat(soundData[2], 1.0f);
            Sound sound = getSound(soundData[0]);
            playerToSend.playSound(playerToSend.getLocation(), sound, volume, pitch);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(
                    messageManager.getConsole_sound_not_found().replaceAll("%sound_name%", soundData[0])
            );
        }
    }

    private void sendSoundToAdmins(String path) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("EpicReports.Admin")) {
                generateSound(player, path);
            }
        }
    }

    private Sound getSound(String soundName) {
        try {
            return Sound.valueOf(soundName);
        } catch (IllegalArgumentException e) {
            return Sound.valueOf("NOTE_PLING");
        }
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private float parseFloat(String value, float defaultValue) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void notifyTitle(Notify notify) {
        String title, subtitle;
        if (notify.isUserType()) {
            if (notify.isProcessed()) {
                title = messageManager.getNotify_processed_reports_title_title();
                subtitle = messageManager.getNotify_processed_reports_title_subtitle();
            } else {
                title = messageManager.getNotify_processing_reports_title_title();
                subtitle = messageManager.getNotify_processing_reports_title_subtitle();
            }
            Player playerToSend = utilitiesPlayers.getPlayer(notify.getReporterNick());
            if (playerToSend != null) {
                sendTitle(playerToSend, title, subtitle, notify);
            }
        } else {
            title = setReplace(messageManager.getNotify_reports_title_title(), notify);
            subtitle = setReplace(messageManager.getNotify_reports_title_subtitle(), notify);
            sendTitleToAdmins(
                    title,
                    subtitle
            );
        }
    }

    private void sendTitle(Player player, String title, String subtitle, Notify notify) {
        title = setReplace(title, notify);
        subtitle = setReplace(subtitle, notify);
        player.sendTitle(title, subtitle);
    }

    private void sendTitleToAdmins(String title, String subtitle) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("EpicReports.Admin")) {
                player.sendTitle(title, subtitle);
            }
        }
    }

    private void notifyMessage(Notify notify) {
        String message;
        if (notify.isUserType()) {
            message = notify.isProcessed() ?
                    messageManager.getNotify_processed_reports_msg() :
                    messageManager.getNotify_processing_reports_msg();

            Player playerToSend = Bukkit.getPlayer(notify.getStaffNick());
            if (playerToSend != null) {
                sendMessage(playerToSend, message, notify);
            }
        } else {
            String msg = setReplace(messageManager.getNotify_reports_msg(), notify);
            sendMessageToAdmins(msg);
        }
    }

    private void sendMessage(Player player, String message, Notify notify) {
        message = setReplace(message, notify);
        player.sendMessage(message);
    }

    private void sendMessageToAdmins(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("EpicReports.Admin")) {
                player.sendMessage(message);
            }
        }
    }

    private String setReplace(String path, Notify notify) {
        path = setReplace(path, "%reporter%", notify.getReporterNick());
        path = setReplace(path, "%reported%", notify.getReportedNick());
        if (notify.isUserType()) {
            path = setReplace(path, "%staff%", notify.getStaffNick());
        }
        return path;
    }

    private String setReplace(String msg, String placeholder, String newText) {
        return msg.replaceAll(placeholder, newText);
    }

    private String setReplace(String msg){
        return msg.replaceAll("%size%", configManager.getMinimum_size_to_skip() + "");
    }
}
