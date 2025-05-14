package teik.ers.bukkit.managers.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import teik.ers.global.utils.sql.TablesHelper;

import java.sql.Connection;
import java.sql.SQLException;

public class SqLiteMG {
    private HikariDataSource hikariDataSource;

    public SqLiteMG(String path){
        HikariConfig hikariConfig = new HikariConfig();
        try{
            hikariConfig.setDriverClassName("org.sqlite.JDBC");
            hikariConfig.setConnectionTestQuery("SELECT 1");
            hikariConfig.setJdbcUrl("jdbc:sqlite:" + path);
            hikariConfig.addDataSourceProperty("busy_timeout", "3000");
            hikariDataSource = new HikariDataSource(hikariConfig);
            TablesHelper tablesHelper = new TablesHelper(getConnection(), false);
            tablesHelper.createTablesAndTriggers();
            logInfoBukkit();
        } catch (Exception e) {
            hikariDataSource = null;
            logErrorBukkit();
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try{
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            logErrorBukkit();
            return null;
        }
    }

    private void logErrorBukkit() {
        Bukkit.getConsoleSender().sendMessage(convertColor("&b[&fEpicReports&b]&c Error loading the plugin!"));
    }
    private void logInfoBukkit() {
        Bukkit.getConsoleSender().sendMessage(convertColor("&b[&fEpicReports&b]&a reports.db is done!"));
    }

    public HikariDataSource getHikariDataSource() {
        return hikariDataSource;
    }

    private String convertColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
