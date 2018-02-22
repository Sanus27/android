package com.sanus.sanus.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanus.sanus.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NuevoComentarioDoctor extends AppCompatActivity {
    RatingBar ratingBar;
    ImageView guardarComentario;
    EditText edNuevoComentario;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private int dia,mes, anio;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_comentario_doctor2);

        edNuevoComentario = (EditText) findViewById(R.id.edComentario);
        guardarComentario = (ImageView) findViewById(R.id.btnGuardarComentario);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.getRating();

        mFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            id=user.getUid();
        }

        final java.util.Calendar calendar = Calendar.getInstance();
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        anio = calendar.get(Calendar.YEAR);


        guardarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cometario = edNuevoComentario.getText().toString();
                float valoracion = ratingBar.getRating();
                String fechaA = (dia + "/" + (mes+1) + "/" +anio);


                Map<String, String> userMap = new HashMap<>();
                userMap.put("comentario", cometario);
                userMap.put("fecha", fechaA);
                userMap.put("usuario", id);

                mFirestore.collection("comentarios").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent intent = new Intent(getApplicationContext(), CurriculumActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(NuevoComentarioDoctor.this, "error " + e, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}

