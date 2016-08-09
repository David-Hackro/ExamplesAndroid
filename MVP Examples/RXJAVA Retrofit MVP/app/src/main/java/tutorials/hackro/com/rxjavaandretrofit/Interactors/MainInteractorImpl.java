package tutorials.hackro.com.rxjavaandretrofit.Interactors;

import android.util.Log;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tutorials.hackro.com.rxjavaandretrofit.Interfaces.MainInteractor;
import tutorials.hackro.com.rxjavaandretrofit.Interfaces.MainPresenter;
import tutorials.hackro.com.rxjavaandretrofit.Interfaces.Services.JsonPlaceHolderService;
import tutorials.hackro.com.rxjavaandretrofit.Models.Photo;

/**
 * Created by david on 9/08/16.
 */
public class MainInteractorImpl implements MainInteractor {

    private Retrofit retrofit;
    private JsonPlaceHolderService service;
    private String result;
    private MainPresenter presenter;
    //in retrofit 2 is necesary /

    static final String URL = "http://jsonplaceholder.typicode.com/";


    public MainInteractorImpl(MainPresenter presenter) {
        this.presenter = presenter;

        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .build();
        service = retrofit.create(JsonPlaceHolderService.class);
    }

    @Override
    public void getAllData() {
        Observable<List<Photo>> listPhotos = service.getAllPhotos();

        listPhotos.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Photo>>() {
                    @Override
                    public void onCompleted() {

                        Log.e("completed"," al fin termino de hacer el proceso");
                        presenter.onSucess(result);
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("error "," ocurrio un error");
                        presenter.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Photo> photos) {
                        Log.e("process .... "," proceso .... ");
                        for (Photo photo : photos)
                                result+=photo.getId() +" | " + photo.getTitle();


                    }


                });

    }

}
