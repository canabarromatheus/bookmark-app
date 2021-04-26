package com.example.bookmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmark.adapter.GenderAdapter;
import com.example.bookmark.adapter.OnClickListener;
import com.example.bookmark.adapter.RecyclerViewBookAdapter;
import com.example.bookmark.adapter.RecyclerViewGenderAdapter;
import com.example.bookmark.model.Book;
import com.example.bookmark.model.Gender;
import com.example.bookmark.ui.main.PageViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BooksFragment extends Fragment {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static int tabSelected;
    private ArrayList<Gender> genders;
    private List<Book> books;
    private RecyclerView recyclerViewGender;
    RecyclerViewBookAdapter bookAdapter;
//    private ListView listViewGender;
    private RecyclerView recyclerViewBook;
    private CardView teste;

    private PageViewModel pageViewModel;

    public static BooksFragment newInstance(int index) {
        tabSelected = index;
        BooksFragment fragment = new BooksFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        teste = (CardView) root.findViewById(R.id.genderes_cardview_romance);

//        mAuth = FirebaseAuth.getInstance();
//        mUser = mAuth.getCurrentUser();

//        recyclerViewGender = (RecyclerView) root.findViewById(R.id.gender_rv);
//        listViewGender = (ListView) root.findViewById(R.id.gender_lv);
        recyclerViewBook = (RecyclerView) root.findViewById(R.id.book_rv);
//        recyclerViewGender.setHasFixedSize(true);
        recyclerViewBook.setHasFixedSize(true);
        recyclerViewBook.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        LinearLayoutManager linearLayoutManagerGender = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManagerBook = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerViewBook.setLayoutManager(linearLayoutManagerBook);
//        recyclerViewGender.setLayoutManager(linearLayoutManagerGender);

//        initializeDataGender();
        initializeDataBook();

        teste.setOnClickListener(v -> {
            books = new ArrayList<>();

            Query romanceQuery = database.collection("books").whereArrayContains("Gender", "Romance");

            romanceQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot snapshot: task.getResult()) {
                            Book book = snapshot.toObject(Book.class);
                            books.add(book);
                        }

                        initializeBookAdapter();
                    }
                }
            });
        });

        return root;
    }

//    private void initializeDataGender() {
//        genders = new ArrayList<>();
//
//        genders.add(new Gender("Aventura", R.drawable.ic_bxs_directions));
//        genders.add(new Gender("Romance", R.drawable.ic_bxs_heart));
//        genders.add(new Gender("Ficção científica", R.drawable.ic_bx_infinite));
//        genders.add(new Gender("Drama", R.drawable.ic_bxs_wine));
//        genders.add(new Gender("Biografia", R.drawable.ic_bxs_book_bookmark));
//        genders.add(new Gender("HQ/Mangá", R.drawable.ic_bxs_book_open));
//        genders.add(new Gender("Terror", R.drawable.ic_bxs_ghost));
//        genders.add(new Gender("Suspense", R.drawable.ic_bxs_show));
//        genders.add(new Gender("Acadêmico", R.drawable.ic_bxs_flask));
//        genders.add(new Gender("Adulto", R.drawable.ic_bxs_hot));
//        genders.add(new Gender("Investigativo", R.drawable.ic_bxs_extension));
//        genders.add(new Gender("Policial", R.drawable.ic_bxs_star));
//        genders.add(new Gender("Didático", R.drawable.ic_bxs_school));
//        genders.add(new Gender("Comédia", R.drawable.ic_bxs_laugh));
//
//        initializeGenderAdapter();
//    }

    private void initializeDataBook() {
        books = new ArrayList<>();

//        books.add(new Book("azfaegaeg", R.mipmap.ic_person_1_round, "A menina que roubava livros", "drama", "alamo", 2014, "bla bla bla", "Rua são manoel, 808"));
//        books.add(new Book("azfaegaeg", R.mipmap.ic_person_1_round, "Um gato de rua chamado bob", "drama", "alamo", 2016, "bla bla bla", "Avenida Brasil, 222"));
//        books.add(new Book("azfaegaeg", R.mipmap.ic_person_1_round, "Percy jackson", "aventura", "alamo", 2010, "bla bla bla", "Rua C, 332"));
//        books.add(new Book("azfaegaeg", R.mipmap.ic_person_1_round, "Harry Potter", "aventura", "alamo", 2010, "bla bla bla", "Travessa são marcos 200"));
//        books.add(new Book("azfaegaeg", R.mipmap.ic_person_1_round, "A menina que roubava livros", "drama", "alamo", 2014, "bla bla bla", "Rua são manoel, 808"));
//        books.add(new Book("azfaegaeg", R.mipmap.ic_person_1_round, "Um gato de rua chamado bob", "drama", "alamo", 2016, "bla bla bla", "Avenida Brasil, 222"));
//        books.add(new Book("azfaegaeg", R.mipmap.ic_person_1_round, "Percy jackson", "aventura", "alamo", 2010, "bla bla bla", "Rua C, 332"));
//        books.add(new Book("azfaegaeg", R.mipmap.ic_person_1_round, "Harry Potter", "aventura", "alamo", 2010, "bla bla bla", "Travessa são marcos 200"));

        database.collection("books").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        Snackbar emptyDataAdvice = Snackbar.make(getView(), "Nenhum livro encontrado!", Snackbar.LENGTH_LONG);
                        emptyDataAdvice.show();
                    }

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Book actBook = document.toObject(Book.class);

                        books.add(actBook);
                    }

                    initializeBookAdapter();

//                    if (books.size() == queryDocumentSnapshots.size()) {
//
//                    }
                })
                .addOnFailureListener(e -> {
                    Snackbar failureError = Snackbar.make(getView(), "Houve algo de errado, tente novamente mais tarde...", Snackbar.LENGTH_LONG);
                    failureError.show();
                });


    }

    private void initializeBookAdapter() {

        bookAdapter = new RecyclerViewBookAdapter(books);

        recyclerViewBook.setAdapter(bookAdapter);

    }

    private void initializeGenderAdapter() {
        RecyclerViewGenderAdapter genderAdapter = new RecyclerViewGenderAdapter(genders);
        recyclerViewGender.setAdapter(genderAdapter);
//        GenderAdapter genderAdapter = new GenderAdapter(getContext(), genders);
//        listViewGender.setAdapter(genderAdapter);
    }
}