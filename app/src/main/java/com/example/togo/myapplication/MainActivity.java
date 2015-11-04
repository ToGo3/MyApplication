package com.example.togo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private int i=0;
    private EditText editText;
    public static String ip;
    private SmartM3 smartM3;
    private static ProgressBar progressBar;

    InputFilter[] filters = new InputFilter[1];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.textView);
        editText=(EditText)findViewById(R.id.ip_address);
        smartM3 = new SmartM3();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        filters[0] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       android.text.Spanned dest, int dstart, int dend) {
                if (end > start) {
                    String destTxt = dest.toString();
                    String resultingTxt = destTxt.substring(0, dstart)
                            + source.subSequence(start, end)
                            + destTxt.substring(dend);
                    if (!resultingTxt
                            .matches("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) {
                        return "";
                    } else {
                        String[] splits = resultingTxt.split("\\.");
                        for (int i = 0; i < splits.length; i++) {
                            if (Integer.valueOf(splits[i]) > 255) {
                                return "";
                            }
                        }
                    }
                }
                return null;
            }

        };


        editText.setFilters(filters);
    }





    public void onClick(View view) {
       // i++;
        if(editText.length()!=0) {
            ip=editText.getText().toString();
            smartM3.execute();
            try {
                if(smartM3.get()){
                    Intent intent = new Intent(MainActivity.this, WordActivity.class);
                    startActivity(intent);
                }
                else {
                    editText.setText("Error with connection");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        /*textView.setText(editText.getText());
        try {
            smartM3=new SmartM3(editText.getText().toString());
        } catch (SmartSpaceException e) {
            //e.printStackTrace();
            //Log.e("SmartM3", e.toString());
        }
        finally {
            smartM3.leaveSmart();
        }*/
    }

    public void setTextView(String s){
        textView.setText(s);
    }

    public void exit(View view) {
        finish();
    }

    public static void setProgressBarVisible() {
        if (progressBar.getVisibility() == ProgressBar.VISIBLE)
            progressBar.setVisibility(ProgressBar.INVISIBLE);
        else
            progressBar.setVisibility(ProgressBar.VISIBLE);
    }
}
