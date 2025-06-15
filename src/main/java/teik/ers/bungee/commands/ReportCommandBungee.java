package teik.ers.bungee.commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import teik.ers.bungee.EpicReports;
import teik.ers.bungee.configs.MessagesFile;
import teik.ers.bungee.utilities.MsgsUtilsBungee;
import teik.ers.bungee.utilities.PlayersUtilsBungee;
import teik.ers.global.mgs.MaxReportsMG;
import teik.ers.global.mgs.ReasonsCtrlMG;
import de.myzelyam.api.vanish.BungeeVanishAPI;

import java.util.*;

public class ReportCommandBungee extends Command implements TabExecutor {
    private final EpicReports plugin;

    private final boolean isMysql;

    private final MessagesFile messagesFile;
    private final List<String> whitelistTab;

    private final MsgsUtilsBungee msgsUtilsBungee;
    private final PlayersUtilsBungee playersUtilsBungee;

    private final MaxReportsMG maxReportsMG;
    private final ReasonsCtrlMG reasonsCtrlMG;

    private final ReportUtilitiesBungee reportUtilitiesBungee;

    private final boolean isPremiumVanish;

    public ReportCommandBungee(EpicReports plugin){
        super("report");
        this.plugin = plugin;

        isMysql = plugin.isMysql;

        this.messagesFile = plugin.messagesFile;
        this.whitelistTab = plugin.configFile.getWhitelistTab();

        this.msgsUtilsBungee = plugin.msgsUtilsBungee;
        this.playersUtilsBungee = plugin.playersUtilsBungee;

        this.maxReportsMG = new MaxReportsMG(plugin.configFile.getMax_reports_size());
        this.reasonsCtrlMG = new ReasonsCtrlMG(plugin.reasonsCtrlFile.getMinimumLength(), plugin.reasonsCtrlFile.getBlacklist());

        this.reportUtilitiesBungee = new ReportUtilitiesBungee(plugin, maxReportsMG);

        this.isPremiumVanish = plugin.isPremiumVanish;
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
                reportUtilitiesBungee.openOnlinePlys(player);
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
            if(args.length == 1){
                reportUtilitiesBungee.openPredefinedReports(player, reportedName);
                return;
            }

            String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            if(!reasonsCtrlMG.isReasonValid(reason)){
                player.sendMessage(msgsUtilsBungee.convertBungeeColor(messagesFile.getReason_invalid()));
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

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) return Collections.emptyList();

        List<String> results = new ArrayList<>();

        //Return reason
        if(args.length == 2){
            results.add("<Reason>");
            return results;
        }

        if(args.length == 1){
            ProxiedPlayer player = (ProxiedPlayer) sender;

            //Add online players
            for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers()){

                if(whitelistTab.contains(p.getName())) continue;
                if(p.getName().equalsIgnoreCase(player.getName())) continue;
                if(isPremiumVanish){
                    if(BungeeVanishAPI.isInvisible(p)) continue;
                }

                results.add(p.getName());
            }

            return results;
        }


        return Collections.emptyList();
    }
}
