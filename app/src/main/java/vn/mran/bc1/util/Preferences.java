package vn.mran.bc1.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * Created by Covisoft on 24/12/2015.
 */
public final class Preferences {
    private SharedPreferences pref;
    private Editor editor;

    public static final String PREF_NAME = "XD1";

    public Preferences(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void storeValue(String key, Object value) {
        if (value instanceof String) {
            editor.putString(key, (String) value);
        }
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        }
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        }
        if (value instanceof Long) {
            editor.putLong(key, (long) value);
        }
        editor.apply();
        editor.commit();
        Log.d("Preferences store", "Key(" + key + ") , Value(" + value + ")");
    }

    public boolean getBooleanValue(String key) {
        return pref.getBoolean(key, false);
    }

    public boolean getBooleanValue(String key,boolean defaultValue) {
        return pref.getBoolean(key, defaultValue);
    }

    public String getStringValue(String key) {
        return pref.getString(key, "");
    }

    public String getStringValue(String key,String defaultValue) {
        return pref.getString(key, defaultValue);
    }

    public int getIntValue(String key){
        return pref.getInt(key,-1);
    }

    public int getIntValue(String key,int defaultValue){
        return pref.getInt(key,defaultValue);
    }
}
