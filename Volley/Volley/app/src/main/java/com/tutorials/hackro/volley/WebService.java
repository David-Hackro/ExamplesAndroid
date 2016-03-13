package com.tutorials.hackro.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hackro on 26/09/15.
 * Esta clase permitira hacer las peticiones para consumir el web service
 */
public class WebService implements Values {//Implementamos una interface que he creado llamada Values

    private Context context;

    public WebService(Context context) {//Cuando creamos la clase,podremos pasar el contexto del Activity
        this.context = context;
    }

    public WebService() {
    }

    //Metodo que realizara una peticion utilizando el metodo POST y con paso de parametros
    public void QueryPost(final String id) {//Los parametros que se ocuparan deberan ser final

        final RequestQueue queue = Volley.newRequestQueue(context);//Debemos pasar el contexto de la actividad(Activity)

        //creamos una instancia de StringRequest
        //Recibe tres parametros
        //1)Metodo[Get-POST]
        //2)URL[Server]Se encuentra en la interface Value
        //3)Respuesta[]
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//Si la respuesta es positiva,si todo salio OK
                try {
                    //La respuesta viene en forma de cadena,por lo cual parseamos esa cadena en un jsonArray
                    JSONArray array = new JSONArray(response);//Cadena de respuesta como parametro
                    for (int i = 0; i < array.length(); i++) {//Recorremos nuestro Arrayson
                        JSONObject object = array.getJSONObject(i);//Ahora solo parseamos cada objeto de ese ArrayJson en un JsonObject
                        Log.e("response: ", object.getString("user"));//object.getString se refiere a que obtendremos un String y colocaremos el nombre del campo
                        //para recibir numeros es con object.getint("id");
                    }
                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {//En el caso de que ocurra un error X
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage() + "");//Se mostrara el error producido
            }
        }) {
            @Override //Esta seccion es para el paso de parametros
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user", id);//Colocaremos en orden los parametros que enviaremos al backend,colocando la key y el value
                return params;
            }
        };
        queue.add(stringRequest);

    }

    public void QueryGet(final String title) {//Pasamos los parametros
        final RequestQueue queue = Volley.newRequestQueue(context);//Creamos instancia de RequestQueue con el contexto como parametro
        String url = "http://www.omdbapi.com/?t="+title+"&y=&plot=short&r=json";//Colocamos la URL,concatenando el parametro
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {//Hacemos la peticion
            @Override
            public void onResponse(String response) {//Se es correcta OK
                Log.e("response: ", response);//Se mostrara en la consola la cadena con los valores obtenidos

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage() + "");//Se mostrara en la consola la cadena de error

            }
        });
        queue.add(stringRequest);

    }


}
