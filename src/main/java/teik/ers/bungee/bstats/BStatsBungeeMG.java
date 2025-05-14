package teik.ers.bungee.bstats;

import teik.ers.bungee.EpicReports;

public class BStatsBungeeMG {
    private EpicReports pluginBungee;
    private final int pluginID;

    private teik.ers.bungee.bstats.Metrics bungeeMetrics;

    public BStatsBungeeMG(EpicReports pluginBungee, int pluginID) {
        this.pluginBungee = pluginBungee;
        this.pluginID = pluginID;
        registerBungee();
    }

    public void registerBungee(){
        bungeeMetrics = new teik.ers.bungee.bstats.Metrics(pluginBungee, pluginID);
    }

    public teik.ers.bungee.bstats.Metrics getBungeeMetrics() {
        return bungeeMetrics;
    }
}
