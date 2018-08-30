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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dev.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import dev.info.basic.viswaLab.AnalysisReportsPage.Adapters.AnalysisDensitytAdapter;
import dev.info.basic.viswaLab.AnalysisReportsPage.models.AnalysisDensityModel;
import dev.info.basic.viswaLab.AnalysisReportsPage.models.AnalysisFoModel;
import dev.info.basic.viswaLab.AnalysisReportsPage.models.AnalysisReportDensityDataModel;
import dev.info.basic.viswaLab.ApiInterfaces.ApiInterface;
import dev.info.basic.viswaLab.Fragments.BaseFragment;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.models.ReportDataModel;
import dev.info.basic.viswaLab.models.ShipdetailsModel;
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
    //    private Common common;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private RelativeLayout main_loader;
    ArrayList<AnalysisReportDensityDataModel> mAnalysisDensityModelModelList;
    RecyclerView mRecyclerView;
    private AnalysisDensitytAdapter mReporterAdapter;
    Spinner spnVesselShips;
    List<ShipdetailsModel> shipdetailsList;
    private int shipId = 0;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_analysis_density_reports, container, false);
        setHasOptionsMenu(true);
        fragmentActivity = (LoginFragmentActivity) getActivity();
        spnVesselShips = (Spinner) rootView.findViewById(R.id.spnVesselShips);

//        common = new Common();
        fragmentActivity.displayActionBar();
        fragmentActivity.setActionBarTitle("Quantity Diff Based on Density");
        fragmentActivity.showActionBar();
        fragmentActivity.hideBackActionBar();
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();
        main_loader = (RelativeLayout) rootView.findViewById(R.id.initial_loader);
        main_loader = (RelativeLayout) rootView.findViewById(R.id.initial_loader);
        mAnalysisDensityModelModelList = new ArrayList<>();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.mRecyclerView);
        getUserShipDetailsOfDensitydiff(prefs.getString("userid", ""));

//        GetAnalysisDensityData();
        return rootView;
    }

    private void getUserShipDetailsOfDensitydiff(String userid) {
        if (Common.isNetworkAvailable(this.getActivity())) {
            RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
            final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
            main_loader.setVisibility(View.VISIBLE);
            apiInterface.GetFuelOilReportsAnalysisReportsShips(userid, new Callback<JsonObject>() {
                @Override
                public void success(JsonObject response_data_obj, Response response) {
                    if (isDebug)
                        Log.v("RESPONSE==>", response_data_obj.toString());
                    main_loader.setVisibility(View.GONE);
                    if (response_data_obj != null) {
                        try {
                            shipdetailsList = new Gson().fromJson(response_data_obj.getAsJsonArray("ReportsData"), new TypeToken<List<ShipdetailsModel>>() {
                            }.getType());
                            final String[] shipList = new String[shipdetailsList.size() + 1];
                            if (shipdetailsList != null) {
                                int j = 1;
                                shipList[0] = "All Ships*";
                                for (int i = 0; i < shipdetailsList.size(); i++) {
                                    shipList[j] = shipdetailsList.get(i).getShipName();
                                    j++;
                                }
                            }
                            renderDetails(shipList);
                        } catch (Exception e) {
                            showAlertDialog("Quantity Difference Based on Density", "http://173.11.229.171/viswaweb/VLReports/SampleReports/BQS.PDF");
                        }
                    } else {
                        main_loader.setVisibility(View.GONE);
                        showToast(getString(R.string.something_went_wrong));
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    main_loader.setVisibility(View.GONE);
                    showToast();
                }
            });
        } else {
//            common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, getString(R.string.network_error));
        }


    }
    private void renderDetails(String[] shipList) {

        final ArrayAdapter<String> shipdetailsListAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, shipList);
        shipdetailsListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnVesselShips.setAdapter(shipdetailsListAdapter);
        spnVesselShips.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int shipPosition = spnVesselShips.getSelectedItemPosition();
                if (shipPosition > 0) {
                    shipId = shipdetailsList.get(shipPosition - 1).getShipId();
                    submitReport();
                } else if (shipPosition == 0) {
                    shipId = 0;
                    submitReport();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void submitReport() {
        main_loader.setVisibility(View.VISIBLE);
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        apiInterface.GetNewDensityReportsAnalysisReports(prefs.getString("userid", ""), shipId, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
                if (isDebug)
                    Log.v("RESPONSE==>", response_data_obj.toString());
                main_loader.setVisibility(View.GONE);
                try {
                    if (response_data_obj != null) {
                        mAnalysisDensityModelModelList = new Gson().fromJson(response_data_obj.getAsJsonArray("ReportData"), new TypeToken<List<AnalysisReportDensityDataModel>>() {
                        }.getType());
                        if (mAnalysisDensityModelModelList != null) {
                            renderTheResponse();
                        } else {
//                            if (shipId == 0) {
//                            } else {
//                                showToast(getString(R.string.something_went_wrong));

                            showToast("Could Not Find Details!");
                            }
//                        }
                    } else {
//                        renderTheResponse(true);
                        showToast(getString(R.string.something_went_wrong));

//                        common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Find Details!");
                    }

                } catch (Exception e) {
                    showAlertDialog("Quantity Difference Based on Density", "http://173.11.229.171/viswaweb/VLReports/SampleReports/BQS.PDF");
                }

            }

            @Override
            public void failure(RetrofitError error) {
                main_loader.setVisibility(View.GONE);
                showToast();
            }
        });

    }


    private void GetAnalysisDensityData() {
        main_loader.setVisibility(View.VISIBLE);
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        apiInterface.GetAnalysisDensityDetails(prefs.getString("userid", ""), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
                Log.v("RESPONSE==>", response_data_obj.toString());
                try {
                    if (response_data_obj != null) {
                        main_loader.setVisibility(View.GONE);
                        mAnalysisDensityModelModelList = new ArrayList<AnalysisReportDensityDataModel>();
                        mAnalysisDensityModelModelList = new Gson().fromJson(response_data_obj.getAsJsonArray("ReportData"), new TypeToken<List<AnalysisReportDensityDataModel>>() {
                        }.getType());
                        if (mAnalysisDensityModelModelList != null) {
                            renderTheResponse();
                        }
                    } else {
                        main_loader.setVisibility(View.GONE);
                        cshowToast();
//                        common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Find Details!");
                    }

                } catch (Exception e) {
                    showAlertDialog("Density Difference Data", "http://173.11.229.171/viswaweb/VLReports/SampleReports/BQS.PDF");
                    main_loader.setVisibility(View.GONE);
                    cshowToast();
//                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Find Details!");
                }

            }

            @Override
            public void failure(RetrofitError error) {
                main_loader.setVisibility(View.GONE);
                showToast();
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
        if (mAnalysisDensityModelModelList != null) {
            mReporterAdapter = new AnalysisDensitytAdapter(getActivity(), mAnalysisDensityModelModelList, prefs.getString("userid", ""));
            mRecyclerView.setAdapter(mReporterAdapter);
        }
    }


}

