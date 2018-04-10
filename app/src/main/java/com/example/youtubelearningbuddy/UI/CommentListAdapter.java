package com.example.youtubelearningbuddy.UI;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youtubelearningbuddy.Model.commentresponse.Item;
import com.example.youtubelearningbuddy.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    private List<Item> items;

    public CommentListAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(!items.isEmpty()) {
            holder.comment.setText(items.get(position).getSnippet().getTopLevelComment().getComment().getTextDisplay());
            holder.bindThumbnail(items.get(position).getSnippet().getTopLevelComment().getComment().getAuthorProfileImageUrl());
        }
    }

    @Override
    public int getItemCount() {
        if(items.size()>0){
            return items.size();
        }else{
            return 1;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView comment;
        public ImageView thumbnail;

        public ViewHolder(View view) {
            super(view);
            comment = (TextView) view.findViewById(R.id.comment);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnails);

        }

        public void bindThumbnail(String url) {
            Picasso.with(thumbnail.getContext())
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(thumbnail);
        }
    }
}
