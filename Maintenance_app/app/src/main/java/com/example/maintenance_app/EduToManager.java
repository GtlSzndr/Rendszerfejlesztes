package com.example.maintenance_app;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class EduToManager extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Button saveEdToManager;
    private Spinner managerSpinner, educationSpinner;

    private FirebaseDatabase db;
    private DatabaseReference managerDbRef;
    private ArrayList<String> managerList;
    private ArrayAdapter<String> managerAdapter;

    private DatabaseReference educationDbRef;
    private ArrayList<String> educationList;
    private ArrayAdapter<String> educationAdapter;

    private DatabaseReference eduToManagerDbRef;
    private String manager, education;
    private HashMap<String, String> ManagerWithEducation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ed_to_manager);

        managerList = new ArrayList<String>();
        managerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, managerList);

        managerSpinner = (Spinner) findViewById(R.id.manager);
        managerSpinner.setAdapter(managerAdapter);

        educationList = new ArrayList<String>();
        educationList.add("nincs megadva végzettség");
        educationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, educationList);

        educationSpinner = (Spinner) findViewById(R.id.educationCat);
        educationSpinner.setAdapter(educationAdapter);

        ManagerWithEducation = new HashMap<>();

        db = FirebaseDatabase.getInstance();
        educationDbRef = db.getReference("educations");
        managerDbRef = db.getReference("manager");
        eduToManagerDbRef = db.getReference("educationToManager");
        fetchDatas();
        fetchEduAndDevCat();


        managerSpinner.setOnItemSelectedListener(this);

        educationSpinner.setOnItemSelectedListener(this);

        saveEdToManager = (Button) findViewById(R.id.saveEdToManager);
        saveEdToManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(education.equals("nincs megadva végzettség")) {
                    education = "";
                    Toast.makeText(EduToManager.this, "Nem történt módosítás!", Toast.LENGTH_SHORT).show();
                } else {
                    if(ManagerWithEducation.containsKey(manager)) {
                        Query query = eduToManagerDbRef.orderByChild("manager").equalTo(manager);
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
                        edToDevCat edToDevCat = new edToDevCat(manager, education);
                        eduToManagerDbRef.push().setValue(edToDevCat);
                    }

                    Toast.makeText(EduToManager.this, "Végzettség hozzárendelve!", Toast.LENGTH_SHORT).show();
                    educationList.clear();
                    educationList.add("nincs megadva végzettség");
                    managerList.clear();
                    fetchDatas();
                }

            }
        });
    }

    public void fetchEduAndDevCat() {
        ValueEventListener eduToDevCatListener = eduToManagerDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot Snapshot : snapshot.getChildren())
                    ManagerWithEducation.put(Snapshot.child("manager").getValue().toString(), Snapshot.child("education").getValue().toString());
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

        ValueEventListener deviceListener = managerDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot deviceSnapshot : snapshot.getChildren())
                    managerList.add(deviceSnapshot.child("name").getValue().toString());
                managerAdapter.notifyDataSetChanged();
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
            manager = managerSpinner.getSelectedItem().toString();
            if(ManagerWithEducation.containsKey(manager)) {
                for (int v = 0; v < educationList.size(); v++) {
                    if(ManagerWithEducation.get(manager).equals(educationList.get(v)))
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
