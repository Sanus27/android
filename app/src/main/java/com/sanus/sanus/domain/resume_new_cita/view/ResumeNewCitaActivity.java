package com.sanus.sanus.domain.resume_new_cita.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sanus.sanus.R;
import com.sanus.sanus.domain.main.view.MainActivity;
import com.sanus.sanus.domain.resume_new_cita.presenter.ResumeNewCitaPresenter;
import com.sanus.sanus.domain.resume_new_cita.presenter.ResumeNewCitaPresenterImpl;

public class ResumeNewCitaActivity extends AppCompatActivity implements ResumeNewCitaView{
    private final String TAG= this.getClass().getSimpleName();
    private ResumeNewCitaPresenter presenter;
    FloatingActionButton cerrarCita, guardarCita;
    String idHospital, idDoctor, fecha, hour;
    TextView nameClinic, directionClinic, nameDoctor, specialty, hora, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resume_new_cita);

        setUpVariable();
        setUpView();
    }

    private void setUpVariable() {
        if (presenter == null){
            presenter = new ResumeNewCitaPresenterImpl(this);
        }
    }

    private void setUpView(){

        idHospital = getIntent().getStringExtra("idHospital");
        idDoctor = getIntent().getStringExtra("idDoctor");
        fecha = getIntent().getStringExtra("fecha");
        hour = getIntent().getStringExtra("hour");
        Log.d(TAG, "idHospital=>" + idHospital + " " + "idDoctor=>" + idDoctor + " " + "fecha=>" +fecha + " " + "hora=>" + hour);

        presenter.viewDataDoctor(idDoctor);
        presenter.viewDataHospital(idHospital);

        nameClinic = findViewById(R.id.nameClinic);
        directionClinic = findViewById(R.id.directionClinic);
        nameDoctor = findViewById(R.id.nameDoctor);
        specialty = findViewById(R.id.specialty);
        date = findViewById(R.id.fechaCite);
        hora = findViewById(R.id.horaCite);

        setDate();
        setHour();

        cerrarCita = findViewById(R.id.closeCita);
        guardarCita = findViewById(R.id.saveCita);

        cerrarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               goActivity();
            }
        });

        guardarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addAppointment(idHospital, idDoctor, fecha, hour);
                goActivity();
            }
        });
    }

    public void goActivity(){
        Intent intent = new Intent(ResumeNewCitaActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    @Override
    public String setDate() {
        date.setText(fecha);
        return String.valueOf(date);
    }

    @Override
    public String setHour() {
        hora.setText(hour);
        return String.valueOf(hora);
    }

    @Override
    public String setNameDoctor(String nameDr) {
        nameDoctor.setText(nameDr);
        return String.valueOf(nameDoctor);
    }

    @Override
    public String setSpecialty(String especialidad) {
        specialty.setText(especialidad);
        return String.valueOf(specialty);
    }

    @Override
    public String setNameHospital(String nameHos) {
        nameClinic.setText(nameHos);
        return String.valueOf(nameClinic);
    }

    @Override
    public String setDirectionHospital(String direction) {
        directionClinic.setText(direction);
        return String.valueOf(directionClinic);
    }
}
