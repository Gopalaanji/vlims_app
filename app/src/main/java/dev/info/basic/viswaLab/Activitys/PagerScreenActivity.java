package dev.info.basic.viswaLab.Activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import dev.info.basic.viswaLab.Adapters.PagerAdapter;
import dev.info.basic.viswaLab.BaseActivity;
import dev.info.basic.viswaLab.Fragments.PagerFragment;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.pushNotificationServices.GCMRegistrationIntentService;
import dev.info.basic.viswaLab.utils.PricedPropertyApplication;

public class PagerScreenActivity extends BaseActivity {
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    private List<Fragment> fragments;
    LinearLayout start_layout;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    public String token;
    PricedPropertyApplication app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(PagerScreenActivity.this);
        editor = prefs.edit();
        if (!getSharedPreferences("rsr.info.basic.pricedproperty", Context.MODE_PRIVATE).getString("companyid", "").isEmpty()) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else {
            initViews();
        }
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        circleIndicator = (CircleIndicator) findViewById(R.id.indicator);
        start_layout = (LinearLayout) findViewById(R.id.start_layout);
        fragments = new ArrayList<>();
        fragments.clear();
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments));
        circleIndicator.setViewPager(viewPager);
        broadCastReciver();
        start_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScreens();
            }
        });
    }

    private void broadCastReciver() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            //When the broadcast received
//We are sending the broadcast from GCMRegistrationIntentService
            @Override
            public void onReceive(Context context, Intent intent) {
                //If the broadcast has received with success
                //that means device is registered successfully
                if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)) {
                    //Getting the registration token from the intent
                    token = intent.getStringExtra("token");
                    app.token = token;
                    //if the intent is not with success then displaying error messages
                } else if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)) {
                    Toast.makeText(getApplicationContext(), "GCM registration error!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();
                }
            }
        };
        //Checking play sdddddervice is available or not
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        //if play service is not available
        if (ConnectionResult.SUCCESS != resultCode) {
            //If play service is supported but not installed
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                //Displaying message that play service is not installed
                Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());
                //If play service is not supported
            } else {
                Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
            }
        } else {
            //Starting intent to register device
            Intent itent = new Intent(this, GCMRegistrationIntentService.class);
            startService(itent);
        }
    }

    public void startScreens() {
        if (!prefs.getString("userMobileNo", "").isEmpty()) {
            startActivity(new Intent(PagerScreenActivity.this, HomeActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, LoginFragmentActivity.class));
        }
    }

    public Fragment getBundle(int position, int position1, String title, String desc) {
        Fragment fragment = new PagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        bundle.putString("title", title);
        bundle.putString("desc", desc);
        if (position1 != 0) {
            bundle.putInt("pos1", position1);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.pager_screen;
    }
}
