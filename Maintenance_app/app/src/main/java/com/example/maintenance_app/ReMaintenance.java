package com.example.maintenance_app;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;

public class ReMaintenance extends AppCompatActivity{
    private FirebaseDatabase db;
    private DatabaseReference deviceDbRef;
    private DatabaseReference maintenanceDbRef;
    private HashMap<String, String> devices;
    private ArrayList<String> deviceList;
    private ArrayAdapter<String> deviceAdapter;
    private Spinner deviceSpinner;
    private Spinner spinner;
    private EditText date;
    private EditText time;
    private EditText error;
    private Button button;
    private Button back;

    String [] periods = {"heti", "havi", "negyedéves ","féléves", "éves"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_re_maintenance);
        super.onCreate(savedInstanceState);
        db = FirebaseDatabase.getInstance();
        deviceDbRef = db.getReference("devices");
        maintenanceDbRef = db.getReference("maintenance");

        fetchDevices();
        deviceList = new ArrayList<String>();
        deviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, deviceList);

        deviceSpinner = (Spinner) findViewById(R.id.devices2);
        date = (EditText) findViewById(R.id.em_date2);
        time = (EditText) findViewById(R.id.em_time2);
        error = (EditText) findViewById(R.id.em_error2);
        button = findViewById(R.id.button2);
        deviceSpinner.setAdapter(deviceAdapter);

        spinner =findViewById(R.id.periods2);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,periods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String _date = date.getText().toString();
                String _time = time.getText().toString();
                String _error = error.getText().toString();
                String _device = deviceSpinner.getSelectedItem().toString();
                String _repeat = spinner.getSelectedItem().toString();

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

                Maintenance maintenance = new Maintenance(_device, _date, _time, "Rendszeres", _repeat);

                Toast.makeText(ReMaintenance.this, "Rendszeres karbantartás hozzáadva!", Toast.LENGTH_SHORT).show();

                maintenanceDbRef.push().setValue(maintenance);

                Intent intent = new Intent(ReMaintenance.this, ReMaintenance.class);
                startActivity(intent);

            }
        });
        back = (Button) findViewById(R.id.back3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReMaintenance.this, WorkerHome.class);
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
