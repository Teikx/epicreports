package teik.ers.global.utils.querys;

import teik.ers.bukkit.utilities.models.Comment;
import teik.ers.global.models.objects.PlayerOBJ;
import teik.ers.global.models.objects.Report;

import java.sql.*;
import java.util.List;

public class AddQuerysUT {
    private final Connection connection;

    public AddQuerysUT(Connection connection) {
        this.connection = connection;
    }

    public int addReportSQL(Report report) {
        insertPlayer(report.getReportedPlayer());
        insertPlayer(report.getReporterPlayer());

        return insertReport(report);
    }

    public void addPlayersSqLite(List<PlayerOBJ> players) {
        String sql = "INSERT OR IGNORE INTO PlayersTable (UUID, Nick, IP, IsOnline, LastServer, LastLocation, LastConnection) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (PlayerOBJ player : players) {
                    preparedStatement.setString(1, player.getUuid());
                    preparedStatement.setString(2, player.getName());
                    preparedStatement.setString(3, player.getIp());
                    preparedStatement.setBoolean(4, true);
                    preparedStatement.setString(5, player.getLastserver());
                    preparedStatement.setString(6, player.getLastLocation());
                    preparedStatement.setString(7, player.getLastConnection());
                    preparedStatement.addBatch();
                }

                preparedStatement.executeBatch();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                handleSQLException(rollbackEx);
            }
            handleSQLException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                handleSQLException(autoCommitEx);
            }
        }
    }


    public void addReportListSQLite(List<Report> reports) {
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement playerStmt = connection.prepareStatement(
                    "INSERT OR IGNORE INTO PlayersTable (UUID, Nick, IP, IsOnline, " +
                            "LastServer, LastLocation, LastConnection) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                for (Report report : reports) {
                    addPlayerToBatch(playerStmt, report.getReportedPlayer());
                    addPlayerToBatch(playerStmt, report.getReporterPlayer());
                }
                playerStmt.executeBatch();
            }

            try (PreparedStatement reportStmt = connection.prepareStatement(
                    "INSERT INTO ReportsTable (ReportedUUID, ReporterUUID, Reason, ReportDate, " +
                            "ReporterServer, ReporterLocation, ReportedServer, ReportedLocation, " +
                            "ReportedGamemode, ReporterGamemode, ReportedHealth, ReporterHealth, Process) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                for (Report report : reports) {
                    addReportToBatch(reportStmt, report);
                }
                reportStmt.executeBatch();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                handleSQLException(rollbackEx);
            }
            handleSQLException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                handleSQLException(autoCommitEx);
            }
        }
    }

    public void addArchivedReportListSQLite(List<Report> reports) {
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement playerStmt = connection.prepareStatement(
                    "INSERT OR IGNORE INTO PlayersTable (UUID, Nick, IP, IsOnline, " +
                            "LastServer, LastLocation, LastConnection) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                for (Report report : reports) {
                    addPlayerToBatch(playerStmt, report.getReportedPlayer());
                    addPlayerToBatch(playerStmt, report.getReporterPlayer());
                }
                playerStmt.executeBatch();
            }

            try (PreparedStatement reportStmt = connection.prepareStatement(
                    "INSERT INTO ArchivedReportsTable (ReportedUUID, ReporterUUID, Reason, ReportDate, " +
                            "ReporterServer, ReporterLocation, ReportedServer, ReportedLocation, " +
                            "ReportedGamemode, ReporterGamemode, ReportedHealth, ReporterHealth, Process) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                for (Report report : reports) {
                    addReportToBatch(reportStmt, report);
                }
                reportStmt.executeBatch();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                handleSQLException(rollbackEx);
            }
            handleSQLException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                handleSQLException(autoCommitEx);
            }
        }
    }

    public void addCommentListSQLite(List<Comment> comments) {
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement reportStmt = connection.prepareStatement(
                    "INSERT INTO CommentsTable (ReportedUUID, ReportedName, StaffUUID, StaffName, " +
                            "CommentTxt, CommentDate) VALUES (?, ?, ?, ?, ?, ?)")) {

                for (Comment comment : comments) {
                    addCommentToBatch(reportStmt, comment);
                }
                reportStmt.executeBatch();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                handleSQLException(rollbackEx);
            }
            handleSQLException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                handleSQLException(autoCommitEx);
            }
        }
    }

    private void insertPlayer(PlayerOBJ playerOBJ) {
            String sql = "INSERT IGNORE INTO PlayersTable (UUID, Nick, IP, IsOnline) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, playerOBJ.getUuid());
                preparedStatement.setString(2, playerOBJ.getName());
                preparedStatement.setString(3, playerOBJ.getIp());
                preparedStatement.setBoolean(4, true);
                preparedStatement.executeUpdate();
            } catch (Exception ignored) {}
    }

    private int insertReport(Report report) {
        String sql = "INSERT INTO ReportsTable (ReportedUUID, ReporterUUID, Reason, ReportDate, " +
                "ReporterServer, ReporterLocation, ReportedServer, ReportedLocation, " +
                "ReportedGamemode, ReporterGamemode, ReportedHealth, ReporterHealth, Process) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, report.getReportedUUID());
            preparedStatement.setString(2, report.getReporterUUID());
            preparedStatement.setString(3, report.getReason());
            preparedStatement.setString(4, report.getDate());
            preparedStatement.setString(5, report.getReporterServer());
            preparedStatement.setString(6, report.getReporterLocation());
            preparedStatement.setString(7, report.getReportedServer());
            preparedStatement.setString(8, report.getReportedLocation());
            preparedStatement.setString(9, report.getReportedGamemode());
            preparedStatement.setString(10, report.getReporterGamemode());
            preparedStatement.setInt(11, report.getReportedHealth());
            preparedStatement.setInt(12, report.getReporterHealth());
            preparedStatement.setString(13, report.getProcess().toString());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
            return -1;
        }
    }

    private void addPlayerToBatch(PreparedStatement stmt, PlayerOBJ playerOBJ) throws SQLException {
        stmt.setString(1, playerOBJ.getUuid());
        stmt.setString(2, playerOBJ.getName());
        stmt.setString(3, playerOBJ.getIp());
        stmt.setBoolean(4, false);
        stmt.setString(5, playerOBJ.getLastserver());
        stmt.setString(6, playerOBJ.getLastLocation());
        stmt.setString(7, playerOBJ.getLastConnection());
        stmt.addBatch();
    }

    private void addReportToBatch(PreparedStatement stmt, Report report) throws SQLException {
        stmt.setString(1, report.getReportedUUID());
        stmt.setString(2, report.getReporterUUID());
        stmt.setString(3, report.getReason());
        stmt.setString(4, report.getDate());
        stmt.setString(5, report.getReporterServer());
        stmt.setString(6, report.getReporterLocation());
        stmt.setString(7, report.getReportedServer());
        stmt.setString(8, report.getReportedLocation());
        stmt.setString(9, report.getReportedGamemode());
        stmt.setString(10, report.getReporterGamemode());
        stmt.setInt(11, report.getReportedHealth());
        stmt.setInt(12, report.getReporterHealth());
        stmt.setString(13, report.getProcess().toString());
        stmt.addBatch();
    }

    private void addCommentToBatch(PreparedStatement stmt, Comment comment) throws SQLException {
        stmt.setString(1, comment.getReportedUUID());
        stmt.setString(2, comment.getReportedName());
        stmt.setString(3, comment.getStaffUUID());
        stmt.setString(4, comment.getStaffName());
        stmt.setString(5, comment.getComment());
        stmt.setString(6, comment.getDate());
        stmt.addBatch();
    }

    private void handleSQLException(SQLException e) {System.out.print("[EpicReports] Error with EpicReports: " + e.getMessage());
        e.printStackTrace();}
}
