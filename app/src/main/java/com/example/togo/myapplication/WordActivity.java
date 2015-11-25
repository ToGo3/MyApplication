package com.example.togo.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.Vector;

import wrapper.SmartSpaceTriplet;

public class WordActivity extends AppCompatActivity {
    private EditText editText;
    private SharedPreferences pref;
    private SharedPreferences lettersPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        editText=(EditText)findViewById(R.id.editText);
        pref = getSharedPreferences("main", MODE_PRIVATE);
        lettersPref = getSharedPreferences("letters", MODE_PRIVATE);
        String word = pref.getString("last_word", "");
        if (!word.equals("")) {
            editText.setText(word);
            editText.setSelection(editText.getText().length());
        }
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
                SharedPreferences.Editor editPref = pref.edit();
                editPref.putString("last_word", editText.getText().toString());
                editPref.commit();
            } else {
                PD.showToast(WordActivity.this, "Error! Check your connecting");
            }
        }

    }
}
