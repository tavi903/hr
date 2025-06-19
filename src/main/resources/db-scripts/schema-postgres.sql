CREATE TABLE regions
    ( id      numeric
       CONSTRAINT  region_id_nn NOT NULL
    , region_name    varchar(25)
    );

ALTER TABLE regions
ADD CONSTRAINT reg_id_pk PRIMARY KEY (id);

CREATE TABLE countries
    ( id      CHAR(2)
       CONSTRAINT  country_id_nn NOT NULL
    , country_name    varchar(60)
    , region_id       numeric
    , CONSTRAINT     country_c_id_pk
        	     PRIMARY KEY (id)
    );
--    ORGANIZATION INDEX;

ALTER TABLE countries
ADD CONSTRAINT countr_reg_fk
        	 FOREIGN KEY (region_id)
          	  REFERENCES regions(id);

CREATE TABLE locations
    ( id    numeric(4) NOT NULL
    , street_address varchar(40)
    , postal_code    varchar(12)
    , city       varchar(30)
	CONSTRAINT     loc_city_nn  NOT NULL
    , state_province varchar(25)
    , country_id     CHAR(2)
    ) ;

ALTER TABLE locations
ADD CONSTRAINT loc_id_pk
       		 PRIMARY KEY (id);
ALTER TABLE locations
ADD CONSTRAINT loc_c_id_fk
       		 FOREIGN KEY (country_id)
        	  REFERENCES countries(id);

CREATE SEQUENCE locations_seq
 START WITH     3300
 INCREMENT BY   100
 MAXVALUE       9900
 ;

CREATE TABLE departments
    ( id    numeric(4) NOT NULL
    , department_name  varchar(30)
	CONSTRAINT  dept_name_nn  NOT NULL
    , manager_id       numeric(6)
    , location_id      numeric(4)
    ) ;

ALTER TABLE departments
ADD CONSTRAINT dept_id_pk
       		 PRIMARY KEY (id);
ALTER TABLE departments
ADD CONSTRAINT dept_loc_fk
       		 FOREIGN KEY (location_id)
        	  REFERENCES locations (id);

CREATE SEQUENCE departments_seq
 START WITH     280
 INCREMENT BY   10
 MAXVALUE       9990
 ;

CREATE TABLE jobs
    ( id         varchar(10) NOT NULL
    , job_title      varchar(35)
	CONSTRAINT     job_title_nn  NOT NULL
    , min_salary     numeric(6)
    , max_salary     numeric(6)
    ) ;

ALTER TABLE jobs
ADD CONSTRAINT job_id_pk
      		 PRIMARY KEY(id);

CREATE TABLE employees
    ( id    numeric(6) NOT NULL
    , first_name     varchar(20)
    , last_name      varchar(25)
	 CONSTRAINT     emp_last_name_nn  NOT NULL
    , email          varchar(25)
	CONSTRAINT     emp_email_nn  NOT NULL
    , phone_number   varchar(20)
    , hire_date      DATE
	CONSTRAINT     emp_hire_date_nn  NOT NULL
    , job_id         varchar(10)
	CONSTRAINT     emp_job_nn  NOT NULL
    , salary         numeric(8,2)
    , commission_pct numeric(2,2)
    , manager_id     numeric(6)
    , department_id  numeric(4)
    , CONSTRAINT     emp_salary_min
                     CHECK (salary > 0)
    , CONSTRAINT     emp_email_uk
                     UNIQUE (email)
    ) ;

ALTER TABLE employees
ADD CONSTRAINT     emp_emp_id_pk
                     PRIMARY KEY (id);

ALTER TABLE employees
ADD CONSTRAINT emp_dept_fk
 FOREIGN KEY (department_id)
  REFERENCES departments;

ALTER TABLE employees
ADD CONSTRAINT     emp_job_fk
                     FOREIGN KEY (job_id)
                      REFERENCES jobs (id);
ALTER TABLE employees
ADD CONSTRAINT  emp_manager_fk
                     FOREIGN KEY (manager_id)
                      REFERENCES employees;

CREATE SEQUENCE employees_seq
 START WITH     207
 INCREMENT BY   1
 ;

CREATE TABLE job_history
    ( employee_id   numeric(6)
	 CONSTRAINT    jhist_employee_nn  NOT NULL
    , start_date    DATE
	CONSTRAINT    jhist_start_date_nn  NOT NULL
    , end_date      DATE
	CONSTRAINT    jhist_end_date_nn  NOT NULL
    , job_id        varchar(10)
	CONSTRAINT    jhist_job_nn  NOT NULL
    , department_id numeric(4)
    , CONSTRAINT    jhist_date_interval
                    CHECK (end_date > start_date)
    ) ;


ALTER TABLE job_history
ADD CONSTRAINT jhist_emp_id_st_date_pk
      PRIMARY KEY (employee_id, start_date);

ALTER TABLE job_history
ADD CONSTRAINT     jhist_job_fk
                     FOREIGN KEY (job_id)
                     REFERENCES jobs;
ALTER TABLE job_history
ADD CONSTRAINT     jhist_emp_fk
                     FOREIGN KEY (employee_id)
                     REFERENCES employees;
ALTER TABLE job_history
ADD CONSTRAINT     jhist_dept_fk
                     FOREIGN KEY (department_id)
                     REFERENCES departments;

CREATE INDEX emp_department_ix
       ON employees (department_id);

CREATE INDEX emp_job_ix
       ON employees (job_id);

CREATE INDEX emp_manager_ix
       ON employees (manager_id);

CREATE INDEX emp_name_ix
       ON employees (last_name, first_name);

CREATE INDEX dept_location_ix
       ON departments (location_id);

CREATE INDEX jhist_job_ix
       ON job_history (job_id);

CREATE INDEX jhist_employee_ix
       ON job_history (employee_id);

CREATE INDEX jhist_department_ix
       ON job_history (department_id);

CREATE INDEX loc_city_ix
       ON locations (city);

CREATE INDEX loc_state_province_ix
       ON locations (state_province);

CREATE INDEX loc_country_ix
       ON locations (country_id);
