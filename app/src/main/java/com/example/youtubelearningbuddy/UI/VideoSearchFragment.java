package com.example.youtubelearningbuddy.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.youtubelearningbuddy.InteractionListener;
import com.example.youtubelearningbuddy.Model.VideoList;
import com.example.youtubelearningbuddy.Model.videoresponse.APIResponseVideos;
import com.example.youtubelearningbuddy.Model.videoresponse.Item;
import com.example.youtubelearningbuddy.R;
import com.example.youtubelearningbuddy.Retrofit.ApiInterface;
import com.example.youtubelearningbuddy.Retrofit.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VideoSearchFragment extends Fragment {

    private static final String TAG = VideoSearchFragment.class.getSimpleName();
    private InteractionListener listener;
    ApiInterface APIInterface;
    RecyclerView recyclerView;
    VideoSearchAdapter adapter;
    LinearLayoutManager layoutManager;
    VideoList videoList;
    View view;
    String querySaved;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VideoSearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");

        /*Instantiate the api interface.*/
        APIInterface = ApiUtils.getApiInterface();

        /*Create menu items for this fragment.*/
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");

        /*Inflate the view for this fragment.*/
        view = inflater.inflate(R.layout.video_search_fragment, container, false);

        /*Create the text view and set it's visibility to gone.
        * NOTE: The inflated layout is reused by other fragments but this fragment doesn't use the text view so set it to gone. */
        TextView topicName = (TextView) view.findViewById(R.id.topicName);
        topicName.setVisibility(View.GONE);

        /*Create a new video list.*/
        videoList = new VideoList();

        toggleMessage();

        /*Search with the stored query to prevent having to type query every time the fragment is created.*/
        if(querySaved!=null) {
            searchYoutube(querySaved);
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,"onAttached");

        /*Initialize the interface to pass data to activity.*/
        listener = (InteractionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"onDetached");

        listener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG,"onCreateOptionsMenu");

        /*Inflate the new fragment menu from resource.*/
        inflater.inflate(R.menu.search_menu,menu);

        /*Initialize search menu on action bar.*/
        MenuItem searchViewItem = menu.findItem(R.id.search);
        final SearchView searchViewActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                /*Create a new video list every time a new query is made to prevent the video list from accumulating items that are not in the current query.*/
                videoList = new VideoList();

                /*Store the search query so that the video list is displayed unless a new query is entered.*/
                querySaved = query;

                /*Search with the initial query.*/
                searchYoutube(querySaved);

                searchViewActionBar.clearFocus();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /*Do nothing as this will interrupt the stored query.*/
                return false;
            }
        });

    }

    private void searchYoutube(String query) {
        APIInterface.getVideos(query).enqueue(new Callback<APIResponseVideos>() {
            @Override
            public void onResponse(Call<APIResponseVideos> call, Response<APIResponseVideos> response) {

                /*Create a list of items from the response.*/
                List<Item> itemList = response.body().getItems();

                /*Use item list from response to create a video list.*/
                videoList.setVideoList(itemList);

                Log.d(TAG,"videoList size: " + videoList.getVideoList().size());
                toggleMessage();

                /*Create the adapter.*/
                adapter = new VideoSearchAdapter(videoList, listener);
                /*Subscribe the adapter to the observable video list.*/
                videoList.asObservable().subscribe(adapter);
                layoutManager = new LinearLayoutManager(getActivity());

                /*Create the recycler view.*/
                recyclerView = (RecyclerView) view.findViewById(R.id.video_list_recycler_view);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);

            }

            @Override
            public void onFailure(Call<APIResponseVideos> call, Throwable t) {
                Log.d("Api call failed: ", t.toString());
            }
        });
    }

    private void toggleMessage() {
        /*Create the text view and set it's visibility to invisible.
        * NOTE: The inflated layout is reused by other fragments but this fragment doesn't use the text view so set it to invisible. */
       TextView noVideos = (TextView) view.findViewById(R.id.noVideos);

        if (videoList.getVideoList().size() > 0) {
            noVideos.setVisibility(View.GONE);
        } else {
            noVideos.setVisibility(View.VISIBLE);
        }
    }
}
