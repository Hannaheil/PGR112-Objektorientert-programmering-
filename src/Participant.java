public class Participant extends Person{
    private String studyprogram;

    public Participant() {
    }

    public Participant(String studyprogram) {
        this.studyprogram = studyprogram;
    }

    public Participant(String name, String role, String studyprogram) {
        super(name, role);
        this.studyprogram = studyprogram;
    }

    public void printParticipant(){
        System.out.println("Participant:\t" + getName() + "\t Role: \t" + getRole() + "\t Study Program: \t" + getStudyprogram());
    }


    public String getStudyprogram() {
        return studyprogram;
    }

    public void setStudyprogram(String studyprogram) {
        this.studyprogram = studyprogram;
    }



}
