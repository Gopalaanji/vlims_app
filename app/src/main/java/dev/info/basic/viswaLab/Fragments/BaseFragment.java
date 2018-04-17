package dev.info.basic.viswaLab.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import dev.info.basic.viswaLab.R;

/**
 * Created by E5000096 on 19-07-2016.
 */
public class BaseFragment extends Fragment {
    private static final int REQUEST_PHONE_CALL = 1;
    public static String number = "";

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
                Intent browse = new Intent(getActivity(), WebViewActivity.class);
                browse.putExtra("pdf_id", url);
                getActivity().startActivity(browse);
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
//    public void renderFeatureLockedpage(String header, String image_url, final String referral, LinearLayout parent_tab) {
//        final View locked_page_view = getActivity().getLayoutInflater().inflate(R.layout.pro_version_locked_page, parent_tab, false);
//        locked_page_view.findViewById(R.id.page_text_header).setVisibility(View.VISIBLE);
//        ((TextView) locked_page_view.findViewById(R.id.page_text_header)).setText(header);
//        Glide.with(getActivity()).load(image_url)
//                .crossFade()
//                .thumbnail(0.5f)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(((ImageView) locked_page_view.findViewById(R.id.feature_screenshot)));
//        parent_tab.addView(locked_page_view);
//    }
}
