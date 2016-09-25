package ua.nure.bycheva.SummaryTask4.db.bean;

import ua.nure.bycheva.SummaryTask4.db.entity.Entity;

/**
 * Provide records for virtual table:
 * <pre>
 * |faculties_subjects.faculty_id|faculties_subject.subejct_id|subjects.subject_name|faculties_subjects.ratio|
 * </pre>
 *
 */
public class FacultySubjectBean extends Entity{

    private Long facultyId;

    private Long subjectId;

    private String subjectName;

    private double ratio;

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
