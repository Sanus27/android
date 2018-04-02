package com.sanus.sanus.domain.curriculum.interactor;

import android.widget.RatingBar;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public interface CurriculumInteractor {
    void init(String idDoct, final RatingBar ratingBar, final TextView especialidad, final TextView cedula, final TextView cv);
    void showImage(String idImage, CircleImageView image);
    void insertContact(String idUser, String idDoct);
    void verifyContact(final String idUser, final String idDoct);
}

