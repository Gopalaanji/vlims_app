package dev.info.basic.viswaLab.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import dev.info.basic.viswaLab.BaseActivity;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.utils.Common;
import me.majiajie.pagerbottomtabstrip.Controller;

public class HomeActivity extends BaseActivity {
    int[] testColors = {0xFF83A72E};
    Controller controller;
    List<Fragment> mFragments;
    private int prevSelectedIndex = 0;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        Button luboilReportsBtn = (Button) findViewById(R.id.luboilReportsBtn);
        luboilReportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LubeOilActivity.class);
                startActivity(intent);

            }
        });
//
   }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
//        mFragments.add(createFragment("0", new NotificationsFragment(), null));
//        mFragments.add(createFragment("1", new HomeFragment(), null));
//        mFragments.add(createFragment("2", new ProFragment(), null));

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.commit();
    }

    public void loadPopBackStack() {
        Log.i("", "count " + fragmentManager.getBackStackEntryCount());
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }
    }



    private Fragment createFragment(String content, Fragment fragment, String title) {
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
