package edu.udla.integracion.progreso2_juliana_sosa.consumer;
import edu.udla.integracion.progreso2_juliana_sosa.model.CitaRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class BillingConsumer {

    @RabbitListener(queues = "billing.queue")
    public void procesarFacturacion(CitaRequest cita) {

        System.out.println(
                "[FACTURACION] Generando factura para "
                        + cita.getIdCita()
        );
    }
}