package ua.nure.bycheva.SummaryTask4.db.entity;

/**
 * Created by yulia on 15.08.16.
 */
public class FacultySubject extends Entity{

    private Long facultyId;

    private Long subjectId;

    private double ratio;

    public FacultySubject() {
    }

    public FacultySubject(Long facultyId, Long subjectId, double ratio) {
        this(null, facultyId, subjectId, ratio);
    }

    public FacultySubject(Long id, Long facultyId, Long subjectId, double ratio) {
        super(id);
        this.facultyId = facultyId;
        this.subjectId = subjectId;
        this.ratio = ratio;
    }

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

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FacultySubject that = (FacultySubject) o;

        if (Long.compare(that.getId(), getId()) != 0) {
            return false;
        }
        if (Double.compare(that.ratio, ratio) != 0) {
            return false;
        }
        if (facultyId != null ? !facultyId.equals(that.facultyId) : that.facultyId != null) {
            return false;
        }
        return subjectId != null ? subjectId.equals(that.subjectId) : that.subjectId == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = facultyId != null ? facultyId.hashCode() : 0;
        result = 31 * result + (subjectId != null ? subjectId.hashCode() : 0);
        temp = Double.doubleToLongBits(ratio);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "FacultySubject{" +
                "facultyId=" + facultyId +
                ", subjectId=" + subjectId +
                ", ratio=" + ratio +
                ", getId()=" + getId() +
                "} " + super.toString();
    }
}
