package dev.info.basic.viswaLab.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import dev.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import dev.info.basic.viswaLab.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends BaseFragment {
    LinearLayout muskee_features_container;
    private LoginFragmentActivity fragmentActivity;
    LinearLayout hustonView, singapoorView, ukView;

    public ContactUsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        muskee_features_container = (LinearLayout) view.findViewById(R.id.muskee_features_container);
        renderQuestion(muskee_features_container, "USA,HUSTON", getResources().getString(R.string.huston_address));
        renderQuestion(muskee_features_container, "SINGAPOOR", getResources().getString(R.string.singapoor_address));
        renderQuestion(muskee_features_container, "UNITED KINGDOM", getResources().getString(R.string.uk_address));
        renderQuestion(muskee_features_container, "UNITED ARAB EMIRATES", getResources().getString(R.string.arab_address));
        renderQuestion(muskee_features_container, "BELGIUM", getResources().getString(R.string.belgium_address));
        setHasOptionsMenu(true);
        fragmentActivity = (LoginFragmentActivity) getActivity();
        fragmentActivity.setActionBarTitle("Contact Us");
        return view;
    }

    public void renderQuestion(LinearLayout container, String header, String descriptions) {
        final LinearLayout faq_view = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.muskee_features, container, false);
        final TextView muskee_header = (TextView) faq_view.findViewById(R.id.muskee_header);
        muskee_header.setText(header);
        final LinearLayout maskee_title_description = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.muskee_title_description, faq_view, false);
        ((TextView) maskee_title_description.findViewById(R.id.muskee_description)).setText(Html.fromHtml(descriptions));
        ((LinearLayout) faq_view.findViewById(R.id.maskee_title_description_container)).addView(maskee_title_description);
        faq_view.findViewById(R.id.muskee_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faq_view.findViewById(R.id.maskee_title_description_container).setVisibility(faq_view.findViewById(R.id.maskee_title_description_container).getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
        container.addView(faq_view);
    }
}