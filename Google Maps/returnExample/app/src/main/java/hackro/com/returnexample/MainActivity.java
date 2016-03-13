package hackro.com.returnexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("example ",example()+"");
    }


    public static boolean example()
    {
        int i = 10;
        boolean result = false;

        if(i > 5)
        {
            if(i > 9){
                if(i == 10) {
                    return true;
                }
                return true;
            }
            return true;
        }
        else
        {
            return false;
        }

    }


}
