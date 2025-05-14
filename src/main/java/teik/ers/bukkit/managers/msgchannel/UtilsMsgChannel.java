package teik.ers.bukkit.managers.msgchannel;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UtilsMsgChannel {

    private final Connection connection;

    public UtilsMsgChannel(Connection connection) {
        this.connection = connection;
    }


    public void updateReportedDetails(int reportID, String reportedLocation, String reportedGamemode, int reportedHealth){
        updatePlayerDetails(
          reportID,
                "ReportedLocation", reportedLocation,
                "ReportedGamemode", reportedGamemode,
                "ReportedHealth", reportedHealth
        );
    }

    public void updateReporterDetails(int reportID, String reporterLocation, String reporterGamemode, int reporterHealth){
        updatePlayerDetails(
                reportID,
                "ReporterLocation", reporterLocation,
                "ReporterGamemode", reporterGamemode,
                "ReporterHealth", reporterHealth
        );
    }

    private void updatePlayerDetails(int reportID,
                                     String column1, String playerLocation,
                                     String column2, String playerGamemode,
                                     String column3, int playerHealth
                                     ){

        String sql = "UPDATE ReportsTable SET " + column1 + " = ?, "
                + column2 + " = ?, " + column3 + " = ? WHERE ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, playerLocation);
            preparedStatement.setString(2, playerGamemode);
            preparedStatement.setInt(3, playerHealth);
            preparedStatement.setInt(4, reportID);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.print("[EpicReports] Error whit EpicReports: " + e.getMessage());
        }
    }
}
