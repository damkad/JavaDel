package com.example.kadiridamilola.javadel;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Kadiri Tosin on 7/22/2017.
 */

class JavaDelAdapter extends ArrayAdapter<JavaDel> {

    public JavaDelAdapter(Context context, List<JavaDel> vArrayList) {
        super(context, 0, vArrayList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View recycler = convertView;
        if (recycler == null) {
            recycler = LayoutInflater.from(getContext()).inflate(R.layout.template, parent, false);
        }
        //gets the position of the JavaDel
        JavaDel current = getItem(position);
        TextView username = recycler.findViewById(R.id.username);
        assert current != null;
        username.setText(current.getmUsername());

        //uses the fresco image loading library to handle the images
        Uri uri = Uri.parse(current.getmPicName());
        SimpleDraweeView draweeView = recycler.findViewById(R.id.profilePic);
        draweeView.setImageURI(uri);

        return recycler;
    }
}
