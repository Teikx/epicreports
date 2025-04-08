package teik.ers.bukkit.utilities;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.configs.LanguagesManager;

import java.util.List;

public class UtilitiesMsgs {

    private final LanguagesManager messageManager;

    public UtilitiesMsgs(LanguagesManager messageManager) {
        this.messageManager = messageManager;
    }

    public String convertColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    public String removeColor(String text) {
        return ChatColor.stripColor(text);
    }
    public void sendListString(Player player, List<String> List){
        for (String msg : List)
            player.sendMessage(convertColor(msg));
    }
    public void suggestMsg(Player player, String suggest, String type){
        String msg = messageManager.getClick_here();
        msg = msg
                .replaceAll("%type%", type)
        ;

        TextComponent msj = new TextComponent(msg);
        msj.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggest));
        msj.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(messageManager.getClick_here_show_text_hover()).create()));

        player.spigot().sendMessage(msj);
    }
    public void pluginMessage(boolean isEnabling){
        if(isEnabling){
            Bukkit.getConsoleSender().sendMessage(convertColor("&b[&fEpicReports&b] &aThe plugin is enabled successful"));
            return;
        }
        Bukkit.getConsoleSender().sendMessage(convertColor("&b[&fEpicReports&b] &cThe plugin is disabled successful"));

    }
}
