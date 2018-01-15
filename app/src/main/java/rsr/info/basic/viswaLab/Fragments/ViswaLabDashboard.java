package rsr.info.basic.viswaLab.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import rsr.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import rsr.info.basic.viswaLab.R;
import rsr.info.basic.viswaLab.utils.Common;

/**
 * Created by E5000096 on 02-10-2016.
 */
public class ViswaLabDashboard extends BaseFragment {
    private View rootView;
    private Common common;
    private LoginFragmentActivity fragmentActivity;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.viswalab_dashboard, container, false);
        setHasOptionsMenu(false);
        fragmentActivity = (LoginFragmentActivity) getActivity();
        common = new Common();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = prefs.edit();
        ImageView luboil = (ImageView) rootView.findViewById(R.id.luboil);
        ImageView imgStats = (ImageView) rootView.findViewById(R.id.imgStats);
        ImageView btncautionAlerts = (ImageView) rootView.findViewById(R.id.btncautionAlerts);
        ImageView btnSampleStatus = (ImageView) rootView.findViewById(R.id.btnSampleStatus);
        TextView tvuserName = (TextView) rootView.findViewById(R.id.tvuserName);
        tvuserName.setText(prefs.getString("Username", ""));
        luboil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentActivity.replaceFragment(new AnalysisReportFragment(), "agent_signup", null);
            }
        });
        imgStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentActivity.replaceFragment(new StatisticsReportFragment(), "agent_signup", null);
            }
        });
        btncautionAlerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentActivity.replaceFragment(new CautionAlertAnalysisReport(), "agent_signup", null);
            }
        });
        btnSampleStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentActivity.replaceFragment(new SampleStatusFragment(), "agent_signup", null);
            }
        });
//        fragmentActivity.displayActionBar();
        fragmentActivity.setActionBarTitle("Viswa Lab");
        fragmentActivity.showActionBar();
        fragmentActivity.hideBackActionBar();
        return rootView;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setVisible(false);
        switch (item.getItemId()) {
            case android.R.id.home:
                fragmentActivity.loadPopBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

}
