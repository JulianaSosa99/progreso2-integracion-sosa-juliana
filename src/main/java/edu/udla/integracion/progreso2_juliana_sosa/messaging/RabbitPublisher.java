package edu.udla.integracion.progreso2_juliana_sosa.messaging;
import edu.udla.integracion.progreso2_juliana_sosa.config.RabbitConfig;
import edu.udla.integracion.progreso2_juliana_sosa.model.CitaRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
@Service
public class RabbitPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarFacturacion(CitaRequest cita) {

        rabbitTemplate.convertAndSend(
                RabbitConfig.BILLING_QUEUE,
                cita
        );
    }

    public void publicarEvento(CitaRequest cita) {

        rabbitTemplate.convertAndSend(
                RabbitConfig.APPOINTMENTS_EXCHANGE,
                "",
                cita
        );
    }
}