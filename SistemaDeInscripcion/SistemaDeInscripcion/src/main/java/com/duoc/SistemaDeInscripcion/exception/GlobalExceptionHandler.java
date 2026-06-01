package com.duoc.sistemadeinscripcion.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "No encontrado",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Solicitud inválida",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {


        Map<String, String> erroresCampos = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err -> {
            String campo = ((FieldError) err).getField();
            String mensaje = err.getDefaultMessage();
            erroresCampos.put(campo, mensaje);
        });


        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now());
        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
        respuesta.put("error", "Error de validación");
        respuesta.put("campos", erroresCampos);


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Error interno del servidor",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
