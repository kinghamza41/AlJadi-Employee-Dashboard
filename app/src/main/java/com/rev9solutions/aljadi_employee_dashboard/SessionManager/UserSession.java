package com.rev9solutions.aljadi_employee_dashboard.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

public  class UserSession {
    private final String sharedprofileName = "haccount";
    SharedPreferences preferences;
    private final Context _context;
    private UserSession instance;
    private SharedPreferences.Editor editor;

    public  UserSession(Context context) {
        this._context = context;
    }
    public void SaveKeyValue(String key, String value) {

        preferences = _context.getSharedPreferences(sharedprofileName, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).apply();
       // preferences.edit().putBoolean("KEY_FLAG", false).apply();
    }

    public void setFlag(Boolean flag) {
        preferences = _context.getSharedPreferences(sharedprofileName, Context.MODE_PRIVATE);
        preferences.edit().putBoolean("KEY_FLAG", flag).apply();
       // this.editor.commit();
    }

    public boolean getFlag() {
        preferences = _context.getSharedPreferences(sharedprofileName, Context.MODE_PRIVATE);
        return preferences.getBoolean("KEY_FLAG", false);
    }
    public  String GetKeyValue(String key) {

        SharedPreferences preferences = _context.getSharedPreferences(sharedprofileName, Context.MODE_PRIVATE);
       return preferences.getString(key, null);
    }
    public void SaveCredentials(String token) {

        SaveKeyValue("access_token", token);
//return SaveCredentials(token);
    }


}
