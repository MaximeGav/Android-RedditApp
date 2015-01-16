package school.reddittestvoorexamen1.utils.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import school.reddittestvoorexamen1.OnPostListener;
import school.reddittestvoorexamen1.utils.constants.AppConstants;
import school.reddittestvoorexamen1.utils.json.JSONParser;

public class DownloadTask extends AsyncTask<String,String,String> {

    public static final String TAG = "download_task";

    private ProgressDialog progressDialog;
    private Context context;
    private String result = "";
    private OnPostListener onPostListener;
    private JSONParser jsonParser;

    public DownloadTask(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Downloading the posts...");

        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                cancel(true);
           }
        });
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "OnPreExecute");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... post) {
        URL url = null;

        try {
            this.onPostListener.onPostDownloading();
            url = new URL(AppConstants.API_URL);

            URLConnection connection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream in = httpURLConnection.getInputStream();
                processStream(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
            this.onPostListener.onPostReceived(true, result);


        progressDialog.dismiss();
        super.onPostExecute(result);
    }

    private void processStream(InputStream input) {
        // Convert response to string using String Builder
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(input, "iso-8859-1"), 8);
            StringBuilder sBuilder = new StringBuilder();

            String line = null;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            input.close();
            result = sBuilder.toString();

        } catch (Exception e) {
            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
        }
    }


    public void setOnPostListener(OnPostListener onPostListener) {
        this.onPostListener = onPostListener;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
