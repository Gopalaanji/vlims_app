package dev.info.basic.viswaLab.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dev.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import dev.info.basic.viswaLab.AnalysisReportsPage.Fragments.AnalysisReportFragment;
import dev.info.basic.viswaLab.ApiInterfaces.ApiInterface;
import dev.info.basic.viswaLab.CautionAlertsReportsPage.CautionAlertAnalysisReport;
import dev.info.basic.viswaLab.Database.helper;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.StatisticsReportPage.Fragments.StatisticsReportFragment;
import dev.info.basic.viswaLab.models.ShipdetailsModel;
import dev.info.basic.viswaLab.services.TimeService;
import dev.info.basic.viswaLab.utils.Common;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static dev.info.basic.viswaLab.utils.PricedPropertyApplication.TAG;

/**
 * Created by E5000096 on 02-10-2016.
 */
public class ViswaLabDashboard extends BaseFragment implements View.OnClickListener {
    private static final int PERMISSION_REQUEST_CODE = 537;
    private View rootView;
    private Common common;
    public LoginFragmentActivity fragmentActivity;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    helper dbHelper;
    ArrayList<ShipdetailsModel> ShipdetailsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.shit, container, false);
        setHasOptionsMenu(false);
        fragmentActivity = (LoginFragmentActivity) getActivity();
        common = new Common();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = prefs.edit();
        fragmentActivity.startService((new Intent(getContext(), TimeService.class)));
        LinearLayout btnAnalysisReport = (LinearLayout) rootView.findViewById(R.id.btnAnalysisReport);
        btnAnalysisReport.setOnClickListener(this);
        LinearLayout btnStatisticsReports = (LinearLayout) rootView.findViewById(R.id.btnStatisticsReports);
        btnStatisticsReports.setOnClickListener(this);
        LinearLayout btncautionAlerts = (LinearLayout) rootView.findViewById(R.id.btncautionAlerts);
        btncautionAlerts.setOnClickListener(this);
        LinearLayout btnShceduleAlerts = (LinearLayout) rootView.findViewById(R.id.btnShceduleAlerts);
        btnShceduleAlerts.setOnClickListener(this);
        LinearLayout btnTechnicalUpdates = (LinearLayout) rootView.findViewById(R.id.btnTechnicalUpdates);
        btnTechnicalUpdates.setOnClickListener(this);
        LinearLayout btnAdhocreports = (LinearLayout) rootView.findViewById(R.id.btnAdhocreports);
        btnAdhocreports.setOnClickListener(this);
        LinearLayout btnContactus = (LinearLayout) rootView.findViewById(R.id.btnContactus);
        btnContactus.setOnClickListener(this);
        LinearLayout btnForms = (LinearLayout) rootView.findViewById(R.id.btnForms);
        btnForms.setOnClickListener(this);

        helper.getInstance(getActivity());
        dbHelper = new helper(getActivity());
       /* if (prefs.getString("userid", "") != null && dbHelper.getAllShipDetails().size() == 0) {
            dbHelper.deleteAllShips();
            getUserShipDetails(prefs.getString("userid", ""));
        }*/

        fragmentActivity.setActionBarTitle("Viswa Lab");
        fragmentActivity.showActionBar();
        fragmentActivity.hideBackActionBar();

        if (hasFilePermission()) {
        } else {
            requestFilePermission();
        }
        return rootView;
    }
    private boolean hasFilePermission() {
        int result = 0;
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        for (String perm : permissions) {
            result = getActivity().checkCallingOrSelfPermission(perm);
            if (!(result == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }
    private void requestFilePermission() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }
    }





   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean video_allowed = true;
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                for (int res : grantResults)
                    video_allowed = video_allowed && (res == PackageManager.PERMISSION_GRANTED);
                if (video_allowed) {

                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            Toast.makeText(getContext(), "FILE PERMISIONS IGNORED", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;

            default:
                break;
        }
    }

    private void getUserShipDetails(String userid) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        apiInterface.GetUserShipDetails("808", new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
                Log.v("RESPONSE==>", response_data_obj.toString());
                if (response_data_obj != null) {
                    ShipdetailsList = new Gson().fromJson(response_data_obj.getAsJsonArray("Shipdetails"), new TypeToken<List<ShipdetailsModel>>() {
                    }.getType());
                    progressDialog.cancel();
                    if (ShipdetailsList != null) {
                        for (int i = 0; i < ShipdetailsList.size(); i++) {
                            dbHelper.AddShipsData(ShipdetailsList.get(i));
                        }
                        Log.v("SHIPS_COUNT", dbHelper.getAllShipDetails().size() + "");
                    }

                } else {
                    showToast(getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void failure(RetrofitError error) {
showToast();            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAnalysisReport:
                fragmentActivity.replaceFragment(new AnalysisReportFragment(), "agent_signup", null);
                break;
            case R.id.btnStatisticsReports:
                fragmentActivity.replaceFragment(new StatisticsReportFragment(), "agent_signup", null);
                break;
            case R.id.btncautionAlerts:
                fragmentActivity.replaceFragment(new CautionAlertAnalysisReport(), "agent_signup", null);
                break;
            case R.id.btnShceduleAlerts:
                fragmentActivity.replaceFragment(new ScheduleAlertsFragment(), "agent_signup", null);
                break;
            case R.id.btnTechnicalUpdates:
                fragmentActivity.replaceFragment(new TechnicalUpdatesFragment(), "agent_signup", null);
                break;
            case R.id.btnAdhocreports:
                fragmentActivity.replaceFragment(new AdhocReportsFragment(), "agent_signup", null);
                break;
            case R.id.btnContactus:
                fragmentActivity.replaceFragment(new ContactUsFragment(), "agent_signup", null);
                break;
            case R.id.btnForms:
                fragmentActivity.replaceFragment(new FormsFragment(), "agent_signup", null);
                break;
        }

    }
}
