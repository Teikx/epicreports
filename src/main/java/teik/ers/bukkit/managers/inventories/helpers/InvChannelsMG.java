package teik.ers.bukkit.managers.inventories.helpers;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import teik.ers.bukkit.EpicReports;

@SuppressWarnings("ALL")
public class InvChannelsMG {
    private final EpicReports plugin;

    public InvChannelsMG(EpicReports plugin) {
        this.plugin = plugin;
    }

    public void sendUpdateCall(Player player){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("BungeeUpdateReportsManager");

        player.sendPluginMessage(plugin, "epicreports:main", out.toByteArray());
    }

    public void teleportPlayerToReportPlayer(Player player, String otherPlayerName){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("BTeleportPlayerToPlayer");
        out.writeUTF(player.getName());
        out.writeUTF(otherPlayerName);
        player.sendPluginMessage(plugin, "epicreports:main", out.toByteArray());
    }

    public void teleportReportPlayerToPlayer(Player player, String otherPlayerName){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("BTeleportPlayerToPlayer");
        out.writeUTF(otherPlayerName);
        out.writeUTF(player.getName());
        player.sendPluginMessage(plugin, "epicreports:main", out.toByteArray());
    }

    public void teleportPlayerToLocation(Player player, String location, String server){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("BTeleportPlayerToLocate");
        out.writeUTF(player.getName());
        out.writeUTF(location);
        out.writeUTF(server);
        player.sendPluginMessage(plugin, "epicreports:main", out.toByteArray());
    }

    public void freezePlayer(Player player, String nick){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("BFreezePlayer");
        out.writeUTF(nick);
        player.sendPluginMessage(plugin, "epicreports:main", out.toByteArray());
    }
    public void unfreezePlayer(Player player, String nick){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("BUnfreezePlayer");
        out.writeUTF(nick);
        player.sendPluginMessage(plugin, "epicreports:main", out.toByteArray());
    }
}
