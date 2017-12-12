package com.nurettinkizilkaya.firebaseinstagram;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by user on 10.12.2017.
 */

public class PostClass extends ArrayAdapter<String> {
    private final ArrayList<String> useremail;
    private final ArrayList<String> userImage;
    private final ArrayList<String> userComment;
    private final ArrayList<String> time;
    private final ArrayList<String> date;

    private final Activity context;

   public PostClass(ArrayList<String> useremail, ArrayList<String> userImage, ArrayList<String> userComment,ArrayList<String> time,ArrayList<String> date,Activity context){
       super(context,R.layout.custom_view,useremail);
       this.useremail=useremail;
       this.userImage=userImage;
       this.userComment=userComment;
       this.context=context;
       this.time=time;
       this.date=date;

   }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater=context.getLayoutInflater();
        View customView=layoutInflater.inflate(R.layout.custom_view,null,true);

        TextView useremailText=(TextView)customView.findViewById(R.id.username);
        TextView commentText=(TextView)customView.findViewById(R.id.commentText);
        ImageView imageView=(ImageView)customView.findViewById(R.id.uploadedImage);
        TextView dateandhour=(TextView)customView.findViewById(R.id.dateandhour);

        dateandhour.setText(time.get(position)+" "+date.get(position));
        useremailText.setText(useremail.get(position));
        commentText.setText(userComment.get(position));
        Picasso.with(context).load(userImage.get(position)).into(imageView);
        return customView;
    }
}
