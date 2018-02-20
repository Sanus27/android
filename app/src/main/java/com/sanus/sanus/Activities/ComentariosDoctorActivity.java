package com.sanus.sanus.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.sanus.sanus.Adapters.CitasAdapter;
import com.sanus.sanus.Adapters.ComentarioDoctorAdapter;
import com.sanus.sanus.Data.ComentarioDoctor;
import com.sanus.sanus.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ComentariosDoctorActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ComentarioDoctor> comentarioDoctorList;
    ComentarioDoctorAdapter adapter;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios_doctor);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        mFirestore = FirebaseFirestore.getInstance();

        initializedData();
        adapter = new ComentarioDoctorAdapter(getApplicationContext(), comentarioDoctorList);
        recyclerView.setAdapter(adapter);
    }

    private void initializedData() {
        comentarioDoctorList = new ArrayList<>();
        mFirestore.collection("comentarios").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e!=null){
                    Log.d(TAG, "Error: " + e.getMessage());
                }
                for(DocumentChange doc: documentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        String usuario = doc.getDocument().getString("usuario");
                        String fecha = doc.getDocument().getString("fecha");
                        String comentario = doc.getDocument().getString("comentario");
                        comentarioDoctorList.add(new ComentarioDoctor(usuario,comentario,fecha));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

}