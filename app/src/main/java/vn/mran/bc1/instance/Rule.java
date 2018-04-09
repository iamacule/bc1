package vn.mran.bc1.instance;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.lang3.ArrayUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import vn.mran.bc1.constant.PrefValue;
import vn.mran.bc1.helper.Log;
import vn.mran.bc1.model.RuleChild;
import vn.mran.bc1.model.RuleChildPlay;
import vn.mran.bc1.model.RuleMain;
import vn.mran.bc1.model.RuleMainPlay;
import vn.mran.bc1.model.RuleOffline;
import vn.mran.bc1.model.RuleOfflinePlay;
import vn.mran.bc1.util.Preferences;
import vn.mran.bc1.util.Task;

/**
 * Created by Mr An on 20/12/2017.
 */

public class Rule {

    private int[] ruleChildAssignNumArray;
    private int[] ruleChildPlayAssignNumArray;
    private int[] ruleOfflineAssignNumArray;
    private int[] ruleOfflinePlayAssignNumArray;

    public interface OnFireBaseDataBattleChanged {
        void onTextChanged(String TEXT);

        void onDataChanged();
    }

    public interface OnFireBaseDataPlayChanged {
        void onTextChanged(String TEXT);

        void onDataChanged();
    }

    private final String TAG = getClass().getSimpleName();

    public final String STATUS_ON = "on";

    private static Rule instance;

    private Preferences preferences;

    private int ruleChildAdditionalNumber;
    public int ruleChildAssignNum1;
    public int ruleChildAssignNum2;
    public int ruleChildAssignNum3;
    public int ruleChildAssignNum4;
    public int ruleChildAssignNum5;
    public int ruleChildAssignNum6;
    private int ruleChildQuantum;
    private int ruleChildRule;
    private String ruleChildStatus;

    private int ruleChildPlayAdditionalNumber;
    public int ruleChildPlayAssignNum1;
    public int ruleChildPlayAssignNum2;
    public int ruleChildPlayAssignNum3;
    public int ruleChildPlayAssignNum4;
    public int ruleChildPlayAssignNum5;
    public int ruleChildPlayAssignNum6;
    private int ruleChildPlayQuantum;
    private int ruleChildPlayRule;
    private String ruleChildPlayStatus;

    private int ruleMainQuantum;
    private String ruleMainStatus;

    private int ruleMainPlayQuantum;
    private String ruleMainPlayStatus;

    private int ruleOfflineAdditionalNumber;
    public int ruleOfflineAssignNum1;
    public int ruleOfflineAssignNum2;
    public int ruleOfflineAssignNum3;
    public int ruleOfflineAssignNum4;
    public int ruleOfflineAssignNum5;
    public int ruleOfflineAssignNum6;
    private int ruleOfflineQuantum;
    private String ruleOfflineStatus;

    private int ruleOfflinePlayAdditionalNumber;
    public int ruleOfflinePlayAssignNum1;
    public int ruleOfflinePlayAssignNum2;
    public int ruleOfflinePlayAssignNum3;
    public int ruleOfflinePlayAssignNum4;
    public int ruleOfflinePlayAssignNum5;
    public int ruleOfflinePlayAssignNum6;
    public int ruleOfflinePlayQuantum;
    private String ruleOfflinePlayStatus;

    public final byte RULE_NORMAL = 0;
    public final byte RULE_OFFLINE = 1;
    public final byte RULE_MAIN = 2;
    private byte currentRule = RULE_NORMAL;
    private byte currentRulePlay = RULE_NORMAL;

    //Bau, cua
    public final int[] RULE_MAIN_GONE_1 = new int[]{0, 1};

    //Tom ca
    public final int[] RULE_MAIN_GONE_2 = new int[]{2, 3};

    //Ga, nai
    public final int[] RULE_MAIN_GONE_3 = new int[]{4, 5};

    private int[] ruleMainGoneArrays;
    private int[] ruleMainPlayGoneArrays;

    private int[] resultArrays = new int[]{};
    private int[] resultPlayArrays = new int[]{};

    private OnFireBaseDataBattleChanged onFireBaseDataBattleChanged;
    private OnFireBaseDataPlayChanged onFireBaseDataPlayChanged;

    private Rule(Context context) {
        preferences = new Preferences(context);
        initValue();
        initFirebase();
    }

