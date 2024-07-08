package com.example.chatapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private final List<Message> messageList;
    private final String currentUserId;

    public MessageAdapter(List<Message> messageList, String currentUserId) {
        this.messageList = messageList;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message mensaje = messageList.get(position);


        RequestOptions circleOptions = new RequestOptions().circleCrop();

        if (mensaje.esEntrante(currentUserId)) {
            holder.layoutMensajeEntrante.setVisibility(View.VISIBLE);
            holder.layoutMensajeSaliente.setVisibility(View.GONE);
            holder.textoMensajeEntrante.setText(mensaje.getMessage());
            Glide.with(holder.itemView.getContext())
                    .load(mensaje.getProfileImageUrl())
                    .apply(circleOptions)
                    .placeholder(R.drawable.ft_profile)
                    .into(holder.imagenPerfilEntrante);
        } else {
            holder.layoutMensajeSaliente.setVisibility(View.VISIBLE);
            holder.layoutMensajeEntrante.setVisibility(View.GONE);
            holder.textoMensajeSaliente.setText(mensaje.getMessage());
            Glide.with(holder.itemView.getContext())
                    .load(mensaje.getProfileImageUrl())
                    .apply(circleOptions)
                    .placeholder(R.drawable.ft_profile)
                    .into(holder.imagenPerfilSaliente);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutMensajeEntrante;
        LinearLayout layoutMensajeSaliente;
        TextView textoMensajeEntrante;
        TextView textoMensajeSaliente;
        ImageView imagenPerfilEntrante;
        ImageView imagenPerfilSaliente;

        MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutMensajeEntrante = itemView.findViewById(R.id.layoutMensajeEntrante);
            layoutMensajeSaliente = itemView.findViewById(R.id.layoutMensajeSaliente);
            textoMensajeEntrante = itemView.findViewById(R.id.textoMensajeEntrante);
            textoMensajeSaliente = itemView.findViewById(R.id.textoMensajeSaliente);
            imagenPerfilEntrante = itemView.findViewById(R.id.imagenPerfilEntrante);
            imagenPerfilSaliente = itemView.findViewById(R.id.imagenPerfilSaliente);
        }
    }
}