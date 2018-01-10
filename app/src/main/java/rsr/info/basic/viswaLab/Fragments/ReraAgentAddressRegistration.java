package rsr.info.basic.viswaLab.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rsr.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import rsr.info.basic.viswaLab.R;
import rsr.info.basic.viswaLab.utils.Common;

/**
 * Created by RSR on 06-09-2017.
 */

public class ReraAgentAddressRegistration extends BaseFragment implements View.OnClickListener {
    EditText rera_agent_houseNo, rera_agent_buildingName, rera_agent_streetName, rera_agent_locality, rera_agent_landmark, rera_agent_state, rera_agent_district, rera_agent_mandal, rera_agent_village, rera_agent_pincode;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private LoginFragmentActivity fragmentActivity;
    private View rootView;
    Button btnNextPage;
    private Common common;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.rera_agent_address_fragment, container, false);
        fragmentActivity = (LoginFragmentActivity) getActivity();
        setHasOptionsMenu(true);
        common = new Common();
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();
        rera_agent_houseNo = (EditText) rootView.findViewById(R.id.rera_agent_houseNo);
        rera_agent_buildingName = (EditText) rootView.findViewById(R.id.rera_agent_buildingName);
        rera_agent_streetName = (EditText) rootView.findViewById(R.id.rera_agent_streetName);
        rera_agent_locality = (EditText) rootView.findViewById(R.id.rera_agent_locality);
        rera_agent_landmark = (EditText) rootView.findViewById(R.id.rera_agent_landmark);
        rera_agent_state = (EditText) rootView.findViewById(R.id.rera_agent_state);
        rera_agent_district = (EditText) rootView.findViewById(R.id.rera_agent_district);
        rera_agent_mandal = (EditText) rootView.findViewById(R.id.rera_agent_mandal);
        rera_agent_village = (EditText) rootView.findViewById(R.id.rera_agent_village);
        rera_agent_pincode = (EditText) rootView.findViewById(R.id.rera_agent_pincode);
        btnNextPage = (Button) rootView.findViewById(R.id.btnNextPage);
        rera_agent_houseNo.setTypeface(common.regularTypeface(getActivity()));
        rera_agent_buildingName.setTypeface(common.regularTypeface(getActivity()));
        rera_agent_streetName.setTypeface(common.regularTypeface(getActivity()));
        rera_agent_locality.setTypeface(common.regularTypeface(getActivity()));
        rera_agent_landmark.setTypeface(common.regularTypeface(getActivity()));
        rera_agent_state.setTypeface(common.regularTypeface(getActivity()));
        rera_agent_district.setTypeface(common.regularTypeface(getActivity()));
        rera_agent_mandal.setTypeface(common.regularTypeface(getActivity()));
        rera_agent_village.setTypeface(common.regularTypeface(getActivity()));
        rera_agent_pincode.setTypeface(common.regularTypeface(getActivity()));
        btnNextPage.setOnClickListener(this);
        if (prefs.getString("PART_TWO", "").equals("DONE")) {
            rera_agent_houseNo.setText(prefs.getString("AGENT_HNO", ""));
            rera_agent_buildingName.setText(prefs.getString("AGENT_BUILDINGNAME", ""));
            rera_agent_streetName.setText(prefs.getString("AGENT_STREETNAME", ""));
            rera_agent_locality.setText(prefs.getString("AGENT_LOCALITY", ""));
            rera_agent_landmark.setText(prefs.getString("AGENT_LANDMARK", ""));
            rera_agent_state.setText(prefs.getString("AGENT_STATE", ""));
            rera_agent_district.setText(prefs.getString("AGENT_DISTRICT", ""));
            rera_agent_mandal.setText(prefs.getString("AGENT_MANDAL", ""));
            rera_agent_village.setText(prefs.getString("AGENT_VILLAGE", ""));
            rera_agent_pincode.setText(prefs.getString("AGENT_PINCODE", ""));
        }
        Log.v("PrefValues", prefs.getString("AGENT_FIRSTNAME", ""));
        Log.v("PrefValues", prefs.getString("AGENT_LASTNAME", ""));
        Log.v("PrefValues", prefs.getString("AGENT_PANNUMBER", ""));
        Log.v("PrefValues", prefs.getString("AGENT_AADHAR_NUMBER", ""));
        Log.v("PrefValues", prefs.getString("AGENT_FATHERNAME", ""));
        Log.v("PrefValues", prefs.getString("AGENT_OCCUPTION", ""));
        Log.v("PrefValues", prefs.getString("AGENT_PASTEXPERIANCE", ""));
        Log.v("PrefValues", prefs.getString("AGENT_CRIMINALCASE", ""));
        Log.v("PrefValues", prefs.getString("AGENT_REGOTHERSTATE", ""));
        Log.v("PrefValues", prefs.getString("PART_ONE", ""));
        fragmentActivity.displayActionBar();
        fragmentActivity.showActionBar();
        fragmentActivity.setActionBarTitle("ADDRESS FOR OFFICIAL COMMUNICATION");
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextPage:
//                gotoNextPage();
                fragmentActivity.replaceFragment(new ReraAgentPaymentDetailsThreeFragment(), "payment", null);
                break;
        }
    }

    private void gotoNextPage() {
        if (Common.isNetworkAvailable(getActivity())) {
            if (Common.stringValidation(rera_agent_houseNo.getText().toString().trim()).length() != 0) {
                if (Common.stringValidation(rera_agent_buildingName.getText().toString().trim()).length() != 0) {
                    if (Common.stringValidation(rera_agent_streetName.getText().toString().trim()).length() != 0) {
                        if (Common.stringValidation(rera_agent_locality.getText().toString().trim()).length() != 0) {
                            if (Common.stringValidation(rera_agent_landmark.getText().toString().trim()).length() != 0) {
                                if (Common.stringValidation(rera_agent_state.getText().toString().trim()).length() != 0) {
                                    if (Common.stringValidation(rera_agent_district.getText().toString().trim()).length() != 0) {
                                        if (Common.stringValidation(rera_agent_mandal.getText().toString().trim()).length() != 0) {
                                            if (Common.stringValidation(rera_agent_village.getText().toString().trim()).length() != 0) {
                                                if (Common.stringValidation(rera_agent_pincode.getText().toString().trim()).length() != 0) {
                                                    saveValuesGotoNextpage();
                                                } else
                                                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Agent Pincode cannot be empty!");
                                            } else
                                                common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Agent Village cannot be empty!");
                                        } else
                                            common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Agent Mandal cannot be empty!");
                                    } else
                                        common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Agent District cannot be empty!");
                                } else
                                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Agent State cannot be empty!");
                            } else
                                common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Agent Landmark cannot be empty!");
                        } else
                            common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Agent Locality cannot be empty!");
                    } else
                        common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Agent Street Name cannot be empty!");
                } else
                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Agent Building Name cannot be empty!");
            } else
                common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Agent House No cannot be empty!");
        } else
            common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, getString(R.string.network_error));
    }

    private void saveValuesGotoNextpage() {
        editor.putString("AGENT_HNO", rera_agent_houseNo.getText().toString().trim());
        editor.putString("AGENT_BUILDINGNAME", rera_agent_buildingName.getText().toString().trim());
        editor.putString("AGENT_STREETNAME", rera_agent_streetName.getText().toString().trim());
        editor.putString("AGENT_LOCALITY", rera_agent_locality.getText().toString().trim());
        editor.putString("AGENT_LANDMARK", rera_agent_landmark.getText().toString().trim());
        editor.putString("AGENT_STATE", rera_agent_state.getText().toString().trim());
        editor.putString("AGENT_DISTRICT", rera_agent_district.getText().toString().trim());
        editor.putString("AGENT_MANDAL", rera_agent_mandal.getText().toString().trim());
        editor.putString("AGENT_VILLAGE", rera_agent_village.getText().toString().trim());
        editor.putString("AGENT_PINCODE", rera_agent_pincode.getText().toString().trim());
        editor.putString("PART_TWO", "DONE");
        editor.commit();
        fragmentActivity.replaceFragment(new ReraAgentPaymentDetailsThreeFragment(), "payment", null);
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