    private void initValue() {
        ruleChildAdditionalNumber = preferences.getIntValue(PrefValue.RULE_CHILD_ADDITIONAL_NUMBER, PrefValue.DEFAULT_ADDITIONAL_NUMBER);
        ruleChildAssignNum1 = preferences.getIntValue(PrefValue.RULE_CHILD_NUM_1, PrefValue.DEFAULT_ASSIGN_NUM_1);
        ruleChildAssignNum2 = preferences.getIntValue(PrefValue.RULE_CHILD_NUM_2, PrefValue.DEFAULT_ASSIGN_NUM_2);
        ruleChildAssignNum3 = preferences.getIntValue(PrefValue.RULE_CHILD_NUM_3, PrefValue.DEFAULT_ASSIGN_NUM_3);
        ruleChildAssignNum4 = preferences.getIntValue(PrefValue.RULE_CHILD_NUM_4, PrefValue.DEFAULT_ASSIGN_NUM_4);
        ruleChildAssignNum5 = preferences.getIntValue(PrefValue.RULE_CHILD_NUM_5, PrefValue.DEFAULT_ASSIGN_NUM_5);
        ruleChildAssignNum6 = preferences.getIntValue(PrefValue.RULE_CHILD_NUM_6, PrefValue.DEFAULT_ASSIGN_NUM_6);
        ruleChildQuantum = preferences.getIntValue(PrefValue.RULE_CHILD_QUANTUM, PrefValue.DEFAULT_QUANTUM);
        ruleChildRule = preferences.getIntValue(PrefValue.RULE_CHILD_RULE, PrefValue.DEFAULT_RULE);
        ruleChildStatus = preferences.getStringValue(PrefValue.RULE_CHILD_STATUS, PrefValue.DEFAULT_STATUS);

        ruleChildPlayAdditionalNumber = preferences.getIntValue(PrefValue.RULE_CHILD_PLAY_ADDITIONAL_NUMBER, PrefValue.DEFAULT_ADDITIONAL_NUMBER);
        ruleChildPlayAssignNum1 = preferences.getIntValue(PrefValue.RULE_CHILD_PLAY_NUM_1, PrefValue.DEFAULT_ASSIGN_NUM_1);
        ruleChildPlayAssignNum2 = preferences.getIntValue(PrefValue.RULE_CHILD_PLAY_NUM_2, PrefValue.DEFAULT_ASSIGN_NUM_2);
        ruleChildPlayAssignNum3 = preferences.getIntValue(PrefValue.RULE_CHILD_PLAY_NUM_3, PrefValue.DEFAULT_ASSIGN_NUM_3);
        ruleChildPlayAssignNum4 = preferences.getIntValue(PrefValue.RULE_CHILD_PLAY_NUM_4, PrefValue.DEFAULT_ASSIGN_NUM_4);
        ruleChildPlayAssignNum5 = preferences.getIntValue(PrefValue.RULE_CHILD_PLAY_NUM_5, PrefValue.DEFAULT_ASSIGN_NUM_5);
        ruleChildPlayAssignNum6 = preferences.getIntValue(PrefValue.RULE_CHILD_PLAY_NUM_6, PrefValue.DEFAULT_ASSIGN_NUM_6);
        ruleChildPlayQuantum = preferences.getIntValue(PrefValue.RULE_CHILD_PLAY_QUANTUM, PrefValue.DEFAULT_QUANTUM);
        ruleChildPlayRule = preferences.getIntValue(PrefValue.RULE_CHILD_PLAY_RULE, PrefValue.DEFAULT_RULE);
        ruleChildPlayStatus = preferences.getStringValue(PrefValue.RULE_CHILD_PLAY_STATUS, PrefValue.DEFAULT_STATUS);

        ruleMainQuantum = preferences.getIntValue(PrefValue.RULE_MAIN_QUANTUM, PrefValue.DEFAULT_QUANTUM);
        ruleMainStatus = preferences.getStringValue(PrefValue.RULE_MAIN_STATUS, PrefValue.DEFAULT_STATUS);

        ruleMainPlayQuantum = preferences.getIntValue(PrefValue.RULE_MAIN_PLAY_QUANTUM, PrefValue.DEFAULT_QUANTUM);
        ruleMainPlayStatus = preferences.getStringValue(PrefValue.RULE_MAIN_PLAY_STATUS, PrefValue.DEFAULT_STATUS);

        ruleOfflineAdditionalNumber = preferences.getIntValue(PrefValue.RULE_OFFLINE_ADDITIONAL_NUMBER, PrefValue.DEFAULT_ADDITIONAL_NUMBER);
        ruleOfflineAssignNum1 = preferences.getIntValue(PrefValue.RULE_OFFLINE_NUM_1, PrefValue.DEFAULT_ASSIGN_NUM_1);
        ruleOfflineAssignNum2 = preferences.getIntValue(PrefValue.RULE_OFFLINE_NUM_2, PrefValue.DEFAULT_ASSIGN_NUM_2);
        ruleOfflineAssignNum3 = preferences.getIntValue(PrefValue.RULE_OFFLINE_NUM_3, PrefValue.DEFAULT_ASSIGN_NUM_3);
        ruleOfflineAssignNum4 = preferences.getIntValue(PrefValue.RULE_OFFLINE_NUM_4, PrefValue.DEFAULT_ASSIGN_NUM_4);
        ruleOfflineAssignNum5 = preferences.getIntValue(PrefValue.RULE_OFFLINE_NUM_5, PrefValue.DEFAULT_ASSIGN_NUM_5);
        ruleOfflineAssignNum6 = preferences.getIntValue(PrefValue.RULE_OFFLINE_NUM_6, PrefValue.DEFAULT_ASSIGN_NUM_6);
        ruleOfflineQuantum = preferences.getIntValue(PrefValue.RULE_OFFLINE_QUANTUM, PrefValue.DEFAULT_QUANTUM);
        ruleOfflineStatus = preferences.getStringValue(PrefValue.RULE_OFFLINE_STATUS, PrefValue.DEFAULT_STATUS);


        ruleOfflinePlayAdditionalNumber = preferences.getIntValue(PrefValue.RULE_OFFLINE_PLAY_ADDITIONAL_NUMBER, PrefValue.DEFAULT_ADDITIONAL_NUMBER);
        ruleOfflinePlayAssignNum1 = preferences.getIntValue(PrefValue.RULE_OFFLINE_PLAY_NUM_1, PrefValue.DEFAULT_ASSIGN_NUM_1);
        ruleOfflinePlayAssignNum2 = preferences.getIntValue(PrefValue.RULE_OFFLINE_PLAY_NUM_2, PrefValue.DEFAULT_ASSIGN_NUM_2);
        ruleOfflinePlayAssignNum3 = preferences.getIntValue(PrefValue.RULE_OFFLINE_PLAY_NUM_3, PrefValue.DEFAULT_ASSIGN_NUM_3);
        ruleOfflinePlayAssignNum4 = preferences.getIntValue(PrefValue.RULE_OFFLINE_PLAY_NUM_4, PrefValue.DEFAULT_ASSIGN_NUM_4);
        ruleOfflinePlayAssignNum5 = preferences.getIntValue(PrefValue.RULE_OFFLINE_PLAY_NUM_5, PrefValue.DEFAULT_ASSIGN_NUM_5);
        ruleOfflinePlayAssignNum6 = preferences.getIntValue(PrefValue.RULE_OFFLINE_PLAY_NUM_6, PrefValue.DEFAULT_ASSIGN_NUM_6);
        ruleOfflinePlayQuantum = preferences.getIntValue(PrefValue.RULE_OFFLINE_PLAY_QUANTUM, PrefValue.DEFAULT_QUANTUM);
        ruleOfflinePlayStatus = preferences.getStringValue(PrefValue.RULE_OFFLINE_PLAY_STATUS, PrefValue.DEFAULT_STATUS);
    }

