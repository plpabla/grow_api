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
        public double luminance;
        public double moisture_a;
        public double moisture_b;
        public double moisture_c;
        public double voltage;
    }
}
