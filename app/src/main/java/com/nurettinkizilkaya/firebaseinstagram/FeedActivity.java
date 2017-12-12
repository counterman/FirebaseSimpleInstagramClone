package com.nurettinkizilkaya.firebaseinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedActivity extends AppCompatActivity {
    static ArrayList<String> useremailsFromFB;
    static ArrayList<String> userimageFromFB;
    static ArrayList<String> usercommentFromFB;
    static ArrayList<String> uuidFromFBinFeed;
    static ArrayList<String> timeFromFB;
    static ArrayList<String> dateFromFB;




    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRef;
    PostClass adapter;
    ListView listView;


    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_post,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add_post){
            Intent intent=new Intent(getApplicationContext(),UploadActivity.class);
            startActivity(intent);

        }

        if(item.getItemId()==R.id.exit){
            MainActivity.editor.putInt("myInt",-1);
            MainActivity.editor.apply();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        useremailsFromFB=new ArrayList<String>();
        usercommentFromFB=new ArrayList<String>();
        userimageFromFB=new ArrayList<String>();
        uuidFromFBinFeed=new ArrayList<String>();
        dateFromFB=new ArrayList<String>();
        timeFromFB=new ArrayList<String>();




        firebaseDatabase=FirebaseDatabase.getInstance();
        mRef=firebaseDatabase.getReference();


        adapter=new PostClass(useremailsFromFB,userimageFromFB,usercommentFromFB,timeFromFB,dateFromFB,this);
        listView=(ListView)findViewById(R.id.listview5);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),CommentActivity.class);
                intent.putExtra("position",i);
                startActivity(intent);
            }
        });
        getDataFromDB();
    }




    protected  void getDataFromDB(){
        DatabaseReference newReference=firebaseDatabase.getReference("Posts");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clearArrayLists();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String, String>) ds.getValue();
                    useremailsFromFB.add(hashMap.get("useremail"));
                    userimageFromFB.add(hashMap.get("downloadUrl"));
                    usercommentFromFB.add(hashMap.get("comment"));
                    uuidFromFBinFeed.add(hashMap.get("uuid"));
                    dateFromFB.add(hashMap.get("date"));
                    timeFromFB.add(hashMap.get("time"));
                    adapter.notifyDataSetChanged();

                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void clearArrayLists(){

        useremailsFromFB.clear();
        userimageFromFB.clear();
        usercommentFromFB.clear();
        uuidFromFBinFeed.clear();
        dateFromFB.clear();
        timeFromFB.clear();

    }



}
