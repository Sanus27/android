package com.sanus.sanus.domain.search.presenter;


import android.content.Context;

import com.sanus.sanus.domain.search.view.SearchView;

import de.hdodenhof.circleimageview.CircleImageView;

public interface SearchPresenter extends SearchView {
    void init();
    void showImage(String idImage, final Context context, final CircleImageView image);
    void buscador(String texto);


}
