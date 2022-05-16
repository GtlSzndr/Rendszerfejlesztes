package com.example.maintenance_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChangeState extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button changeState;
    private Spinner deviceSpinner, stateSpinner;
    private TextView error;

    private FirebaseDatabase db;
    private DatabaseReference deviceDbRef;
    private DatabaseReference stateDbRef;

    private ArrayList<String> deviceList;
    private ArrayAdapter<String> deviceAdapter;

    private ArrayList<String> stateList;
    private ArrayAdapter<String> stateAdapter;

    private HashMap<String, String> deviceStates;
    private HashMap<String, String> errors;

    private String selectedDevice, selectedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_state);

        deviceList = new ArrayList<String>();
        deviceList.add("Nincs kiválasztva feladat");
        deviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, deviceList);
        deviceSpinner = (Spinner) findViewById(R.id.dev);
        deviceSpinner.setAdapter(deviceAdapter);

        error = (TextView) findViewById(R.id.error);

        error.setText("---");
        errors = new HashMap<>();
        deviceStates = new HashMap<>();

        stateList = new ArrayList<String>();
        stateList.add("---");
        stateList.add("Elfogadva");
        stateList.add("Elutasítva");
        stateList.add("Megkezdve");
        stateList.add("Befejezve");

        stateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, stateList);
        stateSpinner = (Spinner) findViewById(R.id.state);
        stateSpinner.setAdapter(stateAdapter);

        db = FirebaseDatabase.getInstance();
        deviceDbRef = db.getReference("maintenance");
        stateDbRef = db.getReference("states");

        fetchDatas();
        fetchDeviceState();

        deviceSpinner.setOnItemSelectedListener(this);

        changeState = (Button) findViewById(R.id.changeState);
        changeState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedState = stateSpinner.getSelectedItem().toString();
                if (!selectedDevice.equals(deviceList.get(0))){
                    if(deviceStates.containsKey(selectedDevice)) {
                        Query query = stateDbRef.orderByChild("device").equalTo(selectedDevice);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot devSnapshot: dataSnapshot.getChildren()) {
                                    devSnapshot.getRef().child("state").setValue(selectedState);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                throw error.toException();
                            }
                        });
                    } else {
                        states changeState = new states(selectedDevice, selectedState);
                        stateDbRef.push().setValue(changeState);
                    }

                    Toast.makeText(ChangeState.this, "Státusz módosítva!", Toast.LENGTH_SHORT).show();
                    error.setText("---");
                    deviceList.clear();
                    deviceList.add("Nincs kiválasztva feladat");
                    deviceSpinner.setSelection(0);
                    stateSpinner.setSelection(0);
                    fetchDatas();
                    fetchDeviceState();
                } else {
                    Toast.makeText(ChangeState.this, "Nincs kiválasztva feladat!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void fetchDatas(){
        ValueEventListener deviceListener = deviceDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot deviceSnapshot : snapshot.getChildren()){
                    errors.put(deviceSnapshot.child("device").getValue().toString(), deviceSnapshot.child("error").getValue().toString());
                    deviceList.add(deviceSnapshot.child("device").getValue().toString());
                }
                deviceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }

    public void fetchDeviceState(){
        ValueEventListener devStateListener = stateDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot Snapshot : snapshot.getChildren()){
                    deviceStates.put(Snapshot.child("device").getValue().toString(), Snapshot.child("state").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.dev){
            selectedDevice = deviceSpinner.getSelectedItem().toString();

            if(errors.containsKey(selectedDevice)) {
                // hiba beallitasa a devicehoz
                for (Map.Entry<String, String> entry : errors.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if(key.equals(selectedDevice)){
                        error.setText(value);
                    }
                }
                // allapot
                if(deviceStates.containsKey(selectedDevice)) {
                    for (Map.Entry<String, String> entry : deviceStates.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        int idx = 0;
                        if(key.equals(selectedDevice)){
                            for (int a = 0; a < stateList.size(); a++){
                                if (value.equals(stateList.get(a))){
                                    stateSpinner.setSelection(idx);
                                }
                                idx++;
                            }
                        }
                    }
                } else {
                    stateSpinner.setSelection(0);
                }

            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }
}