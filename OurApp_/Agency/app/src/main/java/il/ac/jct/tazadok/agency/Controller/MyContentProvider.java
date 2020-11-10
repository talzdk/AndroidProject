package il.ac.jct.tazadok.agency.Controller;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import il.ac.jct.tazadok.agency.Model.backend.DB_Manager;
import il.ac.jct.tazadok.agency.Model.backend.DB_ManagerFactory;
import il.ac.jct.tazadok.agency.Model.datasource.Tools;


public class MyContentProvider extends ContentProvider {


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete " + uri.toString());

        String listName = uri.getLastPathSegment();
        int id = (int)ContentUris.parseId(uri);
        switch (listName) {
//            case "activities":
//                if (manager.deleteActivity(id))
//                    return 1;
//                break;
//
//            case "businesses":
//                if (manager.deleteBusiness(id))
//                    return 1;
//                break;
//            case "users":
//                if(manager.deleteUser(String.valueOf(id)))
//                    return 1;
//                break;
        }
        return 0;
    }

    DB_Manager manager = DB_ManagerFactory.getManager();
    final String TAG = "myContent";
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI("il.ac.jct.tazadok.agency", "/*", 1);/*'*' = every authority*/
    }

    public MyContentProvider() {
        Log.d(TAG, "ctor ");
    }


    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType " + uri.toString());
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert " + uri.toString());
        int code = uriMatcher.match(uri);
        String listName = uri.getLastPathSegment();
        long id = -1;
        if (code == 1) {
            Log.d(TAG, "in the condition ");
            switch (listName) {
                case "users":
                    try {
                        Log.d(TAG, "in case users ");
                        id = manager.add_user(values);
                        Log.d(TAG, "the size if arr" + (DB_ManagerFactory.getManager()).get_users().getCount());
                        Log.d(TAG, "the id of new user " + id);
                    } catch (Exception e) {
                        Log.d(TAG, "on error");
                        e.printStackTrace();
                    }
                    return ContentUris.withAppendedId(uri, id);

                case "businesses":
                    try {
                        Log.d(TAG, "in case businesses ");
                        id = manager.add_business(values);

                        Log.d(TAG, "the id of new busi " + id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return ContentUris.withAppendedId(uri, id);

                case "activities":
                    try {
                        Log.d(TAG, "in case activities ");
                        id = manager.add_activity_(values);

                        Log.d(TAG, "the id of new acti " + id);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                    return ContentUris.withAppendedId(uri, id);

            }
        }
        return null;


    }
    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate");
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query " + uri.toString());
        int code = uriMatcher.match(uri);
        String listName = uri.getLastPathSegment();

        if (code == 1)
        {
            Log.d(TAG, "entered to the condition");
            Log.d(TAG, "listname is:  " + listName);

            switch (listName) {
                case "users":
                    Log.d(TAG, "entered to the users");
                    Cursor c=manager.get_users();
                    Log.d(TAG, "the manager returned this:  " + c);
                    return c;

                case "activities":
                    Log.d(TAG, "entered to the get_Activities");
                    return manager.get_Activities();

                case "businesses":
                    Log.d(TAG, "entered to the get_businesses");
                    return manager.get_businesses();
                case "codes":
                    Log.d(TAG, "entered to the get_codes");
                    return manager.get_codes();

            }
        }

        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        Log.d(TAG, "update " + uri.toString());
        manager.updatecode(values);

        return 0;
    }
}
