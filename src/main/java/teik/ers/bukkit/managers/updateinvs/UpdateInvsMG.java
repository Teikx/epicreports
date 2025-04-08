package teik.ers.bukkit.managers.updateinvs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.inventories.helpers.InvChannelsMG;
import teik.ers.bukkit.utilities.models.InventoryPlayer;

public class UpdateInvsMG {

    private final InventoryMG inventoryMG;
    private final InvDataMG invDataMG;

    private final InvChannelsMG invChannelsMG;

    private final boolean isMysql;

    public UpdateInvsMG(InventoryMG inventoryMG) {
        this.inventoryMG = inventoryMG;
        this.invDataMG = inventoryMG.invDataMG;

        this.invChannelsMG = inventoryMG.invChannelsMG;

        this.isMysql = inventoryMG.isMysql;
    }

    public void sendUpdateInvs(Player player){
        if(isMysql){
            invChannelsMG.sendUpdateCall(player);
            return;
        }
        checkOpenInventories();
    }

    public void checkOpenInventories(){
        for(Player player : Bukkit.getOnlinePlayers()){
            InventoryPlayer inventoryPlayer = invDataMG.getPlayerInventory(player);

            if(inventoryPlayer==null) return;


            player.closeInventory();
            int page = inventoryPlayer.getPage();

            switch (inventoryPlayer.getInventorySection()){
                case ReportedsMenu:
                    inventoryMG.openReportedsM(player, page, null, null, null);
                    break;

                case ServersMenu:
                    inventoryMG.openServersM(player, page, inventoryPlayer.getLastInventorySection());
                    break;

                case DataMenu:
                    inventoryMG.openDataM(player);
                    break;

                case ReportsMenu:
                    inventoryMG.openReportsM(player, page, null, null, null);
                    break;

                case InfoReportMenu:
                    inventoryMG.openIfReportsM(player);
                    break;
            }
        }
    }
}
