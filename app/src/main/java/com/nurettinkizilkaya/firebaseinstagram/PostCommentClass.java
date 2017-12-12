package com.nurettinkizilkaya.firebaseinstagram;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 11.12.2017.
 */

public class PostCommentClass extends ArrayAdapter<String>{
    private final ArrayList<String> userEmails;
    private final ArrayList<String> userComments;
    private final Activity context;

    PostCommentClass(ArrayList<String> userEmails,ArrayList<String>userComments,Activity context){
        super(context,R.layout.custom_comment_view,userEmails);
        this.userEmails=userEmails;
        this.userComments=userComments;
        this.context=context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater=context.getLayoutInflater();
        View customView=layoutInflater.inflate(R.layout.custom_comment_view,null,true);

        TextView useremailText=(TextView)customView.findViewById(R.id.userEmail);
        TextView commentText=(TextView)customView.findViewById(R.id.userComment);


        useremailText.setText(userEmails.get(position));
        commentText.setText(userComments.get(position));

        return customView;
    }
}
