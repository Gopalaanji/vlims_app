package rsr.info.basic.viswaLab.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import it.sephiroth.android.library.tooltip.TooltipManager;
import rsr.info.basic.viswaLab.R;

/**
 * Created by E5000096 on 19-07-2016.
 */
public class BaseFragment extends Fragment {
    private static final int REQUEST_PHONE_CALL = 1;

    public void showTooltip(View clicked_view, CharSequence display_string, TooltipManager.Gravity position) {
        TooltipManager.getInstance(getActivity())
                .create(100)
                .anchor(clicked_view, position)
                .closePolicy(TooltipManager.ClosePolicy.TouchOutside, 60000)
                .activateDelay(800)
                .text(display_string)
                .withStyleId(R.style.ToolTipLayoutDefaultStyle)
                .show();
    }

    public void contactRsr(String number) {
        if (hasCallPermision()) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + number));
            startActivity(callIntent);
        } else {
            requestCallPermission();
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
                    callIntent.setData(Uri.parse("tel:8008020666"));
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
