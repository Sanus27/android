package com.sanus.sanus.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.sanus.sanus.Adapters.BusquedaDoctorAdapter;
import com.sanus.sanus.Data.BusquedaDoctor;
import com.sanus.sanus.R;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Mireya on 08/02/2018.
 */

public class BusquedaFragment extends Fragment {
    RecyclerView recyclerView;
    List<BusquedaDoctor> busquedaDoctors;
    List<BusquedaDoctor> listAuxiliar;
    BusquedaDoctorAdapter adapter;
    private FirebaseFirestore mFirestore;
    EditText edbuscador;

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
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        edbuscador = (EditText) view.findViewById(R.id.edbuscador);

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


                       String user_id = doc.getDocument().getId();
                       Bundle miBundle = new Bundle();
                       miBundle.putString("id",user_id);

                        mFirestore.collection("doctores").document(user_id);

                       //String user_id = doc.getDocument().getId();
                       //busquedaDoctors.add(new BusquedaDoctor(nombre, especialidad).withId(user_id));
                       //https://www.youtube.com/watch?v=kyGVgrLG3KU
                       busquedaDoctors.add(new BusquedaDoctor(nombre, especialidad));
                       listAuxiliar.add(new BusquedaDoctor(nombre, especialidad));
                       //Toast.makeText(getContext(), "id: " + user_id, Toast.LENGTH_SHORT).show();

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

