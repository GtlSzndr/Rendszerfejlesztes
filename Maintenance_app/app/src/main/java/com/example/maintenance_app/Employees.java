package com.example.maintenance_app;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Employees {
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private DataSnapshot ds;

    public void Employees(){
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("workers");
    }

    public Task<Void> add(Manager manager){
        return dbRef.push().setValue(manager);
    }

    public String get(){
        return ds.getValue(String.class);
    }

    public DatabaseReference getRef(){
        return dbRef;
    }
}
