package teik.ers.bukkit.managers.inventories.helpers;

import teik.ers.bukkit.managers.reportmg.helpers.PlayersListMG;
import teik.ers.bukkit.managers.reportmg.helpers.ReportsListMG;
import teik.ers.bukkit.utilities.UtilitiesPlayers;
import teik.ers.bukkit.utilities.models.enums.FilterType;
import teik.ers.global.models.objects.Report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvHelperMG {
    private final PlayersListMG playersListMG;
    private final ReportsListMG reportsListMG;
    private final FilterHelperMG filterHelperMG;

    public InvHelperMG(PlayersListMG playersListMG, ReportsListMG reportsListMG, UtilitiesPlayers utilitiesPlayers, Boolean isMysql) {
        this.playersListMG = playersListMG;
        this.reportsListMG = reportsListMG;
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
}
