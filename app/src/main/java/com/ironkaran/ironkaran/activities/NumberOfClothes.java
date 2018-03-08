package com.ironkaran.ironkaran.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ironkaran.ironkaran.R;
import com.ironkaran.ironkaran.constants.DefaultValueConstants;
import com.ironkaran.ironkaran.interfaces.UserInterface;
import com.ironkaran.ironkaran.models.GenericResponse;
import com.ironkaran.ironkaran.models.OrderDetails;
import com.ironkaran.ironkaran.models.UserDetails;
import com.ironkaran.ironkaran.preferences.UserDetailPreference;
import com.ironkaran.ironkaran.singletons.ApiManager;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NumberOfClothes extends AppCompatActivity {

    private Button confirmPickup;
    private static int pickupHour;
    private static int pickUpMinute;
    private TextView summaryText;
    private static String timeBefore;
    private static String timeStarting;

    private EditText numberOfClothesToPickup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_of_clothes);
        summaryText = (TextView)findViewById(R.id.summaryText);
        numberOfClothesToPickup = (EditText)findViewById(R.id.numberOfClothesToPickup);

        Intent intent = getIntent();

        pickupHour = intent.getIntExtra(DefaultValueConstants.pickupHour,0);
        pickUpMinute = intent.getIntExtra(DefaultValueConstants.pickupMinute,0);

        Timepoint timepoint = new Timepoint(pickupHour,pickUpMinute,0);
        timepoint.add(Timepoint.TYPE.MINUTE,30);

        timeBefore = timepoint.toString();
        timeStarting = new Timepoint(pickupHour,pickUpMinute,0).toString();
        confirmPickup= (Button)findViewById(R.id.confirmPickup);

    }

    @Override
    protected void onResume() {
        super.onResume();

        String textToDisplay = "On Clicking CONFIRM PICKUP, your clothes will be picked up between "+timeStarting+" - " + timeBefore+" . Our skilled ironkaran will process your clothes and an invoice will be generated later";

        summaryText.setText(textToDisplay);

        confirmPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int pickupnumber = 0;
                if(numberOfClothesToPickup.getText().toString().length()>0) {
                    pickupnumber = Integer.parseInt(numberOfClothesToPickup.getText().toString());
                }
                if(pickupnumber<=3){
                    Snackbar.make(view, "Minimum number of clothes is 3..", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                UserDetailPreference userDetailPreference = UserDetailPreference.getInstance(getApplicationContext());
                OrderDetails orderDetails = new OrderDetails(0l,userDetailPreference.getUserId(),userDetailPreference.getAddress(),pickupnumber,timeBefore,0,0,0,0,0,0,0,DefaultValueConstants.scheduledPickedState,0,"APP",userDetailPreference.getApartmentId());
                Retrofit retrofit = ApiManager.getAdapter();
                UserInterface userInterface = retrofit.create(UserInterface.class);
                Call<GenericResponse> orderDetailsCall = userInterface.initiateOrder(orderDetails);
                orderDetailsCall.enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                        Intent intent = new Intent(NumberOfClothes.this,SuccessActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<GenericResponse> call, Throwable t) {
                        Snackbar.make(findViewById(R.id.rootLayout), "Could not place order. Its our fault. Please call us at 9952188202 to place order", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Action", null).show();

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
