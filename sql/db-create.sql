connect 'jdbc:derby://localhost:1527/SummaryTask4;creat=true;user=bycheva;password=root';

DROP TABLE sheets_entrants;
DROP TABLE passed_statuses;
DROP TABLE sheets;
DROP TABLE sheet_statuses;
DROP TABLE applications;
DROP TABLE marks;
DROP TABLE faculties_subjects;
DROP TABLE subjects;
DROP TABLE certificates;
DROP TABLE entrants;
DROP TABLE statuses;
DROP TABLE faculties;
DROP TABLE users;
DROP TABLE roles;

-- -----------------------------------------------------
-- Table ROLES
-- users roles
-- -----------------------------------------------------
CREATE TABLE roles(
  id INTEGER NOT NULL PRIMARY KEY,
  role_name VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO roles VALUES(0, 'admin');
INSERT INTO roles VALUES(1, 'entrant');

-- -----------------------------------------------------
-- Table USERS
-- -----------------------------------------------------
CREATE TABLE users(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  login VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(512) NOT NULL ,
  first_name VARCHAR(20),
  last_name VARCHAR(20),
  middle_name VARCHAR(20),
  email VARCHAR(50),
  role_id INTEGER NOT NULL REFERENCES roles(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  locale VARCHAR(10) NOT NULL
);

--id=1
INSERT INTO users VALUES (default, 'admin','C7AD44CBAD762A5DA0A452F9E854FDC1E0E7A52A38015F23F3EAB1D80B931DD472634DFAC71CD34EBC35D16AB7FB8A90C81F975113D6C7538DC69DD8DE9077EC','Name','Admin', 'Administrator', 'admin@mail.ru', 0, 'ru');
--id=2
INSERT INTO users VALUES (default, 'entrant','D6B4E1C3FB0CEFC7A52E25506ABF495BD3A96332D569A6A590A6B9AE75E70388834E110A81F2FDE5C4444F7DF17F90CE6B97C0F0B46CF337CAC61E27E0614B80','Entrant','Entrant', 'Entrant', 'entrant@mail.ru', 1, 'en');

-- -----------------------------------------------------
-- Table FACULTIES
-- -----------------------------------------------------
CREATE TABLE faculties(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  faculty_name VARCHAR(45) NOT NULL UNIQUE ,
  budget_places INTEGER NOT NULL DEFAULT 0,
  total_places INTEGER NOT NULL DEFAULT 0
);

--id=1
INSERT INTO faculties VALUES (default,'факультет географии',10,55);
--id=2
INSERT INTO faculties VALUES (default,'факультет математики',20,45);
--id=3
INSERT INTO faculties VALUES (default,'факультет истории',30,65);
--id=4
INSERT INTO faculties VALUES (default,'факультет социологии',5,25);

-- -----------------------------------------------------
-- Table STATUSES
-- entrants statuses
-- -----------------------------------------------------
CREATE TABLE statuses(
  id INTEGER NOT NULL PRIMARY KEY,
  status_name VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO statuses VALUES(0, 'active');
INSERT INTO statuses VALUES(1, 'blocked');

-- -----------------------------------------------------
-- Table ENTRANTS
-- -----------------------------------------------------
CREATE TABLE entrants(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES users(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  city VARCHAR(50),
  region VARCHAR(100),
  school VARCHAR(200),
  status_id INTEGER NOT NULL REFERENCES statuses(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT
);

--id=1 , user_id=2
INSERT INTO entrants VALUES (default, 2, 'Kharkov', 'Kharkov region', 'Gymnasium #23', 0);

-- -----------------------------------------------------
-- Table CERTIFICATES
-- entrants certificates
-- -----------------------------------------------------
CREATE TABLE certificates(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  entrant_id INTEGER NOT NULL REFERENCES entrants(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  file_name VARCHAR(512) NOT NULL UNIQUE,
  file_content BLOB
);

-- -----------------------------------------------------
-- Table SUBJECTS
-- -----------------------------------------------------
CREATE TABLE subjects(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  subject_name VARCHAR(100) NOT NULL UNIQUE
);

--id=1
INSERT INTO subjects VALUES (default, 'MATH');
--id=2
INSERT INTO subjects VALUES (default, 'ENGLISH');
--id=3
INSERT INTO subjects VALUES (default, 'HISTORY');

-- -----------------------------------------------------
-- Table FACULTIES_SUBJECTS
-- faculties required test subjects
-- -----------------------------------------------------
CREATE TABLE faculties_subjects(
  faculty_id INTEGER NOT NULL REFERENCES faculties(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  subject_id INTEGER NOT NULL REFERENCES subjects(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  ratio DOUBLE NOT NULL DEFAULT 0
);

-- faculty = факультет математики subject = MATH
INSERT INTO faculties_subjects VALUES (2,1,0.4);
-- faculty = факультет математики subject = ENGLISH
INSERT INTO faculties_subjects VALUES (2,2,0.25);
-- faculty = факультет математики subject = HISTORY
INSERT INTO faculties_subjects VALUES (2,3,0.25);

-- -----------------------------------------------------
-- Table MARKS
-- entrants marks
-- entrants_subjects
-- -----------------------------------------------------
CREATE TABLE marks(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  subject_id INTEGER NOT NULL REFERENCES subjects(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  entrant_id INTEGER NOT NULL REFERENCES entrants(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  mark_value DOUBLE NOT NULL DEFAULT 0,
  exam_type VARCHAR(15)
);

-- mark = MATH certificate
INSERT INTO marks VALUES (default,1,1,12,'certificate');
-- mark = MATH test
INSERT INTO marks VALUES (default,1,1,180,'test');
-- mark = HISTORY certificate
INSERT INTO marks VALUES (default,3,1,8,'certificate');
-- mark = HISTORY test
INSERT INTO marks VALUES (default,3,1,155.5,'test');
-- mark = ENGLISH certificate
INSERT INTO marks VALUES (default,2,1,10,'certificate');
-- mark = ENGLISH test
INSERT INTO marks VALUES (default,2,1,145.5,'test');

-- -----------------------------------------------------
-- Table APPLICATIONS
-- entrants faculties applications
-- entrants_faculties
-- -----------------------------------------------------
CREATE TABLE applications(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  entrant_id INTEGER NOT NULL REFERENCES entrants(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  faculty_id INTEGER NOT NULL REFERENCES  faculties(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  create_date DATE NOT NULL DEFAULT CURRENT DATE,
  avg_point DOUBLE NOT NULL DEFAULT 0
);

-- entrant id = 1, faculty = факультет математики
INSERT INTO applications VALUES(default,1,2,null,null);

-- -----------------------------------------------------
-- Table STATUSES
-- entrants statuses
-- -----------------------------------------------------
CREATE TABLE sheet_statuses(
  id INTEGER NOT NULL PRIMARY KEY,
  sheet_status_name VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO sheet_statuses VALUES(0, 'in_work');
INSERT INTO sheet_statuses VALUES(1, 'finally');

-- -----------------------------------------------------
-- Table SHEETS
-- -----------------------------------------------------
CREATE TABLE sheets(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  uid INTEGER NOT NULL UNIQUE,
  faculty_id INTEGER NOT NULL REFERENCES  faculties(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  create_date DATE NOT NULL DEFAULT CURRENT DATE,
  sheet_status_id INTEGER NOT NULL REFERENCES sheet_statuses(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT
);

-- -----------------------------------------------------
-- Table STATUSES
-- entrants statuses
-- -----------------------------------------------------
CREATE TABLE passed_statuses(
  id INTEGER NOT NULL PRIMARY KEY,
  passed_status_name VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO passed_statuses VALUES(0, 'budget');
INSERT INTO passed_statuses VALUES(1, 'contract');

-- -----------------------------------------------------
-- Table SHEETS_ENTRANTS
-- entrants faculties applications
-- entrants_faculties
-- -----------------------------------------------------
CREATE TABLE sheets_entrants(
  sheet_id INTEGER NOT NULL REFERENCES sheets(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  entrant_id INTEGER NOT NULL REFERENCES entrants(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  passed_status_id INTEGER NOT NULL REFERENCES passed_statuses(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT
);

DISCONNECT;