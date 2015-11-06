package com.example.togo.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Vector;

import wrapper.SmartSpaceException;
import wrapper.SmartSpaceKPI;
import wrapper.SmartSpaceTriplet;

/**
 * Created by ToGo on 29.10.2015.
 */
public class SmartM3 extends AsyncTask<Vector<SmartSpaceTriplet>, Void, Boolean> {
    private SmartSpaceKPI smartSpaceKPI;
    private Context activ;
    // private ProgressDialog progressDialog;

    public SmartM3(Context context) {
        this.activ = context;
    }


    @Override
    protected Boolean doInBackground(Vector<SmartSpaceTriplet>... params) {
        try {
            smartSpaceKPI=new SmartSpaceKPI(MainActivity.ip,10010,"x");
            Log.d("Smart Space KP ", " correct join to " + MainActivity.ip);
            if (params.length != 0) {
                for (int i=0; i < params[0].size(); i++) {
                    try {
                        Vector<SmartSpaceTriplet> query = smartSpaceKPI.query(new SmartSpaceTriplet(params[0].elementAt(i).getSubject(), params[0].elementAt(i).getPredicate(), null));
                        if (query.isEmpty())
                            smartSpaceKPI.insert(params[0].elementAt(i));
                        else
                            smartSpaceKPI.update(params[0].elementAt(i), new SmartSpaceTriplet(params[0].elementAt(i).getSubject(), params[0].elementAt(i).getPredicate(), null));
                    } catch (SmartSpaceException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        } catch (SmartSpaceException e) {
            return false;
        }
        finally {
            if (smartSpaceKPI!=null)
                try {
                    smartSpaceKPI.leave();
                } catch (SmartSpaceException e) {
                    e.printStackTrace();
                }
        }
        /*core=new KPICore(ip,10010,"x");
        joinResult=core.join();
        if (joinResult != null) {
            if (joinResult.isConfirmed()) { // KPI 7.1
                Log.d("Smart Space KP ", " correct join to " + ip);
                this.leaveSmart();
                return true;
            } else {
                Log.d("Smart Space KP ", " Problem with join to the " + ip+". Check your connection information");
                return false;
            }
        } else {
            Log.d("Smart Space KP ", " Problem with join to the " + ip +". Check your connection information");
            return false;
        }*/
        //Log.d("SmartM3", "ConnectIsEstablish");
        //Log.d("SmartM3", core.insert("Phone", "in", "Smart", "uri", "literal").toString());


        //return null;
    }

    protected void onPreExecute() {
        //super.onPreExecute();
        /*progressDialog = new ProgressDialog(this.activ);
        progressDialog.setMessage("Connecting...");
        progressDialog.show();*/
        Log.d("SmartM3", "BeginToConnect");
    }

    @Override
    protected void onPostExecute(Boolean result) {
        //super.onPostExecute(result);
        /*if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }*/
        Log.d("SmarM3", "Disconnect");
    }



}

