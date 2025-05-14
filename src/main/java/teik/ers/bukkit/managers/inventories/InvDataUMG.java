package teik.ers.bukkit.managers.inventories;

import org.bukkit.entity.Player;
import teik.ers.bukkit.utilities.models.InventoryPlayer;
import teik.ers.bukkit.utilities.models.enums.FilterType;
import teik.ers.bukkit.utilities.models.enums.SortType;

import java.util.HashMap;

public class InvDataUMG {
    public InvDataUMG() {
        //ReportedsM
        this.playerSortType = new HashMap<>();
        this.playerSortTypeReports = new HashMap<>();

        //ReportsM
        this.playerFilterType = new HashMap<>();
        this.playerFilterTypeReports = new HashMap<>();

        //ArchivedsM
        this.playerSortTypeArchiveds = new HashMap<>();
        this.playerFilterTypeArchiveds = new HashMap<>();

        //Comments
        this.playerSortTypeComments = new HashMap<>();
        this.playerFilterTypeComments = new HashMap<>();

        //AllReports
        this.playerSortTypeAllReports = new HashMap<>();
        this.playerFilterAllReports = new HashMap<>();

        //ReportersM
        playerSortTypeReporters = new HashMap<>();
        playerFilterReporters = new HashMap<>();

        //ReportersReportsM
        playerSortTypeReporterReports = new HashMap<>();
        playerFilterReporterReports = new HashMap<>();

        //OnlinePlayersM
        playerSortTypeOnlinePlayers = new HashMap<>();
    }

    //===============\\
    //   Sort Data   \\
    //===============\\

    //ReportedsM
    private final HashMap<Player, SortType> playerSortType;

    public void putPlayerSortType(Player player, SortType sortType){
        playerSortType.put(player, sortType);
    }

    private SortType getPlayerSortType(Player player){
        return playerSortType.getOrDefault(player, SortType.dateAZ);
    }

    public void setSortType(Player player, SortType sortType, InventoryPlayer inventoryPlayer) {
        if(sortType !=null) inventoryPlayer.setSortType(sortType);
        else{
            inventoryPlayer.setSortType(getPlayerSortType(player));}
    }

    //ReportsM
    private final HashMap<Player, SortType> playerSortTypeReports;

    public void putPlayerSortTypeReports(Player player, SortType sortType){
        playerSortTypeReports.put(player, sortType);
    }

    private SortType getPlayerSortTypeReports(Player player){
        return playerSortTypeReports.getOrDefault(player, SortType.dateAZ);
    }

    public void setSortTypeReports(Player player, SortType sortType, InventoryPlayer inventoryPlayer) {
        if(sortType !=null) inventoryPlayer.setSortType(sortType);
        else{
            inventoryPlayer.setSortType(getPlayerSortTypeReports(player));}
    }

    //ArchivedsM
    private final HashMap<Player, SortType> playerSortTypeArchiveds;

    public void putPlayerSortTypeArchiveds(Player player, SortType sortType){
        playerSortTypeArchiveds.put(player, sortType);
    }

    private SortType getPlayerSortTypeArchiveds(Player player) {
        return playerSortTypeArchiveds.getOrDefault(player, SortType.dateAZ);
    }

    public void setSortTypeArchiveds(Player player, SortType sortType, InventoryPlayer inventoryPlayer) {
        if(sortType !=null) inventoryPlayer.setSortType(sortType);
        else{
            inventoryPlayer.setSortType(getPlayerSortTypeArchiveds(player));}
    }

    //CommentsM
    private final HashMap<Player, SortType> playerSortTypeComments;

    public void putPlayerSortTypeComments(Player player, SortType sortType){
        playerSortTypeComments.put(player, sortType);
    }

    private SortType getPlayerSortTypeComments(Player player) {
        return playerSortTypeComments.getOrDefault(player, SortType.dateAZ);
    }

