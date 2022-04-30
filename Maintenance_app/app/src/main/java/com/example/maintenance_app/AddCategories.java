package com.example.maintenance_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCategories extends AppCompatActivity {
    private EditText name, hierarchy;
    private Button addCategories, back;

    private FirebaseDatabase db;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categories);

        hierarchy = (EditText) findViewById(R.id.reghierarchy);
        name = (EditText) findViewById(R.id.regName);

        db = FirebaseDatabase.getInstance();


        addCategories = (Button) findViewById(R.id.regAddCategories);
        addCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef = db.getReference("device_categories");

                String _name = name.getText().toString();
                String _h = hierarchy.getText().toString();

                if (_name.isEmpty()) {
                    name.setError("Kategória név megadása kötelező!");
                    name.requestFocus();
                    return;
                }
                if (_h.isEmpty()) {
                    hierarchy.setError("Hierarchia szám megadása kötelező!");
                    hierarchy.requestFocus();
                    return;
                }

                Category category = new Category(_name, _h);
                Toast.makeText(AddCategories.this, "Kategória hozzáadva!", Toast.LENGTH_SHORT).show();

                dbRef.push().setValue(category);

                Intent intent = new Intent(AddCategories.this, AddCategories.class);
                startActivity(intent);
            }
        });

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategories.this, AdminHome.class);
                startActivity(intent);
            }
        });
    }
}