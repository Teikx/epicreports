package teik.ers.bukkit.managers.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import teik.ers.bukkit.configs.ConfigManager;
import teik.ers.global.utils.sql.TablesHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlMG {
    private HikariDataSource hikariDataSource;
    private final String host;
    private final int port;
    private final String database;
    private final String user;
    private final String password;
    private Connection connection;

    private final ConfigManager configManager;

    public MysqlMG(String host, int port, String database, String user, String password, ConfigManager configManager) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        this.configManager = configManager;
        connectWithBukkit();
    }

    private void connectWithBukkit() {
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
            if(!tablesHelper.checkPlayersTable()){
                configManager.setMYSQLActive(false);
                return;
            }

            logInfoBukkit();
        } catch (Exception e) {
            logErrorBukkit();
            configManager.setMYSQLActive(false);
            hikariDataSource = null;
            System.out.print("[EpicReports] Error whit mysql: " + e.getMessage());
        }
    }

    private Connection createConnection() throws ClassNotFoundException, SQLException {
        String url = String.format("jdbc:mysql://%s:%d/%s?useSSL=false&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useLegacyDatetimeCode=false&serverTimezone=UTC", host, port, database);
        return DriverManager.getConnection(url, user, password);
    }

    private void logErrorBukkit() {
        Bukkit.getConsoleSender().sendMessage(convertColor("&b[&fEpicReports&b] &c" + "Error connecting to your DataBase"));
    }
    private void logInfoBukkit() {
        Bukkit.getConsoleSender().sendMessage(convertColor("&b[&fEpicReports&b] &a" + "EpicReports connected to MYSQL"));
    }

    public Connection getConnection() {
        try{
            if(hikariDataSource != null){
                return hikariDataSource.getConnection();
            }
            return connection;
        } catch (SQLException e) {
            System.out.print("[EpicReports] Error whit mysql: " + e.getMessage());
        }
        return null;
    }

    private String convertColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
