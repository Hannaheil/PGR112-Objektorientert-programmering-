import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        //Velger å slette data først, slik at "gammel" data blir fjernet
        clearContent();
        //NB! Her har jeg tenkt på rekkefølgen til koden slik at alt først blir slettet, men så blir det lagt til igjen den dataen som trenger å være med
        populateDB();
        eventMenu eventMenu = new eventMenu();
        eventMenu.startMainMenu();
    }
    //Setter en metode som sletter all gammel data fra to av tabellene, slik at det ikke hoper seg opp alt for mye data
    private static void clearContent() throws SQLException {
        JDBC jdbc = new JDBC();
        jdbc.deleteContent();
    }

    private static void populateDB() throws SQLException {
        JDBC jdbc = new JDBC();
        jdbc.populateStudent();
        jdbc.populateParticipants();
    }
}