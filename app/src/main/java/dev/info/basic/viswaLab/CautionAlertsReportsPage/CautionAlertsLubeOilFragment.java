package dev.info.basic.viswaLab.CautionAlertsReportsPage;

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
import dev.info.basic.viswaLab.Adapters.ReporterAdapter;
import dev.info.basic.viswaLab.ApiInterfaces.ApiInterface;
import dev.info.basic.viswaLab.Database.helper;
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
 * Created by ${GIRI} on 12-01-2018.
 */

public class CautionAlertsLubeOilFragment extends BaseFragment implements View.OnClickListener {
    private LoginFragmentActivity fragmentActivity;
    private View rootView;
//    private Common common;
    private RelativeLayout main_loader;
    List<ShipdetailsModel> shipdetailsList;
    ArrayList<ReportDataModel> mReportDataModelList;
    private int shipId = 0;
    private String eqId;
    ImageView btnSubmit, btnsrSubmit;
    private String bandId;
    RecyclerView mRecyclerView;
    ReporterAdapter mReporterAdapter;
    Spinner spnVesselShips;
    EditText imo_number, sr_number;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    helper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_analysis_reports_lube_oil_reports, container, false);
        setHasOptionsMenu(true);
//        common = new Common();
        fragmentActivity = (LoginFragmentActivity) getActivity();
        fragmentActivity.displayActionBar();
        fragmentActivity.showActionBar();
        fragmentActivity.hideBackActionBar();
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();
        fragmentActivity.setActionBarTitle("Lube Oil Caution Reports");
        main_loader = (RelativeLayout) rootView.findViewById(R.id.initial_loader);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.mRecyclerView);
        spnVesselShips = (Spinner) rootView.findViewById(R.id.spnVesselShips);
        btnSubmit = (ImageView) rootView.findViewById(R.id.btnSubmit);
        btnsrSubmit = (ImageView) rootView.findViewById(R.id.btnsrSubmit);
        imo_number = (EditText) rootView.findViewById(R.id.imo_number);
        sr_number = (EditText) rootView.findViewById(R.id.sr_number);
        btnSubmit.setOnClickListener(this);
        btnsrSubmit.setOnClickListener(this);
        imo_number.setEnabled(true);
        sr_number.setEnabled(true);
        helper.getInstance(getContext());
        dbHelper = new helper(getContext());
        fetchLubeOilReports();
        return rootView;
    }

    private void fetchLubeOilReports() {
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        main_loader.setVisibility(View.VISIBLE);
        apiInterface.GetLOOilCOShips(prefs.getString("userid", ""), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
                Log.v("RESPONSE==>", response_data_obj.toString());
                main_loader.setVisibility(View.GONE);
                if (response_data_obj != null) {
                    try {
                        shipdetailsList = new Gson().fromJson(response_data_obj.getAsJsonArray("ReportData"), new TypeToken<List<ShipdetailsModel>>() {
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
                        showAlertDialog("Caution Alerts LubeOil","http://173.11.229.171/viswaweb/VLReports/SampleReports/LO_C.PDF");
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
                    imo_number.setText("");
//                    imo_number.setEnabled(false);
                    submitReport();
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

    private void fetchIntialData() {
        main_loader.setVisibility(View.VISIBLE);
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        apiInterface.GetSampleReportForUserByShipIdAndImoId(prefs.getString("userid", ""), 0, "", new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
                Log.v("RESPONSE==>", response_data_obj.toString());
                try {
                    if (response_data_obj != null) {
                        main_loader.setVisibility(View.GONE);
                        mReportDataModelList = new ArrayList<ReportDataModel>();
                        mReportDataModelList = new Gson().fromJson(response_data_obj.getAsJsonArray("UserReportdata"), new TypeToken<List<ReportDataModel>>() {
                        }.getType());
                        if (mReportDataModelList != null) {
                            renderTheResponse(false);
                        } else {
                            imo_number.setText("");
                            main_loader.setVisibility(View.GONE);
                            cshowToast();
//                            common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Find Details!");
                        }
                    } else {
                        main_loader.setVisibility(View.GONE);
                        cshowToast();
//                        common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Find Details!");
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
showToast();                imo_number.setText("");
            }
        });

    }

    private void renderTheResponse(boolean nodata) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        mRecyclerView.setLayoutManager(layoutManager);
        if (nodata) {
            ReportDataModel analysisFoModel = new ReportDataModel();
            analysisFoModel.setShipName("Test Ship");
            analysisFoModel.setEquipment("LO_CA");
            analysisFoModel.setOilCondition("1");
            mReportDataModelList.add(analysisFoModel);
            mReporterAdapter = new ReporterAdapter(getActivity(), false, mReportDataModelList, prefs.getString("userid", ""), prefs.getString("Username", ""), prefs.getString("pwd", ""), new ReporterAdapter.CA_lubeoileListner() {
                @Override
                public void itemClicked(String calcId) {
                    showPdf(calcId,"LO_AR","");
                }
            });

            mRecyclerView.setAdapter(mReporterAdapter);
        } else {
            if (mReportDataModelList != null) {
                mReporterAdapter = new ReporterAdapter(getActivity(), false, mReportDataModelList, prefs.getString("userid", ""), prefs.getString("Username", ""), prefs.getString("pwd", ""), new ReporterAdapter.CA_lubeoileListner() {
                    @Override
                    public void itemClicked(String pdfname) {
                        showPdf(pdfname,"LO_AR","");

                    }
                });
                mRecyclerView.setAdapter(mReporterAdapter);
            }
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
                    submitSerialDataReport();
                }
                break;
        }
    }

    private void submitSerialDataReport() {
        if (!imo_number.getText().toString().isEmpty() && imo_number.getText().toString().length() > 0) {
            shipId = 0;
        }
        if (!sr_number.getText().toString().isEmpty() && sr_number.getText().toString().length() > 0) {
            shipId = 0;
        }
        Log.v("FUCK", "SHIPID" + shipId + "EDIT" + sr_number.getText().toString());
        main_loader.setVisibility(View.VISIBLE);
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        apiInterface.GetSrDataForReport(prefs.getString("userid", ""), sr_number.getText().toString(), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
                Log.v("RESPONSE==>", response_data_obj.toString());
                try {
                    if (response_data_obj != null) {
                        main_loader.setVisibility(View.GONE);
                        mReportDataModelList = new ArrayList<ReportDataModel>();
                        mReportDataModelList = new Gson().fromJson(response_data_obj.getAsJsonArray("ReportData"), new TypeToken<List<ReportDataModel>>() {
                        }.getType());
                        if (mReportDataModelList != null) {
                            renderTheResponse(false);
                        } else {
                            renderTheResponse(true);
                            imo_number.setText("");
                            main_loader.setVisibility(View.GONE);
                            cshowToast();
//                            common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Find Details!");
                        }
                    } else {
                        renderTheResponse(true);
                        main_loader.setVisibility(View.GONE);
                        cshowToast();
//                        common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Find Details!");
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


    private void submitReport() {

        if (!imo_number.getText().toString().isEmpty() && imo_number.getText().toString().length() > 0) {
            shipId = 0;
        }
        Log.v("FUCK", "SHIPID" + shipId + "EDIT" + imo_number.getText().toString());
        main_loader.setVisibility(View.VISIBLE);
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        apiInterface.GetSampleReportForUserByShipIdAndImoId(prefs.getString("userid", ""), shipId, imo_number.getText().toString(), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
                Log.v("RESPONSE==>", response_data_obj.toString());
                try {
                    if (response_data_obj != null) {
                        main_loader.setVisibility(View.GONE);
                        mReportDataModelList = new ArrayList<ReportDataModel>();
                        mReportDataModelList = new Gson().fromJson(response_data_obj.getAsJsonArray("UserReportdata"), new TypeToken<List<ReportDataModel>>() {
                        }.getType());
                        if (mReportDataModelList != null) {
                            renderTheResponse(false);
                        } else {
                            imo_number.setText("");
                            main_loader.setVisibility(View.GONE);
                            if(shipId==0){
                                showAlertDialog("Caution Alerts LubeOil","http://173.11.229.171/viswaweb/VLReports/SampleReports/LO_C.PDF");
                            }else{
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
                    cshowToast();
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
}