package entities;

import java.io.Serializable;
import java.util.Objects;

public class Sensor implements Serializable {
    private final int id;
    private final String description;
    private final Status status;

    public Sensor(int id, String description, Status status) {
        this.id = id;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor s = (Sensor) o;
        return id == s.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
