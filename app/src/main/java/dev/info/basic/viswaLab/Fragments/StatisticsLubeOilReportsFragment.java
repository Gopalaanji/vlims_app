package dev.info.basic.viswaLab.Fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dev.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import dev.info.basic.viswaLab.Adapters.StatisticsluboilAdapter;
import dev.info.basic.viswaLab.ApiInterfaces.ApiInterface;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.models.SampleStatsModel;
import dev.info.basic.viswaLab.utils.Common;
import dev.info.basic.viswaLab.views.TextViewPlus;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ${GIRI} on 12-01-2018.
 */

public class StatisticsLubeOilReportsFragment extends BaseFragment {
    private View rootView;
    private LoginFragmentActivity fragmentActivity;
    ArrayList<SampleStatsModel> statsModels;
    private Common common;

    /*DountChart*/
    Spinner spnrChart;
    PieChart mPieChart;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private RelativeLayout main_loader;
    StatisticsluboilAdapter mStatisticsluboilAdapter;
    RecyclerView mRecyclerView;

    /*Dates*/

    TextView curmnt, premnt, curyear;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_statistics_lube_oil_report, container, false);
        setHasOptionsMenu(true);
        common = new Common();
        fragmentActivity = (LoginFragmentActivity) getActivity();
        fragmentActivity.displayActionBar();
        fragmentActivity.showActionBar();
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();
        fragmentActivity.setActionBarTitle("Sample Statistics");
        fragmentActivity.hideBackActionBar();
        main_loader = (RelativeLayout) rootView.findViewById(R.id.initial_loader);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.mRecyclerView);
        spnrChart = (Spinner) rootView.findViewById(R.id.spnr);
        mPieChart = (PieChart) rootView.findViewById(R.id.mPieChart);
        curmnt = (TextViewPlus) rootView.findViewById(R.id.curmnt);
        premnt = (TextViewPlus) rootView.findViewById(R.id.premnt);
        curyear = (TextViewPlus) rootView.findViewById(R.id.curyear);

        displayDates();

        fetchDetails();
        final ArrayAdapter<String> chartOptionsAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, getResources().getStringArray(R.array.pie_array));
        spnrChart.setAdapter(chartOptionsAdapter);
        spnrChart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                fillUpThePieChart(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return rootView;
    }

    private void displayDates() {
        DateFormat dateFormat = new SimpleDateFormat("MMM");

        Date date = new Date();
        String xx = Calendar.getInstance().get(Calendar.YEAR) + "";
        String month = Calendar.getInstance().get(Calendar.MONTH) - 1 + "";
        String prevMonth = null;
        if (month.equals("0")) {
            prevMonth = "Jan";
        } else if (month.equals("1")) {
            prevMonth = "Feb";
        } else if (month.equals("2")) {
            prevMonth = "Mar";
        } else if (month.equals("3")) {
            prevMonth = "Apr";
        } else if (month.equals("4")) {
            prevMonth = "May";
        } else if (month.equals("5")) {
            prevMonth = "Jun";
        } else if (month.equals("6")) {
            prevMonth = "Jul";
        } else if (month.equals("7")) {
            prevMonth = "Aug";
        } else if (month.equals("8")) {
            prevMonth = "Sep";
        } else if (month.equals("9")) {
            prevMonth = "Oct";
        } else if (month.equals("10")) {
            prevMonth = "Nov";
        } else if (month.equals("11")) {
            prevMonth = "Dec";
        }
//        curmnt.setText(getString(R.string.crtMonth) + "(" + dateFormat.format(date) + "'" + xx.substring(xx.length() - 2) + ")");
//        premnt.setText(getString(R.string.preMonth) + "(" + prevMonth + "'" + xx.substring(xx.length() - 2) + ")");
//        curyear.setText(getString(R.string.crtYear) + "(" + Calendar.getInstance().get(Calendar.YEAR) + ")");
    }

    private void fillUpThePieChart(int pos) {
        Description mDescription = new Description();
        mDescription.setText("Sample Status");
        mDescription.setTextSize(12);
        mDescription.setTextAlign(Paint.Align.RIGHT);
        mPieChart.setDescription(mDescription);
        mPieChart.setTransparentCircleAlpha(180);
        mPieChart.setRotationEnabled(true);
        mPieChart.setCenterText("VLIMS");
        mPieChart.setHoleRadius(35f);
        mPieChart.setTransparentCircleRadius(10f);
        mPieChart.setTransparentCircleAlpha(255);
        ArrayList<PieEntry> yEntries = new ArrayList<>();
        List<String> curMonth = new ArrayList<>();
        String[] xmonths;
        try {
            for (int i = 0; i < statsModels.size(); i++) {
                if (pos == 0) {
                    curMonth.add(statsModels.get(i).getCurrMonthCount());
                } else if (pos == 1) {
                    curMonth.add(statsModels.get(i).getPrevMonthCount());
                } else {
                    curMonth.add(statsModels.get(i).getYTDCount());
                }
            }
            xmonths = curMonth.toArray(new String[curMonth.size()]);
            for (int i = 0; i < xmonths.length; i++) {
                yEntries.add(new PieEntry((float) Double.parseDouble(xmonths[i]), statsModels.get(i).getDataFor()));
            }
        } catch (Exception e) {
        }
        //CREATE THE DATA SET
        /*SHOOWING THE COLORS ON TOP WITH TEXT*/
        PieDataSet mPieDataSet = new PieDataSet(yEntries, "");
        mPieDataSet.setSliceSpace(2);
        mPieDataSet.setValueTextColor(R.color.black);
        mPieDataSet.setValueTextSize(10);
        mPieDataSet.setSelectionShift(5f);
        mPieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        mPieDataSet.setValueLinePart1OffsetPercentage(80.f);
        mPieDataSet.setValueLinePart1Length(0.2f);
        mPieDataSet.setValueLinePart2Length(0.4f);

        //ADD COLORS TO THE DATA SET
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.rgb(255, 165, 0));

        mPieDataSet.setColors(colors);

        //ADD LEGEND TO THE CHART
        Legend mLegend = mPieChart.getLegend();
        mLegend.setForm(Legend.LegendForm.CIRCLE);
        mLegend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);

        //CREATE PIEDATA
        PieData mPieData = new PieData(mPieDataSet);
        mPieChart.setData(mPieData);
