package teik.ers.global.models.objects;

public class PlayerOBJ {
    private final String name, uuid, ip;
    private String lastLocation = " ", lastServer = " ", lastConnection = " ", rank = " ";
    private boolean isOnline = false;
    private Locate lastLocate = new Locate();

    public PlayerOBJ(String name, String uuid, String ip) {
        this.name = name;
        this.uuid = uuid;
        this.ip = ip;
    }

    public String getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(String lastLocation) {
        this.lastLocation = lastLocation;
        try {
            lastLocate.setFromString(lastLocation);
        }catch (Exception ignored){
            lastLocate = null;
        }
    }

    public String getLastserver() {
        return lastServer;
    }

    public void setLastServer(String lastServer) {
        this.lastServer = lastServer;
    }

    public String getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(String lastconnection) {
        this.lastConnection = lastconnection;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public String getIp() {
        return ip;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public Locate getLastLocate() {
        return lastLocate;
    }
}
