package com.example.youtubelearningbuddy.Model.commentresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*This class represents the item object in APIResponseComments.*/
public class Item {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("snippet")
    @Expose
    private Snippet snippet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

}
