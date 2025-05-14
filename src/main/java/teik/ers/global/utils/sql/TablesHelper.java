package teik.ers.global.utils.sql;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.*;


public class TablesHelper {
    private final Connection connection;
    private final boolean isMysql;

    public TablesHelper(Connection connection, boolean isMysql) {
        this.isMysql = isMysql;
        this.connection = connection;
    }

    private boolean tableExists(String tableName) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            try (ResultSet resultSet = metaData.getTables(null, null, tableName.toUpperCase(), null)) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("[EpicReports] Failed to check if table exists: " + tableName, e);
        }
    }

    public boolean checkPlayersTable() {
        if (!tableExists("PlayersTable")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&b[&fEpicReports&b] &cIf you're going to use MySQL," +
                    "&e it's necessary to install the plugin on your BungeeCord and on all the servers.&c Don't forget to configure " +
                    "every servers to the same database!"));
            return false;
        }
        return true;
    }

    public void createTablesAndTriggers() {
        try (Statement statement = connection.createStatement()) {
            executeUpdate(statement, createPlayersTable());
            executeUpdate(statement, createReportsTable());
            executeUpdate(statement, createArchivedReportsTable());
            executeUpdate(statement, createCommentsTable());
            executeUpdate(statement, createNotifysTable());
            executeUpdate(statement, createDeleteTrigger());
            executeUpdate(statement, createMoveToArchivedTrigger());
            if(isMysql) {
                executeUpdate(statement, createServersTable());
                executeUpdate(statement, createOnlinePlayersTable());
                executeUpdate(statement, createInsertServerTrigger());
                executeUpdate(statement, createDeleteServerTrigger());
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private void executeUpdate(Statement statement, String sql){
        try{
            statement.executeUpdate(sql);
        }catch (SQLException e){
            handleSQLException(e);
        }
    }

    private String createPlayersTable() {
        return "CREATE TABLE IF NOT EXISTS PlayersTable (" +
                "UUID VARCHAR(36) PRIMARY KEY, " +
                "Nick VARCHAR(40), " +
                "IP VARCHAR(45), " +
                "LastServer VARCHAR(20), " +
                "LastLocation VARCHAR(255), " +
                "LastConnection VARCHAR(255), " +
                "IsOnline BOOLEAN" +
                ");";
    }
    private String createReportsTable() {
        if(isMysql) {
            return "CREATE TABLE IF NOT EXISTS ReportsTable (" +
                    "ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    "ReportedUUID VARCHAR(36), " +
                    "ReporterUUID VARCHAR(36) NOT NULL, " +
                    "Reason TEXT NOT NULL, " +
                    "ReportDate VARCHAR(255) NOT NULL, " +
                    "ReporterServer VARCHAR(20), " +
                    "ReporterLocation VARCHAR(255), " +
                    "ReportedServer VARCHAR(20), " +
                    "ReportedLocation VARCHAR(255), " +
                    "ReportedGamemode VARCHAR(15), " +
                    "ReporterGamemode VARCHAR(15), " +
                    "ReportedHealth INT, " +
                    "ReporterHealth INT, " +
                    "Process VARCHAR(10), " +
                    "FOREIGN KEY (ReporterUUID) REFERENCES PlayersTable(UUID), " +
                    "FOREIGN KEY (ReportedUUID) REFERENCES PlayersTable(UUID)" +
                    ");";
        }else{
            return "CREATE TABLE IF NOT EXISTS ReportsTable (" +
                    "ID INTEGER PRIMARY KEY, " +
                    "ReportedUUID VARCHAR(36), " +
                    "ReporterUUID VARCHAR(36) NOT NULL, " +
                    "Reason TEXT NOT NULL, " +
                    "ReportDate VARCHAR(255) NOT NULL, " +
                    "ReporterServer VARCHAR(20), " +
                    "ReporterLocation VARCHAR(255), " +
                    "ReportedServer VARCHAR(20), " +
                    "ReportedLocation VARCHAR(255), " +
                    "ReportedGamemode VARCHAR(15), " +
                    "ReporterGamemode VARCHAR(15), " +
                    "ReportedHealth INT, " +
                    "ReporterHealth INT, " +
                    "Process VARCHAR(10), " +
                    "FOREIGN KEY (ReporterUUID) REFERENCES PlayersTable(UUID), " +
                    "FOREIGN KEY (ReportedUUID) REFERENCES PlayersTable(UUID)" +
                    ");";
        }
    }
    private String createArchivedReportsTable() {
        return "CREATE TABLE IF NOT EXISTS ArchivedReportsTable (" +
                "ID INT AUTO_INCREMENT PRIMARY KEY, " +
                "ReportedUUID VARCHAR(36), " +
                "ReporterUUID VARCHAR(36) NOT NULL, " +
                "Reason TEXT NOT NULL, " +
                "ReportDate VARCHAR(255) NOT NULL, " +
                "ReporterServer VARCHAR(20), " +
                "ReporterLocation VARCHAR(255), " +
                "ReportedServer VARCHAR(20), " +
                "ReportedLocation VARCHAR(255), " +
                "ReportedGamemode VARCHAR(15), " +
                "ReporterGamemode VARCHAR(15), " +
                "ReportedHealth INT, " +
                "ReporterHealth INT, " +
                "Process VARCHAR(10), " +
                "FOREIGN KEY (ReporterUUID) REFERENCES PlayersTable(UUID), " +
                "FOREIGN KEY (ReportedUUID) REFERENCES PlayersTable(UUID)" +
                ");";
    }
    private String createCommentsTable() {
        if(isMysql){
            return "CREATE TABLE IF NOT EXISTS CommentsTable (" +
                    "CommentID INT AUTO_INCREMENT PRIMARY KEY, " +
                    "ReportedUUID VARCHAR(36), " +
                    "ReportedName VARCHAR(36), " +
                    "StaffUUID VARCHAR(36) NOT NULL, " +
                    "StaffName VARCHAR(255) NOT NULL, " +
                    "CommentTxt TEXT NOT NULL, " +
                    "CommentDate VARCHAR(255) NOT NULL, " +
                    "FOREIGN KEY (ReportedUUID) REFERENCES PlayersTable(UUID)" +
                    ");";
        }
        return "CREATE TABLE IF NOT EXISTS CommentsTable (" +
                "CommentID INT PRIMARY KEY, " +
                "ReportedUUID VARCHAR(36), " +
                "ReportedName VARCHAR(36), " +
                "StaffUUID VARCHAR(36) NOT NULL, " +
                "StaffName VARCHAR(255) NOT NULL, " +
                "CommentTxt TEXT NOT NULL, " +
                "CommentDate VARCHAR(255) NOT NULL, " +
                "FOREIGN KEY (ReportedUUID) REFERENCES PlayersTable(UUID)" +
                ");";
    }
    private String createNotifysTable() {
        return "CREATE TABLE IF NOT EXISTS NotifysTable (" +
                "NotifysID INT AUTO_INCREMENT PRIMARY KEY, " +
                "ReporterName VARCHAR(255) NOT NULL, " +
                "ReportedName VARCHAR(255) NOT NULL, " +
                "StaffName VARCHAR(255), " +
                "IsProcessed BOOLEAN" +
                ");";
    }
    private String createOnlinePlayersTable(){
        return "CREATE TABLE IF NOT EXISTS OnlinePlayersTable (" +
                "Nick VARCHAR(36) PRIMARY KEY, " +
                "IsOnline BOOLEAN" +
                ");";
    }
    private String createServersTable() {
        return "CREATE TABLE IF NOT EXISTS Servers (" +
                "Name VARCHAR(255) PRIMARY KEY" +
                ");";
    }
    private String createDeleteTrigger() {
        return "CREATE TRIGGER IF NOT EXISTS deleteReportData " +
                "AFTER DELETE ON PlayersTable " +
                "FOR EACH ROW " +
                "BEGIN " +
                "DELETE FROM ReportsTable WHERE ReportedUUID = OLD.UUID; " +
                "DELETE FROM CommentsTable WHERE ReportedUUID = OLD.UUID; " +
                "END";
    }
    private String createMoveToArchivedTrigger(){
        if(isMysql) {
            return "CREATE TRIGGER IF NOT EXISTS MoveToArchived " +
                    "AFTER UPDATE ON ReportsTable " +
                    "FOR EACH ROW " +
                    "BEGIN " +
                    "IF NEW.Process = 'Archived' THEN " +
                    "INSERT INTO ArchivedReportsTable (" +
                    "ReportedUUID, ReporterUUID, Reason, ReportDate, ReporterServer, " +
                    "ReporterLocation, ReportedServer, ReportedLocation, ReportedGamemode, " +
                    "ReporterGamemode, ReportedHealth, ReporterHealth, Process" +
                    ") " +
                    "VALUES (" +
                    "NEW.ReportedUUID, NEW.ReporterUUID, NEW.Reason, NEW.ReportDate, " +
                    "NEW.ReporterServer, NEW.ReporterLocation, NEW.ReportedServer, " +
                    "NEW.ReportedLocation, NEW.ReportedGamemode, NEW.ReporterGamemode, " +
                    "NEW.ReportedHealth, NEW.ReporterHealth, NEW.Process" +
                    "); " +
                    "END IF; " +
                    "END;";
        }
        return "CREATE TRIGGER IF NOT EXISTS MoveToArchived " +
                "AFTER UPDATE ON ReportsTable " +
                "FOR EACH ROW " +
                "WHEN NEW.Process = 'Archived' " +
                "BEGIN " +
                "    INSERT INTO ArchivedReportsTable (" +
                "        ReportedUUID, ReporterUUID, Reason, ReportDate, ReporterServer, " +
                "        ReporterLocation, ReportedServer, ReportedLocation, ReportedGamemode, " +
                "        ReporterGamemode, ReportedHealth, ReporterHealth, Process" +
                "    ) " +
                "    VALUES (" +
                "        NEW.ReportedUUID, NEW.ReporterUUID, NEW.Reason, NEW.ReportDate, " +
                "        NEW.ReporterServer, NEW.ReporterLocation, NEW.ReportedServer, " +
                "        NEW.ReportedLocation, NEW.ReportedGamemode, NEW.ReporterGamemode, " +
                "        NEW.ReportedHealth, NEW.ReporterHealth, NEW.Process" +
                "    ); " +
                "END;";
    }
    private String createInsertServerTrigger(){
        return "CREATE TRIGGER IF NOT EXISTS insert_server_after_report "
                + "AFTER INSERT ON ReportsTable "
                + "FOR EACH ROW BEGIN "
                + "IF NOT EXISTS (SELECT 1 FROM Servers WHERE Name = NEW.ReportedServer) THEN "
                + "INSERT INTO Servers (Name) VALUES (NEW.ReportedServer); "
                + "END IF; END;";

    }
    private String createDeleteServerTrigger(){
        return "CREATE TRIGGER IF NOT EXISTS delete_server_after_report "
                + "AFTER DELETE ON ReportsTable "
                + "FOR EACH ROW BEGIN "
                + "IF NOT EXISTS (SELECT 1 FROM ReportsTable WHERE ReportedServer = OLD.ReportedServer) THEN "
                + "DELETE FROM Servers WHERE Name = OLD.ReportedServer; "
                + "END IF; END;";
    }

    public void checkTablesAndDelete() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS ReportsValue");
            statement.executeUpdate("DROP TABLE IF EXISTS Notifys");
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private void handleSQLException(SQLException e) {
        System.out.print("[EpicReports] Error whit mysql: " + e.getMessage());
        e.printStackTrace();
    }
}

