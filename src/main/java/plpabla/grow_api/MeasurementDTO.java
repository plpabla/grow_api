package plpabla.grow_api;

import lombok.Data;

@Data
public class MeasurementDTO {
    Integer id;
    String boardName;
    String uid;
    String timestamp;
    Double temperature;
    Double humidity;
    Double pressure;
    Double luminance;
    Double moistureA;
    Double moistureB;
    Double moistureC;
    Double voltage;

    public MeasurementDTO(Integer id, String boardName, String uid, String timestamp,
        Double temperature, Double humidity, Double pressure, Double luminance,
                          Double moistureA, Double moistureB, Double moistureC, Double voltage) {
        this.id = id;
        this.boardName = boardName;
        this.uid = uid;
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.luminance = luminance;
        this.moistureA = moistureA;
        this.moistureB = moistureB;
        this.moistureC = moistureC;
        this.voltage = voltage;
    };

}
