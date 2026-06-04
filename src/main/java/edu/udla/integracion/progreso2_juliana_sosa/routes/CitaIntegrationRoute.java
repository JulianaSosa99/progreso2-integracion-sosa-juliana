package edu.udla.integracion.progreso2_juliana_sosa.routes;
import edu.udla.integracion.progreso2_juliana_sosa.messaging.RabbitPublisher;
import edu.udla.integracion.progreso2_juliana_sosa.model.CitaRequest;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.io.FileWriter;

@Component
public class CitaIntegrationRoute extends RouteBuilder {

    private final RabbitPublisher rabbitPublisher;

    public CitaIntegrationRoute(RabbitPublisher rabbitPublisher) {
        this.rabbitPublisher = rabbitPublisher;
    }

    @Override
    public void configure() {

        onException(Exception.class)
                .handled(true)
                .process(exchange -> {

                    Exception exception =
                            exchange.getProperty(
                                    Exchange.EXCEPTION_CAUGHT,
                                    Exception.class);

                    String mensaje =
                            java.time.LocalDateTime.now()
                                    + " | ERROR | "
                                    + exception.getMessage()
                                    + System.lineSeparator();

                    try (FileWriter writer =
                                 new FileWriter(
                                         "data/errors/citas-rechazadas.log",
                                         true)) {

                        writer.write(mensaje);

                    }
                });

        from("direct:citas")
                .routeId("cita-route")

                .log("Procesando cita: ${body}")

                // Generar CSV de auditoría
                .process(exchange -> {

                    CitaRequest cita =
                            exchange.getIn().getBody(CitaRequest.class);

                    String lineaCsv =
                            cita.getIdCita() + "," +
                                    cita.getPaciente() + "," +
                                    cita.getCorreo() + "," +
                                    cita.getEspecialidad() + "," +
                                    cita.getFechaCita() + "," +
                                    cita.getSede() + "," +
                                    cita.getValor() +
                                    System.lineSeparator();

                    try (FileWriter writer =
                                 new FileWriter(
                                         "data/outbox/auditoria-citas.csv",
                                         true)) {

                        writer.write(lineaCsv);
                    }
                })

                .log("Registro agregado al CSV correctamente")

                // Point-to-Point
                .process(exchange -> {

                    CitaRequest cita =
                            exchange.getIn().getBody(CitaRequest.class);

                    rabbitPublisher.enviarFacturacion(cita);
                })

                .log("Mensaje enviado a billing.queue")

                // Publish / Subscribe
                .process(exchange -> {

                    CitaRequest cita =
                            exchange.getIn().getBody(CitaRequest.class);

                    rabbitPublisher.publicarEvento(cita);
                })

                .log("Evento publicado en appointments.events");
    }
}