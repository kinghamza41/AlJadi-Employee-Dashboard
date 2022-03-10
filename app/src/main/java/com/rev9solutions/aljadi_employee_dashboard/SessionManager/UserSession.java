package com.rev9solutions.aljadi_employee_dashboard.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;

public  class UserSession {
    private final String sharedprofileName = "haccount";
    private final Context _context;
    private UserSession instance;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    public  UserSession(Context context) {
        this._context = context;
    }
    public void setFlag(Boolean flag) {
        this.editor.putBoolean("KEY_FLAG", flag);
        this.editor.commit();
    }

    public boolean getFlag() {
        return this.sharedPreferences.getBoolean("KEY_FLAG", false);
    }

    public void setCurrentTime(String currentTime) {
        this.editor.putString("KEY_TIME", currentTime);
        this.editor.commit();
    }

    public String getCurrentTime() {
        return this.sharedPreferences.getString("KEY_TIME", "");
    }

    public void SaveKeyValue(String key, String value) {

        SharedPreferences preferences = _context.getSharedPreferences(sharedprofileName, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).apply();
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
