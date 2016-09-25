package ua.nure.bycheva.SummaryTask4.exception;

import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Holder for messages of exceptions and errors.
 *
 */
public class MessageManager {

    private static final Logger LOG = Logger.getLogger(MessageManager.class);

    private static final String RESOURCE_FILE_NAME = "message";

    public static final String ERR_MUST_BE_DECIMAL_NO_CHARACTER = "ERR_MUST_BE_DECIMAL_NO_CHARACTER";
    public static final String ERR_CANNOT_BE_EMPTY = "ERR_CANNOT_BE_EMPTY";
    public static final String ERR_EMAIL_MUST_BE_CORRECT = "ERR_EMAIL_MUST_BE_CORRECT";
    public static final String ERR_SUBJECT_WITH_THAT_NAME_EXISTS = "ERR_SUBJECT_WITH_THAT_NAME_EXISTS";
    public static final String ERR_FIELDS_MUST_BE_NUMBER = "ERR_FIELDS_MUST_BE_NUMBER";
    public static final String ERR_FACULTY_WITH_THAT_NAME_EXISTS = "ERR_FACULTY_WITH_THAT_NAME_EXISTS";
    public static final String ERR_BUDGET_PLACES_CANT_BE_MORE_THAN_TOTAL = "ERR_BUDGET_PLACES_CANT_BE_MORE_THAN_TOTAL";
    public static final String ERR_FIELDS_VALUE_MUST_BE_BEETWEEN_0_AND_1 = "ERR_FIELDS_VALUE_MUST_BE_BEETWEEN_0_AND_1";
    public static final String ERR_FIELDS_VALUE_MUST_BE_BEETWEEN_0_AND_200 = "ERR_FIELDS_VALUE_MUST_BE_BEETWEEN_0_AND_200";
    public static final String ERR_FIELDS_VALUE_MUST_BE_BEETWEEN_1_AND_12 = "ERR_FIELDS_VALUE_MUST_BE_BEETWEEN_1_AND_12";
    public static final String ERR_SHEET_WITH_THAT_NUMBER_EXIST = "ERR_SHEET_WITH_THAT_NUMBER_EXIST";

    //fields validation error
    public static final String ERR_USER_WITH_THAT_LOGIN_EXISTS = "ERR_USER_WITH_THAT_LOGIN_EXISTS";
    public static final String ERR_CANNOT_UPLOAD_FILE = "ERR_CANNOT_UPLOAD_FILE";
    public static final String ERR_PASSWORDS_SHOULD_BE_EQUAL = "ERR_PASSWORDS_SHOULD_BE_EQUAL";

