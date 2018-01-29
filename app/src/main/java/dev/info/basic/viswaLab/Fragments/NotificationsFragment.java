package dev.info.basic.viswaLab.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import dev.info.basic.viswaLab.Activitys.HomeActivity;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.views.TextViewPlus;


public class NotificationsFragment extends BaseFragment {
    dev.info.basic.viswaLab.views.TextViewPlus contactMobile, contactEmail, contactPhone_one, contactPhone_two;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).setActionBarTitle("WELCOME TO " + getString(R.string.gst));
        View rootView = inflater.inflate(R.layout.invitation_card, container, false);
        final LinearLayout full_screen_popup = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.natasha_ok, null, false);
        Button btn_popup_close = (Button) full_screen_popup.findViewById(R.id.tutorial_full_screen_close_button);
        TextView tutorial_full_screen_help_message = (TextView) full_screen_popup.findViewById(R.id.tutorial_full_screen_help_message);
        tutorial_full_screen_help_message.setText(getString(R.string.invitation_msg));
        ((ViewGroup) getActivity().getWindow().getDecorView()).addView(full_screen_popup);
        btn_popup_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((ViewGroup) getActivity().getWindow().getDecorView()).removeView(full_screen_popup);
                } catch (Exception e) {
                    Log.v("Ex", e.toString());
                }
            }
        });
        full_screen_popup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                full_screen_popup.setX(full_screen_popup.getWidth());
                full_screen_popup.animate().translationX(0).setDuration(1000).start();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    full_screen_popup.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    full_screen_popup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
        ImageView map_img = (ImageView) rootView.findViewById(R.id.map);
        contactMobile = (TextViewPlus) rootView.findViewById(R.id.contactMobile);
        contactEmail = (TextViewPlus) rootView.findViewById(R.id.contactEmail);
        contactPhone_one = (TextViewPlus) rootView.findViewById(R.id.contactPhone_one);
        contactPhone_two = (TextViewPlus) rootView.findViewById(R.id.contactPhone_two);
        contactMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactRsr("8008020666");
            }
        });
        contactPhone_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactRsr("040 65886632");
            }
        });
        contactPhone_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactRsr("040 65886633");
            }
        });
        contactEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String[] recipients = {"rajashekar.mooli@rsrinfotech.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT, "GST & RERA INVITATION");
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Send mail"));
            }
        });
        map_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=1600 Raddison Blu Plaza Hotel,Banjarahills, Hyderabad");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        return rootView;
    }
}