//        mPieChart.animateX(2000);
        mPieChart.animateY(2000);
        mPieChart.setEntryLabelTextSize(10f);
        mPieChart.setEntryLabelColor(R.color.black);
//        mPieChart.setEntryLabelTypeface(tf);
        mPieChart.invalidate();

    }

    private void fetchDetails() {

        main_loader.setVisibility(View.VISIBLE);
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.HeadUrl).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        Log.v("xxx", prefs.getString("userid", ""));
        apiInterface.GetSampleSummaryReport(prefs.getString("userid", ""), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                if (jsonObject != null) {
                    main_loader.setVisibility(View.GONE);
                    statsModels = new Gson().fromJson(jsonObject.getAsJsonArray("SummaryDetails"), new TypeToken<List<SampleStatsModel>>() {
                    }.getType());

                    if (statsModels != null) {
                        renderData();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                main_loader.setVisibility(View.GONE);
                common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, getString(R.string.something_went_wrong));
            }
        });

    }

    private void renderData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        mRecyclerView.setLayoutManager(layoutManager);
        if (statsModels != null) {
            mStatisticsluboilAdapter = new StatisticsluboilAdapter(getActivity(), statsModels);
            mRecyclerView.setAdapter(mStatisticsluboilAdapter);
        } else {
            mRecyclerView.removeAllViews();
        }
        fillUpThePieChart(0);
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
