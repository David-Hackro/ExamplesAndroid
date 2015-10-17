package com.tutorials.hackro.recyclerviewandcardview.Adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorials.hackro.recyclerviewandcardview.Models.Persona;
import com.tutorials.hackro.recyclerviewandcardview.R;

import java.util.List;

/**
 * Created by hackro on 16/10/15.
 */

//Este es el adaptador ara nuestro Recycler y Card View
public class PersonasAdapter extends RecyclerView.Adapter<PersonasAdapter.PersonasViewHolder> {//Heredaos de RecyclerView


    private List<Persona> personastList;//Creamos una lista de objetos[Recuerda quie nuestro modelo es la clase Persona]

                                //Nuestros Resectivos constructores sobrecargados :D[alt+insert]
    //------------------------------------------------------------------------------------------//
    public PersonasAdapter() {
    }

    public PersonasAdapter(List<Persona> personastList) {
        this.personastList = personastList;
    }
    //------------------------------------------------------------------------------------------//


    @Override
    public void onBindViewHolder(PersonasViewHolder personasViewHolder, int i) {
        Persona ci = personastList.get(i);//Obtenemos la persona seleccionada
        personasViewHolder.txtNombre.setText(ci.getNombre());
        personasViewHolder.txtUsuario.setText(ci.getUsuario());
        personasViewHolder.txtTwitter.setText(ci.getTwitter());

        final Persona subject = personastList.get(i);//Obtengo de la lista a la persona que ha sido seleccionada

        //Si necesitas saber que usuario ha sido seleccionadoñ,es mediante el evento onClick :D
        personasViewHolder.card_view.setOnClickListener(new View.OnClickListener() {//[card_view]Se activara al dar click en cualquier parte del CardView,
            @Override
            public void onClick(View v) {
                Log.e("usuario seleccionado ",subject.getNombre());//Muestro los datos en consola del usuario seleccionado
            }
        });

    }

    @Override
    public PersonasViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout_persona, viewGroup, false);//Recuerda colocar el id del cardView
        return new PersonasViewHolder(itemView);
    }

    public static class PersonasViewHolder extends RecyclerView.ViewHolder {

        //Declaramos nuestros elementos de nuestro XML en objetos java :3
        protected TextView txtNombre;
        protected TextView txtUsuario;
        protected TextView txtTwitter;
        protected CardView card_view;

        public PersonasViewHolder(View v) {
            super(v);
            txtNombre = (TextView) v.findViewById(R.id.txtNombre);
            txtUsuario = (TextView) v.findViewById(R.id.txtUsuario);
            txtTwitter = (TextView) v.findViewById(R.id.txtTwitter);
            card_view = (CardView) v.findViewById(R.id.card_view);
        }
    }
            //Metodo que nos retornarta la cantidad de personas en la lista
    @Override
    public int getItemCount() {
        return personastList.size();
    }
}
