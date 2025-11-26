# 🧬 Proyecto ADN Mutante

API REST desarrollada en **Java + Spring Boot** para determinar si una
persona es mutante a partir de su secuencia de ADN.\
El sistema detecta patrones de **cuatro letras consecutivas iguales**
(A, T, C, G) en dirección **horizontal, vertical y diagonal**,
registrando cada verificación en una base de datos H2 y permitiendo
consultar estadísticas globales.

## 🏛️ Arquitectura del Proyecto

El proyecto adopta una **Arquitectura en Capas**, garantizando
legibilidad, escalabilidad y mantenimiento:

-   **Controller:** Manejo de solicitudes HTTP.
-   **Service:** Lógica de negocio y orquestación del flujo.
-   **Repository:** Acceso a la base de datos mediante JPA.
-   **Entity:** Representación del modelo persistido.
-   **DTO:** Entrada y salida de datos.
-   **Validator:** Validaciones custom de ADN.
-   **Exception Handler:** Manejo unificado de errores.

## 📂 Estructura del Proyecto

``` text
src/
├── main/java/com/example/appMutante/
│   ├── AppMutanteApplication.java
│   ├── Config/
│   │   └── SwaggerConfig.java
│   ├── Controllers/
│   │   └── MutantController.java
│   ├── DTO/
│   │   ├── DnaRequest.java
│   │   ├── StatsResponse.java
│   │   └── ErrorResponse.java
│   ├── Entity/
│   │   └── DnaRecord.java
│   ├── Exception/
│   │   ├── GlobalExceptionHandler.java
│   │   └── DnaHashCalculationException.java
│   ├── Repository/
│   │   └── DnaRecordRepository.java
│   ├── Service/
│   │   ├── MutantDetector.java
│   │   ├── MutantService.java
│   │   └── StatsService.java
│   └── Validator/
│       ├── DnaValidator.java
│       └── ValidDna.java
│
└── main/resources/
    └── application.properties

test/java/com/example/appMutante/
    ├── Controllers/MutantControllerTest.java
    └── Service/
        ├── MutantDetectorTest.java
        ├── MutantServiceTest.java
        └── StatsServiceTest.java
```

## 🛠️ Tecnologías Utilizadas

-   **Java 17**
-   **Spring Boot 3**
-   **Spring Web / JPA**
-   **H2 Database (In-Memory)**
-   **Lombok**
-   **Swagger - OpenAPI**
-   **Gradle**
-   **JUnit 5 + Mockito**

## 🚀 Cómo Ejecutar el Proyecto

### Ejecutar en Local

1.  Clonar el repositorio.\
2.  Abrir en IntelliJ
3.  Ejecutar la clase:


    AppMutanteApplication.java

4.  El servicio quedará activo en:


    http://localhost:8080

### Ejecutar Tests

``` bash
./gradlew test
```

## 📘 Swagger API Docs

-   **Local:** http://localhost:8080/swagger-ui/index.html\
-   **Producción (Render):** https://app-mutante.onrender.com

## 🧪 Casos de Prueba (POST /mutant)

### ✔️ Mutante (Horizontal + Diagonal)

``` json
{
  "dna": [
    "ATGCGA",
    "CAGTGC",
    "TTATGT",
    "AGAAGG",
    "CCCCTA",
    "TCACTG"
  ]
}
```

### ✔️ Mutante (Vertical)

``` json
{
  "dna": [
    "AAAAGA",
    "CAGTGC",
    "TTATGT",
    "AGAAGG",
    "CACCTA",
    "TCACTG"
  ]
}
```

### ❌ Humano (solo una secuencia)

``` json
{
  "dna": [
    "ATGCGA",
    "CAGTGC",
    "TTATTT",
    "AGACGG",
    "GCGTCA",
    "TCACTG"
  ]
}
```

### ❌ Humano (sin secuencias)

``` json
{
  "dna": [
    "ATGC",
    "CAGT",
    "TTAT",
    "AGAC"
  ]
}
```

### ❌ Inválido (caracteres incorrectos)

``` json
{
  "dna": [
    "ATXC",
    "CAGT",
    "TTAT",
    "AGAC"
  ]
}
```

## 💾 Acceso a H2 Console

1.  Ir a: http://localhost:8080/h2-console\
2.  Configurar:
    -   **URL:** `jdbc:h2:mem:testdb`\
    -   **User:** `sa`\
    -   **Password:** *(vacío)*\
3.  Conectar.

## 📊 Recursos Adicionales

-   **Diagrama de Secuencia:**\
    https://drive.google.com/file/d/1aZ9MXM75vvoZ_gLJoRmfmxUnPohfYfwi/view
-   **Casos de Prueba (POST /mutant)**\
    https://drive.google.com/file/d/1rcdvdqrQYKDktsHMRrAiOaiUTjySNMY1/view?usp=sharing

## 🎓 Conclusión

Este proyecto aplica buenas prácticas de arquitectura, validación,
testing y documentación.
Se destacan:

-   **Algoritmo optimizado** con detección temprana.
-   **Validaciones robustas** mediante anotaciones personalizadas.
-   **Persistencia eficiente**, evitando re-procesamientos mediante
    hashing.
-   **Cobertura de pruebas** para garantizar la confiabilidad del
    sistema.
-   **Arquitectura limpia y extensible**, ideal para escalar la solución
    en el futuro.

## 👤 Autor

**Florencia Antonella Artaza Atencio**\
**Legajo:** 50779\
**Curso:** 3K9 -- Desarrollo de Software\
**Universidad Tecnológica Nacional (UTN)**
