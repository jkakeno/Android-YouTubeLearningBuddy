package com.example.youtubelearningbuddy.UI;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.youtubelearningbuddy.InteractionListener;
import com.example.youtubelearningbuddy.Model.Topic;
import com.example.youtubelearningbuddy.Model.TopicList;
import com.example.youtubelearningbuddy.R;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.ViewHolder> implements Consumer<TopicList> {

    TopicList topicList;
    private final InteractionListener listener;

    public TopicListAdapter(TopicList topics, InteractionListener listener) {
        this.topicList = topics;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topic_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Topic topic = topicList.getTopicList().get(position);
        holder.topicName.setText(topic.getTopic());
        holder.videoCount.setText(String.valueOf(topicList.getTopicList().get(position).getVideoList().size()));
        holder.topicName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    /* Notify the active callbacks interface (the activity, if the fragment is attached to one) that an item has been selected.*/
                    listener.onTopicListFragmentInteraction(topic);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return topicList.getTopicList().size();
    }

    /*Consumer<VideoList> implemented method. Here the subscriber can observe the data coming in.*/
    @Override
    public void accept(@NonNull TopicList topicList) throws Exception {
        this.topicList = topicList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public Button topicName;
        TextView videoCount;

        public ViewHolder(View view) {
            super(view);
            topicName = (Button) view.findViewById(R.id.topicName);
            videoCount = (TextView) view.findViewById(R.id.videoCount);
        }
    }
}
