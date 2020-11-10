package il.ac.jct.tazadok.agency.Model.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;

import il.ac.jct.tazadok.agency.Model.backend.Contact;
import il.ac.jct.tazadok.agency.Model.entities.Activity_;
import il.ac.jct.tazadok.agency.Model.entities.Business;
import il.ac.jct.tazadok.agency.Model.entities.Code_;
import il.ac.jct.tazadok.agency.Model.entities.Description;
import il.ac.jct.tazadok.agency.Model.entities.User;
import il.ac.jct.tazadok.agency.R;
import android.database.MatrixCursor;
import android.location.Address;
import android.util.Log;
//import static il.ac.jct.tazadok.agency.Controller.Add.getContext;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Talush122 on 11/12/2016.
 */

public class Tools {

    static final String TAG = "Tools";
    public static ContentValues ActiveToContentValues(Activity_ active) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contact.Activity_.SHORT_DES, active.getShort_des());
        contentValues.put(Contact.Activity_.ACTIVITY_CODE, active.getCode());
        contentValues.put(Contact.Activity_.BUSINESS_ID, active.getBusiness_id());
        contentValues.put(Contact.Activity_.PRICE, active.getPrice());
        contentValues.put(Contact.Activity_.COUNTRY, active.getCountry());
        contentValues.put(Contact.Activity_.DESCRIPTION, String.valueOf(active.getDes()));//enum to string- to check ittt


        contentValues.put(Contact.Activity_.END, active.getEnd());
        contentValues.put(Contact.Activity_.START, active.getStart());
        return contentValues;
    }

    public static ContentValues BusinessToContentValues(Business business) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contact.Business.ID, business.getId());
        contentValues.put(Contact.Business.NAME, business.getName());
        contentValues.put(Contact.Business.PHONE, business.getPhone());
        contentValues.put(Contact.Business.ADDRESS, business.getAddress());
        contentValues.put(Contact.Business.EMAIL, business.getEmail());
        contentValues.put(Contact.Business.WEBSITE, String.valueOf(business.getWebSite()));//the same problem
        return contentValues;
    }
    public static ContentValues UserToContentValues(User user) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contact.User.PASSWORD, user.getPassword());
        contentValues.put(Contact.User.USER_NAME, user.getUser_name());
        contentValues.put(Contact.User.USER_CODE, user.getUser_code());
        return contentValues;
    }
    public static ContentValues CodeToContentValues(Code_ c) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contact.Code.CODEA, c.getAcode());
        contentValues.put(Contact.Code.CODEB, c.getBcode());
        contentValues.put(Contact.Code.CODEU, c.getUcode());
        return contentValues;
    }
    public static User ContentValuesToUser(ContentValues contentValues) {

        User user = new User();
        user.setPassword(contentValues.getAsString(Contact.User.PASSWORD));
        user.setUser_name(contentValues.getAsString(Contact.User.USER_NAME));
        user.setUser_code(contentValues.getAsInteger(Contact.User.USER_CODE));

        return user;
    }

    public static Business ContentValuesToBusiness(ContentValues values) {
        Business business = new Business();
        business.setAddress(values.getAsString(Contact.Business.ADDRESS));
        business.setEmail(values.getAsString(Contact.Business.EMAIL));
        business.setId(values.getAsInteger(Contact.Business.ID));
        business.setPhone(values.getAsString(Contact.Business.PHONE));
        business.setName(values.getAsString(Contact.Business.NAME));
        business.setWebSite(values.getAsString(Contact.Business.WEBSITE));
        return business;
    }

    public static Activity_ ContentValuesToActivity_(ContentValues values) {
        Activity_ active = new Activity_();
        active.setBusiness_id(values.getAsInteger(Contact.Activity_.BUSINESS_ID));
        active.setCode(values.getAsInteger(Contact.Activity_.ACTIVITY_CODE));
        active.setShort_des(values.getAsString(Contact.Activity_.DESCRIPTION));
        active.setPrice(values.getAsDouble(Contact.Activity_.PRICE));
        active.setCountry(values.getAsString(Contact.Activity_.COUNTRY));
        active.setDes(Description.valueOf(values.getAsString(Contact.Activity_.DESCRIPTION)));
        active.setEnd(values.getAsString(Contact.Activity_.END));
        active.setStart(values.getAsString(Contact.Activity_.START));
        return active;
    }
    public static Cursor Activity_ListToCursor(ArrayList<Activity_> a_list) {
        String[] columns = new String[]
                {
                        Contact.Activity_.BUSINESS_ID,
                        Contact.Activity_.ACTIVITY_CODE,
                        Contact.Activity_.DESCRIPTION,
                        Contact.Activity_.PRICE,
                        Contact.Activity_.COUNTRY,
                        Contact.Activity_.DESCRIPTION,
                        Contact.Activity_.END,
                        Contact.Activity_.START
                };

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Activity_ a : a_list) {
            matrixCursor.addRow(new Object[]{ a.getBusiness_id(),a.getCode(),a.getDes(),a.getPrice(),a.getCountry(),a.getShort_des(),a.getEnd(),a.getStart()});
        }
        return matrixCursor;
    }

    public static Cursor BusinessListToCursor(ArrayList<Business> b_list) {
        String[] columns = new String[]
                {
                        Contact.Business.ID,
                        Contact.Business.ADDRESS,
                        Contact.Business.EMAIL,
                        Contact.Business.NAME,
                        Contact.Business.PHONE,
                        Contact.Business.WEBSITE
                };

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Business b : b_list) {
            matrixCursor.addRow(new Object[]{b.getId(),b.getAddress(), b.getEmail(),b.getName(),b.getPhone(),b.getWebSite()});
        }

        return matrixCursor;
    }

    public static Cursor UserListToCursor(ArrayList<User> u_list) {
//        Log.d(TAG, "in the list to cursor");
//        String[] columns = new String[]
//                {
//                        Contact.User.USER_NAME,
//                        Contact.User.PASSWORD,
//                        Contact.User.USER_CODE
//                };
//        Log.d(TAG, "cols:" + columns);
//
//        MatrixCursor matrixCursor = new MatrixCursor(columns);
//        Log.d(TAG, "matCur:" + matrixCursor);
//        Log.d(TAG, "u list size:" + u_list.size());
//        Log.d(TAG, "u list:" + u_list);
//        for (User u : u_list) {
//            Log.d(TAG, "u :" + u);
//            matrixCursor.addRow(new Object[]{u.getUser_name(),u.getPassword(),u.getUser_code()});
//            Log.d(TAG, "matCur w u ^ :" + matrixCursor);
//        }
//        Log.d(TAG, "matCur final:" + matrixCursor);
//        return matrixCursor;
//    }
        String[] columns = new String[]
                {
                        Contact.User.USER_CODE,
                        Contact.User.USER_NAME,
                        Contact.User.PASSWORD,
                };

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (User u : u_list) {
            matrixCursor.addRow(new Object[]{u.getUser_code(), u.getUser_name(), u.getPassword()});
        }
        return matrixCursor;
    }

    public static List<Activity_> CursorToActivityList(Cursor c)
    {
        List<Activity_> activeList= new ArrayList<Activity_>();
        while(c.moveToNext()) {
            Activity_ active = new Activity_();



            active.setDes(Enum.valueOf(Description.class,c.getString(c.getColumnIndex("Description"))));
            active.setCountry(c.getString(c.getColumnIndex("Country")));
            active.setStart((c.getString(c.getColumnIndex("Start"))));
            active.setEnd(c.getString(c.getColumnIndex("End")));
            active.setPrice(c.getDouble(c.getColumnIndex("Price")));
            active.setBusiness_id(c.getInt(c.getColumnIndex("BusinessId")));
            active.setCode(c.getInt(c.getColumnIndex("Code")));
            active.setShort_des(c.getString(c.getColumnIndex("ShortDescription")));
            activeList.add(active);
        }
        c.close();
        return activeList;
    }


    public static List<User> CursorToUsersList(Cursor c) {
        List<User> usersList = new ArrayList<User>();
        while (c.moveToNext()) {
            User user1 = new User();
            user1.setUser_code(c.getInt(c.getColumnIndex("Code")));
            user1.setUser_name(c.getString(c.getColumnIndex("UserName")));
            user1.setPassword(c.getString(c.getColumnIndex("Password")));
            usersList.add(user1);
        }
        c.close();
        return usersList;
    }

    public static List<Business> CursorToBusinessList(Cursor c) {
        List<Business> businessList = new ArrayList<Business>();
        while (c.moveToNext()) {
            Business business1 = new Business();
            business1.setId(c.getInt(c.getColumnIndex("Id")));
            business1.setName(c.getString(c.getColumnIndex("Name")));
            business1.setAddress(c.getString(c.getColumnIndex("Address")));
            business1.setPhone(c.getString(c.getColumnIndex("Phone")));
            business1.setEmail(c.getString(c.getColumnIndex("Email")));
            business1.setWebSite(c.getString(c.getColumnIndex("Website")));
            businessList.add(business1);
        }
        c.close();
        return businessList;
    }


}
