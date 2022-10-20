package entities;

public enum Status {
    ACTIVE("A"),
    REMOVED("R"),
    INACTIVE("I");

    private final String statusName;

    Status(String statusName){
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

}
