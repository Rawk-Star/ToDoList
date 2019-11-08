package com.zybooks.to_dolist;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionsUtil {

    // Checks if a specified permission is already granted and if not, asks the user
    // to grant the permission. May also display explanation for why permission is
    // needed, if appropriate.
    // Returns true if permission is already granted.  Otherwise returns false.
    public static boolean hasPermissions(final Activity activity, final String permission,
                                         int rationaleMessageId, final int requestWriteCode) {
        // See if permission is granted
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Explain why permission needed?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {

                // Show why permission is needed
                showPermissionRationaleDialog(activity, rationaleMessageId, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Request permission again
                        ActivityCompat.requestPermissions(activity,
                                new String[] { permission }, requestWriteCode);
                    }
                });
            }
            else {
                // Request permission
                ActivityCompat.requestPermissions(activity,
                        new String[] { permission }, requestWriteCode);
            }
            return false;
        }
        return true;
    }

    // Displays a dialog that explains the reason why a permission is required.
    private static void showPermissionRationaleDialog(Activity activity, int messageId,
                                                      DialogInterface.OnClickListener onClickListener) {
        // Show dialog explaining why permission is needed
        new AlertDialog.Builder(activity)
                .setTitle(R.string.permission_needed)
                .setMessage(messageId)
                .setPositiveButton(R.string.ok, onClickListener)
                .create()
                .show();
    }
}