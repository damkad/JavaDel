package com.example.kadiridamilola.javadel;

/**
 * Created by Kadiri Tosin on 7/22/2017.
 */

public class JavaDel {
    //attributes JavaDel objects includes String username, String gUrl, Image profilePic
    private final String mUsername;
    private final String mGUrl;
    private final String mPicName;


    public JavaDel(String vUsername, String vGUrl, String vPicName) {
        mUsername = vUsername;
        mGUrl = vGUrl;
        mPicName = vPicName;

    }

    public String getmUsername() {
        return mUsername;
    }

    public String getmGUrl() {
        return mGUrl;
    }

    public String getmPicName() {
        return mPicName;
    }


}
