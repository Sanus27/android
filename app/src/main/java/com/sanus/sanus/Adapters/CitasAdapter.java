package com.sanus.sanus.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sanus.sanus.Data.BusquedaDoctor;
import com.sanus.sanus.Data.Citas;
import com.sanus.sanus.R;
import com.sanus.sanus.Data.Citas;
import java.util.List;

/**
 * Created by Mireya on 10/02/2018.
 */

public class CitasAdapter extends RecyclerView.Adapter<CitasAdapter.ViewHolder> {
    Context context;
    List<Citas> busquedaDoctorList;

    public CitasAdapter(Context context, List<Citas> busquedaDoctorList) {
        this.context = context;
        this.busquedaDoctorList = busquedaDoctorList;
    }

    @Override
    public CitasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.citas_item, parent, false);
        return new CitasAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(CitasAdapter.ViewHolder holder, int position) {
        holder.fecha.setText(busquedaDoctorList.get(position).getFecha());
        holder.hora.setText(busquedaDoctorList.get(position).getHora());
        holder.doctor.setText(busquedaDoctorList.get(position).getDoctor());
        holder.hospital.setText(busquedaDoctorList.get(position).getHospital());

    }

    @Override
    public int getItemCount() {
        return busquedaDoctorList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        TextView fecha, hora, doctor, hospital;

        public ViewHolder(View itemView) {

            super(itemView);
            mView = itemView;

            fecha = (TextView) itemView.findViewById(R.id.fecha);
            hora = (TextView) itemView.findViewById(R.id.hora);
            doctor = (TextView) itemView.findViewById(R.id.doctor);
            hospital = (TextView) itemView.findViewById(R.id.hospital);
        }
    }
}
