# CURP Suite Java

Migración a Java de la librería [CURPSuite](https://github.com/jacobszpz/CURPSuite) originalmente escrita en Python.

## Descripción

CURP Suite es una librería para el análisis y validación de la CURP (Clave Única de Registro de Población) mexicana.

Con esta librería puedes:
- Validar una CURP
- Extraer información como fecha de nacimiento, sexo y entidad federativa
- Verificar la correspondencia de una CURP con un nombre o apellidos

## Requisitos

- Java 11 o superior
- Maven (para compilación)

## Instalación

### Usando Maven

Añade la dependencia a tu archivo `pom.xml`:

```xml
<dependency>
    <groupId>com.curpsuite</groupId>
    <artifactId>curpsuite</artifactId>
    <version>2.6.1</version>
</dependency>
```

### Compilación Manual

1. Clona el repositorio
2. Compila con Maven:

```bash
mvn clean package
```

3. El JAR resultante estará en el directorio `target/`

## Uso Básico

### Como Librería

```java
import com.curpsuite.CURP;

public class Example {
    public static void main(String[] args) {
        // Crear una instancia de CURP
        CURP curp = new CURP("SABC560626MDFLRN01");
        
        // Obtener información de la CURP
        System.out.println("Fecha de nacimiento: " + curp.getFechaNacimiento());
        System.out.println("Sexo: " + curp.getSexo());
        System.out.println("Entidad: " + curp.getEntidad());
        
        // Validar contra un nombre
        if (curp.nombreValido("CONSUELO")) {
            System.out.println("El nombre coincide con la CURP");
        }
        
        // Obtener información en formato JSON
        String json = curp.toJson();
        System.out.println(json);
    }
}
```

### Uso desde Línea de Comandos

```bash
java -jar curpsuite-2.6.1-jar-with-dependencies.jar SABC560626MDFLRN01
```

Opciones disponibles:
```
-n, --nombre NOMBRE              Nombre de pila para validar la CURP
-p, --primer-apellido APELLIDO   Primer apellido para validar la CURP
-s, --segundo-apellido APELLIDO  Segundo apellido para validar la CURP
-c, --nombre-completo NOMBRE     Nombre completo para validar la CURP
```

## Diferencias con la Versión Python

- Se utilizan convenciones de nomenclatura de Java (camelCase para métodos y variables)
- Uso de `java.time.LocalDate` en lugar de `datetime.date`
- Implementación de excepciones siguiendo la jerarquía de Java
- Método `toJson()` en lugar de `json()`

## Licencia

Este proyecto está licenciado bajo la Licencia Pública General de GNU v2.0 - consulta el archivo LICENSE para más detalles.

## Autor

* Versión Python original: Jacob Sánchez Pérez
* Migración a Java: [Tu nombre]