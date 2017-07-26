package com.example.kadiridamilola.javadel;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Kadiri Tosin on 7/24/2017.
 */

class JavaDelAsyncLoader extends AsyncTaskLoader<List<JavaDel>> {
   private final String mUrl;
  public JavaDelAsyncLoader(Context context, String vUrl){
      super(context);
      mUrl = vUrl;

  }

    @Override
    public List<JavaDel> loadInBackground() {

        return QueryDevs.fetchDevData(mUrl);




    }

    @Override
    protected void onStartLoading() {
        forceLoad();

    }
}

