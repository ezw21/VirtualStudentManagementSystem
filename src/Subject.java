
import java.util.HashSet;
import java.util.Set;
public class Subject {

    //Declare Subject class fields
    private String name;
    private int creditPoints;

    public Subject(String name, int creditPoints) {
        this.name = name;
        this.creditPoints = creditPoints;
    }

    // Getter and Setter for all fields

    public String getName() {
        return name;
    }
    public void setName(String newName) {
        this.name = newName;
    }
    public int getCreditPoints() {
        return creditPoints;
    }
    public void setCreditPoints(int newCreditPoints) {
        this.creditPoints = newCreditPoints;
    }
    public String toString() {
        return "Subject : " + getName() + "; CreditPoints = " + getCreditPoints();
    }
}