package ua.nure.bycheva.SummaryTask4.db.entity;

/**
 * Created by yulia on 15.08.16.
 */
public class Mark extends Entity {

    private Long subjectId;

    private Long entrantId;

    private double markValue;

    private String examType;

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getEntrantId() {
        return entrantId;
    }

    public void setEntrantId(Long entrantId) {
        this.entrantId = entrantId;
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

    @Override
    public String toString() {
        return "Mark{" +
                "subjectId=" + subjectId +
                ", entrantId=" + entrantId +
                ", markValue=" + markValue +
                ", examType='" + examType + '\'' +
                ", getId()=" + getId() +
                "} " + super.toString();
    }
}
