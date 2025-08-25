# **Taxi Navigator API**

# **About**

The **Tax Navigator API** helps commuters easily find public taxi routes between two locations in South Africa. It was created to solve the challenge of navigating taxi routes without needing to rely on strangers for directions, making commuting safer and more convenient.

# **Features**

This API has the following features:

- Allows admins to authenticate using JWTs with Spring Security - Endpoints under `/api/admin/**` require JWT authentication. Endpoints under `/api/users/**` are public.
    
- Uses database routes or an algorithm to find a route from A to B with relevant information in relation to routes.
    
- Allows admins to add routes, manage them and also download a CSV template and upload a CSV file with relevant routes for bulk uploads.
    
- Supports bidirectional routing for each added route.
    

# **Technologies**

- **Java 17**
    
- Uses Spring Boot with Spring Security, Spring JPA, tests, Lombok,mapstruct and Open CSV.
    
- Docker containers for app and database containerization.
    
- PostgreSQL database as a storage medium running via a Docker container.
    

# **Installation**

- Make sure you have Docker and Maven installed.
    
- Follow the following steps as mentioned - In the command line run :
    

``` bash
cd TaxiNavigator
mvn clean package
docker compose up --build

 ```

- Using any testing tool, such as Postman - you may begin sending requests.
    

# **Testing**

Testing includes both unit and integration tests. The test suite leverages:

- **JUnit** – For structured test cases
    
- **Mockito** – For mocking dependencies
    
- **MockMvc** – For testing MVC endpoints
    
- **Testcontainers** – For running PostgreSQL in Docker during integration tests
    

Run tests with:

- mvn test
    

# **Future Plans**

- Add adequate testing - testing is in progress.
    
- Use CI/CD.
    
- Log exceptions for uncaught exceptions so that the app improves in those areas.
    
- Log and allow admins to download the requested routes that are not in the system so they may be added later.
    
- Front-end implementation.

  # **Contact Details**
  If you have any feedback or would like to collaborate, please email me at phashamokgadi631@gmail.com
