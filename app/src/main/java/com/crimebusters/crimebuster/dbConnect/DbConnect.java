package com.crimebusters.crimebuster.dbConnect;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.crimebusters.crimebuster.UserBean;


/**
 * Created by awais on 6/6/2017.
 */

public class DbConnect extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "crimebuster";

    private static final int DATABASE_VERSION = 3;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "Create table users(userId INTEGER PRIMARY KEY  AUTOINCREMENT,userFullname Varchar Not Null,userCnic int Not Null,userCity Varchar,userDob Varchar,userEmail Varchar Not Null,userPass Varchar Not null,userStatus int);";

    public DbConnect(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){
        Log.w(DbConnect.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS MyEmployees");
        onCreate(database);
    }
}
