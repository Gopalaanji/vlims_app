package dev.info.basic.viswaLab.Fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

import dev.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.utils.Common;

/**
 * Created by ${GIRI} on 15-01-2018.
 */

public class SampleStatusFragment extends BaseFragment {
    private View rootView;
    private Common common;
    private LoginFragmentActivity fragmentActivity;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    PieChart mPieChart;
    float[] perce = {20.0f, 40.0f, 60.0f, 10.0f};
    String[] names = {"A", "B", "C", "D"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sample_status, container, false);
        setHasOptionsMenu(false);
        fragmentActivity = (LoginFragmentActivity) getActivity();
        common = new Common();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = prefs.edit();
        fragmentActivity.setActionBarTitle("Sample Status");
        fragmentActivity.showActionBar();
        fragmentActivity.hideBackActionBar();
        mPieChart = (PieChart) rootView.findViewById(R.id.pieChartView);
        Description mDescription = new Description();
        mDescription.setText("Sample Statistics");
        mDescription.setTextSize(25);
        mDescription.setTextAlign(Paint.Align.LEFT);
        mPieChart.setDescription(mDescription);
        mPieChart.setTransparentCircleAlpha(0);
        mPieChart.setRotationEnabled(true);
        mPieChart.setCenterText("VLIMS");
        mPieChart.setHoleRadius(35f);
        addDataToPieChart();

        return rootView;
    }

    private void addDataToPieChart() {
        ArrayList<PieEntry> yEntries = new ArrayList<>();
        ArrayList<String> xEntries = new ArrayList<>();

        for (int i = 0; i < perce.length; i++) {
            yEntries.add(new PieEntry(perce[i], i));
        }
        for (int i = 0; i < names.length; i++) {
            xEntries.add(names[i]);
        }

        //CREATE THE DATA SET
        /*SHOOWING THE COLORS ON TOP WITH TEXT*/
        PieDataSet mPieDataSet = new PieDataSet(yEntries, "Status");
        mPieDataSet.setSliceSpace(2);
        mPieDataSet.setValueTextSize(16);


        //ADD COLORS TO THE DATA SET
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.MAGENTA);
        mPieDataSet.setColors(colors);

        //ADD LEGEND TO THE CHART
        Legend mLegend = mPieChart.getLegend();
        mLegend.setForm(Legend.LegendForm.CIRCLE);
        mLegend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //CREATE PIEDATA
        PieData mPieData = new PieData(mPieDataSet);
        mPieChart.setData(mPieData);
        mPieChart.invalidate();
    }

}
