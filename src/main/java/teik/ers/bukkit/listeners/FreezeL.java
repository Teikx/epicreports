package teik.ers.bukkit.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import teik.ers.bukkit.managers.freeze.FreezeMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;

public class FreezeL implements Listener {
    private final FreezeMG freezeMG;

    public FreezeL(InventoryMG inventoryMG) {
        this.freezeMG = inventoryMG.freezeMG;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent playerMoveEvent){
        Player player = playerMoveEvent.getPlayer();
        String nick = player.getName();

        if(!freezeMG.playerIsFreezed(nick)) return;

        playerMoveEvent.setTo(playerMoveEvent.getFrom());
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent playerPickupItemEvent){
        Player player = playerPickupItemEvent.getPlayer();
        String nick = player.getName();

        if(!freezeMG.playerIsFreezed(nick)) return;

        playerPickupItemEvent.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent playerDropItemEvent){
        Player player = playerDropItemEvent.getPlayer();
        String nick = player.getName();

        if(!freezeMG.playerIsFreezed(nick)) return;

        playerDropItemEvent.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent blockPlaceEvent){
        Player player = blockPlaceEvent.getPlayer();
        String nick = player.getName();

        if(!freezeMG.playerIsFreezed(nick)) return;

        blockPlaceEvent.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent blockBreakEvent){
        Player player = blockBreakEvent.getPlayer();
        String nick = player.getName();

        if(!freezeMG.playerIsFreezed(nick)) return;

        blockBreakEvent.setCancelled(true);
    }
}
