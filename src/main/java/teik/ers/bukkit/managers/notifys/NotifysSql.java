package teik.ers.bukkit.managers.notifys;

import teik.ers.global.models.objects.Notify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotifysSql {
    private final NotifysMG notifysManager;
    private final Connection connection;

    public NotifysSql(NotifysMG notifysManager, Connection connection) {
        this.notifysManager = notifysManager;
        this.connection = connection;
    }

    public void checkNewNotifications(int lastID) {
        String sql = "SELECT NotifysID, ReporterName, ReportedName, StaffName, IsProcessed "
                + "FROM NotifysTable WHERE NotifysID > ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, lastID);
            try (ResultSet resultSet = stmt.executeQuery()) {
                processResultSet(resultSet);
            }
        } catch (SQLException ignored) {}
    }

    private void processResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Notify notify = mapResultSetToNotify(resultSet);
            if(notify.isUserType()){
                notifysManager.AddNotifyToUserQueue(notify);
            }else{
                notifysManager.AddNotifyToAdminQueue(notify);
            }
        }
    }

    private Notify mapResultSetToNotify(ResultSet resultSet) throws SQLException {
        String reporterName = resultSet.getString("ReporterName");
        String reportedName = resultSet.getString("ReportedName");
        String staffName = resultSet.getString("StaffName");
        boolean isProcessed = resultSet.getBoolean("IsProcessed");
        int notifyID = resultSet.getInt("NotifysID");

        Notify notify = new Notify(reporterName, reportedName, notifyID);

        if (staffName != null) {
            notify.setStaffNick(staffName);
            notify.setProcessed(isProcessed);
        }

        return notify;
    }
}
