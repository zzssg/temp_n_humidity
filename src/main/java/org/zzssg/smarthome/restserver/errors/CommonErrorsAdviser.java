package org.zzssg.smarthome.restserver.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestControllerAdvice
public class CommonErrorsAdviser {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorEntity> handleBadRequest(MethodArgumentTypeMismatchException e, HttpServletRequest request, HttpSession session) {
        log.error("CommonErrorsAdviser reported INTERNAL_SERVER_ERROR caused by {}", e.getClass());
        ErrorEntity errorEntity = new ErrorEntity(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Incorrect input received",
                request.getRequestURI());
        return new ResponseEntity(errorEntity, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorEntity> handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request, HttpSession session) {
        log.warn("The URI requested [{}] is not served by this application", request.getRequestURI());
        ErrorEntity errorEntity = new ErrorEntity(
                HttpStatus.NOT_FOUND,
                "The URI you have reached is not in service at this time (404)",
                request.getRequestURI());
        return new ResponseEntity<>(errorEntity, HttpStatus.NOT_FOUND);
    }
}
