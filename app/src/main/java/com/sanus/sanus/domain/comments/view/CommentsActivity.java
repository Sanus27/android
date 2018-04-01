package com.sanus.sanus.domain.comments.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanus.sanus.R;
import com.sanus.sanus.domain.comments.adapter.CommentsDoctorAdapter;
import com.sanus.sanus.domain.comments.data.CommentsDoctor;
import com.sanus.sanus.domain.comments.presenter.CommentsPresenter;
import com.sanus.sanus.domain.comments.presenter.CommentsPresenterImpl;
import com.sanus.sanus.domain.curriculum.view.CurriculumActivity;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentsActivity extends AppCompatActivity implements CommentsView{
    private CommentsPresenter presenter;
    private RatingBar ratingBar;
    private EditText edNuevoComentario;
    private String idUser;
    private RecyclerView recyclerView;
    private CommentsDoctorAdapter adapter;
    private String idDoct;
    private String hour;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coments);

        setUpVariable();
        setUpView();
        presenter.viewComents(idDoct);
    }

    private void setUpVariable() {
        if(presenter == null){
            presenter = new CommentsPresenterImpl(this);
        }
    }

    private void setUpView() {
        idDoct = getIntent().getStringExtra("idDoctor");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_comments);

        edNuevoComentario = findViewById(R.id.edComentario);
        FloatingActionButton guardarComentario = findViewById(R.id.btnGuardarComentario);
        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.getRating();
        guardarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComments();
                edNuevoComentario.getText().clear();
                ratingBar.setRating(0);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this, CurriculumActivity.class);
                intent.putExtra("idDoctor", idDoct);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
                break;
        }
        return true;
    }



    @Override
    public void sendComments() {
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        final FirebaseFirestore mFirestoreDoct = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            idUser = user.getUid();
        }

        getDate();

        String comments = edNuevoComentario.getText().toString();
        float valoracion = (ratingBar.getRating()) * 20;
        final int valoracionDoc = (int) valoracion;

        Map<String, String> commentMap = new HashMap<>();
        commentMap.put("comentario", comments);
        commentMap.put("fecha", date);
        commentMap.put("calificacion", String.valueOf(valoracionDoc));
        commentMap.put("doctor", idDoct);
        commentMap.put("usuario", idUser);
        commentMap.put("hora", hour);


        mFirestore.collection("comentarios").add(commentMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                mFirestoreDoct.collection("doctores").document(idDoct).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                String cv = task.getResult().getString("cv");
                                String cedula = task.getResult().getString("cedula");
                                String especialida = task.getResult().getString("especialidad");
                                String hospital = task.getResult().getString("hospital");
                                String comentarios = task.getResult().getString("comentario");
                                String calificacion = task.getResult().getString("calificacion");
                                Integer com = Integer.parseInt(comentarios);
                                Integer califi = Integer.parseInt(calificacion);

                                int totalCalif = califi + valoracionDoc;
                                int totalComen = com + 1;

                                Map<String, String> doctMap = new HashMap<>();
                                doctMap.put("comentario", String.valueOf(totalComen));
                                doctMap.put("calificacion", String.valueOf(totalCalif));
                                doctMap.put("cv", cv);
                                doctMap.put("cedula", cedula);
                                doctMap.put("especialidad", especialida);
                                doctMap.put("hospital", hospital);

                                mFirestoreDoct.collection("doctores").document(idDoct).set(doctMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        presenter.viewComents(idDoct);
                                    }
                                });
                            }
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CommentsActivity.this, "error " + e, Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void setDataAdapter(List<CommentsDoctor> commentsDoctorList) {
        CommentsDoctorAdapter commentsDoctorAdapter = new CommentsDoctorAdapter(getApplicationContext(), commentsDoctorList, presenter);
        recyclerView.setAdapter(commentsDoctorAdapter);
        commentsDoctorAdapter.notifyDataSetChanged();
    }

    @Override
    public void getDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss:SS");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        hour = simpleTimeFormat.format(calendar.getTime());
        date = simpleDateFormat.format(calendar.getTime());
    }

}