    public static void init(Context context) {
        instance = new Rule(context);
    }

    public static Rule getInstance() {
        return instance;
    }

    public void setCurrentRule(byte currentRule) {
        this.currentRule = currentRule;
    }

    public void setCurrentRulePlay(byte currentRulePlay) {
        this.currentRulePlay = currentRulePlay;
    }

    public void setRuleMainGoneArrays(int[] array) {
        this.ruleMainGoneArrays = array;
    }

    public void setRuleMainPlayGoneArrays(int[] array) {
        this.ruleMainPlayGoneArrays = array;
    }

    public String getRuleMainStatus() {
        return ruleMainStatus;
    }

    public String getRuleMainPlayStatus() {
        return ruleMainPlayStatus;
    }

    public String getRuleOfflineStatus() {
        return ruleOfflineStatus;
    }

    public String getRuleOfflinePlayStatus() {
        return ruleOfflinePlayStatus;
    }

    public void setOnFireBaseDataBattleChanged(OnFireBaseDataBattleChanged onFireBaseDataBattleChanged) {
        this.onFireBaseDataBattleChanged = onFireBaseDataBattleChanged;
    }

    public void setOnFireBaseDataPlayChanged(OnFireBaseDataPlayChanged onFireBaseDataPlayChanged) {
        this.onFireBaseDataPlayChanged = onFireBaseDataPlayChanged;
    }


    /**
     * Get result in battle
     *
     * @return
     */
    public int[] getResult() {
        int[] returnArrays = getRandomNumberArrays();
        Log.d(TAG, "Rule child status on");
        Log.d(TAG, "Current rule : " + currentRule);
        switch (currentRule) {
            case RULE_NORMAL:
                Log.d(TAG, "Rule normal");
                if (ruleChildStatus.equals(STATUS_ON)) {
                    if (ruleChildQuantum == 0) {
                        switch (ruleChildRule) {
                            case 1:
                                Log.d(TAG, "Rule 1");
                                returnArrays = getRule1();
                                returnArrays = updateReturnArrayFollowAssignNumber(returnArrays, RULE_NORMAL, false);
                                break;
                            case 2:
                                Log.d(TAG, "Rule 2");
                                returnArrays = getRule2();
                                returnArrays = updateReturnArrayFollowAssignNumber(returnArrays, RULE_NORMAL, false);
                                break;
                            default:
                                Log.d(TAG, "Rule 1 default");
                                returnArrays = getRule1();
                                returnArrays = updateReturnArrayFollowAssignNumber(returnArrays, RULE_NORMAL, false);
                                break;

                        }
                    }
                } else {
                    Log.d(TAG, "Rule child status off");
                }

                break;
            case RULE_OFFLINE:
                if (ruleOfflineStatus.equals(STATUS_ON)) {
                    Log.d(TAG, "Rule offline");
                    if (ruleOfflineQuantum == 0) {
                        returnArrays = getRuleOffline();
                        returnArrays = updateReturnArrayFollowAssignNumber(returnArrays, RULE_OFFLINE, false);
                    }
                } else {
                    Log.d(TAG, "Rule offline status off");
                }
                break;

            default:
                if (ruleMainStatus.equals(STATUS_ON)) {
                    Log.d(TAG, "Rule Main");
                    if (ruleMainQuantum == 0) {
                        returnArrays = getRuleMain();
                    }
                } else {
                    Log.d(TAG, "Rule Main status off");
                }
                break;
        }


        resultArrays = ArrayUtils.addAll(resultArrays, returnArrays);
        return returnArrays;
    }

    private int[] updateReturnArrayFollowAssignNumber(int[] returnArrays, int rule, boolean isPlayMode) {
        Log.d(TAG, "Before update : ");
        for (int i : returnArrays) {
            Log.d(TAG, i+"");
        }
        if (isPlayMode) {
            switch (rule) {
                case RULE_NORMAL:
                    if (ruleChildPlayAssignNumArray != null) {
                        returnArrays[0] = getAnimalByAssignNumber(ruleChildPlayAssignNumArray, returnArrays[0]);
                        returnArrays[1] = getAnimalByAssignNumber(ruleChildPlayAssignNumArray, returnArrays[1]);
                        returnArrays[2] = getAnimalByAssignNumber(ruleChildPlayAssignNumArray, returnArrays[2]);
                    }

                    break;

                default:
                    if (ruleOfflinePlayAssignNumArray != null) {
                        returnArrays[0] = getAnimalByAssignNumber(ruleOfflinePlayAssignNumArray, returnArrays[0]);
                        returnArrays[1] = getAnimalByAssignNumber(ruleOfflinePlayAssignNumArray, returnArrays[1]);
                        returnArrays[2] = getAnimalByAssignNumber(ruleOfflinePlayAssignNumArray, returnArrays[2]);
                    }
                    break;
            }
        } else {
            switch (rule) {
                case RULE_NORMAL:
                    if (ruleChildAssignNumArray != null) {
                        returnArrays[0] = getAnimalByAssignNumber(ruleChildAssignNumArray, returnArrays[0]);
                        returnArrays[1] = getAnimalByAssignNumber(ruleChildAssignNumArray, returnArrays[1]);
                        returnArrays[2] = getAnimalByAssignNumber(ruleChildAssignNumArray, returnArrays[2]);
                    }

                    break;

                default:
                    if (ruleOfflineAssignNumArray != null) {
                        returnArrays[0] = getAnimalByAssignNumber(ruleOfflineAssignNumArray, returnArrays[0]);
                        returnArrays[1] = getAnimalByAssignNumber(ruleOfflineAssignNumArray, returnArrays[1]);
                        returnArrays[2] = getAnimalByAssignNumber(ruleOfflineAssignNumArray, returnArrays[2]);
                    }
                    break;
            }
        }
        Log.d(TAG, "After update : ");
        for (int i : returnArrays) {
            Log.d(TAG, i+"");
        }
        return returnArrays;
    }

