package com.lnp.selfcontrol;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

public class DataCursorWrapper extends CursorWrapper {
    public DataCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Record getRecord() {
        String uuidString = getString(getColumnIndex(MyDBHelper.DataTable.Cols.UUID));
        String title = getString(getColumnIndex(MyDBHelper.DataTable.Cols.TITLE));
        long date = getLong(getColumnIndex(MyDBHelper.DataTable.Cols.DATE));
        int value = getInt(getColumnIndex(MyDBHelper.DataTable.Cols.VALUE));
        int status = getInt(getColumnIndex(MyDBHelper.DataTable.Cols.STATUS));
        int target = getInt(getColumnIndex(MyDBHelper.DataTable.Cols.TARGET));
        Record record = new Record(UUID.fromString(uuidString));
        record.setTitle(title);
        record.setDate(new Date(date));
        record.setValue(value);
        record.setStatus(status);
        record.setTarget(target);
        return record;
    }
}
