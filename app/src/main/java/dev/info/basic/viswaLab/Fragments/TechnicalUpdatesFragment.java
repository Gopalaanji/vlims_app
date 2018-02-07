package dev.info.basic.viswaLab.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import dev.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import dev.info.basic.viswaLab.Adapters.TechnicalUpdatesAdapter;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.models.TechnicalUpdatesModel;
import dev.info.basic.viswaLab.utils.Common;

public class TechnicalUpdatesFragment extends BaseFragment implements View.OnClickListener{


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
        fragmentActivity.setActionBarTitle("Schedule Alerts");
        main_loader = (RelativeLayout) rootView.findViewById(R.id.initial_loader);
        technicalUpdatesRecyclerView = (RecyclerView) rootView.findViewById(R.id.technicalUpdatesRecyclerView);
        return rootView;
    }


    @Override
    public void onClick(View v) {

    }
}