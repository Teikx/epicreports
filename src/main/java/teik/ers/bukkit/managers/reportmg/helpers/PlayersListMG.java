package teik.ers.bukkit.managers.reportmg.helpers;

import teik.ers.global.models.objects.PlayerOBJ;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayersListMG {
    private final Connection connection;

    public PlayersListMG(Connection connection) {
        this.connection = connection;
        updatePlayerOBJList();
    }

    private final HashMap<String, PlayerOBJ> playersHashMap = new HashMap<>();

    public void updatePlayerOBJList(){
        String query = "SELECT * FROM PlayersTable";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            try (ResultSet resultSet = statement.executeQuery()) {
                playersHashMap.clear();

                while (resultSet.next()) {
                    String nick = resultSet.getString("Nick");
                    String uuid = resultSet.getString("UUID");
                    String ip = resultSet.getString("IP");

                    PlayerOBJ playerOBJ = new PlayerOBJ(nick, uuid, ip);

                    String lastLocation = " ";
                    String lastConnection = " ";
                    String lastServer = " ";

                    try {
                        lastLocation = resultSet.getString("LastLocation");
                        lastConnection = resultSet.getString("LastConnection");
                        lastServer = resultSet.getString("LastServer");
                    }catch(SQLException ex) {
                        handleSQLException(ex);
                    }

                    if(lastLocation != null) playerOBJ.setLastLocation(lastLocation);
                    if(lastConnection != null) playerOBJ.setLastConnection(lastConnection);
                    if(lastServer != null) playerOBJ.setLastServer(lastServer);

                    playersHashMap.put(uuid, playerOBJ);
                }
            }
        } catch(SQLException e){
            handleSQLException(e);
        }
    }

    public void addPlayerOBJ(PlayerOBJ playerOBJ){
        playersHashMap.putIfAbsent(playerOBJ.getUuid(), playerOBJ);
    }

    public PlayerOBJ getPlayerOBJ(String uuid){
        return playersHashMap.get(uuid);
    }

    public void setPlayerOnline(String uuid){
        PlayerOBJ playerOBJ = playersHashMap.get(uuid);
        if(playerOBJ != null){
            playerOBJ.setOnline(true);
        }
    }

    public void setPlayerOffline(String uuid){
        PlayerOBJ playerOBJ = playersHashMap.get(uuid);
        if(playerOBJ != null){
            playerOBJ.setOnline(false);
        }
    }

    public List<String> getOnlinePlayers(){
        return playersHashMap.entrySet().stream()
                .filter(entry -> entry.getValue().isOnline())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<String> getOfflinePlayers(){
        return playersHashMap.entrySet().stream()
                .filter(entry -> !entry.getValue().isOnline())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<PlayerOBJ> getPlayersList(){
        return new ArrayList<>(playersHashMap.values());
    }

    private void handleSQLException(SQLException e) {System.out.print("[EpicReports] Error with EpicReports: " + e.getMessage());
        e.printStackTrace();}
}
