package teik.ers.bukkit.commands.report;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.LanguagesManager;
import teik.ers.bukkit.utilities.UtilitiesPlayers;
import teik.ers.global.utils.MaxReportsMG;


public class RCommand implements CommandExecutor {
    private final boolean isMysql;

    private final MaxReportsMG maxReportsMG;
    private final LanguagesManager messageManager;

    private final UtilitiesPlayers utilitiesPlayers;
    private final RCUtilities rcUtilities;

    public RCommand(EpicReports plugin) {
        this.isMysql = plugin.configManager.isMYSQLActive();

        maxReportsMG = new MaxReportsMG(plugin.configManager.getMax_reports_size());
        this.messageManager = plugin.messageManager;

        this.utilitiesPlayers = plugin.utilitiesPlayers;
        rcUtilities = new RCUtilities(plugin.reportMG,
                plugin.notifysMG,
                utilitiesPlayers,
                messageManager.getReport_successful(),
                maxReportsMG
        );
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (isMysql) return false;
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (!player.hasPermission("EasyReports.User")) {
                player.sendMessage(messageManager.getNot_permission());
                return false;
            }
            if (args.length < 1) {
                player.sendMessage(messageManager.getCommand_use());
                return false;
            }
            if (utilitiesPlayers.getUUID(args[0]) == null) {
                player.sendMessage(messageManager.getPlayer_not_exist());
                return false;
            }
            if (maxReportsMG.isMaxReport(player.getUniqueId().toString(), args[0])) {
                player.sendMessage(messageManager.getMax_reports_to_player());
                return false;
            }
            if (player.getName().equalsIgnoreCase(args[0])) {
                player.sendMessage(messageManager.getReport_yourself());
                return false;
            }
            rcUtilities.addReport(player, args);
            return true;
        }
        Bukkit.getConsoleSender().sendMessage(messageManager.getConsole_report());
        return false;
    }
}
