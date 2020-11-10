package il.ac.jct.tazadok.agency.Model.backend;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

import il.ac.jct.tazadok.agency.Model.entities.Activity_;
import il.ac.jct.tazadok.agency.Model.entities.Business;
import il.ac.jct.tazadok.agency.Model.entities.User;

/**
 * Created by Talush122 on 11/12/2016.
 */

public interface DB_Manager {

//    long addActivity(ContentValues values);
//    boolean deleteActivity(int codeA);
//    boolean updateActivity(int codeA, ContentValues values);
//
//    long addBusiness(ContentValues values);
//    boolean deleteBusiness(int codeB);
//    boolean updateBusiness(int codeB, ContentValues values);
//
//    long addUser(ContentValues values);
//    boolean deleteUser(String userName);
//    boolean updateUser(int code, ContentValues values);
//
//    List<Business> getAllBusinesses ();
//    List<Activity_> getAllActivities ();
//    List<User> getAllUsers ();
//
//    //do the 3 lines above ^^^ like this:
////    Cursor getAllBusinesses ();
////    Cursor getAllActivities ();
////    Cursor getAllUsers ();
//
//    boolean findUser(ContentValues contentValues);
//    boolean findActivity(ContentValues contentValues);
//boolean removeActivity_(long active_id);
//    boolean removeBusiness(long business_id);
//    boolean removeUser(long user_num);

    long add_user(ContentValues user) throws Exception;
    long add_business(ContentValues business) throws Exception;
    long add_activity_(ContentValues active) throws Exception;
    boolean is_added_activity_();
    boolean is_added_business();
    Cursor get_businesses();
    Cursor get_Activities();
    Cursor get_users();
    Cursor get_codes();
    int updatecode (ContentValues contentValues);
//    boolean updateUsers(long user_num, ContentValues values);
//    boolean updateActivities(long active_id, ContentValues values);
//    boolean updateBusinesses(long business_id, ContentValues values);
  //  boolean isUpdate();
}
