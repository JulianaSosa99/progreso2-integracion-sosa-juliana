package edu.udla.integracion.progreso2_juliana_sosa.controller;

import edu.udla.integracion.progreso2_juliana_sosa.model.CitaRequest;
import jakarta.validation.Valid;
import org.apache.camel.ProducerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final ProducerTemplate producerTemplate;

    public CitaController(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    @PostMapping
    public ResponseEntity<String> registrarCita(
            @Valid @RequestBody CitaRequest cita) {

        producerTemplate.sendBody("direct:citas", cita);

        return ResponseEntity.ok(
                "Cita enviada al flujo de integración: "
                        + cita.getIdCita()
        );
    }
}