package ua.nure.bycheva.SummaryTask4.db.entity;

/**
 * Created by yulia on 13.08.16.
 */
public class Entrant extends Entity {

    private Long userId;

    private String city;

    private String region;

    private String school;

    private Integer statusId;

    public Entrant() {}

    public Entrant(Long userId, String city, String region, String school, Integer statusId) {
        this(null, userId, city, region, school, statusId);
    }

    public Entrant(Long id, Long userId, String city, String region, String school, Integer statusId) {
        super(id);
        this.userId = userId;
        this.city = city;
        this.region = region;
        this.school = school;
        this.statusId = statusId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public void changeStatus(){
        if(statusId != null){
            this.statusId = statusId == 0 ? 1 : 0;
        }
    }

    @Override
    public String toString() {
        return "Entrant{" +
                "userId=" + userId +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", school='" + school + '\'' +
                ", statusId='" + statusId + '\'' +
                ", getId()=" + getId() +
                "} " + super.toString();
    }
}
