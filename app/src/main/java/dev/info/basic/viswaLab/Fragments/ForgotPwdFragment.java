package dev.info.basic.viswaLab.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dev.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import dev.info.basic.viswaLab.ApiInterfaces.ApiInterface;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.utils.Common;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by E5000096 on 02-10-2016.
 */
public class ForgotPwdFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    EditText emailEditText;
    Button pwdReset;
    //    private Common common;
    private LoginFragmentActivity fragmentActivity;
    private EditText userName, mobile;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    public RelativeLayout main_loader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_forgot_pwd, container, false);

        setHasOptionsMenu(true);
        fragmentActivity = (LoginFragmentActivity) getActivity();
        fragmentActivity.displayActionBar();
        fragmentActivity.setActionBarTitle(getString(R.string.forgot_pwd));
        fragmentActivity.setBtnLogOutVisibility();
        emailEditText = (EditText) rootView.findViewById(R.id.email);
        main_loader = (RelativeLayout) rootView.findViewById(R.id.initial_loader);
        userName = (EditText) rootView.findViewById(R.id.userName);
        mobile = (EditText) rootView.findViewById(R.id.mobile);
        pwdReset = (Button) rootView.findViewById(R.id.pwd_reset);
        pwdReset.setOnClickListener(this);
//        common = new Common();
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();
//        emailEditText.setTypeface(common.regularTypeface(getActivity()));
        return rootView;
    }

    public void checkMobile() {
        if (Common.stringValidation(mobile.getText().toString()).length() > 0) {
            if (validateNumber()) {
                if (Common.isNetworkAvailable(getActivity())) {
                    if (Common.stringValidation(userName.getText().toString()).trim().length() > 0) {
                        if (Common.stringValidation(emailEditText.getText().toString()).length() > 0 && Common.isValidEmail(emailEditText.getText().toString())) {
                            sendForgotPassword(userName.getText().toString().trim(), emailEditText.getText().toString(), mobile.getText().toString());
                        } else
                            showToast("Please Enter Valid Email Address!");
                    } else
                        showToast(getString(R.string.valid_user_name));
                } else
                    showToast(getString(R.string.network_error));

            } else {
                showToast(getString(R.string.valid_mobile_hint));
            }
        } else {
            showToast(getString(R.string.valid_mobile_hint));
        }
    }

    public boolean validateNumber() {
        if ((mobile.getText().toString().length() == 10 || mobile.getText().toString().length() == 12)
                && (mobile.getText().toString() != "0000000000")
                && ((String.valueOf(mobile.getText().toString().charAt(0)).equals("7"))
                || (String.valueOf(mobile.getText().toString().charAt(0)).equals("8"))
                || (String.valueOf(mobile.getText().toString().charAt(0)).equals("9")))) {

            return true;
        } else {
            return false;
        }
    }

    private void sendForgotPassword(String userName, String email, String mobile) {
        if (Common.isNetworkAvailable(getActivity())) {
            RestAdapter rest_adapter = new RestAdapter.Builder().setEndpoint(ApiInterface.pdf_Head).build();
            final ApiInterface apiInterface = rest_adapter.create(ApiInterface.class);
            main_loader.setVisibility(View.VISIBLE);
            apiInterface.GetForgot(userName, email, mobile, new Callback<JsonObject>() {
                @Override
                public void success(JsonObject jsonObject, Response response) {
                    Log.v("RESPONSE==>", jsonObject.toString());
                    if (jsonObject.get("Result").getAsString().equals("Success")) {
                        main_loader.setVisibility(View.GONE);
                        editor.putString("userid", "");
                        editor.putString("Username", "");
                        editor.putString("is_checkbox_rem", "");
                        editor.putString("pet", "");
                        editor.putString("pwd", "");
                        editor.commit();
                        Toast.makeText(getActivity(), "Password Sent to Your Email id", Toast.LENGTH_SHORT).show();
                        fragmentActivity.loadPopBackStack();
                    } else {
                        main_loader.setVisibility(View.GONE);
                        showToast("Invalid UserName");
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    main_loader.setVisibility(View.GONE);
                    showToast();
                }
            });
        } else
            showToast(getString(R.string.network_error));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pwd_reset:
                checkMobile();
                break;
        }
    }
}
