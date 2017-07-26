package com.example.kadiridamilola.javadel;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

public class NewPage extends AppCompatActivity {
private String url;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_page);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        url = intent.getStringExtra("url");
        String image_url = intent.getStringExtra("image_url");

        //uses the fresco library to load images
        Uri uri = Uri.parse(image_url);
        //finds the SimpleDraweeView
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.profilePicSub);
        draweeView.setImageURI(uri);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                     //   .setAction("Action", null).show();
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome developer, @"+ username+", "+url);
                startActivity(shareIntent);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        TextView user1 = (TextView) findViewById(R.id.usernameSub);
        user1.setText(username);
        TextView url1 = (TextView) findViewById(R.id.urlSub);
        url1.setText(url);
        url1.setPaintFlags(url1.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        //a medium to open the user's url on the web
        url1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent webIntent = new Intent();
                webIntent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(url);
                webIntent.setData(uri);
                startActivity(webIntent);
            }
        });
    }
//finish activity when closed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
