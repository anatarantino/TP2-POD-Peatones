package ar.edu.itba.pod.entities;

import java.io.Serializable;
import java.util.Objects;

public class Reading implements Serializable {
    private int readingId;
    private int sensorId;
    private String sensorName;
    private int year;
    private String month;
    private int day;
    private String weekday;
    private int time;
    private int pedestrians;

    public Reading(int readingId, int sensorId, String sensorName, int year, String month, int day, String weekday, int time, int pedestrians) {
        this.readingId = readingId;
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.year = year;
        this.month = month;
        this.day = day;
        this.weekday = weekday;
        this.time = time;
        this.pedestrians = pedestrians;
    }

    public int getReadingId() {
        return readingId;
    }

    public void setReadingId(int readingId) {
        this.readingId = readingId;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPedestrians() {
        return pedestrians;
    }

    public void setPedestrians(int pedestrians) {
        this.pedestrians = pedestrians;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reading)) return false;

        Reading reading = (Reading) o;

        return readingId == reading.readingId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(readingId);
    }
}
