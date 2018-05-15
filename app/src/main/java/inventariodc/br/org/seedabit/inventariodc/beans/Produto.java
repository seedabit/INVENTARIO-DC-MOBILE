package inventariodc.br.org.seedabit.inventariodc.beans;


import java.io.Serializable;

public class Produto {
    private String barcode;
    private String description;
    private String responsable;
    private String location;
    private String status;
    private String observation;

    public Produto() { }

    public Produto(String barcode, String description, String responsable, String location, String status, String observation) {
        this.barcode = barcode;
        this.description = description;
        this.responsable = responsable;
        this.location = location;
        this.status = status;
        this.observation = observation;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "barcode='" + barcode + '\'' +
                ", description='" + description + '\'' +
                ", responsable='" + responsable + '\'' +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                ", observation=" + observation +
                '}';
    }
}
