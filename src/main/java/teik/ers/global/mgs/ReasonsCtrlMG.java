package teik.ers.global.mgs;

import java.util.List;

public class ReasonsCtrlMG {
    private final int minimumLength;
    private final List<String> blacklist;

    public ReasonsCtrlMG(int minimumLength, List<String> blacklist) {
        this.minimumLength = minimumLength;
        this.blacklist = blacklist;
    }

    public boolean isReasonValid(String reason){
        if(isReasonTooShort(reason)) return false;
        if(isReasonBlacklisted(reason)) return false;
        return true;
    }

    private boolean isReasonTooShort(String reason){
        return reason.length() < minimumLength;
    }

    private boolean isReasonBlacklisted(String reason){
        return blacklist.contains(reason);
    }

}
