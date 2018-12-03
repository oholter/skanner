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
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class JsonCommunicator {

    // SETT INN ADDRESSEN TIL SIDEN HER 10.0.2.2:5000 er addr til lokal flask server
    private static final String SERVER_ADDR = "http://10.0.2.2:5000/";

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

            try {
                URL url = new URL(params[0]);
                Log.i("URL:", url.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                Log.i("JSON:", params[1]);

                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(params[1]);
                os.flush();
                os.close();

                Log.i("STATUS:", String.valueOf(conn.getResponseCode()));
                Log.i("MSG:", conn.getResponseMessage());

                conn.disconnect();
            } catch (Exception e) {
                Log.i("JSON exception:", e.getMessage());
                e.printStackTrace();
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
