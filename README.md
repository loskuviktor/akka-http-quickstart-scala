# Running the App

In a console, change directories to the top level of the project.

###Start sbt:

On OSX or Linux systems, enter ```./sbt```
On Windows systems, enter ```sbt.bat```.
When you run sbt, it downloads project dependencies. The ```>``` prompt indicates that sbt is running in interactive mode.

At the sbt prompt, enter ```reStart```.

sbt builds the project, starts an Akka HTTP server, and runs the application.

# Exercising the app
To send a set of user provided records the set should be passed in request parameters in the JSON format. It is possible to use one of the following tools:

- cURL commands
- Browser-based tools

### cURL commands:
Open a shell that supports cURL and follow these steps to send user data and retrieve results:

Copy and paste the following line to create a payload:

```shell script
curl -H "Content-type: application/json" -X POST -d '{"threshold":0.5,"userRecords":[{"id":"4047907","name":"Jesus House"},{"id":"3274858","name":"Alchemy Venture Partners Limited"}]}' http://localhost:8080/companies
```

## TODO: not implemented API:

#### to create a user request:
```shell script
curl -H "Content-type: application/json" -X POST -d '{"threshold":0.5,"userRecords":[{"id":"4047907","name":"Jesus House"},{"id":"3274858","name":"Alchemy Venture Partners Limited"}]}' http://localhost:8080/companies
```
The system should respond after each command to verify that the user request was created and return a session_id.

#### to retrieve the result
```shell script
curl http://localhost:8080/companies/SESSION_ID
```

// the logic is defined in Actor ```ProcessRecord```

There are 2 possible answers:
1. if processing of data is not finished:
    - ```status``` with percentage of processed records sent by user
    - ```query_result``` is empty
2. if processing of data is finished:
    - ```status``` that notifies of completion of processing of all records
    - ```query_result``` will contain 4 fields for each record sent by user (2 additional fields after matching)