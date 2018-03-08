package com.ironkaran.ironkaran;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.ironkaran.ironkaran.activities.FAQActivity;
import com.ironkaran.ironkaran.activities.FeedbackActivity;
import com.ironkaran.ironkaran.activities.HistoryActivity;
import com.ironkaran.ironkaran.activities.PlaceOrderActivity;
import com.ironkaran.ironkaran.activities.PriceActivity;
import com.ironkaran.ironkaran.activities.ScheduleActivity;
import com.ironkaran.ironkaran.constants.DefaultValueConstants;
import com.ironkaran.ironkaran.interfaces.DetailsInterface;
import com.ironkaran.ironkaran.models.OrderDetails;
import com.ironkaran.ironkaran.preferences.UserDetailPreference;
import com.ironkaran.ironkaran.singletons.ApiManager;

import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LivePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView currentStatusText;
    private TextView secondLabel;
    private TextView secondText;
    private TextView thirdLabel;
    private TextView thirdText;
    private TextView fourthText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentStatusText = (TextView)findViewById(R.id.currentStatusText);
        secondLabel = (TextView)findViewById(R.id.secondLabel);
        secondText = (TextView)findViewById(R.id.secondText);
        thirdLabel = (TextView)findViewById(R.id.thirdLabel);
        thirdText = (TextView)findViewById(R.id.thirdText);
        fourthText = (TextView)findViewById(R.id.fourthText);
        fourthText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LivePageActivity.this, PriceActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                int todayDay = calendar.get(Calendar.DAY_OF_WEEK);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);

                if(todayDay == 3){
                    Snackbar.make(view, "Sorry! Tuesday is Ironkarans' day off. Please be back tomorrow", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                    return;
                }

                if(hour>=19){
                    Snackbar.make(view, "Sorry! We are done for today!\nScheduling for tomorrow starts at midnight", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                Log.d("Calendar day","Today day "+todayDay+"hour is "+hour);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Retrofit retrofit = ApiManager.getAdapter();
                DetailsInterface detailsInterface = retrofit.create(DetailsInterface.class);
                Call<OrderDetails> detailsCall = detailsInterface.getCurrentOrderDetails(UserDetailPreference.getInstance(getApplicationContext()).getUserId());

                detailsCall.enqueue(new Callback<OrderDetails>() {
                    @Override
                    public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                        OrderDetails orderDetails = response.body();
                        if(orderDetails.getUserId()==0){
                            Intent intent = new Intent(LivePageActivity.this, PlaceOrderActivity.class);
                            startActivity(intent);
                        }else{
                            Snackbar.make(view, "Sorry. You already have one active order. \nIf you have no active orders, please call us", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderDetails> call, Throwable t) {
                        Snackbar.make(view, "Please check your internet \nPlease call us if issue persists", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                });


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onResume() {
        Log.d("IRONKARAN","CMES");
        super.onResume();

        UserDetailPreference userDetailPreference = UserDetailPreference.getInstance(getApplicationContext());
        long userId = userDetailPreference.getUserId();

        Retrofit retrofit = ApiManager.getAdapter();
        DetailsInterface detailsInterface = retrofit.create(DetailsInterface.class);
        Call<OrderDetails> detailsCall = detailsInterface.getCurrentOrderDetails(userId);

        detailsCall.enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                OrderDetails orderDetails = response.body();
                Log.d("TESTINGTAKA",orderDetails.toString()+" TESTINGTAKA");
                if(orderDetails.getUserId()==0){
                    Log.d("Testing","No live orders");
                    currentStatusText.setText("No live orders yet");
                    secondLabel.setText("Pick up timings");
                    secondText.setText("7:30 AM to 7 PM");
                    thirdLabel.setText("Holidays");
                    thirdText.setText("Tuesday");
                }else{
                    Log.d("TESTING2","IRONKARA "+orderDetails.getProcessState());
                    updateView(orderDetails);
                }
            }

            @Override
            public void onFailure(Call<OrderDetails> call, Throwable t) {
                Log.d("TESTINGTAKA"," TESTINGTAKA2");

            }
        });

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent;
        switch (id){
            case R.id.nav_history:
                intent = new Intent(LivePageActivity.this, HistoryActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_prices:
                intent = new Intent(LivePageActivity.this,PriceActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_feedback:
                intent = new Intent(LivePageActivity.this, FeedbackActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_rate_us:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ironkaran.com"));
                startActivity(browserIntent);
                break;

            default:break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void updateView(final OrderDetails orderDetails){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(orderDetails.getProcessState().equals(DefaultValueConstants.scheduledPickedState)){
                    currentStatusText.setText("Pick up scheduled");
                    secondLabel.setText("Expected pickup");
                    secondText.setText("Before "+orderDetails.getPickupBefore());
                    thirdLabel.setText("Number of clothes scheduled");
                    thirdText.setText(""+orderDetails.getNumberOfClothes());
                }
                if(orderDetails.getProcessState().equals(DefaultValueConstants.state_pickedup)){
                    currentStatusText.setText("Picked up");
                    secondLabel.setText("Price");
                    secondText.setText("Yet to process");
                    thirdLabel.setText("Number of clothes given");
                    thirdText.setText(""+orderDetails.getNumberOfClothes());
                }

                if(orderDetails.getProcessState().equals(DefaultValueConstants.state_processed)){
                    currentStatusText.setText("Processed.");
                    secondLabel.setText("Price");
                    secondText.setText(""+orderDetails.getPrice());
                    thirdLabel.setText("Breakdown");
                    thirdText.setText("R:"+orderDetails.getCategoryA()+" C:"+orderDetails.getCategoryB()+" S:"+orderDetails.getCategoryC());
                }
            }
        });
    }
}
