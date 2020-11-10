package il.ac.jct.tazadok.agency.Model.datasource;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import il.ac.jct.tazadok.agency.Model.backend.DB_Manager;
import il.ac.jct.tazadok.agency.Model.entities.Activity_;
import il.ac.jct.tazadok.agency.Model.entities.Business;
import il.ac.jct.tazadok.agency.Model.entities.Description;
import il.ac.jct.tazadok.agency.Model.entities.User;

/**
 * Created by Talush122 on 05/12/2016.
 */

public class Lists implements DB_Manager {


    final String TAG = "Lists";
    static ArrayList<Business> b_list;
    static ArrayList<User> u_list;
    static ArrayList<Activity_> a_list;

    public Lists() {
        b_list= new ArrayList<>();
        u_list= new ArrayList<>();
        a_list= new ArrayList<>();

    }

    boolean isUpdate = false;
    boolean isAddedBusiness = false;
    boolean isAddedActivity_ = false;


    @Override
    public long add_user(ContentValues user)  {
        Log.d(TAG, "in func add user");
        User user1=Tools.ContentValuesToUser(user);
        u_list.add(user1);
        Log.d(TAG, "the size of new list:" + u_list.size());
        isUpdate=true;
        return user1.getUser_code();
    }

    @Override
    public long add_business(ContentValues business) {
        Business business1 = Tools.ContentValuesToBusiness(business);
        b_list.add(business1);
        isUpdate=true;
        isAddedBusiness=true;
        return business1.getId();
    }

    @Override
    public long add_activity_(ContentValues activity_)  {
        Activity_ activity_1 = Tools.ContentValuesToActivity_(activity_);
        a_list.add(activity_1);
        isUpdate=true;
        isAddedActivity_=true;
        return activity_1.getCode();
    }

    @Override
    public boolean is_added_activity_() {
        return isAddedActivity_;
    }

    @Override
    public boolean is_added_business()
    {
        return isAddedBusiness;
    }

    @Override
    public Cursor get_businesses() {
        return Tools.BusinessListToCursor(b_list);
    }

    @Override
    public Cursor get_Activities() {
        return Tools.Activity_ListToCursor(a_list);
    }

    @Override
    public Cursor get_users() {
        Log.d(TAG, "in the get users");
        Cursor c = Tools.UserListToCursor(u_list);
        Log.d(TAG, "get users returned:" + c);
        return c;
    }

    @Override
    public Cursor get_codes() {
        return null;
    }

    @Override
    public int updatecode(ContentValues contentValues) {
        return 0;
    }


}