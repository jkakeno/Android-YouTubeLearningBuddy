package com.example.youtubelearningbuddy.Model;

import com.example.youtubelearningbuddy.DataBase;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;

/*This class represents a list of topics.
* It is made a subject which functions as an observer and as an observable.
* It gets its data from the data base and uses a helper method so that subscribers can subscribe to this class and observe it.*/

public class TopicList {

    ReplaySubject<TopicList> notifier = ReplaySubject.create();
    ArrayList<Topic> topicList;

    public TopicList(DataBase db){
        this.topicList=db.getTopicList();
    }

    /*Helper method to expose this class as an observable, so that subscribers can subscribe to it and observe it.*/
    public Observable<TopicList> asObservable(){
        return notifier;
    }

    public ArrayList<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(ArrayList<Topic> topicList) {
        this.topicList = topicList;
    }
}
