package vn.mran.bc1.model;

/**
 * Created by Mr An on 20/12/2017.
 */

public class RuleMainPlay {
    public long quantum;
    public String status;

    public RuleMainPlay() {
    }

    public RuleMainPlay(long quantum, String status) {
        this.quantum = quantum;
        this.status = status;
    }

    public int getQuantum() {
        return (int) quantum;
    }
}
