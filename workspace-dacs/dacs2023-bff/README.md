---
# ms-dacs2013-bff 

Microservicio de backend for frontend.

# Levantar en entorno local
```
mvn clean spring-boot:run
```

Opcionalmente se puede agregar el paŕametro:

-P local

```


### API de Prueba

Para chequear que la aplicacion levanto correctamente (Procedemos a consumir el servicio "/metrics/health"):

```
http://localhost:9001/metrics/health
```
