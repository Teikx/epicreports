package teik.ers.bukkit.managers.inventories.helpers;

import teik.ers.bukkit.managers.reportmg.helpers.PlayersListMG;
import teik.ers.bukkit.utilities.UtilitiesPlayers;
import teik.ers.bukkit.utilities.models.Comment;
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

    public List<Report> filterReportList(List<Report> reports, FilterType filterType, String name){
        Set<String> uuidSet;
        switch (filterType) {
            case Waiting:
                return reports.stream()
                        .filter(report -> Process.Waiting.equals(report.getProcess()))
                        .collect(Collectors.toList());
            case Processing:
                return reports.stream()
                        .filter(report -> Process.Processing.equals(report.getProcess()))
                        .collect(Collectors.toList());
            case Server:
                return reports.stream()
                        .filter(report -> name.equalsIgnoreCase(report.getReporterServer()))
                        .collect(Collectors.toList());
            case ReportedName:
                return reports.stream()
                        .filter(report -> name.equalsIgnoreCase(report.getReportedName()))
                        .collect(Collectors.toList());
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

    public List<Comment> filterCommentList(List<Comment> comments, FilterType filterType, String name){
        Set<String> uuidSet;
        switch (filterType){
            case ReportedName:
                return comments.stream()
                        .filter(comment -> name.equalsIgnoreCase(comment.getReportedName()))
                        .collect(Collectors.toList());
            case ReportedOnline:
                uuidSet = setOnlineSet();
                return comments.stream()
                        .filter(comment -> uuidSet.contains(comment.getReportedUUID()))
                        .collect(Collectors.toList());
            case ReportedOffline:
                uuidSet = setOfflineSet();
                return comments.stream()
                        .filter(comment -> uuidSet.contains(comment.getReportedUUID()))
                        .collect(Collectors.toList());
            default:
                return comments;
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
