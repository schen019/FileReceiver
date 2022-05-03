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
import android.widget.TextView;

import com.suyi.filereceiver.R;


public class ReceiveFragment extends Fragment {

    private TextView tvReceive;
    private EditText etSender;
    private EditText etCode3;
    private Button btnReceive;



    public ReceiveFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_receive, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvReceive = view.findViewById(R.id.tvReceive);
        etSender = view.findViewById(R.id.etSender);
        etCode3 = view.findViewById(R.id.etCode3);
        btnReceive = view.findViewById(R.id.btnReceive);
    }
}