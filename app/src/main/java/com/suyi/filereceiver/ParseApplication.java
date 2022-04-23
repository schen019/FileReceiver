package com.suyi.filereceiver;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("00WrnaQMIzHjt3M6MCwoxVXyM7YJKTg68bZqsoDS")
                .clientKey("eghyjf3t3IlTKKM0wh61sLLCPYpIuEBYCFil2ZSJ")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}

