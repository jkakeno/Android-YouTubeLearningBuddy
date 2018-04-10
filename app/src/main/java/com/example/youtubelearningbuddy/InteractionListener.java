package com.example.youtubelearningbuddy;

import android.view.MenuItem;

import com.example.youtubelearningbuddy.Model.Topic;
import com.example.youtubelearningbuddy.Model.Video;

/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 * <p/>
 * See the Android Training lesson <a href=
 * "http://developer.android.com/training/basics/fragments/communicating.html"
 * >Communicating with Other Fragments</a> for more information.
 */

public interface InteractionListener {

    /*TopicSelectFragmentInteraction method.*/
    void onTopicSelectFragmentInteraction(Topic topic);

    void confirmButtonInteraction(boolean confirmed);

    /*TopicListFragmentInteraction method.*/
    void onTopicListFragmentInteraction(Topic topic);

    /*VideoDetailFragmentInteraction method.*/
    void onVideoDetailFragmentInteraction(MenuItem item);

    /*VideoSearchFragmentInteraction method.*/
    void onVideoSearchFragmentInteraction(Video video);

    /*TopicDetailInteraction method.*/
    void onTopicDetailInteraction(Video video, Topic topic);

    /*PlayVideoInteraction method.*/
    void onPlayVideoInteraction(Video video);
}
