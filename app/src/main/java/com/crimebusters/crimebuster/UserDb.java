package com.crimebusters.crimebuster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.crimebusters.crimebuster.dbConnect.DbConnect;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.password;
import static com.crimebusters.crimebuster.R.drawable.email;

/**
 * Created by awais on 6/6/2017.
 */

public class UserDb {

    private DbConnect dbHelper;


    public final static String User_table="users"; // name of table

    public final static String User_id="userId"; //
    public final static String User_fullname="userFullname";
    public final static String User_cnic="userCnic"; //
    public final static String User_city="userCity";  //
    public final static String User_Dob="userDob";
    public final static String User_email="userEmail"; //
    public final static String User_pass="userPass";
    public final static String User_status="userStatus";

    //


    /**
     *
     * @param context
     */
    public UserDb(Context context){

        dbHelper = new DbConnect(context);
    }


    public long AddUsers(UserBean userBean){
        SQLiteDatabase  sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(User_fullname, userBean.getUserFullname());
        values.put(User_cnic, userBean.getUserCnic());
        values.put(User_city, userBean.getUserCity());
        values.put(User_Dob, userBean.getUserDob());
        values.put(User_email, userBean.getUserEmail());
        values.put(User_pass, userBean.getUserPass());
        values.put(User_status, userBean.getUserStatus());

        long id = sqLiteDatabase.insert(User_table, null, values);

        Log.i("UserId"," Id "+id);
        return id ;

    }

   /* public Cursor selectRecords() {
        String[] cols = new String[] {EMP_ID, EMP_NAME};
        Cursor mCursor = database.query(true, EMP_TABLE,cols,null
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; // iterate to get each value.
    }
    */


    public UserBean getUserDetails(String email,String pass){
        SQLiteDatabase sqdb = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM users where userEmail = ? and userPass = ?";
        UserBean bean = null;


        Cursor cursor = sqdb.rawQuery(query,new String[]{email, pass});
        if (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int nic = cursor.getInt(2);
            String city = cursor.getString(3);
            String dob = cursor.getString(4);
            String useremail = cursor.getString(5);
            String userpass = cursor.getString(6);
            int userStatus = cursor.getInt(7);


            bean = new UserBean(id,name,nic,city,dob,useremail,userpass,userStatus);
        }
        sqdb.close();
        return bean;
    }//end getUser


    public void UpdateStatus(){
        SQLiteDatabase sqdb = dbHelper.getWritableDatabase();

        sqdb.execSQL( "update users set userStatus=1 where userStatus = 0");

    }
    public List<UserBean> getAllUserDetails(int userStatu){
        SQLiteDatabase sqdb = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM users where userStatus = ?";
        List<UserBean> list = new ArrayList<>();


        Cursor cursor = sqdb.rawQuery(query,new String[]{String.valueOf(userStatu)});
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int nic = cursor.getInt(2);
            String city = cursor.getString(3);
            String dob = cursor.getString(4);
            String useremail = cursor.getString(5);
            String userpass = cursor.getString(6);
            int userStatus = cursor.getInt(7);


            list.add( new UserBean(id,name,nic,city,dob,useremail,userpass,userStatus));
        }
        sqdb.close();
        return list;
    }//end getAllUsers




}