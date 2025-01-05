package com.example.medicine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseConn extends SQLiteOpenHelper
{

    public DataBaseConn(Context context) {super(context, "Medicinedb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table MDTable(MedicineName text, date text, time text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    public boolean insertvalues(String medname, String meddate, String medtime)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MedicineName",medname);
        contentValues.put("date",meddate);
        contentValues.put("time",medtime);
        long res = database.insert("MDTable",null,contentValues);
        if(res==-1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    public Cursor fetchdata(String date, String time)
    {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.rawQuery("Select * from MDTable where date='"+date+"' AND time='"+time+"'",null);
        return c;
    }
}