package edu.udla.integracion.progreso2_juliana_sosa.config;

import org.springframework.amqp.core.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
@Configuration
public class RabbitConfig {

    public RabbitConfig() {
        System.out.println("RABBIT CONFIG CARGADA");
    }
    public static final String BILLING_QUEUE = "billing.queue";

    public static final String APPOINTMENTS_EXCHANGE = "appointments.events";

    public static final String NOTIFICATIONS_QUEUE = "notifications.queue";

    public static final String ANALYTICS_QUEUE = "analytics.queue";

    @Bean
    public Queue billingQueue() {
        return new Queue(BILLING_QUEUE, true);
    }

    @Bean
    public Queue notificationsQueue() {
        return new Queue(NOTIFICATIONS_QUEUE, true);
    }

    @Bean
    public Queue analyticsQueue() {
        return new Queue(ANALYTICS_QUEUE, true);
    }

    @Bean
    public FanoutExchange appointmentsExchange() {
        return new FanoutExchange(APPOINTMENTS_EXCHANGE);
    }

    @Bean
    public Binding notificationsBinding(
            Queue notificationsQueue,
            FanoutExchange appointmentsExchange) {

        return BindingBuilder
                .bind(notificationsQueue)
                .to(appointmentsExchange);
    }

    @Bean
    public Binding analyticsBinding(
            Queue analyticsQueue,
            FanoutExchange appointmentsExchange) {

        return BindingBuilder
                .bind(analyticsQueue)
                .to(appointmentsExchange);
    }
    @Bean
    public CommandLineRunner testRabbit() {
        return args -> {
            System.out.println("RabbitMQ configurado");
        };
    }
    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
    @Bean
    public CommandLineRunner verificarRabbit(
            AmqpAdmin admin,
            Queue billingQueue,
            Queue notificationsQueue,
            Queue analyticsQueue,
            FanoutExchange appointmentsExchange) {

        return args -> {

            admin.declareQueue(billingQueue);
            admin.declareQueue(notificationsQueue);
            admin.declareQueue(analyticsQueue);

            admin.declareExchange(appointmentsExchange);

            System.out.println("COLAS Y EXCHANGE DECLARADOS");
        };

    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}