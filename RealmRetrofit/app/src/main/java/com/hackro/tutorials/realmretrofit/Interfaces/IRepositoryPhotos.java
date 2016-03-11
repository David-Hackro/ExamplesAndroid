package com.hackro.tutorials.realmretrofit.Interfaces;

import com.hackro.tutorials.realmretrofit.Entidades.Photo;

import java.util.List;

/**
 * Created by david on 10/03/16.
 */
public interface IRepositoryPhotos {

    public void addPhoto(Photo photo);
    public List<Photo> readPostAll();

}
