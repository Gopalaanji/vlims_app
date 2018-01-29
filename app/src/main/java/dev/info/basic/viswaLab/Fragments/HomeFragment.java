package dev.info.basic.viswaLab.Fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import dev.info.basic.viswaLab.Activitys.HomeActivity;
import dev.info.basic.viswaLab.Activitys.WebViewActivity;
import dev.info.basic.viswaLab.Adapters.ProjectsAdapter;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.models.NewProjectDto;
import dev.info.basic.viswaLab.utils.EndlessScrollListener;

public class HomeFragment extends BaseFragment {
    private View rootView;
    ListView listView;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private TextView noData;
    public RelativeLayout main_loader;
    HomeActivity fragmentActivity;
    //LOADMORE
    public boolean hasMore;
    public Boolean loading_more = false;
    List<NewProjectDto> projects;
    private ProjectsAdapter projects_adapter;
    public View notification_list_loader;
    //VIEW ANIMATOR
    public ObjectAnimator arrow_animation = null;
    ImageView pro_target_pointer;
    int offsetValue = 10;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.home));
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();
        initViews();
        return rootView;
    }

    private void initViews() {
        fragmentActivity = (HomeActivity) getActivity();
        listView = (ListView) rootView.findViewById(R.id.listView);
        noData = (TextView) rootView.findViewById(R.id.noData);
        main_loader = (RelativeLayout) rootView.findViewById(R.id.initial_loader);
        pro_target_pointer = (ImageView) rootView.findViewById(R.id.pro_target_pointer);
        Log.v("companyid", prefs.getString("companyid", ""));
        if (!getActivity().getSharedPreferences("rsr.info.basic.pricedproperty", Context.MODE_PRIVATE).getBoolean("pro_indicator_shown", false)) {
            showIndicator();
        }
//        getProjectDetails(prefs.getString("companyId", ""));
    }

    private void showIndicator() {
        final LinearLayout full_screen_popup = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.natasha_ok, null, false);
        Button btn_popup_close = (Button) full_screen_popup.findViewById(R.id.tutorial_full_screen_close_button);
        pro_target_pointer.setVisibility(View.VISIBLE);
        if (arrow_animation != null)
            arrow_animation.end();
        arrow_animation = ObjectAnimator.ofFloat(pro_target_pointer, "translationY", 20).setDuration(250);
        arrow_animation.setRepeatMode(ValueAnimator.REVERSE);
        arrow_animation.setRepeatCount(ValueAnimator.INFINITE);
        arrow_animation.start();

        ((ViewGroup) getActivity().getWindow().getDecorView()).addView(full_screen_popup);
        btn_popup_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewGroup) getActivity().getWindow().getDecorView()).removeView(full_screen_popup);
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
        getActivity().getSharedPreferences("rsr.info.basic.pricedproperty", Context.MODE_PRIVATE).edit().putBoolean("pro_indicator_shown", true).apply();
    }

/*
    private void getProjectDetails(final String companyId) {
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.HeadUrl).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        apiInterface.getProjectDetails(companyId, offsetValue, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject response_data_obj, Response response) {
                main_loader.setVisibility(View.GONE);
                try {
                    projects = new Gson().fromJson(response_data_obj.getAsJsonArray("Projects"), new TypeToken<List<NewProjectDto>>() {
                    }.getType());
                    hasMore = response_data_obj.get("hasMore").getAsBoolean();
                    offsetValue = Integer.parseInt(response_data_obj.get("offsetvalue").getAsString());
                    render(companyId);
                } catch (Exception e) {
                    noData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
*/

    private void render(final String companyId) {
        notification_list_loader = getActivity().getLayoutInflater().inflate(R.layout.notification_list_loader, null, false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
//                Intent intent = new Intent(ProjectsByClustorId.this, WebViewActivity.class);
                Bundle bundle = new Bundle();
//                bundle.putString("project_code", projects.get(position - 1).getProject_code());
                bundle.putString("frame_id", projects.get(position - 1).getProject_mobileview_url());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
//                loadMore(companyId);
            }
        });
        listView.addHeaderView(new View(getActivity()));
        projects_adapter = new ProjectsAdapter(getActivity(), projects);
        listView.setAdapter(projects_adapter);
    }

/*
    private void loadMore(String companyId) {
        if (hasMore && !loading_more) {
            loading_more = true;
            listView.addFooterView(notification_list_loader, null, false);
            RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.HeadUrl).build();
            final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
            apiInterface.getProjectDetails(companyId, offsetValue + 10, new Callback<JsonObject>() {
                @Override
                public void success(JsonObject response_data_obj, Response response) {
                    handleMoreProjectsResponse(response_data_obj);
                }

                @Override
                public void failure(RetrofitError error) {
                }
            });
        }
    }
*/

    private void handleMoreProjectsResponse(JsonObject response_data_obj) {
        try {
            List<NewProjectDto> more_projects = new Gson().fromJson(response_data_obj.getAsJsonArray("Projects"), new TypeToken<List<NewProjectDto>>() {
            }.getType());
            for (NewProjectDto prjcts : more_projects) {
                projects.add(prjcts);
            }
            hasMore = response_data_obj.get("hasMore").getAsBoolean();
            projects_adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.v("refresh", "ERROR IN REFRESH");
        }
        loading_more = false;
        listView.removeFooterView(notification_list_loader);
    }
}
