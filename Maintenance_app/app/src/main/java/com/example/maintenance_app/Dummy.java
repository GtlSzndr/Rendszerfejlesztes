package com.example.maintenance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Dummy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
    }




    public class Schedule {
        public String worker, maintenance;

        public Schedule(String _worker, String _maintenance){
            this.worker = _worker;
            this.maintenance = _maintenance;
        }
    }
}