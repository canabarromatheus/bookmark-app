package com.example.bookmark;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookmark.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private EditText emailText;
    private EditText passwordText;
    private EditText passwordConfirmationText;
    private Button buttonSignUp;
    private Button buttonSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        emailText = findViewById(R.id.editTextEmailSignUp);
        passwordText = findViewById(R.id.editTextPasswordSignUp);
        passwordConfirmationText = findViewById(R.id.editTextPasswordConfirmationSignUp);
        buttonSignIn = findViewById(R.id.buttonRegisterSignIn);
        buttonSignUp = findViewById(R.id.buttonRegisterSignUp);

        buttonSignUp.setOnClickListener(v -> signUp());
        buttonSignIn.setOnClickListener(v -> toSignIn());
    }

    private void signUp() {
        if (emailText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty() || passwordConfirmationText.getText().toString().isEmpty()) {
            Snackbar invalidInputError = Snackbar.make(findViewById(R.id.register_constLayout), "Digite seu E-mail/Senha!", Snackbar.LENGTH_SHORT);
            invalidInputError.show();
        } else {
            if (!passwordText.getText().toString().equals(passwordConfirmationText.getText().toString())) {
                Snackbar invalidInputError = Snackbar.make(findViewById(R.id.register_constLayout), "Senhas não coincidem!", Snackbar.LENGTH_SHORT);
                invalidInputError.show();
            } else {
                User user = new User();
                user.setEmail(emailText.getText().toString());
                user.setPassword(passwordText.getText().toString());

                mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Snackbar successNewUser = Snackbar.make(findViewById(R.id.register_constLayout), "Usuário criado com sucesso!", Snackbar.LENGTH_SHORT);
                            successNewUser.show();

                            mAuth = FirebaseAuth.getInstance();

                            user.setUid(mAuth.getUid());
                            user.setRating(5.0);

                            database.collection("users").document(user.getUid()).set(user)
                                    .addOnSuccessListener(aVoid -> {
                                        startActivity(new Intent(getBaseContext(), UserDataActivity.class));
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        startActivity(new Intent(getBaseContext(), UserDataActivity.class));
                                        finish();
                                    });

//                            startActivity(new Intent(getBaseContext(), MainActivity.class));
//                            finish();
                        } else {
                            Snackbar creationNewUserError = Snackbar.make(findViewById(R.id.register_constLayout), "Não foi possível criar um novo usuário!", Snackbar.LENGTH_SHORT);
                            creationNewUserError.show();
                        }
                    }
                });
            }
        }

    }

    private void toSignIn() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


}
