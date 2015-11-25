package com.example.togo.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Vector;

import wrapper.SmartSpaceException;
import wrapper.SmartSpaceKPI;
import wrapper.SmartSpaceTriplet;

/**
 * Created by ToGo on 29.10.2015.
 */
public class SmartM3 {
    private static SmartSpaceKPI smartSpaceKPI;


    public static boolean insert(Vector<SmartSpaceTriplet> triples, Context context) {
        try {
            smartSpaceKPI=new SmartSpaceKPI(MainActivity.ip,10010,"x");
            if (triples != null) {
                if (triples.elementAt(0).getSubject() != null) {
                    smartSpaceKPI.remove(new SmartSpaceTriplet(null, triples.elementAt(0).getPredicate(), null));
                    for (int i = 0; i < triples.size(); i++) {
                        try {
                            Vector<SmartSpaceTriplet> query = smartSpaceKPI.query(new SmartSpaceTriplet(triples.elementAt(i).getSubject(), triples.elementAt(i).getPredicate(), null));
                            if (query.isEmpty())
                                smartSpaceKPI.insert(triples.elementAt(i));
                            else
                                smartSpaceKPI.update(triples.elementAt(i), new SmartSpaceTriplet(triples.elementAt(i).getSubject(), triples.elementAt(i).getPredicate(), null));
                        } catch (SmartSpaceException e) {
                            e.printStackTrace();
                        }
                    }
                } else
                    smartSpaceKPI.remove(new SmartSpaceTriplet(null, triples.elementAt(0).getPredicate(), null));
            }
            Vector<SmartSpaceTriplet> query = smartSpaceKPI.query(new SmartSpaceTriplet("Word", "is", null));
            SharedPreferences pref = context.getSharedPreferences("main", context.MODE_PRIVATE);
            SharedPreferences.Editor editPref = pref.edit();
            editPref.clear();
            if (!query.isEmpty()) {
                editPref.putString("last_word", query.lastElement().getObject());
            }
            editPref.commit();
            query = smartSpaceKPI.query(new SmartSpaceTriplet(null, "hasColor", null));
            pref = context.getSharedPreferences("letters", context.MODE_PRIVATE);
            editPref = pref.edit();
            editPref.clear();
            if (!query.isEmpty()) {
                for (int i = 0; i < query.size(); i++) {
                    editPref.putString(query.elementAt(i).getSubject(), query.elementAt(i).getObject());
                }
            }
            editPref.commit();
            return true;
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
    }
}