    /**
     * Get result in battle
     *
     * @return
     */
    public int[] getResultPlay() {
        int[] returnArrays = getRandomNumberArrays();
        Log.d(TAG, "Rule child play status on");
        Log.d(TAG, "Current rule play : " + currentRulePlay);
        switch (currentRulePlay) {
            case RULE_NORMAL:
                Log.d(TAG, "Rule normal play ");
                if (ruleChildPlayStatus.equals(STATUS_ON)) {
                    if (ruleChildPlayQuantum == 0) {
                        switch (ruleChildPlayRule) {
                            case 1:
                                Log.d(TAG, "Rule 1 play ");
                                returnArrays = getRule1Play();
                                returnArrays = updateReturnArrayFollowAssignNumber(returnArrays, RULE_NORMAL, true);
                                break;
                            case 2:
                                Log.d(TAG, "Rule 2 play ");
                                returnArrays = getRule2Play();
                                returnArrays = updateReturnArrayFollowAssignNumber(returnArrays, RULE_NORMAL, true);
                                break;
                            default:
                                Log.d(TAG, "Rule 1 play default");
                                returnArrays = getRule1Play();
                                returnArrays = updateReturnArrayFollowAssignNumber(returnArrays, RULE_NORMAL, true);
                                break;

                        }
                    }
                } else {
                    Log.d(TAG, "Rule child Play status off");
                }

                break;
            case RULE_OFFLINE:
                if (ruleOfflinePlayStatus.equals(STATUS_ON)) {
                    Log.d(TAG, "Rule offline play ");
                    if (ruleOfflinePlayQuantum == 0) {
                        returnArrays = getRuleOfflinePlay();
                        returnArrays = updateReturnArrayFollowAssignNumber(returnArrays, RULE_OFFLINE, true);
                    }
                } else {
                    Log.d(TAG, "Rule offline play status off");
                }
                break;

            default:
                if (ruleMainPlayStatus.equals(STATUS_ON)) {
                    Log.d(TAG, "Rule Main play ");
                    if (ruleMainPlayQuantum == 0) {
                        returnArrays = getRuleMainPlay();
                    }
                } else {
                    Log.d(TAG, "Rule Main play status off");
                }
                break;
        }


        resultPlayArrays = ArrayUtils.addAll(resultPlayArrays, returnArrays);
        return returnArrays;
    }

    /**
     * Rule Main
     *
     * @return
     */
    private int[] getRuleMain() {
        int[] resultArray = new int[3];
        for (int i = 0; i < resultArray.length; i++) {
            while (true) {
                int value = getRandomAnimalPosition();
                if (value != ruleMainGoneArrays[0] && value != ruleMainGoneArrays[1]) {
                    resultArray[i] = value;
                    break;
                }
            }
        }
        return resultArray;
    }

    /**
     * Rule Main
     *
     * @return
     */
    private int[] getRuleMainPlay() {
        int[] resultArray = new int[3];
        for (int i = 0; i < resultArray.length; i++) {
            while (true) {
                int value = getRandomAnimalPosition();
                if (value != ruleMainPlayGoneArrays[0] && value != ruleMainPlayGoneArrays[1]) {
                    resultArray[i] = value;
                    break;
                }
            }
        }
        return resultArray;
    }

    /**
     * Rule offline
     *
     * @return
     */
    private int[] getRuleOffline() {
        int tong = 0;

        int range = 3;
        if (resultArrays.length == 0) {
            Log.d(TAG, "resultArrays length = 0");
            return getRandomNumberArrays();
        } else if (resultArrays.length >= 6) {
            range = 6;
        }
        for (int i = resultArrays.length - 1; i >= resultArrays.length - range; i--) {
            Log.d(TAG, "Result array sub : " + resultArrays[i]);
            switch (resultArrays[i]) {
                case 0:
                    tong += ruleOfflineAssignNum1;
                    break;
                case 1:
                    tong += ruleOfflineAssignNum2;
                    break;
                case 2:
                    tong += ruleOfflineAssignNum3;
                    break;
                case 3:
                    tong += ruleOfflineAssignNum4;
                    break;
                case 4:
                    tong += ruleOfflineAssignNum5;
                    break;
                default:
                    tong += ruleOfflineAssignNum6;
                    break;
            }
        }

        tong += ruleOfflineAdditionalNumber;

        int min = Integer.parseInt((new SimpleDateFormat("mm").format(new Date())).toString());

        if (min < 10)
            tong -= 1;
        if (min >= 10 && min < 20)
            tong -= 2;
        if (min >= 20 && min < 30)
            tong -= 3;
        if (min >= 30 && min < 40)
            tong -= 4;
        if (min >= 40 && min < 50)
            tong -= 5;
        if (min >= 50 && min < 60)
            tong -= 6;

        Log.d(TAG, "Tong : " + tong);
        int number = tong % 6;
        Log.d(TAG, "Number : " + number);

        int[] returnArrays = getRandomNumberArrays();
        returnArrays[getRandomNumber(0, 2)] = number;
        return returnArrays;
    }

