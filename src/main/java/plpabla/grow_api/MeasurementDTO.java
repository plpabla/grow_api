package plpabla.grow_api;

import lombok.Data;

@Data
public class MeasurementDTO {
    Integer id;
    String boardName;
    String uid;
    Integer timestamp;
    Double temperature;
    Double humidity;
    Double pressure;
    Integer luminance;
    Integer moistureA;
    Integer moistureB;
    Integer moistureC;
    Double voltage;

    public MeasurementDTO(Integer id, String boardName, String uid, String timestamp,
        Double temperature, Double humidity, Double pressure, Integer luminance,
        Integer moistureA, Integer moistureB, Integer moistureC, Double voltage) {
        this.id = id;
        this.boardName = boardName;
        this.uid = uid;
        this.timestamp = 0;
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
