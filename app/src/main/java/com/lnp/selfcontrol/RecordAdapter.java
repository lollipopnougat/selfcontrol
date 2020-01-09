package com.lnp.selfcontrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

public class RecordAdapter extends ArrayAdapter<Record> {
    public static String str = "yyy-MM-dd HH:mm:ss";
    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat sdf = new SimpleDateFormat(str);

    private int textViewId;


    public RecordAdapter(Context context, int textViewResourceId, List<Record> obj){
        super(context, textViewResourceId, obj);
        textViewId = textViewResourceId;
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

        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(textViewId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.timeText = view.findViewById(R.id.time);
            viewHolder.currVal = view.findViewById(R.id.curr_value);
            viewHolder.status = view.findViewById(R.id.status);
            view.setTag(viewHolder);//保存
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();//取出
        }
        // = view.findViewById(R.id.time);
        //TextView currVal = view.findViewById(R.id.curr_value);
        //TextView status = view.findViewById(R.id.status);


        viewHolder.timeText.setText(view.getResources().getString(R.string.time,sdf.format(record.getDate())));
        viewHolder.currVal.setText(view.getResources().getString(R.string.curr_value,record.getValue()));
        int statusTmp = record.getStatus();
        switch (statusTmp) {
            case 1:viewHolder.status.setText(R.string.status_add);break;
            case -1:viewHolder.status.setText(R.string.status_sub);break;
            case 0:viewHolder.status.setText(R.string.status_zero);break;
            default: viewHolder.status.setText(R.string.unknownError);break;
        }
        return view;
    }

    private class ViewHolder {
        TextView timeText;
        TextView currVal;
        TextView status;
    }


}
