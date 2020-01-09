package com.lnp.selfcontrol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MyDataControl {
    private static MyDataControl dataControl;

    private Context context;
    private SQLiteDatabase database;

    private MyDataControl(Context con) {
        context = con.getApplicationContext();
        database = new MyDBHelper(context).getWritableDatabase();
        //addRecord(new Record());
    }

    public static MyDataControl getAvailable(Context context) {
        if (dataControl == null) {
            dataControl = new MyDataControl(context);
        }
        return dataControl;
    }

    public Record getRecord(UUID id) {
        DataCursorWrapper cursor = queryRecords(MyDBHelper.DataTable.Cols.UUID + " = ?", new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getRecord();
        }
        catch (Exception er) {
            return null;
        }finally {
            cursor.close();
        }
    }

    public void addRecord(Record c) {
        ContentValues values = getContentValues(c);
        database.insert(MyDBHelper.DataTable.NAME, null, values);
    }

    public List<Record> getRecords() {
        List<Record> records = new ArrayList<>();

        DataCursorWrapper cursor = queryRecords(null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            records.add(cursor.getRecord());
            cursor.moveToNext();
        }
        cursor.close();

        return records;
    }

    public void updateRecord(Record record) {
        String uuidString = record.getId().toString();
        ContentValues values = getContentValues(record);

        database.update(MyDBHelper.DataTable.NAME, values, MyDBHelper.DataTable.Cols.UUID + " = ?", new String[] { uuidString });
    }

    public void deleteRecord(UUID id) {
        database.delete(MyDBHelper.DataTable.NAME,MyDBHelper.DataTable.Cols.UUID + " = ?",new String[] { id.toString() });
    }

    public void deleteAllRecord() {
        database.delete(MyDBHelper.DataTable.NAME,null,null);
    }

    private static ContentValues getContentValues(Record record) {
        ContentValues values = new ContentValues();
        values.put(MyDBHelper.DataTable.Cols.UUID, record.getId().toString());
        values.put(MyDBHelper.DataTable.Cols.TITLE, record.getTitle());
        values.put(MyDBHelper.DataTable.Cols.DATE, record.getDate().getTime());
        values.put(MyDBHelper.DataTable.Cols.VALUE, record.getValue());
        values.put(MyDBHelper.DataTable.Cols.STATUS, record.getStatus());

        return values;
    }

    private DataCursorWrapper queryRecords(String whereClause, String[] whereArgs) {
        Cursor cursor = database.query(
                MyDBHelper.DataTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new DataCursorWrapper(cursor);
    }
}
