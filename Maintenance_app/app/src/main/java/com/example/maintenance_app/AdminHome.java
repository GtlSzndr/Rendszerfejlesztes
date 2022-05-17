package com.example.maintenance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminHome extends AppCompatActivity{

    private Button addManager;
    private Button logout;
    private Button addCategories;
    private Button add_devices;
    private Button addEducation;
    private Button addWorkWorker;
    private FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


        addManager = (Button) findViewById(R.id.addManager);
        addManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, AddManager.class);
                startActivity(intent);
            }
        });

        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdminHome.this, "Sikeres kijelentkezés!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AdminHome.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addWorkWorker = (Button) findViewById(R.id.addWorkWorker);
        addWorkWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, WorkWorker.class);
                startActivity(intent);
            }
        });


        addCategories = (Button) findViewById(R.id.addCategories);
        addCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, AddCategories.class);
                startActivity(intent);
            }
        });

        add_devices = findViewById(R.id.btn_add_devices);
        add_devices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (AdminHome.this, Devices.class);
                startActivity(intent);
            }
        });

        addEducation = findViewById(R.id.addEducation);
        addEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (AdminHome.this, AddEducation.class);
                startActivity(intent);
            }
        });

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this,searchworkdata.class);
                startActivity(intent);
            }
        });
    }

}