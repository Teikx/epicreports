package teik.ers.global.mgs;

import teik.ers.bukkit.ErsDiscord;
import teik.ers.global.models.objects.Report;

import java.time.LocalDateTime;

public class DiscordMG {

    //Bukkit
    private ErsDiscord ersDiscord;

    //Bungee
    private teik.ers.bungee.ErsDiscord ersDiscordBungee;

    //All
    private final boolean isBukkit;

    public DiscordMG(ErsDiscord ersDiscord, boolean isBukkit) {
        this.ersDiscord = ersDiscord;
        this.isBukkit = isBukkit;
    }

    public DiscordMG(teik.ers.bungee.ErsDiscord ersDiscordBungee, boolean isBukkit) {
        this.ersDiscordBungee = ersDiscordBungee;
        this.isBukkit = isBukkit;
    }

    public void sendDiscordMsg(Report newReport){
        if(!isDiscordActive()) return;

        teik.ers.global.models.Report report = convertReport(newReport);

        if(isBukkit){
            ersDiscord.sendDiscordMessage(report);
            return;
        }
        ersDiscordBungee.sendDiscordMessage(report);
    }

    private boolean isDiscordActive() {
        if(isBukkit){
            if(ersDiscord == null) return false;

            if(!ersDiscord.isDiscordActive()) return false;

            return true;
        }

        if(ersDiscordBungee == null) return false;

        if(!ersDiscordBungee.isDiscordActive()) return false;

        return true;
    }

    private teik.ers.global.models.Report convertReport(Report newReport){
        return new teik.ers.global.models.Report(
                newReport.getReason(),
                newReport.getDate(),
                newReport.getReporterUUID(),
                newReport.getReportedUUID(),
                newReport.getReporterName(),
                newReport.getReportedName(),
                newReport.getReporterLocation(),
                newReport.getReportedLocation(),
                newReport.getReporterServer(),
                newReport.getReportedServer(),
                newReport.getReportedGamemode(),
                newReport.getReporterGamemode(),
                newReport.getProcess().toString(),
                newReport.getReporterIP(),
                newReport.getReportedIP(),
                newReport.getReportedHealth(),
                newReport.getReporterHealth(),
                newReport.getReportID(),
                LocalDateTime.now()
        );
    }
}
