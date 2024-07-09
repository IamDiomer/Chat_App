package com.example.chatapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {
    private EditText editTextName, editTextSurname, editTextDni, editTextPhone;
    private Button buttonSave;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editTextName = findViewById(R.id.etNombre);
        editTextSurname = findViewById(R.id.etApellido);
        editTextDni = findViewById(R.id.etDni);
        editTextPhone = findViewById(R.id.etTelefono);
        buttonSave = findViewById(R.id.guardar);

        db = FirebaseFirestore.getInstance();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileInformation();
            }
        });
    }

    private void saveProfileInformation() {
        String name = editTextName.getText().toString();
        String surname = editTextSurname.getText().toString();
        String dni = editTextDni.getText().toString();
        String phone = editTextPhone.getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            Map<String, Object> userProfile = new HashMap<>();
            userProfile.put("nombre", name);
            userProfile.put("apellido", surname);
            userProfile.put("dni", dni);
            userProfile.put("telefono", phone);

            db.collection("Users").document(userId).set(userProfile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SettingsActivity.this, "Información guardada", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(SettingsActivity.this, "Error al guardar la información", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
