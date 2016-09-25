package ua.nure.bycheva.SummaryTask4.db.bean;

import java.util.Date;

import ua.nure.bycheva.SummaryTask4.db.entity.Entity;

/**
 * Provide records for virtual table:
 * <pre>
 * |sheets.uid|sheets.create_date|coutn(entrants_id)|sheets_statuses.status_name|
 * </pre>
 *
 */
public class SheetBean extends Entity{

    private Long uid;

    private String facultyName;

    private Date createDate;

    private int passedCount;

    private String statusName;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
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

    public int getPassedCount() {
        return passedCount;
    }

    public void setPassedCount(int passedCount) {
        this.passedCount = passedCount;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
