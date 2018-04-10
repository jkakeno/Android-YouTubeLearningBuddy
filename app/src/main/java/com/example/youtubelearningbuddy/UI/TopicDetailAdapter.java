package com.example.youtubelearningbuddy.UI;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youtubelearningbuddy.InteractionListener;
import com.example.youtubelearningbuddy.Model.Topic;
import com.example.youtubelearningbuddy.Model.Video;
import com.example.youtubelearningbuddy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class TopicDetailAdapter extends RecyclerView.Adapter<TopicDetailAdapter.ViewHolder> {

    private final InteractionListener listener;
    ArrayList<Video> videos;
    Topic topic;

    public TopicDetailAdapter(ArrayList<Video> videos, Topic topic, InteractionListener listener) {
        this.videos = videos;
        this.listener = listener;
        this.topic = topic;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if(!videos.isEmpty()) {
            /*Had to create a separate method in holder class so that view thumbnail can be accessed from this class. */
            holder.bindView(videos.get(position));
            /*Put a video in video holder to pass the video to the main activity using the listener.*/
            holder.video = videos.get(position);
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    /* Notify the active callbacks interface (the activity, if the fragment is attached to one) that an item has been selected.*/
                    listener.onTopicDetailInteraction(holder.video, topic);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (videos.size() > 0) {
            return videos.size();
        } else {
            return 1;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        /*Create variables that can be used in the adapter class.*/
        public final View view;
        public Video video;
        /*Create the view variables.*/
        public ImageView thumbnail;
        public TextView title;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            thumbnail = (ImageView) view.findViewById(R.id.thumbnails);
            title = (TextView) view.findViewById(R.id.title);
        }

        /*Helper method to put videoList thumbnail url to image view using Picasso.*/
        public void bindView(Video video) {
            if (!video.getTitle().isEmpty()) {
                title.setText(video.getTitle());

                Picasso.with(thumbnail.getContext())
                        .load(video.getThumbnailUrl())
                        .fit()
                        .centerCrop()
                        .into(thumbnail);
            }
        }
    }
}
