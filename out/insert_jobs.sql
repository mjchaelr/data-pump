alter trigger x disable;
-- delete target records locations
delete from locations where 1=1;
-- delete target records employees
delete from employees where employee_id in (select manager_id from departments where department_id in (select department_id from employees where employee_id = 101));
-- delete target records departments
delete from departments where department_id in (select department_id from employees where employee_id = 101);
-- delete target records jobs
delete from jobs where job_id in (select job_id from employees where employee_id = 101);
-- delete target records employees
delete from employees where employee_id in (select manager_id from employees where employee_id = 101);
-- delete target records employees
delete from employees where employee_id=101;
--Insert for table: employees
insert into employees(EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE, JOB_ID, SALARY, COMMISSION_PCT, MANAGER_ID, DEPARTMENT_ID) values (101,'Neena','Kochhar','NKOCHHAR','515.123.4568',to_date('21/09/2005 00:00:00', 'DD/MM/YYYY HH24:MI:SS'),'AD_VP',17000,null,100,90);
--Insert for table: employees
insert into employees(EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE, JOB_ID, SALARY, COMMISSION_PCT, MANAGER_ID, DEPARTMENT_ID) values (100,'Steven','King','SKING','515.123.4567',to_date('17/06/2003 00:00:00', 'DD/MM/YYYY HH24:MI:SS'),'AD_PRES',24000,null,null,90);
--Insert for table: jobs
insert into jobs(JOB_ID, JOB_TITLE, MIN_SALARY, MAX_SALARY) values ('AD_VP','Administration Vice President',15000,30000);
--Insert for table: departments
insert into departments(DEPARTMENT_ID, DEPARTMENT_NAME, MANAGER_ID, LOCATION_ID) values (90,'Executive',100,1700);
--Insert for table: employees
insert into employees(EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE, JOB_ID, SALARY, COMMISSION_PCT, MANAGER_ID, DEPARTMENT_ID) values (100,'Steven','King','SKING','515.123.4567',to_date('17/06/2003 00:00:00', 'DD/MM/YYYY HH24:MI:SS'),'AD_PRES',24000,null,null,90);
--Insert for table: locations
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (1000,'1297 Via Cola di Rie','00989','Roma',null,'IT');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (1100,'93091 Calle della Testa','10934','Venice',null,'IT');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (1200,'2017 Shinjuku-ku','1689','Tokyo','Tokyo Prefecture','JP');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (1300,'9450 Kamiya-cho','6823','Hiroshima',null,'JP');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (1400,'2014 Jabberwocky Rd','26192','Southlake','Texas','US');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (1500,'2011 Interiors Blvd','99236','South San Francisco','California','US');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (1600,'2007 Zagora St','50090','South Brunswick','New Jersey','US');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (1700,'2004 Charade Rd','98199','Seattle','Washington','US');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (1800,'147 Spadina Ave','M5V 2L7','Toronto','Ontario','CA');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (1900,'6092 Boxwood St','YSW 9T2','Whitehorse','Yukon','CA');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (2000,'40-5-12 Laogianggen','190518','Beijing',null,'CN');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (2100,'1298 Vileparle (E)','490231','Bombay','Maharashtra','IN');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (2200,'12-98 Victoria Street','2901','Sydney','New South Wales','AU');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (2300,'198 Clementi North','540198','Singapore',null,'SG');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (2400,'8204 Arthur St',null,'London',null,'UK');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (2500,'Magdalen Centre, The Oxford Science Park','OX9 9ZB','Oxford','Oxford','UK');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (2600,'9702 Chester Road','09629850293','Stretford','Manchester','UK');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (2700,'Schwanthalerstr. 7031','80925','Munich','Bavaria','DE');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (2800,'Rua Frei Caneca 1360 ','01307-002','Sao Paulo','Sao Paulo','BR');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (2900,'20 Rue des Corps-Saints','1730','Geneva','Geneve','CH');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (3000,'Murtenstrasse 921','3095','Bern','BE','CH');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (3100,'Pieter Breughelstraat 837','3029SK','Utrecht','Utrecht','NL');
insert into locations(LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID) values (3200,'Mariano Escobedo 9991','11932','Mexico City','Distrito Federal,','MX');
commit;
alter trigger x enable;

