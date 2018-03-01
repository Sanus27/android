package com.sanus.sanus.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    private CircleImageView setupImage;

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
            final Map<String, String> userMap = new HashMap<>();
            userMap.put("tipo", "Paciente");
            userMap.put("nombre", name);
            userMap.put("apellido", apelli);
            userMap.put("edad", edaD);
            userMap.put("sexo", sex);
            userMap.put("avatar","avatar.png");


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

}
