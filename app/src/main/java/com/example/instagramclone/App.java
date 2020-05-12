package com.example.instagramclone;

import android.app.Application;
import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("z0CXbMx05znxBrJOXA7k9wimGe9ALDNUYzEPoSvr")
                // if defined
                .clientKey("uQyqcXvYBIC71tFu2dQjKOA8Axk9XWfnS3musiG7")
                .server("https://parseapi.back4app.com/")
                .build()
        );

    }
}
