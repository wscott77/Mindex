# Coding Challenge
## What's Been Updated

### Task 1
The "ReportingStructure" type was added with three properties: directReports, employee and numberOfReports.

### How to Use
The following endpoints are available to use:
```
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/employee/{id}/all-reports
    * RESPONSE: ReportingStructure
```

ReportingStructure has a JSON schema of:
```json
{
  "type": "ReportingStructure",
  "properties": {
    "directReports": {
      "type" : "array",
      "items": "Employee"
    },
    "employee":{
      "type":"Employee"
    },
    "numberOfReports":{
      "type":"Integer"
    }
  }
}

```
For all endpoints that require an "id" in the URL, this is the "employeeId" field.

### Task 2
The "Compensation" type was added with the following fields: employee, salary, and effectiveDate.
Two new "Compensation" REST endpoints were added; create and read. The new employee and compensation can be queried from the persistence layer.

### How to Use
The following endpoints are available to use:
```
* CREATE
    * HTTP Method: POST 
    * URL: localhost:8080/employee/compensation
    * PAYLOAD: Compensation
    * RESPONSE: Compensation
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/employee/compensation/{id}
    * RESPONSE: ReportingStructure
```
Compensation has a JSON schema of:
```json
{
  "type": "Compensation",
  "properties": {
    "employee": {
      "type" : "Employee"
    },
    "salary": {
      "properties": {
        "effectiveDate": {
          "type": "Date"
        },
        "employeeId": {
          "type": "String"
        },
        "salary": {
          "type": "Decimal"
        }
      }
    }
  }
}

```
For all endpoints that require an "id" in the URL, this is the "employeeId" field.

### How to Run
The application may be executed by running `gradlew bootRun`.

## Delivery
The update is publicly accessible via this Git repo. 

## TODOs
Consider renaming the property fields in Compensation.

Could The mongoDB repository classes be streamlined?

More testing?
