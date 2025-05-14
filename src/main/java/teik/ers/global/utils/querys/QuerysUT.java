package teik.ers.global.utils.querys;

import teik.ers.bukkit.utilities.models.Comment;
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

    //GET
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

    public List<String> getOnlinePlayers(){
        String sql = "SELECT Nick FROM OnlinePlayersTable WHERE IsOnline = TRUE";
        List<String> onlinePlayers = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                onlinePlayers.add(rs.getString("Nick"));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return onlinePlayers;
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

    public Comment populateCommentData(ResultSet resultSet){
        try {
            int id = resultSet.getInt("CommentID");
            String reportedUUID = resultSet.getString("ReportedUUID");
            String reportedName = resultSet.getString("ReportedName");
            String staffUUID = resultSet.getString("StaffUUID");
            String staffName = resultSet.getString("StaffName");
            String commentTxt = resultSet.getString("commentTxt");
            String commentDate = resultSet.getString("CommentDate");

            return new Comment(id, reportedUUID, reportedName, staffUUID, staffName, commentTxt, commentDate);
        } catch (Exception e) {
            System.out.print("[EpicReports] Error 101 (ReportsManager), contact with developer : " + e.getMessage());
        }
        return null;
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
        String query = "SELECT IsOnline FROM PlayersTable WHERE UUID = ?";

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

    public void deleteComment(int commentID) {
        String query = "DELETE FROM CommentsTable WHERE CommentID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, commentID);
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

    //ADD

    public void addComment(Comment comment){
        String sql = "INSERT INTO CommentsTable (ReportedUUID, ReportedName, StaffUUID, StaffName, " +
                "CommentTxt, CommentDate) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, comment.getReportedUUID());
            preparedStatement.setString(2, comment.getReportedName());
            preparedStatement.setString(3, comment.getStaffUUID());
            preparedStatement.setString(4, comment.getStaffName());
            preparedStatement.setString(5, comment.getComment());
            preparedStatement.setString(6, comment.getDate());

            preparedStatement.executeUpdate();
        } catch (Exception e) {System.out.print("[EpicReports] Error with EpicReports: " + e.getMessage());}
    }

    public void addPlayer(String nick, boolean isOnline) {
        String sql = "INSERT INTO OnlinePlayersTable (Nick, IsOnline) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE IsOnline = VALUES(IsOnline)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nick);
            stmt.setBoolean(2, isOnline);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private void handleSQLException(SQLException e) {System.out.print("[EpicReports] Error with EpicReports: " + e.getMessage());
        e.printStackTrace();}
}
