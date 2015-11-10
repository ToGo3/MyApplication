package com.example.togo.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.Vector;

import wrapper.SmartSpaceTriplet;

public class WordActivity extends AppCompatActivity {
    private EditText editText;

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
            new useSmart().execute(query);
        }
    }

    class useSmart extends AsyncTask<Vector<SmartSpaceTriplet>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Vector<SmartSpaceTriplet>... params) {
            return SmartM3.insert(params[0]);
        }

        protected void onPreExecute() {
            PD.showDialog(WordActivity.this, "Inserting...");
        }

        @Override
        protected void onPostExecute(Boolean result) {
            PD.hideDialog();
            if (result) {
                PD.showToast(WordActivity.this, "Data was correctly inserted");
            } else {
                PD.showToast(WordActivity.this, "Error! Check your connecting");
            }
        }

    }
}
