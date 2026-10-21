# Tech & Gaming Store - Sistema Web de Inventario y Catálogo

Aplicación web desarrollada con arquitectura **Spring Boot (MVC)**, motor de plantillas **Thymeleaf**, base de datos relacional **MySQL**, diseño responsivo con **Tailwind CSS**, CSS Grid y Flexbox, estilos propios mínimos y frontend interactivo con **Vanilla JavaScript nativo (Debouncing de 4s y Endpoints Anidados)**.

---

## 1. Tecnologías Utilizadas

### Backend & Persistencia
- **Java 21 (LTS)**: Lenguaje principal de desarrollo backend.
- **Spring Boot 4.x**:
  - `spring-boot-starter-webmvc`: Controladores y arquitectura MVC.
  - `spring-boot-starter-data-jpa`: Capa de persistencia con Spring Data y Hibernate.
  - `spring-boot-starter-thymeleaf`: Motor de renderizado de vistas en el servidor.
- **MySQL 8.x**: Base de datos relacional para el catálogo de productos (`inventory_db`).
- **Maven Wrapper (`mvnw` / `mvnw.cmd`)**: Gestor de dependencias y empaquetado autónomo (no requiere tener Maven preinstalado).

### Frontend & Diseño Responsivo
- **HTML5 Semántico**: Estructura accesible (`nav`, `header`, `main`, `section`, `article`, `footer`).
- **Tailwind CSS + Vanilla CSS (`styles.css`)**:
  - Tailwind se carga por CDN y aporta clases utilitarias para navegación, formularios, botones, espaciado, grid y responsive design.
  - `styles.css` conserva únicamente estilos propios de tarjetas, badges, estados y mensajes del catálogo.
  - **CSS Flexbox**: Barra de navegación superior, contenedor de búsqueda y filtros, alineación de precios y badges.
  - **CSS Grid**: Cuadrícula adaptable para catálogo de productos y tarjetas de bienvenida.
- **Vanilla JavaScript**: Programación reactiva nativa en el cliente (sin librerías externas), manipulación directa del DOM, consumo asíncrono con `fetch`, **retardo de 4 segundos sin reinicio por tecla** y encadenamiento de **Endpoints Anidados**.

### Integración Externa
- **Rick and Morty REST API** (`https://rickandmortyapi.com/api`): Consumo en tiempo real de recursos para la tienda de coleccionables.

---

## 2. Estructura y Módulos de la Aplicación

| Ruta URL | Módulo | Tecnología Principal | Descripción |
|---|---|---|---|
| `http://localhost:8080/products` | **Page 1: Products** | Spring Boot + MySQL + Vanilla JS | Catálogo de productos almacenados en MySQL. Búsqueda instantánea en vivo por nombre y descripción utilizando Vanilla JS nativo. |
| `http://localhost:8080/products-1` | **Page 2: REST API** | Vanilla JS + REST API | Coleccionables oficiales de Rick & Morty. Incorpora **retardo controlado de 4 segundos** y **3 Endpoints Anidados**. |

---

## 3. Funcionalidades Destacadas

### A. Diseño Responsivo con Media Queries (PC y Móviles)
- **En Pantallas de Escritorio (PC):** Tailwind muestra cuadrículas multilínea con CSS Grid, barra de navegación horizontal y buscador en una sola fila compacta con Flexbox.
- **En Dispositivos Móviles (Smartphones):** Las clases responsive de Tailwind apilan la navbar y el buscador, y cambian la cuadrícula a una sola columna legible.

### B. Búsqueda con Retardo Controlado de 4 Segundos
- Ubicada en la página **Products 1** (`products-1.js`).
- **Cómo funciona el retardo:** Al escribir la primera letra, se inicia una cuenta regresiva de exactamente 4 segundos. El temporizador corre de forma continua mientras el usuario sigue escribiendo (sin reiniciar el reloj en cada letra), permitiéndole completar el término de búsqueda en una ventana de 4 segundos sin tener que esperar 8, 12 o 16 segundos acumulados.
- Al cumplirse los 4 segundos, se dispara la consulta HTTP con el texto completo ingresado. Si el campo se limpia por completo, el retardo se cancela y se recargan los personajes de inmediato.
- Incluye una barra de cuenta regresiva visual (`countdown-box`) que notifica los segundos restantes (4... 3... 2... 1...).

### C. Consumo de 3 Endpoints Anidados
- **Endpoint 1 (`/character`)**: Consulta inicial que obtiene los personajes, genera las tarjetas, calcula dinámicamente precios y asigna badges de stock.
- **Endpoint 2 (`/location`)**: Al hacer clic en una tarjeta, se ejecuta una petición anidada al recurso `origin.url` para obtener el planeta o dimensión de procedencia.
- **Endpoint 3 (`/episode`)**: Dentro de la resolución de la ubicación, se anida una tercera petición al recurso `episode[0]` para certificar el episodio de debut original del coleccionable.

