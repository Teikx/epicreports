package teik.ers.bukkit.configs;

import org.bukkit.configuration.file.FileConfiguration;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;

import java.util.List;

public class ReasonsCtrlManager {
    private final CustomConfig configFile;

    private int minimumLength;
    private List<String> blacklist;

    public ReasonsCtrlManager(EpicReports plugin) {
        this.configFile = new CustomConfig("reasons-control.yml", null, null, plugin, false);
        this.configFile.registerConfig();
        loadConfig();
    }

    private void loadConfig() {
        FileConfiguration config = this.configFile.getConfig();
        this.minimumLength = config.getInt("Reasons_Config.Minimum_Length");
        this.blacklist = config.getStringList("Reasons_Config.Blacklist");
    }

    public void reloadConfig() {
        this.configFile.reloadConfig();
        loadConfig();
    }

    public CustomConfig getConfigFile() {
        return this.configFile;
    }

    public int getMinimumLength() {
        return minimumLength;
    }

    public List<String> getBlacklist() {
        return blacklist;
    }
}
