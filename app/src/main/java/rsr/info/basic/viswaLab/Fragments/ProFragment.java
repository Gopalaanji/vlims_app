package rsr.info.basic.viswaLab.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rsr.info.basic.viswaLab.Activitys.HomeActivity;
import rsr.info.basic.viswaLab.R;


public class ProFragment extends BaseFragment {
    private View rootView;
    LinearLayout muskee_features_container;
    List<String> notifications_titles;
    List<String> notification_descriptions;
    List<String> social_media_titles;
    List<String> social_media_descriptions;
    List<String> water_mark_titles;
    List<String> water_mark_descriptions;
    List<String> multiac_titles;
    List<String> multiac_descriptions;
    //TOOLTIP

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_pro, container, false);
        muskee_features_container = (LinearLayout) rootView.findViewById(R.id.muskee_features_container);
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.pro));
        //NOTIFICATIONS
        notifications_titles = new ArrayList<String>();
        notifications_titles.add("Why Notifications");
        notifications_titles.add("Types OF Notifications");
        notifications_titles.add("Company Announcements*");
        notifications_titles.add("Notifications From Customers*");
        notification_descriptions = new ArrayList<String>();
        notification_descriptions.add("Notification is a thread system of medium where Administration and its Employes can Interact with each other throgh confident media.");
        notification_descriptions.add("We figure out Notifications into 3 types,Administration to Agents(A TO A),Administration to Customers(A To C), Customers to Agents(C To A");
        notification_descriptions.add("Company Meetings,Company Events, Everything Will Announce to the Agents Professionally.");
        notification_descriptions.add("Now keep your Customers contact with you when they are intrested to buy Plots.");
        renderQuestion(muskee_features_container, "NOTIFICATIONS", notifications_titles, notification_descriptions);
        //SOCIAL MEDIA
        social_media_titles = new ArrayList<String>();
        social_media_titles.add("Form Refresh");
        social_media_titles.add("Jersey Colour and Number");
        social_media_titles.add("Team Customization");
        social_media_titles.add("Press Release");
        social_media_titles.add("Hall of Fame*");
        social_media_descriptions = new ArrayList<String>();
        social_media_descriptions.add("Bad form update before an important match? try the 'Form Refresh' option and try for a better update!");
        social_media_descriptions.add("Assign Jersey numbers to your players");
        social_media_descriptions.add("Customize your team page with Team Logo and Team Motto");
        social_media_descriptions.add("Keep your fans/friends updated about the latest happenings about your team with your own press release");
        social_media_descriptions.add("Make your star player immortal by inducting him in your club's Hall Of Fame");
        renderQuestion(muskee_features_container, "SOCIAL MEDIA", social_media_titles, social_media_descriptions);
