package dev.info.basic.viswaLab.StatisticsReportPage.Fragments;

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

public class StatisticsReportFragment extends BaseFragment {
    private View rootView;
    private LoginFragmentActivity fragmentActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_statistics_analysis_report, container, false);

        setHasOptionsMenu(true);
        fragmentActivity = (LoginFragmentActivity) getActivity();
        fragmentActivity.displayActionBar();
        fragmentActivity.showActionBar();
        fragmentActivity.setActionBarTitle("Viswa Lab");
        fragmentActivity.hideBackActionBar();
        LinearLayout luboilReportsBtn = (LinearLayout) rootView.findViewById(R.id.luboilReportsBtn);
        LinearLayout fueloilReportsBtn = (LinearLayout) rootView.findViewById(R.id.fueloilReportsBtn);
        LinearLayout cylinerBtn = (LinearLayout) rootView.findViewById(R.id.cylinerBtn);
        LinearLayout additionalBtn = (LinearLayout) rootView.findViewById(R.id.additionalBtn);
        LinearLayout pureffyBtn = (LinearLayout) rootView.findViewById(R.id.pureffyBtn);
        LinearLayout pompReportsBtn = (LinearLayout) rootView.findViewById(R.id.pompReportsBtn);
        luboilReportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentActivity.replaceFragment(new StatisticsLubeOilReportsFragment(), "agent_signup", null);
            }
        });
        fueloilReportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentActivity.replaceFragment(new StatisticsFuelOilFragment(), "agent_signup", null);
            }
        });
        cylinerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentActivity.replaceFragment(new StatisticsCylinderOilFragment(), "agent_signup", null);
            }
        });
        additionalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentActivity.replaceFragment(new StatisticsAdditionalReportsFragment(), "agent_signup", null);
            }
        });
        pompReportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentActivity.replaceFragment(new StatisticsPOMPReportsFragment(), "agent_signup", null);
            }
        });
        pureffyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentActivity.replaceFragment(new StatisticsPurEffyReportsFragment(), "agent_signup", null);
            }
        });
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
}
