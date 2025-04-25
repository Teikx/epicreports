package teik.ers.bukkit.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import teik.ers.bukkit.configs.LanguagesManager;
import teik.ers.bukkit.managers.inventories.InvDataMG;
import teik.ers.bukkit.managers.inventories.InventoryMG;
import teik.ers.bukkit.managers.reportmg.ReportMG;
import teik.ers.bukkit.managers.reportmg.helpers.PlayersListMG;
import teik.ers.bukkit.utilities.models.Comment;
import teik.ers.global.models.objects.PlayerOBJ;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatL implements Listener {

    private final InvDataMG invDataMG;
    private final ReportMG reportMG;
    private final PlayersListMG playersListMG;
    private final LanguagesManager messagesMG;

    public ChatL(InventoryMG inventoryMG) {
        this.invDataMG = inventoryMG.invDataMG;
        this.reportMG = inventoryMG.reportMG;
        this.playersListMG = reportMG.getPlayersListMG();
        this.messagesMG = inventoryMG.messageManager;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent asyncPlayerChatEvent){
        Player player = asyncPlayerChatEvent.getPlayer();

        if(!invDataMG.playerIsCommenting(player)) return;
        String commentTxt = asyncPlayerChatEvent.getMessage();

        if(commentTxt.equalsIgnoreCase("cancel")) {
            invDataMG.removePlayerCommenting(player);
            return;
        }

        String reportedUUID = invDataMG.getUuidSelected(player);
        PlayerOBJ reportedOBJ = playersListMG.getPlayerOBJ(reportedUUID);

        String dateFormatted = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"));

        Comment comment = new Comment(1,
                reportedOBJ.getUuid(),
                reportedOBJ.getName(),
                player.getUniqueId().toString(),
                player.getName(),
                commentTxt,
                dateFormatted
                );

        reportMG.addComment(comment);
        invDataMG.removePlayerCommenting(player);
        asyncPlayerChatEvent.setCancelled(true);
        player.sendMessage(messagesMG.getComments_successful());
    }
}
