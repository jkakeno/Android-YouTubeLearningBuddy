package com.example.youtubelearningbuddy.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.youtubelearningbuddy.Model.videoresponse.Item;

public class Video implements Parcelable{

    private String publishedAt;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String id;
    private String foreignKey;

    public Video() {

    }

    /*Initialize all video fields.*/
    public Video(String id, String title, String description, String publishedAt,String thumbnailUrl, String foreignKey) {
        this.publishedAt = publishedAt;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.id = id;
        this.foreignKey = foreignKey;
    }


    /*Parcel all video fields so that the field's value can be used in other classes.*/
    protected Video(Parcel in) {
        publishedAt = in.readString();
        title = in.readString();
        description = in.readString();
        thumbnailUrl = in.readString();
        id = in.readString();
        foreignKey = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };


    /*Helper method to create a video from api response item.*/
    public static Video loadFromItem(Item item) {
		/*An item represents information about a video.
		* Create a video and set the video with the item received.*/
        Video video = new Video();
        video.id = item.getId().getVideoId();
        video.publishedAt = item.getSnippet().getPublishedAt();
        video.title = item.getSnippet().getTitle();
        video.description = item.getSnippet().getDescription();
        video.thumbnailUrl = item.getSnippet().getThumbnails().getHigh().getUrl();

		/*Return the video created.*/
        return video;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {return id;}

    @Override
    public int describeContents() {
        return 0;
    }

    /*Parcel all video fields so that the field's value can be used in other classes.*/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(publishedAt);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(thumbnailUrl);
        dest.writeString(id);
        dest.writeString(foreignKey);
    }
}
