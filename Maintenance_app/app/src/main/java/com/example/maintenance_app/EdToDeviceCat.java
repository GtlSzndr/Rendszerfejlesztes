package com.example.maintenance_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class EdToDeviceCat extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Button saveEdToCat;
    private Spinner deviceSpinner, educationSpinner;

    private FirebaseDatabase db;
    private DatabaseReference deviceDbRef;
    private ArrayList<String> deviceList;
    private ArrayAdapter<String> deviceAdapter;

    private DatabaseReference educationDbRef;
    private ArrayList<String> educationList;
    private ArrayAdapter<String> educationAdapter;

    private DatabaseReference eduToDevCatDbRef;
    private String deviceCat, education;
    private HashMap<String, String> devCatWithEducation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ed_to_device_cat);

        deviceList = new ArrayList<String>();
        deviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, deviceList);

        deviceSpinner = (Spinner) findViewById(R.id.deviceCat);
        deviceSpinner.setAdapter(deviceAdapter);

        educationList = new ArrayList<String>();
        educationList.add("nincs megadva végzettség");
        educationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, educationList);

        educationSpinner = (Spinner) findViewById(R.id.educationSpinner);
        educationSpinner.setAdapter(educationAdapter);

        devCatWithEducation = new HashMap<>();

        db = FirebaseDatabase.getInstance();
        educationDbRef = db.getReference("educations");
        deviceDbRef = db.getReference("device_categories");
        eduToDevCatDbRef = db.getReference("educationToDeviceCategory");
        fetchDatas();
        fetchEduAndDevCat();



        deviceSpinner.setOnItemSelectedListener(this);

        educationSpinner.setOnItemSelectedListener(this);

        saveEdToCat = (Button) findViewById(R.id.saveEdToCat);
        saveEdToCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(education.equals("nincs megadva végzettség")) {
                    education = "";
                    Toast.makeText(EdToDeviceCat.this, "Nem történt módosítás!", Toast.LENGTH_SHORT).show();
                } else {
                    if(devCatWithEducation.containsKey(deviceCat)) {
                        Query query = eduToDevCatDbRef.orderByChild("deviceCategory").equalTo(deviceCat);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot devSnapshot: dataSnapshot.getChildren()) {
                                    devSnapshot.getRef().child("education").setValue(education);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                throw error.toException();
                            }
                        });
                    } else {
                        edToDevCat edToDevCat = new edToDevCat(deviceCat, education);
                        eduToDevCatDbRef.push().setValue(edToDevCat);
                    }

                    Toast.makeText(EdToDeviceCat.this, "Végzettség hozzárendelve!", Toast.LENGTH_SHORT).show();
                    educationList.clear();
                    educationList.add("nincs megadva végzettség");
                    deviceList.clear();
                    fetchDatas();
                }

            }
        });
    }

    public void fetchEduAndDevCat() {
        ValueEventListener eduToDevCatListener = eduToDevCatDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot Snapshot : snapshot.getChildren())
                    devCatWithEducation.put(Snapshot.child("deviceCategory").getValue().toString(), Snapshot.child("education").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    public void fetchDatas() {
        ValueEventListener educationListener = educationDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot educationSnapshot : snapshot.getChildren()) {
                    educationList.add(educationSnapshot.child("name").getValue().toString());
                }

                educationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        ValueEventListener deviceListener = deviceDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot deviceSnapshot : snapshot.getChildren())
                    deviceList.add(deviceSnapshot.child("name").getValue().toString());
                deviceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.deviceCat) {
            deviceCat = deviceSpinner.getSelectedItem().toString();
            if(devCatWithEducation.containsKey(deviceCat)) {
                for (int v = 0; v < educationList.size(); v++) {
                    if(devCatWithEducation.get(deviceCat).equals(educationList.get(v)))
                        educationSpinner.setSelection(v);
                }
            } else {
                educationSpinner.setSelection(0);
            }
        } else {
            education = educationSpinner.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }
}