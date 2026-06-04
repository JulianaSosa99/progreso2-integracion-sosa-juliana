# Progreso 2 - Integración de Sistemas

## Descripción

Este proyecto implementa una solución de integración para la gestión de citas médicas utilizando Spring Boot, Apache Camel y RabbitMQ desplegado en CloudAMQP.

La aplicación expone una API REST que permite registrar citas médicas. Una vez recibida una solicitud, Apache Camel orquesta el flujo de integración para:

* Validar la información recibida.
* Registrar auditoría en archivos CSV.
* Enviar mensajes a una cola Point-to-Point para facturación.
* Publicar eventos mediante Publish/Subscribe para notificaciones y analítica.
* Registrar errores y solicitudes rechazadas.

---

## Arquitectura

La solución implementa los siguientes patrones de integración:

### Point-to-Point

Utilizado para el proceso de facturación.

```text
Cita → billing.queue → BillingConsumer
```

Cada mensaje es consumido por un único consumidor.

### Publish / Subscribe

Utilizado para distribuir eventos relacionados con las citas.

```text
appointments.events
        │
        ├── notifications.queue
        └── analytics.queue
```

Un único evento puede ser recibido por múltiples consumidores.

---

## Tecnologías Utilizadas

* Java 17
* Spring Boot 3.5.14
* Apache Camel 4.18.2
* RabbitMQ
* CloudAMQP
* Spring AMQP
* Swagger / OpenAPI
* Maven
* Lombok
* Postman
* PlantUML

---

## Estructura del Proyecto

```text
src/main/java

├── config
│   └── RabbitConfig.java
│
├── controller
│   └── CitaController.java
│
├── consumer
│   ├── BillingConsumer.java
│   ├── NotificationConsumer.java
│   └── AnalyticsConsumer.java
│
├── messaging
│   └── RabbitPublisher.java
│
├── model
│   └── CitaRequest.java
│
├── routes
│   └── CitaIntegrationRoute.java
│
├── service
│   └── CitaValidationService.java
│
└── Progreso2JulianaSosaApplication.java
```

---

## Flujo de Integración

1. El usuario envía una solicitud HTTP POST a `/api/citas`.
2. El controlador recibe la información.
3. Apache Camel procesa la solicitud mediante `direct:citas`.
4. Se registra la información en `auditoria-citas.csv`.
5. Se envía un mensaje a `billing.queue`.
6. Se publica un evento en `appointments.events`.
7. RabbitMQ distribuye el evento a:

   * `notifications.queue`
   * `analytics.queue`
8. Los consumidores procesan cada mensaje.
9. Los errores son registrados en `citas-rechazadas.log`.

---

## Configuración de RabbitMQ

Archivo `application.properties`:

```properties
spring.rabbitmq.host=cougar.rmq.cloudamqp.com
spring.rabbitmq.port=5672
spring.rabbitmq.username=USUARIO
spring.rabbitmq.password=PASSWORD
spring.rabbitmq.virtual-host=VHOST
```

---

## Endpoint Principal

### Registrar Cita

**POST**

```http
/api/citas
```

### Ejemplo de solicitud

```json
{
  "idCita": "CITA-1001",
  "paciente": "Ana Torres",
  "correo": "ana.torres@email.com",
  "especialidad": "Cardiologia",
  "fechaCita": "2026-06-15",
  "sede": "Centro Norte",
  "valor": 45.50
}
```

### Respuesta exitosa

```text
Cita enviada al flujo de integración: CITA-1001
```

---

## Auditoría

Las citas procesadas son almacenadas en:

```text
data/outbox/auditoria-citas.csv
```

Ejemplo:

```csv
CITA-1001,Ana Torres,ana.torres@email.com,Cardiologia,2026-06-15,Centro Norte,45.5
```

---

## Manejo de Errores

Los errores de procesamiento y validación son registrados en:

```text
data/errors/citas-rechazadas.log
```

---

## Documentación API

Swagger se encuentra disponible en:

```text
http://localhost:8081/swagger-ui/index.html
```

---

## Evidencias de Funcionamiento

Se verificó correctamente:

* Recepción de solicitudes REST.
* Procesamiento mediante Apache Camel.
* Generación de archivos CSV.
* Integración con CloudAMQP.
* Funcionamiento de Point-to-Point.
* Funcionamiento de Publish/Subscribe.
* Consumo de mensajes desde RabbitMQ.
* Registro de errores y auditoría.

---

## Autor

Juliana Sosa

Universidad de Las Américas (UDLA)

Integración de Sistemas
