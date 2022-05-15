package com.suyi.filereceiver.fragments;

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

import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.ParseException;
import com.suyi.filereceiver.File;
import com.suyi.filereceiver.R;



public class SendFragment extends Fragment {

    private TextView tvSend;
    private EditText etReceiver;
    private EditText etCode;
    private Button btnSelect;
    private ImageView ivPostImage;
    private Button btnSend;

    private File fileContent;

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
                String receiver = etReceiver.getText().toString();
                if (receiver.isEmpty()) {
                    Toast.makeText(getContext(), "invalid user", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fileContent == null) {
                    Toast.makeText(getContext(), "there is no file", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();

                saveFile(receiver, currentUser, fileContent);


        /*btnSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("SendGragment","select button");
                    Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                    chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
                    chooseFile.setType("/*
                    Intent i = Intent.createChooser(chooseFile,"Choose a file");
                    GotoFile(i, FILE_MANAGER);
                }
            }); */
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                Intent i = Intent.createChooser(intent,"Choose a file");
                startActivityForResult(i, 1);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub


                if(requestCode == 1 && resultCode==RESULT_OK){

                  Uri uri = null;
                  if (data != null){
                    uri = data.getData();
                    Log.d("SendFragment","GotoFile"+uri.getPath());

                }
                }



    private void saveFile(String receiver, ParseUser currentUser, File fileContent) {
        File file =  new File();
        file.setReceiver(receiver);
        file.setSender(currentUser);
        //file.setFile(new ParseFile(fileContent));
    }

}