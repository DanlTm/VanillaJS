================================================================================
TECH & GAMING STORE - WEB PROJECT
================================================================================

1. PROJECT DESCRIPTION
--------------------------------------------------------------------------------
This is a web application developed with Spring Boot, Thymeleaf, MySQL, 
and modern CSS (Flexbox, Grid, and Tailwind CSS).

The application has three main pages:

- Home (http://localhost:8080/):
  Store landing page displaying categories and catalog overview.

- Products (http://localhost:8080/products):
  Local tech hardware inventory connected to MySQL.
  Includes real-time instant search and category filter.

- Products 1 (http://localhost:8080/products-1):
  Official collectibles department consuming the public Rick and Morty REST API.
  * Endpoint 1 (/character): Collectible cards, status, prices, and images.
  * Endpoint 2 (/location): Origin manufacturing hub details.
  * Endpoint 3 (/episode): Authenticity certificate with debut episode info.
  * Search Bar: Fixed 4-second delay throttle before triggering search.


2. REQUIREMENTS
--------------------------------------------------------------------------------
- Java Development Kit (JDK 21 or compatible JDK 17+)
- MySQL Server (running on port 3306)
- Internet connection (required for Products 1 external REST API)


3. DATABASE CONFIGURATION
--------------------------------------------------------------------------------
Configured in: src/main/resources/application.properties

- Database URL: jdbc:mysql://localhost:3306/DB
- User: <your_mysql_user>
- Password:<your_mysql_password>
- The database is created automatically if it does not exist.


4. HOW TO RUN THE APPLICATION
--------------------------------------------------------------------------------
Open a terminal in the project root folder and execute:

* On Windows:
    .\mvnw.cmd spring-boot:run

* On Linux / macOS:
    ./mvnw spring-boot:run

* Or open in your IDE (VS Code, IntelliJ IDEA, Eclipse, NetBeans) and run:
    InventoryApplication.java


5. ACCESS IN BROWSER
--------------------------------------------------------------------------------
When the console says "Started InventoryApplication", open your browser:

- Home:        http://localhost:8080/
- Products:    http://localhost:8080/products
- Products 1:  http://localhost:8080/products-1

================================================================================
