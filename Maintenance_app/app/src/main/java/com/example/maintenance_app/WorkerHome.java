package com.example.maintenance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WorkerHome extends AppCompatActivity {

    private Button logout;
    private Button em_maintenance;
    private Button re_maintenance;
    private Button list_maintenance;

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
        em_maintenance = (Button) findViewById(R.id.addEmMaintenance);
        em_maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WorkerHome.this, EmMaintenance.class);
                startActivity(intent);
            }
        });
        re_maintenance = (Button) findViewById(R.id.addReMaintenance);
        re_maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WorkerHome.this, ReMaintenance.class);
                startActivity(intent);
            }
        });
        list_maintenance = (Button) findViewById(R.id.listMaintenance);
        list_maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WorkerHome.this, ListMaintenance.class);
                startActivity(intent);
            }
        });
    }


}