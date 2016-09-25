package ua.nure.bycheva.SummaryTask4.db.bean;

import ua.nure.bycheva.SummaryTask4.db.entity.Entity;

/**
 * Provide records for virtual table:
 * <pre>
 * |subjects.subject_name|marks.mark_value|marks.exam_type|
 * </pre>
 *
 */
public class ApplicationMarkBean extends Entity {

    private String subjectName;

    private double markValue;

    private String examType;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public double getMarkValue() {
        return markValue;
    }

    public void setMarkValue(double markValue) {
        this.markValue = markValue;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }
}
