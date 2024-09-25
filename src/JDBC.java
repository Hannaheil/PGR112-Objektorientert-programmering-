import java.beans.PropertyEditorSupport;
import java.sql.*;
import java.util.ArrayList;

public class JDBC {
    public JDBC() {
    }
    //konnekter til databasen
    public Connection connectDB() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/universityDB";
        String username = "root";
        String password = "root";
        return DriverManager.getConnection(url, username, password);
    }

    //Jeg har valgt å lage en metode som slette alle tidligere handlinger i databasen, slik at den ikke skulle fylle seg opp når
    // jeg satt og jobbet med den. Den vil slette innholdet i databasen hver gang programmet kjøres for første gang på nytt.
    //Etter det kaller jeg på en annen metode som setter inn den "orginale"/"default" dataen igjen.
    public void deleteContent() throws SQLException {
        String sqlStudent = """
                DELETE FROM universityDB.Student;
                """;
        PreparedStatement preparedStatement = connectDB().prepareStatement(sqlStudent);
        preparedStatement.executeUpdate();

        String sqlGuest = """
                DELETE FROM universityDB.Guest;
                """;
        PreparedStatement preparedStatement2 = connectDB().prepareStatement(sqlGuest);
        preparedStatement2.executeUpdate();

        String sqlParticipant = """
                DELETE FROM universityDB.Participants;
                """;
        PreparedStatement preparedStatement3 = connectDB().prepareStatement(sqlParticipant);
        preparedStatement3.executeUpdate();
    }
    public void populateStudent() throws SQLException {
        String sqlPopulateStudent = """
                INSERT INTO universityDB.Student(studentName, studyprogram, guestInvited)
                VALUES (?, ?, ?), (?, ?, ?), (?, ?, ?);
                """;
        PreparedStatement preparedStatement = connectDB().prepareStatement(sqlPopulateStudent);
        String studentName = "Kari";
        String studyprogram = "Programming";
        int guestInvited = 0;

        String studentName1 = "Kjell";
        String studyprogram1 = "Art";
        int guestInvited1 = 0;

        String studentName2 = "Mia";
        String studyprogram2 = "Sports";
        int guestInvited2 = 0;

        preparedStatement.setString(1, studentName);
        preparedStatement.setString(2, studyprogram);
        preparedStatement.setInt(3, guestInvited);
        preparedStatement.setString(4, studentName1);
        preparedStatement.setString(5, studyprogram1);
        preparedStatement.setInt(6, guestInvited1);
        preparedStatement.setString(7, studentName2);
        preparedStatement.setString(8, studyprogram2);
        preparedStatement.setInt(9, guestInvited2);

        preparedStatement.executeUpdate();
    }

    //Jeg valgte å sette inn de samme studentene som participants. Der jeg tok utgangspunt i at alle som logger inn her blir participants
    public void populateParticipants() throws SQLException {
        String sqlPopulateParticipants = """
                INSERT INTO universityDB.Participants(role, name, studyprogram)
                VALUES (?, ?, ?), (?, ?, ?), (?, ?, ?);
                """;
        PreparedStatement preparedStatement = connectDB().prepareStatement(sqlPopulateParticipants);
        String role = "Student";
        String studentName = "Kari";
        String studyprogram = "Programming";

        String role1 = "Student";
        String studentName1 = "Kjell";
        String studyprogram1 = "Art";

        String role2 = "Student";
        String studentName2 = "Mia";
        String studyprogram2 = "Sports";

        preparedStatement.setString(1, role);
        preparedStatement.setString(2, studentName);
        preparedStatement.setString(3, studyprogram);
        preparedStatement.setString(4, role1);
        preparedStatement.setString(5, studentName1);
        preparedStatement.setString(6, studyprogram1);
        preparedStatement.setString(7, role2);
        preparedStatement.setString(8, studentName2);
        preparedStatement.setString(9, studyprogram2);

        preparedStatement.executeUpdate();
    }

    public boolean findStudent(String studentName, String studyprogram) throws SQLException {
        String sqlFindParticipant = """
                SELECT *
                FROM universityDB.Student
                WHERE studentName = ? AND studyprogram = ?;
                """;
        PreparedStatement statement = connectDB().prepareStatement(sqlFindParticipant);
        statement.setString(1, studentName);
        statement.setString(2,studyprogram);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            String name = resultSet.getString(1);
            String program = resultSet.getString(2);
            return true;
        }
        return false;
    }
    public int getGuestCount(String studentName, String studyprogram) throws SQLException {
        String sqlFindParticipant = "SELECT guestInvited FROM universityDB.Student WHERE studentName = ? AND studyprogram = ?";
        PreparedStatement statement = connectDB().prepareStatement(sqlFindParticipant);
        statement.setString(1, studentName);
        statement.setString(2, studyprogram);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int amountOfGuests = resultSet.getInt("guestInvited");
            return amountOfGuests;
        }

        return 0; // Returnerer 0 hvis ingen rad ble funnet i resultatsettet
    }

    public ArrayList<Guest> getGuests(String studentName) throws SQLException {
        ArrayList<Guest> guests = new ArrayList<>();
        String sqlGetGuests = """
                SELECT *
                FROM universityDB.Guest
                WHERE inviter = ?;
                """;
        PreparedStatement preparedStatement = connectDB().prepareStatement(sqlGetGuests);
        preparedStatement.setString(1, studentName);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            String guestName = resultSet.getString("guestName");
            Guest temp = new Guest(guestName);
            guests.add(temp);
        }
        return guests;
    }

    public void deleteGuest(String guestName, String inviter) throws SQLException {
        String sqlDeleteGuestFromGuest = """
                DELETE FROM universityDB.Guest WHERE guestName = ? AND inviter = ?;
                """;
        PreparedStatement preparedStatement = connectDB().prepareStatement(sqlDeleteGuestFromGuest);
        preparedStatement.setString(1, guestName);
        preparedStatement.setString(2, inviter);
        preparedStatement.executeUpdate();
    }

    public void withdrawStudentGuest(int guests, String studentName) throws SQLException {
        String sqlWithdrawStudentGuest = """
                UPDATE universityDB.Student  
                SET guestInvited = ?
                WHERE studentName = ?
                """;
        PreparedStatement preparedStatement = connectDB().prepareStatement(sqlWithdrawStudentGuest);
        preparedStatement.setInt(1, guests);
        preparedStatement.setString(2, studentName);
        preparedStatement.executeUpdate();
    }

    public void updateStudentGuestList(String studentName, int guestCount) throws SQLException {
        String sqlUpdateStudentGuestList = "UPDATE universityDB.Student SET guestInvited = ? WHERE studentName = ?";
        PreparedStatement preparedStatement = connectDB().prepareStatement(sqlUpdateStudentGuestList);
        preparedStatement.setInt(1, guestCount);
        preparedStatement.setString(2, studentName);
        preparedStatement.executeUpdate();
    }


    public void updateParticipant(Participant participant) throws SQLException {
        String sqlUpdateParticipant = """
                INSERT INTO universityDB.Participants(role, name, studyprogram)
                VALUES (?, ?, ?)
                """;
        PreparedStatement preparedStatement = connectDB().prepareStatement(sqlUpdateParticipant);
        preparedStatement.setString(1, participant.getRole());
        preparedStatement.setString(2, participant.getName());
        preparedStatement.setString(3, participant.getStudyprogram());
        preparedStatement.executeUpdate();
    }

    public void updateGuest(Guest guest) throws SQLException {
        String sqlUpdateGuest = """
                INSERT INTO universityDB.Guest(guestName, inviter)
                VALUES (?, ?);
                """;
        PreparedStatement preparedStatement = connectDB().prepareStatement(sqlUpdateGuest);
        preparedStatement.setString(1, guest.getName());
        preparedStatement.setString(2, guest.getInvitor());
        preparedStatement.executeUpdate();
    }


    public ArrayList<Participant> getParticipants() throws SQLException {
        ArrayList<Participant> participants = new ArrayList<>();
        String sqlGetParticipants = """
                SELECT *
                FROM universityDB.Participants;
                """;
        PreparedStatement preparedStatement = connectDB().prepareStatement(sqlGetParticipants);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            String role = resultSet.getString(1);
            String name = resultSet.getString(2);
            String studyprogram = resultSet.getString(3);

            Participant temp = new Participant(name, role, studyprogram);
            participants.add(temp);
        }
        return participants;
    }

    public ArrayList<Participant> getParticipantsFromSameStudyProgram(String studyprogram) throws SQLException {
        ArrayList<Participant> participants = new ArrayList<>();
        String sqlGetParticipantsFromSameStudyProgram = """
                SELECT *
                FROM universityDB.Participants
                WHERE studyprogram = ?;
                """;
        PreparedStatement preparedStatement = connectDB().prepareStatement(sqlGetParticipantsFromSameStudyProgram);
        preparedStatement.setString(1, studyprogram);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            String role = resultSet.getString(1);
            String name = resultSet.getString(2);
            String studyprogram1 = resultSet.getString(3);

            Participant temp = new Participant(name, role, studyprogram1);
            participants.add(temp);
        }
        return participants;
    }

    public ArrayList<Participant> findParticipant(String inputName) throws SQLException {
        ArrayList<Participant> searchParticipants = new ArrayList<>();
        String sqlFindParticipant = """
                SELECT *
                FROM universityDB.Participants
                WHERE name = ?;
                """;
        PreparedStatement statement = connectDB().prepareStatement(sqlFindParticipant);
        statement.setString(1, inputName);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            String role = resultSet.getString(1);
            String name = resultSet.getString(2);
            String studyprogram = resultSet.getString(3);

            Participant temp = new Participant(name, role, studyprogram);
            searchParticipants.add(temp);
        }
        return searchParticipants;
    }
}
