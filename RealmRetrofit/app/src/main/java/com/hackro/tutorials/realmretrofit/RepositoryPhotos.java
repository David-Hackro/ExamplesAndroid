package com.hackro.tutorials.realmretrofit;

import android.util.Log;

import com.hackro.tutorials.realmretrofit.Entidades.Photo;
import com.hackro.tutorials.realmretrofit.Interfaces.IRepositoryPhotos;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by david on 10/03/16.
 */
public class RepositoryPhotos  implements IRepositoryPhotos{


    private Realm realm;
    private Photo photo;
    private RealmConfiguration realmConfig;


    public RepositoryPhotos(RealmConfiguration r) {
        realmConfig = r;
        realm = Realm.getInstance(realmConfig);

    }


    @Override
    public void addPhoto(Photo p) {
        realm.beginTransaction();
        photo = realm.createObject(Photo.class);
        photo.setTitle(p.getTitle());
        photo.setId(p.getId());
        photo.setAlbumId(p.getAlbumId());
        photo.setThumbnailUrl(p.getThumbnailUrl());
        photo.setUrl(p.getUrl());
        realm.commitTransaction();
    }

    @Override
    public List<Photo> readPostAll() {
        RealmResults<Photo> result = realm.where(Photo.class).findAll();
        for (Photo p : result)
            Log.e("resultados:::: ",p.getTitle());
        return null;
    }



}
