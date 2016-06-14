package com.example.kuba.activitytracker.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 * przechowuje historie wszystkich activity
 * <p/>
 * TODO sprawdzić czy zapisywanie działa,
 * TODO dodać jakies activity do wyświetlania, mape mogę pozniej dorobic
 */
public class History {
    private LinkedList<Log> history = new LinkedList<>();

    public void add(Log log) {
        getHistory().add(log);
    }

    public void add(CaloriesData data) {
        getHistory().add(new Log(data.getCalories(), data.getAverageSpeed(), data.getDistance(), data.getTime()));
    }

    public void add(double calories, double averageSpeed, double distance, double time) {
        getHistory().add(new Log(calories, averageSpeed, distance, time));
    }

    public void save() {
        if (!getHistory().isEmpty()) {
            FileOutputStream fos = null;
            ObjectOutputStream out = null;
            for (Log i : getHistory()) {
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

    public LinkedList<Log> getHistory() {
        return history;
    }
}
