package com.tutorials.hackro.sendemail;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tutorials.hackro.sendemail.Interfaces.DataValue;
import com.tutorials.hackro.sendemail.Modelos.Email;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hackro on 14/10/15.
 */
public class SendMail implements DataValue{



    private Context context;


    public SendMail(Context context) {
        this.context = context;
    }

    public void EnviarMail(final Email email) {
        final RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, SendMailPHP,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context,"EMAIL Enviado",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "Error al enviar el EMAIL " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("emisor", email.getEmisor());
                params.put("receptor", email.getReceptor());
                params.put("asunto", email.getAsunto());
                params.put("mensaje", email.getMensaje());


                return params;
            }
        };
        queue.add(postRequest);
    }

}
