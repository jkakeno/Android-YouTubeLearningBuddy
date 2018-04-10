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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youtubelearningbuddy.InteractionListener;
import com.example.youtubelearningbuddy.Model.Video;
import com.example.youtubelearningbuddy.Model.commentresponse.APIResponseComments;
import com.example.youtubelearningbuddy.Model.commentresponse.Item;
import com.example.youtubelearningbuddy.R;
import com.example.youtubelearningbuddy.Retrofit.ApiInterface;
import com.example.youtubelearningbuddy.Retrofit.ApiUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VideoDetailFragment extends Fragment {

    private static final String ARG = "arg";
    private InteractionListener listener;
    ApiInterface APIInterface;
    Video video;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<Item> items;
    CommentListAdapter adapter;

    public VideoDetailFragment() {
        // Required empty public constructor
    }

    /* Use this factory method to create a new instance of
     * this fragment using the provided parameters.*/
    public static VideoDetailFragment newInstance(Video video) {
        /*Create an instance of this fragment.*/
        VideoDetailFragment fragment = new VideoDetailFragment();
        /*Store the received data in this fragment tag.*/
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG,video);
        fragment.setArguments(bundle);
        /*Return the fragment.*/
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Get the video stored in bundle.*/
        if (getArguments() != null) {
            video =getArguments().getParcelable(ARG);
        }
        /*Initialize api interface.*/
        APIInterface = ApiUtils.getApiInterface();
        /*Set the fragment menu.*/
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =inflater.inflate(R.layout.video_detail_fragment, container, false);
        ImageView thumbnail = (ImageView) view.findViewById(R.id.videoThumbnail);
        TextView description = (TextView) view.findViewById(R.id.videoDescription);
        final TextView noComments = (TextView) view.findViewById(R.id.noComments);

        /*Set views.*/
        Picasso.with(getActivity())
                .load(video.getThumbnailUrl())
                .fit()
                .centerCrop()
                .into(thumbnail);
        description.setText(video.getDescription());
        noComments.setVisibility(View.INVISIBLE);

        /*Call comment threads.*/
        APIInterface.getComments(video.getId()).enqueue(new Callback<APIResponseComments>() {
            @Override
            public void onResponse(Call<APIResponseComments> call, Response<APIResponseComments> response) {
                if(response.body()!=null) {
                    if (response.isSuccessful()) {
                        if (!response.body().getItems().isEmpty()) {
                            /*Get a list of items which is located in the callback response body.
                            * From the list of items you can get the comments and the author profile thumbnail url.*/
                            items = response.body().getItems();

                            /*Create the adapter.*/
                            adapter = new CommentListAdapter(items);
                            layoutManager = new LinearLayoutManager(getActivity());

                            /*Create the recycler view.*/
                            recyclerView = (RecyclerView) view.findViewById(R.id.videoComments);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(layoutManager);

                        } else {
                            noComments.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Log.d("", "Error getting results");
                    }
                }
            }

            @Override
            public void onFailure(Call<APIResponseComments> call, Throwable t) {
                Log.d("Api call failed: ", t.toString());
            }
        });

        /*Play video when click on thumbnail*/
        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPlayVideoInteraction(video);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*Initialize the interface to pass data to activity.*/
        listener = (InteractionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        /*Clear the menu from the previous fragment.*/
        menu.clear();
        /*Inflate the new fragment menu from resource.*/
        inflater.inflate(R.menu.video_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_to_topic:
                listener.onVideoDetailFragmentInteraction(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
