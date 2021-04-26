package com.example.bookmark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.bookmark.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

public class UserDataActivity extends AppCompatActivity {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private EditText userName;
    private Button next1Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        mAuth = FirebaseAuth.getInstance();

        userName = findViewById(R.id.new_user_name);
        next1Button = findViewById(R.id.new_user_button_next_1);

        next1Button.setOnClickListener(v -> addNameForUser());

    }

    private void addNameForUser() {
        DocumentReference firestoreUser = database.collection("users").document(mAuth.getUid());

        firestoreUser.update("name", userName.getText().toString())
                .addOnSuccessListener(aVoid -> {
                    startActivity(new Intent(getBaseContext(), UserAddressActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    Snackbar setNameError = Snackbar.make(getCurrentFocus(), "Não foi possível atualizar o nome do usuário, tente novamente mais tarde...", Snackbar.LENGTH_LONG);
                    setNameError.show();
                });

    }
}