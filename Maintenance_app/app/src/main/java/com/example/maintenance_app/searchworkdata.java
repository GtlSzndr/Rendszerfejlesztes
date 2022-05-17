package com.example.maintenance_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class searchworkdata extends AppCompatActivity {

    DatabaseReference mref;
    private ListView listWork;
    private ListView listdata;
    private AutoCompleteTextView txtsearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchworkdata);

        mref = FirebaseDatabase.getInstance().getReference("schedules");
        listdata=findViewById(R.id.lv_ww);

        txtsearch = findViewById(R.id.txtsearch);



        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                populateSearch(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mref.addListenerForSingleValueEvent(event);
    }

    private void populateSearch(DataSnapshot snapshot) {

        ArrayList<String> listworkworker = new ArrayList<>();
        if(snapshot.exists()){

            for(DataSnapshot ds:snapshot.getChildren()){
                String worker=ds.child("worker").getValue(String.class);
                listworkworker.add(worker);
            }
            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listworkworker);
            txtsearch.setAdapter(adapter);
            txtsearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String worker = txtsearch.getText().toString();
                    searchWorker(worker);
                }
            });
        }else {
            Log.d("schedules","Nincs megjelenitheto adat");
        }
    }

    private void searchWorker(String worker) {
        Query query = mref.orderByChild("worker").equalTo(worker);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    ArrayList<String> listusers= new ArrayList<>();
                    for(DataSnapshot ds:snapshot.getChildren()){
                        Schedule schedule = new Schedule(ds.child("worker").getValue(String.class), ds.child("maintenance").getValue(String.class));
                    listusers.add(schedule.getWorker()+"\n"+schedule.getMaintenance());
                    }
                    ArrayAdapter adapter= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,listusers);
                    listdata.setAdapter(adapter);
                }else{
                    Log.d("worker", "nincs megjelenitheto adat");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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