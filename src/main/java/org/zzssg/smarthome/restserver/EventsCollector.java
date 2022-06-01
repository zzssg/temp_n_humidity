package org.zzssg.smarthome.restserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.zzssg.smarthome.restserver.configs.InfluxDbConfig;
import org.zzssg.smarthome.restserver.events.TempAndHumidityEvent;
import org.zzssg.smarthome.restserver.model.TempAndHumidityData;
import reactor.core.publisher.Mono;


@Slf4j
@Component
public class EventsCollector {

    private final WebClient influxdbClient;

    @Value("${influxdb.database}")
    private String influxdbDatabase;


    public EventsCollector(@Value("${influxdb.host}") String influxdbHost, @Value("${influxdb.port}") int influxdbPort) {
        log.info("InfluxDB host:port [{}:{}]", influxdbHost, influxdbPort);
        this.influxdbClient = WebClient
                .builder()
                .baseUrl("http://" + influxdbHost + ":" + influxdbPort)
                .build();
        log.info("InfluxDB web client has been initialized");
    }

    @Async
    @EventListener
    public void collectTempAndHumidityData(TempAndHumidityEvent evt) {
        log.info("New TnH event received: {}", evt.getMessage());
        log.info("InfluxDB database: {}", influxdbDatabase);
        final TempAndHumidityData thd = evt.getMessage();
        String requestPayload = "temp_and_humidity,id=" +thd.getId() + ",type=temperature value=" + thd.getTemperature() + " " + thd.getTs();
        Mono<ClientResponse> resultT = influxdbClient.post()
                .uri("/write?db=" + influxdbDatabase)
                .contentType(MediaType.TEXT_HTML)
                .bodyValue(requestPayload)
                .exchange();
        log.info("Temperature posted with result: {}", resultT.block());
        resultT.doOnSuccess((ClientResponse clientResponse) -> {
            log.info("Temperature result code received: {}", clientResponse.statusCode().name());
        });

        requestPayload = "temp_and_humidity,id=" +thd.getId() + ",type=humidity value=" + thd.getHumidity() + " " + thd.getTs();
        Mono<ClientResponse> resultH = influxdbClient.post()
                .uri("/write?db=" + influxdbDatabase)
                .contentType(MediaType.TEXT_HTML)
                .bodyValue(requestPayload)
                .exchange();
        log.info("Humidity posted with result: {}", resultH.block());
        resultH.doOnSuccess((ClientResponse clientResponse) -> {
                log.info("Humidity result code received: {}", clientResponse.statusCode().name());
        });
    }
}
