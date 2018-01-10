package rsr.info.basic.viswaLab.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import rsr.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import rsr.info.basic.viswaLab.R;
import rsr.info.basic.viswaLab.utils.Common;

/**
 * Created by E5000096 on 02-10-2016.
 */
public class SplashFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private int checksavedInstanceState = 0;
    LinearLayout relativeLayout;
    Button loginTextView;
    Button agentsignUpTextView, companysignUpTextView;
    TextView registerHint;

    private LoginFragmentActivity fragmentActivity;
    private Common common;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (checksavedInstanceState == 0) {
            checksavedInstanceState = 1;
            rootView = inflater.inflate(R.layout.activity_splash, container, false);
            relativeLayout = (LinearLayout) rootView.findViewById(R.id.login_layout);
            loginTextView = (Button) rootView.findViewById(R.id.login);
            agentsignUpTextView = (Button) rootView.findViewById(R.id.agentsignUpTextView);
            companysignUpTextView = (Button) rootView.findViewById(R.id.companysignUpTextView);
            registerHint = (TextView) rootView.findViewById(R.id.register_hint);
            fragmentActivity = (LoginFragmentActivity) getActivity();
            fragmentActivity.hideActionBar();
            registerHint.setText(getResources().getString(R.string.register_msg));
            common = new Common();
            showScreen();
        }
        fragmentActivity.hideActionBar();
        loginTextView.setOnClickListener(this);
        agentsignUpTextView.setOnClickListener(this);
        companysignUpTextView.setOnClickListener(this);
        return rootView;
    }

    public void loginScreen() {
        fragmentActivity.replaceFragment(new LoginFragment(), "login", null);
    }


    private void showScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                relativeLayout.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                loginScreen();
                break;
            case R.id.agentsignUpTextView:
                agentSignUpScreen();
                break;
            case R.id.companysignUpTextView:
                CompanySignUpScreen();
                break;
        }
    }

    private void CompanySignUpScreen() {
        fragmentActivity.replaceFragment(new ReraCompanySignupPage(), "rera_signup", null);
    }

    private void agentSignUpScreen() {
        fragmentActivity.replaceFragment(new ViswaLabDashboard(), "rera_signup", null);
    }
}
