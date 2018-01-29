package dev.info.basic.viswaLab.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;

import dev.info.basic.viswaLab.BaseActivity;
import dev.info.basic.viswaLab.Fragments.LoginFragment;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.utils.Common;

/**
 * Created by Giri on 02-10-2016.
 */
public class LoginFragmentActivity extends BaseActivity {

    private FragmentManager fragmentManager;
    private Common common;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        common = new Common();
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null)
            return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
//        if (prefs.getString("Username", "") != null && !prefs.getString("Username", "").isEmpty()) {
//            fragmentTransaction.add(R.id.frag_container, new ViswaLabDashboard(), "from_login_activity").commit();
//
//        } else {
            fragmentTransaction.add(R.id.frag_container, new LoginFragment(), "from_login_activity").commit();
//        }
        setActionBarTitle(Html.fromHtml("<font color='#C79147'>Viswa </font>").toString() + "Lab");
        displayActionBar();
    }

    public void replaceFragment(Fragment fragment, String backStackTag, Bundle bundle) {
        common.replaceFragment(fragmentManager, fragment, backStackTag, bundle, R.id.frag_container);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frag_container);
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frag_container);
        fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void loadPopBackStack() {
        Log.i("count==>", "count " + fragmentManager.getBackStackEntryCount());
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        loadPopBackStack();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.main_fragment_layout;
    }
}
