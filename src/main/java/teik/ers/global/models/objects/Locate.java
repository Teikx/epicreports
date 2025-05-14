package teik.ers.global.models.objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Locate {
    private String world = "";
    private Double x = 0.0, y = 0.0, z = 0.0;
    private Float yaw = (float) 0, pitch = (float) 0;

    public void createLocate(String world, Double x, Double y, Double z, Float yaw, Float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }
    public String createString(){
        return String.format("%s;%.2f;%.2f;%.2f;%.2f;%.2f", world, x, y, z, yaw, pitch);
    }

    public void setFromString(String data){
        try{
            String[] parts = data.split(";");

            this.world = parts[0];
            this.x = Double.parseDouble(parts[1]);
            this.y = Double.parseDouble(parts[2]);
            this.z = Double.parseDouble(parts[3]);
            this.yaw = Float.parseFloat(parts[4]);
            this.pitch = Float.parseFloat(parts[5]);
        } catch (Exception ignored) {}
    }

    public String getWorld(){
        return this.world;
    }

    public String getXYZ(){
        return String.format("x: %.2f; y: %.2f; z: %.2f", x, y, z);
    }

    public void teleportToLocate(Player player){
        Location location = getLocation();
        if(location == null) {
            player.sendMessage(
                    ChatColor.translateAlternateColorCodes('&',
                    "&b[&fEpicReports&b] &cThis world not exist here: " + this.world
                    )
            );
            return;
        }
        player.teleport(location);
    }

    private Location getLocation(){
        World world = Bukkit.getWorld(this.world);
        if(world != null)  return new Location(world, x, y, z, yaw, pitch);
        return null;
    }
}
