package edu.udla.integracion.progreso2_juliana_sosa.consumer;

import edu.udla.integracion.progreso2_juliana_sosa.model.CitaRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    @RabbitListener(queues = "notifications.queue")
    public void procesarNotificacion(CitaRequest cita) {

        System.out.println(
                "[NOTIFICACIONES] Correo enviado a "
                        + cita.getCorreo()
        );
    }
}