package dev.info.basic.viswaLab.Fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import dev.info.basic.viswaLab.Activitys.LoginFragmentActivity;
import dev.info.basic.viswaLab.R;

/**
 * Created by RSR on 07-09-2017.
 */

public class UploadDocumentsFragment extends BaseFragment implements View.OnClickListener {

    private static final int REQUEST_CAMERA_PIC_FILE = 987;
    private static final int SELECT_GALLERY_PIC_FILE = 789;
    private View rootView;
    private LoginFragmentActivity fragmentActivity;
    Button btnPancard, btnletterHead, btmrubber, btnstate, btnTax, btnCriminal, btnOther;
    TextView txtPancard, txtletterHead, txtrubber, txtstate, txtTax, txtCriminal, txtOther;
    private File destinationFile = null;
    private Bitmap file_thumbnail;
    private TextView doneText;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.rera_agent_upload_documents_fragment, container, false);
        setHasOptionsMenu(true);
        fragmentActivity = (LoginFragmentActivity) getActivity();
        btnPancard = (Button) rootView.findViewById(R.id.btnPancard);
        btnletterHead = (Button) rootView.findViewById(R.id.btnletterHead);
        btmrubber = (Button) rootView.findViewById(R.id.btmrubber);
        btnstate = (Button) rootView.findViewById(R.id.btnstate);
        btnTax = (Button) rootView.findViewById(R.id.btnTax);
        btnCriminal = (Button) rootView.findViewById(R.id.btnCriminal);
        btnOther = (Button) rootView.findViewById(R.id.btnOther);
        txtPancard = (TextView) rootView.findViewById(R.id.txtPancard);
        txtletterHead = (TextView) rootView.findViewById(R.id.txtletterHead);
        txtrubber = (TextView) rootView.findViewById(R.id.txtrubber);
        txtstate = (TextView) rootView.findViewById(R.id.txtstate);
        txtTax = (TextView) rootView.findViewById(R.id.txtTax);
        txtCriminal = (TextView) rootView.findViewById(R.id.txtCriminal);
        txtOther = (TextView) rootView.findViewById(R.id.txtOther);

        btnPancard.setOnClickListener(this);
        btnletterHead.setOnClickListener(this);
        btmrubber.setOnClickListener(this);
        btnstate.setOnClickListener(this);
        btnTax.setOnClickListener(this);
        btnCriminal.setOnClickListener(this);
        btnOther.setOnClickListener(this);
        fragmentActivity.displayActionBar();
        fragmentActivity.setActionBarTitle("UPLOADING DOCUMENTS");
        fragmentActivity.showActionBar();
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPancard:
                choiceFunction();
                doneText = txtPancard;
                break;
            case R.id.btnletterHead:
                choiceFunction();
                doneText = txtletterHead;
                break;
            case R.id.btmrubber:
                choiceFunction();
                doneText = txtrubber;
                break;
            case R.id.btnstate:
                choiceFunction();
                doneText = txtstate;
                break;
            case R.id.btnTax:
                choiceFunction();
                doneText = txtTax;
                break;
            case R.id.btnCriminal:
                choiceFunction();
                doneText = txtCriminal;
                break;
            case R.id.btnOther:
                choiceFunction();
                doneText = txtOther;
                break;
        }
    }

    private void choiceFunction() {
        final CharSequence[] items = {"Images", "Documents",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(fragmentActivity);
        builder.setTitle("Select File To Upload");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Images")) {
                    showImageChooser();
                } else if (items[item].equals("Documents")) {
                    showFileChooser();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void showImageChooser() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(fragmentActivity);
        builder.setTitle("Select profile Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA_PIC_FILE);
                } else if (items[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_GALLERY_PIC_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("file/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    1);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                onSelectDocumentResult(data);
            } else if (requestCode == SELECT_GALLERY_PIC_FILE) {
                onSelectFromGalleryResult(data);
            } else if (requestCode == REQUEST_CAMERA_PIC_FILE) {
                onCaptureImageResult(data);
            }
        }
    }

    private void onSelectDocumentResult(Intent data) {
        Uri selectedFileURI = data.getData();
        File file = new File(selectedFileURI.getPath().toString());
        Log.v("FILE_NAME==>DOCUMENT", "NAME*=>" + file.getName());
        String uploadedFileName = file.getName().toString();
        StringTokenizer tokens = new StringTokenizer(uploadedFileName, ":");
        String first = tokens.nextToken();
        Log.v("Filexxx", first);
        doneText.setVisibility(View.VISIBLE);
    }

    private void onCaptureImageResult(Intent data) {
        file_thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        file_thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        destinationFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destinationFile.createNewFile();
            fo = new FileOutputStream(destinationFile);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (destinationFile != null) {
            Log.v("FILE_NAME==>CAMERA", "NAME*=>" + destinationFile);
            doneText.setVisibility(View.VISIBLE);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        try {
            Uri selectedImageUri = data.getData();
            final InputStream imageStream = getContext().getContentResolver().openInputStream(selectedImageUri);
            file_thumbnail = BitmapFactory.decodeStream(imageStream);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            file_thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            destinationFile = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");
            FileOutputStream fo;
            try {
                destinationFile.createNewFile();
                fo = new FileOutputStream(destinationFile);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (destinationFile != null) {
                Log.v("FILE_NAME==>Gallery", "NAME*=>" + destinationFile);
                doneText.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Log.i("Exception", e.toString());
        }
    }
}
