package com.example.togo.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Deque;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    public static String ip;
    private InputFilter[] filters = new InputFilter[1];
    private TextView textView;
    private EditText editText;
    private SharedPreferences pref;
    private Deque<String> deque;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.ip_address);
        pref = getSharedPreferences("main", MODE_PRIVATE);


        deque = new LinkedList<String>();
        setDeque();

        if (!deque.isEmpty()) {
            ip = deque.getFirst();
            editText.setText(ip);
            editText.setSelection(editText.getText().length());
        }

        //TODO: корректное отображение списка. в edittext по нажатию. тесты

        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);

        while (!deque.isEmpty()) {
            TextView tv = new TextView(this);
            tv.setText(deque.pop());
            layout.addView(tv);
        }

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
                        for (String split : splits) {
                            if (Integer.valueOf(split) > 255) {
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
        openQuitDialog();
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                MainActivity.this);
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

    public void onClick(View view) {
        if (isOnline()) {
            if (IPAddressValidator.validate(editText.getText().toString())) {
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                ip = editText.getText().toString();
                new useSmart().execute();
            } else
                PD.showToast(MainActivity.this, "Incorrect IP address!");
        } else
            PD.showToast(MainActivity.this, "No internet connection!");
    }


    public void setTextView(String s) {
        textView.setText(s);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void exit(View view) {
        openQuitDialog();
    }

    public void setDeque() {
        for (int i = 4; i >= 0; i--) {
            if (!pref.getString("ip_" + i, "").isEmpty())
                deque.addFirst(pref.getString("ip_" + i, ""));
        }
    }


    class useSmart extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return SmartM3.insert(null, MainActivity.this);
        }

        protected void onPreExecute() {
            PD.showDialog(MainActivity.this, "Connecting...");
        }

        @Override
        protected void onPostExecute(Boolean result) {
            PD.hideDialog();
            if (result) {
                setDeque();
                if (deque.contains(ip)) {
                    deque.removeLastOccurrence(ip);
                }
                deque.addFirst(ip);
                if (deque.size() > 5) {
                    deque.removeLast();
                }

                SharedPreferences.Editor editPref = pref.edit();
                for (int i = 0; i < deque.size(); i++) {
                    editPref.putString("ip_" + i, deque.peekFirst());
                    //editPref.commit();
                }
                deque.clear();
                editPref.commit();
                Intent intent = new Intent(MainActivity.this, WordActivity.class).addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                PD.showToast(MainActivity.this, "Error! Check your connecting!");
            }
        }

    }
}
