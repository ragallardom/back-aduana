# Sistema Aduanero

Este proyecto contiene un servidor Spring Boot y un cliente JavaFX.

## Prerrequisitos

- **Java 17** o superior
- **Maven 3** (puede utilizarse el wrapper `mvnw` incluido)
- **PostgreSQL** ejecutándose localmente

## Configuración de base de datos

El archivo `application.properties` define la conexión a PostgreSQL:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/aduana
spring.datasource.username=aduodonor
spring.datasource.password=aduodonor
```

Cree una base de datos llamada `aduana` y un usuario con las credenciales indicadas o modifique dichas propiedades según corresponda.

## Configuración de la carpeta de cargas

El servidor guarda archivos en la ruta indicada en `DocumentoAdjuntoService` y los sirve de forma estática:

```
private final String BASE_PATH = "C:/Users/ragal/IdeaProjects/sistema_aduanero/uploads";
```

```
spring.web.resources.static-locations=file:///C:/Users/ragal/IdeaProjects/sistema_aduanero/uploads/
spring.mvc.static-path-pattern=/uploads/**
```

Actualice estas rutas para que apunten a un directorio existente en su máquina y asegúrese de que la propiedad `spring.web.resources.static-locations` comience con `file:///`.

## Compilar y ejecutar el servidor

Desde la raíz del proyecto ejecute:

```
./mvnw clean package
```

Esto generará `target/sistema_aduanero-0.0.1-SNAPSHOT.jar`. Para iniciar el servidor:

```
java -jar target/sistema_aduanero-0.0.1-SNAPSHOT.jar
```

Por defecto el servidor se levanta en el puerto 8080.

## Ejecutar el cliente JavaFX

El cliente se encuentra en `MainApp` dentro del paquete `cl.duoc.sistema_aduanero.javafx`. Puede iniciarse desde un IDE o con Maven utilizando:

```
./mvnw -Dexec.mainClass=cl.duoc.sistema_aduanero.javafx.MainApp exec:java
```

Asegúrese de tener JavaFX disponible en el módulo path en caso de ejecutar fuera de un IDE.

