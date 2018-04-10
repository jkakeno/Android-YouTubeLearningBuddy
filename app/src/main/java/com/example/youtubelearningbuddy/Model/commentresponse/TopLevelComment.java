package com.example.youtubelearningbuddy.Model.commentresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*This class represents the top level comment object in snippet (comment).*/

public class TopLevelComment {

    @SerializedName("id")
    @Expose
    private String id;

    /*Changed the name of this snippet object to "Comment" to differentiate between
    the snippet object of the item and the snippet object of the top level comment.*/

    @SerializedName("snippet")
    @Expose
    private Comment comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

}