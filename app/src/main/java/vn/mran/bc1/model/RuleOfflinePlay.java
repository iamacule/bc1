package vn.mran.bc1.model;

/**
 * Created by Mr An on 20/12/2017.
 */

public class RuleOfflinePlay {
    public long additionalNumber;
    public String assignNumber;
    public long quantum;
    public String status;

    public RuleOfflinePlay() {
    }

    public RuleOfflinePlay(long additionalNumber, String assignNumber, long quantum, String status) {
        this.additionalNumber = additionalNumber;
        this.assignNumber = assignNumber;
        this.quantum = quantum;
        this.status = status;
    }
}
