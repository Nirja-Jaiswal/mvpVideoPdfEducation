package com.Padhantueducation.Session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.Padhantueducation.view_section.Login.LoginSection.LoginActivity;


/**
 * Created by Raghvendra Sahu on 04/11/2019.
 */
public class Session extends Object {

    private static final String TAG = Session.class.getSimpleName();
    private static final String PREF_NAME = "Rapidine_pref2";
    private static final String IS_LOGGEDIN = "isLoggedIn";
    private static final String FAV = "fav";
    private static final String Mobile = "mobile";
    private static final String Email = "email";
    private static final String UserId = "user_id";
    private static final String User_name = "user_name";
    private static final String Pro_Image = "pro_img";
    private static final String role_ = "user_role";

    private Context _context;
    private SharedPreferences  Rapidine_pref;
    private SharedPreferences.Editor  editor;
    Session session;

    public Session(Context context) {
        this._context = context;
        Rapidine_pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = Rapidine_pref.edit();
        editor.apply();
    }




    public void setMobile(String mobile, String email) {
        editor.putString(Mobile, mobile);
        editor.putString(Email, email);
        editor.apply();
        editor.commit();
    }

    public String getMobile() {
        return Rapidine_pref.getString(Mobile, "");

    }
    public String getUser_name() {
        return Rapidine_pref.getString(User_name, "");

    }
    public void setUserId(String userId) {
        editor.putString(UserId, userId);
        this.editor.apply();
    }

    public void set_role(String role) {
        editor.putString(role_, role);
        editor.apply();
        editor.commit();
    }

    public String get_role()
    {
        return Rapidine_pref.getString(role_, "");
    }

    public String getUserId() {
        return Rapidine_pref.getString(UserId, "");
    }




    public void setUser_name(String user_name) {
        editor.putString(User_name, user_name);
        this.editor.apply();
    }

    public String getEmail() {
        return Rapidine_pref.getString(Email, "");
    }





    public void logout() {
        editor.clear();
        editor.apply();
        Intent showLogin = new Intent(_context, LoginActivity.class);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(showLogin);
    }




    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGGEDIN, isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return Rapidine_pref.getBoolean(IS_LOGGEDIN, false);
    }






}
