package ua.nure.bycheva.SummaryTask4.db;

public interface SQL {

    //SQL for User entity
    String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
    String SQL_ALL_FIND_USERS_BY_ENTRANTS_ID = "SELECT * FROM users WHERE id=(SELECT user_id FROM entrants WHERE id=?)";
	String SQL_SAVE_USER = "INSERT INTO users VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?)";
    String SQL_UPDATE_USER = "UPDATE users SET login=?, password=?, first_name=?, last_name=?, middle_name=?, email=?, role_id=?, locale=? WHERE id=?";
    String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";
    String SQL_FIND_USERS = "SELECT * FROM users";

    //SQL for Faculty entity
    String SQL_FIND_FACULTIES = "SELECT * FROM faculties";
    String SQL_FIND_FACULTY_BY_NAME = "SELECT * FROM faculties WHERE faculty_name=?";
	String SQL_FIND_FACULTIES_NOT_EXISTS_IN_SHEETS = "SELECT * FROM faculties f WHERE NOT EXISTS (SELECT * FROM sheets s WHERE s.faculty_id=f.id)";
	String SQL_SAVE_FACULTY = "INSERT INTO faculties VALUES (default,?,?,?)";
	String SQL_UPDATE_FACULTY = "UPDATE faculties SET faculty_name=?, budget_places=?, total_places=? WHERE id=?";
	String SQL_FIND_FACULTY_BY_ID = "SELECT * FROM faculties WHERE id=?";
	String SQL_FIND_FACULTY_SUBJECTS_BEAN_BY_FACULTY_ID = "SELECT fs.faculty_id, fs.subject_id, s.subject_name, fs.ratio FROM subjects s, faculties_subjects fs WHERE s.id=fs.subject_id AND fs.faculty_id=?";

    //SQL for Subject entity
    String SQL_FIND_SUBJECTS = "SELECT * FROM subjects";
    String SQL_FIND_SUBJECT_BY_NAME = "SELECT * FROM subjects WHERE subject_name=?";
    String SQL_SAVE_SUBJECT = "INSERT INTO subjects VALUES (default,?)";
    String SQL_FIND_SUBJECT_BY_ID = "SELECT * FROM subjects WHERE id=?";
    String SQL_FIND_SUBJECTS_NOT_EXIST_IN_FACULTY ="SELECT * FROM subjects " +
            "WHERE NOT EXISTS (SELECT * FROM faculties_subjects WHERE subject_id=subjects.id AND faculty_id=?)";
    String SQL_UPDATE_SUBJECT = "UPDATE subjects SET subject_name=? WHERE id=?";
    String SQL_FIND_TEST_SUBJECTS_WHICH_ENTRANT_HAVE_NOT_BY_FACULTY_ID ="SELECT * FROM subjects " +
            "WHERE EXISTS (SELECT * FROM faculties_subjects WHERE subject_id=subjects.id AND faculty_id=?) " +
                    "AND NOT EXISTS (SELECT * FROM marks WHERE subject_id=subjects.id AND exam_type='test' AND entrant_id=?)";
    String SQL_FIND_CERTIFICATE_SUBJECTS_WHICH_ENTRANT_HAVE_NOT ="SELECT * FROM subjects " +
            "WHERE NOT EXISTS (SELECT * FROM marks WHERE subject_id=subjects.id AND exam_type='certificate' AND entrant_id=?)";

    //SQL for Entrant entity
    String  SQL_SAVE_ENTRANT = "INSERT INTO entrants VALUES (default,?,?,?,?,?)";
    String  SQL_UPDATE_ENTRANT = "UPDATE entrants SET user_id=?, city=?, region=?, school=?, status_id=? WHERE id=?";
    String SQL_FIND_ENTRANT_BY_USER_ID = "SELECT * FROM entrants WHERE user_id=?";
    String SQL_FIND_ALL_ENTRANT_BEANS = "SELECT e.id, u.email, u.last_name, u.first_name, u.middle_name, e.city, e.region, e.school, s.status_name " +
            "FROM users u, entrants e, statuses s " +
            "WHERE u.id=e.user_id AND e.status_id=s.id";
    String SQL_FIND_ENTRANT_BEANS = "SELECT e.id, u.email, u.last_name, u.first_name, u.middle_name, e.city, e.region, e.school, s.status_name " +
            "FROM users u, entrants e, statuses s " +
            "WHERE u.id=e.user_id AND e.status_id=s.id AND e.id=?";
    String SQL_FIND_ENTRANT_BY_ID = "SELECT * FROM entrants WHERE id=?";

