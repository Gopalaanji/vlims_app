package dev.info.basic.viswaLab.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dev.info.basic.viswaLab.ApiInterfaces.ApiInterface;
import dev.info.basic.viswaLab.Database.helper;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.models.ShipdetailsModel;
import dev.info.basic.viswaLab.utils.Common;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SplashScreen extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2500;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    ArrayList<ShipdetailsModel> ShipdetailsList;
    private Common common;
    helper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        prefs = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
        editor = prefs.edit();
        common = new Common();
        helper.getInstance(SplashScreen.this);
        dbHelper = new helper(SplashScreen.this);

     /*   Intent i = new Intent(SplashScreen.this, LoginFragmentActivity.class);
        startActivity(i);
        finish();*/




        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, LoginFragmentActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (prefs.getString("userid", "") != null&&!prefs.getString("userid", "").isEmpty()&&!prefs.getString("userid", "").equals("")) {
//            dbHelper.deleteAllShips();
//            getUserShipDetails(prefs.getString("userid", ""));
//        }else{
//            Intent i = new Intent(SplashScreen.this, LoginFragmentActivity.class);
//            startActivity(i);
//            finish();
//        }
//    }

    private void getUserShipDetails(String userid) {
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        apiInterface.GetUserShipDetails(userid, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
               // Log.v("RESPONSE==>", response_data_obj.toString());
                if (response_data_obj != null) {
                    ShipdetailsList = new Gson().fromJson(response_data_obj.getAsJsonArray("Shipdetails"), new TypeToken<List<ShipdetailsModel>>() {
                    }.getType());
                    if (ShipdetailsList != null) {
                        for (int i = 0; i < ShipdetailsList.size(); i++) {
                            dbHelper.AddShipsData(ShipdetailsList.get(i));
                        }
                       // Log.v("SHIPS_COUNT", dbHelper.getAllShipDetails().size()+"");
                        Intent i = new Intent(SplashScreen.this, LoginFragmentActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(SplashScreen.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(SplashScreen.this,getString(R.string.something_went_wrong),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                    Toast.makeText(SplashScreen.this,"Something Went Wrong!!",Toast.LENGTH_LONG).show();

            }
        });

    }
}
