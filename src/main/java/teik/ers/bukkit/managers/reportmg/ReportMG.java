package teik.ers.bukkit.managers.reportmg;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import teik.ers.bukkit.EpicReports;
import teik.ers.bukkit.managers.inventories.helpers.InvHelperMG;
import teik.ers.bukkit.managers.reportmg.helpers.PlayersListMG;
import teik.ers.bukkit.managers.reportmg.helpers.ReportsListMG;
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
    private final InvHelperMG invHelperMG;

    private final LocalReportMG localReportMG;
    private final SqlReportMG sqlReportMG;

    public ReportMG(EpicReports plugin) {
        this.isMysql = plugin.configManager.isMYSQLActive();

        Connection connection = plugin.getConnection();

        querysUtils = plugin.querysUT;
        addQuerysUT = new AddQuerysUT(connection);

        playersListMG = new PlayersListMG(connection);
        reportsListMG = new ReportsListMG(querysUtils, playersListMG, connection);
        invHelperMG = new InvHelperMG(playersListMG, reportsListMG, plugin.utilitiesPlayers, plugin.configManager.isMYSQLActive());

        localReportMG = new LocalReportMG(querysUtils, addQuerysUT, reportsListMG, playersListMG);
        sqlReportMG = new SqlReportMG(querysUtils);
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

    public void removeArchivedReport(int id){
        if(isMysql){
            querysUtils.deleteArchivedReport(id);
            return;
        }
        localReportMG.removeArchiveReport(id);
    }

    public void updateReportProcess(int id, Process process){
        if(isMysql){
            querysUtils.updateReportProcess(id, process.toString());
            return;
        }
        localReportMG.updateReportProcess(id, process);
    }

    //Getters

    public PlayersListMG getPlayersListMG() {
        return playersListMG;
    }

    public ReportsListMG getReportsListMG() {
        return reportsListMG;
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
