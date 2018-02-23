package com.sanus.sanus.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.sanus.sanus.Data.BusquedaDoctor;
import com.sanus.sanus.Data.ComentarioDoctor;
import com.sanus.sanus.Data.DatosDoctor;
import com.sanus.sanus.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Mireya on 12/02/2018.
 */

public class CurriculumActivity extends AppCompatActivity {

    List<DatosDoctor> datosDoctorList;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth auth;
    TextView nombre1, cv, especialidad, cedula;
    FloatingActionButton btnCometario, nuevoComentario;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum);
        setupActionBar();

        nombre1 = (TextView) findViewById(R.id.tvNombre);
        cv = (TextView) findViewById(R.id.tvCv);
        especialidad = (TextView) findViewById(R.id.tvEspecialidad);
        cedula = (TextView) findViewById(R.id.tvCedula);
        btnCometario = (FloatingActionButton) findViewById(R.id.floatinIrComentarios);
        nuevoComentario = (FloatingActionButton) findViewById(R.id.floatinNewComent);
        mFirestore = FirebaseFirestore.getInstance();
        initializedData();

       // Bundle bundle = getIntent().getExtras();
        //String idUs = bundle.getString("id");

        //Toast.makeText(this, "id us" + idUs, Toast.LENGTH_SHORT).show();

        btnCometario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurriculumActivity.this, ComentariosDoctorActivity.class);
                startActivity(intent);
            }
        });
        nuevoComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurriculumActivity.this, NuevoComentarioDoctor.class);
                startActivity(intent);
            }
        });

    }
    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("Sanus");
        }

        }
    private void initializedData() {
        datosDoctorList = new ArrayList<>();
        final DocumentReference docRef = mFirestore.collection("doctores").document("OTYMy6HA1EPTrQHzuV3E");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot != null) {
                        user_id = documentSnapshot.getId();
                        Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                        String nombre = documentSnapshot.getString("nombre");
                        String esp = documentSnapshot.getString("especialidad");
                        String cvv = documentSnapshot.getString("cv");
                        String cedul = documentSnapshot.getString("cedula");
                        nombre1.setText(nombre);
                        especialidad.setText(esp);
                        cv.setText(cvv);
                        cedula.setText(cedul);
                    }else{
                        Log.d(TAG, "No such document");
                    }
                }else {
                    Log.d(TAG, "get failed with", task.getException());
                }
            }
        });
    }




}
