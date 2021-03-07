package com.example.myschoolapp.ui.intranet;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.myschoolapp.R;

import java.util.List;

public class IntranetFunctionAdapter extends BaseAdapter {

    private List<IntranetFunction> functionList;

    @Override
    public int getCount() {
       return functionList.size();
    }

    @Override
    public Object getItem(int position) {
        return functionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder=null;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            convertView=View.inflate(parent.getContext(), R.layout.function_item,null);
            viewHolder.function_icon = convertView.findViewById(R.id.function_icon);
            viewHolder.function_title = convertView.findViewById(R.id.function_title);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }
//        viewHolder.function_icon.setImageResource(newsList.get(position).getImageId());
//        Glide.with(parent.getContext()).load(functionList.get(position).getIcon()).into(viewHolder.function_icon);
        viewHolder.function_title.setText(functionList.get(position).getTitle());
        viewHolder.function_icon.setImageResource(functionList.get(position).getIcon());
        return convertView;
    }

    static class  ViewHolder  {
        ImageView function_icon;
        TextView function_title;
    }

    // 构造函数，用于传入数据源
    public IntranetFunctionAdapter(List<IntranetFunction> newsList) {
        this.functionList = newsList;
    }

}

