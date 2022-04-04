package com.example.maintenance_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCategories extends AppCompatActivity {

    private EditText device_cat, education_cat;
    private Button addCategories;

    private FirebaseDatabase db;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categories);

        device_cat = (EditText) findViewById(R.id.regDevice_cat);
        education_cat = (EditText) findViewById(R.id.regEducation_cat);


        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("categories");

        addCategories = (Button) findViewById(R.id.regAddCategories);
        addCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String _device_cat = device_cat.getText().toString();
                String _education_cat = education_cat.getText().toString();

                if (_device_cat.isEmpty()) {
                    device_cat.setError("Eszköz kategória megadása kötelező!");
                    device_cat.requestFocus();
                    return;
                }
                if (_education_cat.isEmpty()){
                    education_cat.setError("Képzettség kategória megadása kötelező!");
                    education_cat.requestFocus();
                    return;
                }

                Categories categories = new Categories(_device_cat,  _education_cat);

                dbRef.push().setValue(categories);


            }
        });
    }
}
