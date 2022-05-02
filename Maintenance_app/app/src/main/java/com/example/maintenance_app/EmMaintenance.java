package com.example.maintenance_app;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class EmMaintenance extends AppCompatActivity {
    private FirebaseDatabase db;
    private DatabaseReference deviceDbRef;
    private DatabaseReference maintenanceDbRef;
    private HashMap<String, String> devices;
    private ArrayList<String> deviceList;
    private ArrayAdapter<String> deviceAdapter;
    private Spinner deviceSpinner;
    private EditText date;
    private EditText time;
    private EditText error;
    private Button button;
    private Button back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_em_maintenance);
        super.onCreate(savedInstanceState);
        db = FirebaseDatabase.getInstance();
        deviceDbRef = db.getReference("devices");
        maintenanceDbRef = db.getReference("maintenance");

        fetchDevices();
        deviceList = new ArrayList<String>();
        deviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, deviceList);

        deviceSpinner = (Spinner) findViewById(R.id.devices);
        date = (EditText) findViewById(R.id.em_date);
        time = (EditText) findViewById(R.id.em_time);
        error = (EditText) findViewById(R.id.em_error);
        button = findViewById(R.id.button);
        Toast.makeText(EmMaintenance.this, date.getText(), Toast.LENGTH_LONG);
        Toast.makeText(EmMaintenance.this, time.getText(), Toast.LENGTH_LONG);
        deviceSpinner.setAdapter(deviceAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String _date = date.getText().toString();
                String _time = time.getText().toString();
                String _error = error.getText().toString();
                String _device = deviceSpinner.getSelectedItem().toString();

                if (_date.isEmpty()) {
                    date.setError("A dátum megadása kötelező!");
                    date.requestFocus();
                    return;
                }

                if (_time.isEmpty()) {
                    time.setError("Az idő megadása kötelező!");
                    time.requestFocus();
                    return;
                }

                if (_error.isEmpty()) {
                    error.setError("A hiba megadása kötelező!");
                    error.requestFocus();
                    return;
                }

                Maintenance maintenance = new Maintenance(_device, _date, _time, "Emergency", "Once");

                Toast.makeText(EmMaintenance.this, "Rendkívüli karbantartás hozzáadva!", Toast.LENGTH_SHORT).show();

                maintenanceDbRef.push().setValue(maintenance);

                Intent intent = new Intent(EmMaintenance.this, EmMaintenance.class);
                startActivity(intent);

            }
        });
        back = (Button) findViewById(R.id.back2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmMaintenance.this, WorkerHome.class);
                startActivity(intent);
            }
        });
    }
    public void fetchDevices() {
        ValueEventListener eduToDevCatListener = deviceDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot Snapshot : snapshot.getChildren())
                    deviceList.add(Snapshot.child("name").getValue().toString());
                deviceAdapter.notifyDataSetChanged();            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}
