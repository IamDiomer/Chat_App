package com.example.chatapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.adaptadores.MessageAdapter;
import com.example.chatapp.clases.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private DatabaseReference messagesReference;
    private String groupId;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private EditText messageEditText;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recyclerView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageList = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        ///// Obtener el id delgrupo desde el Intent
        groupId = getIntent().getStringExtra("groupId");

        /////// Verificar si el id del grupo es nulo o vacío
        if (groupId == null || groupId.isEmpty()) {
            Toast.makeText(this, "Falta ID del grupo", Toast.LENGTH_SHORT).show();
            finish(); ////7 Cierra la actividad si falta el id
            return;
        }

        // Inicializar el adaptador con la lista de mensajes y el ID del usuario actuaL
        messageAdapter = new MessageAdapter(messageList, currentUser.getUid());
        recyclerView.setAdapter(messageAdapter);

        // Obtener la referencia a los mensajes del grupo en Firebase
        messagesReference = FirebaseDatabase.getInstance().getReference().child("Groups").child(groupId).child("messages");

        loadMessages();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void loadMessages() {
        messagesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Message message = dataSnapshot.getValue(Message.class);
                    if (message != null) {
                        messageList.add(message);
                    }
                }
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, "Fallo carga de mensaje", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage() {
        String messageText = messageEditText.getText().toString().trim();
        if (messageText.isEmpty()) {
            Toast.makeText(this, "No se puede enviar un mensaje vacio :(", Toast.LENGTH_SHORT).show();
            return;
        }

        String messageId = messagesReference.push().getKey();
        if (messageId == null) {
            Toast.makeText(this, "Fallo generar ID de mendaje", Toast.LENGTH_SHORT).show();
            return;
        }

        String profileImageUrl = currentUser.getPhotoUrl() != null ? currentUser.getPhotoUrl().toString() : "url_del_marcador_de_posición";
        Message message = new Message(currentUser.getUid(), messageText, System.currentTimeMillis(), profileImageUrl);

        messagesReference.child(messageId).setValue(message)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        messageEditText.setText("");
                    } else {
                        Toast.makeText(ChatActivity.this, "Error al enviar mensaje", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
