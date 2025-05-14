package teik.ers.bungee.notifys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotifysSql {

    private final Connection connection;

    public NotifysSql(Connection connection) {
        this.connection = connection;
    }

    public void addNotification(String reporterName, String reportedName, String staffName, boolean isProcessed) {
        if (!notificationExists(reporterName, reportedName)) {
            insertNotification(reporterName, reportedName, staffName, isProcessed);
        }
    }

    private boolean notificationExists(String reporterName, String reportedName) {
        String checkSql = "SELECT COUNT(*) FROM NotifysTable WHERE ReporterName = ? AND ReportedName = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setString(1, reporterName);
            checkStmt.setString(2, reportedName);
            try (ResultSet resultSet = checkStmt.executeQuery()) {
                if (resultSet.next()) {
                    return false;
                }
            }
        } catch (SQLException ignored) {}
        return true;
    }

    private void insertNotification(String reporterName, String reportedName, String staffName, boolean isProcessed) {
        String insertSql = "INSERT INTO NotifysTable (ReporterName, ReportedName, StaffName, IsProcessed) "
                + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
            insertStmt.setString(1, reporterName);
            insertStmt.setString(2, reportedName);
            insertStmt.setString(3, staffName);
            insertStmt.setBoolean(4, isProcessed);
            insertStmt.executeUpdate();
        } catch (SQLException ignored) {}
    }

    public void clearNotifysTable() {
        String sql = "TRUNCATE TABLE NotifysTable";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException ignored) {}
    }
}
