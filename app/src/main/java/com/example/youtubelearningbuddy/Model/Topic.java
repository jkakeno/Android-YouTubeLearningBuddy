package com.example.youtubelearningbuddy.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Topic implements Parcelable {
    String topic;
    long id;
    ArrayList<Video> videoList;

    /*Constructor*/
    public Topic(String topic) {
        this.topic = topic;
        videoList = new ArrayList<>();
    }

    /*Getter & Setter methods*/
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(ArrayList<Video> videoList) {
        this.videoList = videoList;
    }

    /*Helper method.*/
    public void addVideo(Video video) {
        videoList.add(video);
    }

    public ArrayList<String> getVideosTitle() {
        ArrayList<String> videoTitle = new ArrayList<>();
        for(Video video : videoList){
            videoTitle.add(video.getTitle());
        }
        return videoTitle;
    }

/*Parcelable methods*/
    protected Topic(Parcel in) {
        topic = in.readString();
        id = in.readLong();
        in.readList(videoList, Topic.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(topic);
        parcel.writeLong(id);
        parcel.writeList(videoList);
    }



    /*Parcelable creator*/
    public static final Parcelable.Creator<Topic> CREATOR = new Parcelable.Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel in) {
            return new Topic(in);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };

}
