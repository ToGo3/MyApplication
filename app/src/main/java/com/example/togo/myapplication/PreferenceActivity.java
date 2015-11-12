package com.example.togo.myapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Vector;

public class PreferenceActivity extends AppCompatActivity {
    String[] colors = {"None", "Black", "Blue", "Green", "Red", "White", "Yellow"};
    Vector<TableRow> rows;
    Vector<TextView> textViews;
    Vector<Spinner> spinners;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO set scrollable, add buttons && backmessage

        TableLayout table = new TableLayout(this);
        /*android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:paddingBottom="@dimen/activity_vertical_margin"
        android:paddingLeft="@dimen/activity_horizontal_margin"
        android:paddingRight="@dimen/activity_horizontal_margin"
        android:paddingTop="@dimen/activity_vertical_margin"*/

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        params.setMargins(16, 16, 16, 16);


        rows = new Vector<TableRow>();
        textViews = new Vector<TextView>();
        spinners = new Vector<Spinner>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner, R.id.colors, colors);

        table.setStretchAllColumns(true);
        table.setShrinkAllColumns(true);

        for (int i = 0; i < 26; i++) {
            rows.add(new TableRow(this));
            textViews.add(new TextView(this));
            textViews.elementAt(i).setText("A");
            textViews.elementAt(i).setTextAppearance(this, android.R.style.TextAppearance_Large);
            textViews.elementAt(i).setTypeface(null, Typeface.BOLD);
            spinners.add(new Spinner(this));
            spinners.elementAt(i).setAdapter(adapter);
            rows.elementAt(i).addView(textViews.elementAt(i), params);
            rows.elementAt(i).addView(spinners.elementAt(i), params);
            table.addView(rows.elementAt(i), params);
        }
        setContentView(table, params);
/*
        TableRow rowTitle = new TableRow(this);
        rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);

        TableRow rowDayLabels = new TableRow(this);
        TableRow rowHighs = new TableRow(this);
        TableRow rowLows = new TableRow(this);
        TableRow rowConditions = new TableRow(this);
        rowConditions.setGravity(Gravity.CENTER);

        TextView empty = new TextView(this);

        // title column/row
        TextView title = new TextView(this);
        title.setText("Java Weather Table");

        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 6;

        rowTitle.addView(title, params);

        // labels column
        TextView highsLabel = new TextView(this);
        highsLabel.setText("Day High");
        highsLabel.setTypeface(Typeface.DEFAULT_BOLD);

        TextView lowsLabel = new TextView(this);
        lowsLabel.setText("Day Low");
        lowsLabel.setTypeface(Typeface.DEFAULT_BOLD);

        TextView conditionsLabel = new TextView(this);
        conditionsLabel.setText("Conditions");
        conditionsLabel.setTypeface(Typeface.DEFAULT_BOLD);

        rowDayLabels.addView(empty);
        rowHighs.addView(highsLabel);
        rowLows.addView(lowsLabel);
        rowConditions.addView(conditionsLabel);

        // day 1 column
        TextView day1Label = new TextView(this);
        day1Label.setText("Feb 7");
        day1Label.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TextView day1High = new TextView(this);
        day1High.setText("28°F");
        day1High.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView day1Low = new TextView(this);
        day1Low.setText("15°F");
        day1Low.setGravity(Gravity.CENTER_HORIZONTAL);

        ImageView day1Conditions = new ImageView(this);
        day1Conditions.setImageResource(R.drawable.hot);

        rowDayLabels.addView(day1Label);
        rowHighs.addView(day1High);
        rowLows.addView(day1Low);
        rowConditions.addView(day1Conditions);





        super.onCreate(savedInstanceState);
        TableLayout tableLayout=new TableLayout(this);
        TableLayout.LayoutParams tabLayoutParam = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        setContentView(tableLayout,tabLayoutParam);

        TextView textView=new TextView(this);
        textView.setText("A");
        textView.setLayoutParams(tabLayoutParam);*/

        /*final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner, R.id.colors, colors);
        spinner.setAdapter(adapter);*/
    }
}
