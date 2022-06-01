package org.zzssg.smarthome.restserver.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "influxdb")
public class InfluxDbConfig {
    @Getter @Setter private String host;
    @Getter @Setter private int port;
    @Getter @Setter private String database;
}
