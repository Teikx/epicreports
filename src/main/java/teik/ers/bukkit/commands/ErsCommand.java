package teik.ers.bukkit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.LanguagesManager;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.notifys.NotifysMG;
import teik.ers.bukkit.managers.reportmg.ReportMG;
import teik.ers.bukkit.utilities.UtilitiesMsgs;

public class ErsCommand implements CommandExecutor {

    private final EpicReports plugin;
    private final LanguagesManager messageManager;

    private final InventoryMG inventoryMG;
    private final NotifysMG notifysMG;

    private final UtilitiesMsgs utilitiesMsgs;

    private final ReportMG reportMG;

    public ErsCommand(EpicReports plugin) {
        this.plugin = plugin;
        this.messageManager = plugin.messageManager;

        this.inventoryMG = plugin.inventoryMG;
        this.notifysMG = plugin.notifysMG;

        this.utilitiesMsgs = plugin.utilitiesMsgs;

        this.reportMG = plugin.reportMG;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if (!player.hasPermission("EasyReports.Admin")) {
                player.sendMessage(messageManager.getNot_permission());
                return false;
            }
            if(args.length > 0){
                SwitchForPlayers(args, player);
                return true;
            }
            utilitiesMsgs.sendListString(player, messageManager.getHelp_msg());
            return false;
        }
        return false;
    }

    private void SwitchForPlayers(String[] args, Player player) {
        switch (args[0]) {
            case "reload":
                this.plugin.reloadAllConfigs();
                player.sendMessage(messageManager.getReload_successful());
                return;
            case "menu":
                inventoryMG.openReportedsM(player, 1, null, null, null);
                return;
            case "skip":
                notifysMG.SkipNotifys();
                break;
            case "save":
                reportMG.onDisable();
                player.sendMessage("saved");
                break;
            default:
                utilitiesMsgs.sendListString(player, messageManager.getHelp_msg());
        }
    }
}
