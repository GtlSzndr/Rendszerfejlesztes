package com.example.maintenance_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AddManager extends AppCompatActivity{

    private EditText username, password;
    private Button addManager;
    private Button back;

    private FirebaseDatabase db;
    private DatabaseReference dbRef;

    private Spinner educationSpinner;
    private DatabaseReference educationDbRef;
    private ArrayList<String> educationList;
    private ArrayAdapter<String> educationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manager);

        username = (EditText) findViewById(R.id.regUsername);
        password = (EditText) findViewById(R.id.regPassword);

        educationList = new ArrayList<String>();
        educationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, educationList);
        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        educationSpinner = (Spinner) findViewById(R.id.educationCat2);
        educationSpinner.setAdapter(educationAdapter);

        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("employees");
        educationDbRef = db.getReference("educations");
        fetchDatas();

        addManager = (Button) findViewById(R.id.regAddManager);
        addManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String _username = username.getText().toString();
                String _password = password.getText().toString();
                String _education = educationSpinner.getSelectedItem().toString();

                if(_username.isEmpty()){
                    username.setError("A név megadása kötelező!");
                    username.requestFocus();
                    return;
                }

                if(_password.isEmpty()){
                    password.setError("A jelszó megadása kötelező!");
                    password.requestFocus();
                    return;
                }

                if(_password.length() < 6){
                    password.setError("A jelszónak minimum 6 karakternek kell lennie!");
                    password.requestFocus();
                    return;
                }

                Manager manager = new Manager(_username, _education, _password);

                Toast.makeText(AddManager.this, "Karbantartó hozzáadva!", Toast.LENGTH_SHORT).show();

                dbRef.push().setValue(manager);

                Intent intent = new Intent(AddManager.this, AddManager.class);
                startActivity(intent);

            }
        });

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddManager.this, AdminHome.class);
                startActivity(intent);
            }
        });
    }

    public void fetchDatas() {
        ValueEventListener educationListener = educationDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot educationSnapshot : snapshot.getChildren()) {
                    educationList.add(educationSnapshot.child("name").getValue().toString());
                }

                educationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}