package teik.ers.bukkit.managers.bstats;

import teik.ers.bukkit.EpicReports;

public class BStatsBukkitMG {
    private EpicReports plugin;
    private final int pluginID;

    private Metrics bukkitMetrics;

    public BStatsBukkitMG(EpicReports plugin, int pluginID) {
        this.plugin = plugin;
        this.pluginID = pluginID;
        registerBukkit();
    }

    public void registerBukkit(){
        bukkitMetrics = new Metrics(plugin, pluginID);
    }

    public Metrics getBukkitMetrics() {
        return bukkitMetrics;
    }
}
