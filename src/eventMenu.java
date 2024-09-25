import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class eventMenu {
    //Lagd forekomster av alle klassene slik at det blir lettere for meg å holde på og få tilgang til diverse verdier
    private JDBC jdbc = new JDBC();
    private Participant participant = new Participant();
    private Student student = new Student();
    private Guest guest = new Guest();
    private PrintProgram print = new PrintProgram();

    public eventMenu() {}

    //Hovedmenyklasse, man kommer ikke videre på Sign in hvis ikke innloggingen er korrekt (altså at du er en registrert student i student databasen og har et studieprogram)
    public void startMainMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean programIsRunning = true;

        while (programIsRunning) {
            System.out.println("Write the number of your choice below: " +
                    "\n1. Sign in "+
                    "\n2. See Program" +
                    "\n3. Exit");
            switch (scanner.nextLine()) {
                case "1":{
                    System.out.println("You chose to Sign In");
                    startSignIn();}
                break;
                case "2":{
                    System.out.println("You chose to See Proram");
                    viewAllPrograms();}
                break;
                case "3":{
                    System.out.println("You chose to exit");
                    programIsRunning = false;}
                break;
                default:
                    System.out.println("You have to write the number of your choise: ");
                    startMainMenu();
            }
        }
    }

    public void viewAllPrograms(){
        print.printAllPrograms();
    }


    public void startSignIn() throws SQLException {
        Scanner scannerUsername = new Scanner(System.in);
        System.out.println("Write your name: ");
        String username = scannerUsername.nextLine();
        student.setName(username);

        Scanner userChoice = new Scanner(System.in);

            System.out.println("What is your study program? " +
                    "\n1. Programming " +
                    "\n2. Art" +
                    "\n3. Sports" +
                    "\n4. Business");
            switch (userChoice.nextLine()) {
                case "1": {
                    System.out.println("You chose Programming");
                    student.setStudyprogram("Programming");
                }
                break;
                case "2": {
                    System.out.println("You chose Art");
                    student.setStudyprogram("Art");
                }
                break;
                case "3": {
                    System.out.println("You chose Sports");
                    student.setStudyprogram("Sports");
                }
                break;
                case "4": {
                    System.out.println("You chose Business");
                    student.setStudyprogram("Business");
                }
                break;
                default:
                    System.out.println("You have to write the number of your choise: ");
                    startSignIn();

        }
        //Sjekke om det finnes en en student som er registrert med dette navnet og dette studieprogrammet
        //Jeg vil ikke gi tilgagn til en person som ikke er student, derfor tester jeg opp mot databasen
        boolean checkIfStudentExists = jdbc.findStudent(student.getName(), student.getStudyprogram());
            int countOfGuests = jdbc.getGuestCount(student.getName(), student.getStudyprogram());
            student.setGuests(countOfGuests);
        if (checkIfStudentExists) {
            System.out.println("Welcome " + username);
            participant.setRole("Student");
            System.out.println("You have invited " +
                    student.getGuests() + " out of 4 invited guests");
            studentMenu();
        }else {
            System.out.println("You wrote in wrong name and/or study program or you are not a Student");
            startMainMenu();
        }
    }

    public void studentMenu() throws SQLException {
        Scanner input = new Scanner(System.in);

        boolean programIsRunning = true;

        while (programIsRunning) {
            System.out.println("Welkome " + student.getName() + ". Chose one of the options below: " +
                    "\n1. Register for event "+
                    "\n2. Remove guest"+
                    "\n3. See participants" +
                    "\n4. See participants from your program" +
                    "\n5. Search for participants" +
                    "\n6. See overall program" +
                    "\n7. Exit");
            switch (input.nextLine()) {
                case "1":{
                    registerGuest();}
                break;
                case "2":{
                    removeGuest();}
                    break;
                case "3":{
                    showParticipants();}
                break;
                case "4":{
                    showParticipantsFromSP();}
                break;
                case "5":{
                    searchForParticipant();}
                break;
                case "6":{
                    seeOverAllProgram();}
                    break;
                case "7":{
                    programIsRunning = false;}
                break;
                default:
                    System.out.println("You have to write the number of your choise: ");
                    startSignIn();
            }
        }
    }

    public void registerGuest() throws SQLException {
        //Jeg henter hvor mange gjester denne brukeren har registrert
        int amountOfGuests = jdbc.getGuestCount(student.getName(), student.getStudyprogram());
        int counter = 0;
        System.out.println("How many guests do you want to register? ");
        Scanner userInput = new Scanner(System.in);
        int userAddGuest = Integer.parseInt(userInput.nextLine());
        int updatedGuestCount = counter + amountOfGuests;

        //Jeg vil ikke at studentetn skal kunne registrere flere gjester enn fire total, derofor sjekker jeg på om h*n allerede har
        //fire registrerte gjester, eller om de gjestene som er registrert blir mer enn fire sammenlagt med hva studenten ønsker å legge til
        if (4 < userAddGuest || updatedGuestCount > 4) {
            System.out.println("You have already registered the maximum number of guests or you are trying to register more than 4 guests");
        } else {
            //Jeg løper gjennom løkka like mange ganger som det antall gjester studenten skal registrere
            // sammtidig som jeg øker counteren slik at jeg kan bruke den variabelen til å sjekke at brkeren ikke registrerer flere gjester enn
            //antall gjester den hadde fra før + de nye den har lagt til
            for (int i = 0; i < userAddGuest; i++) {
                Scanner input = new Scanner(System.in);
                System.out.println("Write the name of the guest");
                String guestName = input.nextLine();
                counter++;
                student.setGuests(student.getGuests() + 1);

                Guest guest = new Guest();
                guest.setRole("Guest");
                guest.setName(guestName);
                guest.setInvitor(student.getName());

                jdbc.updateGuest(guest);
                Participant participantGuest = new Participant(guest.getName(), guest.getRole(), "x");
                jdbc.updateParticipant(participantGuest);
                amountOfGuests++; // Oppdaterer amountOfGuests for hver iterasjon
                //Lager en samlet sum for den nye verdien til antall gjester slik at jeg kan sende denne verdien inn og oppdatere databasen
                int sumGuests = amountOfGuests + userAddGuest;
                jdbc.updateStudentGuestList(student.getName(), sumGuests);
            }
        }
    }

    public void removeGuest() throws SQLException {
        int amountOfGuests = jdbc.getGuestCount(student.getName(), student.getStudyprogram());
        ArrayList<Guest> guests = jdbc.getGuests(student.getName());
        boolean foundMatch = false;
        int count = 1;

        //Jeg har tatt sjekker og ser at objektene kommer inn i arrayet, men jeg får ikke tak i navnet på gjesten.
        //Verdien kommer bare som null, derfor kommer jeg ikke inn i løkka og fullført koden
        for(Guest guest : guests){
            if (student.getName().equals(guest.getInvitor())){
                System.out.println(count + "Guest: ");
                guest.printGuest();
                count++;
                foundMatch = true;
            }
        }
        if(!foundMatch){
            System.out.println("You have not invited any guests");
        }else{
            System.out.println("Write the name of the guest that will not attend the ceremony after all: ");
            Scanner scanner = new Scanner(System.in);
            String withdrawGuest = scanner.nextLine();

            for (Guest guest : guests){
                if(guest.getName().equals(withdrawGuest)){
                    jdbc.deleteGuest(withdrawGuest, student.getName());
                    System.out.println("You have successfully removed " + withdrawGuest + " from the registration!");
                    int updateGuestCount = amountOfGuests - 1;
                    jdbc.withdrawStudentGuest(updateGuestCount, student.getName());
                    break;
                }else{
                    System.out.println("Unvalid choice!");
                }
            }
        }
    }
    //Disse metodene er ganske selvforklarende...
    public void showParticipants() throws SQLException {
        ArrayList<Participant> participants = jdbc.getParticipants();
        for(int i = 0; participants.size() > i; i++){
            Participant participant = participants.get(i);
            participant.printParticipant();
        }
    }
    public void showParticipantsFromSP() throws SQLException {
        ArrayList<Participant> participants = jdbc.getParticipantsFromSameStudyProgram(student.getStudyprogram());
        for(int i = 0; participants.size() > i; i++){
            Participant participant = participants.get(i);
            participant.printParticipant();
        }
    }
    public void searchForParticipant() throws SQLException {
        System.out.println("Write in the participant you want to search for: ");
        Scanner input = new Scanner(System.in);
        String searchName = input.nextLine();

        ArrayList<Participant> participants = jdbc.findParticipant(searchName);
        for(int i = 0; participants.size() > i; i++){
            Participant participant = participants.get(i);
            participant.printParticipant();
        }
    }
    public void seeOverAllProgram(){
        System.out.println("This is the program for witch you are registered for: ");
        print.printProgram(student.getStudyprogram());
    }
}