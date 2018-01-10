package rsr.info.basic.viswaLab.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import rsr.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import rsr.info.basic.viswaLab.R;
import rsr.info.basic.viswaLab.utils.Common;

/**
 * Created by RSR on 12-09-2017.
 */

public class ReraCompanySignupPage extends BaseFragment implements View.OnClickListener {
    private View rootView;
    EditText userNameEditText;
    EditText login_user_mobile;
    EditText login_user_email;
    EditText login_agent_pwd;
    EditText login_agent_confirm_pwd;
    TextView forgotView;
    private Common common;
    private LoginFragmentActivity fragmentActivity;
    private int checksavedInstanceState = 0;
    Button btnLogin;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    public RelativeLayout main_loader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.viswalab_dashboard, container, false);
        setHasOptionsMenu(true);
        userNameEditText = (EditText) rootView.findViewById(R.id.login_user_name);
        btnLogin = (Button) rootView.findViewById(R.id.btn_login);
        main_loader = (RelativeLayout) rootView.findViewById(R.id.initial_loader);
        setHasOptionsMenu(true);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();
        fragmentActivity = (LoginFragmentActivity) getActivity();
        forgotView.setText(getString(R.string.trouble_signup));
        common = new Common();
        userNameEditText.setTypeface(common.regularTypeface(getActivity()));
        login_user_mobile.setTypeface(common.regularTypeface(getActivity()));
        login_user_email.setTypeface(common.regularTypeface(getActivity()));
        login_agent_pwd.setTypeface(common.regularTypeface(getActivity()));
        login_agent_confirm_pwd.setTypeface(common.regularTypeface(getActivity()));
        btnLogin.setOnClickListener(this);
//        if (!prefs.getString("userMobileNo", "").equals("null")) {
//            fragmentActivity.replaceFragment(new AnalysisReportFragment(), "agent_signup", null);
//        }
        fragmentActivity.hideActionBar();
        return rootView;
    }

    public void forgotPwd() {
        contactRsr("8008020666");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
//                checkLogin();
                fragmentActivity.replaceFragment(new LoginFragment(), "from_agent_signup", null);
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                fragmentActivity.loadPopBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
