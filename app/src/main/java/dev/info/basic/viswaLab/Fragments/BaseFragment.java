package dev.info.basic.viswaLab.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
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
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

import static android.content.Context.DOWNLOAD_SERVICE;
import static dev.info.basic.viswaLab.utils.PricedPropertyApplication.TAG;

/**
 * Created by E5000096 on 19-07-2016.
 */
public class BaseFragment extends Fragment {
    private static final int REQUEST_PHONE_CALL = 1;
    public static String number = "";
    public static boolean isDebug = true;

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

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

    }

    public void showToast() {
        Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
    }

    public void pshowToast() {
        Toast.makeText(getActivity(), "Please Enter Value!", Toast.LENGTH_SHORT).show();
    }

    public void cshowToast() {
        Toast.makeText(getActivity(), "Could Not Find Details!", Toast.LENGTH_SHORT).show();

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
        if (url.isEmpty()) {
            btndialog.setVisibility(View.INVISIBLE);
        } else {
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

    @SuppressLint("WrongConstant")
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

    private String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }


    BroadcastReceiver attachmentDownloadCompleteReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                long downloadId = intent.getLongExtra(
                        DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                openDownloadedAttachment(context, downloadId);
            }
        }
    };


    private void openDownloadedAttachment(final Context context, final long downloadId) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            String downloadLocalUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            String downloadMimeType = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));
            if ((downloadStatus == DownloadManager.STATUS_SUCCESSFUL) && downloadLocalUri != null) {
                hideProgressBar();
                openDownloadedAttachment(context, Uri.parse(downloadLocalUri), downloadMimeType);
            }
        }
        cursor.close();
    }


    private void openDownloadedAttachment(final Context context, Uri attachmentUri, final String attachmentMimeType) {
        if (attachmentUri != null) {
            // Get Content Uri.
            if (ContentResolver.SCHEME_FILE.equals(attachmentUri.getScheme())) {
                // FileUri - Convert it to contentUri.
                File file = new File(attachmentUri.getPath());
                attachmentUri = FileProvider.getUriForFile(getActivity(), "dev.info.basic.viswaLab", file);

            }

            Intent openAttachmentIntent = new Intent(Intent.ACTION_VIEW);
            openAttachmentIntent.setDataAndType(attachmentUri, attachmentMimeType);
            openAttachmentIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                context.startActivity(openAttachmentIntent);
            } catch (ActivityNotFoundException e) {
                hideProgressBar();
                Toast.makeText(context, "File not exist  ", Toast.LENGTH_LONG).show();
            }
        }
    }

    String downloadfile;
    String sdcard_destinationFile;

    public void downloadZipFile(final String module_type, final String pdfFileName, final String test_name) {
        showProgressBar();
        String file = "";
        String downloadin_filename;
        if (module_type.equalsIgnoreCase("ADD")) {
            downloadin_filename = pdfFileName + "_" + test_name + ".pdf";


        } else {
            downloadin_filename = pdfFileName + ".pdf";
        }


        if (module_type.equalsIgnoreCase("FO")) {
            file = "VL_FOReports_Download?username=" + prefs.getString("pet", "") + "&password=" + prefs.getString("pwd", "") + "&serialNo=" + pdfFileName;

        } else if (module_type.equalsIgnoreCase("LO_AR")) {
            file = "VL_LOReports_Download?username=" + prefs.getString("pet", "") + "&password=" + prefs.getString("pwd", "") + "&serialNo=" + pdfFileName;
        } else if (module_type.equalsIgnoreCase("CLO")) {
            file = "VL_CLOReports_Download?username=" + prefs.getString("pet", "") + "&password=" + prefs.getString("pwd", "") + "&serialNo=" + pdfFileName;
//.replaceAll("\\s", "")
        } else if (module_type.equalsIgnoreCase("ADD")) {
            file = "VL_ADDLReports_Download?username=" + prefs.getString("pet", "") + "&password=" + prefs.getString("pwd", "") + "&serialNo=" + pdfFileName + "_" + test_name.replaceAll("\\s", "+");

        } else if (module_type.equalsIgnoreCase("POMP_AR")) {
            file = "VL_POMPReports_Download?username=" + prefs.getString("pet", "") + "&password=" + prefs.getString("pwd", "") + "&serialNo=" + pdfFileName + "_POMP_REPORT(FINAL)";
            downloadin_filename = pdfFileName +"_POMP_REPORT(FINAL)"+ ".pdf";

        } else if (module_type.equalsIgnoreCase("PFEFFY_AR")) {
            file = "VL_PEDReports_Download?username=" + prefs.getString("pet", "") + "&password=" + prefs.getString("pwd", "") + "&serialNo=" + pdfFileName;


        } else if (module_type.equalsIgnoreCase("TU")) {
            file = "VL_TechUpdates_Download?username=" + prefs.getString("pet", "") + "&password=" + prefs.getString("pwd", "") + "&FileName=" + pdfFileName.replaceAll("\\s", "+");
        }
//        Toast.makeText(getActivity(), "username: " + prefs.getString("pet", "") + "\n" + "password: " + prefs.getString("pwd", "") + "\n" + "Serial No: " + pdfFileName, Toast.LENGTH_SHORT).show();
        if (module_type.equalsIgnoreCase("ADD")) {

            sdcard_destinationFile = Environment.getExternalStorageDirectory() + "/download/" + pdfFileName + "_" + test_name + ".pdf";
        } else {
            sdcard_destinationFile = Environment.getExternalStorageDirectory() + "/download/" + pdfFileName + ".pdf";
        }
        Log.e("filepathsd", sdcard_destinationFile);
        long downloadReference;

        try {

            getActivity().registerReceiver(attachmentDownloadCompleteReceive, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            //  Uri Download_Uri = Uri.parse("https://blog.mozilla.org/security/files/2015/05/HTTPS-FAQ.pdf");
            Uri Download_Uri = Uri.parse("http://74.208.185.23/VLIMSAPP/GETuser.asmx/" + file);
            downloadfile = "http://74.208.185.23/VLIMSAPP/GETuser.asmx/" + file;
            new getPDF().execute("http://74.208.185.23/VLIMSAPP/GETuser.asmx/" + file, downloadin_filename);
            //   DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
            //  DownloadManager.Request request = new DownloadManager.Request(Download_Uri);

//            request.setTitle("PDF file Download");
//            request.setMimeType(getMimeType(Download_Uri.toString()));
//            //Setting description of request
//            request.setDescription("file Downloading... ");
//            request.setDestinationInExternalFilesDir(getActivity(), Environment.DIRECTORY_DOWNLOADS, sdcard_destinationFile);
//            request.allowScanningByMediaScanner();
//            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//            Long id = downloadManager.enqueue(request);
        } catch (Exception e) {
            hideProgressBar();
            Toast.makeText(getContext(), "File not exist  ", Toast.LENGTH_LONG).show();
        }
    }

    class getPDF extends AsyncTask<String, Void, String> {
        int status = 0;
        String reqUrl;
        String filename;

        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            try {
                filename = strings[1];
                reqUrl = strings[0];
                URL url = new URL(reqUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                // read the response
                InputStream in = new BufferedInputStream(conn.getInputStream());
                status = conn.getResponseCode();
                InputStreamReader isr = new InputStreamReader(in, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                response = br.readLine();
                Log.e("rfid_rsponces", response);
            } catch (Exception e) {
                Log.e("responce back", e.getMessage().toString());
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            hideProgressBar();
            if (status == 200) {
                Log.e("reqUrl", reqUrl);
                try {
                    if ("%PDF-1.4".equals(s.trim()) || "%PDF-1.5".equals(s.trim())) {
//                        WebView webView = new WebView(getActivity());
//                        webView.setVisibility(View.GONE);
                        try {
                            Toast.makeText(getActivity(), "Downloading...\n" + filename, Toast.LENGTH_SHORT).show();

                            getActivity().registerReceiver(attachmentDownloadCompleteReceive, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                            //  Uri Download_Uri = Uri.parse("https://blog.mozilla.org/security/files/2015/05/HTTPS-FAQ.pdf");
                            Uri Download_Uri = Uri.parse(reqUrl);
                            DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                            request.setTitle(filename );
                            request.setMimeType(getMimeType(Download_Uri.toString()));
                            //Setting description of request
                            request.setDescription(" Download complete");
                            request.setDestinationInExternalFilesDir(getActivity(), Environment.DIRECTORY_DOWNLOADS, sdcard_destinationFile);
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            Long id = downloadManager.enqueue(request);
                        } catch (Exception e) {
                            Log.e("e", e.getMessage().toString());
                        }
//                        webview.getSettings().setJavaScriptEnabled(true);
//                        webview.loadUrl(downloadfile);
                        Log.e("inside pdf", "insidepdf");

                    } else {
//                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
//                        {"Result":"Failed","Message":"File not exists"}
                        JSONObject jsonObject = new JSONObject(s);
                        String res = jsonObject.getString("Result");
                        if ("Failed".equals(res)) {
                            Toast.makeText(getActivity(), "File not exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("exception", e.getMessage().toString());
                }
            }
        }
    }

    public void saveToDisk(ResponseBody body, String pdfFileName, String module_type, String test_name) {
        try {
            File sdcard_destinationFile;
            if (module_type.equalsIgnoreCase("ADD")) {

                sdcard_destinationFile = new File(Environment.getExternalStorageDirectory()
                        + "/download/" + pdfFileName + "_" + test_name + ".pdf");
            } else {
                sdcard_destinationFile = new File(Environment.getExternalStorageDirectory()
                        + "/download/" + pdfFileName + ".pdf");
            }


            InputStream is = null;
            OutputStream os = null;

            try {
                Log.e("file", body + "");
                is = body.byteStream();
                os = new FileOutputStream(sdcard_destinationFile);
                //  byte data[] = IOUtils.toByteArray(is);

                byte data[] = new byte[16384];
                Log.e("byte", data + "");
                int count;
                int progress = 0;
                while ((count = is.read(data)) != -1) {
                    progress += count;
                    os.write(data, 0, count);
                    Log.d(TAG, "Progress: " + progress + "/" + body.contentLength() + " >>>> " + (float) progress / body.contentLength());
                }
                Log.d("aaaaa", "Progress: " + progress + "/" + body.contentLength() + " >>>> " + (float) progress / body.contentLength());
                os.flush();

                is.close();
                os.close();
                //   if (is != null) is.close();
                //  if (os != null) os.close();
                Log.d(TAG, "File saved successfully!");
                showPdf(pdfFileName, module_type, test_name);
                return;
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Failed to save the file!");
                return;
            } finally {
//                if (is != null) is.close();
//                if (os != null) os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Failed to save the file!");
            return;
        }
    }

    public void showPdf(String pdfFileName, String module_type, String testName) {
        try {
            File destinationFile;
//            File destinationFile = new File(Environment.getExternalStorageDirectory()
//                    .getAbsolutePath()
//                    + "/download/" + pdfFileName + ".pdf");
            if (module_type.equalsIgnoreCase("ADD")) {
                destinationFile = new File(Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + "/download/" + pdfFileName + "_" + testName + ".pdf");
                abc(destinationFile, pdfFileName, module_type, testName);
                Log.e("filename", destinationFile + "");
            } else {
                destinationFile = new File(Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + "/download/" + pdfFileName + ".pdf");
                Log.e("filename", destinationFile + "");
                abc(destinationFile, pdfFileName, module_type, testName);
            }


        } catch (Exception e) {
            Log.v("error", e.toString());
            Toast.makeText(getActivity(), "Brochure Not Open in Your Mobile!", Toast.LENGTH_SHORT).show();
        }


    }

    void abc(File destinationFile, String pdfFileName, String module_type, String testName) {

        try {

            if (destinationFile.exists()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = null;
                // So you have to use Provider
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(getActivity(),
                            getActivity().getPackageName(),
                            destinationFile);
                    // Add in case of if We get Uri from fileProvider.
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } else {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    uri = Uri.fromFile(destinationFile);
                }
                intent.setDataAndType(uri, "application/pdf");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "No Pdf Viewer found on your device", Toast.LENGTH_SHORT).show();
                }
            } else {
                downloadZipFile(module_type, pdfFileName, testName);
            }
        } catch (Exception e) {
            Log.v("error", e.toString());
            Toast.makeText(getActivity(), "Brochure Not Open in Your Mobile!", Toast.LENGTH_SHORT).show();
        }
    }
}
