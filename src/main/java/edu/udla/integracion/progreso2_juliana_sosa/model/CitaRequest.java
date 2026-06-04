package edu.udla.integracion.progreso2_juliana_sosa.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CitaRequest {

    @NotBlank(message = "El idCita es obligatorio")
    private String idCita;

    @NotBlank(message = "El paciente es obligatorio")
    private String paciente;

    @Email(message = "Correo inválido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;

    @NotBlank(message = "La fecha de cita es obligatoria")
    private String fechaCita;

    @NotBlank(message = "La sede es obligatoria")
    private String sede;

    @Positive(message = "El valor debe ser mayor a 0")
    private Double valor;
}