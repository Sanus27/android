package com.sanus.sanus.domain.search.interactor;

import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.sanus.sanus.domain.search.data.BusquedaDoctor;
import com.sanus.sanus.domain.search.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

public class SearchInteractorImpl implements SearchInteractor{
    private SearchPresenter presenter;
    private final String TAG= this.getClass().getSimpleName();
    private List<BusquedaDoctor> busquedaDoctors =  new ArrayList<>();
    private List<BusquedaDoctor> listAuxiliar = new ArrayList<>();

    public SearchInteractorImpl(SearchPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void init() {

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        busquedaDoctors = listAuxiliar;

        mFirestore.collection("doctores").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e!=null){
                    Log.d(TAG, "Error: " + e.getMessage());
                }
                for(DocumentChange doc: documentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        String nombre = doc.getDocument().getString("nombre");
                        String especialidad = doc.getDocument().getString("especialidad");

                        //String user_id = doc.getDocument().getId();
                        //busquedaDoctors.add(new BusquedaDoctor(nombre, especialidad).withId(user_id));
                        //https://www.youtube.com/watch?v=kyGVgrLG3KU
                        busquedaDoctors.add(new BusquedaDoctor(nombre, especialidad));
                        //listAuxiliar.add(new BusquedaDoctor(nombre, especialidad));

                        presenter.setDataAdapter(busquedaDoctors);

                    }
                }
            }
        });
    }

    @Override
    public void buscador(String texto) {

        busquedaDoctors.clear();
        for (int i=0;i<listAuxiliar.size(); i++){
            if(listAuxiliar.get(i).getEspecialidad().toLowerCase().contains(texto.toLowerCase())){
                busquedaDoctors.add(listAuxiliar.get(i));
                Log.d(TAG, "buscador: " + listAuxiliar);
            }
        }

        presenter.setDataAdapter(listAuxiliar);
    }
}
