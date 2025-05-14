package teik.ers.bukkit.configs.utils;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import teik.ers.bukkit.EpicReports;

import java.io.File;
import java.io.IOException;

public class CustomConfig {
    private final EpicReports plugin;
    private final String fileName;

    private FileConfiguration fileConfiguration = null;
    private File file = null;

    private final String folderName;
    private final String subFolderName;
    private final boolean newFile;

    public CustomConfig(String fileName, String folderName, String subFolderName, EpicReports plugin, boolean newFile) {
        this.fileName = fileName;
        this.folderName = folderName;
        this.subFolderName = subFolderName;
        this.plugin = plugin;
        this.newFile = newFile;
    }

    public String getPath() {
        return this.file.getPath();
    }

    public void registerConfig() {
        if(this.subFolderName != null){
            this.file = new File(this.plugin.getDataFolder() + File.separator + this.folderName + File.separator + this.subFolderName, this.fileName);
        }
        else if (this.folderName != null) {
            this.file = new File(this.plugin.getDataFolder() + File.separator + this.folderName, this.fileName);
        } else {
            this.file = new File(this.plugin.getDataFolder(), this.fileName);
        }
        if (!this.file.exists()){
            if(newFile){
                try{
                    file.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                return;
            }
            if(this.subFolderName != null){
                this.plugin.saveResource(this.folderName + File.separator + this.subFolderName + File.separator + this.fileName, false);
            }
            else if (this.folderName != null) {
                this.plugin.saveResource(this.folderName + File.separator + this.fileName, false);
            } else {
                this.plugin.saveResource(this.fileName, false);
            }

        }
        this.fileConfiguration = (FileConfiguration)new YamlConfiguration();
        try {
            this.fileConfiguration.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            this.fileConfiguration.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        if (this.fileConfiguration == null)
            reloadConfig();
        return this.fileConfiguration;
    }

    public void reloadConfig() {
        if (this.fileConfiguration == null)
            if(this.subFolderName != null){
                this.file = new File(this.plugin.getDataFolder() + File.separator + this.folderName + File.separator + this.subFolderName, this.fileName);
            }
            else if (this.folderName != null) {
                this.file = new File(this.plugin.getDataFolder() + File.separator + this.folderName, this.fileName);
            } else {
                this.file = new File(this.plugin.getDataFolder(), this.fileName);
            }
        this.fileConfiguration = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
        if (this.file != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(this.file);
            this.fileConfiguration.setDefaults((Configuration)defConfig);
        }
    }
}
