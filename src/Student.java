
import java.util.ArrayList;
import java.util.Set;
public class Student {

    // Declare student class fields
    private String name;
    private int id;
    private boolean suspendedStatus;
    private Set<Subject> subjects;

    public Student(String name, int id, boolean suspendedStatus, Set<Subject> subjects) {
        // Set the initial value of all attributes
        this.name = name;
        this.id = id;
        this.suspendedStatus = suspendedStatus;
        this.subjects = subjects;
    }

    // Getter and setter for all fields

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSuspended() {
        return suspendedStatus;
    }

    public void setSuspendedStatus(boolean suspendedStatus) {
        this.suspendedStatus = suspendedStatus;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
    public String toString() {
        return "Name : " + getName() + "; ID : " + getId() + "; Suspended Status : " + isSuspended() + "; Subject(s) : " + getSubjects();
    }
}