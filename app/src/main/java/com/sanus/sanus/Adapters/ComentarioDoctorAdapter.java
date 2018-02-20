package com.sanus.sanus.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sanus.sanus.Data.ComentarioDoctor;
import com.sanus.sanus.R;

import java.util.List;

/**
 * Created by Mireya on 16/02/2018.
 */

public class ComentarioDoctorAdapter extends RecyclerView.Adapter<ComentarioDoctorAdapter.ViewHolder> {
    Context context;
    List<ComentarioDoctor> comentarioDoctorList;

    public ComentarioDoctorAdapter(Context context, List<ComentarioDoctor> comentarioDoctorList){
        this.context = context;
        this.comentarioDoctorList = comentarioDoctorList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comentario_item, parent, false);
        return new ComentarioDoctorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.comentario.setText(comentarioDoctorList.get(position).getCometario());
        holder.usuario.setText(comentarioDoctorList.get(position).getUsuario());
        holder.fecha.setText(comentarioDoctorList.get(position).getFecha());
    }

    @Override
    public int getItemCount() {
        return comentarioDoctorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView fecha, usuario, comentario;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            fecha = (TextView) itemView.findViewById(R.id.tvFecha);
            usuario = (TextView) itemView.findViewById(R.id.tvUsuario);
            comentario = (TextView) itemView.findViewById(R.id.tvComentario);
        }
    }
}
