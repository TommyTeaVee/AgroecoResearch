package ojovoz.agroecoresearch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Eugenio on 13/03/2017.
 */
public class httpConnection extends AsyncTask<String, Void, String> {

    Context context;
    private String action;
    public AsyncResponse delegate = null;

    httpConnection(Context c, AsyncResponse delegate){
        this.context=c;
        this.delegate=delegate;
    }

    public interface AsyncResponse {
        void processFinish(String output);
    }

    @Override
    protected String doInBackground(String... params) {

        action = params[1];
        return getData(params[0]);

    }

    @Override
    protected void onPostExecute(String ret) {
        delegate.processFinish(ret);
    }

    private String getData(String u) {
        String ret = null;
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(u);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            ret = readStream(urlConnection.getInputStream());
        } catch (Exception e) {
            ret = "";
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return ret;
    }

    private String readStream(InputStream in) throws IOException {

        BufferedReader r = null;
        r = new BufferedReader(new InputStreamReader(in));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        if (r != null) {
            r.close();
        }
        in.close();
        return total.toString();
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
