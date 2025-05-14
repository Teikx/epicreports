package teik.ers.bukkit.managers.inventories.helpers;

import teik.ers.bukkit.managers.reportmg.helpers.CommentsListMG;
import teik.ers.bukkit.managers.reportmg.helpers.PlayersListMG;
import teik.ers.bukkit.managers.reportmg.helpers.ReportsListMG;
import teik.ers.bukkit.utilities.UtilitiesPlayers;
import teik.ers.bukkit.utilities.models.Comment;
import teik.ers.bukkit.utilities.models.enums.FilterType;
import teik.ers.global.models.objects.Report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvHelperMG {
    private final PlayersListMG playersListMG;
    private final ReportsListMG reportsListMG;
    private final CommentsListMG commentsListMG;

    private final FilterHelperMG filterHelperMG;

    public InvHelperMG(PlayersListMG playersListMG, ReportsListMG reportsListMG, CommentsListMG commentsListMG,
                       UtilitiesPlayers utilitiesPlayers, Boolean isMysql) {
        this.playersListMG = playersListMG;
        this.reportsListMG = reportsListMG;
        this.commentsListMG = commentsListMG;
        filterHelperMG = new FilterHelperMG(playersListMG, utilitiesPlayers, isMysql);
    }

    //ReportedsMenu
    public List<Report> getAllReportedsList(FilterType filterType, String serverName){
        HashMap<String, List<Report>> reportsMap = reportsListMG.getPerReportedReport();
        List<Report> reportList = new ArrayList<>();

        for (Map.Entry<String, List<Report>> entry : reportsMap.entrySet()) {
            List<Report> reports = entry.getValue();

            if(reports.isEmpty()) return  reportList;

            int reportsSize = reports.size();

            Report latestReport = reports.get(reportsSize-1);
            latestReport.setReportsSize(reportsSize);
            reportList.add(latestReport);
        }

        return filterHelperMG.filterReportList(reportList, filterType, serverName);
    }

    //ReportsMenu
    public List<Report> getReportedReportsList(String reportedUUID, FilterType filterType, String serverName){
        List<Report> reportedReportsList = new ArrayList<>(reportsListMG.getPerReportedReport().get(reportedUUID));

        return filterHelperMG.filterReportList(reportedReportsList, filterType, serverName);
    }

    //InfoReportsMenu
    public Report getReportPerId(int id){
        return reportsListMG.getPerIdReport().get(id);
    }

    //ArchivedsMenu
    public List<Report> getArchivedsReportsList(FilterType filterType, String name){
        List<Report> archivedReports = new ArrayList<>(reportsListMG.getArchivedReports());

        return filterHelperMG.filterReportList(archivedReports, filterType, name);
    }

    //CommentsMenu
    public List<Comment> getCommentsList(FilterType filterType, String name){
        List<Comment> commentList = new ArrayList<>(commentsListMG.getCommentList());

        return filterHelperMG.filterCommentList(commentList, filterType, name);
    }

    //AllReportsMenu
    public List<Report> getAllReportList(FilterType filterType, String serverName){
        List<Report> allReports = new ArrayList<>(reportsListMG.getAllReports());

        return filterHelperMG.filterReportList(allReports, filterType, serverName);
    }

    //ReportersMenu
    public List<Report> getAllReportersList(FilterType filterType, String serverName){
        HashMap<String, List<Report>> reportsMap = reportsListMG.getPerReporterReport();
        List<Report> reportList = new ArrayList<>();

        for (Map.Entry<String, List<Report>> entry : reportsMap.entrySet()) {
            List<Report> reports = entry.getValue();

            if(reports.isEmpty()) return  reportList;

            int reportsSize = reports.size();

            Report latestReport = reports.get(reportsSize-1);
            latestReport.setReportsSize(reportsSize);
            reportList.add(latestReport);
        }

        return filterHelperMG.filterReportList(reportList, filterType, serverName);
    }

    //ReporterReportsMenu
    public List<Report> getReporterReportsList(String reporterUUID, FilterType filterType, String serverName){
        List<Report> reporterReportsList = new ArrayList<>(reportsListMG.getPerReporterReport().get(reporterUUID));

        return filterHelperMG.filterReportList(reporterReportsList, filterType, serverName);
    }
}
