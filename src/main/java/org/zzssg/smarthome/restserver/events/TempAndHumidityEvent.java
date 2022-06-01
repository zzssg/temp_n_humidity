package org.zzssg.smarthome.restserver.events;

import org.springframework.context.ApplicationEvent;
import org.zzssg.smarthome.restserver.model.TempAndHumidityData;


public class TempAndHumidityEvent extends ApplicationEvent {

    private TempAndHumidityData message;
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public TempAndHumidityEvent(Object source, TempAndHumidityData message) {
        super(source);
        this.message = message;
    }

    public TempAndHumidityData getMessage() {
        return message;
    }
}
