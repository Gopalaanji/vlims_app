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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dev.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import dev.info.basic.viswaLab.AnalysisReportsPage.Adapters.AnalysisAdditionalTestAdapter;
import dev.info.basic.viswaLab.AnalysisReportsPage.Adapters.AnalysisPurifierEfffyAdapter;
import dev.info.basic.viswaLab.AnalysisReportsPage.models.AnalysisPurifierEffyShipsModel;
import dev.info.basic.viswaLab.AnalysisReportsPage.models.BunkerPortModel;
import dev.info.basic.viswaLab.AnalysisReportsPage.models.PurifierEffyResponseModel;
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
public class AnalysisReportsAdditionalTestReportsFragment extends BaseFragment implements View.OnClickListener {
    private LoginFragmentActivity fragmentActivity;
    private View rootView;
//    private Common common;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private RelativeLayout main_loader;
    EditText imo_number, sr_number;
    List<AnalysisPurifierEffyShipsModel> shipdetailsList;
    List<BunkerPortModel> bunkerPortdetailsList;
    ArrayList<AnalysisPurifierEffyShipsModel> AnalysisPurifierEffyModelModelList;
    RecyclerView mRecyclerView;
    ImageView btnSubmit, btnsrSubmit;
    Spinner spnVesselShips;
    Spinner spnportNames;
    private int shipId = 0;
    private int portId = 0;
    private String[] shipList;
    private String[] portnameList;
    private List<PurifierEffyResponseModel> mReportDataModelList;
    private AnalysisAdditionalTestAdapter mReporterAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_analysis_additionaltest_reports, container, false);
        setHasOptionsMenu(true);
        fragmentActivity = (LoginFragmentActivity) getActivity();