    //SQL for Certificate entity
	String SQL_FIND_CERTIFICATE_BY_ENTRANT_ID = "SELECT * FROM certificates WHERE entrant_id=?";
    String SQL_SAVE_CERTIFICATE = "INSERT INTO certificates VALUES (default,?,?,?)";
    String SQL_FIND_CERTIFICATES = "SELECT * FROM certificates";
    String SQL_UPDATE_CERTIFICATE = "UPDATE certificates SET file_name=?, file_content=? WHERE entrant_id=?";

    //SQL for Mark entity
    String SQL_SAVE_MARK = "INSERT INTO marks VALUES (default,?,?,?,?)";
    String SQL_FIND_ENTRANT_MARKS_BEANS ="SELECT m.id, s.subject_name, m.mark_value, m.exam_type " +
                    "FROM entrants e, subjects s, marks m WHERE e.id=m.entrant_id AND s.id=m.subject_id AND m.entrant_id=?";
    String SQL_UPDATE_MARK = "UPDATE marks SET mark_value=? WHERE id=?";
    String SQL_FIND_MARK_BY_ID = "SELECT * FROM marks WHERE id=?";
    String SQL_FIND_MARKS = "SELECT * FROM marks";
    String SQL_FIND_MARKS_BEANS_FOR_APPLICATION ="SELECT s.subject_name, m.mark_value, m.exam_type FROM subjects s, marks m " +
            "WHERE EXISTS (select * from entrants e WHERE e.id=m.entrant_id AND e.user_id=?) " +
            "AND EXISTS (select * from faculties_subjects fs WHERE fs.subject_id=m.subject_id AND fs.faculty_id=?) " +
            "AND s.id=m.subject_id AND m.exam_type='test'";
    String SQL_FIND_CERTIFICATE_MARKS_BEANS="SELECT s.subject_name, m.mark_value, m.exam_type FROM subjects s, marks m " +
            "WHERE EXISTS (select * from entrants e WHERE e.id=m.entrant_id AND e.user_id=?) " +
            "AND s.id=m.subject_id AND m.exam_type='certificate'";
    String SQL_FIND_SUBJECTS_NOT_EXIST_IN_ENTRANT_MARKS ="SELECT * FROM subjects " +
            "WHERE NOT EXISTS (SELECT * FROM marks WHERE subject_id=subjects.id AND entrant_id=? AND exam_type=?)";

    //SQL for Application entity
    String SQL_FIND_APPLICATIONS = "SELECT * FROM applications";
    String SQL_FIND_APPLICATIONS_IDS = "SELECT a.faculty_id, a.entrant_id FROM applications a, entrants e, statuses s " +
            "WHERE a.entrant_id=e.id AND e.status_id=s.id AND s.status_name='active'";
    String SQL_FIND_APPLICATION_BY_ID = "SELECT * FROM applications WHERE id=?";
    String SQL_SAVE_APPLICATION = "INSERT INTO applications VALUES (default,?,?,?,?)";
    String SQL_UPDATE_APPLICATION ="UPDATE applications SET entrant_id=?, faculty_id=?, avg_point=? WHERE id=?";
    String SQL_UPDATE_APPLICATION_POINT_BY_FACULTY_AND_ENTRANT = "UPDATE applications SET avg_point=? WHERE faculty_id=? AND entrant_id=?";
    String SQL_FIND_ALL_APPLICATIONS_BY_FACULTY ="SELECT f.budget_places, f.total_places, a.entrant_id FROM faculties f, applications a, entrants e, sheets s, statuses st " +
            "WHERE f.id=a.faculty_id AND f.id=s.faculty_id AND a.entrant_id=e.id " +
            "AND e.status_id=st.id AND st.status_name='active' " +
            "AND NOT EXISTS (SELECT * FROM sheets_entrants se WHERE a.entrant_id=se.entrant_id) " +
            "AND s.id=? ORDER BY a.avg_point ASC";
    String SQL_FIND_ENTRANT_APPLICATION_BEANS ="SELECT a.id, a.faculty_id, a.entrant_id, f.faculty_name, a.create_date FROM applications a, faculties f " +
            "WHERE EXISTS (SELECT * FROM entrants e WHERE a.entrant_id=e.id AND e.user_id=?) " +
            "AND f.id=a.faculty_id";
    String SQL_CALCULATE_ENTRANT_AVG_CERTIFICATE_VALUE ="SELECT (AVG(m.mark_value)*10+80) FROM marks m " +
            "WHERE m.exam_type='certificate' AND m.entrant_id=?";
    String SQL_CALCULATE_ENTRANT_AVG_TEST_VALUE ="SELECT SUM(m.mark_value*fs.ratio), SUM(fs.ratio) FROM marks m, faculties_subjects fs " +
            "WHERE  m.subject_id=fs.subject_id AND m.exam_type='test' AND m.entrant_id=? AND fs.faculty_id=?";

