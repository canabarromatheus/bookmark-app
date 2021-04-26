package com.example.bookmark.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmark.R;
import com.example.bookmark.model.Book;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RecyclerViewBookAdapter extends RecyclerView.Adapter<RecyclerViewBookAdapter.BookViewHolder> {

    public List<Book> books;

    public RecyclerViewBookAdapter(List<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_book_item, viewGroup, false);
        BookViewHolder bookViewHolder = new BookViewHolder(view);
        return bookViewHolder;
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int i) {
        holder.bookImage.setImageResource(books.get(i).getImageId());
        holder.bookTitle.setText(books.get(i).getTitle());
        holder.bookGender.setText(books.get(i).getGender().stream().collect(Collectors.joining(" - ")));
        holder.bookAddress.setText(books.get(i).getUserAddress());

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void updateList() {
        this.notifyDataSetChanged();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView bookImage;
        TextView bookTitle;
        TextView bookGender;
        TextView bookAddress;

        BookViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.books_cardview);
            bookImage = (ImageView) itemView.findViewById(R.id.book_image);
            bookTitle = (TextView) itemView.findViewById(R.id.book_title);
            bookGender = (TextView) itemView.findViewById(R.id.book_gender);
            bookAddress = (TextView) itemView.findViewById(R.id.book_address);
        }
    }
}
