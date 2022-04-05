package com.example.maintenance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WorkerHome extends AppCompatActivity {

    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_home);

        logout = (Button) findViewById(R.id.logout2);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WorkerHome.this, "Sikeres kijelentkezés!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(WorkerHome.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}