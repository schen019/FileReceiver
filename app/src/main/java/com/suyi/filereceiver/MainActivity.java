package com.suyi.filereceiver;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;
import com.suyi.filereceiver.fragments.HistoryFragment;
import com.suyi.filereceiver.fragments.ReceiveFragment;
import com.suyi.filereceiver.fragments.SendFragment;

public class MainActivity extends AppCompatActivity {
    private TextView tvHello;
    private Button btnLogout;
    private BottomNavigationView btnNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        tvHello = findViewById(R.id.tvHello);
        btnLogout = findViewById(R.id.btnLogout);
        btnNavigationView = findViewById(R.id.btnNavigationView);

        tvHello.setText("Hello,"+ParseUser.getCurrentUser().getUsername()+"!!!");

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                finish();
            }
        });

        btnNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new SendFragment();
                switch (item.getItemId()) {
                    case R.id.action_send:
                        fragment = new SendFragment();
                        Toast.makeText(MainActivity.this, "Send", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_receive:
                        fragment = new ReceiveFragment();
                        Toast.makeText(MainActivity.this, "Receive", Toast.LENGTH_SHORT).show();
                        break;


                    case R.id.action_history:
                        fragment = new HistoryFragment();
                        Toast.makeText(MainActivity.this, "History", Toast.LENGTH_SHORT).show();
                    default:
                        break;


                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment). commit();
                return true;

            }
        });
        btnNavigationView.setSelectedItemId(R.id.action_send);
    }
}