package teik.ers.bungee.configs;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import teik.ers.bungee.EpicReports;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class ReasonsCtrlFile {
    File reasonsFile;
    Configuration reasons;

    EpicReports plugin;

    private int minimumLength;
    private List<String> blacklist;

    public ReasonsCtrlFile(EpicReports plugin) throws IOException {
        this.plugin = plugin;
        reloadReasons();
    }

    public File getFile(){
        return reasonsFile;
    }
    public Configuration getReasons() {
        return reasons;
    }

    public void reloadReasons() throws IOException {
        registerConfig();
        loadConfig();
    }

    private void registerConfig() throws IOException {
        try {
            makeConfig();
        }catch (Exception e) {
            makeConfigAlternative();
        }
    }

    private void makeConfig() throws IOException {
        reasonsFile = new File(plugin.getDataFolder(), "reasons-control.yml");

        if (!reasonsFile.exists()) {
            try (InputStream in = plugin.getResourceAsStream("reasons-control.yml")) {
                Files.copy(in, reasonsFile.toPath());
            }
        }
        reasons = ConfigurationProvider.getProvider(YamlConfiguration.class).load(reasonsFile);
    }
    private void makeConfigAlternative() throws IOException {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        reasonsFile = new File(plugin.getDataFolder(), "reasons-control.yml");

        if (!reasonsFile.exists()) {
            try (InputStream in = plugin.getResourceAsStream("reasons-control.yml")) {
                Files.copy(in, reasonsFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        reasons = ConfigurationProvider.getProvider(YamlConfiguration.class).load(reasonsFile);
    }

    public void loadConfig() {
        this.minimumLength = reasons.getInt("Reasons_Config.Minimum_Length");
        this.blacklist = reasons.getStringList("Reasons_Config.Blacklist");
    }

    public int getMinimumLength() {
        return minimumLength;
    }

    public List<String> getBlacklist() {
        return blacklist;
    }
}
