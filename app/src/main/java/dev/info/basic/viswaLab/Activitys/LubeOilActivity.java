package dev.info.basic.viswaLab.Activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.View;
import android.widget.Button;

import dev.info.basic.viswaLab.BaseActivity;
import dev.info.basic.viswaLab.Fragments.LoginFragment;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.utils.Common;

public class LubeOilActivity extends BaseActivity {
    private FragmentManager fragmentManager;
    private Common common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        common = new Common();
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null)
            return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_container, new LoginFragment(), "from_login_activity").commit();
        setActionBarTitle(Html.fromHtml("<font color='#C79147'>Viswa </font>").toString() + "Lab");
        displayActionBar();
        Button luboilReportsBtn = (Button) findViewById(R.id.luboilReportsBtn);
        luboilReportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_lube_oil;

    }
}
