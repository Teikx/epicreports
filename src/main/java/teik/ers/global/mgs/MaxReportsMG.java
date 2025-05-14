package teik.ers.global.mgs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MaxReportsMG {

    private final int maxReports;

    public MaxReportsMG(int maxReports) {
        this.maxReports = maxReports;
    }

    //Count reports per player
    private final HashMap<String, List<String>> playerReportsCount = new HashMap<>();

    public void addNewReportPlayer(String reporterUUID, String reportedName){
        //Añade una nueva lista en caso no haya y añade el nuevo reported a la lista
        playerReportsCount.putIfAbsent(reporterUUID, new ArrayList<>());
        playerReportsCount.get(reporterUUID).add(reportedName);
    }

    public boolean isMaxReport(String reporterUUID, String reportedName){
        //Verifica si en la lista de reporteds hay algún UUID que se repite más del límite
        if(!playerReportsCount.containsKey(reporterUUID)) return false;
        List<String> reportedsList = playerReportsCount.get(reporterUUID);
        return Collections.frequency(reportedsList, reportedName) >= maxReports;
    }
}
