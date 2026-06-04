package edu.udla.integracion.progreso2_juliana_sosa.exception;


import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String manejarValidaciones(
            MethodArgumentNotValidException ex) {

        try {

            String errores = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(error ->
                            error.getField() + ": " +
                                    error.getDefaultMessage())
                    .collect(Collectors.joining(" | "));

            try (FileWriter writer =
                         new FileWriter(
                                 "data/errors/citas-rechazadas.log",
                                 true)) {

                writer.write(
                        LocalDateTime.now()
                                + " | VALIDACION | "
                                + errores
                                + System.lineSeparator()
                );
            }

        } catch (Exception ignored) {
        }

        return "Solicitud inválida";
    }
}