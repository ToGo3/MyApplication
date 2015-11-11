package com.example.togo.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class PreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

// Настраиваем адаптер
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Вызываем адаптер
        spinner.setAdapter(adapter);
    }
}
