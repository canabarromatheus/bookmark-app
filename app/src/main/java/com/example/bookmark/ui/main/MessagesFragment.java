package com.example.bookmark.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bookmark.R;

import static android.content.ContentValues.TAG;

public class MessagesFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static int tabSelected;

    private PageViewModel pageViewModel;

    public static MessagesFragment newInstance(int index) {
        tabSelected = index;
        MessagesFragment fragment = new MessagesFragment();
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
        View root = inflater.inflate(R.layout.fragment_main_2, container, false);
        return root;
    }
}
