package teik.ers.bukkit.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.managers.reportmg.helpers.PlayersListMG;
import teik.ers.global.utils.querys.QuerysUT;
import teik.ers.bukkit.utilities.UtilitiesPlayers;
import teik.ers.global.models.objects.PlayerOBJ;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QuitJoinL implements Listener {
    private final boolean isMysql;
    private final UtilitiesPlayers utilitiesPlayers;
    private final QuerysUT querysUT;
    private final PlayersListMG playersListMG;

    public QuitJoinL(EpicReports plugin) {
        this.isMysql = plugin.configManager.isMYSQLActive();
        this.utilitiesPlayers = plugin.utilitiesPlayers;
        this.querysUT = plugin.querysUT;
        this.playersListMG = plugin.reportMG.getPlayersListMG();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent){
        Player player = playerJoinEvent.getPlayer();
        String uuid = player.getUniqueId().toString();
        playersListMG.setPlayerOnline(uuid);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent playerQuitEvent){
        Player player = playerQuitEvent.getPlayer();
        String uuid = player.getUniqueId().toString();

        if(isMysql){
            querysUT.updateLastLocation(uuid, utilitiesPlayers.getPlayerLocate(player).createString());
            return;
        }

        PlayerOBJ playerOBJ = playersListMG.getPlayerOBJ(uuid);

        if(playerOBJ == null) {
            return;
        }

        playerOBJ.setLastLocation(utilitiesPlayers.getPlayerLocate(player).createString());
        playerOBJ.setLastConnection(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")));
        playersListMG.setPlayerOffline(uuid);
    }
}
