public class Student extends Person{
    private String studyprogram;
    private int Id;
    private int guests;

    public Student(){}

    public Student(String name, String role, String studyprogram, int id, int guests) {
        super(name, role);
        this.studyprogram = studyprogram;
        this.Id = id;
        this.guests = guests;
    }


    public Student(String name, String studyprogram, int id, int guests) {
        super(name);
        this.studyprogram = studyprogram;
        this.Id = id;
        this.guests = guests;
    }
    public Student(String name, String studyprogram) {
        super(name);
        this.studyprogram = studyprogram;
    }

    public String getStudyprogram() {
        return studyprogram;
    }

    public void setStudyprogram(String studyprogram) {
        this.studyprogram = studyprogram;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }


}
