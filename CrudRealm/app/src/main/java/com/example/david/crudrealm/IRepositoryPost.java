package com.example.david.crudrealm;

import com.example.david.crudrealm.Entidades.Post;

import java.util.List;

/**
 * Created by david on 1/03/16.
 */
public interface IRepositoryPost {


    public void addPost(Post post);

    public void deletePostId(int IdPost);

    public void deletePostAll();

    public void updatePostId(Post post);

    public List<Post> readPostAll();

    public Post findFirstPost();

    public int getNextKey();

}
