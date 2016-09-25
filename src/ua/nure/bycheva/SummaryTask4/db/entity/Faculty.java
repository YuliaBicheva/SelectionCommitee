package ua.nure.bycheva.SummaryTask4.db.entity;

/**
 * Created by yulia on 13.08.16.
 */
public class Faculty extends Entity {

    private String name;

    private int totalPlaces;

    private int budgetPlaces;

    public Faculty() {
    }

    public Faculty(String name, int totalPlaces, int budgetPlaces) {
        this(null, name, totalPlaces, budgetPlaces);
    }

    public Faculty(Long id, String name, int totalPlaces, int budgetPlaces) {
        super(id);
        this.name = name;
        this.totalPlaces = totalPlaces;
        this.budgetPlaces = budgetPlaces;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalPlaces() {
        return totalPlaces;
    }

    public void setTotalPlaces(int totalPlaces) {
        this.totalPlaces = totalPlaces;
    }

    public int getBudgetPlaces() {
        return budgetPlaces;
    }

    public void setBudgetPlaces(int budgetPlaces) {
        this.budgetPlaces = budgetPlaces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Faculty faculty = (Faculty) o;

        if (Long.compare(getId(),faculty.getId()) != 0) {
            return false;
        }
        if (totalPlaces != faculty.totalPlaces) {
            return false;
        }
        if (budgetPlaces != faculty.budgetPlaces) {
            return false;
        }
        return name != null ? name.equals(faculty.name) : faculty.name == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + totalPlaces;
        result = 31 * result + budgetPlaces;
        return result;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "name='" + name + '\'' +
                ", totalPlaces=" + totalPlaces +
                ", budgetPlaces=" + budgetPlaces +
                ", getId()=" + getId() +
                "} " + super.toString();
    }
}
