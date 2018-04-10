package com.example.youtubelearningbuddy.Model.videoresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*This class represents the thumbnails object of snippet.*/

public class Thumbnails {
    @SerializedName("high")
    @Expose
    private High high;

    public High getHigh() {
        return high;
    }

    public void setHigh(High high) {
        this.high = high;
    }
}
