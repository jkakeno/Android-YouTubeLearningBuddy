package com.example.youtubelearningbuddy.Model.videoresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/*This class represents the response of the api to request
GET https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=25&q=surfing&key={YOUR_API_KEY}
*See api documentation @ https://developers.google.com/youtube/v3/docs/search/list#response
*
* The response template below was modified to the minimum number of items for simplicity.
* {
  "nextPageToken":"string",
  "items": [{
            "id": {
                  "videoId": "string"
            },
            "snippet": {
                      "publishedAt": "string",
                      "title": "string",
                      "description": "string",
                      "thumbnails": {
                                     "high": {
                                             "url": "string"
                                     }
                      }
            }
  }]
}
* The template above is paste in http://www.jsonschema2pojo.org/ to create a Gson class
* so that it can be used as objects in the program.
* */

public class APIResponseVideos {

    @SerializedName("nextPageToken")
    @Expose
    private String nextPageToken;

    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
