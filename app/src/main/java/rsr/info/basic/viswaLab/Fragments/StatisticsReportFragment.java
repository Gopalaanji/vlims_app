package rsr.info.basic.viswaLab.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import rsr.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import rsr.info.basic.viswaLab.R;

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
        Button luboilReportsBtn = (Button) rootView.findViewById(R.id.luboilReportsBtn);
        luboilReportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentActivity.replaceFragment(new StatisticsLubeOilReportsFragment(), "agent_signup", null);
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
