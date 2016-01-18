package com.example.togo.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Vector;

import wrapper.SmartSpaceTriplet;

public class WordActivity extends AppCompatActivity {
    private EditText editText;
    private SharedPreferences lettersPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        editText=(EditText)findViewById(R.id.editText);
        lettersPref = getSharedPreferences("letters", MODE_PRIVATE);

        ListOfLastUse.showList(ListOfLastUse.getList(this, "word_"), (ListView) findViewById(R.id.lvWord), editText);


        //TODO:тесты

    }



    public void acceptWord(View view) {
        if (editText.length() != 0) {
            int sum = 0;
            for (char i : editText.getText().toString().toCharArray()) {
                if (lettersPref.getString(String.valueOf(i), "").equals("")) {
                    PD.showToast(WordActivity.this, "Letter " + i + " has no color");
                    break;
                } else
                    sum++;
            }
            if (sum == editText.length()) {
                Vector<SmartSpaceTriplet> query = new Vector<SmartSpaceTriplet>();
                query.add(new SmartSpaceTriplet("Word", "is", editText.getText().toString()));
                new useSmart().execute(query);
            }

        } else
            PD.showToast(WordActivity.this, "Please insert the world");
    }

    public void options(View view) {
        Intent intent = new Intent(WordActivity.this, PreferenceActivity.class);
        startActivity(intent);
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                WordActivity.this);
        quitDialog.setTitle("Выход: Вы уверены?");

        quitDialog.setPositiveButton("Да!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        quitDialog.show();
    }

    @Override
    public void onBackPressed() {
        openQuitDialog();
    }

    class useSmart extends AsyncTask<Vector<SmartSpaceTriplet>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Vector<SmartSpaceTriplet>... params) {
            return SmartM3.insert(params[0], WordActivity.this);
        }

        protected void onPreExecute() {
            PD.showDialog(WordActivity.this, "Inserting...");
        }

        @Override
        protected void onPostExecute(Boolean result) {
            PD.hideDialog();
            if (result) {
                PD.showToast(WordActivity.this, "Data was correctly inserted");

                ListOfLastUse.setList("word_", editText.getText().toString());
                ListOfLastUse.showList(ListOfLastUse.getList(WordActivity.this, "word_"), (ListView) findViewById(R.id.lvWord), editText);
            } else {
                PD.showToast(WordActivity.this, "Error! Check your connecting");
            }
        }

    }
}
