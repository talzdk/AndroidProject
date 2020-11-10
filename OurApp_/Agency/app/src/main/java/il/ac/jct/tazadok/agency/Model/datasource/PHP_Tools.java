package il.ac.jct.tazadok.agency.Model.datasource;

import android.content.ContentValues;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Talush122 on 04/03/2017.
 */
//

public class PHP_Tools
{
    public static String GET(String url) throws Exception
    {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        Log.d("php tools", "before getresponse " );
        int responseCode = con.getResponseCode();
        Log.d("php tools", "after getresponse " );
        if (responseCode == HttpURLConnection.HTTP_OK)
        { // success

            Log.d("php tools", " in if " );
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            Log.d("php tools", " the in is    " + in);
            String inputLine;
            StringBuffer response = new StringBuffer();

            Log.d("php tools", " the response is    " + response);
            while ((inputLine = in.readLine()) != null)
            {
                Log.d("php tools", " in while    " );
                response.append(inputLine);
                Log.d("php tools", " after append    " );
            }
            Log.d("php tools", " after while    " );
            in.close();
            Log.d("php tools", " after close    " );
            Log.d("php tools", " response:    "  + response);
            // print result
            return response.toString();
        }
        else
        {
            return "";
        }
    }
    static final String TAG = "PHP_Tools";
    public static String POST(String url, ContentValues params) throws IOException
    {

        Log.d(TAG, "in POST ");
        //Convert Map<String,Object> into key=value&key=value pairs.
        StringBuilder postData = new StringBuilder();
        Log.d(TAG, "postData        " + postData);
        Log.d(TAG, " params.keySet()      " +  params.keySet() + "          entering for ");
        for (String param : params.keySet()) {
            Log.d(TAG, "param     "+param);
            Log.d(TAG, "postData.length()     "+postData.length());
            if (postData.length() != 0){
                Log.d(TAG, "in condition     ");
                postData.append('&');
                Log.d(TAG, "postData     "+postData);
            }
            Log.d(TAG, "outside of the condition        postData     "+postData);
            postData.append(URLEncoder.encode(param, "UTF-8"));
            Log.d(TAG, "postData     "+postData);
            postData.append('=');
            Log.d(TAG, "postData     "+postData);
            postData.append(URLEncoder.encode(String.valueOf(params.get(param)), "UTF-8"));
            Log.d(TAG, "postData     "+postData);
        }
        Log.d(TAG, "outside of the for     ");

        URL obj = new URL(url);
        Log.d(TAG, "obj     "+ obj.toString());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        Log.d(TAG, "con     "+ con);
        con.setRequestMethod("POST");
        Log.d(TAG, "con     "+ con);

        // For POST only - START
        con.setDoOutput(true);
        Log.d(TAG, "con     "+ con);
        OutputStream os = con.getOutputStream();
        Log.d(TAG, "os     "+ os);
        os.write(postData.toString().getBytes("UTF-8"));
        Log.d(TAG, "os     "+ os);
        os.flush();
        Log.d(TAG, "os     "+ os);
        os.close();
        Log.d(TAG, "os     "+ os);
        // For POST only - END
        int responseCode = con.getResponseCode();

        System.out.println("POST Response Code :: " + responseCode);
        Log.d(TAG, "checking if     ");
        if (responseCode == HttpURLConnection.HTTP_OK)
        { //success
            Log.d(TAG, "in if     ");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            Log.d(TAG, "in     "+ in);
            String inputLine;

            StringBuffer response = new StringBuffer();
            Log.d(TAG, "response is    "+ response);
            Log.d(TAG, "before while    ");
            while ((inputLine = in.readLine()) != null) {
                Log.d(TAG, "in while    ");
                response.append(inputLine);
                Log.d(TAG, "response is    "+ response);
            }
            Log.d(TAG, "after while    ");
            in.close();
            Log.d(TAG, "in     "+ in);
            Log.d(TAG, "response is    "+ response);
            return response.toString();
        }
        else return "";
    }
}


