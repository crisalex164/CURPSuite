# CURPSuite Java 🇲🇽

[![JitPack](https://jitpack.io/v/crisalex164/CURPSuite.svg)](https://jitpack.io/#crisalex164/CURPSuite)
[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-GPL%20v2-blue.svg)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.html)

Librería Java para análisis y validación de la **CURP** (Clave Única de Registro de Población) mexicana.

> **Migración a Java** de la librería original [CURPSuite](https://github.com/jacobszpz/CURPSuite) creada por **Jacob Sánchez Pérez** en Python.

## ✨ Características

- 🔍 **Validación completa** de formato y estructura CURP
- 📅 **Extracción de datos**: fecha de nacimiento, sexo y entidad federativa  
- ✅ **Verificación de nombres** contra la CURP
- 🚫 **Detección de palabras altisonantes** censuradas
- 📊 **Salida JSON** para integración fácil
- 🎯 **Manejo de excepciones** específicas
- 📱 **CLI incluida** para uso desde terminal

## 🚀 Instalación

### Maven

Agrega el repositorio JitPack y la dependencia a tu `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.crisalex164</groupId>
    <artifactId>CURPSuite</artifactId>
    <version>v2.6.1</version>
</dependency>
```

### Gradle

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.crisalex164:CURPSuite:v2.6.1'
}
```

## 💻 Uso como Librería

### Ejemplo básico

```java
import com.curpsuite.CURP;
import com.curpsuite.enums.Sexo;

public class EjemploCURP {
    public static void main(String[] args) {
        try {
            // Crear instancia con CURP válida
            CURP curp = new CURP("SABC560626MDFLRN01");
            
            // Extraer información
            System.out.println("Fecha de nacimiento: " + curp.getFechaNacimiento()); // 1956-06-26
            System.out.println("Sexo: " + curp.getSexo());                           // MUJER
            System.out.println("Entidad: " + curp.getEntidad());                     // DISTRITO FEDERAL
            System.out.println("Código ISO: " + curp.getCodigoEntidad());            // MX-DIF
            System.out.println("¿Extranjero?: " + curp.esExtranjero());              // false
            
        } catch (CURPException e) {
            System.err.println("CURP inválida: " + e.getMessage());
        }
    }
}
```

### Validación con nombres

```java
// Validar CURP con nombre completo
CURP curp = new CURP("SABC560626MDFLRN01", "María Consuelo", "Sánchez", "Beltrán");

// Validar partes individuales
if (curp.nombreValido("CONSUELO")) {
    System.out.println("✅ El nombre coincide con la CURP");
}

if (curp.primerApellidoValido("SANCHEZ")) {
    System.out.println("✅ El primer apellido coincide");
}
```

### Salida JSON

```java
CURP curp = new CURP("SABC560626MDFLRN01");
String json = curp.toJson();
System.out.println(json);
```

**Salida:**
```json
{
  "curp": "SABC560626MDFLRN01",
  "fechaNacimiento": "1956-06-26",
  "sexo": "MUJER",
  "entidad": "DISTRITO FEDERAL",
  "codigoEntidad": "MX-DIF",
  "esExtranjero": false
}
```

## 🖥️ Uso desde Terminal

Compila con Maven y usa el JAR ejecutable:

```bash
mvn clean package
java -jar target/curpsuite-2.6.1-jar-with-dependencies.jar SABC560626MDFLRN01
```

### Opciones disponibles

```bash
# Validar solo la CURP
java -jar curpsuite.jar SABC560626MDFLRN01

# Validar con nombre completo
java -jar curpsuite.jar SABC560626MDFLRN01 -c "María Consuelo Sánchez Beltrán"

# Validar por partes
java -jar curpsuite.jar SABC560626MDFLRN01 -n "CONSUELO" -p "SANCHEZ" -s "BELTRAN"
```

**Parámetros:**
- `-n, --nombre`: Nombre de pila
- `-p, --primer-apellido`: Primer apellido (paterno)  
- `-s, --segundo-apellido`: Segundo apellido (materno)
- `-c, --nombre-completo`: Nombre completo

## 🔧 API Principal

### Constructor

```java
// Solo CURP
CURP curp = new CURP("SABC560626MDFLRN01");

// CURP con validación de nombres
CURP curp = new CURP("SABC560626MDFLRN01", "CONSUELO", "SANCHEZ", "BELTRAN", null);
```

### Métodos principales

| Método | Descripción | Retorno |
|--------|-------------|---------|
| `getFechaNacimiento()` | Fecha de nacimiento extraída | `LocalDate` |
| `getSexo()` | Sexo de la persona | `Sexo` (enum) |
| `getEntidad()` | Entidad federativa de nacimiento | `String` |
| `getCodigoEntidad()` | Código ISO de la entidad | `String` |
| `esExtranjero()` | Si nació en el extranjero | `boolean` |
| `nombreValido(String)` | Valida el nombre contra la CURP | `boolean` |
| `primerApellidoValido(String)` | Valida primer apellido | `boolean` |
| `segundoApellidoValido(String)` | Valida segundo apellido | `boolean` |
| `toJson()` | Convierte a JSON | `String` |

## ⚠️ Excepciones

La librería maneja excepciones específicas para diferentes tipos de errores:

```java
try {
    CURP curp = new CURP("INVALID_CURP");
} catch (CURPLengthException e) {
    // CURP no tiene 18 caracteres
} catch (CURPVerificationException e) {
    // Dígito verificador incorrecto
} catch (CURPDateException e) {
    // Fecha inválida en la CURP
} catch (CURPSexException e) {
    // Carácter de sexo inválido
} catch (CURPRegionException e) {
    // Código de región inválido
} catch (CURPException e) {
    // Error general de CURP
}
```

## 🔄 Migración desde Python

### Diferencias principales

| Python | Java |
|--------|------|
| `snake_case` | `camelCase` |
| `datetime.date` | `LocalDate` |
| `json()` | `toJson()` |
| Diccionarios | Objetos/Maps |

### Ejemplo de migración

**Python:**
```python
from curpsuite import CURP

curp = CURP("SABC560626MDFLRN01")
print(curp.fecha_nacimiento)
print(curp.json())
```

**Java:**
```java
import com.curpsuite.CURP;

CURP curp = new CURP("SABC560626MDFLRN01");
System.out.println(curp.getFechaNacimiento());
System.out.println(curp.toJson());
```

## 🛠️ Desarrollo

### Requisitos

- **Java 17+**
- **Maven 3.6+**

### Compilar

```bash
git clone https://github.com/crisalex164/CURPSuite.git
cd CURPSuite
mvn clean compile
```

### Ejecutar tests

```bash
mvn test
```

### Generar JAR

```bash
mvn clean package
```

## 📄 Licencia

Este proyecto mantiene la licencia original **GPL v2.0** - consulta el archivo [LICENSE](LICENSE) para más detalles.

## 👥 Créditos

- **🐍 Versión Python original**: [Jacob Sánchez Pérez](https://github.com/jacobszpz) - [CURPSuite](https://github.com/jacobszpz/CURPSuite)
- **☕ Migración a Java**: [crisalex164](https://github.com/crisalex164)

## 🤝 Contribuir

¡Las contribuciones son bienvenidas! Por favor:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📞 Soporte

- 🐛 **Issues**: [GitHub Issues](https://github.com/crisalex164/CURPSuite/issues)
- 📖 **Documentación**: [JavaDoc generada](https://jitpack.io/com/github/crisalex164/CURPSuite/v2.6.1/javadoc/)
- 🔗 **Versión Python original**: [jacobszpz/CURPSuite](https://github.com/jacobszpz/CURPSuite)

---

<div align="center">

**Hecho con ❤️ para la comunidad de desarrolladores mexicanos**

⭐ Si te resulta útil, ¡dale una estrella al repo!

</div>