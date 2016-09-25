package ua.nure.bycheva.SummaryTask4.db.entity;

/**
 * Created by yulia on 15.08.16.
 */
public class Subject extends Entity {

    private String name;

    public Subject() {
    }

    public Subject(String name) {
        this(null, name);
    }

    public Subject(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Subject subject = (Subject) o;

        if(Long.compare(getId(), subject.getId()) != 0){
            return false;
        }
        return name != null ? name.equals(subject.name) : subject.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", getId()=" + getId()  +
                "} " + super.toString();
    }
}
