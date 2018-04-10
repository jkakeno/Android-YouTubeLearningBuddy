package com.example.youtubelearningbuddy.Model;

import com.example.youtubelearningbuddy.Model.videoresponse.Item;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;

/*This class represents a list of videos.
* It is made a subject which functions as an observer and as an observable.
* It gets its data from the data base and uses a helper method so that subscribers can subscribe to this class and observe it.*/

public class VideoList {

    ReplaySubject<VideoList> notifier = ReplaySubject.create();
    List<Video> videoList;

    public VideoList() {
        this.videoList = new ArrayList<>();
    }

    /*Helper method to expose this class as an observable, so that subscribers can subscribe to it and observe it.*/
    public Observable<VideoList> asObservable(){
        return notifier;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Item> items) {
        this.videoList = new ArrayList<>();
        for(Item item : items) {
            this.videoList.add(Video.loadFromItem(item));
        }
        notifier.onNext(this);
    }
}
