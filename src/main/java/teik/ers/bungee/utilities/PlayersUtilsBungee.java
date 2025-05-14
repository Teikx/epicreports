package teik.ers.bungee.utilities;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class PlayersUtilsBungee {
    public UUID getUUIDperName(String name){
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(name);
        if (player != null) {
            return player.getUniqueId();
        }
        return null;
    }

    public ProxiedPlayer getOnlineProxiedPlayer(UUID UUID){
        return ProxyServer.getInstance().getPlayer(UUID);
    }
}
