package teik.ers.bukkit.configs.utils;

import teik.ers.bukkit.EpicReports;

import java.io.File;
import java.util.ArrayList;

public abstract class DataFolderConfigManager {
    private final EpicReports plugin;
    private final String folderName;
    private final String subFolderName;
    private ArrayList<CustomConfig> configFiles;

    public DataFolderConfigManager(EpicReports plugin, String folderName, String subFolderName) {
        this.plugin = plugin;
        this.folderName = folderName;
        this.subFolderName = subFolderName;
        this.configFiles = new ArrayList<>();
        configure();
    }
    public void configure() {
        createFolder();
        reloadConfigs();
    }

    public void reloadConfigs(){
        this.configFiles = new ArrayList<>();
        registerConfigFiles();
        loadConfigs();
    }

    public void createFolder(){
        File folder;
        try {
            folder = new File(plugin.getDataFolder() + File.separator + folderName);
            if(!folder.exists()){
                folder.mkdirs();
            }
        } catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    public void saveConfigFiles() {
        for (CustomConfig configFile : configFiles) {
            configFile.saveConfig();
        }
    }

    public void registerConfigFiles(){
        registerConfigFile("en_EN.yml");
        String path = plugin.getDataFolder() + File.separator + folderName;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                registerConfigFile(file.getName());
            }
        }
    }

    public ArrayList<CustomConfig> getConfigs(){
        return this.configFiles;
    }

    public CustomConfig getConfigFile(String pathName) {
        for (CustomConfig configFile : configFiles) {
            if (configFile.getPath().equalsIgnoreCase(pathName)) {
                return configFile;
            }
        }
        return null;
    }

    public void registerConfigFile(String pathName) {
        CustomConfig config;
        if(pathName.equalsIgnoreCase("en_EN.yml")){
            config = new CustomConfig(pathName, folderName, subFolderName, plugin, false);
        }else{
            config = new CustomConfig(pathName, folderName, subFolderName, plugin, true);
        }
        config.registerConfig();
        configFiles.add(config);
    }


    public abstract void loadConfigs();
    public abstract void saveConfigs();
}
