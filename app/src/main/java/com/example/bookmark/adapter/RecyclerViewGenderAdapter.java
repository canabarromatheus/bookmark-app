package com.example.bookmark.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmark.R;
import com.example.bookmark.model.Gender;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RecyclerViewGenderAdapter extends RecyclerView.Adapter<RecyclerViewGenderAdapter.GenderViewHolder> {

    public List<Gender> genders;

    public RecyclerViewGenderAdapter(List<Gender> genders) {
        this.genders = genders;
    }

    @Override
    public GenderViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_gender_item, viewGroup, false);
        GenderViewHolder genderViewHolder = new GenderViewHolder(view);
        return genderViewHolder;
    }

    @Override
    public void onBindViewHolder(GenderViewHolder holder, int i) {
        //em teste
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseFirestore database = FirebaseFirestore.getInstance();
//                FirebaseAuth mAuth = FirebaseAuth.getInstance();
//                String selectedGender = genders.get(i).gender;
//
//                CollectionReference books = database.collection("books");
//                books.whereArrayContains("gender", selectedGender);
//
//                for (:
//                     ) {
//
//                }
//            }
//        });
        holder.genderGender.setText(genders.get(i).gender);
        holder.genderIcon.setImageResource(genders.get(i).iconId);
    }

    @Override
    public int getItemCount() {
        return genders.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class GenderViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView genderGender;
        ImageView genderIcon;

        GenderViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.genderes_cardview);
            genderGender = (TextView) itemView.findViewById(R.id.gender_gender);
            genderIcon = (ImageView) itemView.findViewById(R.id.gender_icon);
        }
    }

}
