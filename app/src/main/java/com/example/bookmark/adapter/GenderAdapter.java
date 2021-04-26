package com.example.bookmark.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookmark.R;
import com.example.bookmark.model.Gender;

import java.util.ArrayList;
import java.util.List;

public class GenderAdapter extends ArrayAdapter<Gender> {

    private ArrayList<Gender> genders;

    public GenderAdapter(Context context, ArrayList<Gender> genders) {
        super(context, 0, genders);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Gender gender = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recyclerview_gender_item, parent, false);
        }

        ImageView genderIcon = (ImageView) convertView.findViewById(R.id.gender_icon);
        TextView genderGender = (TextView) convertView.findViewById(R.id.gender_gender);

        genderIcon.setImageResource(gender.iconId);
        genderGender.setText(gender.gender);

        return convertView;
    }
}
