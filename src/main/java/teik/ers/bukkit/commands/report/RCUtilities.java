package teik.ers.bukkit.commands.report;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.managers.notifys.NotifysMG;
import teik.ers.bukkit.managers.reportmg.ReportMG;
import teik.ers.bukkit.utilities.UtilitiesPlayers;
import teik.ers.global.mgs.DiscordMG;
import teik.ers.global.models.objects.Notify;
import teik.ers.global.models.objects.PlayerOBJ;
import teik.ers.global.models.objects.Report;
import teik.ers.global.mgs.MaxReportsMG;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.function.Consumer;

public class RCUtilities {
    private final ReportMG reportMG;
    private final NotifysMG notifysManager;

    private final UtilitiesPlayers utilitiesPlayers;
    private final String reportSuccessful;

    private final MaxReportsMG maxReportsMG;
    private final DiscordMG discordMG;


    public RCUtilities(EpicReports plugin, String reportSuccessful,
                       MaxReportsMG maxReportsMG) {
        this.reportMG = plugin.reportMG;
        this.notifysManager = plugin.notifysMG;

        this.utilitiesPlayers = plugin.utilitiesPlayers;
        this.reportSuccessful = reportSuccessful;

        this.maxReportsMG = maxReportsMG;
        this.discordMG = new DiscordMG(plugin.ersDiscord, true);
    }

    public void addReport(Player reporter, String reportedName, String reason){
        //Date
        String dateFormatted = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"));

        UUID reportedUuid = utilitiesPlayers.getUUID(reportedName);
        Report newReport = new Report(reason, dateFormatted);
        setInfoInReport(newReport, reporter, reportedUuid);

        //AddReport
        reportMG.addReport(newReport);

        //Add count to max reports
        maxReportsMG.addNewReportPlayer(reporter.getUniqueId().toString(), reportedName);

        //Send msg report successful
        reporter.sendMessage(reportSuccessful
                .replaceAll("%reported%", reportedName)
        );


        //Send notify
        Notify notify = new Notify(reporter.getName(), reportedName, 0);
        sendNotify(notify);

        //Send notify to discord
        discordMG.sendDiscordMsg(newReport);
    }

    private void sendNotify(Notify notify){
        if(notify.isUserType()){
            notifysManager.AddNotifyToUserQueue(notify);
            return;
        }
        notifysManager.AddNotifyToAdminQueue(notify);
    }

    private void setInfoInReport(Report report, Player reporter, UUID reportedUUID) {
        report.setDateTime(LocalDateTime.now());
        setReporterInfo(report, reporter);
        setReportedInfo(report, reportedUUID);
    }

    private void setReporterInfo(Report report, Player reporter) {
        String reporterUUID = reporter.getUniqueId().toString();

        PlayerOBJ reporterOBJ = new PlayerOBJ(
                reporter.getName(),
                reporterUUID,
                utilitiesPlayers.getPlayerIP(reporter));

        report.setReporterUUID(reporterUUID);
        report.setReporterPlayer(reporterOBJ);

        Consumer<String> setLocation = report::setReporterLocation;
        Consumer<String> setGamemode = report::setReporterGamemode;
        Consumer<Integer> setHealth = report::setReporterHealth;

        setConsumers(reporter, setLocation, setGamemode, setHealth);
    }

    private void setReportedInfo(Report report, UUID reportedUUID) {
        String reportedUUIDS = reportedUUID.toString();

        report.setReportedUUID(reportedUUIDS);

        Player reported = utilitiesPlayers.getPlayer(reportedUUID);

        if(reported == null){
            OfflinePlayer reportedOffline = Bukkit.getOfflinePlayer(reportedUUID);
            setOfflineInfo(report, reportedOffline);
            return;
        }

        PlayerOBJ reporterOBJ = new PlayerOBJ(
                reported.getName(),
                reportedUUIDS,
                utilitiesPlayers.getPlayerIP(reported));
        report.setReportedPlayer(reporterOBJ);

        Consumer<String> setLocation = report::setReportedLocation;
        Consumer<String> setGamemode = report::setReportedGamemode;
        Consumer<Integer> setHealth = report::setReportedHealth;

        setConsumers(reported, setLocation, setGamemode, setHealth);
    }

    private void setConsumers(Player player, Consumer<String> setLocation, Consumer<String> setGamemode, Consumer<Integer> setHealth) {
        setLocation.accept(utilitiesPlayers.getPlayerLocate(player).createString());
        setGamemode.accept(player.getGameMode().toString());
        setHealth.accept((int) Math.round(player.getHealth()));
    }

    private void setOfflineInfo(Report report, OfflinePlayer reported){
        PlayerOBJ reporterOBJ = new PlayerOBJ(
                reported.getName(),
                reported.getUniqueId().toString(),
                "0.0.0.0");

        report.setReportedPlayer(reporterOBJ);

        report.setReportedLocation("offline");
        report.setReportedGamemode("offline");
        report.setReportedHealth(20);
    }
}
