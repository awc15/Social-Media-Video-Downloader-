package com.awcology.smvd.utility;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

public class Util {

    //Create separate folder for fb videos
    public static String rootDirectoryFacebook = "/SMVD/facebook";


    public static File RootDirectory = new File(Environment.getExternalStorageDirectory()
            + "/Download/SMVD/facebook");

    public static void createFolderFacebook() {
        if (!RootDirectory.exists()) {
            RootDirectory.mkdirs();
        }
    }

    public static void showAlertDialog(Context mContext, String title, String description) {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(description);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void download(String downloadPath, String destinationPath, Context context, String fileName) {
        Toast.makeText(context, "Downloading Started.", Toast.LENGTH_LONG).show();

        Uri uri = Uri.parse(downloadPath);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setMimeType("video/mp4");
        request.setVisibleInDownloadsUi(true);
        request.setTitle(fileName);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, destinationPath + fileName);
        ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
    }
}
