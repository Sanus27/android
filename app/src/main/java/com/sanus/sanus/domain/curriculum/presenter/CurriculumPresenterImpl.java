package com.sanus.sanus.domain.curriculum.presenter;

import android.content.Context;

import com.sanus.sanus.domain.curriculum.interactor.CurriculumInteractor;
import com.sanus.sanus.domain.curriculum.interactor.CurriculumInteractorImpl;
import com.sanus.sanus.domain.curriculum.view.CurriculumView;

import de.hdodenhof.circleimageview.CircleImageView;

public class CurriculumPresenterImpl implements CurriculumPresenter {
    private CurriculumView view;
    private CurriculumInteractor interactor;

    public CurriculumPresenterImpl(CurriculumView view) {
        this.view = view;
        interactor = new CurriculumInteractorImpl(this);
    }

    @Override
    public void showImage(String idImage, Context context, CircleImageView image) {
        interactor.showImage(idImage, context, image);
    }
}
