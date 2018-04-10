package com.example.youtubelearningbuddy.Retrofit;

import com.example.youtubelearningbuddy.Model.videoresponse.APIResponseVideos;
import com.example.youtubelearningbuddy.Model.commentresponse.APIResponseComments;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*This class represents the interface needed to make api calls.
* It contains the methods necessary to make the calls.*/

public interface ApiInterface {
    String API_KEY = "AIzaSyAGKMQYpHEBWDIyZkXIcoG_cAb23wbyydY";

    /*This method is to get videos when provided a search query.
    *The method is constructed based on the api doc in the link below:
    *https://developers.google.com/youtube/v3/docs/search/list*/

    @GET("search?part=snippet&order=rating&type=video&maxResults=50&key=" + API_KEY)
    Call<APIResponseVideos> getVideos(
            @Query("q") String query);

    /*NOTE: Acceptable values for maxResults are 1 to 50, inclusive. The default value is 5.*/

    /*This method is to get video comments when provided the video id
    *The method is constructed based on the api doc in the link below:
    *https://developers.google.com/youtube/v3/docs/commentThreads/list*/

    @GET("commentThreads?part=snippet%2Creplies&key=" + API_KEY)
    Call<APIResponseComments> getComments(
            @Query("videoId") String videoId);
}