    //SQL for FacultySubject entity
    String SQL_DELETE_FACULTY_SUBJECT = "DELETE FROM faculties_subjects WHERE faculty_id=? AND subject_id=?";
    String SQL_SAVE_FACULTY_SUBJECT = "INSERT INTO faculties_subjects VALUES (?,?,?)";
    String SQL_UPDATE_FACULTY_SUBJECT = "UPDATE faculties_subjects SET ratio=? WHERE faculty_id=? AND subject_id=?";

    //SQL for Sheet entity
    String SQL_SAVE_SHEET = "INSERT INTO sheets VALUES (default,?,?,?,?)";
    String SQL_UPDATE_SHEET = "UPDATE sheets SET uid=?, faculty_id=?, create_date=?, sheet_status_id=? WHERE id=?";
    String SQL_FIND_SHEETS = "SELECT * FROM sheets";
    String SQL_FIND_SHEET_BY_UID = "SELECT * FROM sheets WHERE uid=?";
    String SQL_FIND_SHEET_BY_ID = "SELECT * FROM sheets WHERE id=?";
    String SQL_FIND_ALL_ENTRANTS_BY_SHEET_ID ="SELECT a.entrant_id, u.last_name, u.first_name, u.middle_name, a.avg_point, se.passed_status_id " +
            "FROM users u, entrants e, applications a, sheets_entrants se, sheets s " +
            "WHERE e.id=se.entrant_id AND e.user_id=u.id AND e.id=a.entrant_id AND a.faculty_id=s.faculty_id " +
            "AND s.id=se.sheet_id AND se.sheet_id=?";
    String SQL_ADD_ENTRANTS_TO_THE_SHEET = "INSERT INTO sheets_entrants VALUES(?,?,?)";
    String SQL_FIND_ALL_SHEET_BEANS ="SELECT s.id, s.uid, f.faculty_name, s.create_date, ss.sheet_status_name, " +
            "(select count(entrant_id) from sheets_entrants se WHERE s.id=se.sheet_id GROUP BY se.sheet_id) " +
            "FROM sheets s, faculties f, sheet_statuses ss " +
            "WHERE s.faculty_id=f.id AND ss.id=s.sheet_status_id";
    String SQL_FIND_SHEET_BEAN_BY_ID = "SELECT s.id, s.uid, f.faculty_name, s.create_date, ss.sheet_status_name, " +
            "(select count(entrant_id) from sheets_entrants se WHERE s.id=se.sheet_id GROUP BY se.sheet_id) " +
            "FROM sheets s, faculties f, sheet_statuses ss " +
            "WHERE s.faculty_id=f.id AND s.id=? AND ss.id=s.sheet_status_id";
    String SQL_FIND_ENTRANT_APPLICATION_BEANS_BY_ENTRANT_ID = "SELECT a.id, f.faculty_name, a.create_date, a.faculty_id, a.entrant_id FROM applications a, faculties f WHERE f.id=a.faculty_id AND a.entrant_id=?";
    String SQL_FIND_ENTRANT_CERTIFICATE_MARKS_BEANS =
            "SELECT s.subject_name, m.mark_value, m.exam_type FROM subjects s, marks m WHERE s.id=m.subject_id AND m.exam_type='certificate' AND m.entrant_id=?";
}
