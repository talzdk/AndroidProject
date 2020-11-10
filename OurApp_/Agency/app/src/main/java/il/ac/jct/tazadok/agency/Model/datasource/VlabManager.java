package il.ac.jct.tazadok.agency.Model.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import il.ac.jct.tazadok.agency.Model.backend.Contact;
import il.ac.jct.tazadok.agency.Model.backend.DB_Manager;
import il.ac.jct.tazadok.agency.Model.entities.Activity_;
import il.ac.jct.tazadok.agency.Model.entities.Business;
import il.ac.jct.tazadok.agency.Model.entities.User;

/**
 * Created by Talush122 on 02/02/2017.
 */

public class VlabManager implements DB_Manager {


    private final String UserName="tazadok";
    private final String WEB_URL = "http://"+UserName+".vlab.jct.ac.il";


    private  boolean flagBusiness=false;
    private boolean flagActivity=false;
    final String TAG = "vlabmanager";

    @Override
    public long add_business(ContentValues b)
    {
        try
        {
            Log.d(TAG, "before post ");
            String result = PHP_Tools.POST(WEB_URL + "/businesses.php", b);
            Log.d(TAG, "after post ");
            Log.d(TAG, "result        " + result);

            if (result.matches("New business created successfully")) {
                flagBusiness = true;
                return Tools.ContentValuesToBusiness(b).getId();
            }

        }
        catch(IOException e)
        {
            e.printStackTrace();
            return 0;

        }
        return  -1;
    }

