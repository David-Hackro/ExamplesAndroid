package hackro.com.retrofit;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class MainActivity extends AppCompatActivity {
    static String url = "http://10.0.3.2/CRUD/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)//
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfacePost service = retrofit.create(InterfacePost.class);

        Call<Persona> call = service.users("15");

        call.enqueue(new Callback<Persona>() {
            @Override
            public void onResponse(Response<Persona> response, Retrofit retrofit) {
                Log.e("onResponse ",response.body().toString());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("onFailure ",t.getMessage());

            }
        });

    }
}
