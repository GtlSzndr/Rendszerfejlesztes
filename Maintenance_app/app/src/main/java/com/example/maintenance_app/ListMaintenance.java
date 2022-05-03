package com.example.maintenance_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListMaintenance extends AppCompatActivity {


    private String[] maintenances;

    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private Button back;
    private TextView tv;
    private String list = "";

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_maintenance);
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("maintenance");
        tv = findViewById(R.id.maintenance_list);
        tv.setMovementMethod(new ScrollingMovementMethod());
        fetchDatas();
        back = (Button) findViewById(R.id.back4);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListMaintenance.this, WorkerHome.class);
                startActivity(intent);
            }
        });
    }
    public void fetchDatas() {
        ValueEventListener maintenanceListener = dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot educationSnapshot : snapshot.getChildren()) {
                    String _type = educationSnapshot.child("type").getValue().toString();
                    String _device = educationSnapshot.child("device").getValue().toString();
                    String _date = educationSnapshot.child("date").getValue().toString();
                    String _time = educationSnapshot.child("time").getValue().toString();
                    String _repeat = educationSnapshot.child("repeat").getValue().toString();
                    ;
                    list += _type + "\n" + _device + "\n" + _date + "\n" + _time + "\n" + _repeat + "\n\n";
                }
                tv.setText(list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
