package com.example.youtubelearningbuddy.UI;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.youtubelearningbuddy.InteractionListener;
import com.example.youtubelearningbuddy.Model.Topic;
import com.example.youtubelearningbuddy.R;

import java.util.List;

public class TopicSelectAdapter extends RecyclerView.Adapter<TopicSelectAdapter.ViewHolder> {

    private List<Topic> topics;
    private final InteractionListener listener;

    public TopicSelectAdapter(List<Topic> topics, InteractionListener listener) {
        this.topics = topics;
        this.listener = listener;
    }

    @Override
    public TopicSelectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topic_fragment, parent, false);
        return new TopicSelectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TopicSelectAdapter.ViewHolder holder, int position) {
        final Topic topic = topics.get(position);
        holder.topicName.setText(topic.getTopic());
        holder.videoCount.setText(String.valueOf(topics.get(position).getVideoList().size()));
        holder.topicName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    /* Notify the active callbacks interface (the activity, if the fragment is attached to one) that an item has been selected.*/
                    listener.onTopicSelectFragmentInteraction(topic);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
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
