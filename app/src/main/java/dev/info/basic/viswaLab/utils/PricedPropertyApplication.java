package dev.info.basic.viswaLab.utils;

import android.app.Application;

/**
 * Created by RSR on 02-09-2017.
 */

public class PricedPropertyApplication extends Application {
    public String token;
    public String user_name;
    public String login_user_name;
    public String phone_number;
    public static final String TAG = PricedPropertyApplication.class
            .getSimpleName();
    private static PricedPropertyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized PricedPropertyApplication getInstance() {
        return mInstance;
    }
}
