package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.tranlate.TranlateService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(this);

        Intent intent = new Intent(getBaseContext(), TranlateService.class);
        startService(intent);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getBaseContext(), "test", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(), TranlateService.class);
        startService(intent);
    }
}
