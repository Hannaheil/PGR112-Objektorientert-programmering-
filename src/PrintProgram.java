public class PrintProgram {
    public PrintProgram() {
    }
    public void printProgram(String studyprogram){
        switch (studyprogram){
            case "Programming":
                printProgramming();
                break;
            case "Art":
                printArt();
                break;
            case "Sports":
                printSports();
                break;
            case "Business":
                printBusiness();
                break;
            default:
                System.out.println("Did not find study program");

        }
    }

    public void printAllPrograms(){
        printProgramming();
        System.out.println("----------------------------------------------");
        printArt();
        System.out.println("----------------------------------------------");
        printSports();
        System.out.println("----------------------------------------------");
        printBusiness();
        System.out.println("----------------------------------------------");
    }
    public void printProgramming(){
        System.out.println("Start time: 13.00");
        System.out.println("Study Program: Programming");
        System.out.println("Your registered as part of this program");
        System.out.println("Break: 5 minutes after 45 minutes");
    }

    public void printArt(){
        System.out.println("Start time: 14.00");
        System.out.println("Study Program: Art");
        System.out.println("Your registered as part of this program");
        System.out.println("Break: 5 minutes after 45 minutes");
    }

    public void printSports(){
        System.out.println("Start time: 12.00");
        System.out.println("Study Program: Sports");
        System.out.println("Your registered as part of this program");
        System.out.println("Break: 5 minutes after 45 minutes");
    }

    public void printBusiness(){
        System.out.println("Start time: 16.00");
        System.out.println("Study Program: Business");
        System.out.println("Your registered as part of this program");
        System.out.println("Break: 5 minutes after 45 minutes");
    }

}
