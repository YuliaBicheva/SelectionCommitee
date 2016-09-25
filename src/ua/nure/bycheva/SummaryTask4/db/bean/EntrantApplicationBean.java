package ua.nure.bycheva.SummaryTask4.db.bean;

import java.util.Date;

import ua.nure.bycheva.SummaryTask4.db.entity.Entity;

/**
 * Provide records for virtual table:
 * <pre>
 * |applications.faculty_id|applications.entrant_id|faculties.faculty_name|applications.create_date|
 * </pre>
 *
 */
public class EntrantApplicationBean extends Entity {

    private Long facultyId;

    private Long entrantId;

    private String facultyName;

    private Date createDate;

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public Long getEntrantId() {
        return entrantId;
    }

    public void setEntrantId(Long entrantId) {
        this.entrantId = entrantId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}