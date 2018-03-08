package com.ironkaran.ironkaran.application;

import android.app.Application;

import com.ironkaran.ironkaran.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by gokulakrishnanm on 21/02/18.
 */

public class IronkaranApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Montserrat-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
