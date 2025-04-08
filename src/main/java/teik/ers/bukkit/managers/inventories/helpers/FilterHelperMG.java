package teik.ers.bukkit.managers.inventories.helpers;

import teik.ers.bukkit.managers.reportmg.helpers.PlayersListMG;
import teik.ers.bukkit.utilities.UtilitiesPlayers;
import teik.ers.bukkit.utilities.models.enums.FilterType;
import teik.ers.global.models.objects.Report;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import teik.ers.global.models.Process;

public class FilterHelperMG {

    private final PlayersListMG playersListMG;
    private final UtilitiesPlayers utilitiesPlayers;
    private final boolean isMysql;

    public FilterHelperMG(PlayersListMG playersListMG, UtilitiesPlayers utilitiesPlayers, boolean isMysql) {
        this.playersListMG = playersListMG;
        this.utilitiesPlayers = utilitiesPlayers;
        this.isMysql = isMysql;
    }

    public List<Report> filterReportList(List<Report> reports, FilterType filterType, String serverName){
        Set<String> uuidSet;
        List<Report> reportFiltered;
        switch (filterType) {
            case Waiting:
                reportFiltered = reports.stream().filter(report -> Process.Waiting.equals(report.getProcess())).collect(Collectors.toList());
                return reportFiltered;
            case Processing:
                return reports.stream().filter(report -> Process.Processing.equals(report.getProcess())).collect(Collectors.toList());
            case Server:
                return reports.stream().filter(report -> serverName.equalsIgnoreCase(report.getReporterServer())).collect(Collectors.toList());
            case ReportedOnline:
                uuidSet = setOnlineSet();
                return reports.stream()
                        .filter(report -> uuidSet.contains(report.getReportedUUID()))
                        .collect(Collectors.toList());
            case ReportedOffline:
                uuidSet = setOfflineSet();
                return reports.stream()
                        .filter(report -> uuidSet.contains(report.getReportedUUID()))
                        .collect(Collectors.toList());
            case ReporterOnline:
                uuidSet = setOnlineSet();
                return reports.stream()
                        .filter(report -> uuidSet.contains(report.getReporterUUID()))
                        .collect(Collectors.toList());
            case ReporterOffline:
                uuidSet = setOfflineSet();
                return reports.stream()
                        .filter(report -> uuidSet.contains(report.getReporterUUID()))
                        .collect(Collectors.toList());
            default:
                return reports;
        }
    }

    private Set<String> setOnlineSet() {
        Set<String> uuidSet;
        if(isMysql){    uuidSet = new HashSet<>(utilitiesPlayers.getPlayersOnlineMysql()); }
        else{   uuidSet = new HashSet<>(playersListMG.getOnlinePlayers()); }
        return uuidSet;
    }

    private Set<String> setOfflineSet() {
        Set<String> uuidSet;
        if(isMysql){    uuidSet = new HashSet<>(utilitiesPlayers.getPlayersOfflineMysql()); }
        else{   uuidSet = new HashSet<>(playersListMG.getOfflinePlayers()); }
        return uuidSet;
    }
}
