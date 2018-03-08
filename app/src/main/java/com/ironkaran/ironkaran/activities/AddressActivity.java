package com.ironkaran.ironkaran.activities;

import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ironkaran.ironkaran.R;
import com.ironkaran.ironkaran.constants.DefaultValueConstants;
import com.ironkaran.ironkaran.interfaces.DetailsInterface;
import com.ironkaran.ironkaran.interfaces.UserInterface;
import com.ironkaran.ironkaran.models.ApartmentDetails;
import com.ironkaran.ironkaran.models.GenericResponse;
import com.ironkaran.ironkaran.models.UserDetails;
import com.ironkaran.ironkaran.preferences.UserDetailPreference;
import com.ironkaran.ironkaran.service.MyFirebaseInstanceIDService;
import com.ironkaran.ironkaran.singletons.ApiManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddressActivity extends AppCompatActivity {

    private Button addAddress;
    private Spinner apartmentSpinner;
    private EditText apartmentBlock;
    private EditText apartmentNumber;
    private static List<Integer> apartmentIds;
    private static List<String> apartmentNames;
    private static int pickupHour;
    private  static int pickupMinute;
    private static int apartmentIdSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        addAddress = (Button)findViewById(R.id.addAddress);
        apartmentBlock = (EditText)findViewById(R.id.apartmentBlockEditText);
        apartmentNumber = (EditText)findViewById(R.id.apartmentNumberEditText);
        apartmentSpinner= (Spinner)findViewById(R.id.apartmentSpinner);

        Intent intent=getIntent();
        pickupHour = intent.getIntExtra(DefaultValueConstants.pickupHour,0);
        pickupMinute = intent.getIntExtra(DefaultValueConstants.pickupMinute,0);

    }

    @Override
    protected void onResume() {
        super.onResume();

        final UserDetailPreference userDetailPreference= UserDetailPreference.getInstance(getApplicationContext());
        final boolean addressSet = userDetailPreference.getIsAddressSet();

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

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddressActivity.this,
                        android.R.layout.simple_spinner_item, apartmentNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                apartmentSpinner.setAdapter(adapter);

                if(addressSet) {
                    int apartmentId = userDetailPreference.getApartmentId();
                    for (int i = 0; i < apartmentIds.size(); i++) {
                        if(apartmentIds.get(i)==apartmentId){
                            apartmentSpinner.setSelection(i);
                            apartmentIdSelected = apartmentIds.get(i);
                            break;
                        }
                    }
                }



            }

            @Override
            public void onFailure(Call<List<ApartmentDetails>> call, Throwable t) {

            }
        });

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                MyFirebaseInstanceIDService myFirebaseInstanceIDService = new MyFirebaseInstanceIDService(AddressActivity.this);
                String pushToken = myFirebaseInstanceIDService.getRegId();

                userDetails.setPushToken(pushToken);
                userDetailPreference.setAddress(address);
                UserInterface userInterface = retrofit.create(UserInterface.class);
                Call<GenericResponse> updateAddressCall = userInterface.updateAddress(userDetails);
                updateAddressCall.enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                        userDetailPreference.setIsAddressSet();
                        Intent intent = new Intent(AddressActivity.this,NumberOfClothes.class);
                        intent.putExtra(DefaultValueConstants.pickupHour,pickupHour);
                        intent.putExtra(DefaultValueConstants.pickupMinute,pickupMinute);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<GenericResponse> call, Throwable t) {
                        Toast.makeText(AddressActivity.this,"Something went wrong. Sorry!",Toast.LENGTH_SHORT).show();

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
