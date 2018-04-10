package com.example.youtubelearningbuddy.UI;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youtubelearningbuddy.InteractionListener;
import com.example.youtubelearningbuddy.Model.Video;
import com.example.youtubelearningbuddy.Model.VideoList;
import com.example.youtubelearningbuddy.R;
import com.squareup.picasso.Picasso;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


public class VideoSearchAdapter extends RecyclerView.Adapter<VideoSearchAdapter.ViewHolder> implements Consumer<VideoList>{

    VideoList videoList;
    private final InteractionListener listener;

    public VideoSearchAdapter(VideoList videoList, InteractionListener listener) {
        this.videoList = videoList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if(!videoList.getVideoList().isEmpty()) {
            /*Had to create a separate method in holder class so that view thumbnail can be accessed from this class. */
            holder.bindView(videoList.getVideoList().get(position));
            /*Put a video in video holder.*/
            holder.video = videoList.getVideoList().get(position);
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    /* Notify the active callbacks interface (the activity, if the fragment is attached to one) that an item has been selected.*/
                    listener.onVideoSearchFragmentInteraction(holder.video);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (videoList.getVideoList().size() > 0) {
            return videoList.getVideoList().size();
        } else {
            return 1;
        }
    }

    /*Consumer<VideoList> implemented method. Here the subscriber can observe the data coming in.*/
    @Override
    public void accept(@NonNull VideoList videoList) throws Exception {
        this.videoList = videoList;
        notifyDataSetChanged();
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

                Log.d("", video.getTitle());
                Log.d("", video.getThumbnailUrl());
            }
        }
    }
}
