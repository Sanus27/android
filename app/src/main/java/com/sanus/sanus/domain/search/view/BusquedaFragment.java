package com.sanus.sanus.domain.search.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import com.sanus.sanus.R;
import com.sanus.sanus.domain.search.adapter.BusquedaDoctorAdapter;
import com.sanus.sanus.domain.search.data.BusquedaDoctor;

import java.util.ArrayList;
import java.util.List;


import static android.content.ContentValues.TAG;


public class BusquedaFragment extends Fragment {

    public static String IDENTIFIER = "SEARCH_FRAGMENT";

    private RecyclerView recyclerView;
    private List<BusquedaDoctor> busquedaDoctors;
    private List<BusquedaDoctor> listAuxiliar;
    private BusquedaDoctorAdapter adapter;
    private FirebaseFirestore mFirestore;
    private EditText edbuscador;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_buscar, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        edbuscador = view.findViewById(R.id.edbuscador);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        mFirestore = FirebaseFirestore.getInstance();
        initializedData();

        adapter = new BusquedaDoctorAdapter(getActivity().getApplicationContext(), busquedaDoctors);
        recyclerView.setAdapter(adapter);

        edbuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {buscador(""+ s);}

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

    private void initializedData() {
        busquedaDoctors = new ArrayList<>();
        listAuxiliar = new ArrayList<>();

       mFirestore.collection("doctores").addSnapshotListener(new EventListener<QuerySnapshot>() {
           @Override
           public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
               if (e!=null){
                   Log.d(TAG, "Error: " + e.getMessage());
               }
               for(DocumentChange doc: documentSnapshots.getDocumentChanges()){
                   if(doc.getType() == DocumentChange.Type.ADDED){
                       String nombre = doc.getDocument().getString("nombre");
                       String especialidad = doc.getDocument().getString("especialidad");

                       //String user_id = doc.getDocument().getId();
                       //busquedaDoctors.add(new BusquedaDoctor(nombre, especialidad).withId(user_id));
                       //https://www.youtube.com/watch?v=kyGVgrLG3KU
                       busquedaDoctors.add(new BusquedaDoctor(nombre, especialidad));
                       listAuxiliar.add(new BusquedaDoctor(nombre, especialidad));

                       adapter.notifyDataSetChanged();
                   }
               }
           }
       });
    }

    public void  buscador (String texto){
        busquedaDoctors.clear();
        for (int i=0;i<listAuxiliar.size(); i++){
            if(listAuxiliar.get(i).getEspecialidad().toLowerCase().contains(texto.toLowerCase())){
                busquedaDoctors.add(listAuxiliar.get(i));
                Log.d(TAG, "buscador: " + listAuxiliar);
            }
        }
        adapter.notifyDataSetChanged();
    }

}
