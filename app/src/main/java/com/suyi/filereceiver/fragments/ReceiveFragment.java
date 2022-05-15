package com.suyi.filereceiver.fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.suyi.filereceiver.File;
import com.suyi.filereceiver.R;

import java.util.List;


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

        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Sender = etSender.getText().toString();
                if (Sender.isEmpty()) {
                    Toast.makeText(getContext(), "invalid user", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                GetFile(Sender,currentUser);
            }
        });
    }

    private void GetFile(String sender, ParseUser currentUser) {
        ParseQuery<File> file =ParseQuery.getQuery(File.class);
        file.include(File.KEY_FILE);
        file.whereEqualTo(File.KEY_RECEIVER,currentUser);
        file.whereEqualTo(File.KEY_SENDER,sender);
        file.setLimit(10);
        file.findInBackground(new FindCallback<File>() {
            @Override
            public void done(List<File> files, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting file",e);
                    return;
                }
                for (File file : files) {
                    Log.i(TAG, "Sender: "+file.getSender()+" Receiver: " + file.getReceiver());
                }

            }

        });  }

}