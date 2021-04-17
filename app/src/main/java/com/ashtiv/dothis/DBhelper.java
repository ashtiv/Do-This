 package com.ashtiv.dothis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

 public class DBhelper extends SQLiteOpenHelper {
    public DBhelper( Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
         DB.execSQL("create Table Userdetails(name TEXT primary key)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists Userdetails");
    }
    public Boolean insertdata(String name){
        SQLiteDatabase DB =this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        Cursor cursor= DB.rawQuery("SELECT * FROM Userdetails WHERE name=?",new String[]{name});
        if(cursor.getCount()>0){
            return false;
        }
        long result=DB.insert("Userdetails",null,contentValues);
        if(result==-1){
            return  false;
        }
        else {
            return  true;
        }
    }
    public Boolean updatedata(String name){
        SQLiteDatabase DB =this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        Cursor cursor= DB.rawQuery("Select * from Userdetails where name = ?", new String[]{name});
        if(cursor.getCount()>0) {
            long result=DB.update("Userdetails",contentValues,"name=?",new String[] {name});
            if(result==-1){
                return  false;
            }
            else {
                return  true;
            }
        }
        else{
            return false;
        }
    }
    public long deldata(String ns){
        SQLiteDatabase DB =this.getWritableDatabase();
//        String com="'";
        String mane=String.valueOf("'"+ns+"'");
        mane.replace("\n","");
        String query="SELECT * FROM Userdetails WHERE name="+ mane;
//        Log.d("mytag",query);
//        String query="SELECT * FROM Userdetails WHERE name=?";
//        ns.replace("\n","");
        Log.d("mytag",query);
        Cursor cursor= DB.rawQuery(query,null);

        if(cursor.getCount()>0) {
            long result=DB.delete("Userdetails","name=?",new String[]{ns});
            return cursor.getCount();
        }
        else{
            return 0;
        }
    }
    public Cursor getdata(){
        SQLiteDatabase DB =this.getWritableDatabase();
        Cursor cursor= DB.rawQuery("Select * from Userdetails",null);
        return cursor;
    }
}
