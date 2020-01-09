package com.lnp.selfcontrol;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "MyDBHelper";
    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "MyData.db";


    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DataTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                DataTable.Cols.UUID + ", " +
                DataTable.Cols.TITLE + ", " +
                DataTable.Cols.DATE + ", " +
                DataTable.Cols.VALUE + ", " +
                DataTable.Cols.STATUS +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table " + DataTable.NAME);
        sqLiteDatabase.execSQL("create table " + DataTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                DataTable.Cols.UUID + ", " +
                DataTable.Cols.TITLE + ", " +
                DataTable.Cols.DATE + ", " +
                DataTable.Cols.VALUE + ", " +
                DataTable.Cols.STATUS +
                ")"
        );
    }

    public static final class DataTable {
        public static final String NAME = "data";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String VALUE = "value";
            public static final String STATUS = "status";
        }
    }
}

