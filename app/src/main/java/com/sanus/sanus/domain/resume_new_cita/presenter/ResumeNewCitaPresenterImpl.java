package com.sanus.sanus.domain.resume_new_cita.presenter;


import com.sanus.sanus.domain.resume_new_cita.interactor.ResumeNewCitaInteractor;
import com.sanus.sanus.domain.resume_new_cita.interactor.ResumeNewCitaInteractorImpl;
import com.sanus.sanus.domain.resume_new_cita.view.ResumeNewCitaView;

public class ResumeNewCitaPresenterImpl implements ResumeNewCitaPresenter{
    private ResumeNewCitaView view;
    private ResumeNewCitaInteractor interactor;

    public ResumeNewCitaPresenterImpl(ResumeNewCitaView view){
        this.view = view;
        interactor = new ResumeNewCitaInteractorImpl(this);
    }

    @Override
    public void addAppointment(String idHospital, String idDoctor, String fecha, String hora) {
        interactor.addAppointment(idHospital, idDoctor, fecha, hora);
    }

    @Override
    public void viewDataDoctor(String idDoctor) {
        interactor.viewDataDoctor(idDoctor);
    }

    @Override
    public void viewDataHospital(String idHospital) {
        interactor.viewDataHospital(idHospital);
    }

    @Override
    public String setDate() {
        return view.setDate();
    }

    @Override
    public String setHour() {
        return view.setHour();
    }

    @Override
    public String setNameDoctor(String nameDr) {
        return view.setNameDoctor(nameDr);
    }

    @Override
    public String setSpecialty(String especialidad) {
        return view.setSpecialty(especialidad);
    }

    @Override
    public String setNameHospital(String nameHos) {
        return view.setNameHospital(nameHos);
    }

    @Override
    public String setDirectionHospital(String direction) {
        return view.setDirectionHospital(direction);
    }
}
