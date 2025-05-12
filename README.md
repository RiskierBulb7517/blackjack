# BlackJack Web Application

## Overview
This is a dynamic web application for playing the classic card game BlackJack. The application is built using Java, Maven, and MySQL, and is deployed on Apache Tomcat.

## Prerequisites
Before setting up the application, ensure the following software is installed and configured on your system:

1. **Java 17**  
    - Install Java 17 and set the `JAVA_HOME` environment variable.

2. **Apache Maven**  
    - Install Maven and set the `MAVEN_HOME` environment variable.

3. **Apache Tomcat 9**  
    - Install Tomcat 9 and set the `CATALINA_HOME` environment variable.

4. **MySQL**  
    - Install MySQL and create a database for the application. The URL, username and password must be changed to match your credential.

5. **Git**  
    - Install Git to clone the repository.

6. **Eclipse IDE**  
    - Install Eclipse IDE for Java EE Developers. Once you managed to connect to the DB through Eclipse datasource explorer, you can use the DDL and INSERT scripts to create the tables and insert the cards.

## Installation

1. **Clone the Repository**  
    ```bash
    git clone https://github.com/your-username/BlackJack.git
    cd BlackJack
    ```

2. **Configure the Database**  
    - Create a MySQL database (e.g., `blackjack_db`).
    - Update the database connection details in the `src/main/resources/application.properties` file.

3. **Build the Project**  
    ```bash
    mvn clean install
    ```

4. **Deploy to Tomcat**  
    - Copy the generated WAR file from the `target` directory to the `webapps` folder of your Tomcat installation.
    - Start the Tomcat server.

5. **Access the Application**  
    Open your browser and navigate to `http://localhost:8080/BlackJack`.

## Development
- Import the project into Eclipse as a Maven project.
- Make changes and test locally using the embedded Tomcat server in Eclipse.

## Further development
One of the main possible improvement can be saving the state of a game, to retrieve it if it was lost due to browser closing for example. 

## Contributing
Feel free to fork the repository and submit pull requests for improvements or bug fixes.
