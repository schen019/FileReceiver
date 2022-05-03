package com.suyi.filereceiver.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.suyi.filereceiver.R;

public class SendFragment extends Fragment {

    private TextView tvSend;
    private EditText etReceiver;
    private EditText etCode;
    private Button btnSelect;
    private ImageView ivPostImage;
    private Button btnSend;

    public SendFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvSend = view.findViewById(R.id.tvSend);
        etReceiver = view.findViewById(R.id.etReceiver);
        etCode = view.findViewById(R.id.etCode);
        btnSelect = view.findViewById(R.id.btnSelect);
        ivPostImage = view.findViewById(R.id.ivPostImage);
        btnSend = view.findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}