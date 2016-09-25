package ua.nure.bycheva.SummaryTask4.db.bean;

import ua.nure.bycheva.SummaryTask4.db.entity.Entity;

/**
 * Provide records for virtual table:
 * <pre>
 * |entrants.id|users.email|entrants.first_name|entrants.last_name|entrants.middle_name|entrants.city|entrants.region|entrants.school|entrants.status_name
 * </pre>
 *
 */
public class UserEntrantBean extends Entity {

    private Long entrantId;

    private String email;

    private String fullName;

    private String city;

    private String region;

    private String school;

    private String statusName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
