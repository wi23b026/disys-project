package at.fhtechnikum.echomsg;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="energy_message")
public class EchoMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;         // PRODUCER / USER
    private String association;  // COMMUNITY / GRID
    private double kwh;
    private LocalDateTime datetime;

    // No-args-Konstruktor für JPA
    protected EchoMessage() {
    }

    @JsonCreator
    public EchoMessage(
            @JsonProperty("type") String type,
            @JsonProperty("association") String association,
            @JsonProperty("kwh") double kwh,
            @JsonProperty("datetime") LocalDateTime datetime) {
        this.type = type;
        this.association = association;
        this.kwh = kwh;
        this.datetime = datetime;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("association")
    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    @JsonProperty("kwh")
    public double getKwh() {
        return kwh;
    }

    public void setKwh(double kwh) {
        this.kwh = kwh;
    }

    @JsonProperty("datetime")
    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
}