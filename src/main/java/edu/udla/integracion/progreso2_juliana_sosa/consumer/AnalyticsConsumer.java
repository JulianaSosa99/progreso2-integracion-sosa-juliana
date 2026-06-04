package edu.udla.integracion.progreso2_juliana_sosa.consumer;
import edu.udla.integracion.progreso2_juliana_sosa.model.CitaRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsConsumer {

    @RabbitListener(queues = "analytics.queue")
    public void procesarAnalitica(CitaRequest cita) {

        System.out.println(
                "[ANALITICA] Evento registrado: "
                        + cita.getIdCita()
        );
    }
}