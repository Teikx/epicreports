package teik.ers.bukkit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AliasCommand extends Command {

    public AliasCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if(!player.hasPermission("EpicReports.Admin")) return false;

        player.performCommand("ers menu");
        return true;
    }
}
