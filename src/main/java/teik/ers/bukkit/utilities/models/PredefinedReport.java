package teik.ers.bukkit.utilities.models;

import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;

import java.util.List;

public class PredefinedReport {
    private String Name;

    private final List<String> Lore;

    private final String Reason;

    private final Permission Permission;

    private final ItemStack Item;

    public PredefinedReport(String name, List<String> lore, String reason, Permission permission, ItemStack item) {
        this.Name = name;
        this.Lore = lore;
        this.Reason = reason;
        this.Permission = permission;
        this.Item = item;
    }

    public String getName() {
        return this.Name;
    }

    public List<String> getLore() {
        return this.Lore;
    }

    public String getReason() {
        return this.Reason;
    }

    public Permission getPermission() {
        return this.Permission;
    }

    public ItemStack getItem() {
        return this.Item;
    }
}
