package rsr.info.basic.viswaLab.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rsr.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import rsr.info.basic.viswaLab.Adapters.StatisticsluboilAdapter;
import rsr.info.basic.viswaLab.ApiInterfaces.ApiInterface;
import rsr.info.basic.viswaLab.R;
import rsr.info.basic.viswaLab.models.SampleStatsModel;
import rsr.info.basic.viswaLab.utils.Common;

/**
 * Created by ${GIRI} on 12-01-2018.
 */

public class StatisticsLubeOilReportsFragment extends BaseFragment {
    private View rootView;
    private LoginFragmentActivity fragmentActivity;
    ArrayList<SampleStatsModel> statsModels;
    private Common common;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private RelativeLayout main_loader;
    StatisticsluboilAdapter mStatisticsluboilAdapter;
    RecyclerView mRecyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

        fetchDetails();

        return rootView;
    }

    private void fetchDetails() {

        main_loader.setVisibility(View.VISIBLE);
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.HeadUrl).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        Log.v("xxx", prefs.getString("userid", ""));
        apiInterface.GetSampleSummaryReport(prefs.getString("userid", ""), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                if (jsonObject != null) {
                    main_loader.setVisibility(View.GONE);
                    statsModels = new Gson().fromJson(jsonObject.getAsJsonArray("SummaryDetails"), new TypeToken<List<SampleStatsModel>>() {
                    }.getType());

                    if (statsModels != null) {
                        renderData();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                main_loader.setVisibility(View.GONE);
                common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, getString(R.string.something_went_wrong));
            }
        });

    }

    private void renderData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        mRecyclerView.setLayoutManager(layoutManager);
        if (statsModels != null) {
            mStatisticsluboilAdapter = new StatisticsluboilAdapter(getActivity(), statsModels);
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
