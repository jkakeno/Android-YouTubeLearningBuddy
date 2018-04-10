package com.example.youtubelearningbuddy.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.youtubelearningbuddy.InteractionListener;
import com.example.youtubelearningbuddy.Model.Topic;
import com.example.youtubelearningbuddy.Model.Video;
import com.example.youtubelearningbuddy.R;

import java.util.ArrayList;


public class TopicDetailFragment extends Fragment {

    private static final String TAG =TopicDetailFragment.class.getSimpleName();
    private static final String ARG1 = "arg1";
    private static final String ARG2 = "arg2";
    private InteractionListener listener;
    ArrayList<Video> videoList;
    RecyclerView recyclerView;
    TopicDetailAdapter adapter;
    LinearLayoutManager layoutManager;
    Topic topic;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TopicDetailFragment() {
    }


    public static TopicDetailFragment newInstance(ArrayList<Video> videos, Topic topic) {
        /*Create an instance of this fragment.*/
        TopicDetailFragment fragment = new TopicDetailFragment();
        /*Store the received data in this fragment tag.*/
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARG1, videos);
        bundle.putParcelable(ARG2, topic);
        fragment.setArguments(bundle);
        /*Return the fragment.*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");

        /*Create a new video list of videos.*/
        videoList = new ArrayList<>();

        /*Get the topic selected and video list stored in bundle.*/
        if (getArguments() != null) {
            videoList = getArguments().getParcelableArrayList(ARG1);
            topic = getArguments().getParcelable(ARG2);
        }

        /*Create menu item in fragment.*/
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");

        /*Inflate the fragment view.*/
        View view = inflater.inflate(R.layout.video_search_fragment, container, false);

        /*Put topic name on top of fragment.*/
        TextView topicName = (TextView) view.findViewById(R.id.topicName);
        topicName.setText(topic.getTopic());

        /*Create the text view and set it's visibility to invisible.
        * NOTE: The inflated layout is reused by other fragments but this fragment doesn't use the text view so set it to invisible. */
        TextView noVideos = (TextView) view.findViewById(R.id.noVideos);
        noVideos.setVisibility(View.INVISIBLE);


        /*Create the adapter.*/
        adapter = new TopicDetailAdapter(videoList, topic,listener);
        layoutManager = new LinearLayoutManager(getActivity());

        /*Create the recycler view.*/
        recyclerView = (RecyclerView) view.findViewById(R.id.video_list_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,"onAttach");

        /*Initialize the interface to pass data to activity.*/
        listener = (InteractionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"onDetach");

        listener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionMenu");

        /*Clear the menu from the previous fragment.*/
        menu.clear();
    }
}
