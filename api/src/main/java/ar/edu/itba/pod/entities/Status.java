package ar.edu.itba.pod.entities;

public enum Status {
    ACTIVE("A"),
    REMOVED("R"),
    INACTIVE("I");

    private final String statusName;

    Status(String statusName){
        this.statusName = statusName;
    }

    public static Status getStatusByName(String name){
        for(Status status : Status.values()){
            if(status.getStatusName().equals(name)){
                return status;
            }
        }
        throw new IllegalArgumentException();
    }

    public String getStatusName() {
        return statusName;
    }

}
