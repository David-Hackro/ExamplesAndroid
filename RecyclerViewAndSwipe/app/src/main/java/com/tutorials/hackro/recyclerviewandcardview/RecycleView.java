package com.tutorials.hackro.recyclerviewandcardview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.tutorials.hackro.recyclerviewandcardview.Adapters.PersonasAdapter;
import com.tutorials.hackro.recyclerviewandcardview.Models.Persona;

import java.util.ArrayList;
import java.util.List;

public class RecycleView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        /*1: Debemos crear el activity[Este activity] que contendra el ReciclerView
          2: Debemos agregar las dependencias al archivo gradle
          3: Agregamos el reciclerView en el XML de este Activity
          4: Creamos un archivo XML --> Layout XML File que sera nuestro CardView \(°u°)/
          5: Creamos una clase modelo,llamada Persona
          6: Creamos un Adaptador para nuestro RecyclerView
          7: Sigueme en Twitter @DavidHackro y comparte tu conocimiento
        */
        RecyclerView recList = (RecyclerView) findViewById(R.id.cardListPersonas);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        final List<Persona> personas = new ArrayList<Persona>();
        // nombre, usuario,  twitter
        personas.add(new Persona("David Tutoriales", "Hackro", "@DavidHackro"));
        personas.add(new Persona("Paulina Mendoza", "Pauu", "@PauMendoza"));
        personas.add(new Persona("Miriam Gomez", "MiriMiri", "@MiriGmz"));
        personas.add(new Persona("Saul Martinez", "SaulMTZ", "@flash34"));
        personas.add(new Persona("Jose Gutierrez", "Gut898", "@GutJose"));

        final PersonasAdapter ca = new PersonasAdapter(personas);
        recList.setAdapter(ca);

// agregamos el listener
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(recList,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {

                            @Override
                            public boolean canSwipeLeft(int position) {
                                return true;
                            }

                            @Override
                            public boolean canSwipeRight(int position) {
                                return true;
                            }

                            //Accion al deslizar a la izquierda
                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    personas.remove(position);
                                    ca.notifyItemRemoved(position);

                                }
                                ca.notifyDataSetChanged();
                            }
                            //Accion al deslizar a la derecha
                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    personas.remove(position);
                                    ca.notifyItemRemoved(position);
                                }
                                ca.notifyDataSetChanged();
                            }
                        });

        recList.addOnItemTouchListener(swipeTouchListener);

    }


}
