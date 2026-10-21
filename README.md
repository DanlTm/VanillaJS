# Tech and Gaming Store

This is a web app for a tech product store.

The app has two pages:

- A local product page with data from MySQL.
- An external API page with Rick and Morty data.

## Main tools

### Backend

- Java 21.
- Spring Boot 4.1.1.
- Spring MVC.
- Spring Data JPA.
- Hibernate.
- Thymeleaf.
- MySQL 8.x.
- Maven Wrapper.

### Frontend

- HTML5.
- CSS3.
- CSS Flexbox.
- CSS Grid.
- Tailwind CSS from a CDN.
- Plain JavaScript.

### External service

The second page uses the Rick and Morty API:

```text
https://rickandmortyapi.com/api
```

## Pages

### Page 1: Local products

URL:

```text
http://localhost:8080/products
```

This page:

- Reads products from MySQL.
- Shows the product name, price, stock, and description.
- Uses Thymeleaf to build the page.
- Has a live search by name and description.
- Shows the number of visible products.
- Has a reset button.

### Page 2: External API

URL:

```text
http://localhost:8080/products-1
```

This page shows Rick and Morty characters as store products.

It uses three API resources:

1. `/character` loads the characters.
2. `/location` loads the character origin.
3. `/episode` loads the first episode.

The last two requests are made when the user opens a product card. The episode
request is made inside the location request flow.

The page also has:

- A name search.
- A status filter.
- A four-second search delay.
- A reset button.
- A product counter.
- A small browser cache for repeated searches.

## Hardware requirements

These values are for local development.

| Resource | Minimum | Recommended |
|---|---:|---:|
| CPU | 2 cores | 4 cores |
| RAM | 4 GB | 8 GB |
| Free disk space | 2 GB | 5 GB |
| Screen | 1280 x 720 | 1920 x 1080 |
| Internet | Needed for Page 2 | Stable connection |

A dedicated graphics card is not needed.

## Software requirements

Install these tools:

- JDK 21.
- MySQL Server 8.x.
- A modern web browser.
- Git, if you need to download or update the project.

The project includes Maven Wrapper files. A separate Maven installation is not
needed.

## Project structure

```text
BaseProyect/
|-- pom.xml
|-- mvnw
|-- mvnw.cmd
|-- README.md
|-- src/
|   |-- main/
|   |   |-- java/com/inventory/
|   |   |   |-- InventoryApplication.java
|   |   |   |-- controller/
|   |   |   |   |-- ApiProductsController.java
|   |   |   |   |-- ProductController.java
|   |   |   |-- model/Product.java
|   |   |   |-- repository/ProductRepository.java
|   |   |   |-- service/ProductService.java
|   |   |-- resources/
|   |       |-- application.properties
|   |       |-- static/css/styles.css
|   |       |-- static/js/products.js
|   |       |-- static/js/products-1.js
|   |       |-- templates/products.html
|   |       |-- templates/products-1.html
|   |-- test/
|       |-- java/com/inventory/InventoryApplicationTests.java
```

## Backend layers

- `controller` receives web requests.
- `service` manages product actions.
- `repository` reads data with JPA.
- `model` defines the `Product` database object.
- `templates` contains the HTML pages.
- `static` contains CSS and JavaScript files.

## Database setup

The database settings are in:

```text
src/main/resources/application.properties
```

The app uses this database URL:

```text
jdbc:mysql://localhost:3306/inventory_db
```

The database name is `inventory_db`.

The app reads the user and password from environment variables:

```properties
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

Do not write real passwords in the source code.

### Windows PowerShell

Set the database values in the current terminal:

```powershell
$env:DB_USERNAME = "your_user"
$env:DB_PASSWORD = "your_password"
```

### Git Bash

Set the database values in the current terminal:

```bash
export DB_USERNAME="your_user"
export DB_PASSWORD="your_password"
```

The app can create the database when the MySQL user has the needed rights.
Hibernate updates the table structure during development.

## Run on Windows

Open PowerShell in the project folder:

```powershell
$env:DB_USERNAME = "your_user"
$env:DB_PASSWORD = "your_password"
.\mvnw.cmd spring-boot:run
```

Open these URLs in your browser:

```text
http://localhost:8080/products
http://localhost:8080/products-1
```

## Run on Linux or macOS

Open a terminal in the project folder:

```bash
export DB_USERNAME="your_user"
export DB_PASSWORD="your_password"
chmod +x mvnw
./mvnw spring-boot:run
```

## Build and test

Run tests on Windows:

```powershell
.\mvnw.cmd test
```

Run tests on Linux or macOS:

```bash
./mvnw test
```

Create the application package on Windows:

```powershell
.\mvnw.cmd package
```

Create the application package on Linux or macOS:

```bash
./mvnw package
```

The project has a Spring Boot context test in:

```text
src/test/java/com/inventory/InventoryApplicationTests.java
```

## Common problems

### MySQL connection error

Check these points:

1. MySQL Server is running.
2. MySQL uses port `3306`.
3. `DB_USERNAME` is set.
4. `DB_PASSWORD` is set.
5. The user can access `inventory_db`.

### Port 8080 is busy

Stop the program that uses port `8080`, or change `server.port` in
`application.properties`.

### Page 2 has no data

Check these points:

1. The computer has Internet access.
2. The Rick and Morty API is available.
3. The browser allows the API requests.

## Safe project rules

- Do not save passwords in the repository.
- Do not save private keys or access tokens in the repository.
- Use environment variables for local secrets.
- Review files before you send them to a remote repository.
