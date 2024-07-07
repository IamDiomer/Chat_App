package com.example.chatapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsuarioViewHolder> {

    private final List<User> listaUsuarios;
    private final List<String> idsUsuariosSeleccionados;

    public UsersAdapter(List<User> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
        this.idsUsuariosSeleccionados = new ArrayList<>();
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        User usuario = listaUsuarios.get(position);
        holder.nombreUsuarioTextView.setText(usuario.getName());
        holder.usuarioCheckBox.setChecked(idsUsuariosSeleccionados.contains(usuario.getId()));

        ////7cargar foto de perfi
        Glide.with(holder.itemView.getContext())
                .load(usuario.getProfile())
                .placeholder(R.drawable.ic_view_users)
                .error(android.R.drawable.ic_dialog_alert)
                .circleCrop()
                .into(holder.profilePictureImageView);

        holder.usuarioCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                idsUsuariosSeleccionados.add(usuario.getId());
            } else {
                idsUsuariosSeleccionados.remove(usuario.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public List<String> getUsuariosSeleccionados() {
        return idsUsuariosSeleccionados;
    }

    static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView nombreUsuarioTextView;
        CheckBox usuarioCheckBox;
        ImageView profilePictureImageView;

        UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreUsuarioTextView = itemView.findViewById(R.id.userNameTextView);
            usuarioCheckBox = itemView.findViewById(R.id.userCheckBox);
            profilePictureImageView = itemView.findViewById(R.id.profilePictureImageView);
        }
    }
}