    //database errors
    public static final String ERR_CANNOT_OBTAIN_CONNECTION = "ERR_CANNOT_OBTAIN_CONNECTION";
    public static final String ERR_CANNOT_CLOSE_CONNECTION = "ERR_CANNOT_CLOSE_CONNECTION";
    public static final String ERR_CANNOT_CLOSE_STATEMENT = "ERR_CANNOT_CLOSE_STATEMENT";
    public static final String ERR_CANNOT_ROLLBACK = "ERR_CANNOT_ROLLBACK";
    public static final String ERR_CANNOT_OBTAIN_CERTIFICATE_SUBJECTS_WHICH_ENTRANT_HAVE_NOT = "ERR_CANNOT_OBTAIN_CERTIFICATE_SUBJECTS_WHICH_ENTRANT_HAVE_NOT";
    public static final String ERR_CANNOT_GENERATE_AND_OPEN_FILE = "ERR_CANNOT_GENERATE_AND_OPEN_FILE";
    public static final String ERR_PASSWORD_MUST_HAVE_4_TILL_10_ELEMENTS = "ERR_PASSWORD_MUST_HAVE_4_TILL_10_ELEMENTS";
    public static final String ERR_CANNOT_LOGIN_WITH_CURRENT_LOGIN_AND_PASSWORD = "ERR_CANNOT_LOGIN_WITH_CURRENT_LOGIN_AND_PASSWORD";
    public static final String ERR_CANNOT_CLEAR_TABLE = "ERR_CANNOT_CLEAR_TABLE";
    public static final String ERR_CANNOT_SAVE_ENTITY = "ERR_CANNOT_SAVE_ENTITY";
    public static final String ERR_CANNOT_UPDATE_ENTITY = "ERR_CANNOT_UPDATE_ENTITY";
    public static final String ERR_CANNOT_DELETE_ENTITY_BY_ID = "ERR_CANNOT_DELETE_ENTITY_BY_ID";
    public static final String ERR_CANNOT_OBTAIN_ENTITY_BY_ID = "ERR_CANNOT_OBTAIN_ENTITY_BY_ID";
    public static final String ERR_CANNOT_OBTAIN_ALL_ENTITIES = "ERR_CANNOT_OBTAIN_ALL_ENTITIES";
    public static final String ERR_CANNOT_OBTAIN_ENTRANT_BY_USER_ID = "ERR_CANNOT_OBTAIN_ENTRANT_BY_USER_ID";
    public static final String ERR_CANNOT_OBTAIN_FACULTY_BY_NAME = "ERR_CANNOT_OBTAIN_FACULTY_BY_NAME";
    public static final String ERR_CANNOT_OBTAIN_SUBJECTS_NOT_EXIST_IN_FACULTY = "ERR_CANNOT_OBTAIN_SUBJECTS_NOT_EXIST_IN_FACULTY";
    public static final String ERR_CANNOT_OBTAIN_SUBJECTS_NOT_EXIST_IN_ENTRANT_MARKS = "ERR_CANNOT_OBTAIN_MARKS_NOT_EXIST_AT_ENTRANT";
    public static final String ERR_CANNOT_OBTAIN_TEST_SUBJECTS_WHICH_ENTRANT_HAVE_NOT_BY_FACULTY_ID = "ERR_CANNOT_OBTAIN_TEST_SUBJECTS_WHICH_ENTRANT_HAVE_NOT_BY_FACULTY_ID";
    public static final String ERR_CANNOT_OBTAIN_SUBJECT_BY_NAME = "ERR_CANNOT_OBTAIN_SUBJECT_BY_NAME";
    public static final String ERR_CANNOT_OBTAIN_USER_BY_LOGIN = "ERR_CANNOT_OBTAIN_USER_BY_LOGIN_AND_PASSWORD";
    public static final String ERR_REGISTRATION = "ERR_REGISTRATION";
    public static final String ERR_CANNOT_OBTAIN_ALL_ENTRANT_APPLICATIONS = "ERR_CANNOT_OBTAIN_ALL_ENTRANT_APPLICATIONS";
    public static final String ERR_CANNOT_CALCULATE_AVG_POINTS = "ERR_CANNOT_CALCULATE_AVG_POINTS";
    public static final String ERR_CANNOT_OBTAIN_ALL_ENTRANTS_INFO = "ERR_CANNOT_OBTAIN_ALL_ENTRANTS_INFO";
    public static final String ERR_CANNOT_OBTAIN_SHEET_BY_NUMBER =  "ERR_CANNOT_OBTAIN_SHEET_BY_NUMBER";
    public static final String ERR_CANNOT_OBTAIN_SHEET_INFO_BY_ID = "ERR_CANNOT_OBTAIN_SHEET_INFO_BY_ID";
    public static final String ERR_CANNOT_OBTAIN_ALL_SHEETS_INFO = "ERR_CANNOT_OBTAIN_ALL_SHEETS_INFO";
    public static final String ERR_CANNOT_ADD_TO_SHEET_PASSED_ENTRANTS = "ERR_CANNOT_ADD_TO_SHEET_PASSED_ENTRANTS";
    public static final String ERR_CANNOT_OBTAIN_ENTRANT_SHEET_INFO = "ERR_CANNOT_OBTAIN_ENTRANT_SHEET_INFO";
    public static final String ERR_CANNOT_OBTAIN_ALL_ENTRANT_MARKS_FOR_APPLICATION = "ERR_CANNOT_OBTAIN_ALL_ENTRANT_MARKS_FOR_APPLICATION";
    public static final String ERR_CANNOT_OBTAIN_ALL_CERTIFICATE_MARKS_FOR_APPLICATION = "ERR_CANNOT_OBTAIN_ALL_CERTIFICATE_MARKS_FOR_APPLICATION";
    public static final String ERR_CANNOT_SAVE_MARKS_LIST = "ERR_CANNOT_SAVE_MARKS_LIST";
    public static final String ERR_CANNOT_UPDATE_MARKS_LIST = "ERR_CANNOT_UPDATE_MARKS_LIST";
    public static final String ERR_CANNOT_OBTAIN_ALL_ENTRANT_MARKS = "ERR_CANNOT_OBTAIN_ALL_ENTRANT_MARKS";
    public static final String ERR_CANNOT_OBTAIN_ALL_FACULTY_SUBJECTS = "ERR_CANNOT_OBTAIN_ALL_FACULTY_SUBJECTS";
    public static final String ERR_CANNOT_UPDATE_FACULTY_SUBJECTS_LIST = "ERR_CANNOT_UPDATE_FACULTY_SUBJECTS_LIST";
    public static final String ERR_CANNOT_DELETE_SUBJECT_FROM_FACULTY = "ERR_CANNOT_DELETE_SUBJECT_FROM_FACULTY";
    public static final String ERR_CANNOT_OBTAIN_CURRENT_ENTRANTS_LIST = "ERR_CANNOT_OBTAIN_CURRENT_ENTRANTS_LIST";
    public static final String ERR_CANNOT_OBTAIN_FACULTIES_WITHOUT_SHEET = "ERR_CANNOT_OBTAIN_FACULTIES_WITHOUT_SHEET";
    public static final String ERR_FIELDS_CANNOT_BE_EMPTY = "ERR_FIELDS_CANNOT_BE_EMPTY";
    public static final String ERR_CANNOT_OBTAIN_ENTRANTS_INFO = "ERR_CANNOT_OBTAIN_ENTRANTS_INFO";

    private static MessageManager instance;

	//file message.properties
	private ResourceBundle resourceBundle;

	private MessageManager(){
		// no op
	}

    public static MessageManager getInstance() {
        if(instance == null){
            instance = new MessageManager();
            instance.resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_NAME);
        }
        return instance;
    }

    public void changeLocale(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_NAME, locale);
    }

    public String getProperty(String key) {
        return (String)resourceBundle.getObject(key);
    }

    public String getProperty(String key, Locale locale) {
        return (String) ResourceBundle.getBundle(RESOURCE_FILE_NAME, locale).getObject(key);
    }

    public String getProperty(String key, String localeName) {
        LOG.trace("Call method getProperty with key: --> " + key + " and userLocal --> " + localeName);
        return (String) ResourceBundle.getBundle(RESOURCE_FILE_NAME, new Locale(localeName)).getObject(key);
    }


}
