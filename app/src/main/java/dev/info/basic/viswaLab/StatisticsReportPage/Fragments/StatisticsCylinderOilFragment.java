package dev.info.basic.viswaLab.StatisticsReportPage.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dev.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import dev.info.basic.viswaLab.ApiInterfaces.ApiInterface;
import dev.info.basic.viswaLab.Fragments.BaseFragment;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.StatisticsReportPage.Adapters.NewStatisticsAdapter;
import dev.info.basic.viswaLab.StatisticsReportPage.Models.NewStatsModel;
import dev.info.basic.viswaLab.utils.Common;
import dev.info.basic.viswaLab.views.TextViewPlus;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Giri Thangellapally on 26-04-2018.
 */
public class StatisticsCylinderOilFragment extends BaseFragment {
    private View rootView;
    private LoginFragmentActivity fragmentActivity;
    ArrayList<NewStatsModel> statsModels;
    private Common common;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private RelativeLayout main_loader;
    NewStatisticsAdapter mStatisticsluboilAdapter;
    RecyclerView mRecyclerView;
    TextView curmnt, premnt, curyear;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_statistics_lube_oil_report, container, false);
        setHasOptionsMenu(true);
        common = new Common();
        fragmentActivity = (LoginFragmentActivity) getActivity();
        fragmentActivity.displayActionBar();
        fragmentActivity.showActionBar();
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();
        fragmentActivity.setActionBarTitle("Sample Statistics");
        fragmentActivity.hideBackActionBar();
        main_loader = (RelativeLayout) rootView.findViewById(R.id.initial_loader);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.mRecyclerView);
        textView = (TextView) rootView.findViewById(R.id.textView);
        textView.setText("CLO Statistics");
        curmnt = (TextViewPlus) rootView.findViewById(R.id.curmnt);
        premnt = (TextViewPlus) rootView.findViewById(R.id.premnt);
        curyear = (TextViewPlus) rootView.findViewById(R.id.curyear);
        fetchDetails();
        return rootView;
    }

    private void fetchDetails() {
        main_loader.setVisibility(View.VISIBLE);
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        Log.v("xxx", prefs.getString("userid", ""));
        apiInterface.GetStatisticsCylinderOilReports(prefs.getString("userid", ""), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                if (jsonObject != null) {
                    main_loader.setVisibility(View.GONE);
                    statsModels = new Gson().fromJson(jsonObject.getAsJsonArray("ReportData"), new TypeToken<List<NewStatsModel>>() {
                    }.getType());

                    if (statsModels != null) {
                        renderData();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                main_loader.setVisibility(View.GONE);
showToast();            }
        });

    }

    private void renderData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        mRecyclerView.setLayoutManager(layoutManager);
        if (statsModels != null) {
            mStatisticsluboilAdapter = new NewStatisticsAdapter(getActivity(), statsModels);
            mRecyclerView.setAdapter(mStatisticsluboilAdapter);
        } else {
            mRecyclerView.removeAllViews();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                fragmentActivity.loadPopBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
