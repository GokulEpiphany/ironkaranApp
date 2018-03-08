package com.ironkaran.ironkaran.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gokulakrishnanm on 21/02/18.
 */

public class UserDetailPreference {

    public static final String PREFS_NAME = "USER_DETAIL";

    public static final String isNewUser = "IS_NEW_USER";

    public static final String userId = "PHONE_NUMBER";

    public static final String userName="User_name";
    public static final String userPushToken = "PUSH_TOKEN";

    public static final String apartmentBlock="Apartment_block";
    public static final String apartmentNumber="Apartment_number";
    public static final String apartmentId ="Apartment_id";
    public static final String address = "ADDRESS";


    public static final String addressAlreadySet = "IS_ADDRESS_SET";


    private static Context mContext;

    private static final UserDetailPreference ourInstance = new UserDetailPreference();

    public static UserDetailPreference getInstance(Context context) {
        mContext = context;
        return ourInstance;
    }

    private UserDetailPreference() {
    }

    public void setIsNewUser(boolean userStatus){
        SharedPreferences temp = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = temp.edit();
        editor.putBoolean(isNewUser,userStatus);
        editor.commit();
    }

    public boolean getIsNewUser(){
        SharedPreferences temp = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return temp.getBoolean(isNewUser,true);
    }

    public void setUserId(long phonenumber){
        SharedPreferences temp = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = temp.edit();
        editor.putLong(userId,phonenumber);
        editor.commit();

    }

    public long getUserId(){
        SharedPreferences temp = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return temp.getLong(userId,0l);
    }

    public void setIsAddressSet(){
        SharedPreferences temp = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = temp.edit();
        editor.putBoolean(addressAlreadySet,true);
        editor.commit();
    }

    public boolean getIsAddressSet(){
        SharedPreferences temp = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return temp.getBoolean(addressAlreadySet,false);

    }

    public void setApartmentBlock(String apartmentBlockGot){
        SharedPreferences temp = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = temp.edit();
        editor.putString(apartmentBlock,apartmentBlockGot);
        editor.commit();
    }

    public String getApartmentBlock(){
        SharedPreferences temp = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return temp.getString(apartmentBlock,"NotGiven");

    }


    public void setAddress(String addressGot){
        SharedPreferences temp = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = temp.edit();
        editor.putString(address,addressGot);
        editor.commit();
    }

    public String getAddress(){
        SharedPreferences temp = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return temp.getString(address,"NotGiven");

    }

    public void setUserPushToken(String pushToken){
        SharedPreferences temp = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = temp.edit();
        editor.putString(userPushToken,pushToken);
        editor.commit();
    }

    public String getUserPushToken(){
        SharedPreferences temp = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return temp.getString(userPushToken,"NotGiven");

    }


    public void setUserName(String name){
        SharedPreferences temp = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = temp.edit();
        editor.putString(userName,name);
        editor.commit();
    }

    public String getUserName(){
        SharedPreferences temp = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return temp.getString(userName,"NotGiven");

    }




    public void setApartmentNumber(int apartmentNumberGot){
        SharedPreferences temp = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = temp.edit();
        editor.putInt(apartmentNumber,apartmentNumberGot);
        editor.commit();
    }

    public int getApartmentNumber(){
        SharedPreferences temp = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return temp.getInt(apartmentNumber,9999);
    }

    public void setApartmentId(int apartmentIdGot){
        SharedPreferences temp = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = temp.edit();
        editor.putInt(apartmentId,apartmentIdGot);
        editor.commit();
    }

    public int getApartmentId(){
        SharedPreferences temp = mContext.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return temp.getInt(apartmentId,9999);
    }

}
