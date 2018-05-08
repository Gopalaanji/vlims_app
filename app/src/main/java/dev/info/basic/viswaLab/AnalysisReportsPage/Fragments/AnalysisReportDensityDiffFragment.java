package dev.info.basic.viswaLab.AnalysisReportsPage.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dev.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import dev.info.basic.viswaLab.AnalysisReportsPage.Adapters.AnalysisDensitytAdapter;
import dev.info.basic.viswaLab.AnalysisReportsPage.models.AnalysisDensityModel;
import dev.info.basic.viswaLab.AnalysisReportsPage.models.AnalysisReportDensityDataModel;
import dev.info.basic.viswaLab.ApiInterfaces.ApiInterface;
import dev.info.basic.viswaLab.Fragments.BaseFragment;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.models.ReportDataModel;
import dev.info.basic.viswaLab.utils.Common;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Giri Thangellapally on 23-04-2018.
 */
public class AnalysisReportDensityDiffFragment extends BaseFragment {
    private LoginFragmentActivity fragmentActivity;
    private View rootView;
    private Common common;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private RelativeLayout main_loader;
    ArrayList<AnalysisReportDensityDataModel> AnalysisDensityModelModelList;
    RecyclerView mRecyclerView;
    private AnalysisDensitytAdapter mReporterAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_analysis_density_reports, container, false);
        setHasOptionsMenu(true);
        fragmentActivity = (LoginFragmentActivity) getActivity();
        common = new Common();
        fragmentActivity.displayActionBar();
        fragmentActivity.setActionBarTitle("Density Difference Data");
        fragmentActivity.showActionBar();
        fragmentActivity.hideBackActionBar();
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();
        main_loader = (RelativeLayout) rootView.findViewById(R.id.initial_loader);
        main_loader = (RelativeLayout) rootView.findViewById(R.id.initial_loader);
        AnalysisDensityModelModelList = new ArrayList<>();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.mRecyclerView);
        GetAnalysisDensityData();
        return rootView;
    }

    private void GetAnalysisDensityData() {
        main_loader.setVisibility(View.VISIBLE);
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.HeadUrl).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        apiInterface.GetAnalysisDensityDetails(prefs.getString("userid", ""), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
                Log.v("RESPONSE==>", response_data_obj.toString());
                try {
                    if (response_data_obj != null) {
                        main_loader.setVisibility(View.GONE);
                        AnalysisDensityModelModelList = new ArrayList<AnalysisReportDensityDataModel>();
                        AnalysisDensityModelModelList = new Gson().fromJson(response_data_obj.getAsJsonArray("ReportData"), new TypeToken<List<AnalysisReportDensityDataModel>>() {
                        }.getType());
                        if (AnalysisDensityModelModelList != null) {
                            renderTheResponse();
                        }
                    } else {
                        main_loader.setVisibility(View.GONE);
                        common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Found Details!");
                    }

                } catch (Exception e) {
                    showAlertDialog("Density Difference Data", "http://173.11.229.171/viswaweb/VLReports/SampleReports/BQS.PDF");
                    main_loader.setVisibility(View.GONE);
                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Found Details!");
                }

            }

            @Override
            public void failure(RetrofitError error) {
                main_loader.setVisibility(View.GONE);
                common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, getString(R.string.something_went_wrong));
            }
        });
    }


    private void renderTheResponse() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        mRecyclerView.setLayoutManager(layoutManager);
        if (AnalysisDensityModelModelList != null) {
            mReporterAdapter = new AnalysisDensitytAdapter(getActivity(), AnalysisDensityModelModelList, prefs.getString("userid", ""));
            mRecyclerView.setAdapter(mReporterAdapter);
        }
    }


}

