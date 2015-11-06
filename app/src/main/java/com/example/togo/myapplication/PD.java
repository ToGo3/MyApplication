package com.example.togo.myapplication;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by ToGo on 06.11.2015.
 */
public class PD {
    static ProgressDialog pd;

    public static void ShowDialog(Context context, String Message) {
        pd = new ProgressDialog(context);
        pd.setTitle("Загрузка");
        pd.setMessage(Message);
        pd.setCancelable(false);
        pd.show();
    }

    public static void HideDialog() {
        pd.hide();
        pd.dismiss();
    }
}
