

# Employee Pair Project
This application reads input from resources/csv/input.csv and stores it in the employeedb (PostgreSQL Database).

## Input.csv Format
The input.csv should contain the following fields:

- employeeID
- projectID
- DateFrom
- DateTo

Example:
143, 12, 2013-11-01, 2014-01-05
Date formats for DateFrom and DateTo can be one of the following:

"yyyy-MM-dd"
"dd-MM-yyyy"
"MM/dd/yyyy"
Dates can also be null or NULL, indicating today.

## Main Functionality
The main function of the program is triggered by making a GET request to the URL:
localhost:8081/api/v1/employee/pair/longest
This endpoint provides information about pairs of employees who have worked together on common projects for the longest period.
It includes details such as the total time they worked together, the projectID, and the time spent on each project for all projects.

Program Workflow
The program identifies pairs of employees who have worked on common projects during the same period. 
It saves the employee IDs, time (in days) for each project they worked on, and the total time. 
The program then returns the pair with the longest total time.

## Additional Features
- **Insert More Employee Data:**
To insert more employee data from another CSV file (in the same format as input.csv), make a GET request to:

localhost:8081/api/v1/employee/add/csv?filepath=<YourFilePath>
- **Manual Data Operations:**
The program allows manual operations such as creating, deleting, or updating employee data in the database.

## Database Configuration
Before running the program, ensure you have created the employeedb (PostgreSQL Database). Update the **application.properties** file with your PostgreSQL database username and password.

Running the Application
Build the project.
Run the application.
Make requests to the specified URLs to interact with the functionalities.