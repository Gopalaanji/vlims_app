package rsr.info.basic.viswaLab;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rsr.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import rsr.info.basic.viswaLab.utils.Common;

public abstract class BaseActivity extends AppCompatActivity {

    public SweetAlertDialog pDialog;
    TextView toolbarTitle;
    ImageView btnLogOut;
    Toolbar toolbar;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnLogOut = (ImageView) findViewById(R.id.btnLogOut);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(BaseActivity.this)
                        .setTitle("Logout")
                        .setMessage("Would you like to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                editor.putString("userid", "");
                                editor.putString("Username", "");
                                editor.commit();
                                Intent intent = new Intent(BaseActivity.this, LoginFragmentActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    public void showLoadingDialog() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(getString(R.string.loading));
        pDialog.show();
        pDialog.setCancelable(false);
    }

    public void dismissDialog() {
        if (pDialog != null)
            pDialog.dismiss();
    }

    //DISPLAY ERROR MISSED
    //SHOW ERROR TITLE
    public void showErrorTitle(Common common, Activity activity, String errorMessage) {
        common.showNewAlertDesign(activity, SweetAlertDialog.ERROR_TYPE, errorMessage);
    }

    public void screenNavigation(Class<?> cls, boolean clearStack) {
        Intent intent = new Intent(this, cls);
        if (clearStack)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void setActionBarIcon(int iconRes) {
        toolbar.setNavigationIcon(iconRes);
    }


    public void setActionBarTitle(String title) {
        toolbarTitle.setText(title);
    }

    public void hideActionBar() {
        getSupportActionBar().hide();
    }

    public void showActionBar() {
        getSupportActionBar().show();
    }

    public void displayActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void hideBackActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    protected abstract int getLayoutResource();


}
