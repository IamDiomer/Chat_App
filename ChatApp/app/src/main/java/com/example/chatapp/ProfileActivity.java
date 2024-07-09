package com.example.chatapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {
    private TextView textViewName, textViewSurname, textViewDni, textViewPhone;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewName = findViewById(R.id.tvNombe);
        textViewSurname = findViewById(R.id.tvApellido);
        textViewDni = findViewById(R.id.tvDni);
        textViewPhone = findViewById(R.id.tvTelefono);

        db = FirebaseFirestore.getInstance();

        loadProfileInformation();
    }

    private void loadProfileInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            db.collection("Users").document(userId).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String nombre = document.getString("nombre");
                                String apellido = document.getString("apellido");
                                String dni = document.getString("dni");
                                String telefono = document.getString("telefono");

                                textViewName.setText("Nombre: " + nombre);
                                textViewSurname.setText("Apellido: " + apellido);
                                textViewDni.setText("DNI: " + dni);
                                textViewPhone.setText("Teléfono: " + telefono);
                            } else {
                                Toast.makeText(ProfileActivity.this, "No se encontró la información", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ProfileActivity.this, "Error al cargar la información", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void abrirConfiguracionPerfil(View view) {
        Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}
