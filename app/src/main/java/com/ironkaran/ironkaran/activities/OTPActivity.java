package com.ironkaran.ironkaran.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.ironkaran.ironkaran.LivePageActivity;
import com.ironkaran.ironkaran.R;
import com.ironkaran.ironkaran.interfaces.UserInterface;
import com.ironkaran.ironkaran.models.OtpDetails;
import com.ironkaran.ironkaran.preferences.UserDetailPreference;
import com.ironkaran.ironkaran.singletons.ApiManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OTPActivity extends AppCompatActivity {
    private Pinview otpData;

    private static int otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otpData = findViewById(R.id.otpData);

        final UserDetailPreference userDetailPreference = UserDetailPreference.getInstance(getApplicationContext());

        Retrofit retrofit = ApiManager.getAdapter();

        UserInterface userInterface = retrofit.create(UserInterface.class);
        Call<OtpDetails> otpDetailsCall = userInterface.sendOtpDetails();

        otpDetailsCall.enqueue(new Callback<OtpDetails>() {
            @Override
            public void onResponse(Call<OtpDetails> call, Response<OtpDetails> response) {
                Log.d("Received OTP","On response call "+response.body().getCurrentOtp());
                otp = response.body().getCurrentOtp();
            }

            @Override
            public void onFailure(Call<OtpDetails> call, Throwable t) {

            }
        });

        otpData.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                Snackbar.make(pinview, "Verifying otp..Please wait..", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

                //Make api calls here or what not
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(OTPActivity.this.getCurrentFocus().getWindowToken(), 0);
                if(otp == Integer.parseInt(pinview.getValue())){
                    Intent intent = new Intent(OTPActivity.this, LivePageActivity.class);
                    userDetailPreference.setIsNewUser(false);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else{
                    Snackbar.make(pinview, "Please enter correct OTP..", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }


            }
        });
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