    /**
     * Rule offline
     *
     * @return
     */
    private int[] getRuleOfflinePlay() {
        int tong = 0;

        int range = 3;
        if (resultPlayArrays.length == 0) {
            Log.d(TAG, "resultPlayArrays length = 0");
            return getRandomNumberArrays();
        } else if (resultPlayArrays.length >= 6) {
            range = 6;
        }
        for (int i = resultPlayArrays.length - 1; i >= resultPlayArrays.length - range; i--) {
            Log.d(TAG, "Result array sub : " + resultPlayArrays[i]);
            switch (resultPlayArrays[i]) {
                case 0:
                    tong += ruleOfflinePlayAssignNum1;
                    break;
                case 1:
                    tong += ruleOfflinePlayAssignNum2;
                    break;
                case 2:
                    tong += ruleOfflinePlayAssignNum3;
                    break;
                case 3:
                    tong += ruleOfflinePlayAssignNum4;
                    break;
                case 4:
                    tong += ruleOfflinePlayAssignNum5;
                    break;
                default:
                    tong += ruleOfflinePlayAssignNum6;
                    break;
            }
        }

        tong += ruleOfflinePlayAdditionalNumber;

        int min = Integer.parseInt((new SimpleDateFormat("mm").format(new Date())).toString());

        if (min < 10)
            tong -= 1;
        if (min >= 10 && min < 20)
            tong -= 2;
        if (min >= 20 && min < 30)
            tong -= 3;
        if (min >= 30 && min < 40)
            tong -= 4;
        if (min >= 40 && min < 50)
            tong -= 5;
        if (min >= 50 && min < 60)
            tong -= 6;

        Log.d(TAG, "Tong Play : " + tong);
        int number = tong % 6;
        Log.d(TAG, "Number Play : " + number);

        int[] returnArrays = getRandomNumberArrays();
        returnArrays[getRandomNumber(0, 2)] = number;
        return returnArrays;
    }

    /**
     * Rule 2
     *
     * @return
     */
    private int[] getRule2() {
        int tong = 0;

        int range = 3;
        if (resultArrays.length == 0) {
            Log.d(TAG, "resultArrays length = 0");
            return getRandomNumberArrays();
        } else if (resultArrays.length >= 6) {
            range = 6;
        }
        for (int i = resultArrays.length - 1; i >= resultArrays.length - range; i--) {
            Log.d(TAG, "Result array sub : " + resultArrays[i]);
            switch (resultArrays[i]) {
                case 0:
                    tong += ruleChildAssignNum1;
                    break;
                case 1:
                    tong += ruleChildAssignNum2;
                    break;
                case 2:
                    tong += ruleChildAssignNum3;
                    break;
                case 3:
                    tong += ruleChildAssignNum4;
                    break;
                case 4:
                    tong += ruleChildAssignNum5;
                    break;
                default:
                    tong += ruleChildAssignNum6;
                    break;
            }
        }

        tong += ruleChildAdditionalNumber;

        int min = Integer.parseInt((new SimpleDateFormat("mm").format(new Date())).toString());

        if (min < 10)
            tong += 1;
        if (min >= 10 && min < 20)
            tong += 2;
        if (min >= 20 && min < 30)
            tong += 3;
        if (min >= 30 && min < 40)
            tong += 4;
        if (min >= 40 && min < 50)
            tong += 5;
        if (min >= 50 && min < 60)
            tong += 6;

        Log.d(TAG, "Tong : " + tong);
        int number = tong % 6;
        Log.d(TAG, "Number : " + number);

        int[] returnArrays = getRandomNumberArrays();
        returnArrays[getRandomNumber(0, 2)] = number;
        return returnArrays;
    }

    /**
     * Rule 2
     *
     * @return
     */
    private int[] getRule2Play() {
        int tong = 0;

        int range = 3;
        if (resultPlayArrays.length == 0) {
            Log.d(TAG, "resultPlayArrays length = 0");
            return getRandomNumberArrays();
        } else if (resultPlayArrays.length >= 6) {
            range = 6;
        }
        for (int i = resultPlayArrays.length - 1; i >= resultPlayArrays.length - range; i--) {
            Log.d(TAG, "Result array sub : " + resultPlayArrays[i]);
            switch (resultPlayArrays[i]) {
                case 0:
                    tong += ruleChildPlayAssignNum1;
                    break;
                case 1:
                    tong += ruleChildPlayAssignNum2;
                    break;
                case 2:
                    tong += ruleChildPlayAssignNum3;
                    break;
                case 3:
                    tong += ruleChildPlayAssignNum4;
                    break;
                case 4:
                    tong += ruleChildPlayAssignNum5;
                    break;
                default:
                    tong += ruleChildPlayAssignNum6;
                    break;
            }
        }

        tong += ruleChildPlayAdditionalNumber;

        int min = Integer.parseInt((new SimpleDateFormat("mm").format(new Date())).toString());

        if (min < 10)
            tong += 1;
        if (min >= 10 && min < 20)
            tong += 2;
        if (min >= 20 && min < 30)
            tong += 3;
        if (min >= 30 && min < 40)
            tong += 4;
        if (min >= 40 && min < 50)
            tong += 5;
        if (min >= 50 && min < 60)
            tong += 6;

        Log.d(TAG, "Tong play : " + tong);
        int number = tong % 6;
        Log.d(TAG, "Number play : " + number);

        int[] returnArrays = getRandomNumberArrays();
        returnArrays[getRandomNumber(0, 2)] = number;
        return returnArrays;
    }

    /**
     * Rule 1
     *
     * @return
     */
    private int[] getRule1() {
        int tong = 0;

        int range = 3;
        if (resultArrays.length == 0) {
            Log.d(TAG, "resultArrays length = 0");
            return getRandomNumberArrays();
        } else if (resultArrays.length >= 6) {
            range = 6;
        }
        for (int i = resultArrays.length - 1; i >= resultArrays.length - range; i--) {
            Log.d(TAG, "Result array sub : " + resultArrays[i]);
            switch (resultArrays[i]) {
                case 0:
                    tong += ruleChildAssignNum1;
                    break;
                case 1:
                    tong += ruleChildAssignNum2;
                    break;
                case 2:
                    tong += ruleChildAssignNum3;
                    break;
                case 3:
                    tong += ruleChildAssignNum4;
                    break;
                case 4:
                    tong += ruleChildAssignNum5;
                    break;
                default:
                    tong += ruleChildAssignNum6;
                    break;
            }
        }

        tong += ruleChildAdditionalNumber;
        Log.d(TAG, "Tong : " + tong);
        int number = tong % 6;
        Log.d(TAG, "Number : " + number);

        int[] returnArrays = getRandomNumberArrays();
        returnArrays[getRandomNumber(0, 2)] = number;
        return returnArrays;
    }

