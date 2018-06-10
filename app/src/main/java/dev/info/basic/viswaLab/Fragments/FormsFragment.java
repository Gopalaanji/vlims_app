package dev.info.basic.viswaLab.Fragments;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;

import dev.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import dev.info.basic.viswaLab.Activitys.WebViewActivity;
import dev.info.basic.viswaLab.BuildConfig;
import dev.info.basic.viswaLab.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormsFragment extends Fragment {

    private LoginFragmentActivity fragmentActivity;

    public FormsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_forms, container, false);
        setHasOptionsMenu(true);
        fragmentActivity = (LoginFragmentActivity) getActivity();
        fragmentActivity.setActionBarTitle("Online Forms");
        LinearLayout btnmsds = (LinearLayout) rootView.findViewById(R.id.btnmsds);
        btnmsds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf("MSDS");
            }
        });
        LinearLayout btninvoice = (LinearLayout) rootView.findViewById(R.id.btninvoice);
        btninvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf("CI");
            }
        });
        return rootView;
    }

    private void openPdf(String fileName) {
      File  destinationFile = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/download/" + fileName+".pdf");

if(destinationFile.exists()){
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

}else{
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://173.11.229.171/viswaweb/VLReports/SampleReports/"+fileName+".PDF"));
    getActivity().startActivity(browserIntent);
}

    }

}
