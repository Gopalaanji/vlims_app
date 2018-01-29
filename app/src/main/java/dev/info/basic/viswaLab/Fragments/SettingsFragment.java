package dev.info.basic.viswaLab.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.info.basic.viswaLab.Activitys.HomeActivity;
import dev.info.basic.viswaLab.R;

public class SettingsFragment extends BaseFragment {
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.settings));
        return rootView;
    }
}
