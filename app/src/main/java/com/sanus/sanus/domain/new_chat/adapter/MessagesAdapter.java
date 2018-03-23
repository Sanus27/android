package com.sanus.sanus.domain.new_chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sanus.sanus.R;
import com.sanus.sanus.domain.new_chat.data.Messages;
import com.sanus.sanus.domain.new_chat.presenter.NewChatPresenter;
import com.sanus.sanus.domain.new_chat.view.NewChatActivity;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {
    private Context context;
    private List<Messages> commentsDoctorList;
    private NewChatPresenter presenter;

    public MessagesAdapter(Context context, List<Messages> commentsDoctorList, NewChatPresenter presenter) {
        this.context = context;
        this.commentsDoctorList = commentsDoctorList;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_mine, parent, false);
        return new MessagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessagesAdapter.ViewHolder holder, int position) {
        holder.message.setText(commentsDoctorList.get(position).getMensaje());

    }

    @Override
    public int getItemCount() {
        return commentsDoctorList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView message;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            message = itemView.findViewById(R.id.viewMessage);

        }
    }
}