package teik.ers.bukkit.managers.freeze;

import java.util.ArrayList;
import java.util.List;

public class FreezeMG {
    private final List<String> playersFreezeds;

    public FreezeMG() {
        this.playersFreezeds = new ArrayList<>();
    }

    public boolean playerIsFreezed(String nick){
        return  playersFreezeds.contains(nick);
    }

    public void freezePlayer(String nick){
        playersFreezeds.add(nick);
    }

    public void unfreezePlayer(String nick){
        playersFreezeds.remove(nick);
    }
}
