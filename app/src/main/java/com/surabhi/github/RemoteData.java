package com.surabhi.github;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Surabhi Agrawal on 1/19/2017.
 */
public class RemoteData {
    static String TAG = "RemoteData";
    /**
     * This methods returns a Connection to the specified URL,
     * with necessary properties like timeout and user-agent
     * set to your requirements.
     *
     * @param url
     * @return
     */


    /**
     * A very handy utility method that reads the contents of a URL
     * and returns them as a String.
     *
     * @param url
     * @return
     */
    public static String readContents(String url) {

        HttpURLConnection httpURLConnection = null;
        String result;

        try {
            URL url1 = new URL(url);
            Log.d(TAG, url1.toString());
            httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(30000); // Timeout at 30 seconds
            BufferedReader bufferReader = new BufferedReader
                    (new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            int responseCode = httpURLConnection.getResponseCode();
            String responseText = httpURLConnection.getResponseMessage();
            if(responseCode == 200) {
                while ((line = bufferReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferReader.close();
                result = stringBuilder.toString();
                Log.d(TAG , "Result:"+ result);
                return result;
            }
            else{
                Log.d(TAG, "Response failed:"+responseCode + " " + responseText);
                return null;
            }

        }
        catch (MalformedURLException e) {
            Log.e("getConnection()",
                    "Invalid URL: "+e.toString());
            return null;
        }
        catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }
        finally {
            httpURLConnection.disconnect();
        }
    }
}
