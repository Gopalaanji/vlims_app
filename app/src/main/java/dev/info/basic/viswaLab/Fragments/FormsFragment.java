package dev.info.basic.viswaLab.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import dev.info.basic.viswaLab.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormsFragment extends Fragment {

    private LoginFragmentActivity fragmentActivity;

    public FormsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_forms, container, false);
        setHasOptionsMenu(true);
        fragmentActivity = (LoginFragmentActivity) getActivity();
        fragmentActivity.setActionBarTitle("Online Forms");
        return rootView;
    }

}
