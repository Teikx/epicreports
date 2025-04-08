package teik.ers.bukkit.utilities.models;

import org.bukkit.entity.Player;
import teik.ers.bukkit.utilities.models.enums.FilterType;
import teik.ers.bukkit.utilities.models.enums.InventorySection;
import teik.ers.bukkit.utilities.models.enums.SortType;
import teik.ers.global.models.objects.Report;

public class InventoryPlayer {
    private final Player player;
    private final InventorySection inventorySection;
    private int Page = 1;

    private SortType sortType = SortType.dateAZ;
    private FilterType filterType = FilterType.None;
    private String serverName = "null";

    private InventorySection lastInventorySection;

    public InventoryPlayer(Player player, InventorySection inventorySection) {
        this.player = player;
        this.inventorySection = inventorySection;
    }

    public Player getPlayer() {
        return player;
    }
    public InventorySection getInventorySection() {
        return inventorySection;
    }

    public int getPage() {
        return Page;
    }
    public void setPage(int page) {
        Page = page;
    }

    public SortType getSortType() {
        return sortType;
    }
    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }
    public FilterType getFilterType() {
        return filterType;
    }
    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public String getServerName() {
        return serverName;
    }
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public InventorySection getLastInventorySection() {
        return lastInventorySection;
    }
    public void setLastInventorySection(InventorySection lastInventorySection) {
        this.lastInventorySection = lastInventorySection;
    }
}