### D. Catálogo Local con MySQL y Vanilla JavaScript
- El controlador (`ProductController.java`) envía la lista de productos desde MySQL a la plantilla Thymeleaf.
- El script `products.js` utiliza JavaScript nativo (`document.getElementById`, `addEventListener("input")`, `dataset`) para filtrar instantáneamente en el cliente por nombre y descripción sin recargar la página.

---

## 4. Requisitos Previos

Para ejecutar y replicar este proyecto en cualquier computadora se requiere:
1. **Java Development Kit (JDK 21 LTS)** instalado y configurado en las variables de entorno (`JAVA_HOME` y `PATH`).
2. **Servidor MySQL 8.x** activo en el puerto estándar `3306`.
3. **Conexión a Internet** (requerida para que la página *Products 1* consuma la API externa).
4. No es necesario instalar Maven por separado; el proyecto incluye los ejecutables del **Maven Wrapper** (`mvnw` y `mvnw.cmd`).

---

## 5. Guía Paso a Paso para Replicar el Proyecto en Otra PC

### Paso 1: Copiar o Clonar el Proyecto
Descargue o copie la carpeta raíz del proyecto (`BaseProyect`) a la nueva computadora.

### Paso 2: Configurar las Credenciales de MySQL
1. Abra el archivo de configuración ubicado en:
   ```text
   src/main/resources/application.properties
   ```
2. Verifique o ajuste las credenciales según la instalación local de MySQL:
   ```properties
   spring.application.name=inventory
   server.port=8080

   # Conexión a MySQL (crea la base de datos automáticamente si no existe)
   spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
   spring.datasource.username=${DB_USERNAME}
   spring.datasource.password=${DB_PASSWORD}

   # Hibernate DDL
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.open-in-view=false

   # Desactivar cache de plantillas para desarrollo
   spring.thymeleaf.cache=false
   ```

> **Nota:** Con el parámetro `createDatabaseIfNotExist=true` y `ddl-auto=update`, Spring Boot creará la base de datos `inventory_db` y la tabla `products` de forma 100% automática al iniciar.

### Paso 3: Compilar y Ejecutar la Aplicación

Abra una terminal o consola de comandos en la carpeta raíz del proyecto y ejecute:

#### En Windows (PowerShell / CMD):
```powershell
.\mvnw.cmd spring-boot:run
```

#### En Linux o macOS:
```bash
chmod +x mvnw
./mvnw spring-boot:run
```

#### Desde un IDE (VS Code, IntelliJ IDEA, Eclipse o NetBeans):
1. Abrir la carpeta raíz como proyecto Maven.
2. Navegar hasta `src/main/java/com/inventory/InventoryApplication.java`.
3. Hacer clic derecho y seleccionar **Run** / **Debug**.

### Paso 4: Abrir la Aplicación en el Navegador
Una vez que en la consola se muestre el mensaje `Started InventoryApplication`, abra cualquier navegador web:
- **Page 1 - Productos MySQL (Vanilla JS):** [http://localhost:8080/products](http://localhost:8080/products)
- **Page 2 - Productos API Externa (Retardo 4s + Endpoints Anidados):** [http://localhost:8080/products-1](http://localhost:8080/products-1)

---

## 6. Estructura del Código Fuente

```text
BaseProyect/
|-- pom.xml
|-- mvnw / mvnw.cmd
|-- src/main/java/com/inventory/
|   |-- InventoryApplication.java
|   |-- controller/
|   |   |-- ApiProductsController.java
|   |   |-- ProductController.java
|   |-- model/Product.java
|   |-- repository/ProductRepository.java
|   |-- service/ProductService.java
|-- src/main/resources/
|   |-- application.properties
|   |-- static/css/styles.css
|   |-- static/js/products.js
|   |-- static/js/products-1.js
|   |-- templates/products.html
|   |-- templates/products-1.html
```
## 7. Solución de Problemas Frecuentes

1. **Error: `Port 8080 was already in use`**
   - Otra instancia de Java o aplicación está usando el puerto 8080.
   - En Windows PowerShell, finalice el proceso ejecutando:
     ```powershell
     Stop-Process -Id (Get-NetTCPConnection -LocalPort 8080).OwningProcess -Force
     ```
   - O cambie el puerto en `application.properties` modificando `server.port=8081`.

2. **Error: `Access denied for user 'root'@'localhost'`**
   - La contraseña de MySQL en `application.properties` no coincide con la configurada en su motor de MySQL local.
   - Ajuste el valor `spring.datasource.password` con su contraseña correcta.

3. **Error en Linux/macOS de permisos con Maven Wrapper:**
   - Asigne permisos de ejecución al script: `chmod +x mvnw`.
