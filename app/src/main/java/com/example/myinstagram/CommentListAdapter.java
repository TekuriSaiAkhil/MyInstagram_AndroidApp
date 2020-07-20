package com.example.myinstagram;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentListAdapter extends BaseAdapter {

    Context context;

    List<String> names,comments;

    public CommentListAdapter(Context context,List<String> name, List<String> comment) {
        this.context = context;
        this.names = name;
        this.comments = comment;
    }

    @Override
    public int getCount() {
        return comments.size();
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
        String name = names.get(position);
        String comment = comments.get(position);


        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.single_comment_list_item, parent, false);
            viewHolder.name =(TextView) convertView.findViewById(R.id.name);
            viewHolder.comment2 =(TextView) convertView.findViewById(R.id.comment2);
            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.name.setText(name);
        viewHolder.comment2.setText(comment+"\n");

        return result;

    }
    private static class ViewHolder {
        TextView name;
        TextView comment2;

    }


}
