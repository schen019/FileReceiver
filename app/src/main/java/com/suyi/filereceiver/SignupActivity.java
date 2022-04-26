package com.suyi.filereceiver;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {
    public static final String TAG = "SignupActivity";
    private EditText etUserName;
    private EditText etPassword;
    private Button btnCreate;
    private Button btnToLogin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        etUserName = findViewById(R.id.etUserName2);
        etPassword = findViewById(R.id.etPassword2);
        btnCreate = findViewById(R.id.btnCreate);
        btnToLogin = findViewById(R.id.btnToLogin);

        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"onClick toLogin Button");
                goLoginActivity();
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick login button");
                String username = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                CreateUser(username, password);
            }
        });
    }

    private void CreateUser(String username, String password) {
        Log.i(TAG,"Attempting to setup user" + username);

        ParseUser user = new ParseUser();

        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Log.i(TAG,"On Success");
                    Toast.makeText(SignupActivity.this, "Signup Success !", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG,"Issue with Setup", e);


                }
            }
        });
    }

    private void goLoginActivity() {
        Intent i = new Intent(SignupActivity.this,LoginActivity.class);
        startActivity(i);
    }
}
