package com.sanus.sanus.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import com.sanus.sanus.Activities.CurriculumActivity;
import com.sanus.sanus.Adapters.BusquedaDoctorAdapter;
import com.sanus.sanus.Data.BusquedaDoctor;
import com.sanus.sanus.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

/**
 * Created by Mireya on 08/02/2018.
 */

public class BusquedaFragment extends Fragment {
    RecyclerView recyclerView;
    List<BusquedaDoctor> busquedaDoctors;
    List<BusquedaDoctor> listAuxiliar;
    BusquedaDoctorAdapter adapter;
    private FirebaseFirestore mFirestore;
    EditText edbuscador;
    private CircleImageView setupAvatar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_buscar, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        edbuscador = (EditText) view.findViewById(R.id.edbuscador);
        setupAvatar = view.findViewById(R.id.setup_image);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        mFirestore = FirebaseFirestore.getInstance();
        initializedData();

        adapter = new BusquedaDoctorAdapter(getActivity().getApplicationContext(), busquedaDoctors);
        recyclerView.setAdapter(adapter);

        edbuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {buscador(""+ s);}

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

    private void initializedData() {
        busquedaDoctors = new ArrayList<>();
        listAuxiliar = new ArrayList<>();

        mFirestore.collection("doctores").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e!=null){
                    Log.d(TAG, "Error: " + e.getMessage());
                }
                for(DocumentChange doc: documentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        //String nombre = doc.getDocument().getString("nombre");
                        String especialidad = doc.getDocument().getString("especialidad");
                        String avatar = doc.getDocument().getString("avatar");

                        mFirestore.collection("doctores").document("MbisakX6endQjlgSdPRqDcAibpY2").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        Toast.makeText(getContext(), "Data exist", Toast.LENGTH_SHORT).show();
                                        String nombre = task.getResult().getString("nombre");
                                        String apellido = task.getResult().getString("apellido");
                                        Toast.makeText(getContext(), "n" + apellido, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getContext(), "Data doen't exist", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                        /*mFirestore.collection("usuarios").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                if (e!=null){
                                    Log.d(TAG, "Error: " + e.getMessage());
                                }
                                for(DocumentChange doc: documentSnapshots.getDocumentChanges()){
                                    if(doc.getType() == DocumentChange.Type.ADDED) {
                                        String nombre = doc.getDocument().getString("nombre");
                                        String apellido = doc.getDocument().getString("apellido");
                                        Toast.makeText(getContext(), "n "+ nombre + apellido, Toast.LENGTH_SHORT).show();
                                    }
                                    }
                            }
                        });*/


                        String user_id = doc.getDocument().getId();
                        //https://www.youtube.com/watch?v=kyGVgrLG3KU
                        busquedaDoctors.add(new BusquedaDoctor( especialidad, avatar));
                        listAuxiliar.add(new BusquedaDoctor( especialidad, avatar));

                        //Picasso.with(getContext()).load(avatar).placeholder(R.drawable.default_image).into(setupAvatar);
                        //Picasso.with(BusquedaFragment.this).load(avatar).placeholder(R.drawable.default_image).into(setupImage);
                        //Toast.makeText(CurriculumActivity.this, "url: " + image, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getContext(), "id: " + avatar, Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();

                    }
                }
            }
        });
    }

    public void  buscador (String texto){
        busquedaDoctors.clear();
        for (int i=0;i<listAuxiliar.size(); i++){
            if(listAuxiliar.get(i).getEspecialidad().toLowerCase().contains(texto.toLowerCase())){
                busquedaDoctors.add(listAuxiliar.get(i));
                Log.d(TAG, "buscador: " + listAuxiliar);
            }
        }
        adapter.notifyDataSetChanged();
    }

}

