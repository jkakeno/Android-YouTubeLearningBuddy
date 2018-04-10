package com.example.youtubelearningbuddy.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.youtubelearningbuddy.DataBase;
import com.example.youtubelearningbuddy.InteractionListener;
import com.example.youtubelearningbuddy.Model.Topic;
import com.example.youtubelearningbuddy.Model.TopicList;
import com.example.youtubelearningbuddy.R;


public class TopicListFragment extends Fragment {

    private static final String TAG = TopicListFragment.class.getSimpleName();
    private InteractionListener listener;
    TopicList topicList;
    RecyclerView recyclerView;
    TopicListAdapter adapter;
    LinearLayoutManager layoutManager;
    Topic topic;
    DataBase db;
    View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TopicListFragment() {   }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");

        /*Instantiate the database.*/
        db = new DataBase(getActivity());



        /*Create menu item in fragment.*/
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");

        /*Inflate view for the fragment.*/
        view = inflater.inflate(R.layout.topic_list_fragment, container, false);

        /*Create the confirm button and set it's visibility to invisible.
        * NOTE: The inflated layout is reused by other fragments but this fragment doesn't use the confirm button so set it to gone. */
        Button confirmBtn = (Button) view.findViewById(R.id.confirm);
        confirmBtn.setVisibility(View.GONE);

        updateRecyclerView();

        toggleMessage();

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
        Log.d(TAG,"onCreatOptionsMenu");

        /*Clear the menu from the previous fragment.*/
        menu.clear();
        /*Inflate the new fragment menu from resource.*/
        inflater.inflate(R.menu.topic_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG,"onOptionsItemSelected");

        switch (item.getItemId()) {
            case R.id.add_topic:

                /*Create dialog to enter topic.*/
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_layout, null);
                dialogBuilder.setView(dialogView);

                final EditText editText = (EditText) dialogView.findViewById(R.id.dialogEdittext);

                /*Set positive button action.*/
                dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /*Create a topic.*/
                        topic = new Topic(editText.getText().toString());

                        /*Add a topic to the data base.*/
                        db.insertTopic(topic);

                        updateRecyclerView();

                        toggleMessage();

                    }
                });

                /*Set negative button action.*/
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateRecyclerView() {
        /*Create a topic list.*/
        topicList= new TopicList(db);
        /*Create the adapter.*/
        adapter = new TopicListAdapter(topicList, listener);
        /*Subscribe the adapter to the observable topic list.*/
        topicList.asObservable().subscribe(adapter);
        layoutManager = new LinearLayoutManager(getActivity());

        /*Create the recycler view.*/
        recyclerView = (RecyclerView) view.findViewById(R.id.topic_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void toggleMessage() {
        /*Create the text view and set it's visibility to invisible.
        * NOTE: The inflated layout is reused by other fragments but this fragment doesn't use the text view so set it to invisible. */
        TextView noTopics = (TextView) view.findViewById(R.id.noTopics);

        if (topicList.getTopicList().size() > 0) {
            noTopics.setVisibility(View.GONE);
        } else {
            noTopics.setVisibility(View.VISIBLE);
        }
    }
}
