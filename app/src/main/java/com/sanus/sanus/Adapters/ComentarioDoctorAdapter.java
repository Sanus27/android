package com.sanus.sanus.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.comentario.setText(comentarioDoctorList.get(position).getCometario());
        holder.usuario.setText(comentarioDoctorList.get(position).getUsuario());
        holder.fecha.setText(comentarioDoctorList.get(position).getFecha());
        holder.calificacion.setRating(3);
        //holder.calificacion.setRating(Float.parseFloat(comentarioDoctorList.get(position).getCalificacion()));
        //Toast.makeText(context, " cal " + holder.calificacion, Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return comentarioDoctorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView fecha, usuario, comentario;
        RatingBar calificacion;
        String cal;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            fecha = (TextView) itemView.findViewById(R.id.tvFecha);
            usuario = (TextView) itemView.findViewById(R.id.tvUsuario);
            comentario = (TextView) itemView.findViewById(R.id.tvComentario);
            calificacion = (RatingBar) itemView.findViewById(R.id.ratingBarVal);
        }
    }
}
