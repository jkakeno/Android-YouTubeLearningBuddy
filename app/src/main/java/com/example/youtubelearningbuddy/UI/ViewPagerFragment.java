package com.example.youtubelearningbuddy.UI;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.youtubelearningbuddy.R;

/*This class represents the first fragment displayed in the app.
* It is a fragment that contains a view pager to create tab fragments within this fragment.*/

public class ViewPagerFragment extends Fragment{

    private static final String TAG =VideoSearchFragment.class.getSimpleName();
    public ViewPagerFragment() {   }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");

        /*Inflate the view that will be display when this fragment is created.*/
        View view = inflater.inflate(R.layout.viewpager_fragment, container, false);

        /*Create the fragments for the view pager.*/
        final VideoSearchFragment videoSearchFragment = new VideoSearchFragment();
        final TopicListFragment topicListFragment = new TopicListFragment();

        /*Set the view pager.*/
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                videoSearchFragment.setArguments(getArguments());
                topicListFragment.setArguments(getArguments());
                return position == 0 ? videoSearchFragment : topicListFragment;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return position == 0 ? "Videos" : "Topics";
            }

            @Override
            public int getCount() {
                return 2;
            }
        });

        /*Set the tabs.*/
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
