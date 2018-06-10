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
import dev.info.basic.viswaLab.Adapters.TechnicalUpdatesAdapter;
import dev.info.basic.viswaLab.ApiInterfaces.ApiInterface;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.models.TechnicalUpdatesModel;
import dev.info.basic.viswaLab.utils.Common;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TechnicalUpdatesFragment extends BaseFragment{


    private LoginFragmentActivity fragmentActivity;
    private View rootView;
    private Common common;
    private RelativeLayout main_loader;
    RecyclerView technicalUpdatesRecyclerView;
    TechnicalUpdatesAdapter mTechnicalUpdatesAdapter;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    ArrayList<TechnicalUpdatesModel> mTechnicalUpdatesList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_technical_updates, container, false);
        setHasOptionsMenu(true);
        common = new Common();
        fragmentActivity = (LoginFragmentActivity) getActivity();
        fragmentActivity.displayActionBar();
        fragmentActivity.showActionBar();
        fragmentActivity.hideBackActionBar();
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();
        fragmentActivity.setActionBarTitle("Technical Updates");
        main_loader = (RelativeLayout) rootView.findViewById(R.id.initial_loader);
        technicalUpdatesRecyclerView = (RecyclerView) rootView.findViewById(R.id.technicalUpdatesRecyclerView);
        fetchTechnicalUpdateReports();
        return rootView;
    }

    private void fetchTechnicalUpdateReports() {
        main_loader.setVisibility(View.VISIBLE);
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        apiInterface.GetTechnicalUpdates(new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
                Log.v("RESPONSE==>", response_data_obj.toString());
                if (response_data_obj != null) {
                    main_loader.setVisibility(View.GONE);
                    mTechnicalUpdatesList = new Gson().fromJson(response_data_obj.getAsJsonArray("UserReportdata"), new TypeToken<List<TechnicalUpdatesModel>>() {
                    }.getType());
                    if (mTechnicalUpdatesList != null) {
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
        technicalUpdatesRecyclerView.setLayoutManager(layoutManager);
        if (mTechnicalUpdatesList != null) {
            mTechnicalUpdatesAdapter = new TechnicalUpdatesAdapter(getActivity(), mTechnicalUpdatesList, new TechnicalUpdatesAdapter.TechnicalUpdateListener() {
                @Override
                public void itemClicked(String pdfname) {
                    showPdf(pdfname,"TU","");
                }
            });
            technicalUpdatesRecyclerView.setAdapter(mTechnicalUpdatesAdapter);
        }
    }


}