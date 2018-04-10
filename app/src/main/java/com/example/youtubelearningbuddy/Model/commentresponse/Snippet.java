package com.example.youtubelearningbuddy.Model.commentresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*This class represents the snippet object in item (comment).*/

public class Snippet {

    @SerializedName("videoId")
    @Expose
    private String videoId;
    @SerializedName("topLevelComment")
    @Expose
    private TopLevelComment topLevelComment;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public TopLevelComment getTopLevelComment() {
        return topLevelComment;
    }

    public void setTopLevelComment(TopLevelComment topLevelComment) {
        this.topLevelComment = topLevelComment;
    }

}