package teik.ers.bungee.commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import teik.ers.bungee.EpicReports;
import teik.ers.bungee.configs.MessagesFile;
import teik.ers.bungee.notifys.NotifysSql;
import teik.ers.bungee.utilities.MsgsUtilsBungee;
import teik.ers.bungee.utilities.PlayersUtilsBungee;
import teik.ers.global.mgs.DiscordMG;
import teik.ers.global.models.objects.Notify;
import teik.ers.global.models.objects.PlayerOBJ;
import teik.ers.global.models.objects.Report;
import teik.ers.global.mgs.MaxReportsMG;
import teik.ers.global.utils.querys.AddQuerysUT;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

@SuppressWarnings("ALL")
public class ReportUtilitiesBungee {

    private final MessagesFile messagesFile;

    private final MsgsUtilsBungee msgsUtilsBungee;
    private final PlayersUtilsBungee playersUtilsBungee;

    private final NotifysSql notifysSql;
    private final AddQuerysUT addQuerysUT;

    private final MaxReportsMG maxReportsMG;
    private final DiscordMG discordMG;

    public ReportUtilitiesBungee(EpicReports plugin, MaxReportsMG maxReportsMG) {
        this.messagesFile = plugin.messagesFile;

        this.msgsUtilsBungee = plugin.msgsUtilsBungee;
        this.playersUtilsBungee = plugin.playersUtilsBungee;

        this.notifysSql = plugin.notifysSql;
        this.addQuerysUT = plugin.addQuerysUT;

        this.maxReportsMG = maxReportsMG;
        this.discordMG = new DiscordMG(plugin.ersDiscord, false);
    }

    public void addReport(ProxiedPlayer reporter, String[] args) {

        //Date
        String dateFormatted = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"));

        //Reason
        String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));


        //Set Report
        String reportedName = args[0];
        UUID reportedUuid = playersUtilsBungee.getUUIDperName(reportedName);
        Report newReport = new Report(reason, dateFormatted);
        setInfoInReport(newReport, reporter, reportedUuid, reportedName);

        //Add Report
        int ReportID = addQuerysUT.addReportSQL(newReport);

        //Add max reports count
        maxReportsMG.addNewReportPlayer(reporter.getUniqueId().toString(), reportedName);

        //Métodos para encontrar la ubicación de el reporter y reported
        sendDetailsPlayer(reporter.getName(), ReportID, false);
        sendDetailsPlayer(reportedName, ReportID, true);

        //Send msg report successful
        reporter.sendMessage(
                msgsUtilsBungee.convertBungeeColor(
                        msgsUtilsBungee.replaceInText(messagesFile.getReport_successful(), "%reported%", reportedName)
                )
        );
        Notify notify = new Notify(reporter.getName(), reportedName, 0);
        addNotify(notify);

        //Send notify to discord
        discordMG.sendDiscordMsg(newReport);
    }

    private void setInfoInReport(Report report, ProxiedPlayer reporter, UUID reportedUUID, String reportedName){
        report.setDateTime(LocalDateTime.now());

        report.setReporterUUID(reporter.getUniqueId().toString());
        report.setReportedUUID(reportedUUID.toString());

        report.setReporterPlayer(setPlayerObj(reporter));

        ProxiedPlayer reported = playersUtilsBungee.getOnlineProxiedPlayer(reportedUUID);
        report.setReportedPlayer(setPlayerObj(reported));

        report.setReporterServer(reporter.getServer().getInfo().getName());
        report.setReportedServer(reported.getServer().getInfo().getName());
    }

    private PlayerOBJ setPlayerObj(ProxiedPlayer player){
        PlayerOBJ playerOBJ = new PlayerOBJ(
                player.getName(),
                player.getUniqueId().toString(),
                player.getAddress().getAddress().getHostAddress()
        );
        return playerOBJ;
    }

    private void addNotify(Notify notify) {
        notifysSql.addNotification(
                notify.getReporterNick(),
                notify.getReportedNick(),
                notify.getStaffNick(),
                notify.isProcessed()
        );
    }

    private void sendDetailsPlayer(String playerName, int ReportID, boolean isReported) {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playerName);
        if (player != null) getPlayerLocation(player, ReportID, isReported);
    }

    private void getPlayerLocation(ProxiedPlayer player, int ReportID, boolean isReported){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        if(isReported){
            out.writeUTF("GetReportedDetails");
        }else{
            out.writeUTF("GetReporterDetails");
        }

        out.writeUTF(player.getName());
        out.writeInt(ReportID);

        player.getServer().sendData("epicreports:main", out.toByteArray());
    }

    public void openOnlinePlys(ProxiedPlayer player){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("openOnlinePlayersMenu");

        out.writeUTF(player.getName());

        player.getServer().sendData("epicreports:main", out.toByteArray());
    }

    public void openPredefinedReports(ProxiedPlayer player, String reportedName){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("openPredefinedMenu");

        out.writeUTF(player.getName());
        out.writeUTF(reportedName);

        player.getServer().sendData("epicreports:main", out.toByteArray());
    }
}
