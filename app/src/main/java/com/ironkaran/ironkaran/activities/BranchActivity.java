package com.ironkaran.ironkaran.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ironkaran.ironkaran.LivePageActivity;
import com.ironkaran.ironkaran.R;
import com.ironkaran.ironkaran.preferences.UserDetailPreference;

import java.util.Timer;
import java.util.TimerTask;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BranchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title

        int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);


        final UserDetailPreference userDetailPreference = UserDetailPreference.getInstance(getApplicationContext());
        setContentView(R.layout.activity_branch);
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d("TestingRak",userDetailPreference.getIsNewUser()+"");
                if(userDetailPreference.getIsNewUser()){
                    //take to Register page
                    Intent intent = new Intent(BranchActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }else{
                    //take to live page
                    Intent intent = new Intent(BranchActivity.this,LivePageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }
        },2000);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
