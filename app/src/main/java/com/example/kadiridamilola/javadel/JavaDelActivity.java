package com.example.kadiridamilola.javadel;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

public class JavaDelActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<JavaDel>> {
    private static final String JAVADELS = "https://api.github.com/search/users?q=location:lagos+language:java";
    private int KEY = 1;
    private JavaDelAdapter javaDelAdapter;
    private ImageView emptyView;
    private android.app.LoaderManager loaderManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_del);
        Fresco.initialize(this);

        javaDelAdapter = new JavaDelAdapter(this, new ArrayList<JavaDel>());
        //finds the listView reference
        final ListView listView = (ListView) findViewById(R.id.rootView);
        listView.setAdapter(javaDelAdapter);


        emptyView = (ImageView) findViewById(R.id.progress);
        final ProgressBar progressBar2 = (ProgressBar) findViewById(R.id.progress2);


        if (javaDelAdapter.isEmpty()) {

            listView.setEmptyView(emptyView);

            emptyView.setOnClickListener(new View.OnClickListener() {


                                             @Override
                                             public void onClick(View view) {
                                                 KEY = KEY + 1;

                                                 emptyView.setImageResource(0);

                                                 progressBar2.setVisibility(View.VISIBLE);

                                                 if (javaDelAdapter.isEmpty()) {

                                                     listView.setEmptyView(emptyView);
                                                 }

                                                 if (hasNetwork()) {
                                                     loaderManager = getLoaderManager();
                                                     loaderManager.initLoader(KEY, null, JavaDelActivity.this);

                                                 } else {
                                                     progressBar2.setVisibility(View.GONE);

                                                     emptyView.setImageResource(R.drawable.progress_internet);
                                                 }


                                                 Toast.makeText(getApplicationContext(), "reloading", Toast.LENGTH_LONG).show();
                                             }
                                         }
            );
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                JavaDel current = javaDelAdapter.getItem(i);
                Intent intent = new Intent(getApplicationContext(), NewPage.class);
                assert current != null;
                intent.putExtra("username", current.getmUsername());
                intent.putExtra("url", current.getmGUrl());
                intent.putExtra("image_url", current.getmPicName());
                startActivity(intent);
            }
        });

        if (hasNetwork()) {
            loaderManager = getLoaderManager();
            loaderManager.initLoader(KEY, null, this);
        } else {

            progressBar2.setVisibility(View.GONE);

            emptyView.setImageResource(R.drawable.progress_internet);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_java_del, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, about.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<List<JavaDel>> onCreateLoader(int i, Bundle bundle) {
        return new JavaDelAsyncLoader(this, JAVADELS);
    }

    @Override
    public void onLoadFinished(Loader<List<JavaDel>> loader, List<JavaDel> javaDels) {


        ProgressBar progressBar2 = (ProgressBar) findViewById(R.id.progress2);
        javaDelAdapter.clear();
        emptyView.setImageResource(R.drawable.progress_list);
        if (!javaDels.isEmpty() || javaDels != null) {

            progressBar2.setVisibility(View.GONE);
            javaDelAdapter.addAll(javaDels);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<JavaDel>> loader) {
        javaDelAdapter.clear();
    }

    private boolean hasNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }
}
