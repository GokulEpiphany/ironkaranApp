package com.ironkaran.ironkaran.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.ironkaran.ironkaran.R;
import com.ironkaran.ironkaran.adapters.CustomHistoryAdapter;
import com.ironkaran.ironkaran.interfaces.DetailsInterface;
import com.ironkaran.ironkaran.models.OrderDetails;
import com.ironkaran.ironkaran.preferences.UserDetailPreference;
import com.ironkaran.ironkaran.singletons.ApiManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HistoryActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = (ListView)findViewById(R.id.historyListView);
        Retrofit retrofit = ApiManager.getAdapter();

        DetailsInterface detailsInterface = retrofit.create(DetailsInterface.class);

        Call<List<OrderDetails>> orderDetailsCall = detailsInterface
                .getAllOrderDetails(UserDetailPreference.getInstance(getApplicationContext()).getUserId());

        orderDetailsCall.enqueue(new Callback<List<OrderDetails>>() {
            @Override
            public void onResponse(Call<List<OrderDetails>> call, Response<List<OrderDetails>> response) {
                    List<OrderDetails> orderDetailsList = response.body();
                    if(orderDetailsList.size()==0){
                        Log.d("TESTINGASS","History TEST EMPTY");

                    }else{

                        ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<OrderDetails>();
                        for(OrderDetails orderDetail:orderDetailsList){
                            orderDetailsArrayList.add(orderDetail);
                        }

                        CustomHistoryAdapter customHistoryAdapter = new CustomHistoryAdapter(orderDetailsArrayList,HistoryActivity.this);
                        listView.setAdapter(customHistoryAdapter);
                    }

            }

            @Override
            public void onFailure(Call<List<OrderDetails>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
