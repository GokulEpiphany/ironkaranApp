package com.ironkaran.ironkaran.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ironkaran.ironkaran.LivePageActivity;
import com.ironkaran.ironkaran.R;
import com.ironkaran.ironkaran.constants.DefaultValueConstants;
import com.ironkaran.ironkaran.interfaces.DetailsInterface;
import com.ironkaran.ironkaran.interfaces.UserInterface;
import com.ironkaran.ironkaran.models.ApartmentDetails;
import com.ironkaran.ironkaran.models.GenericResponse;
import com.ironkaran.ironkaran.models.OrderDetails;
import com.ironkaran.ironkaran.models.PickupWrapper;
import com.ironkaran.ironkaran.models.UserDetails;
import com.ironkaran.ironkaran.preferences.UserDetailPreference;
import com.ironkaran.ironkaran.service.MyFirebaseInstanceIDService;
import com.ironkaran.ironkaran.singletons.ApiManager;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PlaceOrderActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    private Spinner apartmentSpinner;
    private EditText apartmentBlock;
    private EditText apartmentNumber;
    private static int hourToPickup;
    private static int minuteToPickup;
    private Button pickupLater;
    private static String timeBefore;
    private static String timeStarting;

    private static List<Integer> apartmentIds;
    private static List<String> apartmentNames;

    private Button confirmButton;


    private TextView displaySummaryText;
    private EditText numberOfClothes;

    private static String displayTextOne= "Your clothes will be picked up between ";
    private static String displayTextThree =". Our skilled ironkaran will then process your clothes and an invoice will be generated";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        numberOfClothes = (EditText)findViewById(R.id.numberOfClothes);
        Calendar now = Calendar.getInstance();
        hourToPickup = now.get(Calendar.HOUR_OF_DAY);
        minuteToPickup = now.get(Calendar.MINUTE);
        if(hourToPickup<7){
            hourToPickup=7;
            minuteToPickup=30;
        }
        pickupLater = (Button)findViewById(R.id.pickupLater);
        pickupLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();

                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        PlaceOrderActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );

                //Get hour and check
                Timepoint minTimePoint;
                if(now.get(Calendar.HOUR_OF_DAY)>=7){
                    minTimePoint = new Timepoint(now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),0);
                }else{
                    minTimePoint = new Timepoint(7,30,0);
                }
                tpd.setMinTime(minTimePoint);
                tpd.setMaxTime(19,0,0);
                tpd.show(getFragmentManager(), "Pick your time");
            }
        });

        apartmentBlock = (EditText)findViewById(R.id.apartmentBlockEditText);
        apartmentNumber = (EditText)findViewById(R.id.apartmentNumberEditText);
        apartmentSpinner= (Spinner)findViewById(R.id.apartmentSpinner);
        displaySummaryText = (TextView)findViewById(R.id.displaySummaryText);
        Timepoint timepoint = new Timepoint(hourToPickup,minuteToPickup,0);
        timepoint.add(Timepoint.TYPE.MINUTE,30);

        timeBefore = timepoint.toString();
        timeStarting = new Timepoint(hourToPickup,minuteToPickup,0).toString();

        confirmButton = (Button)findViewById(R.id.confirmPickupNew);
        confirmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                int pickupnumber = 0;
                if(numberOfClothes.getText().toString().length()>0) {
                    pickupnumber = Integer.parseInt(numberOfClothes.getText().toString());
                }
                if(pickupnumber<=0){
                    Toast.makeText(PlaceOrderActivity.this,"Please select number of clothes",Toast.LENGTH_SHORT).show();
                    return;
                }

                final UserDetailPreference userDetailPreference = UserDetailPreference.getInstance(getApplicationContext());

                String block = apartmentBlock.getText().toString();
                String number = apartmentNumber.getText().toString();
                userDetailPreference.setApartmentBlock(block);
                userDetailPreference.setApartmentNumber(Integer.parseInt(number));
                int idSelected = apartmentIds.get(apartmentSpinner.getSelectedItemPosition());
                userDetailPreference.setApartmentId(idSelected);

                String address = " Number: "+number+" ,Block: "+block+" Apartment Name: "+apartmentNames.get(apartmentSpinner.getSelectedItemPosition());
                UserDetails userDetails = new UserDetails();
                userDetails.setUserId(userDetailPreference.getUserId());
                userDetails.setAddress(address);
                userDetails.setApartmentId(idSelected);
                userDetails.setName(userDetailPreference.getUserName());
                MyFirebaseInstanceIDService myFirebaseInstanceIDService = new MyFirebaseInstanceIDService(PlaceOrderActivity.this);
                String pushToken = myFirebaseInstanceIDService.getRegId();

                userDetails.setPushToken(pushToken);
                userDetailPreference.setAddress(address);

                OrderDetails orderDetails = new OrderDetails(0l,userDetailPreference.getUserId(),userDetailPreference.getAddress(),pickupnumber,timeBefore,0,0,0,0,0,0,0, DefaultValueConstants.scheduledPickedState,0,"APP",userDetailPreference.getApartmentId());
                Retrofit retrofit = ApiManager.getAdapter();
                UserInterface userInterface = retrofit.create(UserInterface.class);
                PickupWrapper pickupWrapper = new PickupWrapper(orderDetails,userDetails);
                Call<GenericResponse> responseCall = userInterface.initiateOrderFull(pickupWrapper);

                responseCall.enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                        if(!response.body().getError().equals("Error")){
                            userDetailPreference.setIsAddressSet();

                            Toast.makeText(PlaceOrderActivity.this,"Sucess! Pick up scheduled",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PlaceOrderActivity.this, LivePageActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else{
                            Toast.makeText(PlaceOrderActivity.this,"Order failed"+response.body().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GenericResponse> call, Throwable t) {

                    }
                });


            }
        });

        Toast.makeText(this,"You are scheduling pickup at "+hourToPickup+"hrs:"+minuteToPickup+"m ",Toast.LENGTH_LONG).show();
        displaySummaryText.setText(displayTextOne+timeStarting+" - "+timeBefore+displayTextThree);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onResume() {


        final UserDetailPreference userDetailPreference= UserDetailPreference.getInstance(getApplicationContext());
        final boolean addressSet = userDetailPreference.getIsAddressSet();

        super.onResume();

        if(addressSet){
            apartmentNumber.setText(Integer.toString(userDetailPreference.getApartmentNumber()));
            apartmentBlock.setText(userDetailPreference.getApartmentBlock());
        }

        final Retrofit retrofit = ApiManager.getAdapter();
        DetailsInterface detailsInterface = retrofit.create(DetailsInterface.class);
        Call<List<ApartmentDetails>> apartmentDetailsCall = detailsInterface.getAparmentDetails();

        apartmentDetailsCall.enqueue(new Callback<List<ApartmentDetails>>() {
            @Override
            public void onResponse(Call<List<ApartmentDetails>> call, Response<List<ApartmentDetails>> response) {
                List<ApartmentDetails> list = response.body();
                apartmentNames = new ArrayList<String>();
                apartmentIds = new ArrayList<Integer>();
                for(ApartmentDetails temp:list){
                    apartmentIds.add(temp.getApartmentId());
                    apartmentNames.add(temp.getApartmentName());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PlaceOrderActivity.this,
                        android.R.layout.simple_spinner_item, apartmentNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                apartmentSpinner.setAdapter(adapter);

                if(addressSet) {
                    int apartmentId = userDetailPreference.getApartmentId();
                    for (int i = 0; i < apartmentIds.size(); i++) {
                        if(apartmentIds.get(i)==apartmentId){
                            apartmentSpinner.setSelection(i);
                            break;
                        }
                    }
                }



            }

            @Override
            public void onFailure(Call<List<ApartmentDetails>> call, Throwable t) {

            }
        });





    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        hourToPickup = hourOfDay;
        minuteToPickup = minute;
        Timepoint timepoint = new Timepoint(hourToPickup,minuteToPickup,0);
        timepoint.add(Timepoint.TYPE.MINUTE,30);

        timeBefore = timepoint.toString();
        timeStarting = new Timepoint(hourToPickup,minuteToPickup,0).toString();

        Toast.makeText(this,"You are scheduling pickup at "+hourToPickup+"hrs:"+minuteToPickup+"m ",Toast.LENGTH_LONG).show();
        displaySummaryText.setText(displayTextOne+timeStarting+" - "+timeBefore+displayTextThree);
    }
}
