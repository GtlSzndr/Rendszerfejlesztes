package com.example.maintenance_app;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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


public class WorkWorker extends AppCompatActivity {
    private FirebaseDatabase db;
    private DatabaseReference deviceDbRef;
    private DatabaseReference maintenanceDbRef;
    private DatabaseReference employeeDbRef;
    private DatabaseReference dbRef;
    private DatabaseReference device_categoriesDbRef;
    private DatabaseReference edToDeviceCatDbRef;
    private ArrayList<String> maintenanceList;
    private ArrayAdapter<String> maintenanceAdapter;
    private ArrayList<String> workerList;
    private ArrayAdapter<String> workerAdapter;
    private Spinner maintenanceSpinner;
    private Spinner workerSpinner;
    private Button button;
    private HashMap<String, String> devices = new HashMap<String, String>();
    private HashMap<String, String> device_categories = new HashMap<String, String>();
    private HashMap<String, String> edToDeviceCat = new HashMap<String, String>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_worker);
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("schedules");
        deviceDbRef = db.getReference("devices");
        maintenanceDbRef = db.getReference("maintenance");
        device_categoriesDbRef = db.getReference("device_categories");
        employeeDbRef = db.getReference("employees");
        edToDeviceCatDbRef = db.getReference("educationToDeviceCategory");
        button = findViewById(R.id.button_addWorkWorker);
        fetchDevices();

        maintenanceList = new ArrayList<String>();
        maintenanceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, maintenanceList);

        maintenanceSpinner = (Spinner) findViewById(R.id.work_select);

        workerList = new ArrayList<String>();
        workerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, workerList);

        workerSpinner = (Spinner) findViewById(R.id.worker_select);

        maintenanceSpinner.setAdapter(maintenanceAdapter);
        workerSpinner.setAdapter(workerAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maintenance = maintenanceSpinner.getSelectedItem().toString();
                String device = maintenance.substring(maintenance.indexOf("Device:")+8, maintenance.indexOf("Error:")-1);
                String Category = devices.get(device);
                String category_name = device_categories.get(Category);
                String education = edToDeviceCat.get(category_name);
                String worker = workerSpinner.getSelectedItem().toString();
                String edu_2 = worker.substring(worker.indexOf("Education:")+11);
                if (education.equals(edu_2)){
                    Schedule i = new Schedule(worker, maintenance);
                    dbRef.push().setValue(i);
                    Toast.makeText(WorkWorker.this, "Adat feltöltve", Toast.LENGTH_LONG);

                }
                else{
                    Toast.makeText(WorkWorker.this, "A karbantartó nem kezelheti a gépet", Toast.LENGTH_LONG);
                }

            }
        });




    }
    public void fetchDevices() {
        ValueEventListener maintenanceListener = maintenanceDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot Snapshot : snapshot.getChildren())
                    maintenanceList.add("Date: " + Snapshot.child("date").getValue().toString() + " Device: " +Snapshot.child("device").getValue().toString() + " Error: " + Snapshot.child("error").getValue().toString() + " Repeat: " +Snapshot.child("repeat").getValue().toString() +" Time: " +Snapshot.child("time").getValue().toString() +" Type: " +Snapshot.child("type").getValue().toString());
                maintenanceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        ValueEventListener workerListener = employeeDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot Snapshot : snapshot.getChildren())
                    workerList.add("Username: " + Snapshot.child("username").getValue().toString() + " Education: " +Snapshot.child("education").getValue().toString());
                workerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        ValueEventListener deviceListener = deviceDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot Snapshot : snapshot.getChildren())
                    devices.put(Snapshot.child("name").getValue().toString(),Snapshot.child("Category").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        ValueEventListener deviceCatListener = device_categoriesDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot Snapshot : snapshot.getChildren())
                    device_categories.put(Snapshot.child("id").getValue().toString(),Snapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        ValueEventListener edListener = edToDeviceCatDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot Snapshot : snapshot.getChildren())
                    edToDeviceCat.put(Snapshot.child("deviceCategory").getValue().toString(),Snapshot.child("education").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}
