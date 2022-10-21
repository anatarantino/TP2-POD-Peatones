package ar.edu.itba.pod.utils;

import ar.edu.itba.pod.entities.Reading;
import ar.edu.itba.pod.entities.Sensor;
import ar.edu.itba.pod.entities.Status;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class FileUtils {

    public static Stream<Sensor> getSensorStream(final Path path, final String delimiter) throws IOException {
        return Files.lines(path, StandardCharsets.UTF_8)
                .skip(1)
                .map(line -> Arrays.asList(line.split(delimiter)))
                .map(lines -> new Sensor(Integer.parseInt(lines.get(StatusFields.SENSOR_ID.value)),lines.get(StatusFields.SENSOR_DESC.value), Status.getStatusByName(lines.get(StatusFields.SENSOR_STATUS.value))));
    }

    public static Stream<Reading> getReadingsStream(final Path path, final String delimiter) throws IOException {
        return Files.lines(path, StandardCharsets.UTF_8)
                .skip(1)
                .map(line -> Arrays.asList(line.split(delimiter)))
                .map(lines -> new Reading(Integer.parseInt(lines.get(ReadingsFields.SENSOR_ID.value)), Integer.parseInt(lines.get(ReadingsFields.YEAR.value)), lines.get(ReadingsFields.MONTH.value), Integer.parseInt(lines.get(ReadingsFields.DAY.value)), lines.get(ReadingsFields.WEEKDAY.value), Integer.parseInt(lines.get(ReadingsFields.TIME.value)), Integer.parseInt(lines.get(ReadingsFields.PEDESTRIANS.value))));
    }


    public enum StatusFields {
        SENSOR_ID(0),
        SENSOR_DESC(1),
        SENSOR_STATUS(4);

        final int value;

        StatusFields(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum ReadingsFields {
        SENSOR_ID(7),
        YEAR(2),
        MONTH(3),
        DAY(4),
        WEEKDAY(5),
        TIME(6),
        PEDESTRIANS(9);

        final int value;

        ReadingsFields(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
