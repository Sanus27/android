package com.sanus.sanus.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.sanus.sanus.Activities.NuevaCitaActivity;
import com.sanus.sanus.Adapters.BusquedaDoctorAdapter;
import com.sanus.sanus.Adapters.CitasAdapter;
import com.sanus.sanus.Data.BusquedaDoctor;
import com.sanus.sanus.Data.Citas;
import com.sanus.sanus.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CitasFragment extends Fragment {
    ImageView nuevaCita;
    RecyclerView recyclerView;
    List<Citas> busquedaDoctors;
    CitasAdapter adapter;
    private FirebaseFirestore mFirestore;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_citas, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        mFirestore = FirebaseFirestore.getInstance();

        nuevaCita = (ImageView) view.findViewById(R.id.fabAddCita);
        nuevaCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NuevaCitaActivity.class);
                startActivity(intent);
            }
        });

        initializedData();
        adapter = new CitasAdapter(getActivity().getApplicationContext(), busquedaDoctors);
        recyclerView.setAdapter(adapter);

    }

    private void initializedData() {
        busquedaDoctors = new ArrayList<>();

        mFirestore.collection("citas").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e!=null){
                    Log.d(TAG, "Error: " + e.getMessage());
                }
                for(DocumentChange doc: documentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){

                        String doctor = doc.getDocument().getString("doctor");
                        String hospital = doc.getDocument().getString("hospital");
                        String fecha = doc.getDocument().getString("fecha");
                        String hora = doc.getDocument().getString("hora");
                        //String usuario = doc.getDocument().getString("usuario");

                        busquedaDoctors.add(new Citas(doctor,hospital, fecha, hora));

                        adapter.notifyDataSetChanged();
                        //https://www.youtube.com/watch?v=y3exATaC0kA
                    }
                }
            }
        });
    }

}