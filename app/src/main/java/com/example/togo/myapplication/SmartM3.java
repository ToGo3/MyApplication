package com.example.togo.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Vector;

import sofia_kp.KPICore;
import sofia_kp.SIBResponse;
import wrapper.SmartSpaceException;
import wrapper.SmartSpaceKPI;
import wrapper.SmartSpaceTriplet;

/**
 * Created by ToGo on 29.10.2015.
 */
public class SmartM3 extends AsyncTask<Vector<SmartSpaceTriplet>, Void, Boolean> {
    private SmartSpaceKPI smartSpaceKPI;
    private static KPICore core;
    private SIBResponse joinResult;
    //private String ip;

    SmartM3(String ip) {
        //smartSpaceKPI = new SmartSpaceKPI(ip, 10010, "x");
        /*core=new KPICore(ip,10010,"x");
        joinResult=core.join();
        if (joinResult != null) {
            if (joinResult.isConfirmed()) { // KPI 7.1
                Log.d("Smart Space KP ", " correct join to " + ip);
            } else {
                Log.d("Smart Space KP ", " Problem with join to the " + ip+". Check your connection information");
            }
        } else {
            Log.d("Smart Space KP ", " Problem with join to the " + ip +". Check your connection information");
        }*/
        //Log.e("SmartM3", core.insert("Phone", "in", "Smart", "uri", "literal").toString());

    }

    public SmartM3() {

    }



    @Override
    protected Boolean doInBackground(Vector<SmartSpaceTriplet>... params) {
        try {
            smartSpaceKPI=new SmartSpaceKPI(MainActivity.ip,10010,"x");
            Log.d("Smart Space KP ", " correct join to " + MainActivity.ip);
            if (params[0]!=null) {
                for (int i=0; i < params[0].size(); i++) {
                    smartSpaceKPI.insert(params[0].elementAt(i));
                }
            }
            return true;
            //
        } catch (SmartSpaceException e) {
            e.printStackTrace();
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
        super.onPreExecute();
        Log.d("SmartM3","BeginToConnect");
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        //this.leaveSmart();

        Log.d("SmarM3", "Disconnect");
    }



}

