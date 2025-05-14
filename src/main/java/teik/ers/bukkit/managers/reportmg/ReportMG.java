package teik.ers.bukkit.managers.reportmg;

import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.managers.inventories.helpers.InvHelperMG;
import teik.ers.bukkit.managers.reportmg.helpers.CommentsListMG;
import teik.ers.bukkit.managers.reportmg.helpers.PlayersListMG;
import teik.ers.bukkit.managers.reportmg.helpers.ReportsListMG;
import teik.ers.bukkit.utilities.models.Comment;
import teik.ers.global.utils.querys.AddQuerysUT;
import teik.ers.global.utils.querys.QuerysUT;
import teik.ers.global.models.Process;
import teik.ers.global.models.objects.Report;

import java.sql.Connection;

public class ReportMG {
    private final boolean isMysql;

    private final QuerysUT querysUtils;
    private final AddQuerysUT addQuerysUT;

    private final PlayersListMG playersListMG;
    private final ReportsListMG reportsListMG;
    private final CommentsListMG commentsListMG;

    private final LocalReportMG localReportMG;
    private final SqlReportMG sqlReportMG;

    public ReportMG(EpicReports plugin) {
        this.isMysql = plugin.configManager.isMYSQLActive();

        Connection connection = plugin.getConnection();

        querysUtils = plugin.querysUT;
        addQuerysUT = new AddQuerysUT(connection);

        playersListMG = new PlayersListMG(connection);
        reportsListMG = new ReportsListMG(querysUtils, playersListMG, connection);
        commentsListMG = new CommentsListMG(querysUtils, connection);

        localReportMG = new LocalReportMG(querysUtils, addQuerysUT, reportsListMG, playersListMG, commentsListMG);
        sqlReportMG = new SqlReportMG(querysUtils);
    }

    //Update
    public void updateAllMg(){
        reportsListMG.updateAll();
        commentsListMG.updateCommentMap();
    }

    //Modify Reports

    public void removeReport(int id){
        if(isMysql){
            querysUtils.deleteReportById(id);
            return;
        }
        localReportMG.removeReport(id);
    }

    public void removeAllReports(String uuid){
        if(isMysql){
            querysUtils.deleteAllReportsPerReported(uuid);
            return;
        }
        localReportMG.removeAllReports(uuid);
    }

    public void archiveReport(int id){
        if(isMysql){
            sqlReportMG.archiveReport(id);
            return;
        }
        localReportMG.archiveReport(id);
    }

    public void removeArchivedReport(Report report){
        if(isMysql){
            querysUtils.deleteArchivedReport(report.getReportID());
            return;
        }
        localReportMG.removeArchiveReport(report);
    }

    public void updateReportProcess(int id, Process process){
        if(isMysql){
            querysUtils.updateReportProcess(id, process.toString());

            return;
        }
        localReportMG.updateReportProcess(id, process);
    }

    public void addComment(Comment comment){
        if(isMysql){
            querysUtils.addComment(comment);
            return;
        }
        localReportMG.addComment(comment);
    }

    public void removeComment(int id){
        if(isMysql){
            querysUtils.deleteComment(id);
            return;
        }
        localReportMG.removeComment(id);
    }

    //Getters

    public PlayersListMG getPlayersListMG() {
        return playersListMG;
    }

    public ReportsListMG getReportsListMG() {
        return reportsListMG;
    }

    public CommentsListMG getCommentsListMG() {
        return commentsListMG;
    }

    //Verify

    public boolean reportedExist(String uuid){
        return reportsListMG.reportedExist(uuid);
    }

    public boolean reporterExist(String uuid){
        return reportsListMG.reporterExist(uuid);
    }

    public boolean idReportExist(int id){
        return reportsListMG.idReportExist(id);
    }

    //Local

    public void addReport(Report report){
        localReportMG.addReport(report);
        playersListMG.addPlayerOBJ(report.getReportedPlayer());
        playersListMG.addPlayerOBJ(report.getReporterPlayer());
    }

    public void onDisable(){
        if(isMysql) return;
        localReportMG.saveReports();
    }
}
