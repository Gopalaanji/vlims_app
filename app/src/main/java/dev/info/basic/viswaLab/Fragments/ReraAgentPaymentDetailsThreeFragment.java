package dev.info.basic.viswaLab.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import dev.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.utils.Common;

/**
 * Created by RSR on 07-09-2017.
 */

public class ReraAgentPaymentDetailsThreeFragment extends BaseFragment implements View.OnClickListener {
    EditText rera_agent_checkNo, rera_agent_checkDate, rera_agent_amount, rera_agent_infavour_of, rera_agent_bankName;
    Button btnnextPayment;
    private View rootView;
    private Common common;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private LoginFragmentActivity fragmentActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.rera_agent_payment_fragment, container, false);
        setHasOptionsMenu(true);
        rera_agent_checkNo = (EditText) rootView.findViewById(R.id.rera_agent_checkNo);
        rera_agent_checkDate = (EditText) rootView.findViewById(R.id.rera_agent_checkDate);
        rera_agent_amount = (EditText) rootView.findViewById(R.id.rera_agent_amount);
        rera_agent_infavour_of = (EditText) rootView.findViewById(R.id.rera_agent_infavour_of);
        rera_agent_bankName = (EditText) rootView.findViewById(R.id.rera_agent_bankName);
        btnnextPayment = (Button) rootView.findViewById(R.id.btnnextPayment);
        btnnextPayment.setOnClickListener(this);
        common = new Common();
        rera_agent_checkNo.setTypeface(common.regularTypeface(getActivity()));
        rera_agent_checkDate.setTypeface(common.regularTypeface(getActivity()));
        rera_agent_amount.setTypeface(common.regularTypeface(getActivity()));
        rera_agent_infavour_of.setTypeface(common.regularTypeface(getActivity()));
        rera_agent_bankName.setTypeface(common.regularTypeface(getActivity()));
        fragmentActivity = (LoginFragmentActivity) getActivity();
        fragmentActivity.displayActionBar();
        fragmentActivity.setActionBarTitle("Payment Details");
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();
        if (prefs.getString("PART_THREE", "").equals("DONE")) {
            rera_agent_checkNo.setText(prefs.getString("BANK_CHECKNO", ""));
            rera_agent_checkDate.setText(prefs.getString("CHECK_DATE", ""));
            rera_agent_amount.setText(prefs.getString("AMOUNT", ""));
            rera_agent_infavour_of.setText(prefs.getString("INFAVOUR_OF", ""));
            rera_agent_bankName.setText(prefs.getString("BANK_NAME", ""));
        }
        fragmentActivity.showActionBar();
        fragmentActivity.displayActionBar();
        fragmentActivity.setActionBarTitle("AGENT PAYMENT DETAILS");
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnnextPayment:
                gotoNextPagePayment();
                break;
        }
    }

    private void gotoNextPagePayment() {
//        editor.putString("BANK_CHECKNO", rera_agent_checkNo.getText().toString().trim());
//        editor.putString("CHECK_DATE", rera_agent_checkDate.getText().toString().trim());
//        editor.putString("AMOUNT", rera_agent_amount.getText().toString().trim());
//        editor.putString("INFAVOUR_OF", rera_agent_infavour_of.getText().toString().trim());
//        editor.putString("BANK_NAME", rera_agent_bankName.getText().toString().trim());
//        editor.putString("PART_THREE", "DONE");
//        editor.commit();
        fragmentActivity.replaceFragment(new LubeOilReportsFragment(), "payments", null);
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
