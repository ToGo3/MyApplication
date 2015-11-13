package com.example.togo.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Vector;

import wrapper.SmartSpaceTriplet;

public class PreferenceActivity extends AppCompatActivity {
    String[] colors = {"None", "Black", "Blue", "Green", "Red", "White", "Yellow"};
    Vector<TableRow> rows;
    Vector<TextView> textViews;
    Vector<Spinner> spinners;
    Button accept;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        //TODO check changes, start with shared preferences

        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        params.setMargins(16, 16, 16, 16);

        rows = new Vector<TableRow>();
        textViews = new Vector<TextView>();
        spinners = new Vector<Spinner>();
        char letter = 'A';
        int i;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner, R.id.colors, colors);

        table.setStretchAllColumns(true);
        table.setShrinkAllColumns(true);

        for (i = 0; i < 26; i++) {
            rows.add(new TableRow(this));
            rows.elementAt(i).setLayoutParams(params);


            textViews.add(new TextView(this));
            textViews.elementAt(i).setText(String.valueOf(letter++));
            textViews.elementAt(i).setTextAppearance(this, R.style.OverlayText);
            textViews.elementAt(i).setTypeface(null, Typeface.BOLD);


            spinners.add(new Spinner(this));
            spinners.elementAt(i).setAdapter(adapter);
            spinners.elementAt(i).setId(i);
            spinners.elementAt(i).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("Spinner", "" + position + " " + parent.getId());
                    switchColor(position, parent.getId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


            rows.elementAt(i).addView(textViews.elementAt(i), params);
            rows.elementAt(i).addView(spinners.elementAt(i), new TableRow.LayoutParams(143, TableRow.LayoutParams.MATCH_PARENT));
            table.addView(rows.elementAt(i));
        }

        rows.add(new TableRow(this));
        rows.lastElement().setLayoutParams(params);

        accept = new Button(this);
        accept.setText("Accept");
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vector<SmartSpaceTriplet> query = new Vector<SmartSpaceTriplet>();
                for (int i = 0; i < 26; i++) {
                    if (spinners.elementAt(i).getSelectedItemPosition() != 0) {
                        query.add(new SmartSpaceTriplet(textViews.elementAt(i).getText().toString(), "hasColor", spinners.elementAt(i).getSelectedItem().toString()));
                    }
                }
                if (query.size() == 0) {
                    query.add(new SmartSpaceTriplet(null, "hasColor", null));
                }
                new useSmart().execute(query);
            }
        });

        Button clear = new Button(this);
        clear.setText("Clear");
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        rows.lastElement().addView(accept, params);
        rows.lastElement().addView(clear, params);
        table.addView(rows.lastElement());
    }

    private void switchColor(int selectedItem, int a) {
        switch (selectedItem) {
            case 0:
                this.textViews.elementAt(a).setTextColor(Color.GRAY);
                break;
            case 1:
                this.textViews.elementAt(a).setTextColor(Color.BLACK);
                break;
            case 2:
                this.textViews.elementAt(a).setTextColor(Color.BLUE);
                break;                                                                                                //change color of label
            case 3:
                this.textViews.elementAt(a).setTextColor(Color.GREEN);
                break;
            case 4:
                this.textViews.elementAt(a).setTextColor(Color.RED);
                break;
            case 5:
                this.textViews.elementAt(a).setTextColor(Color.WHITE);
                break;
            case 6:
                this.textViews.elementAt(a).setTextColor(Color.YELLOW);
                break;
        }
    }

    public void clear() {
        for (int i = 0; i < 26; i++) {
            spinners.elementAt(i).setSelection(0);
        }
    }

    @Override
    public void onBackPressed() {
        openQuitDialog();
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                PreferenceActivity.this);
        quitDialog.setTitle("Вы хотите сохранить изменения?");

        quitDialog.setNegativeButton("Таки да!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                accept.performClick();
                //finish();
            }
        });

        quitDialog.setPositiveButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        quitDialog.show();
    }

    class useSmart extends AsyncTask<Vector<SmartSpaceTriplet>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Vector<SmartSpaceTriplet>... params) {
            return SmartM3.insert(params[0]);
        }

        protected void onPreExecute() {
            PD.showDialog(PreferenceActivity.this, "Inserting...");
        }

        @Override
        protected void onPostExecute(Boolean result) {
            PD.hideDialog();
            if (result) {
                PD.showToast(PreferenceActivity.this, "Data was correctly inserted");
                finish();
            } else {
                PD.showToast(PreferenceActivity.this, "Error! Check your connecting");
            }
        }

    }
}
