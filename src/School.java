import java.util.*;

/**
 * Write a description of class School here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class School
{
    private StudentDatabase studentDatabase;
    /**
     * Constructor for objects of class School
     */
    public School()
    {
        studentDatabase = new StudentDatabase();
//        mainMenu();
    }
    
    /**
     * Execute action according to user selection
     */
    public void selectAction(int actionCode) 
    {
        switch (actionCode)
        {
            case(1):                                                                                     //option (1)
                addNewStudentAction();break;
                //Done
            case(2):                                                                                     //option (2)
                //deleteStudent();break;
                //DONE
            case(3):                                                                                     //option (3)
                //toggleSuspend();break;
                //DONE
            case(4):                                                                                     //option (4)
                //modifyStudent();break;
            case(5):                                                                                     //option (5)
//                listStudentsBySubjects();break;
                //Edward call the method 2moro
            case(6):                                                                                     //option (6)
                //listSuspendedStudents();break;
                //Edward call the subject 2moro
            case(7):                                                                                     //option (7)
                studentDatabase.getStudentDetails();break;
            case(8):                                                                                     //option (8)
                System.out.println("Goodbye...");break;
            default:
                System.out.println("\n Please enter from action 1 to 8");
                mainMenu();
        }
        
    }
    /**
     * Show main menu.
     */
    public void mainMenu()
    {
        System.out.println("\nWelcome to the Student Management System");
        System.out.println("===================================");
        System.out.println("(1) Add New Student");
        System.out.println("(2) Delete Student");
        System.out.println("(3) Suspend/Unsuspend Student");
        System.out.println("(4) Edit Student");
        System.out.println("(5) List Students by Subjects");
        System.out.println("(6) List Suspended Students");
        System.out.println("(7) List All Students");
        System.out.println("(8) Exit System");
        System.out.println("\nChoose an option: ");
        
        int choice = checkValidIntegerRange(1, 8);                 //set lower bound to 1 and upper bound to 6.
        selectAction(choice);
    
    }
    /**
     * Intialize a scanner that take in user input
     * Check only integer is inputted, and within range
     * @param lowerBound        LowerBound of the accepted range
     * @param upperBound        UpperBound of the accepted range
     * @return number           Number typed by user and passed all validation
     */
    public int checkValidIntegerRange(int lowerBound, int upperBound) 
    {
        Scanner scanner = new Scanner(System.in);
        int number;
        do 
        {
            System.out.println("Please enter a number from " + lowerBound +" to "+ upperBound);
            while(!scanner.hasNextInt())                            // check if input has only integer
            {
                System.out.println("Not an integer, Please enter a number from " + lowerBound +" to "+ upperBound);
                scanner.next();                                     //continue to look next input
            }
            number = scanner.nextInt();                             //if there is integer in input, take it as number
        } 
        while (number < lowerBound || number > upperBound);
        return number;
    }
    public String checkValidString() {
        Scanner scanner = new Scanner(System.in);
        String name;
        do
        {
            System.out.println("Please enter a valid string, special characters are not allowed");
            while(!scanner.hasNextLine())                            // check if input has only string
            {
                System.out.println("Please enter a valid input ");
                scanner.next();                                     //continue to look next input
            }
            name = scanner.nextLine();
        }
        while (validateString(name));
        return name;
    }
    public boolean convertStringToBoolean(String string) {
        while (!string.equals("Y")  && !string.equals("N")) {
            string = checkValidString();
        }
        return (string.equals("Y")) ? true : false;
    }
    /**
     * Add student
     */
    public void addNewStudentAction() {
        Scanner scanner = new Scanner(System.in);
        String name;
        int id;
        boolean status;

        System.out.println("Please enter a name for new student");
        name = checkValidString();
        System.out.println("Please enter a student ID for new student");
        id = checkValidIntegerRange(100, 999);
        while (!checkIdAvailable(id)) {
            System.out.println("The ID is not available, please enter another ID");
            id = checkValidIntegerRange(100, 999);
        }
        System.out.println("Please enter suspended status for new student. " +
                "\n ***'Y' for yes/true ; 'N' for no/false***");
        status = convertStringToBoolean(checkValidString());
        Set<Subject> subjectList= new HashSet<>();
        System.out.println("DO you wan to add subject(s) to this student?");
        boolean addSubject;
        addSubject = convertStringToBoolean(checkValidString());

        // CreditPoint  <= 15 and still wanting to add subject, stay in while loop
        while (checkSubjectsValidCredit(subjectList) && addSubject) {
            System.out.println("What Subject do you wan to enroll for this student?");
            subjectList.add(studentDatabase.subjectDatabase.getSubjectByName(checkValidString()));
            System.out.println("DO you wish to add another subject to this student?");
            addSubject = convertStringToBoolean(checkValidString());
        }
        System.out.println("SubjectList confirmed, enrolling student..");
        System.out.println(studentDatabase.studentDetails.size());
        studentDatabase.addNewStudent(name,id, status, subjectList);
        System.out.println(studentDatabase.studentDetails.size());

    }
    public boolean checkIdAvailable(int id) {
        // Return true if system cannot get student details;
        if(studentDatabase.getStudentById(id) == null) return true;

        return false;
    }
    public boolean checkSubjectsValidCredit(Set<Subject> subjectList) {
        int creditPoints = 0;
        if (subjectList.size() > 0) {
            for (Subject subjectInSystem : studentDatabase.subjectDatabase.subjectDetails) {
                for(Subject subject : subjectList) {
                    if (subjectInSystem.getName().equals(subject.getName())) creditPoints += subject.getCreditPoints();
                }
            }
        } else return true;

        if(creditPoints <= 15) return true;
        else return false;
    }

    /**
     * Validate string.
     */
    public boolean validateString(String testString){
        for(int i = 0; i < testString.length(); i++) {
            if(!Character.isLetter(testString.charAt(i)) || testString.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }
    /**
      * Delete student
      */
    public void deleteStudent() {
        studentDatabase.deleteStudent(checkValidIntegerRange(1, 999));
    }
    public void toggleSuspend() {
        studentDatabase.toggleSuspended(checkValidIntegerRange(1, 999));
    }
    /**
     * Modify student
     */

    public static void main(String[] args) {
        School school = new School();
        System.out.println(school.studentDatabase.getStudentBySubject("Maths"));

        System.out.println("What subject's student list you wan to view?");
        String name = school.checkValidString();
        System.out.println(school.studentDatabase.getStudentBySubject(name));
        // Tutor I have some comment for you => Be good, coding is easy if you are gud, sadly you are not
    }

}
