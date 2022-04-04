package com.example.maintenance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHome extends AppCompatActivity{

    private Button addManager;
    private Button logout;
    private Button addCategories;

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
                Intent intent = new Intent(AdminHome.this, MainActivity.class);
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

    }

}