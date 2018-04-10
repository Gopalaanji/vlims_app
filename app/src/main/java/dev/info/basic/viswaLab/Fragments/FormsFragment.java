package dev.info.basic.viswaLab.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import dev.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import dev.info.basic.viswaLab.Activitys.WebViewActivity;
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
        LinearLayout btnmsds = (LinearLayout) rootView.findViewById(R.id.btnmsds);
        btnmsds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent(getActivity(), WebViewActivity.class);
                browse.putExtra("pdf_id", "http://173.11.229.171/viswaweb/VLReports/SampleReports/MSDS.PDF");
                getActivity().startActivity(browse);
            }
        });
        LinearLayout btninvoice = (LinearLayout) rootView.findViewById(R.id.btninvoice);
        btninvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent(getActivity(), WebViewActivity.class);
                browse.putExtra("pdf_id", "http://173.11.229.171/viswaweb/VLReports/SampleReports/CI.PDF");
                getActivity().startActivity(browse);
            }
        });
        return rootView;
    }

}
