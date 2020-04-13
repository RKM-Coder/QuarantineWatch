package com.goalsr.homequarantineTracker.Utils;

import android.content.Context;
import androidx.preference.PreferenceManager;


/**
 * Created by ramkrishna on 26/6/18.
 */

public class PreferenceStore {

    public static final String ISUPDATEPATENTINFO = "isupdatepatientinfo";
    public static final String DISTRICT_ID = "district_re_id";
    public static final String DISTRICT_NAME ="district_name" ;
    private static PreferenceStore preferenceHelperInstance = new PreferenceStore();

    public static final String MY_GUEST_ID = "MYGUESTID";
    public static final String LOGIN = "loginstatus";
    public static final String KEY_LOGIN = "keylogin";
    public static final String AUTH_TOKEN = "AUTHTOKEN";
    public static final String FIREBASE_TOKEN = "FIREBASETOKEN";
    public static final String FIREBASE_APPID = "FIREBASETOKENAAID";
    public static final String CITIZEN_ID = "UserID";
    public static final String USER_ID_login = "UserID_login";
    public static final String USER_HOME_LAT = "UserIDlat";
    public static final String USER_HOME_LNG = "UserIDlng";
    public static final String ROLL_ID = "roll_id";
    public static final String PRODUCT_CATEGORY = "product_category";
    public static final String NDH_HOME = "ndh_home";
    public static final String ETAGCAT = "Etagcat";
    public static final String USER_IMAGE = "UserImage";
    public static final String ETAGCOLOR = "Etagcolor";
    public static final String ETAGCOUNTRY = "Etagcountry";
    public static final String USER_EMAIL = "email";
    public static final String USER_PHONE = "phone";
    public static final String USER_PHONE_next = "phone_next";
    public static final String USER_NAME = "name";

    public static final String USER_LATITUDE = "current_user_latitude";
    public static final String USER_LONGITUDE = "current_user_longitude";
    public static final String USER_RADIOUS = "current_user_rad";
    public static final String USER_DATE = "current_date";
    public static final String LAST_USER_STATUSUPDATE_DATE = "current_date_time";
    public static final String LAST_OUTOFRADIOUS= "current_date_time_radiourrangeout";

    private PreferenceStore() {
    }

    public static PreferenceStore getPrefernceHelperInstace() {

        return preferenceHelperInstance;
    }

    public void setString(Context appContext, String key, String value) {

        PreferenceManager.getDefaultSharedPreferences(appContext).edit()
                .putString(key, value).apply();
    }

    public void setIntValue(Context appContext, String key, int value) {

        PreferenceManager.getDefaultSharedPreferences(appContext).edit()
                .putInt(key, value).apply();
    }

    public Boolean isFirstTime(Context appContext, String key){
        return PreferenceManager.getDefaultSharedPreferences(appContext)
                .getBoolean(key,true);
    }

    public void setFirstTime(Context appContext, String key, Boolean value){
        PreferenceManager.getDefaultSharedPreferences(appContext).edit()
                .putBoolean(key, value).apply();
    }


    public void clearValue(Context appContext, String key) {

        PreferenceManager.getDefaultSharedPreferences(appContext).edit().remove(key).apply();
    }
    public String getString(Context appContext, String key) {

        return PreferenceManager.getDefaultSharedPreferences(appContext)
                .getString(key, "");
    }

    public int getIntValue(Context appContext, String key) {

        return PreferenceManager.getDefaultSharedPreferences(appContext)
                .getInt(key, 0);
    }
    public void setFlag(Context appContext, String key, boolean value) {

        PreferenceManager.getDefaultSharedPreferences(appContext).edit()
                .putBoolean(key, value).apply();
    }

    public boolean getFlag(Context appContext, String key) {

        return PreferenceManager.getDefaultSharedPreferences(appContext)
                .getBoolean(key, false);
    }


}
