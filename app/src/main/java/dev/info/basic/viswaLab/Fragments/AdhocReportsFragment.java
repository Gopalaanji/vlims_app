package dev.info.basic.viswaLab.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dev.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import dev.info.basic.viswaLab.Adapters.AdhocReportsAdapter;
import dev.info.basic.viswaLab.AnalysisReportsPage.Adapters.AnalysisAdditionalTestAdapter;
import dev.info.basic.viswaLab.ApiInterfaces.ApiInterface;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.models.AdhocReportsModel;
import dev.info.basic.viswaLab.utils.Common;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AdhocReportsFragment extends BaseFragment {

    private LoginFragmentActivity fragmentActivity;
    private View rootView;
    private Common common;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private RelativeLayout main_loader;
    ArrayList<AdhocReportsModel> mAdhocReportsModelsList;
    RecyclerView adhocReportsRecyclerView;
    AdhocReportsAdapter mAdhocReportsAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_adhoc_reports, container, false);
        setHasOptionsMenu(true);
        fragmentActivity = (LoginFragmentActivity) getActivity();
        common = new Common();
        fragmentActivity.displayActionBar();
        fragmentActivity.setActionBarTitle("Other Test Reports");
        fragmentActivity.showActionBar();
        fragmentActivity.hideBackActionBar();
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();
        main_loader = (RelativeLayout) rootView.findViewById(R.id.initial_loader);
        adhocReportsRecyclerView = (RecyclerView) rootView.findViewById(R.id.adhocReportsRecyclerView);
        fetchAdHocReports();
        return rootView;
    }

    private void fetchAdHocReports() {
        main_loader.setVisibility(View.VISIBLE);
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        apiInterface.GetAdhocReports(prefs.getString("userid", ""),new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
                Log.v("RESPONSE==>", response_data_obj.toString());
                if (response_data_obj != null) {
                    main_loader.setVisibility(View.GONE);
                    mAdhocReportsModelsList = new Gson().fromJson(response_data_obj.getAsJsonArray("adhocreports"), new TypeToken<List<AdhocReportsModel>>() {
                    }.getType());
                    if (mAdhocReportsModelsList != null) {
                        renderTheResponse();
                    } else {
                        main_loader.setVisibility(View.GONE);
                        common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Could Not Found Details!");
                    }
                } else {
                    main_loader.setVisibility(View.GONE);
                    showToast(getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                main_loader.setVisibility(View.GONE);
showToast();            }
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
        adhocReportsRecyclerView.setLayoutManager(layoutManager);
        if (mAdhocReportsModelsList != null) {
            mAdhocReportsAdapter = new AdhocReportsAdapter(getActivity(), mAdhocReportsModelsList, new AdhocReportsAdapter.AdhocReportsListener() {
                @Override
                public void itemClicked(String pdfname, String testname) {
                    showPdf(pdfname,"ADD",testname);
                }
            });
            adhocReportsRecyclerView.setAdapter(mAdhocReportsAdapter);
        }
    }

}