package rsr.info.basic.viswaLab.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rsr.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import rsr.info.basic.viswaLab.R;
import rsr.info.basic.viswaLab.utils.Common;

/**
 * Created by E5000096 on 02-10-2016.
 */
public class ForgotPwdFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    EditText emailEditText;
    Button pwdReset;
    private Common common;
    private LoginFragmentActivity fragmentActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_forgot_pwd, container, false);
        emailEditText = (EditText) rootView.findViewById(R.id.email);
        pwdReset = (Button) rootView.findViewById(R.id.pwd_reset);
        pwdReset.setOnClickListener(this);
        setHasOptionsMenu(true);
        common = new Common();
        fragmentActivity = (LoginFragmentActivity) getActivity();
        fragmentActivity.setActionBarTitle(getString(R.string.forgot_pwd));
        emailEditText.setTypeface(common.regularTypeface(getActivity()));
        fragmentActivity.displayActionBar();
        return rootView;
    }

    public void checkMobile() {
        if (Common.stringValidation(emailEditText.getText().toString()).length() > 0) {
            if (Patterns.PHONE.matcher(emailEditText.getText()).matches()) {
                fragmentActivity.showLoadingDialog();
//                Call<ResponseModel> call = fragmentActivity.restApiService.forgotPwd(emailEditText.getText().toString().trim());
//                call.enqueue(new Callback<ResponseModel>() {
//                    @Override
//                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//                        fragmentActivity.dismissDialog();
//                        if (response.code() == 200) {
//                            common.showNewAlertDesign(getActivity(), SweetAlertDialog.SUCCESS_TYPE, response.body().get_statusMessage());
//                        } else {
//                            fragmentActivity.displayError(common, getActivity(), response.errorBody());
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<ResponseModel> call, Throwable t) {
//                        fragmentActivity.dismissDialog();
//                        fragmentActivity.showErrorTitle(common, getActivity(), t.getCause().getLocalizedMessage());
//                    }
//                });
            } else {
                common.showNewAlertDesign(getActivity(), SweetAlertDialog.ERROR_TYPE, getString(R.string.valid_mobile_hint));
            }
        } else {
            fragmentActivity.showErrorTitle(common, getActivity(), getString(R.string.valid_mobile));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pwd_reset:
                checkMobile();
                break;
        }
    }
}
