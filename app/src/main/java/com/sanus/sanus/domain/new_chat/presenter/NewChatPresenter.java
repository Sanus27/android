package com.sanus.sanus.domain.new_chat.presenter;


import com.sanus.sanus.domain.new_chat.view.NewChatView;

public interface NewChatPresenter extends NewChatView{
    void viewMessages(String idDoc, String idUser);
    void sendMessages(String idUser,String idDoct, String fecha, String hora, String message);
    void getDate();
    void getTipoUser(final String idUser, final String idDoct);
    void insertContact(String idUser, String idDoct);
}
