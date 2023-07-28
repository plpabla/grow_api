package plpabla.grow_api;

import lombok.Data;

import java.time.Instant;

@Data
public class MeasGrow {
    public String nickname;
    public String model;
    public String uid;
    public Instant timestamp;
    public Readings readings;

    public static class Readings {
        public double temperature;
        public double humidity;
        public double pressure;
        public int light;
        public int moisture_1;
        public int moisture_2;
        public int moisture_3;
        public double voltage;
    }
}
