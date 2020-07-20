package com.example.myinstagram;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    Context context;
/*    private final String [] values;
    private final int [] images;*/
    private List<FeedsHelperClass> feeds;

    public ListAdapter(Context context, List<FeedsHelperClass> feeds){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.feeds  = feeds;
    }

    @Override
    public int getCount() {
        return feeds.size();
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

        FeedsHelperClass feed = feeds.get(position);

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.single_list_item, parent, false);
            viewHolder.author = (TextView)  convertView.findViewById(R.id.author);
            viewHolder.comment = (TextView) convertView.findViewById(R.id.comment);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.photo);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        String str = "<b>"+feed.getUname()+"</b>";
        viewHolder.author.setText(feed.getUname());
        viewHolder.comment.setText(Html.fromHtml(str)+": "+feed.getComment());
        Picasso.get().load(feed.getImageUrl()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(viewHolder.image);


        return result;
    }

    private static class ViewHolder {


        TextView author;
        TextView comment;
        ImageView image;

    }

}
