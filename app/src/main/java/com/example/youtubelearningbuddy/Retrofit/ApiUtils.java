package com.example.youtubelearningbuddy.Retrofit;

/*This class is in intermediate class between the ApiInterface and the RetrofitClient.
* It helps get a client for a particular api.
* In case the app calls multiple apis the different clients can be constructed here.*/
public class ApiUtils {
    static String BASE_URL = "https://www.googleapis.com/youtube/v3/";

    public static ApiInterface getApiInterface(){
        return RetrofitClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
