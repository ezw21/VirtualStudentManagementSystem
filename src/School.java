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
        mainMenu();
    }
    
    /**
     * Execute action according to user selection
     */
    public void selectAction(int actionCode) 
    {
        switch (actionCode)
        {
            case(1):                                                                                     //option (1) -- Add new student
                addNewStudent();
                mainMenu();break;
            case(2):                                                                                     //option (2) -- Delete a student
                studentDatabase.deleteStudent(checkValidIntegerRange(1, 999));
                mainMenu();break;
            case(3):                                                                                     //option (3) -- Suspend/Unsuspend a student
                studentDatabase.toggleSuspended(checkValidIntegerRange(1, 999));
                mainMenu();break;
            case(4):                                                                                     //option (4) -- Modify student details
                modifyStudent();
                mainMenu();break;
            case(5):                                                                                     //option (5) -- List student(s) by subject
                studentDatabase.getStudentBySubject(subjectNameInput());
                mainMenu();break;
            case(6):                                                                                     //option (6) -- List student that are suspended
                studentDatabase.getSuspendedNameList();
                mainMenu();break;
            case(7):                                                                                     //option (7) -- List all student(s) from database
                studentDatabase.getStudentDetails();
                mainMenu();break;
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
    public void mainMenu() {
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
    public int checkValidIntegerRange(int lowerBound, int upperBound){
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
    public boolean validateString(String testString){
        for(int i = 0; i < testString.length(); i++) {
            if(!Character.isLetter(testString.charAt(i))) {
                return false;
            }
        }
        return true;
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
        while (!validateString(name));
        return name;
    }
    public String subjectNameInput() {
        Scanner scanner = new Scanner(System.in);
        String name;
        System.out.println("Please enter a valid subject name with appropriate SPACE and SPECIAL CHARACTER");
        while(!scanner.hasNextLine())                            // check if input has only string
        {
            System.out.println("Please enter a valid input ");
            scanner.next();                                     //continue to look next input
        }
        name = scanner.nextLine();
        return name;
    }
    public String getInputName() {
        System.out.println("Please enter a name for the student");
         return checkValidString();
    }
    public Integer getInputId() {
        System.out.println("Please enter a student ID for new student");
        int id = checkValidIntegerRange(100, 999);
        while (!checkIdAvailable(id)) {
            System.out.println("The ID is not available, please enter another ID");
            id = checkValidIntegerRange(100, 999);
        }
        return id;
    }
    public boolean getInputBoolean() {
        System.out.println("Please enter suspended status for new student. " +
                "\n ***'Y' for yes/true ; 'N' for no/false***");
        return convertStringToBoolean(checkValidString());
}
    public boolean convertStringToBoolean(String string) {
        while (!string.equals("Y")  && !string.equals("N")) {
            string = checkValidString();
        }
        return (string.equals("Y")) ? true : false;
    }
    public void addNewStudent() {
        String name; int id; boolean status;
        Set<Subject> subjectList= new HashSet<>();

        name = getInputName();
        id = getInputId();
        status = getInputBoolean();
        subjectList = constructSubjectList(subjectList);

        studentDatabase.addNewStudent(name,id, status, subjectList);
        System.out.println(String.format("Successfully enrolled a new student with these details stored in system\n %s",studentDatabase.getStudentById(id)));
    }
    public Set<Subject> constructSubjectList(Set<Subject> subjectList) {
        boolean addSubject;

        System.out.println(String.format("Current subject list = %s \nDO you wish to add another subject to this student?", subjectList));
        addSubject = convertStringToBoolean(checkValidString());
        while (checkSubjectsValidCredit(subjectList) && addSubject) {
            studentDatabase.subjectDatabase.getSubjectDetails();
            System.out.println("Please type in a available subject name that shown in the list above." +
                    "\nWhat Subject do you wan to enroll for this student? (e.g Maths or RocketScience **CAPITAL AND SPACE SENSITIVE)");
            Subject newSubject = studentDatabase.subjectDatabase.getSubjectByName(subjectNameInput());

            if(newSubject == null) System.out.println("Subject name cant be found, please try again.");
            else if(subjectList.contains(newSubject)) System.out.println("You had already picked this subject.");
            else subjectList.add(newSubject);
            if(!checkSubjectsValidCredit(subjectList)) {
                System.out.println("Subject List exceeded 15 credit points, removing the last added subject...");
                subjectList.remove(newSubject);
            }
            System.out.println(String.format("Current subject list = %s \nDO you wish to add another subject to this student?", subjectList));
            addSubject = convertStringToBoolean(checkValidString());
        }
        return subjectList;
}
    public void modifyStudent() {
        String name; int id;

        System.out.println("Please enter the ID of the student you wan to modify");
        Student student = studentDatabase.getStudentById(checkValidIntegerRange(100, 999));
        while(student == null) {
            student = studentDatabase.getStudentById(checkValidIntegerRange(100, 999));
        }
        id = student.getId();
        name = getInputName();
        Set<Subject> subjectList = constructSubjectList(student.getSubjects());

        studentDatabase.modifyStudent(id, name, subjectList);
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
    public static void main(String[] args) {
        School school = new School();
    }

}
