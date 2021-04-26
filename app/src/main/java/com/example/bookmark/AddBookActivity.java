package com.example.bookmark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.bookmark.model.Book;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddBookActivity extends AppCompatActivity {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private EditText addBookTitle;
    private EditText addBookEdition;
    private EditText addBookYear;
    private EditText addBookSynopsis;
    private Spinner addBookGender1;
    private Spinner addBookGender2;
    private Spinner addBookGender3;
    private Button addBookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        addBookTitle = (EditText) findViewById(R.id.add_book_title);
        addBookEdition = (EditText) findViewById(R.id.add_book_edition);
        addBookYear = (EditText) findViewById(R.id.add_book_year);
        addBookSynopsis = (EditText) findViewById(R.id.add_book_synopsis);
        addBookGender1 = (Spinner) findViewById(R.id.add_book_genders1);
        addBookGender2 = (Spinner) findViewById(R.id.add_book_genders2);
        addBookGender3 = (Spinner) findViewById(R.id.add_book_genders3);
        addBookButton = (Button) findViewById(R.id.button_add_book);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genders_array, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        addBookGender1.setAdapter(adapter);
        addBookGender2.setAdapter(adapter);
        addBookGender3.setAdapter(adapter);

        addBookButton.setOnClickListener(v -> addBook());
    }

    private void addBook() {
        if (!addBookTitle.getText().toString().isEmpty() || !addBookEdition.getText().toString().isEmpty() || !addBookYear.getText().toString().isEmpty() || !addBookSynopsis.getText().toString().isEmpty()) {
            Book book = new Book();
            List<String> genderList = joiningAllGenders(addBookGender1, addBookGender2, addBookGender3);

            book.setUserUUID(mUser.getUid());
            book.setImageId(R.mipmap.mulher_image_teste_foreground);
            book.setTitle(addBookTitle.getText().toString());
            book.setGender(genderList);
            book.setEdition(addBookEdition.getText().toString());
            book.setSynopsis(addBookSynopsis.getText().toString());
            book.setUserAddress("Catapimbas");

            database.collection("books").add(book)
                    .addOnSuccessListener(documentReference -> {
                       Snackbar bookRegistered = Snackbar.make(this.getCurrentFocus(), "Livro cadastrado com sucesso!", Snackbar.LENGTH_SHORT);
                       bookRegistered.show();

                       finish();
                       startActivity(new Intent(this, MainActivity.class));
                    })
                    .addOnFailureListener(e -> {
                        Snackbar failureRegisterError = Snackbar.make(this.getCurrentFocus(), "Erro ao registrar o livro, tente novamente mais tarde...", Snackbar.LENGTH_SHORT);
                        failureRegisterError.show();

                        finish();
                        startActivity(new Intent(this, MainActivity.class));
                    });
        } else {
            Snackbar emptyInputError = Snackbar.make(this.getCurrentFocus(), "Por favor, preencha todos os campos!", Snackbar.LENGTH_SHORT);
            emptyInputError.show();
        }
    }

    private ArrayList<String> joiningAllGenders(Spinner gender1, Spinner gender2, Spinner gender3) {
        ArrayList<String> genderToList = new ArrayList<>();

        if (!gender1.getSelectedItem().toString().equals("Gêneros")) {
            genderToList.add(gender1.getSelectedItem().toString());
        }

        if (!gender2.getSelectedItem().toString().equals("Gêneros")) {
            genderToList.add(gender2.getSelectedItem().toString());
        }

        if (!gender3.getSelectedItem().toString().equals("Gêneros")) {
            genderToList.add(gender3.getSelectedItem().toString());
        }

        return genderToList;
    }
}