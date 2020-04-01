# data-pump
Version 0.0.1

# ISSUES
1. refactor Multiple Export Command , add validation, check for strategy to create file when not
exists
2. Check logic behind insert.yml format -- see if a better flow can be adopted.
3. Unit test
4. Retrieve correct version for VERSION command
5. add parametrized version in application.yml AND banner.txt
6. check & fix sonar lint violation

# Configuration
## Database connection
When running the commandline application, the user should specify the database connection:
> --db.connect=hr/hr@xe

Update the username, password and url according to the database that you want to extract your data.

# Usage
```console
usage: java -jar data-pump.jar --db.connect=username/password@sid [run] [file] [version] [help] 
[run OPTIONS]
	 Option 1:
		 [<owner] [tableName] [whereClause]
[file OPTIONS]
	 Option 1:
		 [file.yml]
```
## YAML file format:
For option 2, the file.yml should be in the following format:

```yaml
owner:
tableName:
preInsertScript:
postInsertScript:
whereClause:
primaryKeyField:
wiki: 
dependencies:
relatedTables:
```

## Run program for a specific table:
```console
java -jar data-pump.jar --db.connect=hr/hr@xe run HR JOBS 1=1
```

## Run program for a set of table:

```console
java -jar data-pump.jar --db.connect=hr/hr@xe file jobs.yml
```

jobs.yml:
```yaml
owner: HR
tableName: employees
preInsertScript: alter trigger x disable;
postInsertScript: alter trigger x enable;
whereClause: employee_id = 101
primaryKeyField:
  - employee_id
wiki: "Master Table"
dependencies:
  - tableName: employees
    preInsertScript: none
    whereClause: employee_id in (select manager_id from employees where employee_id = 101)
  - tableName: jobs
    preInsertScript: none
    whereClause: job_id in (select job_id from employees where employee_id = 101)
  - tableName: departments
    preInsertScript: none
    whereClause:  department_id in (select department_id from employees where employee_id = 101)
    dependencies:
      - tableName: employees
        preInsertScript: none
        whereClause:
          employee_id in
            (select manager_id
               from departments
              where department_id in (select department_id
                                        from employees
                                       where employee_id = 101))
relatedTables:
  - tableName: locations
    preInsertScript: none
    whereClause: 1=1
```

