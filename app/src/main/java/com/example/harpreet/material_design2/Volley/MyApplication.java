package com.example.harpreet.material_design2.Volley;

import android.content.Context;

/**
 * Created by Harpreet on 21/02/2017.
 */

public class MyApplication extends android.app.Application {

    static MyApplication application = null;
    private static String Api_Key = "api_key=41930382bcc2c2f40b23477bf4947cec";
    private static String tmdbUrl = "https://api.themoviedb.org/3/movie/";

    public static String url(int page, String preference) {
        // return"https://api.themoviedb.org/3/discover/movie?"+Api_Key+"&language=en-US&sort_by=primary_release_date.desc&include_adult=false&include_video=false&page="+pageLimit;

//       return "https://api.themoviedb.org/3/discover/movie/upcoming?api_key=41930382bcc2c2f40b23477bf4947cec&page="+page;

//        return "http://www.omdbapi.com/?t=Deadpool&y=&plot=short&r=json";
         return tmdbUrl+preference+"?"+Api_Key+"&page="+page;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static MyApplication getInstance() {
        return application;
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }
}
