package teik.ers.bukkit.managers.reportmg.helpers;

import teik.ers.global.utils.querys.QuerysUT;
import teik.ers.global.models.objects.Report;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportsListMG {
    private final Connection connection;

    private final QuerysUT querysUtils;
    private final PlayersListMG playersListMG;

    public ReportsListMG(QuerysUT querysUtils, PlayersListMG playersListMG, Connection connection) {
        this.querysUtils = querysUtils;
        this.playersListMG = playersListMG;

        this.connection = connection;
        updateAll();
    }

    private List<Report> allReports = new ArrayList<>();
    private final List<Report> archivedReports = new ArrayList<>();
    private final HashMap<String, List<Report>> perReportedReport = new HashMap<>();
    private final HashMap<String, List<Report>> perReporterReport = new HashMap<>();
    private final HashMap<Integer, Report> perIdReport = new HashMap<>();

    public void updateAll(){
        playersListMG.updatePlayerOBJList();
        updateReportsList();
        updateArchivedReports();
        populateHashMaps();
    }

    private void updateReportsList(){
        String query = "SELECT * FROM ReportsTable";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            try (ResultSet resultSet = statement.executeQuery()) {
                perIdReport.clear();
                while (resultSet.next()) {
                    String reason = resultSet.getString("Reason");
                    String date = resultSet.getString("ReportDate");
                    Report report = new Report(reason, date);
                    querysUtils.populateReportData(report, resultSet);
                    report.setReportedPlayer(playersListMG.getPlayerOBJ(report.getReportedUUID()));
                    report.setReporterPlayer(playersListMG.getPlayerOBJ(report.getReporterUUID()));
                    perIdReport.put(report.getReportID(), report);
                }
            }
        }
        catch(SQLException e){
            handleSQLException(e);
        }
    }

    private void updateArchivedReports(){
        String query = "SELECT * FROM ArchivedReportsTable";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            try (ResultSet resultSet = statement.executeQuery()) {
                archivedReports.clear();
                while (resultSet.next()) {
                    String reason = resultSet.getString("Reason");
                    String date = resultSet.getString("ReportDate");
                    Report report = new Report(reason, date);
                    querysUtils.populateReportData(report, resultSet);
                    report.setReportedPlayer(playersListMG.getPlayerOBJ(report.getReportedUUID()));
                    report.setReporterPlayer(playersListMG.getPlayerOBJ(report.getReporterUUID()));
                    archivedReports.add(report);
                }
            }
        }
        catch(SQLException e){
            handleSQLException(e);
        }
    }

    public void populateHashMaps(){
        perReportedReport.clear();
        perReporterReport.clear();
        allReports.clear();
        allReports = new ArrayList<>(perIdReport.values());

        for (Report report : allReports) {
            String reportedUUID = report.getReportedUUID();
            perReportedReport.computeIfAbsent(reportedUUID, k -> new ArrayList<>()).add(report);

            String reporterUUID = report.getReporterUUID();
            perReporterReport.computeIfAbsent(reporterUUID, k -> new ArrayList<>()).add(report);
        }
    }

    //Contains

    public boolean reportedExist(String uuid){
        return perReportedReport.containsKey(uuid);
    }

    public boolean reporterExist(String uuid){
        return perReporterReport.containsKey(uuid);
    }

    public boolean idReportExist(int id){
        return perIdReport.containsKey(id);
    }

    //Getters

    public List<Report> getAllReports() {
        return allReports;
    }

    public List<Report> getArchivedReports() {
        return archivedReports;
    }

    public HashMap<String, List<Report>> getPerReportedReport() {
        return perReportedReport;
    }

    public HashMap<String, List<Report>> getPerReporterReport() {
        return perReporterReport;
    }

    public HashMap<Integer, Report> getPerIdReport() {
        return perIdReport;
    }

    private void handleSQLException(SQLException e) {System.out.print("[EpicReports] Error with EpicReports: " + e.getMessage());
        e.printStackTrace();}
}
