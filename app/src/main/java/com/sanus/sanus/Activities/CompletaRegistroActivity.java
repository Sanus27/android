package com.sanus.sanus.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sanus.sanus.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompletaRegistroActivity extends AppCompatActivity {
    EditText nombre, apellido;
    Button masculino, femenino, guardar;
    Spinner spinnerEdad;
    private CircleImageView setupImage;
    private Uri mainImageURI = null;

    private StorageReference storageReference;
    private FirebaseAuth auth;
    private FirebaseFirestore mFirestore;

    private String edadPosition;
    private String sex = "Masculino";
    String[] items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completa_registro);

        storageReference = FirebaseStorage.getInstance().getReference();
        mFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        nombre = (EditText) findViewById(R.id.edNombre);
        apellido = (EditText) findViewById(R.id.edApellido);
        masculino = (Button) findViewById(R.id.btnMasculino);
        femenino = (Button) findViewById(R.id.btnFemenino);
        spinnerEdad = (Spinner) findViewById(R.id.spinnerEdad);
        guardar = (Button) findViewById(R.id.btnGuardar);

        setupImage = findViewById(R.id.setup_image);

        masculino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexo(1);
                masculino.setBackgroundColor(getResources().getColor(R.color.black));
                masculino.setTextColor(getResources().getColor(R.color.text));
                femenino.setBackgroundColor(getResources().getColor(R.color.text));
                femenino.setTextColor(getResources().getColor(R.color.black));
            }
        });

        femenino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexo(2);
                femenino.setBackgroundColor(getResources().getColor(R.color.black));
                femenino.setTextColor(getResources().getColor(R.color.text));
                masculino.setBackgroundColor(getResources().getColor(R.color.text));
                masculino.setTextColor(getResources().getColor(R.color.black));

            }
        });

        setupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(CompletaRegistroActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(CompletaRegistroActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(CompletaRegistroActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        BringImagePicker();
                    }
                } else {
                    BringImagePicker();
                }
            }
        });



        //obtener edad
    items = getResources().getStringArray(R.array.Edad);
    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerEdad.setAdapter(adapter);
        spinnerEdad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            edadPosition = items[position];}
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    });



    //guardando datos en firestore
        guardar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            String id = auth.getCurrentUser().getUid();
            String name = nombre.getText().toString();
            String apelli = apellido.getText().toString();
            String edaD = edadPosition;
            masculino.getText().toString();
            femenino.getText().toString();

            StorageReference image_path = storageReference.child("avatar").child(id);
            image_path.putFile(mainImageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()){
                        Uri download_uri = task.getResult().getDownloadUrl();
                        Toast.makeText(CompletaRegistroActivity.this, "The Image is Uploaded", Toast.LENGTH_LONG).show();
                    }else {
                        String error = task.getException().getMessage();
                        Toast.makeText(CompletaRegistroActivity.this, "Error" + error, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            final Map<String, String> userMap = new HashMap<>();
            userMap.put("tipo", "Paciente");
            userMap.put("nombre", name);
            userMap.put("apellido", apelli);
            userMap.put("edad", edaD);
            userMap.put("sexo", sex);
            userMap.put("avatar", String.valueOf(image_path));

            mFirestore.collection("usuarios").document(id).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(CompletaRegistroActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CompletaRegistroActivity.this, MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CompletaRegistroActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
        });
    }

    public void sexo(int num){
        if(num==1){sex = "Masculino";}
        if(num==2){sex= "Femenino";}
    }
    private void BringImagePicker() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(CompletaRegistroActivity.this);
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
