package com.suyi.filereceiver;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(File.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("RjnQCUMKfpygu6YeD3oa7P2JePd6CrTjXHDeh6vJ")
                .clientKey("TcU0CG019MMxCCnqX6DlX3eHDe0npCxplvBt6IQe")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}

