CONNECT 'jdbc:derby:SummaryTask4;create=true';

DROP TABLE sheets_entrants;
DROP TABLE passed_statuses;
DROP TABLE sheets;
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
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
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

-- -----------------------------------------------------
-- Table FACULTIES
-- -----------------------------------------------------
CREATE TABLE faculties(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  faculty_name VARCHAR(45) NOT NULL UNIQUE ,
  budget_places INTEGER NOT NULL DEFAULT 0,
  total_places INTEGER NOT NULL DEFAULT 0
);

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
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
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

-- -----------------------------------------------------
-- Table CERTIFICATES
-- entrants certificates
-- -----------------------------------------------------
CREATE TABLE certificates(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
  entrant_id INTEGER NOT NULL REFERENCES entrants(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  file_name VARCHAR(20) NOT NULL UNIQUE,
  file_content BLOB NOT NULL
);

-- -----------------------------------------------------
-- Table SUBJECTS
-- -----------------------------------------------------
CREATE TABLE subjects(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
  subject_name VARCHAR(100) NOT NULL UNIQUE
);

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

-- -----------------------------------------------------
-- Table MARKS
-- entrants marks
-- entrants_subjects
-- -----------------------------------------------------
CREATE TABLE marks(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
  subject_id INTEGER NOT NULL REFERENCES subjects(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  entrant_id INTEGER NOT NULL REFERENCES entrants(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  mark_value DOUBLE NOT NULL DEFAULT 0,
  exam_type VARCHAR(15)
);

-- -----------------------------------------------------
-- Table APPLICATIONS
-- entrants faculties applications
-- entrants_faculties
-- -----------------------------------------------------
CREATE TABLE applications(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
  entrant_id INTEGER NOT NULL REFERENCES entrants(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  faculty_id INTEGER NOT NULL REFERENCES  faculties(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  create_date DATE NOT NULL DEFAULT CURRENT DATE,
  avg_point DOUBLE NOT NULL DEFAULT 0
);

-- -----------------------------------------------------
-- Table SHEETS
-- -----------------------------------------------------
CREATE TABLE sheets(
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
  uid INTEGER NOT NULL UNIQUE,
  faculty_id INTEGER NOT NULL REFERENCES  faculties(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  create_date DATE NOT NULL DEFAULT CURRENT DATE
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