    @Override
    public long add_activity_(ContentValues a)
    {
        try
        {
            String result = PHP_Tools.POST(WEB_URL + "/activities.php", a);
            if (result.matches("New activity created successfully"))
            {
                flagActivity=true;
                return Tools.ContentValuesToActivity_(a).getCode();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public long add_user(ContentValues u)
    {
        try
        {
            String s= PHP_Tools.POST(WEB_URL + "/users.php", u);
            if (s.matches("New user created successfully")) {
                return Tools.ContentValuesToUser(u).getUser_code();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return 0;
        }
        return  -1;
    }
    @Override
    public Cursor get_businesses()
    {
        try {
            String[] columns = new String[]
                    {
                            Contact.Business.ID,
                            Contact.Business.NAME,
                            Contact.Business.ADDRESS,
                            Contact.Business.PHONE,
                            Contact.Business.EMAIL,
                            Contact.Business.WEBSITE
                    };

            MatrixCursor matrixCursor = new MatrixCursor(columns);
            String str = PHP_Tools.GET(WEB_URL + "/businesses.php");
            Log.d(TAG, "after GET ");

            JSONArray array = new JSONObject(str).getJSONArray("products");
            Log.d(TAG, "array       "+ array.toString() +   "     array length    "+  array.length());
            Log.d(TAG, "before the for       ");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = null;

                jsonObject = array.getJSONObject(i);

                matrixCursor.addRow(new Object[]{
                        jsonObject.getInt("Id"),
                        jsonObject.getString("Name"),
                        jsonObject.getString("Address"),
                        jsonObject.getString("Phone"),
                        jsonObject.getString("Email"),
                        jsonObject.getString("Website")
                });
            }

            Log.d(TAG, "after the for      matrixCursor        "+matrixCursor);
            return matrixCursor;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Cursor get_Activities() {
        try {
            String[] columns = new String[]
                    {
                            Contact.Activity_.COUNTRY,
                            Contact.Activity_.START,
                            Contact.Activity_.END,
                            Contact.Activity_.PRICE,
                            Contact.Activity_.SHORT_DES,
                            Contact.Activity_.BUSINESS_ID,
                            Contact.Activity_.DESCRIPTION,
                            Contact.Activity_.ACTIVITY_CODE
                    };

            MatrixCursor matrixCursor = new MatrixCursor(columns);
            String str = PHP_Tools.GET(WEB_URL + "/activities.php");
            JSONArray array = new JSONObject(str).getJSONArray("activities");


            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = null;

                jsonObject = array.getJSONObject(i);


                matrixCursor.addRow(new Object[]{
                        jsonObject.getString("Country"),
                        jsonObject.getString("Start"),
                        jsonObject.getString("End"),
                        jsonObject.getInt("Price"),
                        jsonObject.getString("ShortDescription"),
                        jsonObject.getInt("BusinessID"),
                        jsonObject.getString("Description"),
                        jsonObject.getString("Id")
                });
            }
            return matrixCursor;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean get_business(int id)
    {

        try {
            String str = PHP_Tools.GET(WEB_URL + "/businesses.php?" + "BusinessID=" + id);
            JSONArray array = new JSONObject(str).getJSONArray("products");
            return array.length() == 1;
        }
        catch (Exception e) {}
        return false;
    }

    @Override
    public Cursor get_users()
    {
        try {
            String[] columns = new String[]
                    {
                            Contact.User.USER_CODE,
                            Contact.User.USER_NAME,
                            Contact.User.PASSWORD,
                    };

            MatrixCursor matrixCursor = new MatrixCursor(columns);
            Log.d("vlabmanager", "before get " );
            String str = PHP_Tools.GET(WEB_URL + "/users.php");
            Log.d("vlabmanager", "str "  +     str);
            Log.d("vlabmanager", "after get " );
            if(!str.equals("")){
                Log.d("vlabmanager", "before json " );
                JSONArray array = (new JSONObject(str)).getJSONArray("users");
                Log.d("vlabmanager", "after json " );
                Log.d("vlabmanager", "before for " );
                for (int i = 0; i < array.length(); i++) {
                    Log.d("vlabmanager", "in for loop " + i);
                    JSONObject jsonObject = null;
                    Log.d("vlabmanager", "after assignment" );
                    jsonObject = array.getJSONObject(i);
                    Log.d("vlabmanager", "after assignment getJSONObject    "+jsonObject );
                    Object[] objects=new Object[]{
                            jsonObject.getString("Code"),
                            jsonObject.getString("UserName"),
                            jsonObject.getString("Password"),};
                    Log.d("vlabmanager", "after assignment object    "+objects.toString() );
                    if(!objects[0].equals("0"))
                        matrixCursor.addRow(objects);
                }
            }else matrixCursor=null;



            return matrixCursor;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean is_added_activity_()
    {
        if(flagActivity==true)
        {
            flagActivity=false;
            return true;
        }
        else return false;
    }

    @Override
    public boolean is_added_business() {
        if (flagBusiness == true)
        {
            flagBusiness = false;
            return true;
        }
        else return false;
    }

    public int updatecode (ContentValues contentValues){

        try
        {
            String result = PHP_Tools.POST(WEB_URL + "/codes.php", contentValues);

            if (result.matches("New codes updated successfully")) {
                    return 1;
            }

        }
        catch(IOException e)
        {
            e.printStackTrace();
            return 0;

        }
        return  -1;


    }
    public Cursor get_codes()
    {
        try {
            String[] columns = new String[]
                    {
                           Contact.Code.CODEA,
                            Contact.Code.CODEB,
                            Contact.Code.CODEU

                    };

            MatrixCursor matrixCursor = new MatrixCursor(columns);
            String str = PHP_Tools.GET(WEB_URL + "/codes.php");
            Log.d(TAG, "after GET ");

            JSONArray array = new JSONObject(str).getJSONArray("codes");
            Log.d(TAG, "array       "+ array.toString() +   "     array length    "+  array.length());
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = null;

                jsonObject = array.getJSONObject(i);

                matrixCursor.addRow(new Object[]{
                        jsonObject.getInt("Acode"),
                        jsonObject.getInt("Bcode"),
                        jsonObject.getInt("Ucode"),

                });
            }

            Log.d(TAG, "after the for      matrixCursor        "+matrixCursor);
            return matrixCursor;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
