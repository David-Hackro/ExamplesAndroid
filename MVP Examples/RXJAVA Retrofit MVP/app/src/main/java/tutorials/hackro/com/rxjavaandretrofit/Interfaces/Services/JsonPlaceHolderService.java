package tutorials.hackro.com.rxjavaandretrofit.Interfaces.Services;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;
import tutorials.hackro.com.rxjavaandretrofit.Models.Photo;

/**
 * Created by david on 9/08/16.
 */
public interface JsonPlaceHolderService {

    //http://jsonplaceholder.typicode.com/photos

    @GET("photos")
    Observable<List<Photo>> getAllPhotos();
}
