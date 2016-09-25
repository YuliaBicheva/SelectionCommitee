package ua.nure.bycheva.SummaryTask4.db.entity;

import java.util.Date;

/**
 * Created by yulia on 30.08.16.
 */
public class Sheet extends Entity {

    private Long uid;

    private Long facultyId;

    private Date createDate;

    private Integer statusId;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    @Override
    public String toString() {
        return "Sheet{" +
                "uid=" + uid +
                ", facultyId=" + facultyId +
                ", createDate=" + createDate +
                ", statusId=" + statusId +
                ", getId()=" + getId() +
                "} " + super.toString();
    }
}
