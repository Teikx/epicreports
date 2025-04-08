package teik.ers.global.utils.querys;

import teik.ers.global.models.objects.Report;
import teik.ers.global.models.Process;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class QuerysUT {
    private final Connection connection;

    public QuerysUT(Connection connection) {
        this.connection = connection;
    }

    //GET SERVERS
    public List<String> getAllServers() {
        List<String> servers = new ArrayList<>();
        String query = "SELECT * FROM Servers";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                servers.add(resultSet.getString("Name"));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return servers;
    }

    //POPULATE REPORTS

    public void populateReportData(Report report, ResultSet resultSet){
        try {
            report.setReporterUUID(resultSet.getString("ReporterUUID"));
            report.setReportedUUID(resultSet.getString("ReportedUUID"));
            report.setReportID(resultSet.getInt("ID"));
            report.setProcess(Process.valueOf(resultSet.getString("Process")));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
            LocalDateTime dateTime = LocalDateTime.parse(report.getDate(), formatter);
            report.setDateTime(dateTime);
            report.setReporterServer(resultSet.getString("ReporterServer"));
            report.setReportedServer(resultSet.getString("ReportedServer"));
            report.setReporterLocation(resultSet.getString("ReporterLocation"));
            report.setReportedLocation(resultSet.getString("ReportedLocation"));
            report.setReportedGamemode(resultSet.getString("ReportedGamemode"));
            report.setReporterGamemode(resultSet.getString("ReporterGamemode"));
            report.setReportedHealth(resultSet.getInt("ReportedHealth"));
            report.setReporterHealth(resultSet.getInt("ReporterHealth"));
        } catch (Exception e) {
            System.out.print("[EpicReports] Error 101 (ReportsManager), contact with developer : " + e.getMessage());
        }
    }

    //CLEAR

    public void clearTables() {
        String[] tables = { "PlayersTable", "ReportsTable", "ArchivedReportsTable" };

        try (Statement stmt = connection.createStatement()) {

            stmt.execute("PRAGMA foreign_keys = OFF;");

            for (String table : tables) {
                stmt.execute("DELETE FROM " + table + ";");
            }

            stmt.execute("PRAGMA foreign_keys = ON;");
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    //UPDATE

    public void updateReportProcess(int reportId, String newProcess) {
        String query = "UPDATE ReportsTable SET Process = ? WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newProcess);
            statement.setInt(2, reportId);

            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void updateOnline(String uuid) {
        String query = "UPDATE PlayersTable SET IsOnline = 1 WHERE UUID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, uuid);
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
    public void updateOffline(String uuid) {
        String query = "UPDATE PlayersTable SET IsOnline = 0 WHERE UUID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, uuid);
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void updateLastDetails(String uuid, String lastServer) {
        String query = "UPDATE PlayersTable SET LastServer = ?, LastConnection = ? WHERE UUID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, lastServer);
            statement.setString(2, LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")));
            statement.setString(3, uuid);
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void updateLastLocation(String uuid, String lastLocation) {
        String query = "UPDATE PlayersTable SET LastLocation = ? WHERE UUID = ?;";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, lastLocation);
            statement.setString(2, uuid);
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    //CHECK

    public Boolean playerIsOnline(String uuid){
        String query = "SELECT IsOnline PlayersTable WHERE UUID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, uuid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("IsOnline");
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return false;
    }

    public List<String> playersOnline(Boolean isOnline){
        String query = "SELECT UUID FROM PlayersTable WHERE IsOnline = ?";
        List<String> onlineUUIDs = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, isOnline ? 1 : 0);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    onlineUUIDs.add(resultSet.getString("UUID"));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return onlineUUIDs;
    }

    //DELETE

    public void deleteReportById(int reportId) {
        String query = "DELETE FROM ReportsTable WHERE ID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reportId);
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void deleteArchivedReport(int reportId) {
        String query = "DELETE FROM ArchivedReportsTable WHERE ID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reportId);
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
    public void deleteAllReportsPerReported(String uuid){
        String query = "DELETE FROM ReportsTable WHERE ReportedUUID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, uuid);
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
    public void deleteArchivedReports(){
        String query = "DELETE FROM ReportsTable WHERE Process = 'Archived'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private void handleSQLException(SQLException e) {System.out.print("[EpicReports] Error with EpicReports: " + e.getMessage());
        e.printStackTrace();}
}
