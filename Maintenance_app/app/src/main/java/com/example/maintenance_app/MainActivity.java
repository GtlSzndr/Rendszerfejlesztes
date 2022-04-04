package com.example.maintenance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity{

    private Button login;
    private EditText username, password;
    private Employees employee;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        employee = new Employees();
        dbRef = employee.getRef();

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //workerLogin(); //TODO: bejelentkezes
                Intent intent = new Intent(MainActivity.this, AdminHome.class);
                startActivity(intent);
//
            }

            private void workerLogin() {
                String _username = username.getText().toString();
                String _password = password.getText().toString();

                if (_username.isEmpty()){
                    username.setError("A név megadása kötelező!");
                    username.requestFocus();
                    return;
                }

                if (_password.isEmpty()) {
                    password.setError("A jelszó megadása kötelező!");
                    password.requestFocus();
                    return;
                }

                try{
                    String dbValue = employee.get();

                    //TODO: bejelentkezes... aztan abba bele:

                    if (_username.equalsIgnoreCase("admin")){
                        Intent intent = new Intent(MainActivity.this, AdminHome.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, WorkerHome.class);
                        startActivity(intent);
                    }

                } catch (Exception e){
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}