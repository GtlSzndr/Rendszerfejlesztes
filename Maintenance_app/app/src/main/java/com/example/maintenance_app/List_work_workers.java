package com.example.maintenance_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class List_work_workers extends AppCompatActivity {

    private FirebaseDatabase db;
    private DatabaseReference mref;
    private ListView listWorkdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_work_workers);
        listWorkdata = findViewById(R.id.lv_ww);
        mref = FirebaseDatabase.getInstance().getReference("schedules");

        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                readData(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        mref.addListenerForSingleValueEvent(event);
    }

    private void readData(DataSnapshot snapshot) {
        if (snapshot.exists()) {

            ArrayList<String> listworkworker = new ArrayList<>();
            for (DataSnapshot ds : snapshot.getChildren()) {

                Schedule schedule =new Schedule(ds.child("worker").getValue(String.class),ds.child("maintenance").getValue(String.class));
                listworkworker.add(schedule.getWorker()+"\n"+schedule.getMaintenance());
            }
            ArrayAdapter arrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,listworkworker);
            listWorkdata.setAdapter(arrayAdapter);
        } else {
            Log.d("schedules", "Nincs elerheto adat");
        }
    }

    public class Schedule {
        public String getWorker() {
            return worker;
        }

        public String getMaintenance() {
            return maintenance;
        }

        public String worker, maintenance;

        public Schedule(String _worker, String _maintenance) {
            this.worker = _worker;
            this.maintenance = _maintenance;
        }

    }
}