package dev.info.basic.viswaLab.CautionAlertsReportsPage;

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

/**
 * Created by ${GIRI} on 12-01-2018.
 */

public class CautionAlertAnalysisReport extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private LoginFragmentActivity fragmentActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.caution_analysis_report, container, false);

        setHasOptionsMenu(true);
        fragmentActivity = (LoginFragmentActivity) getActivity();
        fragmentActivity.displayActionBar();
        fragmentActivity.showActionBar();
        fragmentActivity.setActionBarTitle("Viswa Lab");
        fragmentActivity.hideBackActionBar();
        LinearLayout luboilReportsBtn = (LinearLayout) rootView.findViewById(R.id.caution_alerts_luboilReportsBtn);
        LinearLayout fuelOilReportsaBtn = (LinearLayout) rootView.findViewById(R.id.caution_alerts_fuelOilReportsaBtn);
        LinearLayout cylinderoilBtn = (LinearLayout) rootView.findViewById(R.id.caution_alerts_cylinderoilBtn);
        luboilReportsBtn.setOnClickListener(this);
        fuelOilReportsaBtn.setOnClickListener(this);
        cylinderoilBtn.setOnClickListener(this);
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
            case R.id.caution_alerts_luboilReportsBtn:
                fragmentActivity.replaceFragment(new CautionAlertsLubeOilFragment(), "agent_signup", null);
                break;
            case R.id.caution_alerts_fuelOilReportsaBtn:
                fragmentActivity.replaceFragment(new CautionAlertsFuelOilReportsFragment(), "agent_signup", null);
                break;
            case R.id.caution_alerts_cylinderoilBtn:
                fragmentActivity.replaceFragment(new CautionAlertsCylinderOilReportsFragment(), "agent_signup", null);
                break;
        }
    }
}
