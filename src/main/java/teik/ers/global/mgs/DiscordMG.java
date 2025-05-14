package teik.ers.global.mgs;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import teik.ers.bukkit.configs.DiscordManager;
import teik.ers.bungee.EpicReports;
import teik.ers.global.models.objects.Report;

import java.awt.*;
import java.util.function.Function;

public class DiscordMG {
    private JDA jda;
    private boolean isDiscordActive;
    private String serverID, channelID, image;
    private String title, reportedDataTitle, reportedDataDescription,
            reporterDataTitle, reporterDataDescription, reason, date, status;

    private static final String discordToken = "ASD";
    private static final String BOT_ACTIVITY_URL = "https://www.spigotmc.org/resources/112351/";
    private static final String FOOTER = "EpicReports • Discord Bot • @Teikx";

    public DiscordMG(DiscordManager discordManager) {
        boolean initialized = initializeBot();
        loadConfigData(discordManager::get);
        if(initialized) {
            Bukkit.getConsoleSender().sendMessage(
                    ChatColor.translateAlternateColorCodes('&',
                            "&b[&fEpicReports&b]&5 Discord Bot&a connected!")

            );
            return;
        }
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&',
                "&b[&fEpicReports&b]&c Error 503: Error connecting to Discord bot. Contact the developer.")
        );
    }

    public DiscordMG(EpicReports plugin) {
        boolean initialized = initializeBot();
        loadConfigData(key -> plugin.discordFile.get(key));

        if(initialized) {
            plugin.getLogger().info(
                    net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&',
                            "&b[&fEpicReports&b]&5 Discord Bot&a connected!")
            );
            return;
        }
        plugin.getLogger().info(
                net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&',
                        "&b[&fEpicReports&b]&c Error 503: Error connecting to Discord bot. Contact the developer.")
        );
    }

    private boolean initializeBot() {
        try {
            jda = JDABuilder
                    .createLight(discordToken)
                    .setActivity(Activity.streaming("EpicReports", BOT_ACTIVITY_URL))
                    .build();

            isDiscordActive = true;
            return true;
        } catch (Exception e) {
            isDiscordActive = false;
            jda = null;
            return false;
        }
    }

    private void loadConfigData(Function<String, String> configGetter) {
        this.serverID = configGetter.apply("serverID");
        this.channelID = configGetter.apply("channelID");
        this.image = configGetter.apply("image");

        this.title = configGetter.apply("title");
        this.reportedDataTitle = configGetter.apply("reportedDataTitle");
        this.reportedDataDescription = configGetter.apply("reportedDataDescription");
        this.reporterDataTitle = configGetter.apply("reporterDataTitle");
        this.reporterDataDescription = configGetter.apply("reporterDataDescription");
        this.reason = configGetter.apply("reason");
        this.date = configGetter.apply("date");
        this.status = configGetter.apply("status");
    }

    public void sendEmbed(Report report) {
        Guild guild = jda.getGuildById(serverID);
        if (guild == null) {
            logError("504", "serverID");
            return;
        }

        TextChannel channel = guild.getTextChannelById(channelID);
        if (channel == null) {
            logError("505", "channelID");
            return;
        }

        EmbedBuilder embed = buildEmbed(report);
        channel.sendMessageEmbeds(embed.build()).queue();
    }

    private EmbedBuilder buildEmbed(Report report) {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle("📝 " + title.replace("%reportID%", String.valueOf(report.getReportID())));
        embed.setColor(Color.GREEN);
        embed.setTimestamp(report.getDateTime().atZone(java.time.ZoneId.systemDefault()).toInstant());
        embed.setFooter(FOOTER, null);

        embed.addField("\u200B", "\u200B", false);

        embed.addField("🎯 " + reportedDataTitle,
                format(report, reportedDataDescription, true), false);

        embed.addField("\u200B", "\u200B", false);

        embed.addField("🗣️ " + reporterDataTitle,
                format(report, reporterDataDescription, false), false);

        embed.addField("\u200B", "\u200B", false);

        embed.addField("📄 " + reason, report.getReason(), false);
        embed.addField("\u200B", "\u200B", false);

        embed.addField("📅 " + date, report.getDate(), true);
        embed.addField("🔄 " + status, report.getProcess().toString(), true);

        return embed;
    }

    private String format(Report r, String template, boolean isReported) {
        return template
                .replace("%" + (isReported ? "reportedName" : "reporterName") + "%", isReported ? r.getReportedName() : r.getReporterName())
                .replace("%" + (isReported ? "reportedUUID" : "reporterUUID") + "%", isReported ? r.getReportedUUID() : r.getReporterUUID())
                .replace("%" + (isReported ? "reportedGamemode" : "reporterGamemode") + "%", isReported ? r.getReportedGamemode() : r.getReporterGamemode())
                .replace("%" + (isReported ? "reportedHealth" : "reporterHealth") + "%", String.valueOf(isReported ? r.getReportedHealth() : r.getReporterHealth()))
                .replace("%" + (isReported ? "reportedServer" : "reporterServer") + "%", isReported ? r.getReportedServer() : r.getReporterServer())
                .replace("%" + (isReported ? "reportedLocation" : "reporterLocation") + "%", isReported ? r.getReportedLocation() : r.getReporterLocation())
                .replace("%" + (isReported ? "reportedIP" : "reporterIP") + "%", isReported ? r.getReportedIP() : r.getReporterIP());
    }

    private void logError(String code, String configKey) {
        System.out.println(String.format("[EpicReports] Error %s: Could not connect using %s. Check your discord-config.yml!", code, configKey));
        isDiscordActive = false;
    }

    public boolean isDiscordActive() {
        return isDiscordActive;
    }
}
