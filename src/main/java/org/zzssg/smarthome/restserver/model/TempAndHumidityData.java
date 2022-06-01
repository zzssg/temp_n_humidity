package org.zzssg.smarthome.restserver.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(includeFieldNames=true)
public class TempAndHumidityData {
    @Getter private long ts;
    @Getter @Setter private int id;
    @Getter @Setter private double temperature;
    @Getter @Setter private double humidity;

    public TempAndHumidityData(int id, double t, double h) {
        this.ts = System.currentTimeMillis();
        this.id = id;
        this.temperature = t;
        this.humidity = h;
    }
}
