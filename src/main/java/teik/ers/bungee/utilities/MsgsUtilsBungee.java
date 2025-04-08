package teik.ers.bungee.utilities;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import teik.ers.bungee.EpicReports;

public class MsgsUtilsBungee {
    public TextComponent convertBungeeColor(String msg){
        msg = ChatColor.translateAlternateColorCodes('&', msg);

        String[] words = msg.split(" ");
        TextComponent component = new TextComponent();
        ChatColor currentColor = ChatColor.WHITE;

        for (String word : words) {
            if (word.contains("§")) {
                currentColor = ChatColor.getByChar(word.charAt(1));
            }
            TextComponent part = new TextComponent(word + " ");
            part.setColor(currentColor);
            component.addExtra(part);
        }
        return component;
    }
    public String replaceInText(String txt, String placeholder, String replacement) {
        return txt.replaceAll(placeholder, replacement);
    }

    public void pluginMessage(Boolean isEnabling, EpicReports plugin){
        if(isEnabling){
            plugin.getLogger().info(ChatColor.translateAlternateColorCodes('&',"&b[&fEpicReports&b] &aThe plugin is enabled successful"));
            return;
        }
        plugin.getLogger().info(ChatColor.translateAlternateColorCodes('&',"&b[&fEpicReports&b] &cThe plugin is disabled successful"));
    }
}
