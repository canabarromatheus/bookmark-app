package com.example.bookmark;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookmark.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private EditText emailText;
    private EditText passwordText;
    private Button signInButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        emailText = findViewById(R.id.editTextEmailSignIn);
        passwordText = findViewById(R.id.editTextPasswordSignIn);
        signInButton = findViewById(R.id.buttonSignIn);
        signUpButton = findViewById(R.id.buttonSignUp);

        signInButton.setOnClickListener(v -> signIn());
        signUpButton.setOnClickListener(v -> toSignUp());
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mUser != null) {

        }
    }

    private void signIn() {
        if (emailText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty()) {
            Snackbar invalidInputError = Snackbar.make(findViewById(R.id.login_constLayout), "Digite seu E-mail/Senha!", Snackbar.LENGTH_SHORT);
            invalidInputError.show();
        } else {
            User user = new User();
            user.setEmail(emailText.getText().toString());
            user.setPassword(passwordText.getText().toString());

            mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d("Autenticação", "Bem-sucedida!");

                        startActivity(new Intent(getBaseContext(), MainActivity.class));
                        finish();
                    } else {
                        Log.d("Autenticação", "Mal-sucedida!");
                        Snackbar invalidInputError = Snackbar.make(findViewById(R.id.login_constLayout), "E-Mail/Senha inválidos!", Snackbar.LENGTH_SHORT);
                        invalidInputError.show();
                    }
                }
            });
        }
    }

    private void toSignUp() {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }


}
