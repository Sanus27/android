package com.sanus.sanus.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.sanus.sanus.Adapters.ComentarioDoctorAdapter;
import com.sanus.sanus.Data.ComentarioDoctor;
import com.sanus.sanus.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ComentariosDoctorActivity extends AppCompatActivity {
    RatingBar ratingBar;
    ImageView guardarComentario;
    EditText edNuevoComentario;
    RecyclerView recyclerView;
    List<ComentarioDoctor> comentarioDoctorList;
    ComentarioDoctorAdapter adapter;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth auth;
    private Toolbar toolbar;
    private int dia,mes, anio;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios_doctor);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        edNuevoComentario = (EditText) findViewById(R.id.edComentario);
        guardarComentario = (ImageView) findViewById(R.id.btnGuardarComentario);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.getRating();

        mFirestore = FirebaseFirestore.getInstance();

        initializedData();
        adapter = new ComentarioDoctorAdapter(getApplicationContext(), comentarioDoctorList);
        recyclerView.setAdapter(adapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Comentarios");


        auth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            id=user.getUid();
        }

        Toast.makeText(this, "my Id: " + id, Toast.LENGTH_SHORT).show();

        final java.util.Calendar calendar = Calendar.getInstance();
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        anio = calendar.get(Calendar.YEAR);


        guardarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cometario = edNuevoComentario.getText().toString();
                float valoracion = (ratingBar.getRating())*20;
                int valoracionBar = (int) valoracion;
                String fechaA = (dia + "/" + (mes+1) + "/" +anio);

                Map<String, String> userMap = new HashMap<>();
                userMap.put("comentario", cometario);
                userMap.put("fecha", fechaA);
                userMap.put("usuario", id);
                userMap.put("calificacion", String.valueOf(valoracionBar));
                userMap.put("doctor", "QS6Qx1mmjtYhK1Aktmwp1c3nbuD2");
                mFirestore.collection("comentarios").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent intent = new Intent(getApplicationContext(), ComentariosDoctorActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ComentariosDoctorActivity.this, "error " + e, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


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
                        String calificacion1 = doc.getDocument().getString("calificacion");
                        comentarioDoctorList.add(new ComentarioDoctor(usuario,comentario,fecha, calificacion1));

                       //Toast.makeText(ComentariosDoctorActivity.this, "cal: " + calificacion1, Toast.LENGTH_SHORT).show();
                        /*if (Integer.parseInt(calificacion1) == 20){
                            Toast.makeText(ComentariosDoctorActivity.this, "20", Toast.LENGTH_SHORT).show();
                            ratingBar.setRating(1);

                        }
                        if (Integer.parseInt(calificacion1) == 100){
                            Toast.makeText(ComentariosDoctorActivity.this, "100", Toast.LENGTH_SHORT).show();
                            ratingBar.setRating(5);

                        }*/

                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this, CurriculumActivity.class);
                startActivity(intent);
                finish();
                break;

        }
        return true;
    }

}