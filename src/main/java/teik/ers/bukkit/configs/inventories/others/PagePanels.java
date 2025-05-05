package teik.ers.bukkit.configs.inventories.others;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.utils.CustomConfig;

import java.util.List;

public class PagePanels {
    private final CustomConfig pagePanelsFile;

    private String closePanelTitle, backPanelTitle, nextPagePanelTitle, backPagePanelTitle, notPagePanelTitle;
    private List<String> closePanelLore, backPanelLore, nextPagePanelLore, backPagePanelLore, notPagePanelLore;

    private String sortDateNewOldTitle, sortDateOldNewTitle, sortNameAZTitle, sortNameZATitle, sortUuidAZTitle,
            sortUuidZATitle, sortAmountMinMaxTitle, sortAmountMaxMinTitle;
    private List<String> sortDateNewOldLore, sortDateOldNewLore, sortNameAZLore, sortNameZALore, sortUuidAZLore,
            sortUuidZALore, sortAmountMinMaxLore, sortAmountMaxMinLore;

    public PagePanels(EpicReports plugin) {
        pagePanelsFile = new CustomConfig("pages-panels.yml", "inventories", "others", plugin, false);
        this.pagePanelsFile.registerConfig();
        loadPagePanels();
    }

    private void loadPagePanels() {
        FileConfiguration pagePanels = pagePanelsFile.getConfig();

        this.closePanelTitle = convertColor(pagePanels.getString("ClosePanel.Title"));
        this.closePanelLore = pagePanels.getStringList("ClosePanel.Lore");
        this.backPanelTitle = convertColor(pagePanels.getString("BackPanel.Title"));
        this.backPanelLore = pagePanels.getStringList("BackPanel.Lore");
        this.nextPagePanelTitle = convertColor(pagePanels.getString("NextPagePanel.Title"));
        this.nextPagePanelLore = pagePanels.getStringList("NextPagePanel.Lore");
        this.backPagePanelTitle = convertColor(pagePanels.getString("BackPagePanel.Title"));
        this.backPagePanelLore = pagePanels.getStringList("BackPagePanel.Lore");
        this.notPagePanelTitle = convertColor(pagePanels.getString("NotPagePanel.Title"));
        this.notPagePanelLore = pagePanels.getStringList("NotPagePanel.Lore");

        this.sortDateNewOldTitle = convertColor(pagePanels.getString("Sort.Date-New-Old.Title"));
        this.sortDateNewOldLore = pagePanels.getStringList("Sort.Date-New-Old.Lore");
        this.sortDateOldNewTitle = convertColor(pagePanels.getString("Sort.Date-Old-New.Title"));
        this.sortDateOldNewLore = pagePanels.getStringList("Sort.Date-Old-New.Lore");
        this.sortNameAZTitle = convertColor(pagePanels.getString("Sort.Name-A-Z.Title"));
        this.sortNameAZLore = pagePanels.getStringList("Sort.Name-A-Z.Lore");
        this.sortNameZATitle = convertColor(pagePanels.getString("Sort.Name-Z-A.Title"));
        this.sortNameZALore = pagePanels.getStringList("Sort.Name-Z-A.Lore");
        this.sortUuidAZTitle = convertColor(pagePanels.getString("Sort.Uuid-A-Z.Title"));
        this.sortUuidAZLore = pagePanels.getStringList("Sort.Uuid-A-Z.Lore");
        this.sortUuidZATitle = convertColor(pagePanels.getString("Sort.Uuid-Z-A.Title"));
        this.sortUuidZALore = pagePanels.getStringList("Sort.Uuid-Z-A.Lore");
        this.sortAmountMinMaxTitle = convertColor(pagePanels.getString("Sort.Amount-Min-Max.Title"));
        this.sortAmountMinMaxLore = pagePanels.getStringList("Sort.Amount-Min-Max.Lore");
        this.sortAmountMaxMinTitle = convertColor(pagePanels.getString("Sort.Amount-Max-Min.Title"));
        this.sortAmountMaxMinLore = pagePanels.getStringList("Sort.Amount-Max-Min.Lore");
    }

    private String convertColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public void reloadConfig() {
        pagePanelsFile.reloadConfig();
        loadPagePanels();
    }

    public CustomConfig getPagePanelsFile() {
        return pagePanelsFile;
    }

    public String getClosePanelTitle() {
        return closePanelTitle;
    }

    public String getBackPanelTitle() {
        return backPanelTitle;
    }

    public String getNextPagePanelTitle() {
        return nextPagePanelTitle;
    }

    public String getBackPagePanelTitle() {
        return backPagePanelTitle;
    }

    public String getNotPagePanelTitle() {
        return notPagePanelTitle;
    }

    public List<String> getClosePanelLore() {
        return closePanelLore;
    }

    public List<String> getBackPanelLore() {
        return backPanelLore;
    }

    public List<String> getNextPagePanelLore() {
        return nextPagePanelLore;
    }

    public List<String> getBackPagePanelLore() {
        return backPagePanelLore;
    }

    public List<String> getNotPagePanelLore() {
        return notPagePanelLore;
    }

    public String getSortDateNewOldTitle() {
        return sortDateNewOldTitle;
    }

    public String getSortDateOldNewTitle() {
        return sortDateOldNewTitle;
    }

    public String getSortNameAZTitle() {
        return sortNameAZTitle;
    }

    public String getSortNameZATitle() {
        return sortNameZATitle;
    }

    public String getSortUuidAZTitle() {
        return sortUuidAZTitle;
    }

    public String getSortUuidZATitle() {
        return sortUuidZATitle;
    }

    public String getSortAmountMinMaxTitle() {
        return sortAmountMinMaxTitle;
    }

    public String getSortAmountMaxMinTitle() {
        return sortAmountMaxMinTitle;
    }

    public List<String> getSortDateNewOldLore() {
        return sortDateNewOldLore;
    }

    public List<String> getSortDateOldNewLore() {
        return sortDateOldNewLore;
    }

    public List<String> getSortNameAZLore() {
        return sortNameAZLore;
    }

    public List<String> getSortNameZALore() {
        return sortNameZALore;
    }

    public List<String> getSortUuidAZLore() {
        return sortUuidAZLore;
    }

    public List<String> getSortUuidZALore() {
        return sortUuidZALore;
    }

    public List<String> getSortAmountMinMaxLore() {
        return sortAmountMinMaxLore;
    }

    public List<String> getSortAmountMaxMinLore() {
        return sortAmountMaxMinLore;
    }
}
