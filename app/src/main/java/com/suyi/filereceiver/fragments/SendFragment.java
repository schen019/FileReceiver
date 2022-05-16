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

import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.ParseException;

import com.parse.SaveCallback;
import com.suyi.filereceiver.File;
import com.suyi.filereceiver.R;



public class SendFragment extends Fragment {

    public static final String TAG = "SendFragment";

    private TextView tvSend;
    private EditText etReceiver;
    private EditText etCode;
    private Button btnSelect;
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
                String receiver = etReceiver.getText().toString();
                if (receiver.isEmpty()) {
                    Toast.makeText(getContext(), "invalid user", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = etCode.getText().toString();
                if (code.isEmpty()) {
                    Toast.makeText(getContext(), "no code", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fileContent == null) {
                    Toast.makeText(getContext(), "there is no file", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "there is no file");
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();

                saveFile(receiver, currentUser, code, fileContent);
            }
        });
    }

            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                // TODO Auto-generated method stub


                if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

                    Uri uri = data.getData();
                    Log.d("SendFragment", "GotoFile" + uri.getPath());
                    fileContent = new java.io.File(uri.getPath());
                }
            }

            private void saveFile(String receiver, ParseUser currentUser, String code, java.io.File fileContent) {
                File file = new File();
                file.setSender(currentUser);
                file.setReceiver(receiver);
                file.setCode(code);
                file.setFile(new ParseFile(this.fileContent));
                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null){
                            Log.e(TAG, "error while saving", e);
                            Toast.makeText(getContext(), "error while saving", Toast.LENGTH_SHORT).show();
                        }
                        Log.i(TAG, "post save was succesful");

                    }
                });

            }
        }