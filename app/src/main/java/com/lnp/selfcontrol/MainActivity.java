package com.lnp.selfcontrol;

import android.content.DialogInterface;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    private ImageButton okBtn;
    private ImageButton notBtn;
    private TextView textView;
    private MyDataControl mydb;
    private List<Record> myList;
    private ImageButton hisBtn;
    private ImageButton delBtn;
    private Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        okBtn = findViewById(R.id.ok_btn);
        notBtn = findViewById(R.id.not_good);
        textView = findViewById(R.id.textView);
        hisBtn = findViewById(R.id.history_btn);
        delBtn = findViewById(R.id.del_btn);

        mydb = MyDataControl.getAvailable(this);
        myList = mydb.getRecords();
        if (myList.size() == 0) {
            record = new Record();
            record.setTitle("自律");
            mydb.addRecord(record);
            textView.setText(getString(R.string.desc_main, 0));
        } else {
            record = myList.get(myList.size() - 1);
            textView.setText(getString(R.string.desc_main, record.getValue()));
        }

        okBtn.setOnClickListener(v -> {
            int val = record.getValue();
            val++;
            record.setValue(val);
            record.setId(UUID.randomUUID());
            record.setDate(new Date());
            record.setStatus(1);
            mydb.addRecord(record);
            textView.setText(getString(R.string.desc_main, val));
            Toast.makeText(getApplicationContext(), getString(R.string.up), Toast.LENGTH_SHORT).show();
        });

        notBtn.setOnClickListener(v -> {
            int val = record.getValue();
            if (val > 0) {
                val--;
                record.setValue(val);
                record.setId(UUID.randomUUID());
                record.setDate(new Date());
                record.setStatus(-1);
                mydb.addRecord(record);
                textView.setText(getString(R.string.desc_main, val));
                Toast.makeText(getApplicationContext(), getString(R.string.harry_up), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.zero_scores), Toast.LENGTH_SHORT).show();
            }

        });

        hisBtn.setOnClickListener(v -> {
            Intent intent = ListActivity.newIntent(getApplicationContext());
            startActivity(intent);
        });

        delBtn.setOnClickListener(v -> {
            showCoverDialog();
        });
    }


    private void showCoverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setTitle(getString(R.string.dialog_title));
        builder.setMessage(getString(R.string.dialog_desc));

        builder.setPositiveButton(getString(R.string.dialog_ok), (dialogInterface, i) -> {
            mydb.deleteAllRecord();
            myList = mydb.getRecords();
            record = new Record();
            record.setTitle("自律");
            mydb.addRecord(record);
            textView.setText(getString(R.string.desc_main, 0));
        });

        builder.setNegativeButton(getString(R.string.dialog_cancel), (dialogInterface, i) -> {
            Toast.makeText(getApplicationContext(), getString(R.string.cancled), Toast.LENGTH_SHORT).show();
        });

        builder.show();
    }

}
