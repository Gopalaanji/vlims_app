package dev.info.basic.viswaLab.Fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dev.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import dev.info.basic.viswaLab.ApiInterfaces.ApiInterface;
import dev.info.basic.viswaLab.BuildConfig;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.utils.Common;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by E5000096 on 02-10-2016.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    EditText userNameEditText;
    EditText userPasswordEditText;
    TextView forgotView,vesriontext;
//    private Common common;
    private LoginFragmentActivity fragmentActivity;
    private int checksavedInstanceState = 0;
    Button btnLogin;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    public RelativeLayout main_loader;
    CheckBox rememberPassword;
    boolean is_checked = false;
    TextView forgotPwd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_login, container, false);
        userNameEditText = (EditText) rootView.findViewById(R.id.login_user_name);
        userPasswordEditText = (EditText) rootView.findViewById(R.id.login_user_pwd);
        btnLogin = (Button) rootView.findViewById(R.id.btn_login);
        rememberPassword = (CheckBox) rootView.findViewById(R.id.rememberPassword);
        vesriontext = (TextView) rootView.findViewById(R.id.vesriontext);
        main_loader = (RelativeLayout) rootView.findViewById(R.id.initial_loader);
        forgotPwd = (TextView) rootView.findViewById(R.id.forgotPwd);
        setHasOptionsMenu(true);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();
        fragmentActivity = (LoginFragmentActivity) getActivity();
        fragmentActivity.hideActionBar();
//        common = new Common();
//        userNameEditText.setTypeface(common.regularTypeface(getActivity()));
//        userPasswordEditText.setTypeface(common.regularTypeface(getActivity()));
        fragmentActivity.displayActionBar();
        btnLogin.setOnClickListener(this);
        forgotPwd.setOnClickListener(this);
        rememberPassword.setChecked(false);
        try{
//            vesriontext.setText("V  "+BuildConfig.VERSION_NAME);
            vesriontext.setText("V 2.0.1");

        }catch (Exception e){

        }


       /* if (prefs.getString("is_checkbox_rem", "") != null) {
            userNameEditText.setText(prefs.getString("pet", ""));
            userPasswordEditText.setText(prefs.getString("pwd", ""));
        }*/

        rememberPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    is_checked = isChecked;
                } else {
                    editor.putString("is_checkbox_rem", "");
                    editor.commit();
                }
            }
        });
        return rootView;
    }

    public void signUp() {
        fragmentActivity.replaceFragment(new ViswaLabDashboard(), "from_login_to_reraSignup", null);
    }

    public void checkLogin() {
        if (Common.isNetworkAvailable(getContext())) {
            if (Common.stringValidation(userNameEditText.getText().toString()).trim().length() != 0) {
                if (Common.stringValidation(userPasswordEditText.getText().toString()).length() != 0) {
                    UserLogin(userNameEditText.getText().toString(), userPasswordEditText.getText().toString());
                } else
                    showToast(getString(R.string.valid_pwd));
//                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, getString(R.string.valid_pwd));
            } else
                showToast(getString(R.string.valid_user_name));
//                common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, getString(R.string.valid_user_name));
        } else
            showToast(getString(R.string.network_error));
//            common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, getString(R.string.network_error));
    }

    private void UserLogin(final String userName, final String userPassword) {
        main_loader.setVisibility(View.VISIBLE);
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        Log.v("User==>", userName + "," + userPassword);
        apiInterface.GetUser(userName, userPassword, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                Log.v("RESPONSE==>", jsonObject.toString());
                if (jsonObject.get("Result").getAsString().equals("Success")) {
                    editor.putString("pet", userName);
                    editor.putString("userid", jsonObject.get("userid").getAsString());
                    editor.putString("Username", jsonObject.get("Username").getAsString());
                    editor.putString("pwd", userPassword);
                    if (is_checked) {
                        editor.putString("is_checkbox_rem", "yes");
                    } else {
//                        editor.putString("pet", "");
//                        editor.putString("pwd", "");
                    }
                    editor.commit();
//                    Log.v("UserId==>", "USER ID" + prefs.getString("userid", "") + "User Name" + prefs.getString("Username", ""));
                    fragmentActivity.replaceFragment(new ViswaLabDashboard(), "from_login_to_reraSignup", null);
                } else {
                    main_loader.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),"Invalid UserName/Password!",Toast.LENGTH_SHORT).show();
//                    common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, "Invalid UserName/Password!!");
                }
//
            }

            @Override
            public void failure(RetrofitError error) {
                main_loader.setVisibility(View.GONE);
                Toast.makeText(getActivity(),getString(R.string.something_went_wrong),Toast.LENGTH_SHORT).show();
//                common.showNewAlertDesign((Activity) getContext(), SweetAlertDialog.ERROR_TYPE, getString(R.string.something_went_wrong));
            }
        });
    }

/*
    private void sendUserToken(final String userName, String android) {
        Log.v("FCM_TOKEN==>", "TOKEN" + prefs.getString("fcm_token", ""));
        RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
        final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
        //WE HAVE TO ADD THE TOKEN AS POST CONNECT TO SERVER FOR KEY
        try {
            Log.v("XXX", prefs.getInt("companyId", 0) + "");
            apiInterface.sendTokenDetailsUsingPost(userName, prefs.getString("UserId", ""), android, prefs.getString("fcm_token", ""), new Callback<JsonObject>() {
                @Override
                public void success(JsonObject jsonObject, Response response) {
                    Log.v("RESPONSE==>Token", jsonObject.toString());
                    if (jsonObject.get("Result").getAsString().equals("Success")) {
                        main_loader.setVisibility(View.GONE);
                        getActivity().getSharedPreferences("rsr.info.basic.pricedproperty", Context.MODE_PRIVATE).edit().putString("userName", userName).apply();
                        fragmentActivity.screenNavigation(HomeActivity.class, true);
                    } else {
                        main_loader.setVisibility(View.GONE);
                        common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, jsonObject.get("Message").getAsString().toString());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    main_loader.setVisibility(View.GONE);
    showToast();                }
            });
        } catch (Exception e) {
        }
    }
*/

    /*   @Override
       public boolean onOptionsItemSelected(MenuItem item) {
           switch (item.getItemId()) {
               case android.R.id.home:
                   fragmentActivity.loadPopBackStack();
                   break;
           }
           return super.onOptionsItemSelected(item);
       }
   */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                checkLogin();
//                fragmentActivity.screenNavigation(HomeActivity.class, true);
//                fragmentActivity.replaceFragment(new AnalysisReportFragment(), "from_agent_login", null);
                break;
            case R.id.forgotPwd:
//                fragmentActivity.screenNavigation(ForgotPasswordActivity.class, true);
                fragmentActivity.replaceFragment(new ForgotPwdFragment(), "from_login_to_reraSignup", null);

                break;
        }

    }


}
