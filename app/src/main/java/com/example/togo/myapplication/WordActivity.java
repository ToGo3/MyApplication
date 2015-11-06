package com.example.togo.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.Vector;

import wrapper.SmartSpaceTriplet;

public class WordActivity extends AppCompatActivity {
    private EditText editText;
    private SmartM3 smartM3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        editText=(EditText)findViewById(R.id.editText);
    }



    public void acceptWord(View view) {
        if (editText.length() != 0) {
            Vector<SmartSpaceTriplet> query = new Vector<SmartSpaceTriplet>();
            query.add(new SmartSpaceTriplet("Word", "is", editText.getText().toString()));
            smartM3 = new SmartM3(this);
            smartM3.execute(query);
        }
    }
}
