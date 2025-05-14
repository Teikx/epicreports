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
import teik.ers.bukkit.managers.updateinvs.UpdateInvsMG;
import teik.ers.bukkit.utilities.models.Comment;
import teik.ers.bukkit.utilities.models.SettingsER;
import teik.ers.bukkit.utilities.models.enums.ChatType;
import teik.ers.global.models.objects.PlayerOBJ;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatL implements Listener {

    private final InventoryMG inventoryMG;
    private final InvDataMG invDataMG;
    private final ReportMG reportMG;
    private final PlayersListMG playersListMG;
    private final LanguagesManager messagesMG;
    private final UpdateInvsMG updateInvsMG;

    public ChatL(InventoryMG inventoryMG) {
        this.inventoryMG = inventoryMG;
        this.invDataMG = inventoryMG.invDataMG;
        this.reportMG = inventoryMG.reportMG;
        this.playersListMG = reportMG.getPlayersListMG();
        this.messagesMG = inventoryMG.messageManager;
        this.updateInvsMG = inventoryMG.updateInvsMG;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent asyncPlayerChatEvent){
        Player player = asyncPlayerChatEvent.getPlayer();

        if(!invDataMG.playerIsChatting(player)) return;
        String txt = asyncPlayerChatEvent.getMessage();

        ChatType chatType = invDataMG.getPlayerChatType(player);

        if(txt.equalsIgnoreCase("cancel")) {
            if(chatType.equals(ChatType.Commenting)) player.sendMessage(messagesMG.getComments_cancelled());
            else{
                player.sendMessage(messagesMG.getText_cancelled());
            }
            invDataMG.removePlayerChatType(player);
            asyncPlayerChatEvent.setCancelled(true);
            return;
        }

        SettingsER settingsER = invDataMG.getSettingsER(player);
        switch (chatType){
            case Commenting:
                commenting(player, txt);
                break;
            case CustomizeCommand:
                customizeCommand(txt, player, settingsER);
                break;
            case MenuAlias:
                menuAlias(txt, player, settingsER);
                break;
            case NotifySound:
                notifySound(txt, player, settingsER);
                break;
            case ProcessedNotifySound:
                processedNotifySound(txt, player, settingsER);
                break;
            case Reporting:
                reporting(txt, player);
                break;
        }

        invDataMG.removePlayerChatType(player);
        asyncPlayerChatEvent.setCancelled(true);
        player.sendMessage(messagesMG.getText_successful());
    }

    private void commenting(Player player, String commentTxt) {
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
        updateInvsMG.sendUpdateInvs(player);
        player.sendMessage(messagesMG.getComments_successful());
    }

    private void customizeCommand(String txt, Player player, SettingsER settingsER) {
        settingsER.setCustomCommand(txt);
        invDataMG.putSettingsER(player, settingsER);
        inventoryMG.openSettingsM(player);
    }

    private void menuAlias(String txt, Player player, SettingsER settingsER) {
        settingsER.setMenuAliasName(txt);
        invDataMG.putSettingsER(player, settingsER);
        inventoryMG.openSettingsM(player);
    }

    private void notifySound(String txt, Player player, SettingsER settingsER) {
        settingsER.setNotifySoundName(txt);
        invDataMG.putSettingsER(player, settingsER);
        inventoryMG.openNotifysM(player);
    }

    private void processedNotifySound(String txt, Player player, SettingsER settingsER) {
        settingsER.setNotifyProcessedSoundName(txt);
        invDataMG.putSettingsER(player, settingsER);
        inventoryMG.openNotifysM(player);
    }

    private void reporting(String txt, Player player){
        String nick = invDataMG.getNickSelected(player);

        player.performCommand("report " + nick + " " +  txt);
    }
}
