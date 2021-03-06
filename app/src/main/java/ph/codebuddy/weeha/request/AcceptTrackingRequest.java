package ph.codebuddy.weeha.request;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import ph.codebuddy.weeha.R;

/**
 * Created by rommeldavid on 24/07/16.
 */
public class AcceptTrackingRequest {
    Context context;
    String id;
    SharedPreferences spUtils;
    private OnTaskCompleted listener;
    public static String TAG = "[Request request..]";

    public AcceptTrackingRequest(Context context, String id, SharedPreferences spUtils, OnTaskCompleted listener) {
        this.context = context;
        this.id = id;
        this.spUtils = spUtils;
        this.listener = listener;
    }

    public void executeAcceptTrackingRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new acceptTrackingRequest().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            new acceptTrackingRequest().execute();
        }
    }

    public class acceptTrackingRequest extends AsyncTask<String, Integer, Boolean> {
        Boolean asyncResult;
        String stringResult;
        ProgressDialog progDl;
        HttpResponse response;

        @Override
        protected void onPreExecute() {
            progDl = new ProgressDialog(context);
            progDl.setTitle("Please wait...");
            progDl.setCancelable(false);
            progDl.show();

        }

        @Override
        protected Boolean doInBackground(String... params) {
            String url = context.getString(R.string.weeha_url) + "relationships/" + id + "/approve";
            Log.v(TAG, "URL:" + url);

            try {
                String boundary = "---------------------------This is the boundary";
                HttpClient client = new DefaultHttpClient();

                MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, boundary, null);

                HttpPut do_this;

                do_this = new HttpPut(url);

                do_this.addHeader("Content-Type", "multipart/form-data; boundary="
                        + boundary);
                do_this.addHeader(new BasicHeader("Authorization", spUtils.getString("access_token", "")));
                do_this.setEntity(entity);
                response = client.execute(do_this);

                stringResult = EntityUtils.toString(response.getEntity());

                if (response.getStatusLine().getStatusCode() == 201) {
                    asyncResult = true;
                    Log.v("Accept Track : ", stringResult);
                } else if (response.getStatusLine().getStatusCode() == 200) {
                    asyncResult = true;
                    Log.v("Accept Track : ", stringResult);
                } else {
                    stringResult = "Accept Track Failed : " + stringResult + "  " + response.getStatusLine().getStatusCode();
                    Log.v(" ", stringResult);
                    asyncResult = false;
                }

            } catch (IOException e) {
                e.printStackTrace();
                asyncResult = false;
            }

            return asyncResult;
        }

        protected void onPostExecute(Boolean result) {
            listener.onTaskCompleted(result, stringResult);
            progDl.dismiss();
        }
    }
}