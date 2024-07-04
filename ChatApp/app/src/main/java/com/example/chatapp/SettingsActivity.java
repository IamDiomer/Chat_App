package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private EditText etNombre, editTextPhone, editTextNumber, editTextText2;
    private Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etNombre = findViewById(R.id.et_nombre);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextText2 = findViewById(R.id.editTextText2);
        guardar = findViewById(R.id.guardar);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString();
                String apellido = editTextText2.getText().toString();
                String telefono = editTextPhone.getText().toString();
                String dni = editTextNumber.getText().toString();

                Intent intent = new Intent(SettingsActivity.this, ProfileActivity.class);
                intent.putExtra("NOMBRE", nombre);
                intent.putExtra("APELLIDO", apellido);
                intent.putExtra("TELEFONO", telefono);
                intent.putExtra("DNI", dni);
                startActivity(intent);
            }
        });
    }
}
