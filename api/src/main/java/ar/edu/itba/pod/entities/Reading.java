package ar.edu.itba.pod.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Reading implements Serializable {
    private int readingId;
    private final LocalDateTime localDate;
    private int sensorId;
    private final String sensorName;
    private final int year;
    private String month;
    private int day;
    private final String weekday;
    private int time;
    private final int pedestrians;

    public Reading(int readingId, int sensorId, LocalDateTime localDate, String sensorName, int year, String month, int day, String weekday, int time, int pedestrians) {
        this.readingId = readingId;
        this.sensorId = sensorId;
        this.localDate = localDate;
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

    public LocalDateTime getLocalDate(){
        return localDate;
    }

    public String getSensorName() {
        return sensorName;
    }

    public int getYear() {
        return year;
    }

    public String getWeekday() {
        return weekday;
    }

    public int getPedestrians() {
        return pedestrians;
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
