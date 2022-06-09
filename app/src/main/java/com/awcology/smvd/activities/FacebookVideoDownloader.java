package com.awcology.smvd.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.awcology.smvd.R;
import com.awcology.smvd.databinding.ActivityFacebookVideoDownloaderBinding;
import com.awcology.smvd.utility.Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FacebookVideoDownloader extends AppCompatActivity {

    private ActivityFacebookVideoDownloaderBinding binding;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialization();
        binding.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Util.isNetworkConnected(mContext)) {
                    getFacebookData();
                } else {
                    Util.showAlertDialog(mContext, "ERROR", "No internet.");
                }
            }
        });

    }

    private void initialization() {
        mContext = this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_facebook_video_downloader);
    }

    private void getFacebookData() {
        URL url = null;
        try {
            url = new URL(binding.etUrl.getText().toString());
            String host = url.getHost();

            //added second check
            if (host.contains("facebook.com") || host.contains("fb.watch")) {
                Toast.makeText(mContext, "Checking for validity of URL", Toast.LENGTH_LONG).show();
                new getFbData().execute(binding.etUrl.getText().toString());
            } else {
                Util.showAlertDialog(mContext, "ERROR", "URL is invalid");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    class getFbData extends AsyncTask<String, Void, String> {

        Document document;

        @Override
        protected String doInBackground(String... strings) {
            try {
                document = Jsoup.connect(strings[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }

            if (document.select("meta[property=\"og:video\"]").last() != null) {
                String url = document.select("meta[property=\"og:video\"]").last().attr("content");
                return url;
            } else {
                return "";
            }
        }

        @Override
        protected void onPostExecute(String videoURL) {
            if (!videoURL.equals("")) {
                Util.download(videoURL, Util.rootDirectoryFacebook, mContext, "smvd_" + System.currentTimeMillis() + ".mp4");
            } else {
                Util.showAlertDialog(mContext, "ERROR", "URL is invalid or issue due to privacy policy.");
            }
        }
    }


}



