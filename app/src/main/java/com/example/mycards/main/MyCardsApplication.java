package com.example.mycards.main;


import android.app.Application;

import com.example.mycards.di.AppComponent;
import com.example.mycards.di.DaggerAppComponent;

public class MyCardsApplication extends Application {

    //The graph that tells Dagger where all the dependencies are and how to make them.
    //Reference to the graph lives here to share the App's lifecycle.
    //This can be used across the whole app.
    AppComponent appComponent = DaggerAppComponent.builder()
            .application(this)
            .build();

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
