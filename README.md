# DevTest

Notes on the task:

- JDK 1.8 was used and build.gradle was adjusted accordingly

- GSON dependency was added, to handle deserialization/serialization of JSON data

- Input, output and google reverse Geocoding API properties can be setup in the app.properties file under resources

- Unit tests for the CustomerServices have been created under src/test/java

- To build application and run tests: ./gradlew clean build

- To run application: #java -Dapp.config=conf/application.properties -jar build/libs/NorthPlainsDevTest-1.0.jar

- the output json data is currently configured to output to /tmp/output.json 

