package vn.mran.bc1.instance;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.mran.bc1.helper.Log;
import vn.mran.bc1.model.RuleChild;
import vn.mran.bc1.model.RuleChildPlay;
import vn.mran.bc1.model.RuleMain;
import vn.mran.bc1.model.RuleMainPlay;
import vn.mran.bc1.model.RuleOffline;
import vn.mran.bc1.model.RuleOfflinePlay;
import vn.mran.bc1.util.Preferences;

/**
 * Created by Mr An on 20/12/2017.
 */

public class Rule {

    private final String TAG = getClass().getSimpleName();

    private static Rule instance;

    private Preferences preferences;

    private Rule(Context context) {
        preferences = new Preferences(context);
        initFirebase();
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
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Rule child
                RuleChild ruleChild = dataSnapshot.child("RuleChild").getValue(RuleChild.class);
                Log.d(TAG, "[RuleChild] [additionalNumber : " + ruleChild.additionalNumber + " ]");
                Log.d(TAG, "[RuleChild] [assignNumber : " + ruleChild.assignNumber + " ]");
                Log.d(TAG, "[RuleChild] [quantum : " + ruleChild.quantum + " ]");
                Log.d(TAG, "[RuleChild] [rule : " + ruleChild.rule + " ]");

                RuleChildPlay ruleChildPlay = dataSnapshot.child("RuleChildPlay").getValue(RuleChildPlay.class);
                Log.d(TAG, "[RuleChildPlay] [additionalNumber : " + ruleChildPlay.additionalNumber + " ]");
                Log.d(TAG, "[RuleChildPlay] [assignNumber : " + ruleChildPlay.assignNumber + " ]");
                Log.d(TAG, "[RuleChildPlay] [quantum : " + ruleChildPlay.quantum + " ]");
                Log.d(TAG, "[RuleChildPlay] [rule : " + ruleChildPlay.rule + " ]");

                RuleMain ruleMain = dataSnapshot.child("RuleMain").getValue(RuleMain.class);
                Log.d(TAG, "[RuleMain] [quantum : " + ruleMain.quantum + " ]");
                Log.d(TAG, "[RuleMain] [status : " + ruleMain.status + " ]");

                RuleMainPlay ruleMainPlay = dataSnapshot.child("RuleMainPlay").getValue(RuleMainPlay.class);
                Log.d(TAG, "[RuleMainPlay] [quantum : " + ruleMainPlay.quantum + " ]");
                Log.d(TAG, "[RuleMainPlay] [status : " + ruleMainPlay.status + " ]");

                RuleOffline ruleOffline = dataSnapshot.child("RuleOffline").getValue(RuleOffline.class);
                Log.d(TAG, "[RuleOffline] [additionalNumber : " + ruleOffline.additionalNumber + " ]");
                Log.d(TAG, "[RuleOffline] [assignNumber : " + ruleOffline.assignNumber + " ]");
                Log.d(TAG, "[RuleOffline] [quantum : " + ruleOffline.quantum + " ]");
                Log.d(TAG, "[RuleOffline] [rule : " + ruleOffline.status + " ]");

                RuleOfflinePlay ruleOfflinePlay = dataSnapshot.child("RuleOfflinePlay").getValue(RuleOfflinePlay.class);
                Log.d(TAG, "[RuleOfflinePlay] [additionalNumber : " + ruleOfflinePlay.additionalNumber + " ]");
                Log.d(TAG, "[RuleOfflinePlay] [assignNumber : " + ruleOfflinePlay.assignNumber + " ]");
                Log.d(TAG, "[RuleOfflinePlay] [quantum : " + ruleOfflinePlay.quantum + " ]");
                Log.d(TAG, "[RuleOfflinePlay] [rule : " + ruleOfflinePlay.status + " ]");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
    }
}
