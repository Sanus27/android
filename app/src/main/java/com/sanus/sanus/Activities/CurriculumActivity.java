package com.sanus.sanus.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sanus.sanus.Data.DatosDoctor;
import com.sanus.sanus.R;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

/**
 * Created by Mireya on 12/02/2018.
 */

public class CurriculumActivity extends AppCompatActivity {

    List<DatosDoctor> datosDoctorList;
    private StorageReference storageReference;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth auth;
    TextView nombre1, cv1, especialidad, cedula;
    FloatingActionButton btnCometario;
    ImageView nuevoComentario;
    String user_id;
    private CircleImageView setupImage;
    private Uri mainImageURI = null;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum);
        //setupActionBar();



        //nombre1 = (TextView) findViewById(R.id.tvNombre);
        cv1 = (TextView) findViewById(R.id.tvCv);
        especialidad = (TextView) findViewById(R.id.tvEspecialidad);
        cedula = (TextView) findViewById(R.id.tvCedula);
        btnCometario = (FloatingActionButton) findViewById(R.id.floatinIrComentarios);
        nuevoComentario = (ImageView) findViewById(R.id.floatinNewComent);
        setupImage = findViewById(R.id.setup_image);

        mFirestore = FirebaseFirestore.getInstance();
        initializedData();



        //Bundle params = getIntent().getExtras();
        //params.get("id");
        //Toast.makeText(this, "id Dotor" + get("id"), Toast.LENGTH_SHORT).show();

        btnCometario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurriculumActivity.this, ComentariosDoctorActivity.class);
                startActivity(intent);
            }
        });
        /*nuevoComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurriculumActivity.this, NuevoComentarioDoctor.class);
                startActivity(intent);
            }
        });*/

    }
    private void initializedData() {
        //datosDoctorList = new ArrayList<>();
       mFirestore.collection("doctores").document("MbisakX6endQjlgSdPRqDcAibpY2").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        //Toast.makeText(CurriculumActivity.this, "Data exist", Toast.LENGTH_SHORT).show();
                        String nombre = task.getResult().getString("nombre");
                        String especialidad1 = task.getResult().getString("especialidad");
                        String cedul = task.getResult().getString("cedula");
                        String cv = task.getResult().getString("cv");
                        String image = task.getResult().getString("avatar");
                        especialidad.setText(especialidad1);
                        cedula.setText(cedul);
                        cv1.setText(cv);

                        storeFirestore(null, user_id);
                        mainImageURI = Uri.parse(image);
                        //setupName.setText(nombre);

                        toolbar = (Toolbar) findViewById(R.id.toolbar);
                        setSupportActionBar(toolbar);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        getSupportActionBar().setTitle(nombre);

                        String url = "https://firebasestorage.googleapis.com/v0/b/sanus-27.appspot.com/o/doctores%2Fconcierge-doctor-1.jpg?alt=media&token=bc8a6c07-53a0-4cb4-adc3-57ba05dbfbed";

                        Picasso.with(CurriculumActivity.this).load(url).placeholder(R.drawable.default_image).into(setupImage);
                        //Toast.makeText(CurriculumActivity.this, "url: " + image, Toast.LENGTH_SHORT).show();


                    }else{
                        Toast.makeText(CurriculumActivity.this, "Data doen't exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(CurriculumActivity.this, "FIRESTORE retrieve error "+ error, Toast.LENGTH_SHORT).show();
                }
            }
        });

       /* final DocumentReference docRef = mFirestore.collection("doctores").document("MbisakX6endQjlgSdPRqDcAibpY2");
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
                        String avat = documentSnapshot.getString("avatar");

                       // mainImageURI = Uri.parse(image);
                        //nombre1.setText(nombre);
                        especialidad.setText(esp);
                        cv.setText(cvv);
                        cedula.setText(cedul);
                        //imgAvatarDoc.setText()

                        toolbar = (Toolbar) findViewById(R.id.toolbar);
                        setSupportActionBar(toolbar);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        getSupportActionBar().setTitle(nombre);

                    }else{
                        Log.d(TAG, "No such document");
                    }
                }else {
                    Log.d(TAG, "get failed with", task.getException());
                }
            }
        });*/
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

        }
        return true;
    }

    private void storeFirestore(@NonNull Task<UploadTask.TaskSnapshot> task, String user_name) {
        Uri download_uri;
        if (task != null) {
            download_uri = task.getResult().getDownloadUrl();
        } else {
            download_uri = mainImageURI;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mainImageURI = result.getUri();
                setupImage.setImageURI(mainImageURI);
                //isChanged = true;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }




}
