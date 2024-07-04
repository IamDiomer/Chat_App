package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewName, textViewSurname, textViewPhone, textViewDNI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewName = findViewById(R.id.textViewName);
        textViewSurname = findViewById(R.id.textViewSurname);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewDNI = findViewById(R.id.textViewDNI);

        Intent intent = getIntent();
        String nombre = intent.getStringExtra("NOMBRE");
        String apellido = intent.getStringExtra("APELLIDO");
        String telefono = intent.getStringExtra("TELEFONO");
        String dni = intent.getStringExtra("DNI");

        textViewName.setText(nombre);
        textViewSurname.setText(apellido);
        textViewPhone.setText(telefono);
        textViewDNI.setText(dni);
    }

    public void abrirConfiguracionPerfil(View view) {
        Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}
