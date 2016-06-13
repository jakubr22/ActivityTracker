package com.example.kuba.activitytracker.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 * przechowuje historie wszystkich activity
 * <p/>
 * TODO klikniecie stop powinno podawać trase.
 * sprawdzić czy zapisywanie działa,
 * dodać jakies activity do wyświetlania, mape mogę pozniej dorobic
 */
public class History {
    private LinkedList<Log> history;

    public void add(Log log) {
        history.add(log);
    }

    public void add(double calories, double averageSpeed, double distance, double time) {
        history.add(new Log(calories, averageSpeed, distance, time));
    }

    public void save() {
        if (!history.isEmpty()) {
            FileOutputStream fos = null;
            ObjectOutputStream out = null;
            for (Log i : history) {
                try {
                    fos = new FileOutputStream("history");
                    out = new ObjectOutputStream(fos);
                    out.writeObject(i);

                    out.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void load() {
        FileInputStream fis = null;
        ObjectInputStream in = null;
        while (true)
            try {
                fis = new FileInputStream("history");
                in = new ObjectInputStream(fis);
                add((Log) in.readObject());
                in.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }

    }
}
