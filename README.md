# Employee management system :people_holding_hands:

---
## The task
Rest API for management of employees and the projects they have worked on. It supports CRUD operations and the special `/employees/besties` endpoint that returns the pair of employees who have worked together on common projects for the longest period of time and the time for each of those projects.

## Endpoints
- `GET` /employees 
  - return all records
- `GET` /employees/{id}
  - return employees who work on the same project
- `DELETE` /employees/delete
  - delete all records
- `DELETE` /employees/delete/{id}
  - delete employee by id
- `POST` /employees
  - add record to the database
- `POST` /employee/update
  - update record by the employee ID
- `POST` /csv
  - read records from csv file
- `GET`  /employees/besties
  - returns pair of employees who have worked together on common projects for the longest period of time and the time for each of those projects
 
## Database
The database that is being used is `PostgreSQL`. To use the program, there should be a manually created database. The table and the columns are created by the program.
