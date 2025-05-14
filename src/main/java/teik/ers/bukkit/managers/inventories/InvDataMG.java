package teik.ers.bukkit.managers.inventories;

import org.bukkit.entity.Player;
import teik.ers.bukkit.utilities.models.Comment;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.SettingsER;
import teik.ers.bukkit.utilities.models.enums.ChatType;
import teik.ers.global.models.objects.Report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvDataMG {
    public InvDataMG() {
        //All
        playerInventorys = new HashMap<>();
        uuidSelected = new HashMap<>();

        //ReportedsMenu
        perPlayerReportedList = new HashMap<>();

        //ServersMenu
        serversList = new ArrayList<>();

        //ReportsMenu
        perPlayerReportList = new HashMap<>();
        reportSelected = new HashMap<>();

        //ArchivedMenu
        perPlayerArchivedList = new HashMap<>();

        //CommentsMenu
        perPlayerCommentList = new HashMap<>();
        playerChatTypeHashMap = new HashMap<>();

        //AllReportsMenu
        perPlayerAllReportList = new HashMap<>();

        //ReportersMenu
        perPlayerReporterList = new HashMap<>();
        reporterUuidSelected = new HashMap<>();

        //ReporterReportsMenu
        perPlayerReporterReportsList = new HashMap<>();

        //SettingsMenu
        perPlayerSettings = new HashMap<>();

        //PredefinedReportsMenu
        perPlayerNickSelected = new HashMap<>();

        //OnlinePlayersMenu
        perPlayerOnlinePlayersList = new HashMap<>();
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

    private final HashMap<Player, Integer> reportSelected;

    public void putReportSelected(Player player, int id){
        reportSelected.put(player, id);
    }

    public int getReportSelected(Player player){
        return reportSelected.get(player);
    }

    //ReportedsMenu Data

    private final HashMap<Player, List<Report>> perPlayerReportedList;

    public void putPerPlayerReportedList(Player player, List<Report> reports){
        perPlayerReportedList.put(player, reports);
    }

    public List<Report> getPerPlayerReportedList(Player player){
        return perPlayerReportedList.get(player);
    }

    public int getPerPlayerReportedListSize(Player player){
        return perPlayerReportedList.get(player).size();
    }

    //ServersMenu Data

    private List<String> serversList;

    public void updateServersList(List<String> list){
        serversList = new ArrayList<>(list);
    }

    public List<String> getServersList(){
        return serversList;
    }

    public int getServersListSize(){
        return serversList.size();
    }

    //ReportsMenu Data

    private final HashMap<Player, List<Report>> perPlayerReportList;

    public void putPerPlayerReportList(Player player, List<Report> reports){
        perPlayerReportList.put(player, reports);
    }

    public List<Report> getPerPlayerReportList(Player player){
        return perPlayerReportList.get(player);
    }

    public int getPerPlayerReportListSize(Player player){
        return perPlayerReportList.get(player).size();
    }

    //ArchivedMenu Data

    private final HashMap<Player, List<Report>> perPlayerArchivedList;

    public void putPerPlayerArchivedList(Player player, List<Report> archivedR){
        perPlayerArchivedList.put(player, archivedR);
    }

    public List<Report> getPerPlayerArchivedList(Player player) {
        return perPlayerArchivedList.get(player);
    }

    public int getPerPlayerArchivedListSize(Player player){
        return perPlayerArchivedList.get(player).size();
    }

    //CommentsMenu Data

    private final HashMap<Player, List<Comment>> perPlayerCommentList;

    public void putPerPlayerCommentList(Player player, List<Comment> commentList){
        perPlayerCommentList.put(player, commentList);
    }

    public List<Comment> getPerPlayerCommentList(Player player) {
        return perPlayerCommentList.get(player);
    }

    public int getPerPlayerCommentListSize(Player player){
        return perPlayerCommentList.get(player).size();
    }

    private final HashMap<Player, ChatType> playerChatTypeHashMap;

    public void putPlayerChatType(Player player, ChatType chatType){
        playerChatTypeHashMap.put(player, chatType);
    }

    public void removePlayerChatType(Player player){
        playerChatTypeHashMap.remove(player);
    }

    public boolean playerIsChatting(Player player){
        return playerChatTypeHashMap.containsKey(player);
    }

    public ChatType getPlayerChatType(Player player){
        return playerChatTypeHashMap.get(player);
    }

    //AllReportsMenu Data

    private final HashMap<Player, List<Report>> perPlayerAllReportList;

    public void putPerPlayerAllReportList(Player player, List<Report> allReportList){
        perPlayerAllReportList.put(player, allReportList);
    }

    public List<Report> getPerPlayerAllReportList(Player player) {
        return perPlayerAllReportList.get(player);
    }

    public int getPerPlayerAllReportSize(Player player) {
        return perPlayerAllReportList.get(player).size();
    }

    //ReportersMenu Data

    private final HashMap<Player, List<Report>> perPlayerReporterList;

    public void putPerPlayerReporterList(Player player, List<Report> reports){
        perPlayerReporterList.put(player, reports);
    }

    public List<Report> getPerPlayerReporterList(Player player){
        return perPlayerReporterList.get(player);
    }

    public int getPerPlayerReporterListSize(Player player){
        return perPlayerReporterList.get(player).size();
    }

    private final HashMap<Player, String> reporterUuidSelected;

    public void putReporterUuidSelected(Player player, String uuid){
        reporterUuidSelected.put(player, uuid);
    }

    public String getReporterUuidSelected(Player player){
        return reporterUuidSelected.get(player);
    }

    //ReporterReportsMenu Data

    private final HashMap<Player, List<Report>> perPlayerReporterReportsList;

    public void putPerPlayerReporterReportsList(Player player, List<Report> reports){
        perPlayerReporterReportsList.put(player, reports);
    }

    public List<Report> getPerPlayerReporterReportsList(Player player){
        return perPlayerReporterReportsList.get(player);
    }

    public int getPerPlayerReporterReportsListSize(Player player){
        return perPlayerReporterReportsList.get(player).size();
    }

    //SettingsMenu Data

    private final HashMap<Player, SettingsER> perPlayerSettings;

    public void putSettingsER(Player player, SettingsER settingsER){
        perPlayerSettings.put(player, settingsER);
    }

    public SettingsER getSettingsER(Player player){
        return perPlayerSettings.get(player);
    }

    public void removeSettingsER(Player player){
        perPlayerSettings.remove(player);
    }

    //PredefinedReportsMenu Data

    private final HashMap<Player, String> perPlayerNickSelected;

    public void putNickSelected(Player player, String nick){
        perPlayerNickSelected.put(player, nick);
    }

    public String getNickSelected(Player player){
        return perPlayerNickSelected.get(player);
    }

    //OnlinePlayersMenu Data

    private final HashMap<Player, List<String>> perPlayerOnlinePlayersList;

    public void putPerPlayerOnlinePlayersList(Player player, List<String> onlinePlayers){
        perPlayerOnlinePlayersList.put(player, onlinePlayers);
    }

    public List<String> getPerPlayerOnlinePlayersList(Player player){
        return perPlayerOnlinePlayersList.get(player);
    }

    public int getPerPlayerOnlinePlayersListSize(Player player){
        return perPlayerOnlinePlayersList.get(player).size();
    }
}
