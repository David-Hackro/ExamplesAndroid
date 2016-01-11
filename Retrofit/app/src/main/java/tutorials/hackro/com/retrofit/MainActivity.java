package tutorials.hackro.com.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import retrofit.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public Repository getRepositorySync(String owner, String name) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .build();

        GithubRepositoryService service = restAdapter.create(GithubRepositoryService.class);

        return service.repository(owner, name);
    }
}
