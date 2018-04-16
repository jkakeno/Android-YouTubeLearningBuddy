package com.example.youtubelearningbuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.youtubelearningbuddy.Model.Topic;
import com.example.youtubelearningbuddy.Model.Video;
import com.example.youtubelearningbuddy.UI.TopicDetailFragment;
import com.example.youtubelearningbuddy.UI.TopicSelectFragment;
import com.example.youtubelearningbuddy.UI.VideoDetailFragment;
import com.example.youtubelearningbuddy.UI.ViewPagerFragment;

/*This class is the main activity which has interaction feed back from the fragments and adapters in this app.
* It controls the display and removal of fragments for the app.
*
* This app utilizes the Rx Java model in TopicList, TopicListFragment, VideoList and VideoSearchFragment.
*
* This app is a youtube play list maker.
* User can search for videos, create topics and save the videos to a desirable topic.
*
* Note: Youtube app needs to be installed in the emulator or phone to play videos.*/

public class MainActivity extends AppCompatActivity implements InteractionListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    Topic topic;
    Video video;
    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");

        setContentView(R.layout.activity_main);

        /*Instantiate the database.*/
        db = new DataBase(this);

        /*Set the action bar logo.*/
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.tv);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        /*By not adding the view pager fragment to the back stack ensures that the app is closed when user presses back button from video search fragment or topic list fragment.
        * https://stackoverflow.com/questions/20340303/in-fragment-on-back-button-pressed-activity-is-blank/34025775*/
        ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, viewPagerFragment).commit();
    }


    /*This is a method from the interface used by the adapter to communicate to the activity which video was clicked.*/
    @Override
    public void onVideoSearchFragmentInteraction(Video video) {
        Log.d(TAG,"Video from search: "+ video.getTitle());

        /*Store the video.*/
        this.video = video;

        VideoDetailFragment videoDetailFragment = VideoDetailFragment.newInstance(video);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,videoDetailFragment).addToBackStack(null).commit();
    }

    @Override
    public void onTopicDetailInteraction(Video video, Topic topic) {
        Log.d(TAG,"Video: "+ video.getTitle() + " from Topic: " + topic.getTopic());

        VideoDetailFragment videoDetailFragment = VideoDetailFragment.newInstance(video);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,videoDetailFragment).addToBackStack(null).commit();

        /*Set the visibility of menu of video detail fragment to invisible since the layout that this fragment uses is shared with other fragments.*/
        videoDetailFragment.setMenuVisibility(false);
    }

    @Override
    public void onTopicSelectFragmentInteraction(Topic topic) {
        Log.d(TAG,"Save video: " + video.getTitle() + " to Topic: " + topic.getTopic());

        /*Store the topic.*/
        this.topic=topic;
    }

    @Override
    public void confirmButtonInteraction(boolean confirmed) {
        Log.d(TAG,"Confirmed btn clicked: " + String.valueOf(confirmed));

        /*Add the video selected to the topic.*/
            topic.addVideo(video);

        /*Add a video to the selected topic to the data base.*/
            db.insertVideo(video, topic.getId());

        /*Get the previous fragment back to display.*/
            getSupportFragmentManager().popBackStackImmediate();

        /*Set topic back to null.*/
            topic = null;
    }

    @Override
    public void onTopicListFragmentInteraction(Topic topic) {
        Log.d(TAG,"Selected Topic: " + topic.getTopic());
        /*Print videos title that exist in the topic selected.*/
        Log.d(TAG,"Videos in " + topic.getTopic() + " are: " + topic.getVideosTitle().toString());

        TopicDetailFragment topicDetailFragment = TopicDetailFragment.newInstance(topic.getVideoList(),topic);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,topicDetailFragment).addToBackStack(null).commit();
    }

    @Override
    public void onVideoDetailFragmentInteraction(MenuItem item) {
        Log.d(TAG,"Menu item: "+ item.toString());

        TopicSelectFragment topicSelectFragment = new TopicSelectFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,topicSelectFragment).addToBackStack(null).commit();
    }
}
