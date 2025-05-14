package teik.ers.bukkit.commands.report;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.LanguagesManager;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.global.mgs.ReasonsCtrlMG;
import teik.ers.bukkit.managers.updateinvs.UpdateInvsMG;
import teik.ers.bukkit.utilities.UtilitiesPlayers;
import teik.ers.bukkit.utilities.models.enums.InventorySection;
import teik.ers.global.mgs.MaxReportsMG;

import java.util.Arrays;


public class RCommand implements CommandExecutor {
    private final boolean isMysql;

    private final InventoryMG inventoryMG;

    private final MaxReportsMG maxReportsMG;
    private final ReasonsCtrlMG reasonsCtrlMG;
    private final UpdateInvsMG updateInvsMG;

    private final LanguagesManager messageManager;

    private final UtilitiesPlayers utilitiesPlayers;
    private final RCUtilities rcUtilities;

    public RCommand(EpicReports plugin) {
        this.isMysql = plugin.configManager.isMYSQLActive();

        this.inventoryMG = plugin.inventoryMG;

        this.maxReportsMG = new MaxReportsMG(plugin.configManager.getMax_reports_size());
        this.reasonsCtrlMG = new ReasonsCtrlMG(plugin.reasonsCtrlManager.getMinimumLength(), plugin.reasonsCtrlManager.getBlacklist());
        this.updateInvsMG = inventoryMG.updateInvsMG;

        this.messageManager = plugin.messageManager;

        this.utilitiesPlayers = plugin.utilitiesPlayers;
        this.rcUtilities = new RCUtilities(
                plugin,
                messageManager.getReport_successful(),
                maxReportsMG
        );
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (isMysql) return false;
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (!player.hasPermission("EpicReports.User")) {
                player.sendMessage(messageManager.getNot_permission());
                return false;
            }
            if (args.length < 1) {
                inventoryMG.openOnlinePlayersM(player, 1, null);
                return false;
            }

            String reportedName = args[0];

            if (utilitiesPlayers.getUUID(reportedName) == null) {
                player.sendMessage(messageManager.getPlayer_not_exist());
                return false;
            }
            if (maxReportsMG.isMaxReport(player.getUniqueId().toString(), reportedName)) {
                player.sendMessage(messageManager.getMax_reports_to_player());
                return false;
            }
            if (player.getName().equalsIgnoreCase(reportedName)) {
                player.sendMessage(messageManager.getReport_yourself());
                return false;
            }
            if(args.length == 1){
                inventoryMG.openPredefinedReportsM(player, 1, reportedName, InventorySection.Close);
                return false;
            }

            String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            if(!reasonsCtrlMG.isReasonValid(reason)){
                player.sendMessage(messageManager.getReason_invalid());
                return false;
            }

            rcUtilities.addReport(player, reportedName, reason);
            updateInvsMG.sendUpdateInvs(player);
            return true;
        }
        Bukkit.getConsoleSender().sendMessage(messageManager.getConsole_report());
        return false;
    }
}
