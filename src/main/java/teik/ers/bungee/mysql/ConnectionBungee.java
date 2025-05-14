package teik.ers.bungee.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.md_5.bungee.api.ChatColor;
import teik.ers.bungee.EpicReports;
import teik.ers.global.utils.sql.TablesHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SuppressWarnings("ALL")
public class ConnectionBungee {
    private HikariDataSource hikariDataSource;
    private Connection connection;
    private final EpicReports plugin;
    private final String host;
    private final int port;
    private final String database;
    private final String user;
    private final String password;

    public ConnectionBungee(String host, int port, String database, String user, String password, EpicReports plugin) {
        this.plugin = plugin;
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        connectWithBungee();
    }

    private void connectWithBungee() {
        HikariConfig hikariConfig = new HikariConfig();
        try {
            try{
                hikariConfig.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false");
                hikariConfig.setUsername(user);
                hikariConfig.setPassword(password);
                hikariConfig.addDataSourceProperty("characterEncoding", "utf8");
                hikariConfig.addDataSourceProperty("useUnicode", "true");
                hikariConfig.addDataSourceProperty("connectionCollation", "utf8_general_ci");

                hikariDataSource = new HikariDataSource(hikariConfig);
            }catch (Exception e){
                Class.forName("com.mysql.jdbc.Driver");
                connection = createConnection();
            }

            TablesHelper tablesHelper = new TablesHelper(getConnection(), true);
            tablesHelper.createTablesAndTriggers();
            tablesHelper.checkTablesAndDelete();

            logInfo();
            plugin.isMysql = true;
        } catch (Exception e) {
            logError();
            e.printStackTrace();
        }
    }

    private Connection createConnection() throws ClassNotFoundException, SQLException {
        String url = String.format("jdbc:mysql://%s:%d/%s?useSSL=false&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useLegacyDatetimeCode=false&serverTimezone=UTC", host, port, database);
        return DriverManager.getConnection(url, user, password);
    }

    private void logError() {
        plugin.getLogger().info(ChatColor.translateAlternateColorCodes('&',
                "&b[&fEpicReports&b] &cError connecting to your DataBase, the plugin is disabled"));
    }
    private void logInfo() {
        plugin.getLogger().info(ChatColor.translateAlternateColorCodes('&',
                "&b[&fEpicReports&b] &aEpicReports connected to MYSQL"));
    }

    public Connection getConnection() {
        try{
            if(hikariDataSource != null){
                return hikariDataSource.getConnection();
            }
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
