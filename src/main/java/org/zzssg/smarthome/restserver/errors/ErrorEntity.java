package org.zzssg.smarthome.restserver.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@ToString(includeFieldNames=true)
public class ErrorEntity {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Getter @Setter private LocalDateTime timestamp;
    @Getter @Setter private HttpStatus httpStatus;
    @Getter @Setter private String message;
    @Getter @Setter private String path;

    public ErrorEntity(HttpStatus status, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.httpStatus = status;
        this.message = message;
        this.path = path;
    }
}
