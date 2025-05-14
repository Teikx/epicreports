package teik.ers.bungee.managers;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import teik.ers.bungee.EpicReports;
import teik.ers.bungee.configs.MessagesFile;
import teik.ers.bungee.utilities.MsgsUtilsBungee;
import teik.ers.global.utils.querys.QuerysUT;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.concurrent.TimeUnit;

public class ListenersManager implements Listener {
    private final EpicReports plugin;

    private final MessagesFile messagesFile;
    private final MsgsUtilsBungee msgsUtilsBungee;
    private final QuerysUT querysUT;

    public ListenersManager(EpicReports plugin) {
        this.plugin = plugin;

        messagesFile = plugin.messagesFile;
        msgsUtilsBungee = plugin.msgsUtilsBungee;
        querysUT = plugin.querysUT;
    }

    @EventHandler
    public void onLogin(PostLoginEvent postLoginEvent){
        ProxiedPlayer player = postLoginEvent.getPlayer();
        querysUT.updateOnline(player.getUniqueId().toString());
        querysUT.addPlayer(player.getName(), true);
    }
    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent playerDisconnectEvent){
        ProxiedPlayer player = playerDisconnectEvent.getPlayer();
        String uuid = player.getUniqueId().toString();
        querysUT.updateOffline(uuid);
        querysUT.updateLastDetails(uuid, player.getServer().getInfo().getName());
        querysUT.addPlayer(player.getName(), false);
    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event){
        if(!event.getTag().equals("epicreports:main")) return;

        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()))) {
            String subchannel = in.readUTF();

            if(subchannel.equals("BTeleportPlayerToPlayer")){
                String playerName = in.readUTF();
                String otherPlayerName = in.readUTF();

                ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(playerName);
                ProxiedPlayer otherPlayer = ProxyServer.getInstance().getPlayer(otherPlayerName);

                if(targetPlayer == null) return;
                if(otherPlayer == null){
                    sendMessage(targetPlayer, messagesFile.getPlayer_not_found());
                    return;
                }

                ServerInfo server = otherPlayer.getServer().getInfo();
                if(server==null) {
                    sendMessage(targetPlayer, messagesFile.getServer_not_found());
                    return;
                }

                sendMessage(targetPlayer, messagesFile.getTeleported());

                if(!targetPlayer.getServer().getInfo().equals(server)) targetPlayer.connect(server);

                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("TeleportPlayerToPlayer");
                out.writeUTF(playerName);
                out.writeUTF(otherPlayerName);

                ProxyServer.getInstance().getScheduler().schedule(plugin, () -> {
                    server.sendData("epicreports:main", out.toByteArray());
                }, 2, TimeUnit.SECONDS);
            }

            if(subchannel.equals("BTeleportPlayerToLocate")){
                String playerName = in.readUTF();
                String locate = in.readUTF();
                String serverName = in.readUTF();

                ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(playerName);
                if(targetPlayer==null) return;

                ServerInfo server = ProxyServer.getInstance().getServerInfo(serverName);
                if(server==null) return;

                sendMessage(targetPlayer, messagesFile.getTeleported());

                if(!targetPlayer.getServer().getInfo().equals(server)) targetPlayer.connect(server);

                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("TeleportPlayerToLocate");
                out.writeUTF(playerName);
                out.writeUTF(locate);

                ProxyServer.getInstance().getScheduler().schedule(plugin, () -> {
                    server.sendData("epicreports:main", out.toByteArray());
                }, 2, TimeUnit.SECONDS);
            }

            if(subchannel.equalsIgnoreCase("BungeeUpdateReportsManager")){
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("UpdateReportsManager");
                sendOutToAllServers(out);
            }

            if(subchannel.equals("BFreezePlayer")){
                String playerName = in.readUTF();
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("FreezePlayer");
                out.writeUTF(playerName);
                sendOutToAllServers(out);
            }

            if(subchannel.equals("BUnfreezePlayer")){
                String playerName = in.readUTF();
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("UnfreezePlayer");
                out.writeUTF(playerName);
                sendOutToAllServers(out);
            }

            if(subchannel.equals("BReportPlayer")){
                String playerName = in.readUTF();
                String reportedPlayerName = in.readUTF();
                String reason = in.readUTF();

                ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(playerName);

                if(targetPlayer == null) return;

                ProxyServer.getInstance().getPluginManager().dispatchCommand(targetPlayer, "report " + reportedPlayerName + " " + reason);
            }
        } catch (Exception e) {
            System.out.print("[EpicReports] Error whit mysql: " + e.getMessage());
        }
    }

    private void sendOutToAllServers(ByteArrayDataOutput out){
        for (ServerInfo server : ProxyServer.getInstance().getServers().values()) {
            server.sendData("epicreports:main", out.toByteArray());
        }
    }

    private void sendMessage(ProxiedPlayer targetPlayer, String message){
        targetPlayer.sendMessage(msgsUtilsBungee.convertBungeeColor(
                message
        ));
    }
}
