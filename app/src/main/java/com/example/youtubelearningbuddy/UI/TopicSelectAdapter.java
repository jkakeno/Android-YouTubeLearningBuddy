package com.example.youtubelearningbuddy.UI;

import android.content.Context;
import android.support.v4.content.ContextCompat;
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
    Context context;
    public static int clickedHolderPosition = -1;
    private static itemClickListener itemClickListener;

    /*This private interface is only used to communicate the parent fragment that a holder has been clicked.*/
    interface itemClickListener {
        void onItemClickListener(boolean isClicked);
    }

    public TopicSelectAdapter(Context context,List<Topic> topics, InteractionListener listener) {
        this.topics = topics;
        this.listener = listener;
        this.context = context;
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
                /*Store the position of the clicked holder.*/
                clickedHolderPosition = holder.getAdapterPosition();
                /*Notify the fragment that a holder has been clicked.*/
                itemClickListener.onItemClickListener(true);
                if (listener !=null) {
                     /*Notify the active callbacks interface (the activity, if the fragment is attached to one) that an item has been selected.*/
                    listener.onTopicSelectFragmentInteraction(topic);
                }
            }
        });

        /*Change the background color of the clicked button in the recycler view.*/
        if(clickedHolderPosition == position) {
            holder.topicName.setBackgroundColor(ContextCompat.getColor(context, R.color.primary_material_light_2));
        }else{
            holder.topicName.setBackgroundColor(ContextCompat.getColor(context, R.color.primary_material_light_1));
        }
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }


    /*Helper method to update the adapter.*/
    public void updateAdapter() {
        notifyDataSetChanged();
    }

    /*Helper method to initialize the itemClickListener interface.*/
    public void setOnItemClickListener(itemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public Button topicName;
        TextView videoCount;

        public ViewHolder(View view) {
            super(view);
            topicName = (Button) view.findViewById(R.id.topicName);
            videoCount = (TextView) view.findViewById(R.id.videoCount);
        }
    }
}
