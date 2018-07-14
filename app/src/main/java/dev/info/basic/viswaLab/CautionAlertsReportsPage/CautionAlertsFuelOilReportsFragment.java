package dev.info.basic.viswaLab.CautionAlertsReportsPage;

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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dev.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import dev.info.basic.viswaLab.AnalysisReportsPage.Adapters.AnalysisReportsAdapter;
import dev.info.basic.viswaLab.AnalysisReportsPage.models.AnalysisFoModel;
import dev.info.basic.viswaLab.ApiInterfaces.ApiInterface;
import dev.info.basic.viswaLab.Database.helper;
import dev.info.basic.viswaLab.Fragments.BaseFragment;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.models.LOEquipmentDetailsModel;
import dev.info.basic.viswaLab.models.LoBrandGradesModel;
import dev.info.basic.viswaLab.models.ShipdetailsModel;
import dev.info.basic.viswaLab.utils.Common;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Giri Thangellapally on 21-03-2018.
 */

public class CautionAlertsFuelOilReportsFragment extends BaseFragment implements View.OnClickListener {
    private LoginFragmentActivity fragmentActivity;
    private View rootView;
//    private Common common;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private RelativeLayout main_loader;
    List<ShipdetailsModel> shipdetailsList;
    ArrayList<LoBrandGradesModel> LoBrandGradesModelList;
    ArrayList<LOEquipmentDetailsModel> EquipmentsMOdelList;
    ArrayList<AnalysisFoModel> mReportDataModelList;
    private int shipId = 0;
    private String eqId;
    ImageView btnSubmit, btnsrSubmit;
    private String bandId;
    RecyclerView mRecyclerView;
    AnalysisReportsAdapter mReporterAdapter;
    EditText imo_number, sr_number;
    helper dbHelper;
    Spinner spnVesselShips;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_analysis_reports_fuel_oil_reports, container, false);
        setHasOptionsMenu(true);
        fragmentActivity = (LoginFragmentActivity) getActivity();
//        common = new Common();
        fragmentActivity.displayActionBar();
        fragmentActivity.setActionBarTitle("Fuel Oil Caution Reports");
        fragmentActivity.showActionBar();
        fragmentActivity.hideBackActionBar();
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();

        main_loader = (RelativeLayout) rootView.findViewById(R.id.initial_loader);
        imo_number = (EditText) rootView.findViewById(R.id.imo_number);
        sr_number = (EditText) rootView.findViewById(R.id.sr_number);
        shipdetailsList = new ArrayList<>();
        LoBrandGradesModelList = new ArrayList<>();
        EquipmentsMOdelList = new ArrayList<>();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.mRecyclerView);
        btnSubmit = (ImageView) rootView.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        spnVesselShips = (Spinner) rootView.findViewById(R.id.spnVesselShips);
        btnsrSubmit = (ImageView) rootView.findViewById(R.id.btnsrSubmit);
        btnsrSubmit.setOnClickListener(this);

        imo_number.setEnabled(true);
        sr_number.setEnabled(true);
        helper.getInstance(getContext());
        dbHelper = new helper(getContext());
        fetchShipDetils();
        return rootView;
    }

    private void fetchShipDetils() {
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        main_loader.setVisibility(View.VISIBLE);
        apiInterface.GetFuelOilCOShips(prefs.getString("userid", ""), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
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
                        showAlertDialog("Caution Alerts FuelOil","http://173.11.229.171/viswaweb/VLReports/SampleReports/FO_C.PDF");
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
//                        getEquipmentDataByShipId(shipId);
                    imo_number.setText("");
                    sr_number.setText("");
                    submitReport();
//                    imo_number.setEnabled(false);
                } else if (shipPosition == 0) {
                    shipId = 0;
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
        }
        if (!sr_number.getText().toString().isEmpty() && sr_number.getText().toString().length() > 0) {
            shipId = 0;
        }
        main_loader.setVisibility(View.VISIBLE);
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        apiInterface.GetFuelOilReportsCautionAlert(prefs.getString("userid", ""), shipId, imo_number.getText().toString(), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
                Log.v("RESPONSE==>", response_data_obj.toString());
                try {
                    if (response_data_obj != null) {
                        main_loader.setVisibility(View.GONE);
                        mReportDataModelList = new ArrayList<AnalysisFoModel>();
                        mReportDataModelList = new Gson().fromJson(response_data_obj.getAsJsonArray("ReportsData"), new TypeToken<List<AnalysisFoModel>>() {
                        }.getType());
                        if (mReportDataModelList != null) {
                            renderTheResponse(false);
                        } else {
                            imo_number.setText("");
                            main_loader.setVisibility(View.GONE);
                            if (shipId == 0) {
                                showAlertDialog("Caution Alerts FuelOil","http://173.11.229.171/viswaweb/VLReports/SampleReports/FO_C.PDF");
                            } else {
                                cshowToast();
//                                common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Find Details!");
                            }
                        }
                    } else {
                        renderTheResponse(true);
                        main_loader.setVisibility(View.GONE);
                        cshowToast();
//                        common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Find Details!");
                    }

                } catch (Exception e) {
                    renderTheResponse(true);
                    main_loader.setVisibility(View.GONE);
                    showToast("Could Not Find Details!");
//                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Find Details!");
                }

            }

            @Override
            public void failure(RetrofitError error) {
                main_loader.setVisibility(View.GONE);
showToast();                imo_number.setText("");
            }
        });

    }

    private void renderTheResponse(boolean nodata) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        mRecyclerView.setLayoutManager(layoutManager);

            if (mReportDataModelList != null) {
                mReporterAdapter = new AnalysisReportsAdapter(getActivity(), "FO", mReportDataModelList, new AnalysisReportsAdapter.ListenerInterface() {
                    @Override
                    public void itemClicked(String pdfFileName) {
                        showPdf(pdfFileName,"FO","");
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
                    submitReport();
                }
                break;
            case R.id.btnsrSubmit:
                if (shipId == 0 && sr_number.getText().toString().isEmpty()) {
                    pshowToast();
//                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Please enter the value!");
                } else {
                    shipId = 0;
//                    submitSerialDataReport();
                }
                break;
        }


    }
}
