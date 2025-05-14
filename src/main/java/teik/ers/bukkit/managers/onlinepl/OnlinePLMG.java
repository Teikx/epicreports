package teik.ers.bukkit.managers.onlinepl;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import teik.ers.bukkit.EpicReports;
import teik.ers.global.utils.querys.QuerysUT;

import java.util.ArrayList;
import java.util.List;

public class OnlinePLMG {
    private final boolean isMysql;

    private final QuerysUT querysUtils;

    public OnlinePLMG(EpicReports plugin) {
        this.isMysql = plugin.configManager.isMYSQLActive();
        this.querysUtils = plugin.querysUT;
    }

    public List<String> getOnlinePlayers(){
        if(isMysql){
            return querysUtils.getOnlinePlayers();
        }
        return getLocalOnlinePlayers();
    }

    private List<String> getLocalOnlinePlayers(){
        List<String> onlinePlayers = new ArrayList<>();
        for(Player player : Bukkit.getOnlinePlayers()){
            onlinePlayers.add(player.getName());
        }
        return onlinePlayers;
    }
}
