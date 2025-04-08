package teik.ers.bukkit.managers.reportmg;

import teik.ers.global.utils.querys.QuerysUT;
import teik.ers.global.models.Process;

public class SqlReportMG {
    private final QuerysUT querysUtils;

    public SqlReportMG(QuerysUT querysUtils) {
        this.querysUtils = querysUtils;
    }

    //Modify Reports

    public void archiveReport(int id){
        querysUtils.updateReportProcess(id, Process.Archived.toString());
        querysUtils.deleteArchivedReports();
    }

}
