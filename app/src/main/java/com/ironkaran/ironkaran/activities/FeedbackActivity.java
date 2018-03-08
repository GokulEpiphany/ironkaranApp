package com.ironkaran.ironkaran.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ironkaran.ironkaran.R;
import com.ironkaran.ironkaran.interfaces.DetailsInterface;
import com.ironkaran.ironkaran.models.GenericResponse;
import com.ironkaran.ironkaran.preferences.UserDetailPreference;
import com.ironkaran.ironkaran.singletons.ApiManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FeedbackActivity extends AppCompatActivity {

    private Button sendFeedback;
    private EditText feedbackEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedbackEditText = (EditText)findViewById(R.id.feedbackEditText);

        sendFeedback = (Button)findViewById(R.id.sendFeedback);
        sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = feedbackEditText.getText().toString();
                long userId = UserDetailPreference.getInstance(getApplicationContext()).getUserId();
                if(message.length()>0){
                    Retrofit retrofit = ApiManager.getAdapter();
                    DetailsInterface detailsInterface = retrofit.create(DetailsInterface.class);
                    Call<GenericResponse> feedbackCall = detailsInterface.saveFeedback(userId,message);
                    feedbackCall.enqueue(new Callback<GenericResponse>() {
                        @Override
                        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                            Toast.makeText(FeedbackActivity.this,"Thanks for the feedback",Toast.LENGTH_LONG).show();
                            FeedbackActivity.this.finish();
                        }


                        @Override
                        public void onFailure(Call<GenericResponse> call, Throwable t) {

                        }
                    });

                }

            }
        });
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
