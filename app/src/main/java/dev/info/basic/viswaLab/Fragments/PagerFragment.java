package dev.info.basic.viswaLab.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import dev.info.basic.viswaLab.R;

/**
 * Created by E5000096 on 21-10-2016.
 */
public class PagerFragment extends BaseFragment {

    private View rootView;
    ImageView imageView;
    ImageView imageView1;
    TextView titleTextView;
    TextView descTextView;
    TextView orTextView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.pager_screen_layout, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.pager_image);
        imageView1 = (ImageView) rootView.findViewById(R.id.pager_image_1);
        titleTextView = (TextView) rootView.findViewById(R.id.pager_title);
        descTextView = (TextView) rootView.findViewById(R.id.pager_desc);
        orTextView = (TextView) rootView.findViewById(R.id.pager_or);
        imageView.setBackgroundResource(getArguments().getInt("pos"));
        if (getArguments().containsKey("pos1")) {
            imageView1.setBackgroundResource(getArguments().getInt("pos1"));
            imageView1.setVisibility(View.VISIBLE);
            orTextView.setText(getString(R.string.or).toLowerCase());
            orTextView.setVisibility(View.VISIBLE);
        }
        titleTextView.setText(getArguments().getString("title"));
        descTextView.setText(getArguments().getString("desc"));
        return rootView;
    }
}
