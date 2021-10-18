
import java.io.*;
import java.util.*;
public class StudentDatabase {

    public Set<Student> studentDetails;
    public SubjectDatabase subjectDatabase;
    /**
     * Constructor for objects of class StudentDatabase
     */
    public StudentDatabase() {
        studentDetails = new HashSet<>();
        //Initialize student database (Idea of Table joining)
        subjectDatabase = new SubjectDatabase();
        readFile();
    }
    /**
     * Read the student.txt document and return an individual student's detail as string
     */
    public void readFile() {
        String filename = ("src/students.txt");
        try
        {
            try (FileReader inputFile = new FileReader(filename)) {
                Scanner scanner = new Scanner(inputFile);
                while (scanner.hasNextLine()) {
                    String[] studentInfo = scanner.nextLine().split(",");
                    String name = studentInfo[0]; Integer id = Integer.parseInt(studentInfo[1]);
                    Boolean suspended = Boolean.parseBoolean(studentInfo[2]);
                    Set<Subject> studentSubject = new HashSet<>();

                    // Consolidate subject into student's subject attribute
                    Integer startIndex = 3;
                    if (studentInfo.length > startIndex) {
                        while (startIndex < studentInfo.length) {
                            studentSubject.add(new Subject(studentInfo[startIndex], Integer.parseInt(studentInfo[startIndex + 1])));
                            startIndex += 2;
                        }
                    }
                    // Add studentName to studentList and all attributes to studentDetails
                    addNewStudent(name, id, suspended,studentSubject);
                }
            } finally {
                System.out.printf("Closing %s...%n", filename);
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
    public void addNewStudent(String name, Integer id, Boolean suspended, Set<Subject> subjectsSet) {
        try {
            // Add student into system
            studentDetails.add(new Student(name, id, suspended, subjectsSet));
            // Update subject's enrolledStudent(s) list
        } catch(Exception error) {
            System.out.println("Failed to add new Student into System.");
        }
    }
    public Student getStudentById(Integer id) {
        try {
            for (Student student : studentDetails) {
                if (student.getId() == id) {
                    return student;
                }
            }
        } catch(Exception error) {
            System.out.println("Id error");
        }
        return null;
    }
    public void deleteStudent(Integer id) {

        try {
            Student student = getStudentById(id);
            studentDetails.remove(student);
        }catch(Exception error) {
            System.out.println("Failed to delete student. \" +\n" +
                    "                    \"Error : Invalid ID");
        }

    }
    public void toggleSuspended(Integer id) {

        try {
            for (Student studentInSystem : studentDetails) {
                if(studentInSystem.getId() == id) {
                    studentInSystem.setSuspendedStatus(!studentInSystem.isSuspended());
                }
            }
        } catch (Exception error) {
            System.out.println("Failed to modify student's suspend status. " +
                    "Error : Student not in the system");
        }
    }
    public void modifyStudent (Integer id, String name, Set<Subject> subjectSet) {
        try {
            for (Student studentInSystem : studentDetails) {
                if(studentInSystem.getId() == id) {
                    studentInSystem.setName(name);
                    studentInSystem.setSubjects(subjectSet);
                }
            }
        } catch (Exception error) {
            System.out.println("Failed to modify student's profile. " +
                    "Error : Student not in the system.");
        }
    }
    public Set<String> getStudentBySubject(String subjectName) {
        Set<String> studentBySubject = new HashSet<>();
        if(subjectDatabase.subjectList.contains(subjectName)) {
            for (Student studentInSystem : studentDetails) {
                for (Subject subject : studentInSystem.getSubjects()) {
                    if(subject.getName().equals(subjectName) &&
                            !studentInSystem.isSuspended()) {
                        studentBySubject.add(studentInSystem.getName());
                    }
                }
            }
        } else {
            System.out.println("Failed to get student(s) bu subject. " +
                    "Error : Subject not in the system.");
        }
        return studentBySubject;
    }
    public Set<String> getStudentNameList() {
        Set<String> studentList = new HashSet<>();
        for (Student studentInSystem : studentDetails) {
            studentList.add(studentInSystem.getName());
        }
        return studentList;
    }
    public Set<String> getSuspendedNameList() {
        Set<String> studentList = new HashSet<>();
        for (Student studentInSystem : studentDetails) {
            if(studentInSystem.isSuspended())
            studentList.add(studentInSystem.getName());
        }
        return studentList;
    }
    public Set<Student> getStudentDetails() {
        for (Student student : studentDetails) {
            System.out.printf("Subject Details = %s, %d, %b, %s%n",
                    student.getName(),student.getId(),
                    student.isSuspended(),student.getSubjects());
        }
        return studentDetails;
    }
    public static void main(String[] args) {
        StudentDatabase database = new StudentDatabase();
//        database.testAddStudent();
//        database.testDeleteStudent();
//        database.testUpdateSuspend();
//        database.testModifyStudent();
//        database.testSuspendedStudentList();
//        database.testStudentNameList();
//        database.testGetStudentBySubject();
        String stats = "H";

    }
    // Testing methods for Action_1 - 7, !Action 8 exit()
    public void testAddStudent() {
        StudentDatabase db = new StudentDatabase();
        System.out.println(db.studentDetails.size());
        db.addNewStudent("foo", 30, false, new HashSet<>());
        System.out.println(db.studentDetails.size());
    }
    public void testDeleteStudent() {
        StudentDatabase db = new StudentDatabase();
        System.out.println(db.studentDetails.size());
        db.deleteStudent(111);
        System.out.println(db.studentDetails.size());
    }
    public void testUpdateSuspend() {
        StudentDatabase db = new StudentDatabase();
        System.out.println(db.getStudentById(111).isSuspended());
        db.toggleSuspended(111);
        System.out.println(db.getStudentById(111).isSuspended());
    }
    public void testModifyStudent() {
        StudentDatabase db = new StudentDatabase();
        System.out.println(db.getStudentById(111));
        db.modifyStudent(111, "Foo", db.getStudentById(111).getSubjects());
        System.out.println(db.getStudentById(111));
    }
    public void testSuspendedStudentList() {
        StudentDatabase db = new StudentDatabase();
        System.out.println(db.getSuspendedNameList());
    }
    public void testStudentNameList() {
        StudentDatabase db = new StudentDatabase();
        System.out.println(db.getStudentNameList());
    }
    public void testGetStudentBySubject() {
        StudentDatabase db = new StudentDatabase();
        Set<Subject> tempSet = new HashSet();
        tempSet.add(new Subject("Maths", 4));
        db.addNewStudent("Cibai", 999, false, tempSet);
        System.out.println(db.getStudentBySubject("Maths"));
    }
}
