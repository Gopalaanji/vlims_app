package rsr.info.basic.viswaLab.Fragments;

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
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rsr.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import rsr.info.basic.viswaLab.Adapters.ReporterAdapter;
import rsr.info.basic.viswaLab.ApiInterfaces.ApiInterface;
import rsr.info.basic.viswaLab.R;
import rsr.info.basic.viswaLab.models.LOEquipmentDetailsModel;
import rsr.info.basic.viswaLab.models.LoBrandGradesModel;
import rsr.info.basic.viswaLab.models.ReportDataModel;
import rsr.info.basic.viswaLab.models.ShipdetailsModel;
import rsr.info.basic.viswaLab.utils.Common;

/**
 * Created by RSR on 07-09-2017.
 */

public class LubeOilReportsFragment extends BaseFragment implements View.OnClickListener {
    private LoginFragmentActivity fragmentActivity;
    private View rootView;
    private Common common;
    RelativeLayout sampling_date_from, sampling_date_to, test_date_from, test_date_to;
    TextView tvsampling_date_from, tvsampling_date_to, tvtest_date_from, tvtest_date_to;
    Spinner spnVesselShips, spnBrandandGrades, spnEquipment;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private RelativeLayout main_loader;
    ArrayList<ShipdetailsModel> ShipdetailsList;
    ArrayList<LoBrandGradesModel> LoBrandGradesModelList;
    ArrayList<LOEquipmentDetailsModel> EquipmentsMOdelList;
    ArrayList<ReportDataModel> mReportDataModelList;
    private int shipId = 0;
    private String eqId;
    ImageView btnSubmit;
    private String bandId;
    RecyclerView mRecyclerView;
    ReporterAdapter mReporterAdapter;
    EditText imo_number;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_lube_oil_reports, container, false);
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
        ShipdetailsList = new ArrayList<>();
        LoBrandGradesModelList = new ArrayList<>();
        EquipmentsMOdelList = new ArrayList<>();


        sampling_date_from = (RelativeLayout) rootView.findViewById(R.id.sampling_date_from);
        tvsampling_date_from = (TextView) rootView.findViewById(R.id.tvsampling_date_from);

        sampling_date_to = (RelativeLayout) rootView.findViewById(R.id.sampling_date_to);
        tvsampling_date_to = (TextView) rootView.findViewById(R.id.tvsampling_date_to);

        test_date_from = (RelativeLayout) rootView.findViewById(R.id.test_date_from);
        tvtest_date_from = (TextView) rootView.findViewById(R.id.tvtest_date_from);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.mRecyclerView);


        test_date_to = (RelativeLayout) rootView.findViewById(R.id.test_date_to);
        tvtest_date_to = (TextView) rootView.findViewById(R.id.tvtest_date_to);


        spnVesselShips = (Spinner) rootView.findViewById(R.id.spnVesselShips);
        spnBrandandGrades = (Spinner) rootView.findViewById(R.id.spnBrandandGrads);
        spnEquipment = (Spinner) rootView.findViewById(R.id.spnEquipment);

        btnSubmit = (ImageView) rootView.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        sampling_date_from.setOnClickListener(this);
        sampling_date_to.setOnClickListener(this);
        test_date_from.setOnClickListener(this);
        test_date_to.setOnClickListener(this);
        imo_number.setEnabled(true);
        fetchLubeOilReports(prefs.getString("userid", ""));
        return rootView;
    }

    private void fetchLubeOilReports(String userid) {
        {
            main_loader.setVisibility(View.VISIBLE);
            RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.HeadUrl).build();
            final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
            apiInterface.GetUserShipDetails(userid, new Callback<JsonObject>() {
                @Override
                public void success(JsonObject response_data_obj, Response response) {
                    Log.v("RESPONSE==>", response_data_obj.toString());
                    if (response_data_obj != null) {
                        main_loader.setVisibility(View.GONE);
                        ShipdetailsList = new Gson().fromJson(response_data_obj.getAsJsonArray("Shipdetails"), new TypeToken<List<ShipdetailsModel>>() {
                        }.getType());
//                        LoBrandGradesModelList = new Gson().fromJson(response_data_obj.getAsJsonArray("SupplierData"), new TypeToken<List<LoBrandGradesModel>>() {
//                        }.getType());
                        //ship
                        final String[] shipList = new String[ShipdetailsList.size() + 1];
                        int j = 1;
                        shipList[0] = "All Ships*";
                        for (int i = 0; i < ShipdetailsList.size(); i++) {
                            shipList[j] = ShipdetailsList.get(i).getShipName();
                            j++;
                        }
                        //Equipment details

                        final String[] equipmentLisst = new String[EquipmentsMOdelList.size() + 1];
                        int l = 1;
                        equipmentLisst[0] = "All Equipments*";
                        for (int i = 0; i < EquipmentsMOdelList.size(); i++) {
                            equipmentLisst[l] = EquipmentsMOdelList.get(i).getLOEquipmentDescription();
                            l++;
                        }
                        //brand details
//                        final String[] loBrandGradlist = new String[LoBrandGradesModelList.size() + 1];
//                        int k = 1;
//                        loBrandGradlist[0] = "All LOBrandGrades*";
//                        for (int i = 0; i < LoBrandGradesModelList.size(); i++) {
//                            loBrandGradlist[k] = LoBrandGradesModelList.get(i).getLOSupplierBrand();
//                            k++;
//                        }
                        renderDetails(shipList, equipmentLisst);
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
    }

    private void renderDetails(final String[] shipdetailsList, String[] loequipmentModellist) {

        final ArrayAdapter<String> shipdetailsListAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, shipdetailsList);
//        final ArrayAdapter<String> loBrandGradesModelListAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, loBrandGradesModelList);
        final ArrayAdapter<String> equipmentModelListAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, loequipmentModellist);

        shipdetailsListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        loBrandGradesModelListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipmentModelListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnVesselShips.setAdapter(shipdetailsListAdapter);
//        spnBrandandGrades.setAdapter(loBrandGradesModelListAdapter);
        spnEquipment.setAdapter(equipmentModelListAdapter);
//        if (imo_number.getText().toString().isEmpty() && imo_number.getText().toString().equals("")) {
        spnVesselShips.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int shipPosition = spnVesselShips.getSelectedItemPosition();
                if (shipPosition > 0) {
                    shipId = ShipdetailsList.get(shipPosition - 1).getShipId();
//                        getEquipmentDataByShipId(shipId);
                    imo_number.setText("");
                    imo_number.setEnabled(false);
                } else if (shipPosition == 0) {
                    shipId = 0;
                    imo_number.setEnabled(true);
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
                    final ArrayAdapter<String> equipmentModelListAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, equipmentLisst);
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
                    final ArrayAdapter<String> loBrandGradesModelListAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, loBrandGradlist);
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
                    });

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
            case R.id.sampling_date_from:
                selectDate(tvsampling_date_from);
                break;
            case R.id.sampling_date_to:
                selectDate(tvsampling_date_to);
                break;
            case R.id.test_date_from:
                selectDate(tvtest_date_from);
                break;
            case R.id.test_date_to:
                selectDate(tvtest_date_to);
                break;
            case R.id.btnSubmit:
                if (shipId == 0 && imo_number.getText().toString().isEmpty()) {
                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Please enter the value!");
                } else {
                    submitReport();
                }
                break;
        }
    }

    private void submitReport() {
        if (!imo_number.getText().toString().isEmpty() && imo_number.getText().toString().length() > 0) {
            shipId = 0;
        }
        Log.v("FUCK", "SHIPID" + shipId + "EDIT" + imo_number.getText().toString());
        main_loader.setVisibility(View.VISIBLE);
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.HeadUrl).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        apiInterface.GetDataForReport(shipId, imo_number.getText().toString(), new Callback<JsonObject>() {
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
                            renderTheResponse();
                        } else {
                            imo_number.setText("");
                            main_loader.setVisibility(View.GONE);
                            common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Found Details!");
                        }
                    } else {

                        main_loader.setVisibility(View.GONE);
                        common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Found Details!");
                    }

                } catch (Exception e) {

                    main_loader.setVisibility(View.GONE);
                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Found Details!");

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

    private void renderTheResponse() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };

        mRecyclerView.setLayoutManager(layoutManager);
        if (mReportDataModelList != null) {
            mReporterAdapter = new ReporterAdapter(getActivity(), false, mReportDataModelList);
            mRecyclerView.setAdapter(mReporterAdapter);
        } else {
            mRecyclerView.removeAllViews();
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