    public void setSortTypeComments(Player player, SortType sortType, InventoryPlayer inventoryPlayer) {
        if(sortType !=null) inventoryPlayer.setSortType(sortType);
        else{
            inventoryPlayer.setSortType(getPlayerSortTypeComments(player));}
    }

    //AllReportsM
    private final HashMap<Player, SortType> playerSortTypeAllReports;

    public void putPlayerSortTypeAllReports(Player player, SortType sortType){
        playerSortTypeAllReports.put(player, sortType);
    }

    private SortType getPlayerSortTypeAllReports(Player player) {
        return playerSortTypeAllReports.getOrDefault(player, SortType.dateAZ);
    }

    public void setSortTypeAllReports(Player player, SortType sortType, InventoryPlayer inventoryPlayer) {
        if(sortType !=null) inventoryPlayer.setSortType(sortType);
        else{
            inventoryPlayer.setSortType(getPlayerSortTypeAllReports(player));}
    }

    //ReportersM
    private final HashMap<Player, SortType> playerSortTypeReporters;

    public void putPlayerSortTypeReporters(Player player, SortType sortType){
        playerSortTypeReporters.put(player, sortType);
    }

    private SortType getPlayerSortTypeReporters(Player player){
        return playerSortTypeReporters.getOrDefault(player, SortType.dateAZ);
    }

    public void setSortTypeReporters(Player player, SortType sortType, InventoryPlayer inventoryPlayer) {
        if(sortType !=null) inventoryPlayer.setSortType(sortType);
        else{
            inventoryPlayer.setSortType(getPlayerSortTypeReporters(player));}
    }

    //ReportersReportsM
    private final HashMap<Player, SortType> playerSortTypeReporterReports;

    public void putPlayerSortTypeReporterReports(Player player, SortType sortType){
        playerSortTypeReporterReports.put(player, sortType);
    }

    private SortType getPlayerSortTypeReporterReports(Player player){
        return playerSortTypeReporterReports.getOrDefault(player, SortType.dateAZ);
    }

    public void setSortTypeReporterReports(Player player, SortType sortType, InventoryPlayer inventoryPlayer) {
        if(sortType !=null) inventoryPlayer.setSortType(sortType);
        else{
            inventoryPlayer.setSortType(getPlayerSortTypeReporterReports(player));}
    }

    //OnlinePlayersM
    private final HashMap<Player, SortType> playerSortTypeOnlinePlayers;

    public void putPlayerSortTypeOnlinePlayers(Player player, SortType sortType){
        playerSortTypeOnlinePlayers.put(player, sortType);
    }

    private SortType getPlayerSortTypeOnlinePlayers(Player player){
        return playerSortTypeOnlinePlayers.getOrDefault(player, SortType.dateAZ);
    }

    public void setSortTypeOnlinePlayers(Player player, SortType sortType, InventoryPlayer inventoryPlayer) {
        if(sortType !=null) inventoryPlayer.setSortType(sortType);
        else{
            inventoryPlayer.setSortType(getPlayerSortTypeOnlinePlayers(player));}
    }

    //===============\\
    //  Filter Data  \\
    //===============\\

    //ReportedsM
    private final HashMap<Player, FilterType> playerFilterType;

    public void putPlayerFilterType(Player player, FilterType filterType){
        playerFilterType.put(player, filterType);
    }

    private FilterType getPlayerFilterType(Player player){
        return playerFilterType.getOrDefault(player, FilterType.None);
    }

    public void setFilterType(Player player, FilterType filterType, InventoryPlayer inventoryPlayer) {
        if(filterType != null) inventoryPlayer.setFilterType(filterType);
        else{
            inventoryPlayer.setFilterType(getPlayerFilterType(player));}
    }


    //ReportM
    private final HashMap<Player, FilterType> playerFilterTypeReports;

    public void putPlayerFilterTypeReports(Player player, FilterType filterType){
        playerFilterTypeReports.put(player, filterType);
    }

