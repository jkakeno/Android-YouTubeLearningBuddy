package com.example.youtubelearningbuddy.Model.videoresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*This class represents the item object of APIResponse.*/

public class Item {
    @SerializedName("id")
    @Expose
    private Id id;
    @SerializedName("snippet")
    @Expose
    private Snippet snippet;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

}
