package hackro.com.retrofit;


import java.util.List;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


/**
 * Created by hackro on 10/01/16.
 */
public interface InterfacePost {


    @FormUrlEncoded
    @POST("selectId.php")
    Call<Persona> users(@Field("id_usuario")String  id);

}
