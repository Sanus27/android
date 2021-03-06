package com.sanus.sanus.domain.comments.presenter;

import com.sanus.sanus.domain.comments.view.CommentsView;

import de.hdodenhof.circleimageview.CircleImageView;

public interface CommentsPresenter extends CommentsView {
    void viewComents(String idDoc);
    void showImage(String idImage, CircleImageView image);
    void onClickSaveData();
    void getDate();
    void updatingCalification(String qualification);
}
