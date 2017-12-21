package vn.mran.bc1.instance;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    private final String TAG = getClass().getSimpleName();

    private static Rule instance;

    private Preferences preferences;

    private int ruleChildAdditionalNumber;
    private int ruleChildAssignNum1;
    private int ruleChildAssignNum2;
    private int ruleChildAssignNum3;
    private int ruleChildAssignNum4;
    private int ruleChildAssignNum5;
    private int ruleChildAssignNum6;
    private int ruleChildQuantum;
    private int ruleChildRule;
    private String ruleChildStatus;

    private int ruleChildPlayAdditionalNumber;
    private int ruleChildPlayAssignNum1;
    private int ruleChildPlayAssignNum2;
    private int ruleChildPlayAssignNum3;
    private int ruleChildPlayAssignNum4;
    private int ruleChildPlayAssignNum5;
    private int ruleChildPlayAssignNum6;
    private int ruleChildPlayQuantum;
    private int ruleChildPlayRule;
    private String ruleChildPlayStatus;

    private int ruleMainQuantum;
    private String ruleMainStatus;

    private int ruleMainPlayQuantum;
    private String ruleMainPlayStatus;

    private int ruleOfflineAdditionalNumber;
    private int ruleOfflineAssignNum1;
    private int ruleOfflineAssignNum2;
    private int ruleOfflineAssignNum3;
    private int ruleOfflineAssignNum4;
    private int ruleOfflineAssignNum5;
    private int ruleOfflineAssignNum6;
    private int ruleOfflineQuantum;
    private String ruleOfflineStatus;

    private int ruleOfflinePlayAdditionalNumber;
    private int ruleOfflinePlayAssignNum1;
    private int ruleOfflinePlayAssignNum2;
    private int ruleOfflinePlayAssignNum3;
    private int ruleOfflinePlayAssignNum4;
    private int ruleOfflinePlayAssignNum5;
    private int ruleOfflinePlayAssignNum6;
    private int ruleOfflinePlayQuantum;
    private String ruleOfflinePlayStatus;

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

                        int[] ruleChildAssignNumArray = ruleChild.getAssignNumberArray();
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
                        preferences.storeValue(PrefValue.RULE_CHILD_QUANTUM,ruleChildQuantum);

                        ruleChildRule = ruleChild.getRule();
                        preferences.storeValue(PrefValue.RULE_CHILD_RULE,ruleChildRule);

                        ruleChildStatus = ruleChild.status;
                        preferences.storeValue(PrefValue.RULE_CHILD_STATUS,ruleChildStatus);

                        //Rule Child Play
                        RuleChildPlay ruleChildPlay = dataSnapshot.child("RuleChildPlay").getValue(RuleChildPlay.class);
                        Log.d(TAG, "[RuleChildPlay] [additionalNumber : " + ruleChildPlay.additionalNumber + " ]");
                        Log.d(TAG, "[RuleChildPlay] [assignNumber : " + ruleChildPlay.assignNumber + " ]");
                        Log.d(TAG, "[RuleChildPlay] [quantum : " + ruleChildPlay.quantum + " ]");
                        Log.d(TAG, "[RuleChildPlay] [rule : " + ruleChildPlay.rule + " ]");
                        Log.d(TAG, "[RuleChildPlay] [status : " + ruleChildPlay.status + " ]");

                        ruleChildPlayAdditionalNumber = ruleChildPlay.getAdditionalNumber();
                        preferences.storeValue(PrefValue.RULE_CHILD_PLAY_ADDITIONAL_NUMBER, ruleChildPlayAdditionalNumber);

                        int[] ruleChildPlayAssignNumArray = ruleChildPlay.getAssignNumberArray();
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
                        preferences.storeValue(PrefValue.RULE_CHILD_PLAY_QUANTUM,ruleChildPlayQuantum);

                        ruleChildPlayRule = ruleChildPlay.getRule();
                        preferences.storeValue(PrefValue.RULE_CHILD_PLAY_RULE,ruleChildPlayRule);

                        ruleChildPlayStatus = ruleChildPlay.status;
                        preferences.storeValue(PrefValue.RULE_CHILD_PLAY_STATUS,ruleChildPlayStatus);

                        //Rule Main
                        RuleMain ruleMain = dataSnapshot.child("RuleMain").getValue(RuleMain.class);
                        Log.d(TAG, "[RuleMain] [quantum : " + ruleMain.quantum + " ]");
                        Log.d(TAG, "[RuleMain] [status : " + ruleMain.status + " ]");

                        ruleMainQuantum = ruleMain.getQuantum();
                        preferences.storeValue(PrefValue.RULE_MAIN_QUANTUM,ruleMainQuantum);

                        ruleMainStatus = ruleMain.status;
                        preferences.storeValue(PrefValue.RULE_MAIN_STATUS,ruleMainStatus);

                        //Rule Main Play
                        RuleMainPlay ruleMainPlay = dataSnapshot.child("RuleMainPlay").getValue(RuleMainPlay.class);
                        Log.d(TAG, "[RuleMainPlay] [quantum : " + ruleMainPlay.quantum + " ]");
                        Log.d(TAG, "[RuleMainPlay] [status : " + ruleMainPlay.status + " ]");

                        ruleMainPlayQuantum = ruleMainPlay.getQuantum();
                        preferences.storeValue(PrefValue.RULE_MAIN_PLAY_QUANTUM,ruleMainPlayQuantum);

                        ruleMainPlayStatus = ruleMain.status;
                        preferences.storeValue(PrefValue.RULE_MAIN_PLAY_STATUS,ruleMainPlayStatus);


                        //Rule offline
                        RuleOffline ruleOffline = dataSnapshot.child("RuleOffline").getValue(RuleOffline.class);
                        Log.d(TAG, "[RuleOffline] [additionalNumber : " + ruleOffline.additionalNumber + " ]");
                        Log.d(TAG, "[RuleOffline] [assignNumber : " + ruleOffline.assignNumber + " ]");
                        Log.d(TAG, "[RuleOffline] [quantum : " + ruleOffline.quantum + " ]");
                        Log.d(TAG, "[RuleOffline] [status : " + ruleOffline.status + " ]");
                        ruleOfflineAdditionalNumber = ruleOffline.getAdditionalNumber();
                        preferences.storeValue(PrefValue.RULE_OFFLINE_ADDITIONAL_NUMBER, ruleOfflineAdditionalNumber);

                        int[] ruleOfflineAssignNumArray = ruleOffline.getAssignNumberArray();
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
                        preferences.storeValue(PrefValue.RULE_OFFLINE_QUANTUM,ruleOfflineQuantum);

                        ruleOfflineStatus = ruleOffline.status;
                        preferences.storeValue(PrefValue.RULE_OFFLINE_STATUS,ruleOfflineStatus);

                        //Rule Offline Play
                        RuleOfflinePlay ruleOfflinePlay = dataSnapshot.child("RuleOfflinePlay").getValue(RuleOfflinePlay.class);
                        Log.d(TAG, "[RuleOfflinePlay] [additionalNumber : " + ruleOfflinePlay.additionalNumber + " ]");
                        Log.d(TAG, "[RuleOfflinePlay] [assignNumber : " + ruleOfflinePlay.assignNumber + " ]");
                        Log.d(TAG, "[RuleOfflinePlay] [quantum : " + ruleOfflinePlay.quantum + " ]");
                        Log.d(TAG, "[RuleOfflinePlay] [status : " + ruleOfflinePlay.status + " ]");ruleOfflinePlayAdditionalNumber = ruleOfflinePlay.getAdditionalNumber();
                        preferences.storeValue(PrefValue.RULE_OFFLINE_PLAY_ADDITIONAL_NUMBER, ruleOfflinePlayAdditionalNumber);

                        int[] ruleOfflinePlayAssignNumArray = ruleOfflinePlay.getAssignNumberArray();
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
                        preferences.storeValue(PrefValue.RULE_OFFLINE_PLAY_QUANTUM,ruleOfflinePlayQuantum);

                        ruleOfflinePlayStatus = ruleOfflinePlay.status;
                        preferences.storeValue(PrefValue.RULE_OFFLINE_PLAY_STATUS,ruleOfflinePlayStatus);
                    }
                }));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
    }
}
