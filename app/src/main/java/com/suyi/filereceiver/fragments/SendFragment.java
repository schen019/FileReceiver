package com.suyi.filereceiver.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
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
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.ParseException;

import com.parse.SaveCallback;
import com.suyi.filereceiver.File;
import com.suyi.filereceiver.MainActivity;
import com.suyi.filereceiver.R;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class SendFragment extends Fragment {

    public final static String TAG = "SendFragment";

    private TextView tvSend;
    private EditText etReceiver;
    private EditText etCode;
    private Button btnSelect;
    private Button btnSend;
    private String fileName;

    private byte [] fileContent;

    //public final String fileName = "file.pdf";
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


            // Create a File reference for future access
            //fileContent = getPhotoFileUri(fileName);

            //Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", fileContent);


            Uri uri = data.getData();

            String result;

            Cursor returnCursor =
                    getContext().getContentResolver().query(uri, null, null, null, null);
            assert returnCursor != null;
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            fileName = returnCursor.getString(nameIndex);
            returnCursor.close();


            Log.i("SendFragment", "GotoFile" + uri.getPath());
            //fileContent = new java.io.File(uri.getPath());
            ParcelFileDescriptor inputPFD = null;
            try {
                inputPFD = getContext().getContentResolver().openFileDescriptor(uri, "r");
                AssetFileDescriptor aFD = getContext().getContentResolver().openAssetFileDescriptor(uri, "r" );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            //File f = new File( inputPFD.getFd() );

            FileDescriptor fd = inputPFD.getFileDescriptor();

            fileContent = new byte[(int) inputPFD.getStatSize()];

            FileInputStream fr = new FileInputStream (fd);

            try {
                fr.read( fileContent );
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private void saveFile(String receiver, ParseUser currentUser, String PIN) {
        File file = new File();
        file.setSender(currentUser.getUsername());
        file.setReceiver(receiver);
        file.setCode(PIN);
        file.setFile(new ParseFile(fileName, fileContent));


        if (fileContent == null){ Log.i(TAG,"empty");}

        file.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e("SendFragment","Error while saving",e);
                    Toast.makeText(getContext(),"Error while save!",Toast.LENGTH_SHORT).show();
                }
                else { Log.i("SendFragment","File upload was successful"); }
            }
        });
    }
    }