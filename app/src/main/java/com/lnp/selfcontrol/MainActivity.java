package com.lnp.selfcontrol;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.melnykov.fab.FloatingActionButton;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*
        private ImageButton okBtn;
        private ImageButton notBtn;
        private TextView textView;*/
    private MyDataControl mydb;
    private List<Record> myList;
    private ListView lView;
    private CardAdapter cardAdapter;
    private FloatingActionButton floatingActionButton;
    /*
    private ImageButton hisBtn;
    private ImageButton delBtn;*/
    private Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout_neo);
        lView = findViewById(R.id.card_list);
        floatingActionButton = findViewById(R.id.fab);
        mydb = MyDataControl.getAvailable(getApplicationContext());
        myList = mydb.getRecords();
        if (myList.size() == 0) {
            record = new Record();
            record.setTitle("自律打卡");
            mydb.addRecord(record);
            myList = mydb.getRecords();
        } else {
            record = myList.get(myList.size() - 1);
        }
        cardAdapter = new CardAdapter(MainActivity.this, R.layout.card_view, myList);
        lView.setAdapter(cardAdapter);
        floatingActionButton.attachToListView(lView);
        floatingActionButton.setOnClickListener(v -> {
            AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);

            //alterDiaglog.setView(R.layout.input_dialog);//加载进去
            LayoutInflater inflater = getLayoutInflater();
            View vie = inflater.inflate(R.layout.input_dialog, null);
            EditText edt1 = vie.findViewById(R.id.edit_desc);
            EditText edt2 = vie.findViewById(R.id.edit_target);

            alterDiaglog.setView(vie);
            alterDiaglog.setPositiveButton(R.string.submit,null);
            alterDiaglog.setNegativeButton(R.string.cancel,null);
            AlertDialog dialog = alterDiaglog.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener( (vi) -> {
                Record rec = new Record();
                rec.setDate(new Date());
                if(edt1.getText().toString().equals("") || edt2.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),R.string.dialog_blank,Toast.LENGTH_SHORT).show();
                    return;
                }
                String title = edt1.getText().toString();
                int target = Integer.parseInt(edt2.getText().toString());
                rec.setTarget(target);
                rec.setTitle(title);
                myList.add(rec);
                mydb.addRecord(rec);
                cardAdapter.notifyDataSetChanged();
                dialog.dismiss();
            });
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener((vi) -> {
                Toast.makeText(getApplicationContext(), R.string.canceled, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });

        });
        /*okBtn = findViewById(R.id.ok_btn);
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
            Toast.makeText(getApplicationContext(), getString(R.string.canceled), Toast.LENGTH_SHORT).show();
        });

        builder.show();*/
    }

}
