package com.example.maintenance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Devices extends AppCompatActivity {
    private EditText dname, devid,categs, location,description;
    private Button addDevice;


    private FirebaseDatabase db =FirebaseDatabase.getInstance() ;
    private DatabaseReference root = db.getReference().child("devices");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        dname = (EditText) findViewById(R.id.txt_devname);
        devid = (EditText) findViewById(R.id.txt_devid);
        categs = (EditText) findViewById(R.id.txtcategs);
        location = (EditText) findViewById(R.id.txtloc);
        description = (EditText) findViewById(R.id.txtdesc);
        addDevice = (Button) findViewById(R.id.btn_save);


        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Devices.this, "Device data uploaded!", Toast.LENGTH_SHORT).show();
                String name = dname.getText().toString();
                String Dev_ID = devid.getText().toString();
                String Category = categs.getText().toString();
                String Location = location.getText().toString();
                String Description = description.getText().toString();

                HashMap<String, String>devMap = new HashMap<>();

                devMap.put("name", name);
                devMap.put("Dev_ID", Dev_ID);
                devMap.put("Category", Category);
                devMap.put("Location", Location);
                devMap.put("Description", Description);

                root.setValue(devMap);
            }
        });

    }
}