    /**
     * Rule 1
     *
     * @return
     */
    private int[] getRule1Play() {
        int tong = 0;

        int range = 3;
        if (resultPlayArrays.length == 0) {
            Log.d(TAG, "resultPlayArrays length = 0");
            return getRandomNumberArrays();
        } else if (resultPlayArrays.length >= 6) {
            range = 6;
        }
        for (int i = resultPlayArrays.length - 1; i >= resultPlayArrays.length - range; i--) {
            Log.d(TAG, "Result array sub : " + resultPlayArrays[i]);
            switch (resultPlayArrays[i]) {
                case 0:
                    tong += ruleChildPlayAssignNum1;
                    break;
                case 1:
                    tong += ruleChildPlayAssignNum2;
                    break;
                case 2:
                    tong += ruleChildPlayAssignNum3;
                    break;
                case 3:
                    tong += ruleChildPlayAssignNum4;
                    break;
                case 4:
                    tong += ruleChildPlayAssignNum5;
                    break;
                default:
                    tong += ruleChildPlayAssignNum6;
                    break;
            }
        }

        tong += ruleChildPlayAdditionalNumber;
        Log.d(TAG, "Tong play: " + tong);
        int number = tong % 6;
        Log.d(TAG, "Number play : " + number);

        int[] returnArrays = getRandomNumberArrays();
        returnArrays[getRandomNumber(0, 2)] = number;
        return returnArrays;
    }

