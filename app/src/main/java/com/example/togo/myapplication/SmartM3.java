package com.example.togo.myapplication;

import java.util.Vector;

import wrapper.SmartSpaceException;
import wrapper.SmartSpaceKPI;
import wrapper.SmartSpaceTriplet;

/**
 * Created by ToGo on 29.10.2015.
 */
public class SmartM3 {
    private static SmartSpaceKPI smartSpaceKPI;


    public static boolean insert(Vector<SmartSpaceTriplet> triples) {
        try {
            smartSpaceKPI=new SmartSpaceKPI(MainActivity.ip,10010,"x");
            if (triples != null) {
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
            }
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

