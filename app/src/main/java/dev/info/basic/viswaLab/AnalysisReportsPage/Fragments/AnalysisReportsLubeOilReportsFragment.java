package dev.info.basic.viswaLab.AnalysisReportsPage.Fragments;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
import dev.info.basic.viswaLab.models.LOEquipmentDetailsModel;
import dev.info.basic.viswaLab.models.LoBrandGradesModel;
import dev.info.basic.viswaLab.models.ReportDataModel;
import dev.info.basic.viswaLab.models.ShipdetailsModel;
import dev.info.basic.viswaLab.utils.Common;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by RSR on 07-09-2017.
 */

public class AnalysisReportsLubeOilReportsFragment extends BaseFragment implements View.OnClickListener {
    private LoginFragmentActivity fragmentActivity;
    private View rootView;
    private Common common;
    Spinner spnVesselShips;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private RelativeLayout main_loader;
    List<ShipdetailsModel> shipdetailsList;
    ArrayList<LoBrandGradesModel> LoBrandGradesModelList;
    ArrayList<LOEquipmentDetailsModel> EquipmentsMOdelList;
    ArrayList<ReportDataModel> mReportDataModelList;
    private int shipId = 0;
    private String eqId;
    ImageView btnSubmit, btnsrSubmit;
    private String bandId;
    RecyclerView mRecyclerView;
    ReporterAdapter mReporterAdapter;
    EditText imo_number, sr_number;
    helper dbHelper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_analysis_reports_lube_oil_reports, container, false);
        setHasOptionsMenu(true);
        fragmentActivity = (LoginFragmentActivity) getActivity();
        common = new Common();
        fragmentActivity.displayActionBar();
        fragmentActivity.setActionBarTitle("Lube Oil Analysis");
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


        spnVesselShips = (Spinner) rootView.findViewById(R.id.spnVesselShips);

        btnSubmit = (ImageView) rootView.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

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
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.HeadUrl).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        main_loader.setVisibility(View.VISIBLE);
        apiInterface.GetFuelOilReportsAnalysisReportsShips(prefs.getString("userid", ""), new Callback<JsonObject>() {
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
                        showAlertDialog("http://173.11.229.171/viswaweb/VLReports/SampleReports/LO.PDF");
                    }
                } else {
                    main_loader.setVisibility(View.GONE);
                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Something went wrong!");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                main_loader.setVisibility(View.GONE);
                common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, getString(R.string.something_went_wrong));
            }
        });

    }

    private void renderDetails(final String[] shipList) {

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
        /*} else {
            Toast.makeText(getActivity(), "Please Clear The IMO number To Select the Ship!", Toast.LENGTH_SHORT).show();
        }*/
    }

    private void getEquipmentDataByShipId(int shipId) {
        main_loader.setVisibility(View.VISIBLE);
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.HeadUrl).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        apiInterface.GetEquipementDataByShipDetails(shipId + "", new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
                Log.v("RESPONSE==>", response_data_obj.toString());
                if (response_data_obj != null) {
                    main_loader.setVisibility(View.GONE);
                    EquipmentsMOdelList = new ArrayList<>();
                    EquipmentsMOdelList = new Gson().fromJson(response_data_obj.getAsJsonArray("EquimentDetails"), new TypeToken<List<LOEquipmentDetailsModel>>() {
                    }.getType());
                    final String[] equipmentLisst = new String[EquipmentsMOdelList.size() + 1];
                    int m = 1;
                    equipmentLisst[0] = "All Equipments*";
                    for (int i = 0; i < EquipmentsMOdelList.size(); i++) {
                        equipmentLisst[m] = EquipmentsMOdelList.get(i).getLOEquipmentDescription();
                        m++;
                    }
                   /* final ArrayAdapter<String> equipmentModelListAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, equipmentLisst);
                    equipmentModelListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnEquipment.setAdapter(equipmentModelListAdapter);
                    spnEquipment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            int eqipmentPosition = spnEquipment.getSelectedItemPosition();
                            if (eqipmentPosition > 0) {
                                eqId = EquipmentsMOdelList.get(eqipmentPosition - 1).getLOEquipmentId();
//                                getBrandtDataByShipId(eqId);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
*/
                } else {
                    main_loader.setVisibility(View.GONE);
                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Something went wrong!");
                }
//
            }

            @Override
            public void failure(RetrofitError error) {
                main_loader.setVisibility(View.GONE);
                common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, getString(R.string.something_went_wrong));
            }
        });

    }

    private void getBrandtDataByShipId(String equipmentId) {
        main_loader.setVisibility(View.VISIBLE);
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.HeadUrl).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        Log.v("Ship&Eqi", "" + shipId + "eq==>" + equipmentId);
        apiInterface.GetSupplierBrandDataByEquipmentDetails(shipId + "", equipmentId, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
                Log.v("RESPONSE==>", response_data_obj.toString());
                if (response_data_obj != null) {
                    main_loader.setVisibility(View.GONE);

                    LoBrandGradesModelList = new ArrayList<>();
                    LoBrandGradesModelList = new Gson().fromJson(response_data_obj.getAsJsonArray("EquimentDetails"), new TypeToken<List<LOEquipmentDetailsModel>>() {
                    }.getType());
                    final String[] loBrandGradlist = new String[LoBrandGradesModelList.size() + 1];
                    int k = 1;
                    loBrandGradlist[0] = "All LOBrandGrades*";
                    for (int i = 0; i < LoBrandGradesModelList.size(); i++) {
                        loBrandGradlist[k] = LoBrandGradesModelList.get(i).getLOSupplierBrand();
                        k++;
                    }
                 /*   final ArrayAdapter<String> loBrandGradesModelListAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, loBrandGradlist);
                    spnBrandandGrades.setAdapter(loBrandGradesModelListAdapter);
                    spnBrandandGrades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            int brandPos = spnBrandandGrades.getSelectedItemPosition();
                            if (brandPos > 0) {
                                bandId = LoBrandGradesModelList.get(brandPos - 1).getRefOilParameterId();
                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });*/

                } else {
                    main_loader.setVisibility(View.GONE);
                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Something went wrong!");
                }
//
            }

            @Override
            public void failure(RetrofitError error) {
                main_loader.setVisibility(View.GONE);
                common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, getString(R.string.something_went_wrong));
            }
        });

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
                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Please enter the value!");
                } else {
                    shipId = 0;
                    submitReport();
                }
                break;
            case R.id.btnsrSubmit:
                if (shipId == 0 && sr_number.getText().toString().isEmpty()) {
                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Please enter the value!");
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
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.HeadUrl).build();
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
                            imo_number.setText("");
                            main_loader.setVisibility(View.GONE);
                            common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Found Details!");
                        }
                    } else {
                        renderTheResponse(true);
                        main_loader.setVisibility(View.GONE);
                        common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Found Details!");
                    }

                } catch (Exception e) {
                    renderTheResponse(true);
                    main_loader.setVisibility(View.GONE);
                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Found Details!");

                }

            }

            @Override
            public void failure(RetrofitError error) {
                main_loader.setVisibility(View.GONE);
                common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, getString(R.string.something_went_wrong));
                sr_number.setText("");
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
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.HeadUrl).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        apiInterface.GetDataForReport(shipId, imo_number.getText().toString(), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
                Log.v("RESPONSE==>", response_data_obj.toString());
                main_loader.setVisibility(View.GONE);

                try {
                    if (response_data_obj != null) {
                        mReportDataModelList = new ArrayList<ReportDataModel>();
                        mReportDataModelList = new Gson().fromJson(response_data_obj.getAsJsonArray("ReportData"), new TypeToken<List<ReportDataModel>>() {
                        }.getType());
                        if (mReportDataModelList != null) {
                            renderTheResponse(false);
                        } else {
                            imo_number.setText("");
                            if (shipId == 0) {
                                showAlertDialog("http://173.11.229.171/viswaweb/VLReports/SampleReports/LO.PDF");
                            } else {
                                common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Found Details!");
                            }
                        }
                    } else {
                        common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Found Details!");
                    }

                } catch (Exception e) {
                    showAlertDialog("http://173.11.229.171/viswaweb/VLReports/SampleReports/LO.PDF");
                }

            }

            @Override
            public void failure(RetrofitError error) {
                main_loader.setVisibility(View.GONE);
                common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, getString(R.string.something_went_wrong));
                imo_number.setText("");
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

        if (nodata) {
            ReportDataModel analysisFoModel = new ReportDataModel();
            analysisFoModel.setShipName("Test Ship");
            analysisFoModel.setEquipment("LO_AR");
            analysisFoModel.setOilCondition("1");
            mReportDataModelList.add(analysisFoModel);
            mReporterAdapter = new ReporterAdapter(getActivity(), false, mReportDataModelList, prefs.getString("userid", ""));
            mRecyclerView.setAdapter(mReporterAdapter);
        } else {
            if (mReportDataModelList != null) {
                mReporterAdapter = new ReporterAdapter(getActivity(), false, mReportDataModelList, prefs.getString("userid", ""));
                mRecyclerView.setAdapter(mReporterAdapter);
            } else {
                mRecyclerView.removeAllViews();
            }
        }


    }

    private void selectDate(final TextView dfrom) {
        final Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Material_Light_Dialog);
        dialog.setContentView(R.layout.date_picker_default_dialog);
        dialog.setTitle("Please Pick Date");
        final DatePicker dp = (DatePicker) dialog.findViewById(R.id.datePicker1);
        dp.setMaxDate(System.currentTimeMillis());
        Button btnOK = (Button) dialog.findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = dp.getDayOfMonth();
                int month = dp.getMonth() + 1;
                int year = dp.getYear();
                String date = day + "/" + month + "/" + year;
                dfrom.setText(date);
                dialog.dismiss();
            }
        });
        dialog.show();


    }
}
