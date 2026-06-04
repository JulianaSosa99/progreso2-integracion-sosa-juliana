package edu.udla.integracion.progreso2_juliana_sosa.service;

import edu.udla.integracion.progreso2_juliana_sosa.model.CitaRequest;
import org.springframework.stereotype.Service;

@Service
public class CitaValidationService {

    public boolean esValida(CitaRequest cita) {

        return cita.getIdCita() != null
                && !cita.getIdCita().isBlank()
                && cita.getPaciente() != null
                && !cita.getPaciente().isBlank()
                && cita.getCorreo() != null
                && !cita.getCorreo().isBlank()
                && cita.getEspecialidad() != null
                && !cita.getEspecialidad().isBlank()
                && cita.getFechaCita() != null
                && !cita.getFechaCita().isBlank()
                && cita.getSede() != null
                && !cita.getSede().isBlank()
                && cita.getValor() != null
                && cita.getValor() > 0;
    }

}