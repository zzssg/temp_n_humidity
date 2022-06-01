package org.zzssg.smarthome.restserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.zzssg.smarthome.restserver.configs.InfluxDbConfig;
import org.zzssg.smarthome.restserver.events.TempAndHumidityEvent;
import org.zzssg.smarthome.restserver.model.TempAndHumidityData;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RestController
public class RestserverController {
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private InfluxDbConfig influxDbConfig;

    @GetMapping("/temp-humid")
    public String receiveTemperatureAndHumidity(
            @RequestParam(value = "id", defaultValue = "-999") int id,
            @RequestParam(value = "t", defaultValue = "-999") double t,
            @RequestParam(value = "h", defaultValue = "-999") double h) {
        try {
            log.info("New /temp-humid request received with params: id={}, temp={}, humid={}", id, t, h);
            final TempAndHumidityData tempAndHumidityData = new TempAndHumidityData(id, t, h);
            applicationEventPublisher.publishEvent(new TempAndHumidityEvent(this, tempAndHumidityData));
            return "OK";
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Failed to process this request", e);
        }
    }
}
