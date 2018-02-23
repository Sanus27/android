package com.sanus.sanus.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sanus.sanus.R;

import java.util.Calendar;

public class NuevaCitaActivity extends AppCompatActivity implements View.OnClickListener {

    TextView fecha, horas;
    ImageView btnFecha, btnHora;
    private int dia,mes, anio, hora, minutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_cita);

        btnFecha = (ImageView) findViewById(R.id.btnFecha);
        btnHora = (ImageView) findViewById(R.id.btnHora);
        fecha = (TextView) findViewById(R.id.edfecha);
        horas = (TextView) findViewById(R.id.edHora);
        btnHora.setOnClickListener(this);
        btnFecha.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v==btnFecha){
            final Calendar calendar = Calendar.getInstance();
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            anio = calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    fecha.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                }
            }, anio, mes, dia);
            datePickerDialog.show();
        }
        if(v==btnHora){
            final Calendar calendar = Calendar.getInstance();
            hora = calendar.get(Calendar.HOUR_OF_DAY);
            minutos = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    horas.setText(hourOfDay + ":" + minute);
                }
            }, hora, minutos,false);
            timePickerDialog.show();
        }

    }
}
