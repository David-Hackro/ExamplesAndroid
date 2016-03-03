package com.example.david.crudrealm.Controls;

import android.content.Context;

import com.example.david.crudrealm.Entidades.Post;
import com.example.david.crudrealm.IControls.IRepositoryPost;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by david on 1/03/16.
 */
public class RepositoryPosts implements IRepositoryPost {
    private Realm realm;
    private RealmConfiguration realmConfig;
    private Post post;

    public RepositoryPosts(Context c) {
        realmConfig = new RealmConfiguration.Builder(c).build();
        realm = Realm.getInstance(realmConfig);
    }

    public RepositoryPosts() {
    }

    //Add new Post
    @Override
    public void addPost(Post p) {
        realm.beginTransaction();
        post = realm.createObject(Post.class);
        post.setTitle(p.getTitle());
        post.setBody(p.getBody());
        post.setUserId(getNextKey());
        realm.commitTransaction();
    }
    //Delete Post for ID
    @Override
    public void deletePostId(int IdPost) {
        realm.beginTransaction();
        Post postTemp = realm.where(Post.class).equalTo("userId", IdPost).findFirst();
        postTemp.removeFromRealm();
        realm.commitTransaction();
    }
    //Delete All Post
    @Override
    public void deletePostAll() {
        // Delete all persons
        realm.beginTransaction();
        realm.allObjects(Post.class).clear();
        realm.commitTransaction();
    }
    //Update Post for Id
    @Override
    public void updatePostId(Post post) {
        realm.beginTransaction();
        Post  p = finPostId(post);
        p.setBody(post.getBody());
        p.setTitle(post.getTitle());
        realm.commitTransaction();
    }
    //Return List all Post for Id
    @Override
    public RealmResults<Post> readPostAll() {
        RealmResults<Post> result = realm.where(Post.class).findAll();
        return result;
    }

    //search for Id
    public Post finPostId(Post POST)
    {
        Post p = realm.where(Post.class).equalTo("userId", POST.getUserId()).findFirst();
        return p;
    }
    //Find First Post
    @Override
    public Post findFirstPost() {
        post = realm.createObject(Post.class);
        post = realm.where(Post.class).findFirst();
        return post;
    }

    @Override
    public int  getNextKey()
    {
        try {
            return realm.where(Post.class).max("userId").intValue() + 1;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
        catch (Exception e)
        {
            return  0;
        }
    }

}
