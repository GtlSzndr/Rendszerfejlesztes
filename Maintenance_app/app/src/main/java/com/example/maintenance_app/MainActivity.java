package com.example.maintenance_app;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{

    private Button login;
    private EditText username, password;
    private DatabaseReference dbRef;
    private String _username, _password;
    private String dbUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        dbRef = FirebaseDatabase.getInstance().getReference().child("employees");

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                workerLogin();
            }

            private void workerLogin() {
                
                _username = username.getText().toString();
                _password = password.getText().toString();

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

                SearchData(dbRef, _username, _password);
            }

            private void SearchData (DatabaseReference ref , String username_, String password_){
                ref.orderByChild("username").equalTo(username_).addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            dbUsername = snapshot.toString();
                            ref.orderByChild("password").equalTo(password_).addValueEventListener(new ValueEventListener(){
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        Intent intent;
                                        if (username_.equals("admin")){
                                            intent = new Intent(MainActivity.this, AdminHome.class);
                                            startActivity(intent);
                                        } else {
                                            intent = new Intent(MainActivity.this, WorkerHome.class);
                                            startActivity(intent);
                                        }

                                    } else {
                                        password.setError("Hibás jelszó!");
                                        password.requestFocus();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        } else {
                            username.setError("Hibás felhasználónév!");
                            username.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
}