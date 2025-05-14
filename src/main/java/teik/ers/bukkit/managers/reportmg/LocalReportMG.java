package teik.ers.bukkit.managers.reportmg;

import teik.ers.bukkit.managers.reportmg.helpers.CommentsListMG;
import teik.ers.bukkit.managers.reportmg.helpers.PlayersListMG;
import teik.ers.bukkit.managers.reportmg.helpers.ReportsListMG;
import teik.ers.bukkit.utilities.models.Comment;
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
    private final CommentsListMG commentsListMG;

    private final List<Report> allReports;
    private final List<Report> archivedReports;
    private final HashMap<String, List<Report>> perReportedReport;
    private final HashMap<String, List<Report>> perReporterReport;
    private final HashMap<Integer, Report> perIdReport;

    private final HashMap<Integer, Comment> commentMap;

    public LocalReportMG(QuerysUT querysUtils, AddQuerysUT addQuerysUT , ReportsListMG reportsListMG,
                         PlayersListMG playersListMG, CommentsListMG commentsListMG) {
        this.querysUtils = querysUtils;
        this.addQuerysUT = addQuerysUT;
        this.reportsListMG = reportsListMG;
        this.playersListMG = playersListMG;
        this.commentsListMG = commentsListMG;

        allReports = reportsListMG.getAllReports();
        archivedReports = reportsListMG.getArchivedReports();
        perReportedReport = reportsListMG.getPerReportedReport();
        perReporterReport = reportsListMG.getPerReporterReport();
        perIdReport = reportsListMG.getPerIdReport();

        commentMap = commentsListMG.getCommentMap();
    }

    //Modify Reports

    public void addReport(Report report){
        int reportID = getLastReportID();
        report.setReportID(reportID);
        perIdReport.put(reportID, report);
        allReports.add(report);
        perReportedReport.computeIfAbsent(report.getReportedUUID(), k -> new ArrayList<>()).add(report);
        perReporterReport.computeIfAbsent(report.getReporterUUID(), k -> new ArrayList<>()).add(report);

    }

    public Report removeReport(int id){
        Report reportHelper = perIdReport.get(id);
        perIdReport.remove(id);
        reportsListMG.populateHashMaps();
        return reportHelper;
    }

    public void removeAllReports(String uuid){
        perIdReport.entrySet().removeIf(entry -> entry.getValue().getReportedUUID().equals(uuid));
        reportsListMG.populateHashMaps();
    }

    public void archiveReport(int id){
        Report report =  removeReport(id);
        archivedReports.add(report);
    }

    public void removeArchiveReport(Report report){
        archivedReports.remove(report);
    }

    public void updateReportProcess(int id, Process process){
        Report report = perIdReport.get(id);
        report.setProcess(process);
        perIdReport.put(id, report);
        reportsListMG.populateHashMaps();
    }

    public void addComment(Comment comment){
        comment.setId(getLastCommentID());
        commentMap.put(comment.getId(), comment);
    }

    public void removeComment(int id){
        commentMap.remove(id);
    }

    //Save

    public void saveReports(){
        querysUtils.clearTables();
        addQuerysUT.addPlayersSqLite(playersListMG.getPlayersList());
        addQuerysUT.addReportListSQLite(allReports);
        addQuerysUT.addArchivedReportListSQLite(archivedReports);
        addQuerysUT.addCommentListSQLite(commentsListMG.getCommentList());
    }

    //Gets

    private int getLastReportID(){
        Map.Entry<Integer, Report> maxEntry = perIdReport.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue(Comparator.comparingInt(Report::getReportID)))
                .orElse(null);

        if (maxEntry != null) return maxEntry.getKey()+1;

        return 1;
    }

    private int getLastCommentID(){
        Map.Entry<Integer, Comment> maxEntry = commentMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue(Comparator.comparingInt(Comment::getId)))
                .orElse(null);

        if (maxEntry != null) return maxEntry.getKey()+1;

        return 1;
    }
}
