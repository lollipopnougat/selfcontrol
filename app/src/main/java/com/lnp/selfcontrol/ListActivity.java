package com.lnp.selfcontrol;

import android.content.Context;
import android.content.Intent;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    private RecordAdapter recordAdapter;
    private MyDataControl myDataControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        myDataControl = MyDataControl.getAvailable(getApplicationContext());
        listView = findViewById(R.id.list_view);
        recordAdapter = new RecordAdapter(getApplicationContext(),R.layout.layout_item,myDataControl.getRecords());
        listView.setAdapter(recordAdapter);
    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, ListActivity.class);
    }
}
