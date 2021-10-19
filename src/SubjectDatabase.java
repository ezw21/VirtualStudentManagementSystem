import java.io.*;
import java.util.*;

public class SubjectDatabase {

    public Set<String> subjectList;
    public Set<Subject> subjectDetails;
    /**
     * Constructor for objects of class StudentDatabase
     */
    public SubjectDatabase()
    {
        // Can read file in default since always read from same file
        readFile();
    }

    /**
     * Read the student.txt document and return an individual student's detail as string
     */
    public void readFile() {
        String filename = ("src/subjects.txt");
        subjectDetails = new HashSet<>();
        subjectList = new HashSet<>();

        try
        {
            try (FileReader inputFile = new FileReader(filename)) {
                Scanner scanner = new Scanner(inputFile);
                //Read file line by line
                while (scanner.hasNextLine()) {

                    // Split the line with ',' to get subjectName and subjectCredit raw string
                    String[] subject = scanner.nextLine().split(",");
                    // Add subjectName to a list
                    // Add subject to subjectDetail collection
                    addNewSubject(subject[0], Integer.parseInt(subject[1]));
                }
            } finally {
                System.out.println(String.format("Closing %s...", filename));
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println(filename + " not found");
        }
        catch (IOException exception)
        {
            System.out.println("Unexpected I/O error occurred");
        }
    }

    // Getter and Setter for all fields

    /**
     * Add a new subject to subjectList and subjectDetails Collection
     * @param subjectName the name of the new subject
     * @param subjectCredit the credit point of the new subject
     */
    public void addNewSubject(String subjectName, Integer subjectCredit) {
        try {
            subjectDetails.add(new Subject(subjectName, subjectCredit));
            subjectList.add(subjectName);
        }
        catch(Exception error) {
            System.out.println("Failed to add new Subject into System.");
        }
    }
    public Set<String> getSubjectList() {
        System.out.println("Subject List = " + subjectList);
        return subjectList;
    }
    public void addSubjectList(String subject) {
        this.subjectList.add(subject);
    }
    public Set<Subject> getSubjectDetails() {
        subjectDetails.forEach(System.out::println);
        return subjectDetails;
    }
    public Subject getSubjectByName(String subjectName) {
        try {
            for (Subject subject : subjectDetails) {
                if (subject.getName().equals(subjectName)) {
                    return subject;
                }
            }
        } catch (Exception error) {
            System.out.println("Failed to add new Subject into System." +
                    "Error : Subject not found in system");
        }
        return null;
    }
    public void addSubjectDetails(Subject subject) {
        this.subjectDetails.add(subject);
    }
    public static void main(String[] args) {
        SubjectDatabase database = new SubjectDatabase();
    }
}
