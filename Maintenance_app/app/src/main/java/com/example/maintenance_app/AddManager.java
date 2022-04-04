package com.example.maintenance_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddManager extends AppCompatActivity{

    private EditText username, education, password;
    private Button addManager;
    private Employees employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manager);

        username = (EditText) findViewById(R.id.regUsername);
        education = (EditText) findViewById(R.id.regEducation);
        password = (EditText) findViewById(R.id.regPassword);

        employee = new Employees();

        addManager = (Button) findViewById(R.id.regAddManager);
        addManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String _username = username.getText().toString();
                String _education = education.getText().toString();
                String _password = password.getText().toString();

                if(_username.isEmpty()){
                    username.setError("A név megadása kötelező!");
                    username.requestFocus();
                    return;
                }

                if(_education.isEmpty()){
                    education.setError("A képzettség megadása kötelező!");
                    education.requestFocus();
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
                try{
                    employee.add(manager).addOnSuccessListener(succ -> {
                        Toast.makeText(AddManager.this, "Karbantartó sikeresen hozzáadva!", Toast.LENGTH_LONG).show();
                    }).addOnFailureListener(err -> {
                        Toast.makeText(AddManager.this, "Sikertelen a felvitel!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(AddManager.this, "error: " + err.getMessage(), Toast.LENGTH_LONG).show();
                    });
                } catch (Exception e){
                    Toast.makeText(AddManager.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}