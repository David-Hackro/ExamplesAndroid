package com.example.david.crudrealm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.david.crudrealm.Entidades.Post;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    private Post post;
    private Realm realm;
    private RealmConfiguration realmConfig;
    private RepositoryPosts rp;
    private EditText Title,Body,IdPost;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rp = new RepositoryPosts(MainActivity.this);

        Title = (EditText)findViewById(R.id.Title);
        Body = (EditText)findViewById(R.id.Body);
        IdPost = (EditText)findViewById(R.id.IdPost);


    }



    public void InsertPost(View view)
    {
        Post post = new Post();
        post.setTitle(Title.getText().toString());
        post.setBody(Body.getText().toString());
        rp.addPost(post);
        Toast.makeText(MainActivity.this,"Insert Successful",Toast.LENGTH_SHORT).show();
    }

    public void UpdatePost(View view)
    {
        Post p  = new Post();

        p.setUserId(Integer.parseInt(IdPost.getText().toString()));
        p.setTitle(Title.getText().toString());
        p.setBody(Body.getText().toString());

        rp.updatePostId(p);
        Toast.makeText(MainActivity.this,"Update Successful",Toast.LENGTH_SHORT).show();

    }

    public void DeletePost(View view)
    {
        rp.deletePostId(Integer.valueOf(IdPost.getText().toString()));
        Toast.makeText(MainActivity.this,"Delete Successful",Toast.LENGTH_SHORT).show();
    }

    public void SelectPost(View view)
    {
        if(rp.readPostAll() != null)
        {
            for (Post p : rp.readPostAll())
            {
                Log.e("| ",p.toString()+" |");
            }
        }
    }










}
