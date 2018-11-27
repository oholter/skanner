/**
 * Denne klassen inneholder funksjonalitet for opprettelse av JSON-objekter og sending av
 * slike objekter over nettverket til en web-server
 */
package com.skanner.org.skanner;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class JsonCommunicator {

    // SETT INN ADDRESSEN TIL SIDEN HER
    private static final String SERVER_ADDR = "HTTP://SOME_LOCAL_SERVER/";

    public static JSONObject createJsonObject(String first, String second, boolean feil) {
        JSONObject object = new JSONObject();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());

        try {
            object.put("user", User.getInstance().getUserName());
            object.put("time", formattedDate);
            object.put("first", first);
            object.put("second", second);
            object.put("feil", feil);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static void sendJsonObjectToServer(JSONObject object) {
        new SendJsonObject().execute(SERVER_ADDR, object.toString());
    }

    private static class SendJsonObject extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes("PostData=" + params[1]);
                wr.flush();
                wr.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }
}
