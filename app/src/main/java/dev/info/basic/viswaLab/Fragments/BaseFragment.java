package dev.info.basic.viswaLab.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import dev.info.basic.viswaLab.Activitys.WebViewActivity;
import dev.info.basic.viswaLab.ApiInterfaces.ApiInterface;
import dev.info.basic.viswaLab.BuildConfig;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.utils.RetrofitInterface;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static dev.info.basic.viswaLab.utils.PricedPropertyApplication.TAG;

/**
 * Created by E5000096 on 19-07-2016.
 */
public class BaseFragment extends Fragment {
    private static final int REQUEST_PHONE_CALL = 1;
    public static String number = "";
    public static boolean isDebug=true;

    private ProgressDialog progressDialog;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = prefs.edit();
    }


   /* public void showTooltip(View clicked_view, CharSequence display_string, TooltipManager.Gravity position) {
        TooltipManager.getInstance(getActivity())
                .create(100)
                .anchor(clicked_view, position)
                .closePolicy(TooltipManager.ClosePolicy.TouchOutside, 60000)
                .activateDelay(800)
                .text(display_string)
                .withStyleId(R.style.ToolTipLayoutDefaultStyle)
                .show();
    }*/

    public void contact(String number) {
        this.number = number;
        if (hasCallPermision()) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + number));
            startActivity(callIntent);
        } else {
            requestCallPermission();
        }
    }
    public void showToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();

    }
    public void showToast(){
        Toast.makeText(getActivity(),getString(R.string.network_error),Toast.LENGTH_SHORT).show();
    }
    public void pshowToast(){
        Toast.makeText(getActivity(),"Please Enter Value!",Toast.LENGTH_SHORT).show();
    }
public void cshowToast(){
        Toast.makeText(getActivity(),"Could Not Found Details!",Toast.LENGTH_SHORT).show();

    }

    public void contactemal(String email) {

        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "VLIMS");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");


        emailIntent.setType("message/rfc822");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "No email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }

    public void showAlertDialog(final String title, final String url) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.all_diaologs, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(alertLayout);
        TextView tvtitle = (TextView) alertLayout.findViewById(R.id.tvdialogtitle);
        Button btndialog = (Button) alertLayout.findViewById(R.id.btndialog);
        tvtitle.setText(title);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent browse = new Intent(getActivity(), WebViewActivity.class);
                browse.putExtra("pdf_id", url);
                getActivity().startActivity(browse);*/
                Intent intent = new Intent();
                intent.setDataAndType(Uri.parse(url), "application/pdf");
                startActivity(intent);

            }
        });
        builder.show();
    }
  public void showAlertDialogOption(final String title, final String description, final String url) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.all_diaologs, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(alertLayout);
        TextView tvtitle = (TextView) alertLayout.findViewById(R.id.tvdialogtitle);
        TextView tvdialogbody = (TextView) alertLayout.findViewById(R.id.tvdialogbody);
        Button btndialog = (Button) alertLayout.findViewById(R.id.btndialog);
        tvtitle.setText(title);
      tvdialogbody.setText(description);
      if(url.isEmpty()){
          btndialog.setVisibility(View.INVISIBLE);
      }else{
          btndialog.setVisibility(View.VISIBLE);
      }
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setDataAndType(Uri.parse(url), "application/pdf");
                startActivity(intent);

