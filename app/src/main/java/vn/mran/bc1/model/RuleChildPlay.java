package vn.mran.bc1.model;

/**
 * Created by Mr An on 20/12/2017.
 */

public class RuleChildPlay {
    public long additionalNumber;
    public String assignNumber;
    public long quantum;
    public long rule;
    public String status;

    public RuleChildPlay() {
    }

    public RuleChildPlay(long additionalNumber, String assignNumber, long quantum, long rule, String status) {
        this.additionalNumber = additionalNumber;
        this.assignNumber = assignNumber;
        this.quantum = quantum;
        this.rule = rule;
        this.status = status;
    }

    public int getAdditionalNumber() {
        return (int) additionalNumber;
    }

    public int getQuantum() {
        return (int) quantum;
    }

    public int getRule() {
        return (int) rule;
    }

    public int[] getAssignNumberArray() {
        String[] assignNumberArray = assignNumber.split(" ");
        return new int[]{Integer.parseInt(assignNumberArray[0]),
                Integer.parseInt(assignNumberArray[1]),
                Integer.parseInt(assignNumberArray[2]),
                Integer.parseInt(assignNumberArray[3]),
                Integer.parseInt(assignNumberArray[4]),
                Integer.parseInt(assignNumberArray[5])};
    }

    public int getAnimal(int value) {
        String[] assignNumberArray = assignNumber.split(" ");
        for (int i = 0; i < assignNumberArray.length; i++) {
            if (Integer.parseInt(assignNumberArray[i]) == value) {
                return i;
            }
        }
        return value;
    }
}
