package teik.ers.bukkit.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.ConfigManager;
import teik.ers.bukkit.configs.LanguagesManager;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.notifys.NotifysMG;
import teik.ers.bukkit.managers.reportmg.ReportMG;
import teik.ers.bukkit.utilities.UtilitiesMsgs;
import teik.ers.bukkit.utilities.models.enums.InventorySection;

public class ErsCommand implements CommandExecutor {

    private final EpicReports plugin;
    private final ConfigManager configManager;
    private final LanguagesManager messageManager;

    private final InventoryMG inventoryMG;
    private final NotifysMG notifysMG;

    private final UtilitiesMsgs utilitiesMsgs;

    private final ReportMG reportMG;

    public ErsCommand(EpicReports plugin) {
        this.plugin = plugin;
        this.configManager = plugin.configManager;
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
            if (!player.hasPermission("EpicReports.Admin")) {
                player.sendMessage(messageManager.getNot_permission());
                return false;
            }
            if(args.length == 0){
                utilitiesMsgs.sendListString(player, messageManager.getHelp_msg());
                return false;
            }

            SwitchForPlayers(args, player);
            return true;
        }

        if(sender instanceof ConsoleCommandSender){
            ConsoleCommandSender consoleSender = (ConsoleCommandSender) sender;

            if(args.length == 0){
                sendHelpMsgToConsole(consoleSender);
                return false;
            }

            SwitchForConsole(args, consoleSender);
            return true;
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
                if(args.length == 1){
                    inventoryMG.openReportedsM(player, 1, null, null, null);
                    return;
                }
                switch (args[1]) {
                    case "archiveds":
                        inventoryMG.openArchivedM(player, 1, null, null, null, InventorySection.Close, null);
                        break;
                    case "comments":
                        inventoryMG.openCommentsM(player, 1, null, null, null, InventorySection.Close, null);
                        break;
                    default:
                        utilitiesMsgs.sendListString(player, messageManager.getHelp_msg());
                        break;
                }
                return;
            case "skip":
                notifysMG.SkipNotifys();
                break;
            case "save":
                if(configManager.isMYSQLActive()) return;

                reportMG.onDisable();
                player.sendMessage(messageManager.getReports_save_successful());
                break;
            default:
                utilitiesMsgs.sendListString(player, messageManager.getHelp_msg());
        }
    }

    private void SwitchForConsole(String[] args, ConsoleCommandSender console) {
        switch (args[0]) {
            case "reload":
                this.plugin.reloadAllConfigs();
                console.sendMessage(messageManager.getReload_successful());
                return;
            case "skip":
                notifysMG.SkipNotifys();
                break;
            case "save":
                if(configManager.isMYSQLActive()) return;

                reportMG.onDisable();
                console.sendMessage(messageManager.getReports_save_successful());
                break;
            default:
                sendHelpMsgToConsole(console);
        }
    }

    private void sendHelpMsgToConsole(ConsoleCommandSender consoleSender) {
        for(String s : messageManager.getConsole_help_msg()){
            consoleSender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
        }
    }
}
