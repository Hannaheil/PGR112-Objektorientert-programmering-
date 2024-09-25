import java.sql.SQLOutput;

public class Guest extends Person{
    private String invitor;

    public Guest(){}

    public Guest(String invitor) {
        this.invitor = invitor;
    }

    public Guest(String name, String role, String invitor) {
        super(name, role);
        this.invitor = invitor;
    }

    public Guest(String name, String invitor) {
        super(name);
        this.invitor = invitor;
    }
    public String getGuestName(
    ) {
        return getName();
    }
    public void printGuest(){
        System.out.println("Guest:\t" + getName() + "\tInviter:\t" + getInvitor());
    }

    public String getInvitor() {
        return invitor;
    }

    public void setInvitor(String invitor) {
        this.invitor = invitor;
    }


}
