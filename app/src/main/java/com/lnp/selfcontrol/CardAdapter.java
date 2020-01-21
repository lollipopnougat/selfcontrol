package com.lnp.selfcontrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CardAdapter extends ArrayAdapter<Record> {
    private int layoutViewId;
    private MyDataControl myDataControl;
    private Context context;
    private List<Record> recList;


    public CardAdapter(Context context, int layoutId, List<Record> obj) {
        super(context, layoutId, obj);
        recList = obj;
        myDataControl = MyDataControl.getAvailable(context);
        this.context = context;
        layoutViewId = layoutId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Record record = getItem(position);//获取当前项的Weather实例
        //LayoutInflater的inflate()方法接收3个参数：需要实例化布局资源的id、ViewGroup类型视图组对象、false
        //false表示只让父布局中声明的layout属性生效，但不会为这个view添加父布局
        @SuppressLint("ViewHolder")
        View view;
        //View view = LayoutInflater.from(getContext()).inflate(textViewId, parent, false);
        //获取实例

        CardAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(layoutViewId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.titleText = view.findViewById(R.id.title_name);
            viewHolder.currVal = view.findViewById(R.id.value_text);
            viewHolder.upBtn = view.findViewById(R.id.up_btn);
            viewHolder.downBtn = view.findViewById(R.id.down_btn);
            viewHolder.delBtn = view.findViewById(R.id.del_btn);
            viewHolder.targetText = view.findViewById(R.id.target_text);
            view.setTag(viewHolder);//保存
        } else {
            view = convertView;
            viewHolder = (CardAdapter.ViewHolder) view.getTag();//取出
        }
        // = view.findViewById(R.id.time);
        //TextView currVal = view.findViewById(R.id.curr_value);
        //TextView status = view.findViewById(R.id.status);

        if (record.getTitle() != null) {
            viewHolder.titleText.setText(record.getTitle());
        } else {
            viewHolder.titleText.setText(view.getResources().getString(R.string.untitled));
        }
        if(record.getTarget()==record.getValue()) viewHolder.currVal.setText(R.string.fine);
        else viewHolder.currVal.setText(view.getResources().getString(R.string.desc_main, record.getValue()));
        viewHolder.targetText.setText(view.getResources().getString(R.string.desc_target, record.getTarget()));
        viewHolder.upBtn.setOnClickListener(v -> {
            int val = record.getValue();
            val++;
            if (val > record.getTarget()) {
                Toast.makeText(context, R.string.fined, Toast.LENGTH_SHORT).show();
                return;
            }

            record.setValue(val);
            myDataControl.updateRecord(record);
            viewHolder.currVal.setText(view.getResources().getString(R.string.desc_main, val));
            if (val == record.getTarget()) {
                Toast.makeText(context, R.string.fine, Toast.LENGTH_SHORT).show();
                viewHolder.currVal.setText(R.string.fine);
            }
            else Toast.makeText(context, R.string.up, Toast.LENGTH_SHORT).show();
        });

        viewHolder.downBtn.setOnClickListener(v -> {
            int val = record.getValue();
            if (val == record.getTarget()) {
                Toast.makeText(context, R.string.fined, Toast.LENGTH_SHORT).show();
                return;
            }
            if (val > 0) {
                val--;
                record.setValue(val);
                myDataControl.updateRecord(record);
                viewHolder.currVal.setText(view.getResources().getString(R.string.desc_main, val));
                Toast.makeText(context, view.getResources().getString(R.string.harry_up), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, view.getResources().getString(R.string.zero_scores), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.delBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert);
            builder.setTitle(view.getResources().getString(R.string.dialog_title));
            builder.setMessage(view.getResources().getString(R.string.dialog_del));
            builder.setPositiveButton(view.getResources().getString(R.string.dialog_ok), (dialogInterface, i) -> {
                myDataControl.deleteRecord(record.getId());
                recList.remove(record);
                CardAdapter.this.notifyDataSetChanged();
            });
            builder.setNegativeButton(view.getResources().getString(R.string.dialog_cancel), (dialogInterface, i) -> {
                Toast.makeText(context, view.getResources().getString(R.string.canceled), Toast.LENGTH_SHORT).show();
            });
            builder.show();
        });

        return view;
    }


    private class ViewHolder {
        TextView titleText;
        TextView currVal;
        TextView targetText;
        ImageButton upBtn;
        ImageButton downBtn;
        ImageButton delBtn;
    }

}
