package ar.edu.itba.pod.entities;

import java.io.Serializable;

public class Reading implements Serializable {
    private int sensorId;
    private int year;
    private String month;
    private int day;
    private String weekday;
    private int time;
    private int pedestrians;

    public Reading(int sensorId, int year, String month, int day, String weekday, int time, int pedestrians) {
        this.sensorId = sensorId;
        this.year = year;
        this.month = month;
        this.day = day;
        this.weekday = weekday;
        this.time = time;
        this.pedestrians = pedestrians;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
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

        if (sensorId != reading.sensorId) return false;
        if (year != reading.year) return false;
        if (day != reading.day) return false;
        if (time != reading.time) return false;
        if (pedestrians != reading.pedestrians) return false;
        if (month != null ? !month.equals(reading.month) : reading.month != null) return false;
        return weekday != null ? weekday.equals(reading.weekday) : reading.weekday == null;
    }

    @Override
    public int hashCode() {
        int result = sensorId;
        result = 31 * result + year;
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + day;
        result = 31 * result + (weekday != null ? weekday.hashCode() : 0);
        result = 31 * result + time;
        result = 31 * result + pedestrians;
        return result;
    }
}
