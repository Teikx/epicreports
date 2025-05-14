package teik.ers.bukkit.completercmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ERSCommandC implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> results = new ArrayList<>();

        if(sender instanceof Player){
            Player player = (Player) sender;
            if(!player.hasPermission("EpicReports.Admin")) {
                return Collections.emptyList();
            }
        }

        if(args.length == 1){
            results.add("reload");
            results.add("skip");
            results.add("save");

            if(!(sender instanceof Player)) return results;

            results.add("menu");
        }

        if(args.length == 2){
            if(args[1].equalsIgnoreCase("menu")){
                results.add("archiveds");
                results.add("comments");
            }
        }

        return results;
    }
}
