package tesis.com.py.sisgourmetmobile.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by diego on 18/10/16.
 */

public class AppPreferences {

    private static SharedPreferences mAppPreferences;
    private static final String SHARED_PREFERENCE_APP = "SHARED_PREFERENCE_APP";
    public static final String KEY_PREFERENCE_LOGGED_IN = "KEY_LOGGED_IN";
    public static final String KEY_USER_ID = "KEY_USER_ID";
    public static final String KEY_SYNC_DATA = "KEY_SYNC_DATA";


    public static SharedPreferences getAppPreferences(Context context) {
        if (mAppPreferences == null) {
            mAppPreferences = context.getSharedPreferences(SHARED_PREFERENCE_APP, Context.MODE_PRIVATE);
        }
        return mAppPreferences;
    }

}


