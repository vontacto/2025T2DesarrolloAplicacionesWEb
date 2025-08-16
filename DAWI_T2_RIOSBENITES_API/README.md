# CiberRecursos API (DAWI_T2_RIOSBENITES_API)

## Endpoints
- GET /api/equipos
- POST /api/equipos
- GET /api/reservaciones
- GET /api/reservaciones/{id}
- POST /api/reservaciones
- DELETE /api/reservaciones/{id}
- GET /api/reservaciones/reporte?usuario=ANA&codEquipo=1001

## Reglas de solapamiento
Una reserva **del mismo equipo y fecha** no puede cruzar horario con otra existente:
`inicio_nuevo < hora_fin_existente` y `fin_nuevo > hora_inicio_existente`.

## Pasos
1. CREATE DATABASE DB2_RIOSBENITES;
2. Configurar credenciales en `src/main/resources/application.properties`.
3. (Opcional) Ejecutar `schema.sql` y `data.sql`.
4. `mvn spring-boot:run`
5. Swagger: http://localhost:8080/swagger-ui/index.html
