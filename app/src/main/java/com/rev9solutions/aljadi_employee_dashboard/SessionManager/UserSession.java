package com.rev9solutions.aljadi_employee_dashboard.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;

public  class UserSession {
    private final String sharedprofileName = "haccount";
    private final Context _context;
    private UserSession instance;

    public  UserSession(Context context) {
        this._context = context;
    }

    public void SaveKeyValue(String key, String value) {

        SharedPreferences preferences = _context.getSharedPreferences(sharedprofileName, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).apply();
    }

    public String GetKeyValue(String key) {

        SharedPreferences preferences = _context.getSharedPreferences(sharedprofileName, Context.MODE_PRIVATE);
        return preferences.getString(key, null);


    }

    public void SaveCredentials(String token) {

        SaveKeyValue("access_token", token);
//return SaveCredentials(token);
    }


}
