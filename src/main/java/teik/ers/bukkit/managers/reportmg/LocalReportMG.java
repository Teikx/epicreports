package teik.ers.bukkit.managers.reportmg;

import teik.ers.bukkit.managers.reportmg.helpers.PlayersListMG;
import teik.ers.bukkit.managers.reportmg.helpers.ReportsListMG;
import teik.ers.global.utils.querys.AddQuerysUT;
import teik.ers.global.utils.querys.QuerysUT;
import teik.ers.global.models.Process;
import teik.ers.global.models.objects.Report;

import java.util.*;

public class LocalReportMG {
    private final QuerysUT querysUtils;
    private final AddQuerysUT addQuerysUT;
    private final PlayersListMG playersListMG;
    private final ReportsListMG reportsListMG;

    private final List<Report> allReports;
    private final List<Report> archivedReports;
    private final HashMap<String, List<Report>> perReportedReport;
    private final HashMap<String, List<Report>> perReporterReport;
    private final HashMap<Integer, Report> perIdReport;

    public LocalReportMG(QuerysUT querysUtils, AddQuerysUT addQuerysUT , ReportsListMG reportsListMG,
                         PlayersListMG playersListMG) {
        this.querysUtils = querysUtils;
        this.addQuerysUT = addQuerysUT;
        this.reportsListMG = reportsListMG;
        this.playersListMG = playersListMG;

        allReports = reportsListMG.getAllReports();
        archivedReports = reportsListMG.getArchivedReports();
        perReportedReport = reportsListMG.getPerReportedReport();
        perReporterReport = reportsListMG.getPerReporterReport();
        perIdReport = reportsListMG.getPerIdReport();
    }

    //Modify Reports

    public void addReport(Report report){
        report.setReportID(getLastID());
        allReports.add(report);
        perReportedReport.computeIfAbsent(report.getReportedUUID(), k -> new ArrayList<>()).add(report);
        perReporterReport.computeIfAbsent(report.getReporterUUID(), k -> new ArrayList<>()).add(report);
        perIdReport.put(report.getReportID(), report);
    }

    public Report removeReport(int id){
        allReports.removeIf(report -> report.getReportID() == id);

        Report reportHelper = perIdReport.get(id);
        String reportedUUID = reportHelper.getReportedUUID();
        String reporterUUID = reportHelper.getReporterUUID();

        perReportedReport.get(reportedUUID).removeIf(report -> report.getReportID() == id);
        perReporterReport.get(reporterUUID).removeIf(report -> report.getReportID() == id);

        perIdReport.remove(id);
        return reportHelper;
    }

    public void removeAllReports(String uuid){
        allReports.removeIf(report -> report.getReportedUUID().equals(uuid));
        reportsListMG.populateHashMaps();
    }

    public void archiveReport(int id){
        Report report =  removeReport(id);
        archivedReports.add(report);
    }

    public void removeArchiveReport(int id){
        archivedReports.removeIf(report -> report.getReportID() == id);
    }

    public void updateReportProcess(int id, Process process){
        Report report = perIdReport.get(id);
        allReports.remove(report);
        report.setProcess(process);
        allReports.add(report);
        reportsListMG.populateHashMaps();
    }

    //Save

    public void saveReports(){
        querysUtils.clearTables();
        addQuerysUT.addPlayersSqLite(playersListMG.getPlayersList());
        addQuerysUT.addReportListSQLite(allReports);
        addQuerysUT.addArchivedReportListSQLite(archivedReports);
    }

    //Gets

    private int getLastID(){
        Map.Entry<Integer, Report> maxEntry = perIdReport.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue(Comparator.comparingInt(Report::getReportID)))
                .orElse(null);

        if (maxEntry != null) return maxEntry.getKey()+1;

        return 1;
    }
}
