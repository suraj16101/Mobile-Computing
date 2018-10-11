package com.quiz.quiz_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="QuizDatabase.db";
    public static final String TABLE_NAME="QUIZ_TABLE";
    public static final String COL_1="ID";
    public static final String COL_2="QUESTION";
    public static final String COL_3="USER_ANSWER";
    public static final String COL_4="CORRECT_ANSWER";



    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = " Create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT, "+ COL_3 + " TEXT, " + COL_4 + " TEXT ); ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addvalue(String s1,String s2)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("QUESTION",s1);
        contentValues.put("CORRECT_ANSWER",s2);
        long result =db.insert(TABLE_NAME,null,contentValues);
        if(result==-1){
            return  true;
        }
        else{
            return false;
        }
    }

    public void updateValue(int id, String s1)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("USER_ANSWER",s1);
        db.update(TABLE_NAME,contentValues,"ID=?",new String[]{Integer.toString(id)});

    }

    public int _getRows()
    {
        int value=0;
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME, new String[]{"QUESTION"}, null,null,null,null, null );
        if (cursor!=null)
        {
            cursor.moveToFirst();
            value=cursor.getCount();
        }
        return value;
    }

    public String selectValue(int id)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        String val="";
        Cursor cursor=db.query(TABLE_NAME, new String[]{"QUESTION"}, "ID=?", new String[]{Integer.toString(id)},null,null, null );
        if (cursor!=null)
        {
            cursor.moveToFirst();
            val = cursor.getString(0);

        }
        return val;

    }

    public String _getUser(int id)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        String val="";
        Cursor cursor=db.query(TABLE_NAME, new String[]{"USER_ANSWER"}, "ID=?", new String[]{Integer.toString(id)},null,null, null );
        if (cursor!=null)
        {
            cursor.moveToFirst();
            val = cursor.getString(0);
        }
        return val;
    }

    public Cursor Select_Row()
    {
        int value=0;
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME, new String[]{"*"}, null,null,null,null, null );
        return cursor;
    }

















}
