package com.suyi.filereceiver.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.ParseException;

import com.parse.SaveCallback;
import com.suyi.filereceiver.File;
import com.suyi.filereceiver.MainActivity;
import com.suyi.filereceiver.R;



public class SendFragment extends Fragment {

    private TextView tvSend;
    private EditText etReceiver;
    private EditText etCode;
    private Button btnSelect;
    private ImageView ivFileImage;
    private Button btnSend;

    private java.io.File fileContent;

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
        btnSend = view.findViewById(R.id.btnSend);
        ivFileImage =view.findViewById(R.id.ivFileImage);

        if (fileContent != null){
            ivFileImage.setImageResource(R.drawable.ic_baseline_sentiment_very_satisfied_24);
        }else{
            ivFileImage.setImageResource(R.drawable.ic_round_sentiment_dissatisfied_24);
        }
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                Intent i = Intent.createChooser(intent, "Choose a file");
                startActivityForResult(i, 1);

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String receiver = etReceiver.getText().toString();;
                String PIN = etCode.getText().toString();
                if (receiver.isEmpty()) {
                    Toast.makeText(getContext(), "invalid user", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (PIN.isEmpty()) {
                    Toast.makeText(getContext(), "no code", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fileContent == null) {
                    Toast.makeText(getContext(), "there is no file", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();

                saveFile(receiver, currentUser, PIN);
                Toast.makeText(getContext(), "File Send!!", Toast.LENGTH_SHORT).show();

                Fragment fragment = new Fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContainer,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub


        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            Uri uri = data.getData();
            Log.i("SendFragment", "GotoFile" + uri.getPath());
            fileContent = new java.io.File(uri.getPath());
        }
    }

    private void saveFile(String receiver, ParseUser currentUser, String PIN) {
        File file = new File();
        file.setSender(currentUser);
        file.setReceiver(receiver);
        file.setCode(PIN);
        file.setFile(new ParseFile(fileContent));
        file.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e("SendFragment","Error while saving",e);
                    Toast.makeText(getContext(),"Error while save!",Toast.LENGTH_SHORT).show();
                }
                Log.i("SendFragment","File upload was successful");
            }
        });
    }
    }