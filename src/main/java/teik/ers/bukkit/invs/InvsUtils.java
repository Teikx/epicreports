package teik.ers.bukkit.invs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import teik.ers.bukkit.configs.inventories.others.PagePanels;
import teik.ers.bukkit.utilities.models.enums.FilterType;
import teik.ers.bukkit.utilities.models.enums.SortType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InvsUtils {

    private final PagePanels pagePanels;

    public InvsUtils(PagePanels pagePanels) {
        this.pagePanels = pagePanels;
    }

    //Decorations

    public void fillWithDarkGlassPanes(Inventory inventory, int rowStart, int rowFinal) {
        ItemStack itemStack = createGlassPaneItem(15, "", null);
        fillInvWhitGlass(inventory, rowStart, rowFinal, itemStack);
    }

    public void fillWithGrayGlassPanes(Inventory inventory, int rowStart, int rowFinal) {
        ItemStack itemStack = createGlassPaneItem(7, "", null);
        fillInvWhitGlass(inventory, rowStart, rowFinal, itemStack);
    }


    //Inventory Options

    public void createPagePanels(Inventory inventory, boolean close, boolean nextPage, boolean backPage, SortType sortType, int row) {
        int rowStart = (row - 1) * 9;
        int rowFinal = rowStart + 9;

        configurePageButtons(inventory, rowStart, rowFinal, nextPage, backPage);

        if (sortType != null) {
            ItemStack sortItemStack = configureSortButton(sortType);
            inventory.setItem(rowFinal - 1, sortItemStack);
        }

        configureCloseButton(inventory, rowStart, close);
    }

    public void configureCloseButton(Inventory inventory, int index, boolean close) {
        String title = close ?
                pagePanels.getClosePanelTitle() :
                pagePanels.getBackPanelTitle();

        List<String> lore = close ?
                pagePanels.getClosePanelLore() :
                pagePanels.getBackPanelLore();

        ItemStack itemStack = createGlassPaneItem(14, title, lore);
        inventory.setItem(index, itemStack);
    }

    public void setChest(Inventory inv, int page, int totalPages, FilterType filterType, int Slot, String Title, List<String> lore){
        if(totalPages == 0) totalPages = 1;
        String title = Title;
        title = title.replaceAll("%CurrentPage%", page + "");
        title = title.replaceAll("%TotalPages%", totalPages + "");

        List<String> Lore = new ArrayList<>(lore);
        Lore.replaceAll(word -> word
                .replace("%FilterType%", filterType.toString())
        );


        inv.setItem(Slot, setItemStack(Material.CHEST,
                title,
                Lore,
                0
        ));
    }

    public int calculateTotalPages(int totalReporteds) {return (int) Math.ceil((double) totalReporteds / 45);}
    //Others

    public ItemStack setItemStack(Material Material, String title, List<String> lore, int n) {
        ItemStack itemStack = new ItemStack(Material, 1, (short) n);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(title);

        if (lore != null && !lore.isEmpty()) {
            itemMeta.setLore(convertLoreColors(lore));
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack setItemStack(Material Material, String title, List<String> lore, int n, int amount) {
        ItemStack itemStack = new ItemStack(Material, amount, (short) n);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(title);

        if (lore != null && !lore.isEmpty()) {
            itemMeta.setLore(convertLoreColors(lore));
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public boolean[] setupPagination(int page, int totalPages) {
        return new boolean[]{
                page > 1,
                page < totalPages
        };
    }

    //Utils

    private void fillInvWhitGlass(Inventory inventory, int rowStart, int rowFinal, ItemStack itemStack) {
        for (int i = rowStart; i < rowFinal; i++) {
            inventory.setItem(i, itemStack);
        }
    }

    private List<String> convertLoreColors(List<String> lore) {
        return lore.stream()
                .map(this::convertColor)
                .collect(Collectors.toList());
    }

    private void configurePageButtons(Inventory inventory, int rowStart, int rowFinal, boolean nextPage, boolean backPage) {
        configurePageButton(inventory, rowStart + 3, backPage, true);
        configurePageButton(inventory, rowFinal - 4, nextPage, false);
    }

    private void configurePageButton(Inventory inventory, int index, boolean condition, boolean isBackButton) {
        String titleTrue = isBackButton ?
                pagePanels.getBackPagePanelTitle() :
                pagePanels.getNextPagePanelTitle();
        String titleFalse = pagePanels.getNotPagePanelTitle();

        List<String> loreTrue = isBackButton ?
                pagePanels.getBackPagePanelLore() :
                pagePanels.getNextPagePanelLore();
        List<String> loreFalse = pagePanels.getNotPagePanelLore();

        int color = condition ? (5) : 0;

        ItemStack itemStack = createGlassPaneItem(color, condition ? titleTrue : titleFalse, condition ? loreTrue : loreFalse);
        inventory.setItem(index, itemStack);
    }

    private ItemStack configureSortButton(SortType sortType) {
        String title;
        List<String> lore;
        int color;

        switch (sortType) {
            case dateAZ:
                title = pagePanels.getSortDateNewOldTitle();
                lore = pagePanels.getSortDateNewOldLore();
                color = 1;
                break;
            case nameAZ:
                title = pagePanels.getSortNameAZTitle();
                lore = pagePanels.getSortNameAZLore();
                color = 2;
                break;
            case nameZA:
                title = pagePanels.getSortNameZATitle();
                lore = pagePanels.getSortNameZALore();
                color = 6;
                break;
            case uuidAZ:
                title = pagePanels.getSortUuidAZTitle();
                lore = pagePanels.getSortUuidAZLore();
                color = 11;
                break;
            case uuidZA:
                title = pagePanels.getSortUuidZATitle();
                lore = pagePanels.getSortUuidZALore();
                color = 3;
                break;
            case amountAZ:
                title = pagePanels.getSortAmountMinMaxTitle();
                lore = pagePanels.getSortAmountMinMaxLore();
                color = 13;
                break;
            case amountZA:
                title = pagePanels.getSortAmountMaxMinTitle();
                lore = pagePanels.getSortAmountMaxMinLore();
                color = 5;
                break;
            default:
                title = pagePanels.getSortDateOldNewTitle();
                lore = pagePanels.getSortDateOldNewLore();
                color = 4;
        }

        return createGlassPaneItem(color, title, lore);
    }

    private ItemStack createGlassPaneItem(int color, String title, List<String> lore) {
        return setItemStack(Material.STAINED_GLASS_PANE, title, lore, color);
    }

    private String convertColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
