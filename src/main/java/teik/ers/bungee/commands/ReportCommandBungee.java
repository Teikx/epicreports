package teik.ers.bungee.commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import teik.ers.bungee.EpicReports;
import teik.ers.bungee.configs.MessagesFile;
import teik.ers.bungee.utilities.MsgsUtilsBungee;
import teik.ers.bungee.utilities.PlayersUtilsBungee;
import teik.ers.global.utils.MaxReportsMG;

import java.util.UUID;

public class ReportCommandBungee extends Command {
    private final EpicReports plugin;

    private final boolean isMysql;

    private final MessagesFile messagesFile;
    private final MsgsUtilsBungee msgsUtilsBungee;
    private final PlayersUtilsBungee playersUtilsBungee;
    private final MaxReportsMG maxReportsMG;

    private final ReportUtilitiesBungee reportUtilitiesBungee;

    public ReportCommandBungee(EpicReports plugin){
        super("report");
        this.plugin = plugin;

        isMysql = plugin.isMysql;

        this.messagesFile = plugin.messagesFile;
        this.msgsUtilsBungee = plugin.msgsUtilsBungee;
        this.playersUtilsBungee = plugin.playersUtilsBungee;

        maxReportsMG = new MaxReportsMG(plugin.configFile.getMax_reports_size());

        reportUtilitiesBungee = new ReportUtilitiesBungee(plugin, maxReportsMG);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if(!isMysql){
                player.sendMessage(msgsUtilsBungee.convertBungeeColor(messagesFile.getDisabled_plugin()));
                return;
            }
            if (args.length < 1) {
                player.sendMessage(msgsUtilsBungee.convertBungeeColor(messagesFile.getCommand_use()));
                return;
            }

            String reportedName = args[0];
            UUID reportedUUID = playersUtilsBungee.getUUIDperName(reportedName);

            if (reportedUUID == null) {
                player.sendMessage(msgsUtilsBungee.convertBungeeColor(messagesFile.getPlayer_not_exist()));
                return;
            }
            if (maxReportsMG.isMaxReport(player.getUniqueId().toString(), reportedName)) {
                player.sendMessage(msgsUtilsBungee.convertBungeeColor(messagesFile.getMax_reports_to_player()));
                return;
            }
            if (player.getName().equalsIgnoreCase(reportedName)) {
                player.sendMessage(msgsUtilsBungee.convertBungeeColor(messagesFile.getReport_yourself()));
                return;
            }
            reportUtilitiesBungee.addReport(player, args);
            sendUpdateReportsManager();
        }else{
            plugin.getLogger().info(ChatColor.translateAlternateColorCodes('&', messagesFile.getConsole_report()));
        }
    }

    private void sendUpdateReportsManager(){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("UpdateReportsManager");

        for (ServerInfo server : ProxyServer.getInstance().getServers().values()) {
            server.sendData("epicreports:main", out.toByteArray());
        }
    }
}
