package com.ironkaran.ironkaran.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ironkaran.ironkaran.LivePageActivity;
import com.ironkaran.ironkaran.R;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SuccessActivity extends AppCompatActivity {

    private KonfettiView konfettiView;
    private TextView trackOrderTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        konfettiView = (KonfettiView)findViewById(R.id.viewKonfetti);
        konfettiView.build()
                .addColors(Color.BLUE,getResources().getColor(R.color.colorPrimaryDark))
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(12,5f))
                .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                .stream(150,6000L);

        trackOrderTextView = (TextView)findViewById(R.id.trackOrderTextView);
        trackOrderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessActivity.this, LivePageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