    private FilterType getPlayerFilterTypeReports(Player player){
        return playerFilterTypeReports.getOrDefault(player, FilterType.None);
    }

    public void setFilterTypeReports(Player player, FilterType filterType, InventoryPlayer inventoryPlayer) {
        if(filterType != null) inventoryPlayer.setFilterType(filterType);
        else{
            inventoryPlayer.setFilterType(getPlayerFilterTypeReports(player));}
    }

    //ArchivedM
    private final HashMap<Player, FilterType> playerFilterTypeArchiveds;

    public void putPlayerFilterTypeArchiveds(Player player, FilterType filterType){
        playerFilterTypeArchiveds.put(player, filterType);
    }

    private FilterType getPlayerFilterTypeArchiveds(Player player) {
        return playerFilterTypeArchiveds.getOrDefault(player, FilterType.None);
    }

    public void setFilterTypeArchiveds(Player player, FilterType filterType, InventoryPlayer inventoryPlayer) {
        if(filterType != null) inventoryPlayer.setFilterType(filterType);
        else{
            inventoryPlayer.setFilterType(getPlayerFilterTypeArchiveds(player));}
    }

    //CommentsM
    private final HashMap<Player, FilterType> playerFilterTypeComments;

    public void putPlayerFilterTypeComments(Player player, FilterType filterType){
        playerFilterTypeComments.put(player, filterType);
    }

    private FilterType getPlayerFilterTypeComments(Player player) {
        return playerFilterTypeComments.getOrDefault(player, FilterType.None);
    }

    public void setFilterTypeComments(Player player, FilterType filterType, InventoryPlayer inventoryPlayer) {
        if(filterType != null) inventoryPlayer.setFilterType(filterType);
        else{
            inventoryPlayer.setFilterType(getPlayerFilterTypeComments(player));}
    }

    //AllReportsM
    private final HashMap<Player, FilterType> playerFilterAllReports;

    public void putPlayerFilterTypeAllReports(Player player, FilterType filterType){
        playerFilterAllReports.put(player, filterType);
    }

    private FilterType getPlayerFilterTypeAllReports(Player player) {
        return playerFilterAllReports.getOrDefault(player, FilterType.None);
    }

    public void setFilterTypeAllReports(Player player, FilterType filterType, InventoryPlayer inventoryPlayer) {
        if(filterType != null) inventoryPlayer.setFilterType(filterType);
        else{
            inventoryPlayer.setFilterType(getPlayerFilterTypeAllReports(player));}
    }

    //ReportersM
    private final HashMap<Player, FilterType> playerFilterReporters;

    public void putPlayerFilterTypeReporters(Player player, FilterType filterType){
        playerFilterReporters.put(player, filterType);
    }

    private FilterType getPlayerFilterTypeReporters(Player player){
        return playerFilterReporters.getOrDefault(player, FilterType.None);
    }

    public void setFilterTypeReporters(Player player, FilterType filterType, InventoryPlayer inventoryPlayer) {
        if(filterType != null) inventoryPlayer.setFilterType(filterType);
        else{
            inventoryPlayer.setFilterType(getPlayerFilterTypeReporters(player));}
    }

    //ReportersReportsM
    private final HashMap<Player, FilterType> playerFilterReporterReports;

    public void putPlayerFilterTypeReporterReports(Player player, FilterType filterType){
        playerFilterReporterReports.put(player, filterType);
    }

    private FilterType getPlayerFilterTypeReporterReports(Player player){
        return playerFilterReporterReports.getOrDefault(player, FilterType.None);
    }

    public void setFilterTypeReporterReports(Player player, FilterType filterType, InventoryPlayer inventoryPlayer) {
        if(filterType != null) inventoryPlayer.setFilterType(filterType);
        else{
            inventoryPlayer.setFilterType(getPlayerFilterTypeReporterReports(player));}
    }
}
