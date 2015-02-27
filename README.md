# DevTest

Notes on the task:

- JDK 1.8 was used and build.gradle was adjusted accordingly

- GSON dependency was added, to handle deserialization/serialization of JSON data

- Input, output and google reverse Geocoding API properties can be setup in the external application.properties

- Unit tests for the CustomerServices have been created under src/test/java

- To build application and run tests: ./gradlew clean build

- To run application from command line: java -Dapp.config=conf/application.properties -jar build/libs/NorthPlainsDevTest-1.0.jar

- the output json data with added addresses is currently configured to output to /tmp/output.json

- the rest of the output will be displayed in the standard out