    /**
     * Initialize Firebase realtime database
     */
    private void initFirebase() {
        FirebaseDatabase.getInstance().getReference("BC1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                Task.startNewBackGroundThread(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //Rule child
                        RuleChild ruleChild = dataSnapshot.child("RuleChild").getValue(RuleChild.class);
                        Log.d(TAG, "[RuleChild] [additionalNumber : " + ruleChild.additionalNumber + " ]");
                        Log.d(TAG, "[RuleChild] [assignNumber : " + ruleChild.assignNumber + " ]");
                        Log.d(TAG, "[RuleChild] [quantum : " + ruleChild.quantum + " ]");
                        Log.d(TAG, "[RuleChild] [rule : " + ruleChild.rule + " ]");
                        Log.d(TAG, "[RuleChild] [status : " + ruleChild.status + " ]");

                        ruleChildAdditionalNumber = ruleChild.getAdditionalNumber();
                        preferences.storeValue(PrefValue.RULE_CHILD_ADDITIONAL_NUMBER, ruleChildAdditionalNumber);

                        ruleChildAssignNumArray = ruleChild.getAssignNumberArray();
                        ruleChildAssignNum1 = ruleChildAssignNumArray[0];
                        ruleChildAssignNum2 = ruleChildAssignNumArray[1];
                        ruleChildAssignNum3 = ruleChildAssignNumArray[2];
                        ruleChildAssignNum4 = ruleChildAssignNumArray[3];
                        ruleChildAssignNum5 = ruleChildAssignNumArray[4];
                        ruleChildAssignNum6 = ruleChildAssignNumArray[5];
                        preferences.storeValue(PrefValue.RULE_CHILD_NUM_1, ruleChildAssignNum1);
                        preferences.storeValue(PrefValue.RULE_CHILD_NUM_2, ruleChildAssignNum2);
                        preferences.storeValue(PrefValue.RULE_CHILD_NUM_3, ruleChildAssignNum3);
                        preferences.storeValue(PrefValue.RULE_CHILD_NUM_4, ruleChildAssignNum4);
                        preferences.storeValue(PrefValue.RULE_CHILD_NUM_5, ruleChildAssignNum5);
                        preferences.storeValue(PrefValue.RULE_CHILD_NUM_6, ruleChildAssignNum6);

                        ruleChildQuantum = ruleChild.getQuantum();
                        preferences.storeValue(PrefValue.RULE_CHILD_QUANTUM, ruleChildQuantum);

                        ruleChildRule = ruleChild.getRule();
                        preferences.storeValue(PrefValue.RULE_CHILD_RULE, ruleChildRule);

                        ruleChildStatus = ruleChild.status;
                        preferences.storeValue(PrefValue.RULE_CHILD_STATUS, ruleChildStatus);

                        //Rule Child Play
                        RuleChildPlay ruleChildPlay = dataSnapshot.child("RuleChildPlay").getValue(RuleChildPlay.class);
                        Log.d(TAG, "[RuleChildPlay] [additionalNumber : " + ruleChildPlay.additionalNumber + " ]");
                        Log.d(TAG, "[RuleChildPlay] [assignNumber : " + ruleChildPlay.assignNumber + " ]");
                        Log.d(TAG, "[RuleChildPlay] [quantum : " + ruleChildPlay.quantum + " ]");
                        Log.d(TAG, "[RuleChildPlay] [rule : " + ruleChildPlay.rule + " ]");
                        Log.d(TAG, "[RuleChildPlay] [status : " + ruleChildPlay.status + " ]");

                        ruleChildPlayAdditionalNumber = ruleChildPlay.getAdditionalNumber();
                        preferences.storeValue(PrefValue.RULE_CHILD_PLAY_ADDITIONAL_NUMBER, ruleChildPlayAdditionalNumber);

                        ruleChildPlayAssignNumArray = ruleChildPlay.getAssignNumberArray();
                        ruleChildPlayAssignNum1 = ruleChildPlayAssignNumArray[0];
                        ruleChildPlayAssignNum2 = ruleChildPlayAssignNumArray[1];
                        ruleChildPlayAssignNum3 = ruleChildPlayAssignNumArray[2];
                        ruleChildPlayAssignNum4 = ruleChildPlayAssignNumArray[3];
                        ruleChildPlayAssignNum5 = ruleChildPlayAssignNumArray[4];
                        ruleChildPlayAssignNum6 = ruleChildPlayAssignNumArray[5];
                        preferences.storeValue(PrefValue.RULE_CHILD_PLAY_NUM_1, ruleChildPlayAssignNum1);
                        preferences.storeValue(PrefValue.RULE_CHILD_PLAY_NUM_2, ruleChildPlayAssignNum2);
                        preferences.storeValue(PrefValue.RULE_CHILD_PLAY_NUM_3, ruleChildPlayAssignNum3);
                        preferences.storeValue(PrefValue.RULE_CHILD_PLAY_NUM_4, ruleChildPlayAssignNum4);
                        preferences.storeValue(PrefValue.RULE_CHILD_PLAY_NUM_5, ruleChildPlayAssignNum5);
                        preferences.storeValue(PrefValue.RULE_CHILD_PLAY_NUM_6, ruleChildPlayAssignNum6);

                        ruleChildPlayQuantum = ruleChildPlay.getQuantum();
                        preferences.storeValue(PrefValue.RULE_CHILD_PLAY_QUANTUM, ruleChildPlayQuantum);

                        ruleChildPlayRule = ruleChildPlay.getRule();
                        preferences.storeValue(PrefValue.RULE_CHILD_PLAY_RULE, ruleChildPlayRule);

                        ruleChildPlayStatus = ruleChildPlay.status;
                        preferences.storeValue(PrefValue.RULE_CHILD_PLAY_STATUS, ruleChildPlayStatus);

                        //Rule Main
                        RuleMain ruleMain = dataSnapshot.child("RuleMain").getValue(RuleMain.class);
                        Log.d(TAG, "[RuleMain] [quantum : " + ruleMain.quantum + " ]");
                        Log.d(TAG, "[RuleMain] [status : " + ruleMain.status + " ]");

                        ruleMainQuantum = ruleMain.getQuantum();
                        preferences.storeValue(PrefValue.RULE_MAIN_QUANTUM, ruleMainQuantum);

                        ruleMainStatus = ruleMain.status;
                        preferences.storeValue(PrefValue.RULE_MAIN_STATUS, ruleMainStatus);

                        //Rule Main Play
                        RuleMainPlay ruleMainPlay = dataSnapshot.child("RuleMainPlay").getValue(RuleMainPlay.class);
                        Log.d(TAG, "[RuleMainPlay] [quantum : " + ruleMainPlay.quantum + " ]");
                        Log.d(TAG, "[RuleMainPlay] [status : " + ruleMainPlay.status + " ]");

                        ruleMainPlayQuantum = ruleMainPlay.getQuantum();
                        preferences.storeValue(PrefValue.RULE_MAIN_PLAY_QUANTUM, ruleMainPlayQuantum);

                        ruleMainPlayStatus = ruleMainPlay.status;
                        preferences.storeValue(PrefValue.RULE_MAIN_PLAY_STATUS, ruleMainPlayStatus);


                        //Rule offline
                        RuleOffline ruleOffline = dataSnapshot.child("RuleOffline").getValue(RuleOffline.class);
                        Log.d(TAG, "[RuleOffline] [additionalNumber : " + ruleOffline.additionalNumber + " ]");
                        Log.d(TAG, "[RuleOffline] [assignNumber : " + ruleOffline.assignNumber + " ]");
                        Log.d(TAG, "[RuleOffline] [quantum : " + ruleOffline.quantum + " ]");
                        Log.d(TAG, "[RuleOffline] [status : " + ruleOffline.status + " ]");
                        ruleOfflineAdditionalNumber = ruleOffline.getAdditionalNumber();
                        preferences.storeValue(PrefValue.RULE_OFFLINE_ADDITIONAL_NUMBER, ruleOfflineAdditionalNumber);

                        ruleOfflineAssignNumArray = ruleOffline.getAssignNumberArray();
                        ruleOfflineAssignNum1 = ruleOfflineAssignNumArray[0];
                        ruleOfflineAssignNum2 = ruleOfflineAssignNumArray[1];
                        ruleOfflineAssignNum3 = ruleOfflineAssignNumArray[2];
                        ruleOfflineAssignNum4 = ruleOfflineAssignNumArray[3];
                        ruleOfflineAssignNum5 = ruleOfflineAssignNumArray[4];
                        ruleOfflineAssignNum6 = ruleOfflineAssignNumArray[5];
                        preferences.storeValue(PrefValue.RULE_OFFLINE_NUM_1, ruleOfflineAssignNum1);
                        preferences.storeValue(PrefValue.RULE_OFFLINE_NUM_2, ruleOfflineAssignNum2);
                        preferences.storeValue(PrefValue.RULE_OFFLINE_NUM_3, ruleOfflineAssignNum3);
                        preferences.storeValue(PrefValue.RULE_OFFLINE_NUM_4, ruleOfflineAssignNum4);
                        preferences.storeValue(PrefValue.RULE_OFFLINE_NUM_5, ruleOfflineAssignNum5);
                        preferences.storeValue(PrefValue.RULE_OFFLINE_NUM_6, ruleOfflineAssignNum6);

                        ruleOfflineQuantum = ruleOffline.getQuantum();
                        preferences.storeValue(PrefValue.RULE_OFFLINE_QUANTUM, ruleOfflineQuantum);

                        ruleOfflineStatus = ruleOffline.status;
                        preferences.storeValue(PrefValue.RULE_OFFLINE_STATUS, ruleOfflineStatus);

                        //Rule Offline Play
                        RuleOfflinePlay ruleOfflinePlay = dataSnapshot.child("RuleOfflinePlay").getValue(RuleOfflinePlay.class);
                        Log.d(TAG, "[RuleOfflinePlay] [additionalNumber : " + ruleOfflinePlay.additionalNumber + " ]");
                        Log.d(TAG, "[RuleOfflinePlay] [assignNumber : " + ruleOfflinePlay.assignNumber + " ]");
                        Log.d(TAG, "[RuleOfflinePlay] [quantum : " + ruleOfflinePlay.quantum + " ]");
                        Log.d(TAG, "[RuleOfflinePlay] [status : " + ruleOfflinePlay.status + " ]");
                        ruleOfflinePlayAdditionalNumber = ruleOfflinePlay.getAdditionalNumber();
                        preferences.storeValue(PrefValue.RULE_OFFLINE_PLAY_ADDITIONAL_NUMBER, ruleOfflinePlayAdditionalNumber);

                        ruleOfflinePlayAssignNumArray = ruleOfflinePlay.getAssignNumberArray();
                        ruleOfflinePlayAssignNum1 = ruleOfflinePlayAssignNumArray[0];
                        ruleOfflinePlayAssignNum2 = ruleOfflinePlayAssignNumArray[1];
                        ruleOfflinePlayAssignNum3 = ruleOfflinePlayAssignNumArray[2];
                        ruleOfflinePlayAssignNum4 = ruleOfflinePlayAssignNumArray[3];
                        ruleOfflinePlayAssignNum5 = ruleOfflinePlayAssignNumArray[4];
                        ruleOfflinePlayAssignNum6 = ruleOfflinePlayAssignNumArray[5];
                        preferences.storeValue(PrefValue.RULE_OFFLINE_PLAY_NUM_1, ruleOfflinePlayAssignNum1);
                        preferences.storeValue(PrefValue.RULE_OFFLINE_PLAY_NUM_2, ruleOfflinePlayAssignNum2);
                        preferences.storeValue(PrefValue.RULE_OFFLINE_PLAY_NUM_3, ruleOfflinePlayAssignNum3);
                        preferences.storeValue(PrefValue.RULE_OFFLINE_PLAY_NUM_4, ruleOfflinePlayAssignNum4);
                        preferences.storeValue(PrefValue.RULE_OFFLINE_PLAY_NUM_5, ruleOfflinePlayAssignNum5);
                        preferences.storeValue(PrefValue.RULE_OFFLINE_PLAY_NUM_6, ruleOfflinePlayAssignNum6);

                        ruleOfflinePlayQuantum = ruleOfflinePlay.getQuantum();
                        preferences.storeValue(PrefValue.RULE_OFFLINE_PLAY_QUANTUM, ruleOfflinePlayQuantum);

                        ruleOfflinePlayStatus = ruleOfflinePlay.status;
                        preferences.storeValue(PrefValue.RULE_OFFLINE_PLAY_STATUS, ruleOfflinePlayStatus);

                        //Text
                        final String text = dataSnapshot.child("Text").getValue().toString();
                        preferences.storeValue(PrefValue.TEXT, text);
                        Log.d(TAG, "Text : " + text);

                        //Text
                        final String textPlay = dataSnapshot.child("TextPlay").getValue().toString();
                        preferences.storeValue(PrefValue.TEXT_PLAY, textPlay);
                        Log.d(TAG, "Text Play : " + textPlay);


                        Task.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                if (onFireBaseDataBattleChanged != null) {
                                    onFireBaseDataBattleChanged.onTextChanged(text);
                                    onFireBaseDataBattleChanged.onDataChanged();
                                }

                                if (onFireBaseDataPlayChanged != null) {
                                    onFireBaseDataPlayChanged.onTextChanged(textPlay);
                                    onFireBaseDataPlayChanged.onDataChanged();
                                }
                            }
                        });
                    }
                }));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
    }

    private int[] getRandomNumberArrays() {
        Random random = new Random();
        return new int[]{random.nextInt((5 - 0) + 1),
                random.nextInt((5 - 0) + 1),
                random.nextInt((5 - 0) + 1)};
    }

    private int getRandomAnimalPosition() {
        return new Random().nextInt((5 - 0) + 1);
    }

    private int getRandomNumber(int min, int max) {
        return new Random().nextInt((max - min) + 1);
    }

    public void minusRuleNumber(byte ruleType) {

        switch (ruleType) {
            case RULE_NORMAL:
                if (ruleChildStatus.equals(STATUS_ON)) {
                    if (ruleChildQuantum > 0)
                        ruleChildQuantum = ruleChildQuantum - 1;
                    Log.d(TAG, "ruleChildQuantum : " + ruleChildQuantum);
                }
                break;

            case RULE_MAIN:
                if (ruleMainStatus.equals(STATUS_ON)) {
                    if (ruleMainQuantum > 0)
                        ruleMainQuantum = ruleMainQuantum - 1;
                    Log.d(TAG, "ruleMainQuantum : " + ruleMainQuantum);
                }
                break;

            case RULE_OFFLINE:
                if (ruleOfflineStatus.equals(STATUS_ON)) {
                    if (ruleOfflineQuantum > 0)
                        ruleOfflineQuantum = ruleOfflineQuantum - 1;
                    Log.d(TAG, "ruleOfflineQuantum : " + ruleOfflineQuantum);
                }
                break;
        }
    }

    public void minusRuleNumberPlay(byte ruleType) {

        switch (ruleType) {
            case RULE_NORMAL:
                if (ruleChildPlayStatus.equals(STATUS_ON)) {
                    if (ruleChildPlayQuantum > 0)
                        ruleChildPlayQuantum = ruleChildPlayQuantum - 1;
                    Log.d(TAG, "ruleChildPlayQuantum : " + ruleChildPlayQuantum);
                }
                break;

            case RULE_MAIN:
                if (ruleMainPlayStatus.equals(STATUS_ON)) {
                    if (ruleMainPlayQuantum > 0)
                        ruleMainPlayQuantum = ruleMainPlayQuantum - 1;
                    Log.d(TAG, "ruleMainPlayQuantum : " + ruleMainPlayQuantum);
                }
                break;

            case RULE_OFFLINE:
                if (ruleOfflinePlayStatus.equals(STATUS_ON)) {
                    if (ruleOfflinePlayQuantum > 0)
                        ruleOfflinePlayQuantum = ruleOfflinePlayQuantum - 1;
                    Log.d(TAG, "ruleOfflinePlayQuantum : " + ruleOfflinePlayQuantum);
                }
                break;
        }
    }

    public int getAnimalByAssignNumber(int[] arrays, int additionalNumber) {
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i] == additionalNumber) {
                return i;
            }
        }
        return additionalNumber;
    }
}
