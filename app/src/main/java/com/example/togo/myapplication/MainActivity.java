package com.example.togo.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public static String ip;
    InputFilter[] filters = new InputFilter[1];
    private TextView textView;
    private EditText editText;
    private SmartM3 smartM3;
    private ProgressDialog progressDialog;

    //TODO popup message; test wordinsert; options activity; test pogressdialog and etc.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.textView);
        editText=(EditText)findViewById(R.id.ip_address);
        progressDialog = new ProgressDialog(MainActivity.this);

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

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //super.onBackPressed();
        openQuitDialog();
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                MainActivity.this);
        quitDialog.setTitle("Выход: Вы уверены?");

        quitDialog.setPositiveButton("Таки да!", new DialogInterface.OnClickListener() {
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

    public void onClick(View view) {
        if (IPAddressValidator.validate(editText.getText().toString())) {

            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

            //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            ip = editText.getText().toString();
            //progressDialog = new ProgressDialog(MainActivity.this.getApplicationContext());
            /*progressDialog.setMessage("Connecting...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();*/
            PD.ShowDialog(this, "Connection...");
            Log.d("assssss", "asdsfffffdfsd");
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            smartM3 = new SmartM3(this);
            smartM3.execute();
            try {
                if(smartM3.get()){
                    Intent intent = new Intent(MainActivity.this, WordActivity.class);
                    startActivity(intent);
                    //progressDialog.dismiss();
                }
                else {
                    //progressDialog.dismiss();
                    showToast();
                    //textView.setText("Error with connection");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            showToast();
            //textView.setText("IP address is incorrect");
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
        openQuitDialog();
    }

    public void showToast() {
        Toast toast = Toast.makeText(MainActivity.this,
                "Пора покормить кота!",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
