package ua.nure.bycheva.SummaryTask4.db;

public interface Fields {

	String ENTITY_ID = "id";
    String SUBJECT_ID = "subject_id";
    String FACULTY_ID = "faculty_id";
    String ENTRANT_ID = "entrant_id";
    String USER_ID = "user_id";

    //fields for ROLE enum in db table roles
    String ROLE_NAME = "role_name";

    //fields for STATUS enum in db table statuses
    String STATUS_NAME = "status_name";

    //fields for APPSTATUS enum in db table app_statuses
    String PASSED_STATUS_NAME = "passed_status_name";

	//fields for FACULTY entity in db table faculties
    String FACULTY_NAME = "faculty_name";
	String FACULTY_BUDGET_PLACES = "budget_places";
	String FACULTY_TOTAL_PLACES = "total_places";

    //fields for FACULTYSUBJECT entity in db table faculties_subjects
    String FACT_SUBJ_RATIO = "ratio";

    //fields for SUBJECT entity in db table subjects
    String SUBJECT_NAME = "subject_name";

    //fields for USER entity in db table users
    String USER_LOGIN = "login";
	String USER_PASSWORD = "password";
	String USER_ROLE_ID= "role_id";
	String USER_FIRST_NAME = "first_name";
	String USER_LAST_NAME = "last_name";
	String USER_MIDDLE_NAME = "middle_name";
	String USER_EMAIL = "email";
    String USER_LOCALE = "locale";

    //fields for ENTRANCE entity in db table entrances
    String ENTRANT_CITY = "city";
	String ENTRANT_REGION = "region";
	String ENTRANT_SCHOOL = "school";
	String ENTRANT_STATUS_ID = "status_id";

    //fields for CERTIFICATE entity in db table certificates
    String CERT_FILE_NAME = "file_name";
    String CERT_FILE_CONTENT = "file_content";

    //fields for MARK entity in db table marks
    String MARK_VALUE = "mark_value";
    String MARK_EXAM_TYPE = "exam_type";

    //fields for APPLICATION entity in db table applications
    String APPLICATION_AVG_POINT = "avg_point";

    String SHEET_CREATE_DATE = "create_date";
    String SHEET_UID = "uid";
    String SHEET_PASSED_STATUS_ID = "passed_status_id";
    String APPLICATION_CREATE_DATE = "create_date";
    String SHEET_STATUS_ID = "sheet_status_id";
    String SHEET_STATUS_NAME = "sheet_status_name";
}
