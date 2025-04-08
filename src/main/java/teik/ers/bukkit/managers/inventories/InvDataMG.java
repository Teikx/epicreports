package teik.ers.bukkit.managers.inventories;

import org.bukkit.entity.Player;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.enums.FilterType;
import teik.ers.bukkit.utilities.models.enums.SortType;
import teik.ers.global.models.objects.Report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvDataMG {
    public InvDataMG() {
        //All
        playerInventorys = new HashMap<>();
        uuidSelected = new HashMap<>();

        playerSortType = new HashMap<>();
        playerSortTypeReports = new HashMap<>();
        playerFilterType = new HashMap<>();
        playerFilterTypeReports = new HashMap<>();

        //ReportedsMenu
        perPlayerReportedList = new HashMap<>();

        //ServersMenu
        serversList = new ArrayList<>();

        //ReportsMenu
        perPlayerReportList = new HashMap<>();
        reportSelected = new HashMap<>();
    }

    //Global
    private final HashMap<Player, InventoryPlayer> playerInventorys;

    public void putPlayerInventory(Player player, InventoryPlayer inventoryPlayer){
        playerInventorys.put(player, inventoryPlayer);
    }

    public InventoryPlayer getPlayerInventory(Player player){
        return playerInventorys.get(player);
    }

    public void removePlayerInventory(Player player){
        playerInventorys.remove(player);
    }

    private final HashMap<Player, String> uuidSelected;

    public void putUuidSelected(Player player, String uuid){
        uuidSelected.put(player, uuid);
    }

    public String getUuidSelected(Player player){
        return uuidSelected.get(player);
    }

    //Sort Data

    private final HashMap<Player, SortType> playerSortType;
    private final HashMap<Player, SortType> playerSortTypeReports;

    public void putPlayerSortType(Player player, SortType sortType){
        playerSortType.put(player, sortType);
    }

    public SortType getPlayerSortType(Player player){
        return playerSortType.getOrDefault(player, SortType.dateAZ);
    }

    public void putPlayerSortTypeReports(Player player, SortType sortType){
        playerSortTypeReports.put(player, sortType);
    }

    public SortType getPlayerSortTypeReports(Player player){
        return playerSortTypeReports.getOrDefault(player, SortType.dateAZ);
    }

    //Filter Data

    private final HashMap<Player, FilterType> playerFilterType;
    private final HashMap<Player, FilterType> playerFilterTypeReports;

    public void putPlayerFilterType(Player player, FilterType filterType){
        playerFilterType.put(player, filterType);
    }

    public FilterType getPlayerFilterType(Player player){
        return playerFilterType.getOrDefault(player, FilterType.None);
    }

    public void putPlayerFilterTypeReports(Player player, FilterType filterType){
        playerFilterTypeReports.put(player, filterType);
    }

    public FilterType getPlayerFilterTypeReports(Player player){
        return playerFilterTypeReports.getOrDefault(player, FilterType.None);
    }


    //ReportedsMenu Data

    private final HashMap<Player, List<Report>> perPlayerReportedList;

    public void putPerPlayerReportedList(Player player, List<Report> reports){
        perPlayerReportedList.put(player, reports);
    }

    public List<Report> getPerPlayerReportedList(Player player){
        return perPlayerReportedList.get(player);
    }

    //ServersMenu Data

    private List<String> serversList;

    public void updateServersList(List<String> list){
        serversList = new ArrayList<>(list);
    }

    public List<String> getServersList(){
        return serversList;
    }

    //ReportsMenu Data

    private final HashMap<Player, List<Report>> perPlayerReportList;

    public void putPerPlayerReportList(Player player, List<Report> reports){
        perPlayerReportList.put(player, reports);
    }

    public List<Report> getPerPlayerReportList(Player player){
        return perPlayerReportList.get(player);
    }

    private final HashMap<Player, Integer> reportSelected;

    public void putReportSelected(Player player, int id){
        reportSelected.put(player, id);
    }

    public int getReportSelected(Player player){
        return reportSelected.get(player);
    }
}
