package com.example.youtubelearningbuddy.Model.commentresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/*This class represents the response of the api to request
* GET https://www.googleapis.com/youtube/v3/commentThreads?part=snippet&videoId=m4Jtj2lCMAA&key={YOUR_API_KEY}
*See api documentation @ https://developers.google.com/youtube/v3/docs/commentThreads/list#response
*
*The response template below was modified to the minimum number of items for simplicity.
*
* {
  "nextPageToken":"string",
  "items": [{
            "id": {
                  "videoId": "string"
            },
            "snippet": {
                      "videoId": "string",
                      "topLevelComment": {
                          				"id": "string",
                        				"snippet": {
                                          			"authorDisplayName": "string",
    												"authorProfileImageUrl": "string",
                                        			"textDisplay": "string"
                                        }
                      }
            }
  }]
}
* The template above is paste in http://www.jsonschema2pojo.org/ to create a Gson class
* so that it can be used as objects in the program.
* */

public class APIResponseComments {

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

/*Careful when importing packages because imported Items from Model.Items caused Gson error as the
  commentThread response didn't match with the Model.Item model.
  So changed the packaged to Model.comment.Item and it was fixed.*/
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


}