package com.example.maintenance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEducation extends AppCompatActivity {
    private EditText education;
    private Button addEducation, edToDeviceCat, edToManager;

    private FirebaseDatabase db;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_education);

        education = (EditText) findViewById(R.id.education);

        db = FirebaseDatabase.getInstance();


        addEducation = (Button) findViewById(R.id.add_education);
        addEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef = db.getReference("educations");

                String educationCat = education.getText().toString();

                if (educationCat.isEmpty()) {
                    education.setError("Végzettség megadása kötelező!");
                    education.requestFocus();
                    return;
                }

                dbRef.push().child("name").setValue(educationCat);

                Toast.makeText(AddEducation.this, "Végzettség hozzáadva!", Toast.LENGTH_SHORT).show();



                Intent intent = new Intent(AddEducation.this, AddEducation.class);
                startActivity(intent);
            }
        });

        edToDeviceCat = (Button) findViewById(R.id.edToDeviceCat);
        edToDeviceCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEducation.this, EdToDeviceCat.class);
                startActivity(intent);
            }
        });

    }
}