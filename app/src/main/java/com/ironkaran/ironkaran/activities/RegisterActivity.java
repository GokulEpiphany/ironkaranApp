package com.ironkaran.ironkaran.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.ironkaran.ironkaran.R;
import com.ironkaran.ironkaran.interfaces.UserInterface;
import com.ironkaran.ironkaran.models.GenericResponse;
import com.ironkaran.ironkaran.models.UserDetails;
import com.ironkaran.ironkaran.preferences.UserDetailPreference;
import com.ironkaran.ironkaran.singletons.ApiManager;

import java.io.IOException;

import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity {

    private static FButton fButton;

    private EditText registerPhoneNumber;
    private EditText registerName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fButton = findViewById(R.id.verifybutton);
        fButton.setButtonColor(getResources().getColor(R.color.colorPrimaryDark));
        fButton.setShadowHeight(0);
        fButton.setShadowEnabled(false);

        registerName = (EditText)findViewById(R.id.registerName);
        registerPhoneNumber=(EditText)findViewById(R.id.registerPhoneNumber);



        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name=registerName.getText().toString();
                final String numberAsString= registerPhoneNumber.getText().toString();
                if(numberAsString.length()!=10){
                    Snackbar.make(view, "Please enter valid number", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                UserDetails temp = new UserDetails();
                temp.setName(name);
                temp.setUserId(Long.parseLong(numberAsString));


                Snackbar.make(view, "Registering..Please wait..", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                Retrofit retrofit = ApiManager.getAdapter();

                UserInterface userService = retrofit.create(UserInterface.class);

                Call<GenericResponse> call = userService.saveUserDetails(temp);

                call.enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                        GenericResponse responseReceived = response.body();
                        if(responseReceived.getMessage().equals("ROW_ADDED")) {
                            UserDetailPreference.getInstance(getApplicationContext()).setUserId(Long.parseLong(numberAsString));
                            UserDetailPreference.getInstance(getApplicationContext()).setUserName(name);
                            Intent intent = new Intent(RegisterActivity.this, OTPActivity.class);

                            startActivity(intent);
                        }else{
                            Log.d("TEMP","Already registered");
                        }

                    }

                    @Override
                    public void onFailure(Call<GenericResponse> call, Throwable t) {

                    }
                });





            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

