package com.sanus.sanus.domain.resume_new_cita.presenter;

import com.sanus.sanus.domain.resume_new_cita.view.ResumeNewCitaView;

public interface ResumeNewCitaPresenter extends ResumeNewCitaView {
    void addAppointment(String idHospital, String idDoctor, String fecha, String hora, String idDocument);
    void viewDataDoctor(String idDoctor);
    void viewDataHospital(String idHospital);
    void deleteAppointment(String idDocument);
    void showImage(String idImage);
    void deleteAppointmentOccupied(String idDoctor, String idFecha, String idHora);
}
