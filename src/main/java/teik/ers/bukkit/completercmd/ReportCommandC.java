package teik.ers.bukkit.completercmd;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import teik.ers.bukkit.EpicReports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportCommandC implements TabCompleter {

    private final EpicReports plugin;

    public ReportCommandC(EpicReports plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            List<String> results = new ArrayList<>();

            //Return reason
            if(args.length == 2){
                results.add("<Reason>");
                return results;
            }

            //Add players names
            if(args.length == 1){
                Player player = (Player) sender;
                //Add online players
                for(Player p : Bukkit.getOnlinePlayers()){
                    if(p.hasPermission("EpicReports.Admin")) continue;
                    if(isVanished(p)) continue;
                    if(p.getName().equalsIgnoreCase(player.getName())) continue;
                    results.add(p.getName());
                }

                //Return if offline players is disabled
                if(!plugin.configManager.isReportOfflinePlayers()) return results;

                //Add offline players
                for(OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()){
                    if(p.getName().equalsIgnoreCase(player.getName())) continue;
                    results.add(p.getName());
                }

                return results;
            }
        }
        return Collections.emptyList();
    }

    private boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }
}
