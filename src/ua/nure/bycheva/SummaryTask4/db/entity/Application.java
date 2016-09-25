package ua.nure.bycheva.SummaryTask4.db.entity;

import java.util.Date;

/**
 * Created by yulia on 15.08.16.
 */
public class Application extends Entity {

    private Long entrantId;

    private Long facultyId;

    private double avgPoint;

    private Date createDate;

    public Application() {
    }

    public Application(Long entrantId, Long facultyId, double avgPoint, Date createDate) {
        this(null, entrantId, facultyId, avgPoint, createDate);
    }

    public Application(Long id, Long entrantId, Long facultyId, double avgPoint, Date createDate) {
        super(id);
        this.entrantId = entrantId;
        this.facultyId = facultyId;
        this.avgPoint = avgPoint;
        this.createDate = createDate;
    }

    public Long getEntrantId() {
        return entrantId;
    }

    public void setEntrantId(Long entrantId) {
        this.entrantId = entrantId;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public double getAvgPoint() {
        return avgPoint;
    }

    public void setAvgPoint(double avgPoint) {
        this.avgPoint = avgPoint;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }

        Application that = (Application) o;

        if (Double.compare(that.getId(), getId()) != 0) {
            return false;
        }
        if (Double.compare(that.avgPoint, avgPoint) != 0) {
            return false;
        }
        if (entrantId != null ? !entrantId.equals(that.entrantId) : that.entrantId != null) {
            return false;
        }
        if (facultyId != null ? !facultyId.equals(that.facultyId) : that.facultyId != null) {
            return false;
        }
        return true;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = entrantId != null ? entrantId.hashCode() : 0;
        result = 31 * result + (facultyId != null ? facultyId.hashCode() : 0);
        temp = Double.doubleToLongBits(avgPoint);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
//        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Application{" +
                "entrantId=" + entrantId +
                ", facultyId=" + facultyId +
                ", avgPoint=" + avgPoint +
                ", createDate=" + createDate +
                ", getId()=" + getId() +
                "} " + super.toString();
    }
}
