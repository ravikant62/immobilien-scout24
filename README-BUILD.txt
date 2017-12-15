Technologies Used 

1. Spring boot 1.5.7
-> The reason of using spring boot is because it gives an 'opinionated' view of spring platform. That is developers who are aware of Spring can
easily and quickly adapt to spring boot. 
-> We can use it to create stand-alone applications that can be run using java -jar
-> Spring boot also provides auto configuration annotation that is, it automatically configure the Spring application based on the jar dependencies that you have added
-> Configuring spring boot starter parent enables to import default jars and dependencies of spring boot dependencies specified in the pom.xml which 
takes less effort to provide all dependencies explicitly in pom.xml

2. Spring starter thymelead 2.1.5
-> Thymeleaf is a java library serves as a template engine to display data on front end
-> It is easy to integrate as spring boot provides spring boot starter thymeleaf dependency

3. Embedded tomcat 8.5.20
-> Need not integrate other server, embedded tomcat serves the purpose

4. Spring starter web (MVC)
-> As spring boot provides spring starter web, it is easy to configure rest in the application.
-> Also, using spring starter web automatically imports default dependencies to implement a web service

5. Spring starter test (junit)
-> Easy to integrate with spring

6. Maven 3.5.0
-> Latest and stable version of maven.
-> Maven is a project management tool that is used for project building and specify dependencies

7. Java 8
-> I have used java 8 so that I can leverage added feature of iterating collection and stream filters using lambda expression.



HOW TO BUILD

1. Unzip immobilien-scout24.zip -> Open any IDE -> Import Immobilien-Scout24 as a maven project
2. Go to directory where Immobilien project resides
   type in the same opened terminal at step 4
            cd <path to project extracted directory>/immobilien-scout24
            mvn clean install
3. Creates a new jar inside target folder of project (there is an existing jar inside target folder)
4. Run the jar by typing : java -jar immobilien-scout24-1.0.00.jar
5. Application is accessible at URL - https://localhost:8443/scout24/home


CONSTRAINTS

1. Application does not integrates any session management
2. Application throws back 500 internal server error if in case response error code does not return valid 
   For instance, HttpStatus.valueOf(errorCode). Need to implement the logic where in all error code is recognised by HTTPStatus class
3. Need to implement logging feature.
   

