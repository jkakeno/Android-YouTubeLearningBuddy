package com.example.youtubelearningbuddy.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.youtubelearningbuddy.DataBase;
import com.example.youtubelearningbuddy.InteractionListener;
import com.example.youtubelearningbuddy.Model.Topic;
import com.example.youtubelearningbuddy.R;

import java.util.ArrayList;

public class TopicSelectFragment extends Fragment implements TopicSelectAdapter.itemClickListener {

    private static final String TAG =TopicSelectFragment.class.getSimpleName();
    private InteractionListener listener;
    ArrayList<Topic> topicList = new ArrayList<>();
    RecyclerView recyclerView;
    TopicSelectAdapter adapter;
    LinearLayoutManager layoutManager;
    DataBase db;
    Button confirmBtn;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TopicSelectFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");

        /*Instantiate the database.*/
        db = new DataBase(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");

        /*Inflate the view for the fragment.*/
        final View view = inflater.inflate(R.layout.topic_list_fragment, container, false);

        /*Get the topic list from the database.*/
        topicList=db.getTopicList();

        /*Set fab visibility as gone since the inflated view is used in other fragment but this fragment doesn't use it.*/
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        confirmBtn = (Button) view.findViewById(R.id.confirm);
        /*Set confirm button invisible because confirm button visibility will be controlled down below in onItemClickListener().*/
        confirmBtn.setVisibility(View.INVISIBLE);

        /*Display noTopics text only if there aren't any topic in the topic list.*/
        TextView noTopics = (TextView) view.findViewById(R.id.noTopics);
        if(topicList.size()>0) {
            noTopics.setVisibility(View.INVISIBLE);
        }else{
            noTopics.setVisibility(View.VISIBLE);
        }

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Notify the active callbacks interface (the activity, if the fragment is attached to one) that an item has been selected.*/
                listener.confirmButtonInteraction(true);
            }
        });


        /*Create the adapter.*/
        adapter = new TopicSelectAdapter(getActivity(),topicList, listener);
        layoutManager = new LinearLayoutManager(getActivity());

        /*Create the recycler view.*/
        recyclerView = (RecyclerView) view.findViewById(R.id.topic_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        /*Initialize the adapter private interface.*/
        adapter.setOnItemClickListener(this);

        return view;
    }


    /*Create search view on action bar.
    * Tutorial http://www.viralandroid.com/2016/03/implementing-searchview-in-android-actionbar.html*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG,"onCreateOptionsMenu");
        /*Clear the menu from the previous fragment.*/
        menu.clear();
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

        /*Return clickedHolderPosition variable in the adapter back to initial value to reset.*/
        adapter.clickedHolderPosition = -1;

    }

    /*TopicSelectAdapter.itemClickListener Implemented method.
    * Here the fragment receives notification that a holder has been clicked in the adapter.*/
    @Override
    public void onItemClickListener(boolean isClicked) {
        /*Update adapter.*/
        adapter.updateAdapter();

        /*Display confirm button only if a holder in adapter has been clicked.*/
        if(isClicked) {
            confirmBtn.setVisibility(View.VISIBLE);
        }else {
            confirmBtn.setVisibility(View.INVISIBLE);
        }
    }
}

