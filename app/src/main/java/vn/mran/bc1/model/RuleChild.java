package vn.mran.bc1.model;

/**
 * Created by Mr An on 20/12/2017.
 */

public class RuleChild {
    public long additionalNumber;
    public String assignNumber;
    public long quantum;
    public long rule;

    public RuleChild() {
    }

    public RuleChild(long additionalNumber, String assignNumber, long quantum, long rule) {
        this.additionalNumber = additionalNumber;
        this.assignNumber = assignNumber;
        this.quantum = quantum;
        this.rule = rule;
    }
}
