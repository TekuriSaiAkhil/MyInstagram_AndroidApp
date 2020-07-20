package com.example.myinstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatlistAdapter extends BaseAdapter {
    Context context;
    List<String> name;
    List<String> dps;

    public ChatlistAdapter() {

    }

    public ChatlistAdapter(Context context, List<String> name,List<String> dps) {
        this.context = context;
        this.name = name;
        this.dps = dps;
    }

    @Override
    public int getCount() {
        return name.size();
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
        ViewHolder viewHolder;
        final View result;
        String s1 = name.get(position);
        String s2 = dps.get(position);


        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.friends_list, parent, false);
            viewHolder.friend_name =(TextView) convertView.findViewById(R.id.friend_user_name);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.friend_dp);
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.friend_name .setText(s1);
        Picasso.get().load(s2).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(viewHolder.imageView);
        return result;
    }


    public static class ViewHolder{
        TextView friend_name;
        ImageView imageView;
    }
}