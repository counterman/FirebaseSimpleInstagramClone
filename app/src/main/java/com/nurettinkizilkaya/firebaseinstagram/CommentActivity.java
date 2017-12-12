package com.nurettinkizilkaya.firebaseinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class CommentActivity extends AppCompatActivity {
    TextView emailText;
    TextView commentText;
    ImageView imgPost;
    static int position;
    EditText uploadCommentText;
    Button uploadCommentButton;

    static ArrayList<String> useremailsFromFB;
    static ArrayList<String> usercommentFromFB;



    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    PostCommentClass adapter;
    ListView listView;


    ArrayList<String> comments;
    static Date now;
    static long timeCount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);



        firebaseDatabase=FirebaseDatabase.getInstance();

        myRef=firebaseDatabase.getReference();
        mAuth=FirebaseAuth.getInstance();

        useremailsFromFB=new ArrayList<String>();
        usercommentFromFB=new ArrayList<String>();

        now=new Date();



        emailText=(TextView)findViewById(R.id.emailText);
        commentText=(TextView)findViewById(R.id.commentText);
        imgPost=(ImageView)findViewById(R.id.imgPost);
        comments=new ArrayList<String>();
        uploadCommentText=(EditText)findViewById(R.id.uploadCommentText);
        uploadCommentButton=(Button)findViewById(R.id.uploadComment);


        Intent intent=getIntent();
        position=intent.getIntExtra("position",1);
        String emailPost=FeedActivity.useremailsFromFB.get(position);
        String imagePost=FeedActivity.userimageFromFB.get(position);
        String commentPost=FeedActivity.usercommentFromFB.get(position);


        emailText.setText(emailPost);
        commentText.setText(commentPost);
        Picasso.with(getApplicationContext()).load(imagePost).into(imgPost);

        adapter=new PostCommentClass(useremailsFromFB,usercommentFromFB,this);
        listView=(ListView)findViewById(R.id.comment_list);

        listView.setAdapter(adapter);



        uploadCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeCount=now.getTime();
                FirebaseUser user=mAuth.getCurrentUser();
                String userEmail=user.getEmail().toString();
                String userComment=uploadCommentText.getText().toString();
                uploadCommentText.setText("");
                UUID uuid=UUID.randomUUID();
                timeCount=now.getTime();
                String uuidString=String.valueOf(timeCount)+uuid.toString();

                myRef.child("Posts").child(FeedActivity.uuidFromFBinFeed.get(position)).child("postComments").child(uuidString).child("commentuser").setValue(userComment);
                myRef.child("Posts").child(FeedActivity.uuidFromFBinFeed.get(position)).child("postComments").child(uuidString).child("commentuseremail").setValue(userEmail);




            }
        });



      getDataFromDB();


    }



    protected  void getDataFromDB(){
        DatabaseReference newReference=firebaseDatabase.getReference("Posts").child(FeedActivity.uuidFromFBinFeed.get(position)).child("postComments");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                   useremailsFromFB.clear();
                   usercommentFromFB.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String, String>) ds.getValue();
                    useremailsFromFB.add(hashMap.get("commentuseremail"));

                    usercommentFromFB.add(hashMap.get("commentuser"));
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    protected void countFromDB(){
        DatabaseReference newReference=firebaseDatabase.getReference("Posts").child(FeedActivity.uuidFromFBinFeed.get(position)).child("postComments");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                useremailsFromFB.clear();
                usercommentFromFB.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String, String>) ds.getValue();
                    useremailsFromFB.add(hashMap.get("commentuseremail"));

                    usercommentFromFB.add(hashMap.get("commentuser"));
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
