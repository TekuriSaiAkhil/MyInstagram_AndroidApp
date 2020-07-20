package com.example.myinstagram;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(username text primary key,phonenumber text,password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists user");
    }

    public boolean insert_user_data(String uname,String phnum,String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues CV = new ContentValues();
        CV.put("username",uname);
        CV.put("phonenumber",phnum);
        CV.put("password",pass);
        long ins = db.insert("user",null,CV);
        if (ins==-1){
            return false;
        }else{
            return true;
        }
    }

    public boolean check_username(String uname){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=?", new String[] {uname});
        if(cursor.getCount()>0){
            return false;
        }else {
            return true;
        }
    }

    public boolean check_credentials(String uname,String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=? and password=?",new String[] {uname,pass});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }


}
