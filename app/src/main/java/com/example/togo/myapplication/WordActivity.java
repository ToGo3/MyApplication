package com.example.togo.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        smartM3 = new SmartM3();

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //super.onBackPressed();
        openQuitDialog();
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                WordActivity.this);
        quitDialog.setTitle("Выход: Вы уверены?");

        quitDialog.setPositiveButton("Таки да!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Smart to service
          //      SmartM3.leaveSmart();
                finish();
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });

        quitDialog.show();
    }

    public void acceptWord(View view) {
        Vector<SmartSpaceTriplet> query=new Vector<SmartSpaceTriplet>();
        query.add(new SmartSpaceTriplet("Word", "is", editText.getText().toString()));
        smartM3.execute(query);
    }
}
