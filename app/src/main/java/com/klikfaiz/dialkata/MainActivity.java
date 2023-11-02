package com.klikfaiz.dialkata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView percentage, bytesDownload;
    ProgressBar progressBar;
    Button btnDownload, btnPause, btnResume;

    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 101;
    private boolean isDownloading = false;
    private DownloadTask downloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        percentage = findViewById(R.id.percentage);
        bytesDownload = findViewById(R.id.bytesDownload);
        progressBar = findViewById(R.id.progressBar);
        btnDownload = findViewById(R.id.buttonDownload);
        btnPause = findViewById(R.id.buttonPause);
        btnResume = findViewById(R.id.buttonResume);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDownloading) {
                    Toast.makeText(MainActivity.this, "Download in progress", Toast.LENGTH_SHORT).show();
                } else {
                    if (isWriteStoragePermissionGranted()) {
                        
                        downloadFile();
                    }
                }
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (downloadTask != null) {
                    downloadTask.cancel(true);
                }
            }
        });

        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isDownloading) {
                    downloadFile();
                } else {
                    Log.i("ao", "onClick: ");
                    downloadFile();
                }
            }
        });
    }

    private boolean isWriteStoragePermissionGranted() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadFile();
            } else {
                Toast.makeText(this, "Permission denied. Cannot download the file.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void downloadFile() {
        // URL of the file to download
        String fileUrl = "http://146.196.106.202:28084/paxstorelite/test";
        // Local file path to save the downloaded file
        String localFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ke1.jpg";

        downloadTask = new DownloadTask();
        downloadTask.execute(fileUrl, localFilePath);
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            isDownloading = true;
            String fileUrl = params[0];
            String localFilePath = params[1];

            try {
                URL url = new URL(fileUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Check if the local file exists, and if so, resume the download from where it left off
                File localFile = new File(localFilePath);
                if (localFile.exists()) {
                    long downloadedBytes = localFile.length();
                    connection.setRequestProperty("Range", "bytes=" + downloadedBytes + "-");
                }

                connection.connect();

                int fileLength = connection.getContentLength();

                // Input stream to read the file
                InputStream input = new BufferedInputStream(connection.getInputStream());

                // Output stream to write the file
                FileOutputStream output = new FileOutputStream(localFilePath, true); // Append mode

                byte data[] = new byte[1024];
                int total = (int) localFile.length();
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;

                    if (isCancelled()) {
                        input.close();
                        return null;
                    }

                    output.write(data, 0, count);
                    int progress = (int) ((total * 100) / fileLength);
                    publishProgress(progress);
                    bytesDownload.setText(total + " / " + fileLength + " bytes");
                }

                output.flush();
                output.close();
                input.close();
                isDownloading = false;
                return localFilePath;
            } catch (IOException e) {
                e.printStackTrace();
                isDownloading = false;
                return null;
            }
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int progress = values[0];
            progressBar.setProgress(progress);
            percentage.setText(progress + "%");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                Toast.makeText(MainActivity.this, "Download complete. File saved at " + result, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Download failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}