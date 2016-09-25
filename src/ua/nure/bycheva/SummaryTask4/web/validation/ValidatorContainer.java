package ua.nure.bycheva.SummaryTask4.web.validation;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * Holder for all form validators.
 *
 */
public class ValidatorContainer {

    private static final Logger LOG = Logger.getLogger(ValidatorContainer.class);

    private static Map<String, Validator> validators = new TreeMap<>();

    static{
        validators.put("applicationForm", new ApplicationFormValidator());
        validators.put("registerForm", new RegisterFormValidator());
        validators.put("subjectForm", new SubjectFormValidator());
        validators.put("facultyForm", new FacultyFormValidator());
        validators.put("facultySubjectForm", new FacultySubjectFormValidator());
        validators.put("markForm", new MarkFormValidator());
        validators.put("updateMarksForm", new UpdateMarksFormValidator());
        validators.put("loginForm", new LoginFormValidator());
        validators.put("settingsForm", new SettingFormValidator());
        validators.put("sheetForm", new SheetFormValidator());
    }

    /**
     * Return checking result of containing validator object by the given name
     * @param formName
     * @return boolean result
     */
    public static boolean needToValidate(String formName){
        return formName != null && validators.containsKey(formName);
    }

    /**
     * Returns validator object with the given name.
     *
     * @param formName
     *            Name of the validator form.
     * @return Validator object.
     */
    public static Validator get(String formName){
        return formName == null ? null : validators.get(formName);
    }
}
