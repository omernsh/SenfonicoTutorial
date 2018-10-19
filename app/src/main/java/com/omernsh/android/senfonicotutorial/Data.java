package com.omernsh.android.senfonicotutorial;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


enum Download { IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK}

public class Data {
    private String LOG_TAG = Data.class.getSimpleName();
    private String mRawUrl;
    private String mData;
    private Download mDownload;

    public Data(String mRawUrl) {
        this.mRawUrl = mRawUrl;
        this.mDownload = Download.IDLE;
    }



    public String getmData() {
        return mData;
    }

    public Download getmDownload() {
        return mDownload;
    }

    public void setmRawUrl(String mRawUrl) {
        this.mRawUrl = mRawUrl;
    }

    public void execute() {
        this.mDownload = Download.PROCESSING;
        DownloadRawData downloadRawData = new DownloadRawData();
        downloadRawData.execute(mRawUrl);
    }

    public class DownloadRawData extends AsyncTask<String, Void, String> {

        protected void onPostExecute(String webData) {
            mData = webData;
            Log.v(LOG_TAG, "Data: " +mData);
            if(mData == null) {
                if(mRawUrl == null) {
                    mDownload = Download.NOT_INITIALISED;
                } else {
                    mDownload = Download.FAILED_OR_EMPTY;
                }
            } else {

                mDownload = Download.OK;
            }
        }


        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            if(params == null)
                return null;

            try {
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                if(inputStream == null) {
                    return null;
                }

                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();

            } catch(IOException e) {
                Log.e(LOG_TAG, "Error", e);
                return null;
            } finally {
                if(urlConnection != null) {
                    urlConnection.disconnect();
                }
                if(reader != null) {
                    try {
                        reader.close();
                    } catch(final IOException e) {
                       Log.e(LOG_TAG,"Error stream", e);
                    }
                }
            }
        }
    }
}
