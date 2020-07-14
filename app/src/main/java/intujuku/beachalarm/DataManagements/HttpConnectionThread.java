package intujuku.beachalarm.DataManagements;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by p on 2016-02-04.
 */

/*  HttpConnectionThread extends AsyncTask
    App transfer some data to Web
    use Post
*/
public class HttpConnectionThread extends AsyncTask<String, Void, String> {
    Context connect_context;

    String echo_message = "";
    public HttpConnectionThread(Context context) {  //this is Constructor
        connect_context = context;
    }
    String rep;
    @Override
    protected String doInBackground(String... str) {
        // URL 연결이 구현될 부분
        URL urls;
        String response = null;
        try {
            urls = new URL(str[0]);
            String msg = str[1];
            byte[] reqMsg = msg.toString().getBytes("utf-8");
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //Write
            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(msg);
            writer.close();
            outputStream.close();

            //Read
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            echo_message = sb.toString();
            Log.d("RESPONSE", "The response is: " + echo_message);
        }
        catch (IOException e) {
            echo_message = DefaultValue.CONNECTION_FAIL;
        }
        return echo_message;
    }
}