//                Intent browse = new Intent(getActivity(), WebViewActivity.class);
//                browse.putExtra("pdf_id", url);
//                getActivity().startActivity(browse);
            }
        });
        builder.show();
    }


    public void openBroucher(String fileName) {

        File fileBrochure = new File(Environment.getExternalStorageDirectory() + "/" + fileName + ".pdf");
        if (!fileBrochure.exists()) {
            CopyAssetsbrochure(fileName);
        }
        /** PDF reader code */
        File file = new File(Environment.getExternalStorageDirectory() + "/" + fileName + ".pdf");

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            getActivity().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "No Pdf Viewer found on your device", Toast.LENGTH_SHORT).show();
        }
    }

    //method to write the PDFs file to sd card
    private void CopyAssetsbrochure(String fileName) {
        AssetManager assetManager = getActivity().getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        for (int i = 0; i < files.length; i++) {
            String fStr = files[i];
            if (fStr.equalsIgnoreCase(fileName + ".pdf")) {
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = assetManager.open(files[i]);
                    out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + files[i]);
                    copyFile(in, out);
                    in.close();
                    in = null;
                    out.flush();
                    out.close();
                    out = null;
                    break;
                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public boolean hasCallPermision() {
        int result = 0;
        String[] permissions = new String[]{Manifest.permission.CALL_PHONE};
        for (String perm : permissions) {
            result = getActivity().checkCallingOrSelfPermission(perm);
            if (!(result == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }

    public void requestCallPermission() {
        String[] permissions = new String[]{Manifest.permission.CALL_PHONE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, REQUEST_PHONE_CALL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean call_allowed = true;
        switch (requestCode) {
            case REQUEST_PHONE_CALL:
                for (int res : grantResults)
                    call_allowed = call_allowed && (res == PackageManager.PERMISSION_GRANTED);
                if (call_allowed) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + number));
                    startActivity(callIntent);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                            Toast.makeText(getContext(), "VIDEO PERMISIONS IGNORED", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
            default:
                break;
        }
    }


    public void showProgressBar() {
        progressDialog = new ProgressDialog(getActivity(), R.style.ProgressBarCustom);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();
    }

    public void hideProgressBar() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    public void downloadZipFile(final String module_type, final String pdfFileName, final String  test_name) {
       showProgressBar();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(ApiInterface.pdf_Head);
        Retrofit retrofit = builder.client(httpClient.build()).build();
        RetrofitInterface downloadService = retrofit.create(RetrofitInterface.class);
        Call<ResponseBody> call = null;

        if (module_type.equalsIgnoreCase("FO")) {
            call = downloadService.downloadFileByUrl_FO(prefs.getString("pet", ""), prefs.getString("pwd", ""), pdfFileName);
        } else if (module_type.equalsIgnoreCase("LO_AR")) {
            call = downloadService.downloadFileByUrl_LO(prefs.getString("pet", ""), prefs.getString("pwd", ""), pdfFileName);
        } else if (module_type.equalsIgnoreCase("CLO")) {
            call = downloadService.downloadFileByUrl_CLO(prefs.getString("pet", ""), prefs.getString("pwd", ""), pdfFileName);

        } else if (module_type.equalsIgnoreCase("ADD")) {
            call = downloadService.downloadFileByUrl_ADDL(prefs.getString("pet", ""), prefs.getString("pwd", ""), pdfFileName + "_" + test_name);

        } else if (module_type.equalsIgnoreCase("POMP_AR")) {
            call = downloadService.downloadFileByUrl_POMP_AR(prefs.getString("pet", ""), prefs.getString("pwd", ""), pdfFileName + "_POMP_REPORT(FINAL)");

        } else if (module_type.equalsIgnoreCase("PFEFFY_AR")) {
            call = downloadService.downloadFileByUrl_PED_AR(prefs.getString("pet", ""), prefs.getString("pwd", ""), pdfFileName);

        }else if (module_type.equalsIgnoreCase("TU")) {
            call = downloadService.downloadFileByUrl_TU(prefs.getString("pet", ""), prefs.getString("pwd", ""), pdfFileName);

        }

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Got the body for the file");
                  hideProgressBar();

                    new AsyncTask<Void, Long, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            saveToDisk(response.body(),pdfFileName,module_type,test_name);
                            return null;
                        }
                    }.execute();

                } else {
                    Log.d(TAG, "Connection failed " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, t.getMessage());
            }
        });



 }
    public void saveToDisk(ResponseBody body, String pdfFileName, String module_type,String test_name) {
        try {
            File sdcard_destinationFile;
            if(module_type.equalsIgnoreCase("ADD")){
                sdcard_destinationFile = new File(Environment.getExternalStorageDirectory()
                        + "/download/" + pdfFileName+"_"+test_name + ".pdf");
            }else{
               sdcard_destinationFile = new File(Environment.getExternalStorageDirectory()
                        + "/download/" + pdfFileName + ".pdf");
            }



      InputStream is = null;
            OutputStream os = null;

            try {
                is = body.byteStream();
                os = new FileOutputStream(sdcard_destinationFile);
                byte data[] = new byte[4096];
                int count;
                int progress = 0;
                while ((count = is.read(data)) != -1) {
                    os.write(data, 0, count);
                    progress += count;
                    Log.d(TAG, "Progress: " + progress + "/" + body.contentLength() + " >>>> " + (float) progress / body.contentLength());
                }
                os.flush();
                Log.d(TAG, "File saved successfully!");
                showPdf(pdfFileName,module_type,test_name);
                return;
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Failed to save the file!");
                return;
            } finally {
                if (is != null) is.close();
                if (os != null) os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Failed to save the file!");
            return;
        }
    }
    public void showPdf(String pdfFileName,String module_type,String testName) {
        try {
            File destinationFile;
//            File destinationFile = new File(Environment.getExternalStorageDirectory()
//                    .getAbsolutePath()
//                    + "/download/" + pdfFileName + ".pdf");
            if(module_type.equalsIgnoreCase("ADD")){
                destinationFile = new File(Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + "/download/" + pdfFileName+"_"+testName + ".pdf");
            }else{
                destinationFile = new File(Environment.getExternalStorageDirectory()
                        .getAbsolutePath()     + "/download/" + pdfFileName + ".pdf");
            }

            if (destinationFile.exists()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = null;
                // So you have to use Provider
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(getActivity(),
                            BuildConfig.APPLICATION_ID + ".provider",
                            destinationFile);
                    // Add in case of if We get Uri from fileProvider.
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } else {
                    uri = Uri.fromFile(destinationFile);
                }
                intent.setDataAndType(uri, "application/pdf");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "No Pdf Viewer found on your device", Toast.LENGTH_SHORT).show();
                }
            } else {
                downloadZipFile(module_type,pdfFileName,testName);
            }

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Brochure Not Open in Your Mobile!", Toast.LENGTH_SHORT).show();
        }

    }


}
