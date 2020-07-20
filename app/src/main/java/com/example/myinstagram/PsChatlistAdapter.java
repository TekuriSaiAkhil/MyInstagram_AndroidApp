package com.example.myinstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PsChatlistAdapter extends BaseAdapter {
    Context context;
    List<String> from,message;

    public PsChatlistAdapter(Context context, List<String> from, List<String> message) {
        this.context = context;
        this.from = from;
        this.message = message;
    }

    @Override
    public int getCount() {
        return from.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder;
        final View result;
        TextView t1 = null;
        String s1 = from.get(position);
        String s2 = message.get(position);


        if (convertView == null) {
            viewholder = new Viewholder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.chat_list, parent, false);
            viewholder.message =(TextView) convertView.findViewById(R.id.chat_user_name);
            result=convertView;
            convertView.setTag(viewholder);

        } else {
            viewholder = (Viewholder) convertView.getTag();
            result=convertView;
        }
        viewholder.message.setText(s1+": "+s2);

        return result;
    }


    public static class Viewholder{
        TextView message;
    }
}
