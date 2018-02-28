package com.sanus.sanus.Activities;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sanus.sanus.R;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class CompletaRegistroActivity extends AppCompatActivity {
    EditText nombre, apellido;
    Button masculino, femenino, guardar;
    Spinner spinnerEdad;
    //ImageView imgavatar, imgCamara, imgSave;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth auth;
    private String edadPosition;
    private String sex = "Masculino";
    private CircleImageView setupImage;
    //private Uri filePath;
    //private final int PICK_IMAGE_REQUEST = 234;
    private StorageReference storageReference;
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completa_registro);

        storageReference = FirebaseStorage.getInstance().getReference();
        nombre = (EditText) findViewById(R.id.edNombre);
        apellido = (EditText) findViewById(R.id.edApellido);
        masculino = (Button) findViewById(R.id.btnMasculino);
        femenino = (Button) findViewById(R.id.btnFemenino);
        spinnerEdad = (Spinner) findViewById(R.id.spinnerEdad);
        guardar = (Button) findViewById(R.id.btnGuardar);

        setupImage = findViewById(R.id.setup_image);

        /*imgavatar =(ImageView) findViewById(R.id.imgHeader);
        imgCamara = (ImageView) findViewById(R.id.imgCamera);
        imgSave = (ImageView) findViewById(R.id.imgSave);*/

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
                    if(ContextCompat.checkSelfPermission(CompletaRegistroActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(CompletaRegistroActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(CompletaRegistroActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }else{
                        Toast.makeText(CompletaRegistroActivity.this, "Permission ok", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


       /* imgCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });*/
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

        mFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

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
                Map<String, String> userMap = new HashMap<>();
                userMap.put("tipo", "Paciente");
                userMap.put("nombre", name);
                userMap.put("apellido", apelli);
                userMap.put("edad", edaD);
                userMap.put("sexo", sex);
                userMap.put("avatar", "avatar.png");

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

   /* private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select"), PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && requestCode == RESULT_OK
                && data != null && data.getData() != null){
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgavatar.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClick(View view) {

        if (view == imgCamara){showFileChooser();
        }else if (view == imgSave){uploadFile();}
    }

    private void uploadFile(){
        if(filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Subiendo");
            progressDialog.show();
            StorageReference fileRef = storageReference.child("images/profile.jpg");
            fileRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            progressDialog.dismiss();
                            Toast.makeText(CompletaRegistroActivity.this, "completo", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(CompletaRegistroActivity.this, "error" , Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage(((int) progress)+ "% subiendo");
                }
            })
            ;
        }else{

        }
    }*/

    public void sexo(int num){
        if(num==1){
            sex = "Masculino";
        }
        if(num==2){
            sex= "Femenino";
        }
    }



}
