package ua.nure.bycheva.SummaryTask4.db.bean;

import ua.nure.bycheva.SummaryTask4.db.entity.Entity;

/**
 * Provide records for virtual table:
 * <pre>
 * sheet_entrants.entrant_id|entrants.first_name|entrants.last_name|entrants.middle_name|applications.avg_point|sheets_entrants.passed_status
 * </pre>
 *
 */
public class SheetEntrantBean extends Entity {

    private Long entrantId;

    private String fullName;

    private double avgPoint;

    private String passedStatusName;

    public Long getEntrantId() {
        return entrantId;
    }

    public void setEntrantId(Long entrantId) {
        this.entrantId = entrantId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getAvgPoint() {
        return avgPoint;
    }

    public void setAvgPoint(double avgPoint) {
        this.avgPoint = avgPoint;
    }

    public String getPassedStatus() {
        return passedStatusName;
    }

    public void setPassedStatus(String passedStatus) {
        this.passedStatusName = passedStatus;
    }

    @Override
    public String toString() {
        return "SheetEntrantBean{" +
                "entrantId=" + entrantId +
                ", fullName='" + fullName + '\'' +
                ", avgPoint=" + avgPoint +
                ", passedStatusName=" + passedStatusName +
                "} " + super.toString();
    }
}
