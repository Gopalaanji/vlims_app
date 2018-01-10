package rsr.info.basic.viswaLab.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rsr.info.basic.viswaLab.R;
import rsr.info.basic.viswaLab.views.DividerDecoration;

/**
 * Created by E5000096 on 19-07-2016.
 */
public class Common {

    public Common() {

    }

    public Typeface regularTypeface(Context appContext) {
        return Typeface.createFromAsset(appContext.getAssets(), appContext.getString(R.string.font_sans));
    }

    public Typeface webTypeface(Context appContext) {
        return Typeface.createFromAsset(appContext.getAssets(), appContext.getString(R.string.font_web));
    }

    public static String stringValidation(String text) {
        String result;
        if (text != null && !TextUtils.isEmpty(text) && checkString(text).length() > 0)
            result = text;
        else
            result = "";
        return result;
    }

    public final boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    public void makeTextViewResizable(final Context context, final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(context, Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(context, Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(context, Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private SpannableStringBuilder addClickablePartTextViewResizable(final Context context, final Spanned strSpanned, final TextView tv,
                                                                     final int maxLine, final String spanableText,
                                                                     final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {

                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(context, tv, -1, context.getString(R.string.read_less), false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(context, tv, 3, context.getString(R.string.read_more), true);
                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;
    }


    public static String checkString(String str) {
        return str.replaceAll("\\s", "");
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.e("Network Testing", "***Available***");
            return true;
        }
        Log.e("Network Testing", "***Not Available***");
        return false;
    }

    public static void toastMessage(final Context context, final String text) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    public String long2String(String stringDate, SimpleDateFormat df) {
        Long longDate = Long.parseLong(stringDate);
        Timestamp timestampDate = new Timestamp(longDate);
        Date date = timeStamp2Date(timestampDate);
        return date2String(date, df);
    }

    public Date timeStamp2Date(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }

    public String date2String(Date date,
                              SimpleDateFormat simpleDateFormat) {

        return simpleDateFormat.format(date);
    }


    public String changeDateFormat(String date, SimpleDateFormat dateFormat, SimpleDateFormat format) throws ParseException {
        return dateFormat.format(format.parse(date));
    }

    public int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public String getRatingValue(String ratingValue) {
        return ratingValue.trim().equalsIgnoreCase("0.0") ? "" : ratingValue;
    }

    public static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public void showNewAlertDesign(final Activity activity, final int msgType, final String content) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new SweetAlertDialog(activity, msgType)
                        .setTitleText(getMsgType(msgType, activity))
                        .setContentText(content)
                        .show();
            }
        });
    }

    public String getRealPathFromURI(Uri uri, Activity activity) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void replaceFragment(FragmentManager fragmentManager, Fragment fragment, String backStackTag, Bundle bundle, int frameId) {
        if (bundle != null && bundle.size() > 0)
            fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frameId, fragment, backStackTag);
        fragmentTransaction.addToBackStack(backStackTag);
        fragmentTransaction.commit();
    }

    public boolean checkPermission(final Activity context) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        else {
            LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
    }

    public boolean checkPermission(final Activity context, String permission, int permissionState) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return ActivityCompat.checkSelfPermission(context, permission) == permissionState;
        else {
            return true;
        }
    }

    public void requestPermissions(int requestCode, Activity activity) {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                requestCode);
    }

    public void requestPermissions(int requestCode, Activity activity, String[] permissions) {
        ActivityCompat.requestPermissions(
                activity, permissions, requestCode);
    }

    private String getMsgType(int msgType, Activity activity) {
        switch (msgType) {
            case SweetAlertDialog.WARNING_TYPE:
                return activity.getString(R.string.warning_hint);
            case SweetAlertDialog.ERROR_TYPE:
                return activity.getString(R.string.error);
            case SweetAlertDialog.SUCCESS_TYPE:
                activity.getString(R.string.success);
            default:
                return "";
        }
    }

    public void recycleViewProperties(Context context, RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerDecoration(context));
    }

}
