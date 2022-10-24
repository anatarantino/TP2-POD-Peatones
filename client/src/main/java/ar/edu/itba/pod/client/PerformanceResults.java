package ar.edu.itba.pod.client;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PerformanceResults {
    LocalDateTime readingFileBegin;
    LocalDateTime readingFileEnd;
    LocalDateTime mapReduceBegin;
    LocalDateTime mapReduceEnd;

    private final static String READING_START = " - Inicio de la lectura del archivo";
    private final static String READING_END = " - Fin de lectura del archivo";
    private final static String MAP_REDUCE_START = " - Inicio del trabajo map/reduce";
    private final static String MAP_REDUCE_END = " - Fin del trabajo map/reduce";
    private final static String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    public void setReadingFileBegin(LocalDateTime readingFileBegin) {
        this.readingFileBegin = readingFileBegin;
    }

    public void setReadingFileEnd(LocalDateTime readingFileEnd) {
        this.readingFileEnd = readingFileEnd;
    }

    public void setMapReduceBegin(LocalDateTime mapReduceBegin) {
        this.mapReduceBegin = mapReduceBegin;
    }

    public void setMapReduceEnd(LocalDateTime mapReduceEnd) {
        this.mapReduceEnd = mapReduceEnd;
    }

    public String getReadingFileBegin() {
        return readingFileBegin.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public String getReadingFileEnd() {
        return readingFileEnd.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public String getMapReduceBegin() {
        return mapReduceBegin.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public String getMapReduceEnd() {
        return mapReduceEnd.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public void exportResults(PrintWriter pw){
        pw.println(getReadingFileBegin() + READING_START);
        pw.println(getReadingFileEnd() + READING_END);
        pw.println(getMapReduceBegin() + MAP_REDUCE_START);
        pw.println(getMapReduceEnd() + MAP_REDUCE_END);
    }
}
