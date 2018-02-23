package com.sanus.sanus.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanus.sanus.Activities.CrearCuentaActivity;
import com.sanus.sanus.Activities.CurriculumActivity;
import com.sanus.sanus.Activities.MainActivity;
import com.sanus.sanus.Data.BusquedaDoctor;
import com.sanus.sanus.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mireya on 09/02/2018.
 */

public class BusquedaDoctorAdapter extends RecyclerView.Adapter<BusquedaDoctorAdapter.ViewHolder> implements SearchView.OnQueryTextListener {
    Context context;
    List<BusquedaDoctor> busquedaDoctorList;

    public BusquedaDoctorAdapter(Context context, List<BusquedaDoctor> busquedaDoctorList){
        this.context = context;
        this.busquedaDoctorList = busquedaDoctorList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buscar_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.nombre.setText(busquedaDoctorList.get(position).getNombre());
        holder.especialidad.setText(busquedaDoctorList.get(position).getEspecialidad());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Bundle params = getIntent().getExtras();
                //Toast.makeText(this, "hi" + params.get("id"), Toast.LENGTH_SHORT).show();
                /*Bundle params = new Bundle();
                params.get("id");
                holder.mView.saveHierarchyState(Bundle);*/
                //Toast.makeText(context, "hi: " + params, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, CurriculumActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                //https://www.youtube.com/watch?v=ZXoGG2XTjzU
            }


        });
    }

    @Override
    public int getItemCount() {
        return busquedaDoctorList.size();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {return false;}

    public void setFilter(List<BusquedaDoctor> busquedaDoctorList){
        busquedaDoctorList = new ArrayList<>();
        busquedaDoctorList.addAll(busquedaDoctorList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView nombre, especialidad;
        CardView cardView;
        ImageView imageView;
        EditText edbuscador;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            //cardView = (CardView) itemView.findViewById(R.id.cardView);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            especialidad = (TextView) itemView.findViewById(R.id.especialidad);
            imageView = (ImageView) itemView.findViewById(R.id.avatar);
            //buscador
            edbuscador = (EditText) itemView.findViewById(R.id.edbuscador);
        }
    }

}
