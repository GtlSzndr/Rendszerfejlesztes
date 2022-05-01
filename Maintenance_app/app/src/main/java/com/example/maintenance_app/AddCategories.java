package com.example.maintenance_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCategories extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText name, hierarchy,normtime,instructions;
    private Button addCategories, back;

    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private Spinner spinner;
    String item;
    Category category;
String [] periods = {"Válassz egyet!","heti", "havi", "negyedéves ","féléves", "éves"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categories);

        hierarchy = (EditText) findViewById(R.id.reghierarchy);
        name = (EditText) findViewById(R.id.regName);
        normtime = (EditText)findViewById(R.id.normtime);
        instructions = (EditText)findViewById(R.id.stepbystep);
        db = FirebaseDatabase.getInstance();

        spinner =findViewById(R.id.maintenperiod);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,periods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        addCategories = (Button) findViewById(R.id.regAddCategories);
        addCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef = db.getReference("device_categories");

                String _name = name.getText().toString();
                String _h = hierarchy.getText().toString();
                String _nt = normtime.getText().toString();
                item = spinner.getSelectedItem().toString();
                String _its = instructions.getText().toString();



                Category category = new Category(_name, _h,_nt,item, _its);

                if(item =="Válassz egyet!"){
                    category.setPeriods("heti");
                }else {
                    category.setPeriods(item);
                }
                if (_name.isEmpty()) {
                    name.setError("Kategória név megadása kötelező!");
                    name.requestFocus();
                    return;
                }
                if (_h.isEmpty()) {
                    hierarchy.setError("Hierarchia szám megadása kötelező!");
                    hierarchy.requestFocus();
                    return;
                }
                if (_nt.isEmpty()) {
                    normtime.setError("Normaidő megadása kötelező!");
                    normtime.requestFocus();
                    return;
                }
                if (_its.isEmpty()) {
                    instructions.setError("Instrukciók megadása kötelező!");
                    instructions.requestFocus();
                    return;
                }


                Toast.makeText(AddCategories.this, "Kategória hozzáadva!", Toast.LENGTH_SHORT).show();

                dbRef.push().setValue(category);

                Intent intent = new Intent(AddCategories.this, AddCategories.class);
                startActivity(intent);
            }
        });

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategories.this, AdminHome.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = spinner.getSelectedItem().toString();
        Toast.makeText(AddCategories.this, (item),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}