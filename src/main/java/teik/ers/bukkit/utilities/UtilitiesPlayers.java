package teik.ers.bukkit.utilities;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import teik.ers.bukkit.configs.ConfigManager;
import teik.ers.global.models.objects.Locate;
import teik.ers.global.utils.querys.QuerysUT;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UtilitiesPlayers {

    private final boolean isReportOfflinePlayers, isMysql;
    private final QuerysUT querysUT;

    public UtilitiesPlayers(ConfigManager configManager, QuerysUT querysUT) {
        this.isReportOfflinePlayers = configManager.isReportOfflinePlayers();
        this.isMysql = configManager.isMYSQLActive();
        this.querysUT = querysUT;
    }

    public UUID getUUID(String name){
        Player player = Bukkit.getPlayer(name);
        if(player != null){
            return player.getUniqueId();
        }

        if(isReportOfflinePlayers){
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
            return offlinePlayer.getUniqueId();
        }
        return null;
    }
    public Player getPlayer(String name){
        return Bukkit.getPlayer(name);
    }
    public Player getPlayer(UUID uuid){
        return Bukkit.getPlayer(uuid);
    }

    public Locate getPlayerLocate(Player player) {
        Locate locate = new Locate();
        locate.createLocate(
                player.getWorld().getName(),
                player.getLocation().getX(),
                player.getLocation().getY(),
                player.getLocation().getZ(),
                player.getLocation().getYaw(),
                player.getLocation().getPitch()
        );
        return locate;
    }

    public String getPlayerIP(Player player){
        return Objects.requireNonNull(player.getAddress()).getAddress().getHostAddress();
    }

    public Boolean playerIsOnline(String uuid){
        if(isMysql){
            return querysUT.playerIsOnline(uuid);
        }

        UUID id = UUID.fromString(uuid);
        Player player = Bukkit.getPlayer(id);
        return player != null && player.isOnline();
    }

    public List<String> getPlayersOnlineMysql(){
        return querysUT.playersOnline(true);
    }

    public List<String> getPlayersOfflineMysql(){
        return querysUT.playersOnline(false);
    }
}
