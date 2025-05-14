package teik.ers.bukkit.managers.notifys;

import org.bukkit.scheduler.BukkitRunnable;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.ConfigManager;
import teik.ers.global.models.objects.Notify;

import java.sql.Connection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class NotifysMG {
    private final EpicReports plugin;
    private final Connection connection;
    private final ConfigManager configManager;

    private int LastProcessedID = 0;
    private final Queue<Notify> NotifysUserQueue = new LinkedList<>();
    private final Deque<Notify> NotifysAdminQueue = new LinkedList<>();

    private final NotifysSender notifysSender;
    private NotifysSql notifysSql;

    public NotifysMG(EpicReports plugin) {
        this.plugin = plugin;
        this.connection = plugin.getConnection();
        this.configManager = plugin.configManager;

        this.notifysSender = new NotifysSender(this, configManager, plugin.messageManager, plugin.utilitiesPlayers);

        if(configManager.isMYSQLActive()) StartSqlNotifyCheck();
        StartSendNotify();
    }

    private void SendUserNotify(){
        notifysSender.notifyAllTypes(Objects.requireNonNull(NotifysUserQueue.poll()));
    }
    private void SendAdminsNotify(){
        notifysSender.notifyAllTypes(Objects.requireNonNull(NotifysAdminQueue.poll()));
    }

    private void StartSendNotify(){
        (new BukkitRunnable() {
            public void run() {
                if(!NotifysUserQueue.isEmpty()){
                    SendUserNotify();
                }
                if(!NotifysAdminQueue.isEmpty()){
                    if(configManager.isSkip_reports()){
                        if(NotifysAdminQueue.size() >= configManager.getMinimum_size_to_skip()){
                            LastProcessedID = NotifysAdminQueue.pollLast().getNotifyID();
                            NotifysAdminQueue.clear();
                            notifysSender.notifySkip();
                            return;
                        }
                    }
                    SendAdminsNotify();
                }
            }
        }).runTaskTimer(plugin, 0L, 60L);
    }
    private void StartSqlNotifyCheck(){
        notifysSql = new NotifysSql(this, connection);

        (new BukkitRunnable() {
            public void run() {
                notifysSql.checkNewNotifications(getLastProcessedID());
            }
        }).runTaskTimer(plugin, 0L, 100L);
    }

    public void AddNotifyToUserQueue(Notify notify){
        NotifysUserQueue.add(notify);
    }
    public void AddNotifyToAdminQueue(Notify notify){
        NotifysAdminQueue.add(notify);
    }

    public void SkipNotifys(){
        NotifysAdminQueue.clear();
    }

    public int getLastProcessedID() {
        return LastProcessedID;
    }
    public void setLastProcessedID(int lastProcessedID) {
        LastProcessedID = lastProcessedID;
    }
}
