package teik.ers.bukkit.managers.msgchannel;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.managers.freeze.FreezeMG;
import teik.ers.bukkit.managers.updateinvs.UpdateInvsMG;
import teik.ers.bukkit.utilities.UtilitiesPlayers;
import teik.ers.global.models.objects.Locate;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class ChannelMG implements PluginMessageListener {

    private final UtilsMsgChannel utilsMsgChannel;
    private final UtilitiesPlayers utilitiesPlayers;

    private final UpdateInvsMG updateInvsMG;
    private final FreezeMG freezeMG;

    public ChannelMG(EpicReports plugin) {
        utilsMsgChannel = new UtilsMsgChannel(plugin.getConnection());
        utilitiesPlayers = plugin.utilitiesPlayers;

        this.updateInvsMG = plugin.inventoryMG.updateInvsMG;
        this.freezeMG = plugin.inventoryMG.freezeMG;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("epicreports:main")) {
            return;
        }

        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(message))) {
            String subchannel = in.readUTF();

            if(subchannel.equals("UpdateReportsManager")){
                updateInvsMG.checkOpenInventories();
                return;
            }

            String playerName = in.readUTF();
            Player targetPlayer = Bukkit.getPlayer(playerName);

            switch (subchannel){
                case "GetReportedDetails":
                    int reportID = in.readInt();

                    if (targetPlayer != null) {
                        Locate locate = utilitiesPlayers.getPlayerLocate(targetPlayer);
                        String gamemode = targetPlayer.getGameMode().toString();
                        int roundedHealth = (int) Math.round(targetPlayer.getHealth());
                        utilsMsgChannel.updateReportedDetails(reportID, locate.createString(), gamemode, roundedHealth);
                    }
                    break;

                case "GetReporterDetails":
                    int reportIDD = in.readInt();

                    if (targetPlayer != null) {
                        Locate locate = utilitiesPlayers.getPlayerLocate(targetPlayer);
                        String gamemode = targetPlayer.getGameMode().toString();
                        int roundedHealth = (int) Math.round(targetPlayer.getHealth());
                        utilsMsgChannel.updateReporterDetails(reportIDD, locate.createString(), gamemode, roundedHealth);
                    }
                    break;

                case "TeleportPlayerToLocate":
                    String locate = in.readUTF();

                    Locate locate1 = new Locate();
                    locate1.setFromString(locate);
                    locate1.teleportToLocate(targetPlayer);
                    break;

                case "TeleportPlayerToPlayer":
                    String otherPlayerName = in.readUTF();
                    Player otherPlayer = Bukkit.getPlayer(otherPlayerName);

                    targetPlayer.teleport(otherPlayer);
                    break;

                case "FreezePlayer":
                    freezeMG.freezePlayer(playerName);
                    break;
                case "UnfreezePlayer":
                    freezeMG.unfreezePlayer(playerName);
                    break;
            }
        } catch (Exception e) {
            System.out.print("[EpicReports] Error 501 (ChannelManager): ");
            e.printStackTrace();
        }
    }
}