//        common = new Common();
        fragmentActivity.displayActionBar();
        fragmentActivity.setActionBarTitle("Additional Test Reports");
        fragmentActivity.showActionBar();
        fragmentActivity.hideBackActionBar();
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();
        main_loader = (RelativeLayout) rootView.findViewById(R.id.initial_loader);
        main_loader = (RelativeLayout) rootView.findViewById(R.id.initial_loader);
        imo_number = (EditText) rootView.findViewById(R.id.imo_number);
        sr_number = (EditText) rootView.findViewById(R.id.sr_number);
        shipdetailsList = new ArrayList<>();
        AnalysisPurifierEffyModelModelList = new ArrayList<>();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.mRecyclerView);
        btnSubmit = (ImageView) rootView.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        spnVesselShips = (Spinner) rootView.findViewById(R.id.spnVesselShips);
        spnportNames = (Spinner) rootView.findViewById(R.id.spnportNames);
        btnsrSubmit = (ImageView) rootView.findViewById(R.id.btnsrSubmit);
        btnsrSubmit.setOnClickListener(this);

        imo_number.setEnabled(true);
        sr_number.setEnabled(true);
        GetAnalysisReportsAdditionalShips();
        return rootView;
    }

    private void GetAnalysisReportsAdditionalShips() {
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        main_loader.setVisibility(View.VISIBLE);
        apiInterface.GetAnalysisReportsAdditionalShips(prefs.getString("userid", ""), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
                Log.v("RESPONSE==>", response_data_obj.toString());
                main_loader.setVisibility(View.GONE);
                if (response_data_obj != null) {
                    try {
                        shipdetailsList = new Gson().fromJson(response_data_obj.getAsJsonArray("ReportData"), new TypeToken<List<AnalysisPurifierEffyShipsModel>>() {
                        }.getType());
                        shipList = new String[shipdetailsList.size() + 1];
                        if (shipdetailsList != null) {
                            int j = 1;
                            shipList[0] = "All Ships*";
                            for (int i = 0; i < shipdetailsList.size(); i++) {
                                shipList[j] = shipdetailsList.get(i).getVlims_ship_name();
                                j++;
                            }
                        }
                        GetAnalysisReportsAdditionalPortNames();
                    } catch (Exception e) {
                        Log.v("XX", e.toString());
                        showAlertDialog("Additional Test Report", "http://173.11.229.171/viswaweb/VLReports/SampleReports/ADD.PDF");
                    }
                } else {
                    main_loader.setVisibility(View.GONE);
                    showToast(getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                main_loader.setVisibility(View.GONE);
showToast();            }
        });

    }

    private void GetAnalysisReportsAdditionalPortNames() {
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        main_loader.setVisibility(View.VISIBLE);
//        renderDetails(shipList);
        apiInterface.GetAnalysisReportsAdditionalPortNames(prefs.getString("userid", ""), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
                Log.v("RESPONSE==>", response_data_obj.toString());
                main_loader.setVisibility(View.GONE);
                if (response_data_obj != null) {
                    try {
                        bunkerPortdetailsList = new Gson().fromJson(response_data_obj.getAsJsonArray("ReportData"), new TypeToken<List<BunkerPortModel>>() {
                        }.getType());
                        portnameList = new String[bunkerPortdetailsList.size() + 1];
                        if (bunkerPortdetailsList != null) {
                            int j = 1;
                            portnameList[0] = "All Port Names*";
                            for (int i = 0; i < bunkerPortdetailsList.size(); i++) {
                                portnameList[j] = bunkerPortdetailsList.get(i).getVlims_bunker_port_name();
                                j++;
                            }
                        }
                        renderDetails();

                    } catch (Exception e) {
                        showAlertDialog("Additional Test Report", "http://173.11.229.171/viswaweb/VLReports/SampleReports/ADD.PDF");
                    }
                } else {
                    main_loader.setVisibility(View.GONE);
                    showToast(getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                main_loader.setVisibility(View.GONE);
showToast();            }
        });


    }

    private void renderDetails() {
        final ArrayAdapter<String> shipdetailsListAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, shipList);
        shipdetailsListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnVesselShips.setAdapter(shipdetailsListAdapter);
        final ArrayAdapter<String> portdetailsListAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, portnameList);
        portdetailsListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnportNames.setAdapter(portdetailsListAdapter);

        spnVesselShips.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int shipPosition = spnVesselShips.getSelectedItemPosition();
                if (shipPosition > 0) {
                    shipId = shipdetailsList.get(shipPosition - 1).getVlims_ship_id();
                    imo_number.setText("");
                    sr_number.setText("");
                    portId = 0;
                    submitReport();
//                    imo_number.setEnabled(false);
                } else if (shipPosition == 0) {
                    shipId = 0;
//                    submitReport();
//                    imo_number.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spnportNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int portPosition = spnportNames.getSelectedItemPosition();
                if (portPosition > 0) {
                    portId = bunkerPortdetailsList.get(portPosition - 1).getVlims_bunker_port_id();
                    imo_number.setText("");
                    shipId = 0;
                    sr_number.setText("");
                    submitReport();
//                    imo_number.setEnabled(false);
                } else if (portPosition == 0) {
                    portId = 0;
                    submitReport();
//                    imo_number.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void submitReport() {
        if (!imo_number.getText().toString().isEmpty() && imo_number.getText().toString().length() > 0) {
            shipId = 0;
            portId = 0;
        }
        if (!sr_number.getText().toString().isEmpty() && sr_number.getText().toString().length() > 0) {
            shipId = 0;
            portId = 0;
        }
        main_loader.setVisibility(View.VISIBLE);
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        apiInterface.GetAdditionalReportAnalysisReports(prefs.getString("userid", ""), shipId, imo_number.getText().toString(), portId, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
                Log.v("RESPONSE==>", response_data_obj.toString());
                try {
                    if (response_data_obj != null) {
                        main_loader.setVisibility(View.GONE);
                        mReportDataModelList = new ArrayList<PurifierEffyResponseModel>();
                        mReportDataModelList = new Gson().fromJson(response_data_obj.getAsJsonArray("ReportData"), new TypeToken<List<PurifierEffyResponseModel>>() {
                        }.getType());
                        if (mReportDataModelList != null) {
                            renderTheResponse();
                        } else {
                            imo_number.setText("");
                            main_loader.setVisibility(View.GONE);
                            if (shipId == 0) {
                                showAlertDialog("Additional Test Report", "http://173.11.229.171/viswaweb/VLReports/SampleReports/ADD.PDF");
                            } else {
                                cshowToast();
//                                common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Find Details!");
                            }
                        }
                    } else {
                        main_loader.setVisibility(View.GONE);
                        cshowToast();
//                        common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Find Details!");
                    }

                } catch (Exception e) {
                    main_loader.setVisibility(View.GONE);
                    showAlertDialog("Additional Test Report", "http://173.11.229.171/viswaweb/VLReports/SampleReports/ADD.PDF");
                }

            }

            @Override
            public void failure(RetrofitError error) {
                main_loader.setVisibility(View.GONE);
                Toast.makeText(getActivity(),getString(R.string.something_went_wrong),Toast.LENGTH_SHORT).show();
//showToast();                imo_number.setText("");
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
        if (mReportDataModelList != null) {
            mReporterAdapter = new AnalysisAdditionalTestAdapter(getActivity(), mReportDataModelList, new AnalysisAdditionalTestAdapter.AdditionalTestListner() {
                @Override
                public void itemClicked(String pdfFileName,String testName) {
                    showPdf(pdfFileName,"ADD",testName);
                }
            });
            mRecyclerView.setAdapter(mReporterAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                if (shipId == 0 && imo_number.getText().toString().isEmpty()) {
                    pshowToast();
//                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Please enter the value!");
                } else {
                    shipId = 0;
                    portId = 0;
                    submitReport();
                }
                break;
            case R.id.btnsrSubmit:
                if (shipId == 0 && portId == 0 && sr_number.getText().toString().isEmpty()) {
                    pshowToast();
//                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Please enter the value!");
                } else {
                    shipId = 0;
                    portId = 0;
                    submitSerialDataReport();
                }
                break;
        }


    }
    private void submitSerialDataReport() {
        if (!imo_number.getText().toString().isEmpty() && imo_number.getText().toString().length() > 0) {
            shipId = 0;
            portId=0;
        }
        if (!sr_number.getText().toString().isEmpty() && sr_number.getText().toString().length() > 0) {
            shipId = 0;
            portId=0;
        }
        main_loader.setVisibility(View.VISIBLE);
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        apiInterface.GetSrDataForAddSNSearch(prefs.getString("userid", ""), sr_number.getText().toString(), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
                Log.v("RESPONSE==>", response_data_obj.toString());
                try {
                    if (response_data_obj != null) {
                        main_loader.setVisibility(View.GONE);
                        mReportDataModelList = new ArrayList<PurifierEffyResponseModel>();
                        mReportDataModelList = new Gson().fromJson(response_data_obj.getAsJsonArray("ReportData"), new TypeToken<List<ReportDataModel>>() {
                        }.getType());
                        if (mReportDataModelList != null) {
                            renderTheResponse();
                        } else {
                            imo_number.setText("");
                            main_loader.setVisibility(View.GONE);
                            cshowToast();
//                            common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Find Details!");
                        }
                    } else {
                        main_loader.setVisibility(View.GONE);
                        cshowToast();
//                        common.showNewAlertDesignwAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Find Details!");
                    }

                } catch (Exception e) {
                    main_loader.setVisibility(View.GONE);
                    cshowToast();
//                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Find Details!");

                }

            }

            @Override
            public void failure(RetrofitError error) {
                main_loader.setVisibility(View.GONE);
showToast();                sr_number.setText("");
            }
        });
    }

}

