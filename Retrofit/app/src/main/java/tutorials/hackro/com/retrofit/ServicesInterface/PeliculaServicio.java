package tutorials.hackro.com.retrofit.ServicesInterface;

import java.util.List;

import retrofit.http.GET;
import retrofit.Callback;
import tutorials.hackro.com.retrofit.Entidades.Pelicula;

/**
 * Created by hackro on 13/11/15.
 */
public interface PeliculaServicio {

        @GET("/posts")
        void getPeliculas(Callback<List<Pelicula>> callback);



}
