package dev.info.basic.viswaLab.AnalysisReportsPage.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import dev.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import dev.info.basic.viswaLab.Fragments.BaseFragment;
import dev.info.basic.viswaLab.R;


public class AnalysisReportFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private LoginFragmentActivity fragmentActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_analysis_report, container, false);

        setHasOptionsMenu(true);
        fragmentActivity = (LoginFragmentActivity) getActivity();
        fragmentActivity.displayActionBar();
        fragmentActivity.showActionBar();
        fragmentActivity.setActionBarTitle("Viswa Lab");
        fragmentActivity.hideBackActionBar();
        LinearLayout luboilReportsBtn = (LinearLayout) rootView.findViewById(R.id.luboilReportsBtn);
        LinearLayout fuelOilReportsaBtnAnalysisReport = (LinearLayout) rootView.findViewById(R.id.fuelOilReportsaBtnAnalysisReport);
        LinearLayout cylinderOilReportsaBtnAnalysisReport = (LinearLayout) rootView.findViewById(R.id.cylinderOilReportsaBtnAnalysisReport);
        LinearLayout pompOilReportsaBtnAnalysisReport = (LinearLayout) rootView.findViewById(R.id.pompOilReportsaBtnAnalysisReport);
        LinearLayout purifierEfficiencyReportsBtn = (LinearLayout) rootView.findViewById(R.id.purifierEfficiencyReportsBtn);
        LinearLayout additionalTestReportsBtn = (LinearLayout) rootView.findViewById(R.id.additionalTestReportsBtn);
        LinearLayout densitybtn = (LinearLayout) rootView.findViewById(R.id.densitybtn);
        luboilReportsBtn.setOnClickListener(this);
        fuelOilReportsaBtnAnalysisReport.setOnClickListener(this);
        cylinderOilReportsaBtnAnalysisReport.setOnClickListener(this);
        pompOilReportsaBtnAnalysisReport.setOnClickListener(this);
        purifierEfficiencyReportsBtn.setOnClickListener(this);
        additionalTestReportsBtn.setOnClickListener(this);
        densitybtn.setOnClickListener(this);
        return rootView;
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
            case R.id.luboilReportsBtn:
                fragmentActivity.replaceFragment(new AnalysisReportsLubeOilReportsFragment(), "agent_signup", null);
                break;
                case R.id.fuelOilReportsaBtnAnalysisReport:
                fragmentActivity.replaceFragment(new AnalysisReportsFuelOilReportsFragment(), "agent_signup", null);
                break;
                case R.id.cylinderOilReportsaBtnAnalysisReport:
                fragmentActivity.replaceFragment(new AnalysisReportsCylinderOilReportsFragment(), "agent_signup", null);
                break;
                case R.id.pompOilReportsaBtnAnalysisReport:
                fragmentActivity.replaceFragment(new AnalysisReportsPOMPpage(), "agent_signup", null);
                break;
                case R.id.purifierEfficiencyReportsBtn:
                fragmentActivity.replaceFragment(new AnalysisReportsPurifierEfficiencyReportsFragment(), "agent_signup", null);
                break;
                case R.id.additionalTestReportsBtn:
                fragmentActivity.replaceFragment(new AnalysisReportsAdditionalTestReportsFragment(), "agent_signup", null);
                break;
                case R.id.densitybtn:
                fragmentActivity.replaceFragment(new AnalysisReportDensityDiffFragment(), "agent_signup", null);
                break;
        }
    }
}