//WATERMARK VIDEO
        water_mark_titles = new ArrayList<String>();
        water_mark_titles.add("Auto Bidding");
        water_mark_titles.add("Transfer Guru");
        water_mark_titles.add("2 Days Transfer");
        water_mark_titles.add("Player Market Price");
        water_mark_titles.add("Pre Schedule Transfers");
        water_mark_descriptions = new ArrayList<String>();
        water_mark_descriptions.add("You can instruct your Assistant Managers (who is ofcourse online 24/7) to place bids on a player on a your behalf upto a maximum limit.");
        water_mark_descriptions.add("Trouble finding players? Ask your transfer guru to notify you when a player of a pre-defined criteria is listed for sale.");
        water_mark_descriptions.add("Don't like waiting 3 days to get your money? List a player with 48hr or 72hr deadline.");
        water_mark_descriptions.add("Worried about overpaying? or wondering how much you could get for your player? Checkout the selling prices of players of similar skill.");
        water_mark_descriptions.add("Want to sell your player at peak time but keep forgetting? Musketeers can schedule the Transfer listing of their player!");
        renderQuestion(muskee_features_container, "IMAGES", water_mark_titles, water_mark_descriptions);
        //WATERMARK VIDEO
        water_mark_titles = new ArrayList<String>();
        water_mark_titles.add("Auto Bidding");
        water_mark_titles.add("Transfer Guru");
        water_mark_titles.add("2 Days Transfer");
        water_mark_titles.add("Player Market Price");
        water_mark_titles.add("Pre Schedule Transfers");
        water_mark_descriptions = new ArrayList<String>();
        water_mark_descriptions.add("You can instruct your Assistant Managers (who is ofcourse online 24/7) to place bids on a player on a your behalf upto a maximum limit.");
        water_mark_descriptions.add("Trouble finding players? Ask your transfer guru to notify you when a player of a pre-defined criteria is listed for sale.");
        water_mark_descriptions.add("Don't like waiting 3 days to get your money? List a player with 48hr or 72hr deadline.");
        water_mark_descriptions.add("Worried about overpaying? or wondering how much you could get for your player? Checkout the selling prices of players of similar skill.");
        water_mark_descriptions.add("Want to sell your player at peak time but keep forgetting? Musketeers can schedule the Transfer listing of their player!");
        renderQuestion(muskee_features_container, "VIDEOS", water_mark_titles, water_mark_descriptions);
        //multiAcount
        //youth scout
        multiac_titles = new ArrayList<String>();
        multiac_titles.add("Scout Type Preference");
        multiac_titles.add("+2 International Countries*");
        multiac_descriptions = new ArrayList<String>();
        multiac_descriptions.add("Tired of getting a batsman when you really a need bowling trainee? Now you can tell your scout to look for a particular type of player.");
        multiac_descriptions.add("Two more countries are randomly unlocked for a Musketeer. These countries stay unlocked even after expiry of your Musketeer subscription.");
        renderQuestion(muskee_features_container, "MULTIPLE ACCOUNTS", multiac_titles, multiac_descriptions);
        return rootView;
    }

    public void renderQuestion(LinearLayout container, final String head, List<String> titles, List<String> descriptions) {
        final LinearLayout faq_view = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.muskee_features, container, false);
        final TextView muskee_header = (TextView) faq_view.findViewById(R.id.muskee_header);
        muskee_header.setText(head);
        for (int i = 0; i < titles.size(); i++) {
            final LinearLayout maskee_title_description = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.muskee_title_description, faq_view, false);
            ((TextView) maskee_title_description.findViewById(R.id.muskee_title)).setText(titles.get(i).toString());
            ((TextView) maskee_title_description.findViewById(R.id.muskee_description)).setText(Html.fromHtml(descriptions.get(i).toString()));
            ((LinearLayout) faq_view.findViewById(R.id.maskee_title_description_container)).addView(maskee_title_description);
        }
        if (head.equalsIgnoreCase("NOTIFICATIONS")) {
            muskee_header.setBackgroundColor(Color.parseColor("#B3990000"));
        } else if (head.equalsIgnoreCase("SOCIAL MEDIA")) {
            muskee_header.setBackgroundColor(Color.parseColor("#B314A82D"));
            faq_view.findViewById(R.id.maskee_title_description_container).setVisibility(View.VISIBLE);
        } else if (head.equalsIgnoreCase("VIDEOS")) {
            muskee_header.setBackgroundColor(Color.parseColor("#B3FFCC00"));
        } else if (head.equalsIgnoreCase("MULTIPLE ACCOUNTS")) {
            muskee_header.setBackgroundColor(Color.parseColor("#B351B2D5"));
        } else if (head.equalsIgnoreCase("IMAGES")) {
            muskee_header.setBackgroundColor(Color.parseColor("#B3ff7373"));
        }
        faq_view.findViewById(R.id.muskee_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faq_view.findViewById(R.id.maskee_title_description_container).setVisibility(faq_view.findViewById(R.id.maskee_title_description_container).getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
        container.addView(faq_view);
    }
}
