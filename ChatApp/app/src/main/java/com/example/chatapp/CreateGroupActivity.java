package com.example.chatapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateGroupActivity extends AppCompatActivity {

    private EditText groupNameEditText;
    private RecyclerView usersRecyclerView;
    private UsersAdapter usersAdapter;
    private List<User> userList;
    private DatabaseReference usersReference, groupsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        groupNameEditText = findViewById(R.id.groupNameEditText);
        usersRecyclerView = findViewById(R.id.usersRecyclerView);
        Button createGroupButton = findViewById(R.id.createGroupButton);

        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();
        usersAdapter = new UsersAdapter(userList);
        usersRecyclerView.setAdapter(usersAdapter);

        usersReference = FirebaseDatabase.getInstance().getReference().child("Users");
        groupsReference = FirebaseDatabase.getInstance().getReference().child("Groups");

        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User usuario = dataSnapshot.getValue(User.class);
                    userList.add(usuario);
                }
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Man
            }
        });

        createGroupButton.setOnClickListener(v -> createGroup());
    }

    private void createGroup() {
        String nombreGrupo = groupNameEditText.getText().toString().trim();
        if (nombreGrupo.isEmpty()) {
            Toast.makeText(this, "Ingrese un nombre para el grupo", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Boolean> miembrosMap = new HashMap<>();
        for (String userId : usersAdapter.getUsuariosSeleccionados()) {
            miembrosMap.put(userId, true);
        }

        // Generar un nuevo ID de grupo
        String groupId = groupsReference.push().getKey();
        if (groupId == null) {
            Toast.makeText(this, "Error al generar el ID del grupo", Toast.LENGTH_SHORT).show();
            return;
        }

        Group grupo = new Group(groupId, nombreGrupo, miembrosMap);
        groupsReference.child(groupId).setValue(grupo)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(CreateGroupActivity.this, "Grupo creado exitosamente", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(CreateGroupActivity.this, "Error al crear grupo", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
